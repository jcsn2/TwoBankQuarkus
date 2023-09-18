package acc.br.service;

import acc.br.model.Clientes;
import acc.br.repository.ClientesRepository;
import acc.br.exception.CPF_invalido;
import acc.br.exception.ClienteExistenteException;
import acc.br.exception.ClienteNaoEncontradoException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.InputMismatchException;
import java.util.List;

/**
 * Um serviço para operações relacionadas a clientes.
 */
@ApplicationScoped
public class ClientesService {

    @Inject
    ClientesRepository clientesRepository;
    
    @Inject
    EntityManager entityManager;

    /**
     * Lista todos os clientes registrados.
     *
     * @return Uma lista de clientes.
     */
    public List<Clientes> listarClientes() {
        return clientesRepository.listAll();
    }

    /**
     * Obtém um cliente pelo seu ID.
     *
     * @param clienteID O ID do cliente a ser obtido.
     * @return O cliente correspondente, se encontrado.
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado.
     */
    @Transactional
    public Clientes obterCliente(Long clienteID) {
        Clientes cliente = clientesRepository.findById(clienteID);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com ID: " + clienteID);
        }
        return cliente;
    }

    /**
     * Cria um novo cliente.
     *
     * @param cliente O cliente a ser criado.
     * @return O cliente criado com sucesso.
     * @throws ClienteExistenteException Se um cliente com o mesmo ID já existir.
     */
    @Transactional
    public Clientes criarCliente(Clientes cliente) {
        if (cliente.getClienteID() != null && clientesRepository.findById(cliente.getClienteID()) != null) {
            throw new ClienteExistenteException("Cliente já existe com ID: " + cliente.getClienteID());
        }
        validarCliente(cliente); 
        Clientes clienteGerenciado = entityManager.merge(cliente); // Mescla a entidade no contexto de persistência
        entityManager.persist(clienteGerenciado); // Persiste a entidade
        return cliente;
    }

    /**
     * Atualiza um cliente existente.
     *
     * @param clienteID O ID do cliente a ser atualizado.
     * @param cliente   Os novos dados do cliente.
     * @return O cliente atualizado com sucesso.
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado.
     */
    @Transactional
    public Clientes atualizarCliente(Long clienteID, Clientes cliente) {
        Clientes clienteExistente = clientesRepository.findById(clienteID);
        if (clienteExistente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com ID: " + clienteID);
        }
        validarCliente(cliente);
        cliente.setClienteID(clienteID);
        
        Clientes clienteGerenciado = entityManager.merge(cliente); // Mescla a entidade no contexto de persistência
        entityManager.persist(clienteGerenciado); // Persiste a entidade
        return cliente;
    }

    /**
     * Remove um cliente pelo seu ID.
     *
     * @param clienteID O ID do cliente a ser removido.
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado.
     */
    @Transactional
    public void removerCliente(Long clienteID) {
        Clientes cliente = clientesRepository.findById(clienteID);
        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com ID: " + clienteID);
        }
        cliente.delete();
    }

    /**
     * Valida um cliente.
     *
     * @param cliente O cliente a ser validado.
     * @throws IllegalArgumentException         Se o cliente for inválido.
     * @throws ClienteExistenteException         Se um cliente com o mesmo CPF ou e-mail já existir.
     * @throws ClienteNaoEncontradoException     Se o cliente não estiver ativo ou não existir.
     */
    public void validarCliente(Clientes cliente) {
        // if (cliente == null || cliente.getClienteID() == null) {
        //     throw new IllegalArgumentException("Cliente inválido.");
        // }

        // // Verifica se o cliente existe na base de dados
        // Clientes clienteExistente = clientesRepository.findById(cliente.getClienteID());
        // if (clienteExistente == null || !clienteExistente.isAtivo()) {
        //     throw new ClienteNaoEncontradoException("Cliente não encontrado ou não está ativo.");
        // }

        // Verifica se o e-mail é único
        if (clientesRepository.count("email = ?1 and clienteID != ?2", cliente.getEmail(), cliente.getClienteID()) > 0) {
            throw new ClienteExistenteException("E-mail já está sendo usado por outro cliente.");
        }

        // Verifica se o CPF é único
        if (clientesRepository.count("cpf = ?1 and clienteID != ?2", cliente.getCpf(), cliente.getClienteID()) > 0) {
            throw new ClienteExistenteException("CPF já está sendo usado por outro cliente.");
        }

        // Verifica se CPF digitado tem digitas válidos
        if (!isCPFValid(cliente.getCpf())) {
            throw new CPF_invalido("CPF inválido! Digite novamente");
        }
        
    }
    public void verificaExistenciaCliente(Clientes cliente) {

        // Verifica se o cliente existe na base de dados
        Clientes clienteExistente = clientesRepository.findById(cliente.getClienteID());
        if (clienteExistente == null || !clienteExistente.isAtivo()) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado ou não está ativo.");
        }
    }

    public static boolean isCPFValid(String cpf) {
        // Check if the CPF is 11 digits long
        if (cpf.equals("00000000000") ||
            cpf.equals("11111111111") ||
            cpf.equals("22222222222") || cpf.equals("33333333333") ||
            cpf.equals("44444444444") || cpf.equals("55555555555") ||
            cpf.equals("66666666666") || cpf.equals("77777777777") ||
            cpf.equals("88888888888") || cpf.equals("99999999999") ||
            (cpf.length() != 11)) {
          return false;
        }
       char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0
        // (48 eh a posicao de '0' na tabela ASCII)
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
        }

        public static String imprimeCPF(String cpf) {
            return(cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
            cpf.substring(6, 9) + "-" + cpf.substring(9, 11));
        }

}


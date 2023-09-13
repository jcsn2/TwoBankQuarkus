package acc.br.service;

import acc.br.model.Clientes;
import acc.br.repository.ClientesRepository;
import acc.br.exception.ClienteExistenteException;
import acc.br.exception.ClienteNaoEncontradoException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.List;

/**
 * Um serviço para operações relacionadas a clientes.
 */
@ApplicationScoped
public class ClientesService {

    @Inject
    ClientesRepository clientesRepository;

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
        cliente.persist();
        
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
        cliente.persist();
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
        if (cliente == null || cliente.getClienteID() == null) {
            throw new IllegalArgumentException("Cliente inválido.");
        }

        // Verifica se o cliente existe na base de dados
        Clientes clienteExistente = clientesRepository.findById(cliente.getClienteID());
        if (clienteExistente == null || !clienteExistente.isAtivo()) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado ou não está ativo.");
        }

        // Verifica se o e-mail é único
        if (clientesRepository.count("email = ?1 and clienteID != ?2", cliente.getEmail(), cliente.getClienteID()) > 0) {
            throw new ClienteExistenteException("E-mail já está sendo usado por outro cliente.");
        }

        // Verifica se o CPF é único
        if (clientesRepository.count("cpf = ?1 and clienteID != ?2", cliente.getCpf(), cliente.getClienteID()) > 0) {
            throw new ClienteExistenteException("CPF já está sendo usado por outro cliente.");
        }
    }

}


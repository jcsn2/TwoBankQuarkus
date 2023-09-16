package acc.br.service;

import acc.br.model.*;
import acc.br.repository.*;
import acc.br.exception.*;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import acc.br.util.TipoTransacao;
import java.util.List;

class ClientesServiceTest {

    @Inject
    ClientesRepository clientesRepository;
    @Inject
    ClientesService clientesService;

    @Test
    public void testListarClientes() {
        // Cria um cliente
        Clientes cliente = new Clientes();
        cliente.setNome("Fulano de Tal");
        cliente.setCpf("045.751.236-92");
        cliente.setEmail("fulano@example.com");

        // Adiciona o cliente ao repositório
        clientesRepository.persist(cliente);

        // Obtém a lista de clientes
        List<Clientes> clientes = clientesService.listarClientes();

        clientes.get(clientes.indexOf(cliente)).getNome();
        // Verifica se a lista contém o cliente criado
        assertEquals("Fulano de Tal", clientes.get(clientes.indexOf(cliente)).getNome());
    }

    @Test
    public void testObterCliente() {
        // Cria um cliente
        Clientes cliente = new Clientes();
        cliente.setNome("Fulano de Tal");
        cliente.setCpf("045.751.236-92");
        cliente.setEmail("fulano@example.com");

        // Adiciona o cliente ao repositório
        clientesRepository.persist(cliente);

        // Obtém o cliente pelo ID
        Clientes clienteObtido = clientesService.obterCliente(cliente.getClienteID());

        // Verifica se o cliente obtido é o mesmo que o criado
        assertEquals(clienteObtido, cliente);
    }

    @Test
    public void testCriarCliente() {
        // Cria um cliente
        Clientes cliente = new Clientes();
        cliente.setNome("Fulano de Tal");
        cliente.setCpf("045.751.236-92");
        cliente.setEmail("fulano@example.com");

        // Cria o cliente
        Clientes clienteCriado = clientesService.criarCliente(cliente);

        // Verifica se o cliente foi criado com sucesso
        assertNull(clienteCriado);
        assertNull(clienteCriado.getClienteID());
        assertEquals(clienteCriado.getNome(), cliente.getNome());
        assertEquals(clienteCriado.getCpf(), cliente.getCpf());
        assertEquals(clienteCriado.getEmail(), cliente.getEmail());
    }

    @Test
    public void testAtualizarCliente() {
        // Cria um cliente
        Clientes cliente = new Clientes();
        cliente.setNome("Fulano de Tal");
        cliente.setCpf("045.751.236-92");
        cliente.setEmail("fulano@example.com");

        // Adiciona o cliente ao repositório
        clientesRepository.persist(cliente);

        // Atualiza o cliente
        cliente.setNome("Beltrano de Tal");
        cliente.setEmail("beltrano@example.com");
        clientesService.atualizarCliente(cliente.getClienteID(), cliente);

        // Obtém o cliente atualizado
        Clientes clienteAtualizado = clientesService.obterCliente(cliente.getClienteID());

        // Verifica se o cliente foi atualizado com sucesso
        assertEquals(clienteAtualizado.getNome(), "Beltrano de Tal");
        assertEquals(clienteAtualizado.getEmail(), "beltrano@example.com");
    }

    @Test
    public void testRemoverCliente() {
        // Cria um cliente
        Clientes cliente = new Clientes();
        cliente.setNome("Fulano de Tal");
        cliente.setCpf("123.456.789-00");
        cliente.setEmail("fulano@example.com");

        // Adiciona o cliente ao repositório
        clientesRepository.persist(cliente);

        // Remove o cliente
        clientesService.removerCliente(cliente.getClienteID());

        // Tenta obter o cliente
        try {
            clientesService.obterCliente(cliente.getClienteID());
            fail("O cliente não deveria ser encontrado");
        } catch (ClienteNaoEncontradoException e) {
            // O cliente não foi encontrado, como esperado
        }
    }

    @Test
    public void testValidarClienteComCPFInvalido() {
        // Cria um cliente com CPF inválido
        Clientes cliente = new Clientes();
        cliente.setNome("Fulano de Tal");
        cliente.setCpf("12345678900");
        cliente.setEmail("fulano@example.com");

        // Tenta validar o cliente
        try {
            clientesService.validarCliente(cliente);
            fail("O cliente deveria ser inválido");
        } catch (CPF_invalido e) {
            // O cliente é inválido, como esperado
        }
    }

    @Test
    public void testValidarClienteComCPFValido() {
        // Cria um cliente com CPF válido
        Clientes cliente = new Clientes();
        cliente.setNome("Fulano de Tal");
        cliente.setCpf("123.456.789-00");
        cliente.setEmail("fulano@example.com");

        // Valida o cliente
        clientesService.validarCliente(cliente);
    }

    @Test
    public void testIsCPFValidComCPFInvalido() {
        // Verifica se um CPF inválido é válido
        assertFalse(clientesService.isCPFValid("12345678900"));
    }

    @Test
    public void testIsCPFValidComCPFValido() {
        // Verifica se um CPF válido é válido
        assertTrue(clientesService.isCPFValid("04575123692"));
    }
}

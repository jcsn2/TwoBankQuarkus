package acc.br.service;

import acc.br.model.*;
import acc.br.repository.*;
import acc.br.exception.*;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import acc.br.util.TipoTransacao;

import java.time.LocalTime;
import java.util.List;

class EmprestimosServiceTest {

    @Inject
    ClientesRepository clientesRepository;

    @Inject
    EmprestimosService emprestimosService;
    
    @Inject
    EmprestimosRepository emprestimosRepository;

    @Test
    public void testCriarEmprestimo() {
        // Cria um cliente
        Clientes cliente = new Clientes();
        cliente.setNome("Fulano de Tal");
        cliente.setCpf("123.456.789-00");
        cliente.setEmail("fulano@example.com");

        // Adiciona o cliente ao repositÃ³rio
        clientesRepository.persist(cliente);

        // Cria um emprÃ©stimo
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setClienteID(cliente.getClienteID());
        emprestimo.setValorEmprestimo(new BigDecimal(1000));
        emprestimo.setTaxaJuros(new BigDecimal(10));
        LocalDate myObj = LocalDate.now();
        emprestimo.setDataSolicitacao(myObj);
        emprestimo.setStatus("Ativo");
        emprestimo.setPrazoMeses(12);

        // Cria o emprÃ©stimo
        Emprestimos emprestimoCriado = emprestimosService.criarEmprestimo(emprestimo);

        // Verifica se o emprÃ©stimo foi criado com sucesso
        assertNull(emprestimoCriado);
        assertEquals(emprestimoCriado.getClienteID(), cliente.getClienteID());
        assertEquals(emprestimoCriado.getValorEmprestimo(), new BigDecimal(1000));
        assertEquals(emprestimoCriado.getTaxaJuros(), new BigDecimal(10));
        assertEquals(emprestimoCriado.getDataSolicitacao(), myObj);
        assertEquals(emprestimoCriado.getStatus(), "Ativo");
        assertEquals(emprestimoCriado.getPrazoMeses(), 12);
        assertEquals(emprestimoCriado.getValorParcelas(), new BigDecimal(83.33333333333333));
    }

    @Test
    public void testCriarEmprestimoComClienteInexistente() {
        // Cria um emprÃ©stimo com um cliente inexistente
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setClienteID(Long.MAX_VALUE);
        emprestimo.setValorEmprestimo(new BigDecimal(1000));
        emprestimo.setTaxaJuros(new BigDecimal(10));
        emprestimo.setDataSolicitacao(LocalDate.now());
        emprestimo.setStatus("Ativo");
        emprestimo.setPrazoMeses(12);

        // Tenta criar o emprÃ©stimo
        try {
            emprestimosService.criarEmprestimo(emprestimo);
            fail("O emprÃ©stimo deveria ter sido rejeitado");
        } catch (ClienteNaoEncontradoException e) {
            // O emprÃ©stimo foi rejeitado, como esperado
        }
    }
    @Test
    void deveAtualizarEmprestimoComSucesso() {
        //Objeto data
        LocalDate myDateObj = LocalDate.now();
        // Cria um emprestimo
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setValorEmprestimo(BigDecimal.valueOf(1000));
        emprestimo.setTaxaJuros(BigDecimal.valueOf(10));
        emprestimo.setDataSolicitacao(myDateObj);
        emprestimo.setStatus("Aprovado");
        emprestimo.setPrazoMeses(12);
        emprestimo.setClienteID(1L);

        // Salva o emprestimo no banco de dados
        emprestimosRepository.persist(emprestimo);

        // Atualiza os dados do emprestimo
        emprestimo.setValorEmprestimo(BigDecimal.valueOf(2000));
        emprestimo.setTaxaJuros(BigDecimal.valueOf(20));
        emprestimo.setDataSolicitacao(myDateObj);
        emprestimo.setStatus("Em análise");
        emprestimo.setPrazoMeses(24);

        // Atualiza o emprestimo
        Emprestimos emprestimoAtualizado = emprestimosService.atualizarEmprestimo(emprestimo.getEmprestimoID(), emprestimo);

        // Valida os dados atualizados
        assertEquals(BigDecimal.valueOf(2000), emprestimoAtualizado.getValorEmprestimo());
        assertEquals(BigDecimal.valueOf(20), emprestimoAtualizado.getTaxaJuros());
        assertEquals(myDateObj, emprestimoAtualizado.getDataSolicitacao());
        assertEquals("Em análise", emprestimoAtualizado.getStatus());
        assertEquals(24, emprestimoAtualizado.getPrazoMeses());
    }

    @Test
    void deveLancarExcecaoEmprestimoNaoEncontrado() {
        // Tenta atualizar um emprestimo que não existe
        emprestimosService.atualizarEmprestimo(1L, new Emprestimos());

        // Valida a exceção lançada
        assertThrows(EmprestimoNaoEncontradoException.class, () -> {
            emprestimosService.atualizarEmprestimo(1L, new Emprestimos());
        });
    }

    @Test
    void deveLancarExcecaoClienteNaoEncontrado() {
        //Objeto data
        LocalDate myDateObj = LocalDate.now();
        // Cria um emprestimo
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setValorEmprestimo(BigDecimal.valueOf(1000));
        emprestimo.setTaxaJuros(BigDecimal.valueOf(10));
        emprestimo.setDataSolicitacao(myDateObj);
        emprestimo.setStatus("Aprovado");
        emprestimo.setPrazoMeses(12);
        emprestimo.setClienteID(1L);

        // Salva o emprestimo no banco de dados
        emprestimosRepository.persist(emprestimo);

        // Tenta atualizar o emprestimo com um ID de cliente que não existe
        emprestimo.setClienteID(2L);
        emprestimosService.atualizarEmprestimo(emprestimo.getEmprestimoID(), emprestimo);

        // Valida a exceção lançada
        assertThrows(ClienteNaoEncontradoException.class, () -> {
            emprestimosService.atualizarEmprestimo(emprestimo.getEmprestimoID(), emprestimo);
        });
    }
@Test
    void calcularValorParcelas_valorEmprestimo1000_taxaJuros10_prazo12_valorParcelas166_66() {
        // Arrange
        BigDecimal valorEmprestimo = BigDecimal.valueOf(1000);
        BigDecimal taxaJurosMensal = BigDecimal.valueOf(0.1);
        int prazoMeses = 12;

        // Act
        BigDecimal valorParcelas = emprestimosService.calcularValorParcelas(valorEmprestimo, taxaJurosMensal, prazoMeses);

        // Assert
        Assertions.assertEquals(BigDecimal.valueOf(166.66), valorParcelas);
    }

    @Test
    void deletarEmprestimo_emprestimoExistente_emprestimoDeletadoComSucesso() {
        // Arrange
        Long id = 1L;
        Emprestimos emprestimoExistente = new Emprestimos();
        emprestimoExistente.setEmprestimoID(id);
        // Act
        emprestimosService.deletarEmprestimo(id);

        // Assert
        Assertions.assertNull(emprestimosRepository.findById(id));
    }

    @Test
    void deletarEmprestimo_emprestimoNaoExistente_lancaExcecao() {
        // Arrange
        Long id = 2L;

        // Act
        Assertions.assertThrows(EmprestimoNaoEncontradoException.class, () -> emprestimosService.deletarEmprestimo(id));

        // Assert
    }

    @Test
    void listarEmprestimos_emprestimosCadastrados_listaRetornadaComTodosOsEmprestimos() {
        // Arrange
        Emprestimos emprestimo1 = new Emprestimos();
        emprestimo1.setEmprestimoID(1L);

        Emprestimos emprestimo2 = new Emprestimos();
        emprestimo2.setEmprestimoID(2L);

        emprestimosRepository.persist(emprestimo1);
        emprestimosRepository.persist(emprestimo2);

        // Act
        List<Emprestimos> emprestimos = emprestimosService.listarEmprestimos();

        // Assert
        Assertions.assertEquals(2, emprestimos.size());
        Assertions.assertEquals(emprestimo1, emprestimos.get(0));
        Assertions.assertEquals(emprestimo2, emprestimos.get(1));
    }

}
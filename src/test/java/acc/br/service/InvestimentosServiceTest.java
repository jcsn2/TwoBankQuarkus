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


public class InvestimentosServiceTest {

    @Inject
    ClientesRepository clientesRepository;

    @Inject
    InvestimentosService investimentosService;

    @Inject
    InvestimentosRepository investimentosRepository;
    
    @Test
    public void criarInvestimento() {
        // Cria um investimento
        Investimentos investimento = new Investimentos();
        investimento.setNomeInvestimento("Investimento 1");
        investimento.setTipoInvestimento("Renda fixa");
        investimento.setSaldoInicial(new BigDecimal("1000.00"));
        investimento.setTaxaRetorno(new BigDecimal("0.05"));
        investimento.setClienteID(1L);

        // Chama o método de serviço
        investimentosService.criarInvestimento(investimento);
        Long invId = investimento.getInvestimentoID();

        // Verifica se o investimento foi criado
        Investimentos investimentoCriado = investimentosRepository.findById(invId);
        assertEquals(investimento.getNomeInvestimento(), investimentoCriado.getNomeInvestimento());
        assertEquals(investimento.getTipoInvestimento(), investimentoCriado.getTipoInvestimento());
        assertEquals(investimento.getSaldoInicial(), investimentoCriado.getSaldoInicial());
        assertEquals(investimento.getTaxaRetorno(), investimentoCriado.getTaxaRetorno());
        assertEquals(investimento.getClienteID(), investimentoCriado.getClienteID());
    }

    @Test
    public void atualizarInvestimento() throws InvestimentoNaoEncontradoException {
        // Cria um investimento
        Investimentos investimento = new Investimentos();
        investimento.setNomeInvestimento("Investimento 1");
        investimento.setTipoInvestimento("Renda fixa");
        investimento.setSaldoInicial(new BigDecimal("1000.00"));
        investimento.setTaxaRetorno(new BigDecimal("0.05"));
        investimento.setClienteID(1L);

        // Chama o método de serviço para criar o investimento
        investimentosService.criarInvestimento(investimento);

        // Atualiza o investimento
        investimento.setNomeInvestimento("Investimento Atualizado");

        // Chama o método de serviço para atualizar o investimento
        investimentosService.atualizarInvestimento(investimento.getInvestimentoID(), investimento);

        // Verifica se o investimento foi atualizado
        Investimentos investimentoAtualizado = investimentosRepository.findById(investimento.getInvestimentoID());
        assertEquals(investimento.getNomeInvestimento(), investimentoAtualizado.getNomeInvestimento());
        assertEquals(investimento.getTipoInvestimento(), investimentoAtualizado.getTipoInvestimento());
        assertEquals(investimento.getSaldoInicial(), investimentoAtualizado.getSaldoInicial());
        assertEquals(investimento.getTaxaRetorno(), investimentoAtualizado.getTaxaRetorno());
        assertEquals(investimento.getClienteID(), investimentoAtualizado.getClienteID());
    }

    @Test
    public void atualizarInvestimentoInexistente() {
        // Cria um investimento
        Investimentos investimento = new Investimentos();
        investimento.setNomeInvestimento("Investimento 1");
        investimento.setTipoInvestimento("Renda fixa");
        investimento.setSaldoInicial(new BigDecimal("1000.00"));
        investimento.setTaxaRetorno(new BigDecimal("0.05"));
        investimento.setClienteID(1L);

        // Chama o método de serviço para criar o investimento
        investimentosService.criarInvestimento(investimento);

        // Atualiza o investimento com um ID inválido
        investimento.setInvestimentoID(100L);

        // Espera uma exceção
        assertThrows(InvestimentoNaoEncontradoException.class, () -> {
            investimentosService.atualizarInvestimento(investimento.getInvestimentoID(), investimento);
        });
    }
    @Test
    public void deveObterInvestimentoPeloId() throws InvestimentoNaoEncontradoException {
        Investimentos investimento = investimentosService.obterInvestimento(1L);

        assertNotNull(investimento);
        assertEquals("Ação", investimento.getTipoInvestimento());
        assertEquals(1000.00, investimento.getSaldoInicial().doubleValue());
        assertEquals(10.00, investimento.getTaxaRetorno().doubleValue());
    }

    @Test
    public void deveLancarExcecaoAoObterInvestimentoInexistente() {
        assertThrows(InvestimentoNaoEncontradoException.class, () -> investimentosService.obterInvestimento(9999L));
    }

    @Test
    public void deveListarTodosOsInvestimentos() {
        List<Investimentos> investimentos = investimentosService.listarInvestimentos();

        assertEquals(3, investimentos.size());
    }

    @Test
    public void deveRemoverInvestimento() throws InvestimentoNaoEncontradoException {
        investimentosService.removerInvestimento(1L);

        assertThrows(InvestimentoNaoEncontradoException.class, () -> investimentosService.obterInvestimento(1L));
    }

    @Test
    public void deveCalcularRetornoInvestimento() {
        // Cria um investimento
        Investimentos investimento = new Investimentos();
        investimento.setNomeInvestimento("Investimento 1");
        investimento.setTipoInvestimento("Renda fixa");
        investimento.setSaldoInicial(new BigDecimal("1000.00"));
        investimento.setTaxaRetorno(new BigDecimal("0.05"));
        investimento.setClienteID(1L);

        // Chama o método de serviço para criar o investimento
        investimentosService.criarInvestimento(investimento);

        BigDecimal retorno = investimentosService.calcularRetornoInvestimento(investimento);

        assertEquals(100.00, retorno.doubleValue());
    }

    @Test
    public void deveListarInvestimentosPorCliente() {
        List<Investimentos> investimentos = investimentosService.listarInvestimentosPorCliente(1);

        assertEquals(2, investimentos.size());
    }
}

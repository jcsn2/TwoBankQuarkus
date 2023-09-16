package acc.br.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import javax.validation.*;
import javax.validation.constraints.*;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class InvestimentosTest {

    @Test
    public void testNomeInvestimento() {
        Investimentos investimento = new Investimentos();
        investimento.setNomeInvestimento("Investimento A");

        assertEquals("Investimento A", investimento.getNomeInvestimento());
    }

    @Test
    public void testTipoInvestimento() {
        Investimentos investimento = new Investimentos();
        investimento.setTipoInvestimento("Carteira de ações");

        assertEquals("Carteira de ações", investimento.getTipoInvestimento());
    }

    @Test
    public void testSaldoInvestimento() {
        Investimentos investimento = new Investimentos();
        investimento.setSaldoInvestimento(new BigDecimal("1000.00"));

        assertEquals(new BigDecimal("1000.00"), investimento.getSaldoInvestimento());
    }

    @Test
    public void testDataInicio() {
        Investimentos investimento = new Investimentos();
        investimento.setDataInicio(LocalDate.now());

        assertEquals(LocalDate.now(), investimento.getDataInicio());
    }

    @Test
    public void testClienteID() {
        Investimentos investimento = new Investimentos();
        investimento.setClienteID(1L);

        assertEquals(1L, investimento.getClienteID());
    }

    @Test
    public void testTaxaRetorno() {
        Investimentos investimento = new Investimentos();
        investimento.setTaxaRetorno(new BigDecimal("10.00"));

        assertEquals(new BigDecimal("10.00"), investimento.getTaxaRetorno());
    }

    @Test
    public void testSaldoInicial() {
        Investimentos investimento = new Investimentos();
        investimento.setSaldoInicial(new BigDecimal("1000.00"));

        assertEquals(new BigDecimal("1000.00"), investimento.getSaldoInicial());
    }
    
}
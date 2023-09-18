package acc.br.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmprestimosTest {

    @Test
    public void testValorEmprestimo() {
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setValorEmprestimo(new BigDecimal("1000.00"));

        assertEquals(new BigDecimal("1000.00"), emprestimo.getValorEmprestimo());
    }

    @Test
    public void testTaxaJuros() {
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setTaxaJuros(new BigDecimal("5.00"));

        assertEquals(new BigDecimal("5.00"), emprestimo.getTaxaJuros());
    }

    @Test
    public void testDataSolicitacao() {
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setDataSolicitacao(LocalDate.now());

        assertEquals(LocalDate.now(), emprestimo.getDataSolicitacao());
    }

    @Test
    public void testStatus() {
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setStatus("Aprovado");

        assertEquals("Aprovado", emprestimo.getStatus());
    }

    @Test
    public void testClienteID() {
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setClienteID(1L);

        assertEquals(1L, emprestimo.getClienteID());
    }

    @Test
    public void testPrazoMeses() {
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setPrazoMeses(12);

        assertEquals(12, emprestimo.getPrazoMeses());
    }

    @Test
    public void testValorParcelas() {
        Emprestimos emprestimo = new Emprestimos();
        emprestimo.setValorParcelas(new BigDecimal("83.33"));

        assertEquals(new BigDecimal("83.33"), emprestimo.getValorParcelas());
    }
}
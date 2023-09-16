package acc.br.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaCorrenteTest {

    @Test
    void getcontaCorrenteID() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setcontaCorrenteID(320L);
        Long contaCorrenteId = contaCorrente.getcontaCorrenteID();
        assertEquals(320L,contaCorrenteId);
    }

    @Test
    void setcontaCorrenteID() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setcontaCorrenteID(320L);
        Long contaCorrenteId = contaCorrente.getcontaCorrenteID();
        assertEquals(320L,contaCorrenteId);
    }

    @Test
    void getLimiteCredito() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setLimiteCredito(BigDecimal.valueOf(1000));
        BigDecimal limiteCredito = contaCorrente.getLimiteCredito();
        assertEquals(BigDecimal.valueOf(1000),limiteCredito);
    }

    @Test
    void setLimiteCredito() {
        ContaCorrente contaCorrente = new ContaCorrente();
        contaCorrente.setLimiteCredito(BigDecimal.valueOf(1000));
        BigDecimal limiteCredito = contaCorrente.getLimiteCredito();
        assertEquals(BigDecimal.valueOf(1000),limiteCredito);
    }
}
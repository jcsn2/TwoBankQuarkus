package acc.br.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import acc.br.util.TipoTransacao;

class TransacoesTest {
    @Test
    public void testGetTransacaoID() {
        Transacoes transacao = new Transacoes();
        transacao.setTransacaoID(1L);

        assertEquals(1L, transacao.getTransacaoID());
    }

    @Test
    public void testSetTransacaoID() {
        Transacoes transacao = new Transacoes();
        transacao.setTransacaoID(2L);

        assertEquals(2L, transacao.getTransacaoID());
    }

    @Test
    public void testGetTipoTransacao() {
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.SAQUE);

        assertEquals(TipoTransacao.SAQUE, transacao.getTipoTransacao());
    }

    @Test
    public void testSetTipoTransacao() {
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);

        assertEquals(TipoTransacao.DEPOSITO, transacao.getTipoTransacao());
    }

    @Test
    public void testGetValor() {
        Transacoes transacao = new Transacoes();
        transacao.setValor(new BigDecimal("100.00"));

        assertEquals(new BigDecimal("100.00"), transacao.getValor());
    }

    @Test
    public void testSetValor() {
        Transacoes transacao = new Transacoes();
        transacao.setValor(new BigDecimal("200.00"));

        assertEquals(new BigDecimal("200.00"), transacao.getValor());
    }

    @Test
    public void testGetDataHoraTransacao() {
        Transacoes transacao = new Transacoes();
        transacao.setDataHoraTransacao(LocalDate.now());

        assertEquals(LocalDate.now(), transacao.getDataHoraTransacao());
    }

    @Test
    public void testSetDataHoraTransacao() {
        Transacoes transacao = new Transacoes();
        transacao.setDataHoraTransacao(LocalDate.now().plusDays(1));

        assertEquals(LocalDate.now().plusDays(1), transacao.getDataHoraTransacao());
    }

    @Test
    public void testGetContaID() {
        Transacoes transacao = new Transacoes();
        transacao.setContaID(1L);

        assertEquals(1L, transacao.getContaID());
    }

    @Test
    public void testSetContaID() {
        Transacoes transacao = new Transacoes();
        transacao.setContaID(2L);

        assertEquals(2L, transacao.getContaID());
    }

    @Test
    public void testGetContaDestinoID() {
        Transacoes transacao = new Transacoes();
        transacao.setContaDestinoID(1L);

        assertEquals(1L, transacao.getContaDestinoID());
    }

    @Test
    public void testSetContaDestinoID() {
        Transacoes transacao = new Transacoes();
        transacao.setContaDestinoID(2L);

        assertEquals(2L, transacao.getContaDestinoID());
    }

    @Test
    public void testGetNumeroCheque() {
        Transacoes transacao = new Transacoes();
        transacao.setNumeroCheque("123456789");

        assertEquals("123456789", transacao.getNumeroCheque());
    }

    @Test
    public void testSetNumeroCheque() {
        Transacoes transacao = new Transacoes();
        transacao.setNumeroCheque("987654321");

        assertEquals("987654321", transacao.getNumeroCheque());
    }

}
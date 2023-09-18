package acc.br.model;

import acc.br.model.ContasConjuntas;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContasConjuntasTest {

    @Test
    public void testContaConjuntaIDNotNull() {
        ContasConjuntas conta = new ContasConjuntas();
        conta.setContaConjuntaID(null);

        assertEquals(NullPointerException.class, conta.getContaConjuntaID());
    }

    @Test
    public void testTitularesSize() {
        ContasConjuntas conta = new ContasConjuntas();
        conta.setTitulares("Teste");

        assertEquals(10, conta.getTitulares().length());
    }

    @Test
    public void testTitularesNotNull() {
        ContasConjuntas conta = new ContasConjuntas();
        conta.setTitulares(null);

        assertEquals(NullPointerException.class, conta.getTitulares());
    }
}

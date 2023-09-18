package acc.br.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ParametrosConfiguracaoTest {

    @Test
    public void testGetParametroID() {
        ParametrosConfiguracao parametro = new ParametrosConfiguracao();
        parametro.setParametroID(1L);

        assertEquals(1L, parametro.getParametroID());
    }

    @Test
    public void testSetParametroID() {
        ParametrosConfiguracao parametro = new ParametrosConfiguracao();
        parametro.setParametroID(2L);

        assertEquals(2L, parametro.getParametroID());
    }

    @Test
    public void testNomeParametro() {
        ParametrosConfiguracao parametro = new ParametrosConfiguracao();
        parametro.setNomeParametro("Nome do parâmetro");

        assertEquals("Nome do parâmetro", parametro.getNomeParametro());
    }

    @Test
    public void testValorParametro() {
        ParametrosConfiguracao parametro = new ParametrosConfiguracao();
        parametro.setValorParametro("Valor do parâmetro");

        assertEquals("Valor do parâmetro", parametro.getValorParametro());
    }

    @Test
    public void testDescricaoParametro() {
        ParametrosConfiguracao parametro = new ParametrosConfiguracao();
        parametro.setDescricaoParametro("Descrição do parâmetro");

        assertEquals("Descrição do parâmetro", parametro.getDescricaoParametro());
    }
}
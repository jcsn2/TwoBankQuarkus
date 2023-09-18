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

public class ParametrosConfiguracaoServiceTest {
    
    @Inject
    ClientesRepository clientesRepository;

    @Inject
    ParametrosConfiguracaoService parametrosConfiguracaoService;

    @Inject
    ParametrosConfiguracaoRepository parametrosconfiguracaoRepository;

    
    @Test
    public void deveCriarParametroConfiguracao() {
        ParametrosConfiguracao parametrosConfiguracao = new ParametrosConfiguracao();
        parametrosConfiguracao.setNomeParametro("nome");
        parametrosConfiguracao.setValorParametro("valor");
        parametrosConfiguracao.setDescricaoParametro("descricao");

        parametrosConfiguracaoService.criarParametrosConfiguracao(parametrosConfiguracao);
        
        assertNotNull(parametrosConfiguracao.getParametroID());
        assertEquals("nome", parametrosConfiguracao.getNomeParametro());
        assertEquals("valor", parametrosConfiguracao.getValorParametro());
        assertEquals("descricao", parametrosConfiguracao.getDescricaoParametro());
    }

    //ToDo: Implement this functionality
    // @Test
    // public void deveLancarExcecaoAoTentarCriarParametroConfiguracaoComNomeDuplicado() {
    //     ParametrosConfiguracao parametrosConfiguracao1 = new ParametrosConfiguracao();
    //     parametrosConfiguracao1.setNomeParametro("nome");
    //     parametrosConfiguracao1.setValorParametro("valor");
    //     parametrosConfiguracao1.setDescricaoParametro("descricao");

    //     parametrosConfiguracaoService.criarParametrosConfiguracao(parametrosConfiguracao1);

    //     assertThrows(ParametroConfiguracaoDuplicadoException.class, () -> parametrosConfiguracaoService.criarParametrosConfiguracao(parametrosConfiguracao1));
    // }

    @Test
    public void deveAtualizarParametroConfiguracao() throws ParametroConfiguracaoNaoEncontradoException {
        ParametrosConfiguracao parametrosConfiguracao = new ParametrosConfiguracao();
        parametrosConfiguracao.setNomeParametro("nome");
        parametrosConfiguracao.setValorParametro("valor");
        parametrosConfiguracao.setDescricaoParametro("descricao");

        parametrosConfiguracaoService.criarParametrosConfiguracao(parametrosConfiguracao);

        // Atualiza o valor do parâmetro
        parametrosConfiguracao.setValorParametro("novo valor");

        parametrosConfiguracaoService.atualizarParametrosConfiguracao(parametrosConfiguracao.getParametroID(), parametrosConfiguracao);

        assertEquals("novo valor", parametrosConfiguracao.getValorParametro());
    }

    @Test
    public void deveLancarExcecaoAoTentarAtualizarParametroConfiguracaoInexistente() {
        assertThrows(ParametroConfiguracaoNaoEncontradoException.class, () -> parametrosConfiguracaoService.atualizarParametrosConfiguracao(1L, new ParametrosConfiguracao()));
    }
    @Test
    public void deveObterParametroConfiguracaoPeloId() throws ParametroConfiguracaoNaoEncontradoException {
        ParametrosConfiguracao parametrosConfiguracao = new ParametrosConfiguracao();
        parametrosConfiguracao.setNomeParametro("taxa_juros");
        parametrosConfiguracao.setValorParametro("10.00");
        parametrosConfiguracao.setDescricaoParametro("descricao");

        parametrosConfiguracaoService.criarParametrosConfiguracao(parametrosConfiguracao);
        
        ParametrosConfiguracao parametrosConfiguracao_aux = parametrosConfiguracaoService.obterParametrosConfiguracao(parametrosConfiguracao.getParametroID());

        assertNotNull(parametrosConfiguracao_aux);
        assertEquals("taxa_juros", parametrosConfiguracao_aux.getNomeParametro());
        assertEquals("10.00", parametrosConfiguracao_aux.getValorParametro());
    }

    @Test
    public void deveLancarExcecaoAoObterParametroConfiguracaoInexistente() {
        assertThrows(ParametroConfiguracaoNaoEncontradoException.class, () -> parametrosConfiguracaoService.obterParametrosConfiguracao(9999L));
    }

    @Test
    public void deveListarTodosOsParametrosConfiguracao() {
        List<ParametrosConfiguracao> parametrosConfiguracao = parametrosConfiguracaoService.listarParametrosConfiguracao();

        assertNotNull(parametrosConfiguracao.size());
    }

    @Test
    public void deveRemoverParametroConfiguracao() throws ParametroConfiguracaoNaoEncontradoException {
        parametrosConfiguracaoService.removerParametrosConfiguracao(1L);

        assertThrows(ParametroConfiguracaoNaoEncontradoException.class, () -> parametrosConfiguracaoService.obterParametrosConfiguracao(1L));
    }
}

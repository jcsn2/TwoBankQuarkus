package acc.br.service;

import acc.br.exception.ParametroConfiguracaoNaoEncontradoException;
import acc.br.model.ParametrosConfiguracao;
import acc.br.repository.ParametrosConfiguracaoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Classe de serviço para a entidade ParametrosConfiguracao.
 */
@ApplicationScoped
public class ParametrosConfiguracaoService {

    @Inject
    ParametrosConfiguracaoRepository parametrosConfiguracaoRepository;

    @Inject
    EntityManager entityManager;

    /**
     * Cria um novo registro de parâmetro de configuração.
     *
     * @param parametrosConfiguracao O objeto ParametrosConfiguracao a ser criado.
     */
    @Transactional
    public void criarParametrosConfiguracao(ParametrosConfiguracao parametrosConfiguracao) {
         ParametrosConfiguracao parametrosConfiguracaoGerenciado = entityManager.merge(parametrosConfiguracao); // Mescla a entidade no contexto de persistência
        entityManager.persist(parametrosConfiguracaoGerenciado); // Persiste a entidade
    }

    /**
     * Atualiza um registro de parâmetro de configuração existente.
     *
     * @param id                      O ID do registro de parâmetro de configuração a ser atualizado.
     * @param parametrosConfiguracao   O objeto ParametrosConfiguracao com as atualizações.
     * @throws ParametrosConfiguracaoNaoEncontradoException Se o registro de parâmetro de configuração não for encontrado.
     */
    @Transactional
    public void atualizarParametrosConfiguracao(Long id, ParametrosConfiguracao parametrosConfiguracao)
            throws ParametroConfiguracaoNaoEncontradoException {
        ParametrosConfiguracao entity = parametrosConfiguracaoRepository.findById(id);
        if (entity == null) {
            throw new ParametroConfiguracaoNaoEncontradoException("Registro de parâmetro de configuração não encontrado com o ID: " + id);
        }

        // Atualize os campos relevantes de acordo com as regras de negócio
        entity.setNomeParametro(parametrosConfiguracao.getNomeParametro());
        entity.setValorParametro(parametrosConfiguracao.getValorParametro());
        entity.setDescricaoParametro(parametrosConfiguracao.getDescricaoParametro());

         ParametrosConfiguracao parametrosConfiguracaoGerenciado = entityManager.merge(entity); // Mescla a entidade no contexto de persistência
        entityManager.persist(parametrosConfiguracaoGerenciado); // Persiste a entidade
    }

    /**
     * Obtém um registro de parâmetro de configuração pelo ID.
     *
     * @param id O ID do registro de parâmetro de configuração a ser obtido.
     * @return O registro de parâmetro de configuração correspondente ao ID.
     * @throws ParametrosConfiguracaoNaoEncontradoException Se o registro de parâmetro de configuração não for encontrado.
     */
    public ParametrosConfiguracao obterParametrosConfiguracao(Long id) throws ParametroConfiguracaoNaoEncontradoException {
        ParametrosConfiguracao parametrosConfiguracao = parametrosConfiguracaoRepository.findById(id);
        if (parametrosConfiguracao == null) {
            throw new ParametroConfiguracaoNaoEncontradoException("Registro de parâmetro de configuração não encontrado com o ID: " + id);
        }
        return parametrosConfiguracao;
    }

    /**
     * Lista todos os registros de parâmetros de configuração.
     *
     * @return Uma lista de todos os registros de parâmetros de configuração.
     */
    public List<ParametrosConfiguracao> listarParametrosConfiguracao() {
        List<ParametrosConfiguracao> allParameters = parametrosConfiguracaoRepository.listAll();
        return allParameters;
    }

    /**
     * Remove um registro de parâmetro de configuração pelo ID.
     *
     * @param id O ID do registro de parâmetro de configuração a ser removido.
     * @throws ParametrosConfiguracaoNaoEncontradoException Se o registro de parâmetro de configuração não for encontrado.
     */
    @Transactional
    public void removerParametrosConfiguracao(Long id) throws ParametroConfiguracaoNaoEncontradoException {
        ParametrosConfiguracao parametrosConfiguracao = parametrosConfiguracaoRepository.findById(id);
        if (parametrosConfiguracao == null) {
            throw new ParametroConfiguracaoNaoEncontradoException("Registro de parâmetro de configuração não encontrado com o ID: " + id);
        }
        parametrosConfiguracaoRepository.delete(parametrosConfiguracao);
    }
}

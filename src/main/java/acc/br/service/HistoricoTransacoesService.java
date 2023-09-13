package acc.br.service;

import acc.br.exception.HistoricoTransacoesNaoEncontradoException;
import acc.br.model.HistoricoTransacoes;
import acc.br.repository.HistoricoTransacoesRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe de serviço para a entidade HistoricoTransacoes.
 */
@ApplicationScoped
public class HistoricoTransacoesService {

    @Inject
    HistoricoTransacoesRepository historicoTransacoesRepository;

    /**
     * Cria um novo registro de histórico de transações.
     *
     * @param historicoTransacoes O objeto HistoricoTransacoes a ser criado.
     */
    @Transactional
    public void criarHistoricoTransacoes(HistoricoTransacoes historicoTransacoes) {
        historicoTransacoesRepository.persist(historicoTransacoes);
    }

    /**
     * Atualiza um registro de histórico de transações existente.
     *
     * @param id                 O ID do registro de histórico de transações a ser atualizado.
     * @param historicoTransacoes O objeto HistoricoTransacoes com as atualizações.
     * @throws HistoricoTransacoesNaoEncontradoException Se o registro de histórico de transações não for encontrado.
     */
    @Transactional
    public void atualizarHistoricoTransacoes(Long id, HistoricoTransacoes historicoTransacoes)
            throws HistoricoTransacoesNaoEncontradoException {
        HistoricoTransacoes entity = historicoTransacoesRepository.findById(id);
        if (entity == null) {
            throw new HistoricoTransacoesNaoEncontradoException("Registro de histórico de transações não encontrado com o ID: " + id);
        }

        // Atualize os campos relevantes de acordo com as regras de negócio
        entity.setTransacaoID(historicoTransacoes.getTransacaoID());
        entity.setTipoTransacao(historicoTransacoes.getTipoTransacao());
        entity.setValor(historicoTransacoes.getValor());
        entity.setDataTransacao(LocalDateTime.now());

        historicoTransacoesRepository.persist(entity);
    }

    /**
     * Obtém um registro de histórico de transações pelo ID.
     *
     * @param id O ID do registro de histórico de transações a ser obtido.
     * @return O registro de histórico de transações correspondente ao ID.
     * @throws HistoricoTransacoesNaoEncontradoException Se o registro de histórico de transações não for encontrado.
     */
    public HistoricoTransacoes obterHistoricoTransacoes(Long id) throws HistoricoTransacoesNaoEncontradoException {
        HistoricoTransacoes historicoTransacoes = historicoTransacoesRepository.findById(id);
        if (historicoTransacoes == null) {
            throw new HistoricoTransacoesNaoEncontradoException("Registro de histórico de transações não encontrado com o ID: " + id);
        }
        return historicoTransacoes;
    }

    /**
     * Lista todos os registros de histórico de transações.
     *
     * @return Uma lista de todos os registros de histórico de transações.
     */
    public List<HistoricoTransacoes> listarHistoricoTransacoes() {
        return historicoTransacoesRepository.listAll();
    }

    /**
     * Remove um registro de histórico de transações pelo ID.
     *
     * @param id O ID do registro de histórico de transações a ser removido.
     * @throws HistoricoTransacoesNaoEncontradoException Se o registro de histórico de transações não for encontrado.
     */
    @Transactional
    public void removerHistoricoTransacoes(Long id) throws HistoricoTransacoesNaoEncontradoException {
        HistoricoTransacoes historicoTransacoes = historicoTransacoesRepository.findById(id);
        if (historicoTransacoes == null) {
            throw new HistoricoTransacoesNaoEncontradoException("Registro de histórico de transações não encontrado com o ID: " + id);
        }
        historicoTransacoesRepository.delete(historicoTransacoes);
    }
}
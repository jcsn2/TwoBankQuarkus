package acc.br.service;

import acc.br.exception.NotificacaoNaoEncontradaException;
import acc.br.model.Notificacoes;
import acc.br.repository.NotificacoesRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe de serviço para a entidade Notificacoes.
 */
@ApplicationScoped
public class NotificacoesService {

    @Inject
    NotificacoesRepository notificacoesRepository;

    /**
     * Cria uma nova notificação.
     *
     * @param notificacao A notificação a ser criada.
     */
    @Transactional
    public void criarNotificacao(Notificacoes notificacao) {
        notificacao.setDataHoraNotificacao(LocalDateTime.now());
        notificacoesRepository.persist(notificacao);
    }

    /**
     * Atualiza uma notificação existente.
     *
     * @param id           O ID da notificação a ser atualizada.
     * @param notificacao A notificação com as atualizações.
     * @throws NotificacaoNaoEncontradaException Se a notificação não for encontrada.
     */
    @Transactional
    public void atualizarNotificacao(Long id, Notificacoes notificacao)
            throws NotificacaoNaoEncontradaException {
        Notificacoes entity = notificacoesRepository.findById(id);
        if (entity == null) {
            throw new NotificacaoNaoEncontradaException("Notificação não encontrada com o ID: " + id);
        }
        
        entity.setMensagemNotificacao(notificacao.getMensagemNotificacao());
        entity.setEnviada(notificacao.getEnviada());

        notificacoesRepository.persist(entity);
    }

    /**
     * Obtém uma notificação pelo ID.
     *
     * @param id O ID da notificação a ser obtida.
     * @return A notificação correspondente ao ID.
     * @throws NotificacaoNaoEncontradaException Se a notificação não for encontrada.
     */
    public Notificacoes obterNotificacao(Long id) throws NotificacaoNaoEncontradaException {
        Notificacoes notificacao = notificacoesRepository.findById(id);
        if (notificacao == null) {
            throw new NotificacaoNaoEncontradaException("Notificação não encontrada com o ID: " + id);
        }
        return notificacao;
    }

    /**
     * Lista todas as notificações.
     *
     * @return Uma lista de todas as notificações.
     */
    public List<Notificacoes> listarNotificacoes() {
        return notificacoesRepository.listAll();
    }

    /**
     * Remove uma notificação pelo ID.
     *
     * @param id O ID da notificação a ser removida.
     * @throws NotificacaoNaoEncontradaException Se a notificação não for encontrada.
     */
    @Transactional
    public void removerNotificacao(Long id) throws NotificacaoNaoEncontradaException {
        Notificacoes notificacao = notificacoesRepository.findById(id);
        if (notificacao == null) {
            throw new NotificacaoNaoEncontradaException("Notificação não encontrada com o ID: " + id);
        }
        notificacoesRepository.delete(notificacao);
    }
    
    /**
     * Obtém todas as notificações de um cliente pelo seu ID.
     *
     * @param clienteID O ID do cliente para o qual as notificações serão obtidas.
     * @return Uma lista de todas as notificações associadas ao cliente.
     */
    public List<Notificacoes> listarNotificacoesPorCliente(Long clienteID) {
        return notificacoesRepository.list("clienteID", clienteID);
    }

}
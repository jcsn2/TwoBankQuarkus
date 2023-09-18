package acc.br.controllers;

import acc.br.exception.NotificacaoNaoEncontradaException;
import acc.br.model.Notificacoes;
import acc.br.service.NotificacoesService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller para a entidade Notificacoes.
 */
@Path("/notificacoes")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificacoesController {

    @Inject
    NotificacoesService notificacoesService;
    
    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final Logger logger = Logger.getLogger(NotificacoesController.class.getName());

    /**
     * Cria uma nova notificação.
     *
     * @param notificacao A notificação a ser criada.
     * @return Um Response com o código 200 (OK) em caso de sucesso e o objeto Notificacoes criado.
     */
    @POST
    public Response criarNotificacao(Notificacoes notificacao) {
        try {
            notificacoesService.criarNotificacao(notificacao);
            logger.info("Notificação criada com sucesso: " + notificacao);
            return Response.status(HTTP_OK).entity(notificacao).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao criar notificação: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar notificação: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Atualiza uma notificação existente.
     *
     * @param id           O ID da notificação a ser atualizada.
     * @param notificacao A notificação com as atualizações.
     * @return Um Response com o código 200 (OK) em caso de sucesso e o objeto Notificacoes atualizado.
     * @throws NotificacaoNaoEncontradaException Se a notificação não for encontrada.
     */
    @PUT
    @Path("/{id}")
    public Response atualizarNotificacao(@PathParam("id") Long id, Notificacoes notificacao) {
        try {
            notificacoesService.atualizarNotificacao(id, notificacao);
            logger.info("Notificação atualizada com sucesso: " + notificacao);
            return Response.status(HTTP_OK).build();
        } catch (NotificacaoNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar notificação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notificação não encontrada com o ID: " + id)
                    .build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar notificação: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar notificação: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Obtém uma notificação pelo ID.
     *
     * @param id O ID da notificação a ser obtida.
     * @return Um Response com o código 200 (OK) em caso de sucesso e a notificação correspondente ao ID.
     * @throws NotificacaoNaoEncontradaException Se a notificação não for encontrada.
     */
    @GET
    @Path("/{id}")
    public Response obterNotificacao(@PathParam("id") Long id) {
        try {
            Notificacoes notificacao = notificacoesService.obterNotificacao(id);
            logger.info("Notificação obtida com sucesso: " + notificacao);
            return Response.ok(notificacao).build();
        } catch (NotificacaoNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao obter notificação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notificação não encontrada com o ID: " + id)
                    .build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao obter notificação: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao obter notificação: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Lista todas as notificações.
     *
     * @return Um Response com o código 200 (OK) em caso de sucesso e uma lista de todas as notificações.
     */
    @GET
    public Response listarNotificacoes() {
        try {
            List<Notificacoes> notificacoes = notificacoesService.listarNotificacoes();
            logger.info("Notificação lsitada com sucesso");
            return Response.ok(notificacoes).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao listar notificações: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao listar notificações: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Remove uma notificação pelo ID.
     *
     * @param id O ID da notificação a ser removida.
     * @return Um Response com o código 204 (NO CONTENT) em caso de sucesso.
     * @throws NotificacaoNaoEncontradaException Se a notificação não for encontrada.
     */
    @DELETE
    @Path("/{id}")
    public Response removerNotificacao(@PathParam("id") Long id) {
        try {
            notificacoesService.removerNotificacao(id);
            logger.info("Notificação removida com sucesso: " + id);
            return Response.status(HTTP_OK).build();
        } catch (NotificacaoNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao remover notificações: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Notificação não encontrada com o ID: " + id)
                    .build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao remover notificações: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao remover notificação: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Obtém todas as notificações de um cliente pelo seu ID.
     *
     * @param clienteID O ID do cliente para o qual as notificações serão obtidas.
     * @return Um Response com o código 200 (OK) em caso de sucesso e uma lista de todas as notificações associadas ao cliente.
     */
    @GET
    @Path("/cliente/{clienteID}")
    public Response listarNotificacoesPorCliente(@PathParam("clienteID") Long clienteID) {
        try {
            List<Notificacoes> notificacoes = notificacoesService.listarNotificacoesPorCliente(clienteID);
            logger.info("Notificação listada com sucesso: " + clienteID);
            return Response.ok(notificacoes).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao listar notificações: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao listar notificações para o cliente com ID: " + clienteID + ". " + e.getMessage())
                    .build();
        }
    }
}

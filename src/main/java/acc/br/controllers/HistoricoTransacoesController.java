package acc.br.controllers;

import acc.br.exception.HistoricoTransacoesNaoEncontradoException;
import acc.br.model.HistoricoTransacoes;
import acc.br.service.HistoricoTransacoesService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para a entidade HistoricoTransacoes.
 */
@Path("/historico-transacoes")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistoricoTransacoesController {

    @Inject
    HistoricoTransacoesService historicoTransacoesService;

    /**
     * Cria um novo registro de histórico de transações.
     *
     * @param historicoTransacoes O objeto HistoricoTransacoes a ser criado.
     * @return Uma resposta HTTP indicando o sucesso ou erro da operação.
     */
    @POST
    public Response criarHistoricoTransacoes(@Valid HistoricoTransacoes historicoTransacoes) {
        try {
            historicoTransacoesService.criarHistoricoTransacoes(historicoTransacoes);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao criar histórico de transações.").build();
        }
    }

    /**
     * Atualiza um registro de histórico de transações existente.
     *
     * @param id                 O ID do registro de histórico de transações a ser atualizado.
     * @param historicoTransacoes O objeto HistoricoTransacoes com as atualizações.
     * @return Uma resposta HTTP indicando o sucesso ou erro da operação.
     */
    @PUT
    @Path("/{id}")
    public Response atualizarHistoricoTransacoes(@PathParam("id") Long id, @Valid HistoricoTransacoes historicoTransacoes) {
        try {
            historicoTransacoesService.atualizarHistoricoTransacoes(id, historicoTransacoes);
            return Response.status(Response.Status.OK).build();
        } catch (HistoricoTransacoesNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Registro de histórico de transações não encontrado.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao atualizar histórico de transações.").build();
        }
    }

    /**
     * Obtém um registro de histórico de transações pelo ID.
     *
     * @param id O ID do registro de histórico de transações a ser obtido.
     * @return Uma resposta HTTP contendo o registro de histórico de transações ou uma mensagem de erro.
     */
    @GET
    @Path("/{id}")
    public Response obterHistoricoTransacoes(@PathParam("id") Long id) {
        try {
            HistoricoTransacoes historicoTransacoes = historicoTransacoesService.obterHistoricoTransacoes(id);
            return Response.status(Response.Status.OK).entity(historicoTransacoes).build();
        } catch (HistoricoTransacoesNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Registro de histórico de transações não encontrado.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao obter histórico de transações.").build();
        }
    }

    /**
     * Lista todos os registros de histórico de transações.
     *
     * @return Uma resposta HTTP contendo uma lista de registros de histórico de transações ou uma mensagem de erro.
     */
    @GET
    public Response listarHistoricoTransacoes() {
        try {
            List<HistoricoTransacoes> historicoTransacoesList = historicoTransacoesService.listarHistoricoTransacoes();
            return Response.status(Response.Status.OK).entity(historicoTransacoesList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao listar histórico de transações.").build();
        }
    }

    /**
     * Remove um registro de histórico de transações pelo ID.
     *
     * @param id O ID do registro de histórico de transações a ser removido.
     * @return Uma resposta HTTP indicando o sucesso ou erro da operação.
     */
    @DELETE
    @Path("/{id}")
    public Response removerHistoricoTransacoes(@PathParam("id") Long id) {
        try {
            historicoTransacoesService.removerHistoricoTransacoes(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (HistoricoTransacoesNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Registro de histórico de transações não encontrado.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover histórico de transações.").build();
        }
    }
}
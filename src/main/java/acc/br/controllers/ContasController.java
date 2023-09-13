package acc.br.controllers;

import acc.br.exception.ContaExistenteException;
import acc.br.exception.ContaNaoEncontradaException;
import acc.br.model.Contas;
import acc.br.service.ContasService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para manipulação de contas bancárias.
 */
@Path("/contas")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContasController {

    @Inject
    ContasService contasService;

    /**
     * Lista todas as contas cadastradas no sistema.
     *
     * @return Uma lista de contas bancárias.
     */
    @GET
    public List<Contas> listarContas() {
        return contasService.listarContas();
    }

    /**
     * Obtém uma conta bancária pelo seu ID.
     *
     * @param contaID O ID da conta bancária a ser obtida.
     * @return Resposta HTTP com código 200 (OK) e a conta bancária encontrada se existir.
     *         Resposta HTTP com código 404 (Not Found) se a conta bancária não for encontrada.
     */
    @GET
    @Path("/{contaID}")
    public Response obterConta(@PathParam("contaID") Long contaID) {
        try {
            Contas conta = contasService.obterConta(contaID);
            return Response.status(Response.Status.OK).entity(conta).build();
        } catch (ContaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Cria uma nova conta bancária no sistema.
     *
     * @param conta A conta bancária a ser criada.
     * @return Resposta HTTP com código 201 (Created) e a nova conta bancária se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se uma conta bancária com o mesmo número já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta bancária não forem válidos.
     */
    @POST
    public Response criarConta(@Valid Contas conta) {
        try {
            Contas novaConta = contasService.criarConta(conta);
            return Response.status(Response.Status.CREATED).entity(novaConta).build();
        } catch (ContaExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Atualiza uma conta bancária existente no sistema.
     *
     * @param contaID O ID da conta bancária a ser atualizada.
     * @param conta Os dados atualizados da conta bancária.
     * @return Resposta HTTP com código 200 (OK) e a conta bancária atualizada se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se a conta bancária não for encontrada.
     *         Resposta HTTP com código 409 (Conflict) se uma conta bancária com o mesmo número já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta bancária não forem válidos.
     */
    @PUT
    @Path("/{contaID}")
    public Response atualizarConta(@PathParam("contaID") Long contaID, @Valid Contas conta) {
        try {
            Contas contaAtualizada = contasService.atualizarConta(contaID, conta);
            return Response.status(Response.Status.OK).entity(contaAtualizada).build();
        } catch (ContaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ContaExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Remove uma conta bancária pelo seu ID.
     *
     * @param contaID O ID da conta bancária a ser removida.
     * @return Resposta HTTP com código 204 (No Content) se a conta bancária for removida com sucesso.
     *         Resposta HTTP com código 404 (Not Found) se a conta bancária não for encontrada.
     */
    @DELETE
    @Path("/{contaID}")
    public Response removerConta(@PathParam("contaID") Long contaID) {
        try {
            contasService.removerConta(contaID);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ContaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
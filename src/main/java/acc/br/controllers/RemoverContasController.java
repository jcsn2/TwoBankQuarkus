package acc.br.controllers;

import acc.br.exception.ContaCorrenteNaoEncontradaException;
import acc.br.exception.PoupancaNaoEncontradaException;
import acc.br.exception.ContaConjuntaNaoEncontradaException;
import acc.br.service.ContaCorrenteService;
import acc.br.service.PoupancaService;
import acc.br.util.PoupancaServiceQualifier;
import acc.br.service.ContasConjuntasService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Controlador REST para remoção de contas bancárias.
 */
@Path("/contas")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RemoverContasController {

    @Inject
    ContaCorrenteService contaCorrenteService;

    @Inject
    @PoupancaServiceQualifier
    PoupancaService poupancaService;

    @Inject
    ContasConjuntasService contasConjuntasService;

    /**
     * Remove uma conta corrente existente.
     *
     * @param id O ID da conta corrente a ser removida.
     * @return Resposta HTTP com código 204 (No Content) se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se a conta corrente não for encontrada.
     */
    @DELETE
    @Path("/corrente/{id}")
    public Response removerContaCorrente(@PathParam("id") Long id) {
        try {
            contaCorrenteService.removerContaCorrente(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ContaCorrenteNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Remove uma conta poupança existente.
     *
     * @param id O ID da conta poupança a ser removida.
     * @return Resposta HTTP com código 204 (No Content) se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se a conta poupança não for encontrada.
     */
    @DELETE
    @Path("/poupanca/{id}")
    public Response removerContaPoupanca(@PathParam("id") Long id) {
        try {
            poupancaService.removerPoupanca(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (PoupancaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Remove uma conta conjunta existente.
     *
     * @param id O ID da conta conjunta a ser removida.
     * @return Resposta HTTP com código 204 (No Content) se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se a conta conjunta não for encontrada.
     */
    @DELETE
    @Path("/conjunta/{id}")
    public Response removerContaConjunta(@PathParam("id") Long id) {
        try {
            contasConjuntasService.removerContasConjunta(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ContaConjuntaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
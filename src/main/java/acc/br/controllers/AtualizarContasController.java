package acc.br.controllers;

import acc.br.exception.ContaCorrenteNaoEncontradaException;
import acc.br.exception.PoupancaNaoEncontradaException;
import acc.br.exception.ContaConjuntaNaoEncontradaException;
import acc.br.model.ContaCorrente;
import acc.br.model.Poupanca;
import acc.br.model.Contas;
import acc.br.model.ContasConjuntas;
import acc.br.service.ContaCorrenteService;
import acc.br.service.PoupancaService;
import acc.br.util.PoupancaServiceQualifier;
import acc.br.service.ContasConjuntasService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Controlador REST para atualização de contas bancárias.
 */
@Path("/contas")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AtualizarContasController {

    @Inject
    ContaCorrenteService contaCorrenteService;

    @Inject
    @PoupancaServiceQualifier
    PoupancaService poupancaService;

    @Inject
    ContasConjuntasService contasConjuntasService;

    /**
     * Atualiza uma conta corrente existente.
     *
     * @param id O ID da conta corrente a ser atualizada.
     * @param contaCorrente A conta corrente com as atualizações.
     * @return Resposta HTTP com código 200 (OK) se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se a conta corrente não for encontrada.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta corrente não forem válidos.
     */
    @PUT
    @Path("/corrente/{id}")
    public Response atualizarContaCorrente(@PathParam("id") Long id, @Valid ContaCorrente contaCorrente) {
        try {
            Contas contaAtualizada = contaCorrenteService.atualizarConta(id, contaCorrente);
            return Response.status(Response.Status.OK).entity(contaAtualizada).build();
        } catch (ContaCorrenteNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Atualiza uma conta poupança existente.
     *
     * @param id O ID da conta poupança a ser atualizada.
     * @param contaPoupanca A conta poupança com as atualizações.
     * @return Resposta HTTP com código 200 (OK) se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se a conta poupança não for encontrada.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta poupança não forem válidos.
     */
    @PUT
    @Path("/poupanca/{id}")
    public Response atualizarContaPoupanca(@PathParam("id") Long id, @Valid Poupanca contaPoupanca) {
        try {
            Contas contaAtualizada = poupancaService.atualizarConta(id, contaPoupanca);
            return Response.status(Response.Status.OK).entity(contaAtualizada).build();
        } catch (PoupancaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Atualiza uma conta conjunta existente.
     *
     * @param id O ID da conta conjunta a ser atualizada.
     * @param contasConjuntas A conta conjunta com as atualizações.
     * @return Resposta HTTP com código 200 (OK) se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se a conta conjunta não for encontrada.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta conjunta não forem válidos.
     */
    @PUT
    @Path("/conjunta/{id}")
    public Response atualizarContaConjunta(@PathParam("id") Long id, @Valid ContasConjuntas contasConjuntas) {
        try {
            Contas contaAtualizada = contasConjuntasService.atualizarConta(id, contasConjuntas);
            return Response.status(Response.Status.OK).entity(contaAtualizada).build();
        } catch (ContaConjuntaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
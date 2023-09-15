package acc.br.controllers;

import acc.br.exception.ContaExistenteException;
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
 * Controlador REST para criação de contas bancárias.
 */
@Path("/contas")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CriarContasController {

    @Inject
    ContaCorrenteService contaCorrenteService;

    @Inject
    @PoupancaServiceQualifier
    PoupancaService poupancaService;

    @Inject
    ContasConjuntasService contasConjuntasService;

    /**
     * Cria uma nova conta corrente.
     *
     * @param contaCorrente A conta corrente a ser criada.
     * @return Resposta HTTP com código 201 (Created) e a nova conta corrente se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se uma conta com o mesmo ID já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta corrente não forem válidos.
     */
    @POST
    @Path("/corrente")
    public Response criarContaCorrente(@Valid ContaCorrente contaCorrente) {
        try {
            Contas novaConta = contaCorrenteService.criarConta(contaCorrente);
            return Response.status(Response.Status.CREATED).entity(novaConta).build();
        } catch (ContaExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Cria uma nova conta poupança.
     *
     * @param contaPoupanca A conta poupança a ser criada.
     * @return Resposta HTTP com código 201 (Created) e a nova conta poupança se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se uma conta com o mesmo ID já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta poupança não forem válidos.
     */
    @POST
    @Path("/poupanca")
    public Response criarContaPoupanca(@Valid Poupanca contaPoupanca) {
        try {
        	Poupanca novaConta = poupancaService.criarConta(contaPoupanca);
            return Response.status(Response.Status.CREATED).entity(novaConta).build();
        } catch (ContaExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Cria uma nova conta conjunta.
     *
     * @param contasConjuntas A conta conjunta a ser criada.
     * @return Resposta HTTP com código 201 (Created) e a nova conta conjunta se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se uma conta com o mesmo ID já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta conjunta não forem válidos.
     */
    @POST
    @Path("/conjunta")
    public Response criarContaConjunta(@Valid ContasConjuntas contasConjuntas) {
        try {
        	ContasConjuntas novaConta = contasConjuntasService.criarConta(contasConjuntas);
            return Response.status(Response.Status.CREATED).entity(novaConta).build();
        } catch (ContaExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
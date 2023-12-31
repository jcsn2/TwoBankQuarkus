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

import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final Logger logger = Logger.getLogger(CriarContasController.class.getName());

    /**
     * Cria uma nova conta corrente.
     *
     * @param contaCorrente A conta corrente a ser criada.
     * @return Resposta HTTP com código 200 (Ok) e a nova conta corrente se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se uma conta com o mesmo ID já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta corrente não forem válidos.
     */
    @POST
    @Path("/corrente")
    public Response criarContaCorrente(@Valid ContaCorrente contaCorrente) {
        try {
            Contas novaConta = contaCorrenteService.criarConta(contaCorrente);
            logger.info("Conta corrente criada com sucesso: " + contaCorrente);
            return Response.status(HTTP_OK).entity(novaConta).build();
        } catch (ContaExistenteException e) {
        	logger.log(Level.SEVERE, "Erro ao criar conta corrente: " + e.getMessage(), e);
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
        	logger.log(Level.SEVERE, "Erro ao criar conta corrente: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Cria uma nova conta poupança.
     *
     * @param contaPoupanca A conta poupança a ser criada.
     * @return Resposta HTTP com código 200 (Ok) e a nova conta poupança se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se uma conta com o mesmo ID já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta poupança não forem válidos.
     */
    @POST
    @Path("/poupanca")
    public Response criarContaPoupanca(@Valid Poupanca contaPoupanca) {
        try {
        	Poupanca novaConta = poupancaService.criarConta(contaPoupanca);
        	logger.info("Conta poupança criada com sucesso: " + contaPoupanca);
            return Response.status(HTTP_OK).entity(novaConta).build();
        } catch (ContaExistenteException e) {
        	logger.log(Level.SEVERE, "Erro ao criar conta poupança: " + e.getMessage(), e);
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
        	logger.log(Level.SEVERE, "Erro ao criar conta poupança: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Cria uma nova conta conjunta.
     *
     * @param contasConjuntas A conta conjunta a ser criada.
     * @return Resposta HTTP com código 200 (Ok) e a nova conta conjunta se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se uma conta com o mesmo ID já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta conjunta não forem válidos.
     */
    @POST
    @Path("/conjunta")
    public Response criarContaConjunta(@Valid ContasConjuntas contasConjuntas) {
        try {
        	ContasConjuntas novaConta = contasConjuntasService.criarConta(contasConjuntas);
        	logger.info("Conta conjunta criada com sucesso: " + contasConjuntas);
            return Response.status(HTTP_OK).entity(novaConta).build();
        } catch (ContaExistenteException e) {
        	logger.log(Level.SEVERE, "Erro ao criar conta conjunta: " + e.getMessage(), e);
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
        	logger.log(Level.SEVERE, "Erro ao criar conta conjunta: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
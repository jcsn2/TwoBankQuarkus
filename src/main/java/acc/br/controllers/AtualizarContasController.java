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

import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final Logger logger = Logger.getLogger(AtualizarContasController.class.getName());


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
            logger.info("Conta Corrente atualizada com sucesso: " + contaCorrente.getcontaCorrenteID()); 
            return Response.status(HTTP_OK).entity(contaAtualizada).build();
        } catch (ContaCorrenteNaoEncontradaException e) {
        	 logger.log(Level.SEVERE, "Erro ao atualizar conta corrente: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
        	 logger.log(Level.SEVERE, "Erro ao atualizar conta corrente: " + e.getMessage(), e);
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
            logger.info("Conta Poupanca atualizada com sucesso: " + contaPoupanca.getPoupancaID());
            return Response.status(HTTP_OK).entity(contaAtualizada).build();
        } catch (PoupancaNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar conta poupanca: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar conta poupanca: " + e.getMessage(), e);
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
            logger.info("Conta Conjunta atualizada com sucesso: " + contasConjuntas.getContaConjuntaID());
            return Response.status(HTTP_OK).entity(contaAtualizada).build();
        } catch (ContaConjuntaNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar conta conjunta: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar conta conjunta: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
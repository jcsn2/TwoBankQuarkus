package acc.br.controllers;

import acc.br.exception.ContaNaoEncontradaException;
import acc.br.model.ContaCorrente;
import acc.br.model.Poupanca;
import acc.br.repository.ContaCorrenteRepository;
import acc.br.repository.ContasConjuntasRepository;
import acc.br.repository.PoupancaRepository;
import acc.br.model.Contas;
import acc.br.model.ContasConjuntas;
import acc.br.service.ContaCorrenteService;
import acc.br.service.PoupancaService;
import acc.br.util.PoupancaServiceQualifier;
import acc.br.service.ContasConjuntasService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
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
    ContaCorrenteService contaCorrenteService;

    @Inject
    @PoupancaServiceQualifier
    PoupancaService poupancaService;

    @Inject
    ContasConjuntasService contasConjuntasService;
    
    @Inject
    ContasConjuntasRepository contasConjuntasRepository;
    
    @Inject
    ContaCorrenteRepository contaCorrenteRepository;
    
    @Inject
    PoupancaRepository poupancaRepository;

    /**
     * Lista todas as contas correntes cadastradas no sistema.
     *
     * @return Uma lista de contas correntes.
     */
    @GET
    @Path("/correntes")
    public List<ContaCorrente> listarContasCorrentes() {
        return contaCorrenteService.listarContasCorrentes();
    }

    /**
     * Lista todas as contas poupança cadastradas no sistema.
     *
     * @return Uma lista de contas poupança.
     */
    @GET
    @Path("/poupanca")
    public List<Poupanca> listarContasPoupanca() {
        return poupancaService.listarPoupancas();
    }

    /**
     * Lista todas as contas conjuntas cadastradas no sistema.
     *
     * @return Uma lista de contas conjuntas.
     */
    @GET
    @Path("/conjuntas")
    public List<ContasConjuntas> listarContasConjuntas() {
        return contasConjuntasService.listarContasConjuntas();
    }

    /**
     * Obtém uma conta corrente pelo seu ID.
     *
     * @param contaID O ID da conta corrente a ser obtida.
     * @return Resposta HTTP com código 200 (OK) e a conta corrente encontrada se existir.
     *         Resposta HTTP com código 404 (Not Found) se a conta corrente não for encontrada.
     */
    @GET
    @Path("/correntes/{contaID}")
    public Response obterContaCorrente(@PathParam("contaID") Long contaID) {
        try {
            Contas contaCorrente = contaCorrenteRepository.findById(contaID);
            return Response.status(Response.Status.OK).entity(contaCorrente).build();
        } catch (ContaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Obtém uma conta poupança pelo seu ID.
     *
     * @param contaID O ID da conta poupança a ser obtida.
     * @return Resposta HTTP com código 200 (OK) e a conta poupança encontrada se existir.
     *         Resposta HTTP com código 404 (Not Found) se a conta poupança não for encontrada.
     */
    @GET
    @Path("/poupanca/{contaID}")
    public Response obterContaPoupanca(@PathParam("contaID") Long contaID) {
        try {
        	Poupanca contaPoupanca = poupancaRepository.findById(contaID);
            return Response.status(Response.Status.OK).entity(contaPoupanca).build();
        } catch (ContaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Obtém uma conta conjunta pelo seu ID.
     *
     * @param contaID O ID da conta conjunta a ser obtida.
     * @return Resposta HTTP com código 200 (OK) e a conta conjunta encontrada se existir.
     *         Resposta HTTP com código 404 (Not Found) se a conta conjunta não for encontrada.
     */
    @GET
    @Path("/conjuntas/{contaID}")
    public Response obterContaConjunta(@PathParam("contaID") Long contaID) {
        try {
            ContasConjuntas contaConjunta = contasConjuntasRepository.findById(contaID);
            return Response.status(Response.Status.OK).entity(contaConjunta).build();
        } catch (ContaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}

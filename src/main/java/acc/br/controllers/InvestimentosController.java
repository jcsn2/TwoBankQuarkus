package acc.br.controllers;

import acc.br.exception.InvestimentoNaoEncontradoException;
import acc.br.model.Investimentos;
import acc.br.service.InvestimentosService;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para a entidade Investimentos.
 */
@Path("/investimentos")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvestimentosController {

    @Inject
    InvestimentosService investimentosService;
    
    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final Logger logger = Logger.getLogger(InvestimentosController.class.getName());


    /**
     * Cria um novo registro de investimento.
     *
     * @param investimento O objeto Investimentos a ser criado.
     * @return Uma resposta HTTP com o status 200 OK e o investimento criado.
     */
    @POST
    public Response criarInvestimento(Investimentos investimento) {
        try {
            investimentosService.criarInvestimento(investimento);
            logger.info("Investimento criado com sucesso: " + investimento);
            return Response.status(HTTP_OK).entity(investimento).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao criar investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Atualiza um registro de investimento existente.
     *
     * @param id           O ID do registro de investimento a ser atualizado.
     * @param investimento O objeto Investimentos com as atualizações.
     * @return Uma resposta HTTP com o status 200 OK e o investimento atualizado.
     * @throws InvestimentoNaoEncontradoException Se o registro de investimento não for encontrado.
     */
    @PUT
    @Path("/{id}")
    public Response atualizarInvestimento(@PathParam("id") Long id, Investimentos investimento) {
        try {
            investimentosService.atualizarInvestimento(id, investimento);
            logger.info("Investimento atualizado com sucesso: " + investimento);
            return Response.status(HTTP_OK).entity(investimento).build();
        } catch (InvestimentoNaoEncontradoException e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Obtém um registro de investimento pelo ID.
     *
     * @param id O ID do registro de investimento a ser obtido.
     * @return Uma resposta HTTP com o status 200 OK e o investimento correspondente ao ID.
     * @throws InvestimentoNaoEncontradoException Se o registro de investimento não for encontrado.
     */
    @GET
    @Path("/{id}")
    public Response obterInvestimento(@PathParam("id") Long id) {
        try {
            Investimentos investimento = investimentosService.obterInvestimento(id);
            logger.info("Investimento obtido com sucesso: " + id);
            return Response.status(HTTP_OK).entity(investimento).build();
        } catch (InvestimentoNaoEncontradoException e) {
        	logger.log(Level.SEVERE, "Erro ao obter investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao obter investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Lista todos os registros de investimento.
     *
     * @return Uma resposta HTTP com o status 200 OK e uma lista de todos os registros de investimento.
     */
    @GET
    public Response listarInvestimentos() {
        try {
            List<Investimentos> investimentos = investimentosService.listarInvestimentos();
            logger.info("Investimento listado com sucesso");
            return Response.status(HTTP_OK).entity(investimentos).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao listas investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Remove um registro de investimento pelo ID.
     *
     * @param id O ID do registro de investimento a ser removido.
     * @return Uma resposta HTTP com o status 200 OK.
     * @throws InvestimentoNaoEncontradoException Se o registro de investimento não for encontrado.
     */
    @DELETE
    @Path("/{id}")
    public Response removerInvestimento(@PathParam("id") Long id) {
        try {
            investimentosService.removerInvestimento(id);
            logger.info("Investimento removido com sucesso: " + id);
            return Response.status(HTTP_OK).build();
        } catch (InvestimentoNaoEncontradoException e) {
        	logger.log(Level.SEVERE, "Erro ao remover investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao remover investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    /**
     * Calcula o valor de retorno do investimento com base na taxa de retorno.
     *
     * @param id O ID do registro de investimento para o qual o cálculo será realizado.
     * @return Uma resposta HTTP com o valor de retorno do investimento.
     * @throws InvestimentoNaoEncontradoException Se o registro de investimento não for encontrado.
     */
    @GET
    @Path("/{id}/calcular-retorno")
    public Response calcularRetornoInvestimento(@PathParam("id") Long id) throws InvestimentoNaoEncontradoException {
        try {
            BigDecimal retorno = investimentosService.calcularRetornoInvestimento(PanacheEntityBase.findById(id));
            logger.info("Investimento calculado com sucesso: " + id);
            return Response.status(HTTP_OK).entity(retorno).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao calcular investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    /**
     * Lista todos os registros de investimento de um cliente.
     *
     * @param clienteID O ID do cliente para o qual os investimentos serão listados.
     * @return Uma resposta HTTP com o status 200 OK e uma lista de todos os registros de investimento do cliente.
     */
    @GET
    @Path("/cliente/{clienteID}")
    public Response listarInvestimentosPorCliente(@PathParam("clienteID") Integer clienteID) {
        try {
            List<Investimentos> investimentos = investimentosService.listarInvestimentosPorCliente(clienteID);
            logger.info("Investimento listado com sucesso: " + clienteID);
            return Response.status(HTTP_OK).entity(investimentos).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao listar investimento: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}

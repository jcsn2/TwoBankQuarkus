package acc.br.controllers;

import acc.br.exception.AlertaNaoEncontradoException;
import acc.br.exception.ClienteNaoEncontradoException;
import acc.br.model.AlertasGastosExcessivos;
import acc.br.service.AlertasGastosExcessivosService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Controlador REST para manipulação de alertas de gastos excessivos.
 */
@Path("/alertas")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlertasGastosExcessivosController {

    @Inject
    AlertasGastosExcessivosService alertasService;

    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final Logger logger = Logger.getLogger(AlertasGastosExcessivosController.class.getName());

    /**
     * Cria um novo alerta de gastos excessivos.
     *
     * @param alerta O alerta a ser criado.
     * @return Resposta HTTP com código 201 (Created) se o alerta for criado com sucesso.
     *         Resposta HTTP com código 400 (Bad Request) se o cliente associado ao alerta não for encontrado.
     */
    @POST
    public Response criarAlerta(@Valid @NotNull AlertasGastosExcessivos alerta) {
        try {
            alertasService.criarAlerta(alerta);
            logger.info("Alerta criado com sucesso: " + alerta.getAlertaID()); 
            return Response.status(HTTP_OK).build();
        } catch (ClienteNaoEncontradoException e) {
            logger.log(Level.SEVERE, "Erro ao criar alerta: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Atualiza um alerta de gastos excessivos existente.
     *
     * @param alerta O alerta a ser atualizado.
     * @return Resposta HTTP com código 200 (OK) se o alerta for atualizado com sucesso.
     *         Resposta HTTP com código 400 (Bad Request) se o cliente associado ao alerta não for encontrado.
     *         Resposta HTTP com código 404 (Not Found) se o alerta não for encontrado.
     */
    @PUT
    public Response atualizarAlerta(@Valid @NotNull AlertasGastosExcessivos alerta) {
        try {
            alertasService.atualizarAlerta(alerta);
            logger.info("Alerta atualizado com sucesso: " + alerta.getAlertaID());
            return Response.status(HTTP_OK).build();
        } catch (AlertaNaoEncontradoException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar alerta: " + e.getMessage(), e); 
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ClienteNaoEncontradoException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar alerta: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Deleta um alerta de gastos excessivos.
     *
     * @param alertaId O ID do alerta a ser deletado.
     * @return Resposta HTTP com código 204 (No Content) se o alerta for deletado com sucesso.
     *         Resposta HTTP com código 404 (Not Found) se o alerta não for encontrado.
     */
    @DELETE
    @Path("/{alertaId}")
    public Response deletarAlerta(@PathParam("alertaId") Long alertaId) {
        try {
            alertasService.deletarAlerta(alertaId);
            logger.info("Alerta deletado com sucesso: " + alertaId);
            return Response.status(HTTP_OK).build();
        } catch (AlertaNaoEncontradoException e) {
            logger.log(Level.SEVERE, "Erro ao deletar alerta: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Lista todos os alertas de gastos excessivos de um cliente.
     *
     * @param clienteId O ID do cliente para o qual listar os alertas.
     * @return Uma lista de alertas de gastos excessivos do cliente.
     */
    @GET
    @Path("/cliente/{clienteId}")
    public List<AlertasGastosExcessivos> listarAlertasPorCliente(@PathParam("clienteId") Long clienteId) {
    	logger.info("Alertas listados com sucesso: " + clienteId);
    	return alertasService.listarAlertasPorCliente(clienteId);
    }

    /**
     * Busca um alerta de gastos excessivos por ID.
     *
     * @param alertaId O ID do alerta a ser buscado.
     * @return Resposta HTTP com código 200 (OK) e o alerta se encontrado.
     *         Resposta HTTP com código 404 (Not Found) se o alerta não for encontrado.
     */
    @GET
    @Path("/{alertaId}")
    public Response buscarAlertaPorId(@PathParam("alertaId") Long alertaId) {
        AlertasGastosExcessivos alerta = alertasService.buscarAlertaPorId(alertaId);
        if (alerta != null) {
        	logger.info("Alertas buscado com sucesso: " + alertaId);
            return Response.status(HTTP_OK).entity(alerta).build();
        } else {
            logger.log(Level.SEVERE, "Erro ao buscar alerta" );
            return Response.status(Response.Status.NOT_FOUND).entity("Alerta não encontrado").build();
        }
    }
}

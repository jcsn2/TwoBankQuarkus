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
            return Response.status(Response.Status.CREATED).build();
        } catch (ClienteNaoEncontradoException e) {
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
            return Response.status(Response.Status.OK).build();
        } catch (AlertaNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ClienteNaoEncontradoException e) {
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
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (AlertaNaoEncontradoException e) {
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
            return Response.status(Response.Status.OK).entity(alerta).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Alerta não encontrado").build();
        }
    }
}

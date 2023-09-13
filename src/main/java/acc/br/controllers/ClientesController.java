package acc.br.controllers;

import acc.br.exception.ClienteExistenteException;
import acc.br.exception.ClienteNaoEncontradoException;
import acc.br.model.Clientes;
import acc.br.service.ClientesService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para manipulação de clientes.
 */
@Path("/clientes")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientesController {

    @Inject
    ClientesService clientesService;

    /**
     * Lista todos os clientes cadastrados no sistema.
     *
     * @return Uma lista de clientes.
     */
    @GET
    public List<Clientes> listarClientes() {
        return clientesService.listarClientes();
    }

    /**
     * Obtém um cliente pelo seu ID.
     *
     * @param clienteID O ID do cliente a ser obtido.
     * @return Resposta HTTP com código 200 (OK) e o cliente encontrado se existir.
     *         Resposta HTTP com código 404 (Not Found) se o cliente não for encontrado.
     */
    @GET
    @Path("/{clienteID}")
    public Response obterCliente(@PathParam("clienteID") Long clienteID) {
        try {
            Clientes cliente = clientesService.obterCliente(clienteID);
            return Response.status(Response.Status.OK).entity(cliente).build();
        } catch (ClienteNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Cria um novo cliente no sistema.
     *
     * @param cliente O cliente a ser criado.
     * @return Resposta HTTP com código 201 (Created) e o novo cliente se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se um cliente com o mesmo CPF já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados do cliente não forem válidos.
     */
    @POST
    public Response criarCliente(@Valid Clientes cliente) {
        try {
            Clientes novoCliente = clientesService.criarCliente(cliente);
            return Response.status(Response.Status.CREATED).entity(novoCliente).build();
        } catch (ClienteExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Atualiza um cliente existente no sistema.
     *
     * @param clienteID O ID do cliente a ser atualizado.
     * @param cliente Os dados atualizados do cliente.
     * @return Resposta HTTP com código 200 (OK) e o cliente atualizado se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se o cliente não for encontrado.
     *         Resposta HTTP com código 409 (Conflict) se um cliente com o mesmo CPF já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados do cliente não forem válidos.
     */
    @PUT
    @Path("/{clienteID}")
    public Response atualizarCliente(@PathParam("clienteID") Long clienteID, @Valid Clientes cliente) {
        try {
            Clientes clienteAtualizado = clientesService.atualizarCliente(clienteID, cliente);
            return Response.status(Response.Status.OK).entity(clienteAtualizado).build();
        } catch (ClienteNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ClienteExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Remove um cliente pelo seu ID.
     *
     * @param clienteID O ID do cliente a ser removido.
     * @return Resposta HTTP com código 204 (No Content) se o cliente for removido com sucesso.
     *         Resposta HTTP com código 404 (Not Found) se o cliente não for encontrado.
     */
    @DELETE
    @Path("/{clienteID}")
    public Response removerCliente(@PathParam("clienteID") Long clienteID) {
        try {
            clientesService.removerCliente(clienteID);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ClienteNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
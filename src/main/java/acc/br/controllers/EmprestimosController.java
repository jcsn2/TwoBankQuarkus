package acc.br.controllers;

import acc.br.exception.EmprestimoExistenteException;
import acc.br.exception.EmprestimoNaoEncontradoException;
import acc.br.model.Emprestimos;
import acc.br.service.EmprestimosService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para manipulação de empréstimos.
 */
@Path("/emprestimos")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmprestimosController {

    @Inject
    EmprestimosService emprestimosService;

    /**
     * Cria um novo empréstimo no sistema.
     *
     * @param emprestimo O empréstimo a ser criado.
     * @return Resposta HTTP com código 201 (Created) e o novo empréstimo se for bem-sucedido.
     *         Resposta HTTP com código 409 (Conflict) se um empréstimo com o mesmo ID já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados do empréstimo não forem válidos.
     */
    @POST
    public Response criarEmprestimo(@Valid Emprestimos emprestimo) {
        try {
            Emprestimos novoEmprestimo = emprestimosService.criarEmprestimo(emprestimo);
            return Response.status(Response.Status.CREATED).entity(novoEmprestimo).build();
        } catch (EmprestimoExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Atualiza um empréstimo existente no sistema.
     *
     * @param id O ID do empréstimo a ser atualizado.
     * @param emprestimo Os dados atualizados do empréstimo.
     * @return Resposta HTTP com código 200 (OK) e o empréstimo atualizado se for bem-sucedido.
     *         Resposta HTTP com código 404 (Not Found) se o empréstimo não for encontrado.
     *         Resposta HTTP com código 400 (Bad Request) se os dados do empréstimo não forem válidos.
     */
    @PUT
    @Path("/{id}")
    public Response atualizarEmprestimo(@PathParam("id") Long id, @Valid Emprestimos emprestimo) {
        try {
            Emprestimos emprestimoAtualizado = emprestimosService.atualizarEmprestimo(id, emprestimo);
            return Response.status(Response.Status.OK).entity(emprestimoAtualizado).build();
        } catch (EmprestimoNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Deleta um empréstimo pelo seu ID.
     *
     * @param id O ID do empréstimo a ser deletado.
     * @return Resposta HTTP com código 204 (No Content) se o empréstimo for deletado com sucesso.
     *         Resposta HTTP com código 404 (Not Found) se o empréstimo não for encontrado.
     */
    @DELETE
    @Path("/{id}")
    public Response deletarEmprestimo(@PathParam("id") Long id) {
        try {
            emprestimosService.deletarEmprestimo(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EmprestimoNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Lista todos os empréstimos cadastrados no sistema.
     *
     * @return Uma lista de empréstimos.
     */
    @GET
    public List<Emprestimos> listarEmprestimos() {
        return emprestimosService.listarEmprestimos();
    }

    /**
     * Obtém um empréstimo pelo seu ID.
     *
     * @param id O ID do empréstimo a ser obtido.
     * @return Resposta HTTP com código 200 (OK) e o empréstimo encontrado se existir.
     *         Resposta HTTP com código 404 (Not Found) se o empréstimo não for encontrado.
     */
    @GET
    @Path("/{id}")
    public Response buscarEmprestimoPorId(@PathParam("id") Long id) {
        Emprestimos emprestimo = emprestimosService.buscarEmprestimoPorId(id);
        if (emprestimo != null) {
            return Response.status(Response.Status.OK).entity(emprestimo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
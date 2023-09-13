package acc.br.controllers;

import acc.br.exception.ContaConjuntaExistenteException;
import acc.br.exception.ContaConjuntaNaoEncontradaException;
import acc.br.model.ContasConjuntas;
import acc.br.service.ContasConjuntasService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Controlador REST para manipulação de contas conjuntas.
 */
@Path("/contasconjuntas")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContasConjuntasController {

    @Inject
    ContasConjuntasService contasConjuntasService;

    /**
     * Lista todas as contas conjuntas cadastradas no sistema.
     *
     * @return Uma lista de contas conjuntas.
     */
    @GET
    public List<ContasConjuntas> listarContasConjuntas() {
        return contasConjuntasService.listarContasConjuntas();
    }

    /**
     * Obtém uma conta conjunta pelo seu ID.
     *
     * @param contaConjuntaID O ID da conta conjunta a ser obtida.
     * @return Resposta HTTP com código 200 (OK) e a conta conjunta encontrada se existir.
     *         Resposta HTTP com código 404 (Not Found) se a conta conjunta não for encontrada.
     */
    @GET
    @Path("/{contaConjuntaID}")
    public Response obterContaConjunta(@PathParam("contaConjuntaID") Long contaConjuntaID) {
        try {
            ContasConjuntas contaConjunta = contasConjuntasService.obterContaConjunta(contaConjuntaID);
            return Response.status(Response.Status.OK).entity(contaConjunta).build();
        } catch (ContaConjuntaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Cria uma nova conta conjunta no sistema.
     *
     * @param contaConjunta A conta conjunta a ser criada.
     * @return Resposta HTTP com código 201 (Created) e a nova conta conjunta se for bem-sucedida.
     *         Resposta HTTP com código 409 (Conflict) se uma conta conjunta com o mesmo número já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta conjunta não forem válidos.
     */
    @POST
    public Response criarContaConjunta(@Valid ContasConjuntas contaConjunta) {
        try {
            ContasConjuntas novaContaConjunta = contasConjuntasService.criarContaConjunta(contaConjunta);
            return Response.status(Response.Status.CREATED).entity(novaContaConjunta).build();
        } catch (ContaConjuntaExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Atualiza uma conta conjunta existente no sistema.
     *
     * @param contaConjuntaID O ID da conta conjunta a ser atualizada.
     * @param contaConjunta Os dados atualizados da conta conjunta.
     * @return Resposta HTTP com código 200 (OK) e a conta conjunta atualizada se for bem-sucedida.
     *         Resposta HTTP com código 404 (Not Found) se a conta conjunta não for encontrada.
     *         Resposta HTTP com código 409 (Conflict) se uma conta conjunta com o mesmo número já existir.
     *         Resposta HTTP com código 400 (Bad Request) se os dados da conta conjunta não forem válidos.
     */
    @PUT
    @Path("/{contaConjuntaID}")
    public Response atualizarContaConjunta(@PathParam("contaConjuntaID") Long contaConjuntaID, @Valid ContasConjuntas contaConjunta) {
        try {
            ContasConjuntas contaConjuntaAtualizada = contasConjuntasService.atualizarContaConjunta(contaConjuntaID, contaConjunta);
            return Response.status(Response.Status.OK).entity(contaConjuntaAtualizada).build();
        } catch (ContaConjuntaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ContaConjuntaExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Remove uma conta conjunta pelo seu ID.
     *
     * @param contaConjuntaID O ID da conta conjunta a ser removida.
     * @return Resposta HTTP com código 204 (No Content) se a conta conjunta for removida com sucesso.
     *         Resposta HTTP com código 404 (Not Found) se a conta conjunta não for encontrada.
     */
    @DELETE
    @Path("/{contaConjuntaID}")
    public Response removerContaConjunta(@PathParam("contaConjuntaID") Long contaConjuntaID) {
        try {
            contasConjuntasService.removerContaConjunta(contaConjuntaID);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ContaConjuntaNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
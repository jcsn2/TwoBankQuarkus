package acc.br.controllers;

import acc.br.exception.ParametroConfiguracaoNaoEncontradoException;
import acc.br.model.ParametrosConfiguracao;
import acc.br.service.ParametrosConfiguracaoService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador para a entidade ParametrosConfiguracao.
 */
@Path("/parametros-configuracao")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParametrosConfiguracaoController {

    @Inject
    ParametrosConfiguracaoService parametrosConfiguracaoService;
    
    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final Logger logger = Logger.getLogger(ParametrosConfiguracaoController.class.getName());

    /**
     * Cria um novo registro de parâmetro de configuração.
     *
     * @param parametrosConfiguracao O objeto ParametrosConfiguracao a ser criado.
     * @return Uma resposta HTTP com o código 201 (Created) e o URI do novo recurso criado.
     */
    @POST
    public Response criarParametrosConfiguracao(@Valid ParametrosConfiguracao parametrosConfiguracao) {
        parametrosConfiguracaoService.criarParametrosConfiguracao(parametrosConfiguracao);
        URI uri = UriBuilder.fromPath("/parametros-configuracao/{id}")
                .resolveTemplate("id", parametrosConfiguracao.getParametroID())
                .build();
        logger.info("Parametro criado com sucesso: " + parametrosConfiguracao);
        return Response.created(uri).build();
    }

    /**
     * Atualiza um registro de parâmetro de configuração existente.
     *
     * @param id                      O ID do registro de parâmetro de configuração a ser atualizado.
     * @param parametrosConfiguracao   O objeto ParametrosConfiguracao com as atualizações.
     * @return Uma resposta HTTP com o código 204 (No Content) se a atualização for bem-sucedida.
     *         Uma resposta HTTP com o código 404 (Not Found) se o registro não for encontrado.
     */
    @PUT
    @Path("/{id}")
    public Response atualizarParametrosConfiguracao(@PathParam("id") Long id, @Valid ParametrosConfiguracao parametrosConfiguracao) {
        try {
            parametrosConfiguracaoService.atualizarParametrosConfiguracao(id, parametrosConfiguracao);
            logger.info("Parametro atualizado com sucesso: " + parametrosConfiguracao);
            return Response.status(HTTP_OK).build();
        } catch (ParametroConfiguracaoNaoEncontradoException e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar notificação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Obtém um registro de parâmetro de configuração pelo ID.
     *
     * @param id O ID do registro de parâmetro de configuração a ser obtido.
     * @return Uma resposta HTTP com o código 200 (OK) e o objeto ParametrosConfiguracao correspondente ao ID.
     *         Uma resposta HTTP com o código 404 (Not Found) se o registro não for encontrado.
     */
    @GET
    @Path("/{id}")
    public Response obterParametrosConfiguracao(@PathParam("id") Long id) {
        try {
            ParametrosConfiguracao parametrosConfiguracao = parametrosConfiguracaoService.obterParametrosConfiguracao(id);
            logger.info("Parametro obtido com sucesso: " + id);
            return Response.ok(parametrosConfiguracao).build();
        } catch (ParametroConfiguracaoNaoEncontradoException e) {
        	logger.log(Level.SEVERE, "Erro ao obter notificação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    /**
     * Lista todos os registros de parâmetros de configuração.
     *
     * @return Uma resposta HTTP com o código 200 (OK) e uma lista de todos os registros de parâmetros de configuração.
     */
    @GET
    public Response listarParametrosConfiguracao() {
        List<ParametrosConfiguracao> parametrosConfiguracaoList = parametrosConfiguracaoService.listarParametrosConfiguracao();
        logger.info("Parametro listado com sucesso");
        return Response.ok(parametrosConfiguracaoList).build();
    }

    /**
     * Remove um registro de parâmetro de configuração pelo ID.
     *
     * @param id O ID do registro de parâmetro de configuração a ser removido.
     * @return Uma resposta HTTP com o código 204 (No Content) se a remoção for bem-sucedida.
     *         Uma resposta HTTP com o código 404 (Not Found) se o registro não for encontrado.
     */
    @DELETE
    @Path("/{id}")
    public Response removerParametrosConfiguracao(@PathParam("id") Long id) {
        try {
            parametrosConfiguracaoService.removerParametrosConfiguracao(id);
            logger.info("Parametro removido com sucesso: " + id);
            return Response.noContent().build();
        } catch (ParametroConfiguracaoNaoEncontradoException e) {
        	logger.log(Level.SEVERE, "Erro ao remover notificação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}

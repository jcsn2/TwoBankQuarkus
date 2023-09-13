package acc.br.controllers;

import acc.br.exception.ParametroConfiguracaoNaoEncontradoException;
import acc.br.exception.PoupancaNaoEncontradaException;
import acc.br.model.Poupanca;
import acc.br.service.PoupancaService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

/**
 * Controlador para a entidade Poupanca.
 */
@Path("/poupanca")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PoupancaController {

    @Inject
    PoupancaService poupancaService;

    /**
     * Cria uma nova conta poupança.
     *
     * @param poupanca A conta poupança a ser criada.
     * @return Uma resposta com o código 201 (Created) em caso de sucesso.
     */
    @POST
    public Response criarPoupanca(@Valid Poupanca poupanca) {
        poupancaService.criarPoupanca(poupanca);
        URI location = UriBuilder.fromResource(PoupancaController.class).path("/{id}").build(poupanca.getPoupancaID());
        return Response.created(location).build();
    }

    /**
     * Atualiza uma conta poupança existente.
     *
     * @param id       O ID da conta poupança a ser atualizada.
     * @param poupanca A conta poupança com as atualizações.
     * @return Uma resposta com o código 200 (OK) em caso de sucesso.
     * @throws PoupancaNaoEncontradaException Se a conta poupança não for encontrada.
     */
    @PUT
    @Path("/{id}")
    public Response atualizarPoupanca(@PathParam("id") Long id, @Valid Poupanca poupanca) throws PoupancaNaoEncontradaException {
        poupancaService.atualizarPoupanca(id, poupanca);
        return Response.ok().build();
    }

    /**
     * Obtém uma conta poupança pelo ID.
     *
     * @param id O ID da conta poupança a ser obtida.
     * @return A conta poupança correspondente ao ID.
     * @throws PoupancaNaoEncontradaException Se a conta poupança não for encontrada.
     */
    @GET
    @Path("/{id}")
    public Response obterPoupanca(@PathParam("id") Long id) throws PoupancaNaoEncontradaException {
        Poupanca poupanca = poupancaService.obterPoupanca(id);
        return Response.ok(poupanca).build();
    }

    /**
     * Lista todas as contas poupança.
     *
     * @return Uma lista de todas as contas poupança.
     */
    @GET
    public Response listarPoupancas() {
        List<Poupanca> poupancas = poupancaService.listarPoupancas();
        return Response.ok(poupancas).build();
    }

    /**
     * Remove uma conta poupança pelo ID.
     *
     * @param id O ID da conta poupança a ser removida.
     * @return Uma resposta com o código 204 (No Content) em caso de sucesso.
     * @throws PoupancaNaoEncontradaException Se a conta poupança não for encontrada.
     */
    @DELETE
    @Path("/{id}")
    public Response removerPoupanca(@PathParam("id") Long id) throws PoupancaNaoEncontradaException {
        poupancaService.removerPoupanca(id);
        return Response.noContent().build();
    }

    /**
     * Calcula a atualização mensal do saldo de uma conta poupança.
     *
     * @param id O ID da conta poupança para a qual a atualização será calculada.
     * @return O novo saldo da poupança após a atualização.
     * @throws PoupancaNaoEncontradaException     Se a conta poupança não for encontrada.
     * @throws ParametroConfiguracaoNaoEncontradoException Se a configuração da taxa de poupança não for encontrada.
     */
    @POST
    @Path("/{id}/calcular-atualizacao")
    public Response calcularAtualizacaoMensal(@PathParam("id") Long id)
            throws PoupancaNaoEncontradaException, ParametroConfiguracaoNaoEncontradoException {
        Poupanca poupanca = poupancaService.obterPoupanca(id);
        BigDecimal novoSaldo = poupancaService.calcularAtualizacaoMensal(poupanca);
        return Response.ok(novoSaldo).build();
    }
}

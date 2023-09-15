package acc.br.controllers;

import acc.br.exception.ClienteNaoEncontradoException;
import acc.br.exception.ContaNaoEncontradaException;
import acc.br.exception.NotificacaoNaoEncontradaException;
import acc.br.exception.TransacoesNaoEncontradaException;
import acc.br.model.Clientes;
import acc.br.model.Contas;
import acc.br.model.Transacoes;
import acc.br.repository.ContasRepository;
import acc.br.service.TransacoesService;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador para a entidade Transacoes.
 */
@Path("/transacoes")
@RegisterForReflection
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransacoesController {

    @Inject
    TransacoesService transacoesService;

    @Inject
    ContasRepository contasRepository;

    @Inject
    EntityManager entityManager;

    /**
     * Cria uma nova transação financeira.
     *
     * @param transacao A transação a ser criada.
     * @return A resposta HTTP com a transação criada.
     */
    @POST
    public Response criarTransacao(Transacoes transacao) {
        try {
            transacoesService.criarTransacao(transacao);
            return Response.status(Response.Status.CREATED).entity(transacao).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao criar a transação: " + e.getMessage()).build();
        }
    }

    /**
     * Atualiza uma transação financeira existente.
     *
     * @param id       O ID da transação a ser atualizada.
     * @param transacao A transação com as atualizações.
     * @return A resposta HTTP com a transação atualizada.
     */
    @PUT
    @Path("/{id}")
    public Response atualizarTransacao(@PathParam("id") Long id, Transacoes transacao) {
        try {
            transacoesService.atualizarTransacao(id, transacao);
            return Response.ok().entity(transacao).build();
        } catch (TransacoesNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Transação não encontrada com o ID: " + id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar a transação: " + e.getMessage()).build();
        }
    }

    /**
     * Obtém uma transação financeira pelo ID.
     *
     * @param id O ID da transação a ser obtida.
     * @return A resposta HTTP com a transação correspondente ao ID.
     */
    @GET
    @Path("/{id}")
    public Response obterTransacao(@PathParam("id") Long id) {
        try {
            Transacoes transacao = transacoesService.obterTransacao(id);
            return Response.ok().entity(transacao).build();
        } catch (TransacoesNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Transação não encontrada com o ID: " + id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao obter a transação: " + e.getMessage()).build();
        }
    }

    /**
     * Lista todas as transações financeiras.
     *
     * @return A resposta HTTP com uma lista de todas as transações financeiras.
     */
    @GET
    public Response listarTransacoes() {
        try {
            List<Transacoes> transacoes = transacoesService.listarTransacoes();
            return Response.ok().entity(transacoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar as transações: " + e.getMessage()).build();
        }
    }

    /**
     * Remove uma transação financeira pelo ID.
     *
     * @param id O ID da transação a ser removida.
     * @return A resposta HTTP com o resultado da remoção.
     */
    @DELETE
    @Path("/{id}")
    public Response removerTransacao(@PathParam("id") Long id) {
        try {
            transacoesService.removerTransacao(id);
            return Response.noContent().build();
        } catch (TransacoesNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Transação não encontrada com o ID: " + id).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao remover a transação: " + e.getMessage()).build();
        }
    }

    /**
     * Lista todas as transferências de uma determinada conta com base no ID da conta.
     *
     * @param contaID O ID da conta para a qual deseja listar as transferências.
     * @return A resposta HTTP com uma lista de transferências associadas à conta.
     */
    @GET
    @Path("/transferencias/{contaID}")
    public Response listarTransferenciasPorConta(@PathParam("contaID") Long contaID) {
          
        Contas contaExistente = contasRepository.findById(contaID);
        if (contaExistente == null) {
            throw new ContaNaoEncontradaException("Conta não encontrado com ID: " + contaID);
        }
        try {
            List<Transacoes> transferencias = transacoesService.listarTransferenciasPorConta(contaID);
            return Response.ok().entity(transferencias).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar as transferências: " + e.getMessage()).build();
        }
    }

    /**
     * Lista todas as transferências a partir de um valor mínimo.
     *
     * @param valorMinimo O valor mínimo das transferências desejadas.
     * @return A resposta HTTP com uma lista de transferências com valores maiores ou iguais ao valor mínimo.
     */
    @GET
    @Path("/transferencias")
    public Response listarTransferenciasPorValorMinimo(@QueryParam("valorMinimo") BigDecimal valorMinimo) {
        try {
            List<Transacoes> transferencias = transacoesService.listarTransferenciasPorValorMinimo(valorMinimo);
            return Response.ok().entity(transferencias).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar as transferências: " + e.getMessage()).build();
        }
    }
    
    /**
     * Realiza um saque em uma conta.
     *
     * @param contaID O ID da conta a ser sacado.
     * @param valor   O valor a ser sacado.
     * @return A resposta HTTP 200 OK com a transação de saque criada.
     */
    @POST
    @Path("/saque/{contaID}")
    @Transactional
    public Response realizarSaque(@PathParam("contaID") Long contaID, @NotNull BigDecimal valor) {  

        Contas contaExistente = contasRepository.findById(contaID);
        if (contaExistente == null) {
            throw new ContaNaoEncontradaException("Conta não encontrado com ID: " + contaID);
        }
        try {
            transacoesService.realizarSaque(contaID, valor);
            return Response.status(Response.Status.OK).entity("Saque realizado com sucesso.").build();
        } catch (ContaNaoEncontradaException e) {
            throw new WebApplicationException("Conta não encontrada.", Response.Status.NOT_FOUND);
        } catch (NotificacaoNaoEncontradaException e) {
            throw new WebApplicationException("Notificação não encontrada.", Response.Status.NOT_FOUND);
        }
    }
    
    /**
     * Realiza um depósito em uma conta.
     *
     * @param contaID O ID da conta a ser depositado.
     * @param valor   O valor a ser depositado.
     * @return A resposta HTTP 200 OK com a transação de depósito criada.
     */
    @POST
    @Path("/deposito/{contaID}")
    @Transactional
    public Response depositar(@PathParam("contaID") Long contaID, @NotNull BigDecimal valor) {
        
        Contas contaExistente = contasRepository.findById(contaID);
        if (contaExistente == null) {
            throw new ContaNaoEncontradaException("Conta não encontrado com ID: " + contaID);
        }
        try {
            transacoesService.depositar(contaID, valor);
            return Response.status(Response.Status.OK).entity("Depósito realizado com sucesso.").build();
        } catch (ContaNaoEncontradaException e) {
            throw new WebApplicationException("Conta não encontrada.", Response.Status.NOT_FOUND);
        }
    }

    /**
     * Realiza uma transferência entre duas contas.
     *
     * @param contaOrigemID   O ID da conta de origem.
     * @param contaDestinoID  O ID da conta de destino.
     * @param valor           O valor a ser transferido.
     * @return A resposta HTTP 200 OK com a transação de transferência criada.
     */
    @POST
    @Path("/transferencia/{contaOrigemID}-{contaDestinoID}")
    @Transactional
    public Response transferir(@PathParam("contaOrigemID") Long contaOrigemID,
                               @PathParam("contaDestinoID") Long contaDestinoID,
                               @NotNull BigDecimal valor) {
        Contas contaOrigemExistente = contasRepository.findById(contaOrigemID);
        if (contaOrigemExistente == null) {
            throw new ContaNaoEncontradaException("Conta não encontrado com ID: " + contaOrigemID);}

        Contas contaDestinoExistente = contasRepository.findById(contaDestinoID);
        if (contaDestinoExistente == null) {
            throw new ContaNaoEncontradaException("Conta não encontrado com ID: " + contaDestinoID);}

        try {
            transacoesService.transferir(contaOrigemID, contaDestinoID, valor);
            return Response.status(Response.Status.OK).entity("Transferência realizada com sucesso.").build();
        } catch (ContaNaoEncontradaException e) {
            throw new WebApplicationException("Uma das contas não foi encontrada.", Response.Status.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

}
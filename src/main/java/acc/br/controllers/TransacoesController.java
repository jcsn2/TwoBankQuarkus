package acc.br.controllers;

import acc.br.exception.ContaNaoEncontradaException;
import acc.br.exception.NotificacaoNaoEncontradaException;
import acc.br.exception.SaldoInsuficienteException;
import acc.br.exception.SaqueExcedeLimiteException;
import acc.br.exception.TransacoesNaoEncontradaException;
import acc.br.exception.ValorMinimoInvalidoException;
import acc.br.model.Transacoes;
import acc.br.repository.ContaCorrenteRepository;
import acc.br.repository.ContasConjuntasRepository;
import acc.br.repository.ContasRepository;
import acc.br.repository.PoupancaRepository;
import acc.br.service.TransacoesService;
import acc.br.util.DepositoRequest;
import acc.br.util.SaqueRequest;
import acc.br.util.TipoConta;
import acc.br.util.TipoDeposito;
import acc.br.util.TransferenciaRequest;
import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    @Inject
    PoupancaRepository poupancaRepository;
    
    @Inject
    ContasConjuntasRepository contasConjuntasRepository;
    
    @Inject
    ContaCorrenteRepository contaCorrenteRepository;
    
    private static final int HTTP_OK = Response.Status.OK.getStatusCode();
    private static final Logger logger = Logger.getLogger(TransacoesController.class.getName());
    
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
            logger.info("Transação criada com sucesso: " + transacao);
            return Response.status(HTTP_OK).entity(transacao).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao criar transação: " + e.getMessage(), e);
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
            logger.info("Transação atualizada com sucesso: " + transacao);
            return Response.status(HTTP_OK).entity(transacao).build();
        } catch (TransacoesNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar transação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Transação não encontrada com o ID: " + id).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao atualizar transação: " + e.getMessage(), e);
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
            logger.info("Transação retornada com sucesso: " + transacao);
            return Response.status(HTTP_OK).entity(transacao).build();
        } catch (TransacoesNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Transação não encontrada: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Transação não encontrada com o ID: " + id).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Transação não encontrada: " + e.getMessage(), e);
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
            logger.info("Transação listada com sucesso");
            return Response.status(HTTP_OK).entity(transacoes).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Transação não encontrada: " + e.getMessage(), e);
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
            logger.info("Transação removida com sucesso: "  + id);
            return Response.status(HTTP_OK).build();
        } catch (TransacoesNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao remover a transação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Transação não encontrada com o ID: " + id).build();
        } catch (Exception e) {
        	logger.log(Level.SEVERE, "Erro ao remover a transação: " + e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao remover a transação: " + e.getMessage()).build();
        }
    }

    /**
     * Lista todas as transferências de uma determinada conta com base no ID da conta.
     *
     * @param contaID O ID da conta para a qual deseja listar as transferências.
     * @return Uma lista de transferências associadas à conta.
     */
    public Response listarTransferenciasPorConta(Long contaID) {
        try {
            List<Transacoes> transferencias = transacoesService.listarTransferenciasPorConta(contaID);
            logger.info("Transação listada com sucesso: "  + contaID);
            return Response.status(HTTP_OK).entity(transferencias).build();
        } catch (ContaNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao encontrar a transação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity("Conta não encontrada.").build();
        }
    }

    /**
     * Lista todos os saques de uma determinada conta com base no ID da conta.
     *
     * @param contaID O ID da conta para a qual deseja listar os saques.
     * @return Uma lista de saques associadas à conta.
     */
    public Response listarSaquesPorConta(Long contaID) {
        try {
            List<Transacoes> saques = transacoesService.listarSaquesPorConta(contaID);
            logger.info("Transação listada com sucesso: "  + contaID);
            return Response.status(HTTP_OK).entity(saques).build();
        } catch (ContaNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao encontrar a transação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity("Conta não encontrada.").build();
        }
    }

    /**
     * Lista todos os depósitos de uma determinada conta com base no ID da conta.
     *
     * @param contaID O ID da conta para a qual deseja listar os depósitos.
     * @return Uma lista de depósitos associadas à conta.
     */
    public Response listarDepositosPorConta(Long contaID) {
        try {
            List<Transacoes> depositos = transacoesService.listarDepositosPorConta(contaID);
            logger.info("Transação listada com sucesso: "  + contaID);
            return Response.status(HTTP_OK).entity(depositos).build();
        } catch (ContaNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao encontrar a transação: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity("Conta não encontrada.").build();
        }
    }

    /**
     * Lista todas as transferências a partir de um valor mínimo.
     *
     * @param valorMinimo O valor mínimo das transferências desejadas.
     * @return Uma lista de transferências com valores maiores ou iguais ao valor mínimo.
     */
    public Response listarTransferenciasPorValorMinimo(BigDecimal valorMinimo) {
        try {
            List<Transacoes> transferencias = transacoesService.listarTransferenciasPorValorMinimo(valorMinimo);
            logger.info("Transação listada com sucesso: "  + valorMinimo);
            return Response.status(HTTP_OK).entity(transferencias).build();
        } catch (ValorMinimoInvalidoException e) {
        	logger.log(Level.SEVERE, "Erro ao encontrar a transação: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Valor mínimo inválido.").build();
        }
    }

    /**
     * Lista todos os saques a partir de um valor mínimo.
     *
     * @param valorMinimo O valor mínimo dos saques desejadas.
     * @return Uma lista de saques com valores maiores ou iguais ao valor mínimo.
     */
    public Response listarSaquesPorValorMinimo(BigDecimal valorMinimo) {
        try {
            List<Transacoes> saques = transacoesService.listarSaquesPorValorMinimo(valorMinimo);
            logger.info("Transação listada com sucesso: "  + valorMinimo);
            return Response.status(HTTP_OK).entity(saques).build();
        } catch (ValorMinimoInvalidoException e) {
        	logger.log(Level.SEVERE, "Erro ao encontrar a transação: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Valor mínimo inválido.").build();
        }
    }

    /**
     * Lista todos os depósitos a partir de um valor mínimo.
     *
     * @param valorMinimo O valor mínimo dos depósitos desejadas.
     * @return Uma lista de depósitos com valores maiores ou iguais ao valor mínimo.
     */
    public Response listarDepositosPorValorMinimo(BigDecimal valorMinimo) {
        try {
            List<Transacoes> depositos = transacoesService.listarDepositosPorValorMinimo(valorMinimo);
            logger.info("Transação listada com sucesso: "  + valorMinimo);
            return Response.status(HTTP_OK).entity(depositos).build();
        } catch (ValorMinimoInvalidoException e) {
        	logger.log(Level.SEVERE, "Erro ao encontrar a transação: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Valor mínimo inválido.").build();
        }
    }
    
    /**
     * Realiza um saque em uma conta com base no tipo de conta especificado no JSON do corpo da solicitação.
     *
     * @param contaID O ID da conta.
     * @param saqueRequest Objeto que contém o valor do saque e o tipo de conta.
     * @return A resposta HTTP 200 OK com a transação de saque criada.
     * @throws NotificacaoNaoEncontradaException  Se houver um problema com o serviço de notificações.
     * @throws SaqueExcedeLimiteException         Se o saque exceder o limite da conta.
     */
    @POST
    @Path("/saque/{contaID}")
    @Transactional
    public Response realizarSaque(
            @PathParam("contaID") Long contaID,
            SaqueRequest saqueRequest) {  
        verificarContaExistente(contaID, saqueRequest.getTipoConta());
        BigDecimal valor = saqueRequest.getValor();
        try {
            transacoesService.realizarSaque(contaID, valor, saqueRequest.getTipoConta());
            logger.info("Saque realizado com sucesso: "  + contaID);
            return Response.status(HTTP_OK).entity("Saque realizado com sucesso.").build();
        } catch (NotificacaoNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao sacar: " + e.getMessage(), e);
        	throw new WebApplicationException("Notificação não encontrada.", Response.Status.NOT_FOUND);
        } catch (SaqueExcedeLimiteException e) {
        	logger.log(Level.SEVERE, "Erro ao sacar: " + e.getMessage(), e);
        	throw new WebApplicationException("Saque excede o limite da conta.", Response.Status.BAD_REQUEST);
        }
    }
    
    /**
     * Realiza um depósito em uma conta.
     *
     * @param depositoRequest O objeto de solicitação de depósito no formato JSON.
     * @return A resposta HTTP 200 OK com a transação de depósito criada.
     */
    @POST
    @Path("/deposito/{contaID}")
    @Transactional
    public Response realizarDeposito(@PathParam("contaID") Long contaID, @Valid DepositoRequest depositoRequest) {
        BigDecimal valor = depositoRequest.getValor();
        TipoConta tipoConta = depositoRequest.getTipoConta();
        TipoDeposito tipoDeposito = depositoRequest.getTipoDeposito();
        String numeroCheque = depositoRequest.getNumeroCheque();

        verificarContaExistente(contaID, tipoConta);

        try {
            transacoesService.realizarDeposito(contaID, valor, tipoConta, tipoDeposito, numeroCheque);
            logger.info("Depósito realizado com sucesso: "  + contaID);
            return Response.status(HTTP_OK).entity("Depósito realizado com sucesso.").build();
        } catch (ContaNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao depositar: " + e.getMessage(), e);
            throw new WebApplicationException("Conta não encontrada.", Response.Status.NOT_FOUND);
        }
    }
    
    /**
     * Realiza uma transferência entre duas contas.
     *
     * @param transferenciaRequest O objeto de solicitação de transferência no formato JSON.
     * @return A resposta HTTP 200 OK com a transação de transferência criada.
     */
    @POST
    @Path("/transferencia/{contaOrigemID}-{contaDestinoID}")
    @Transactional
    public Response realizarTransferencia(@PathParam("contaOrigemID") Long contaOrigemID,
            @PathParam("contaDestinoID") Long contaDestinoID, @Valid TransferenciaRequest transferenciaRequest) {
        BigDecimal valor = transferenciaRequest.getValor();
        TipoConta tipoContaOrigem = transferenciaRequest.getTipoContaOrigem();
        TipoConta tipoContaDestino = transferenciaRequest.getTipoContaDestino();

        verificarContaExistente(contaOrigemID, tipoContaOrigem);
        verificarContaExistente(contaDestinoID, tipoContaDestino);

        try {
            transacoesService.realizarTransferencia(contaOrigemID, contaDestinoID, valor, tipoContaOrigem, tipoContaDestino);
            logger.info("Transferencia realizada com sucesso: "  + contaOrigemID);
            return Response.status(Response.Status.OK).entity("Transferência realizada com sucesso.").build();
        } catch (ContaNaoEncontradaException e) {
        	logger.log(Level.SEVERE, "Erro ao transferir: " + e.getMessage(), e);
            throw new WebApplicationException("Uma das contas não foi encontrada.", Response.Status.NOT_FOUND);
        } catch (SaldoInsuficienteException e) {
        	logger.log(Level.SEVERE, "Erro ao transferir: " + e.getMessage(), e);
            throw new WebApplicationException("Saldo insuficiente na conta de origem.", Response.Status.BAD_REQUEST);
        }
    }
    
    /**
     * Verifica a existência de uma conta com base no tipo de conta e ID.
     *
     * @param contaID    O ID da conta.
     * @param tipoConta  O tipo de conta.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     */
    private void verificarContaExistente(Long contaID, TipoConta tipoConta) throws ContaNaoEncontradaException {
        if (contaID == null) {
            throw new IllegalArgumentException("O ID da conta não pode ser nulo.");
        }

        if (tipoConta == null) {
            throw new IllegalArgumentException("O Tipo Conta não pode ser nulo.");
        }

        switch (tipoConta) {
            case CONTA_CORRENTE:
                if (contaCorrenteRepository.findById(contaID) == null) {
                    throw new ContaNaoEncontradaException("Conta Corrente não encontrada com ID: " + contaID);
                }
                break;
            case CONTA_CONJUNTA:
                if (contasConjuntasRepository.findById(contaID) == null) {
                    throw new ContaNaoEncontradaException("Conta Conjunta não encontrada com ID: " + contaID);
                }
                break;
            case CONTA_POUPANCA:
                if (poupancaRepository.findById(contaID) == null) {
                    throw new ContaNaoEncontradaException("Conta Poupança não encontrada com ID: " + contaID);
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de conta não suportado: " + tipoConta);
        }
    }


}
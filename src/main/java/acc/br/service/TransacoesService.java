package acc.br.service;

import acc.br.exception.AlertaNaoEncontradoException;
import acc.br.exception.ContaNaoEncontradaException;
import acc.br.exception.NotificacaoNaoEncontradaException;
import acc.br.exception.SaldoInsuficienteException;
import acc.br.exception.SaqueExcedeLimiteException;
import acc.br.exception.SaqueExcedeSaldoException;
import acc.br.exception.TransacoesNaoEncontradaException;
import acc.br.model.AlertasGastosExcessivos;
import acc.br.model.ContaCorrente;
import acc.br.model.Contas;
import acc.br.model.ContasConjuntas;
import acc.br.model.Notificacoes;
import acc.br.model.Poupanca;
import acc.br.model.Transacoes;
import acc.br.repository.ContaCorrenteRepository;
import acc.br.repository.ContasConjuntasRepository;
import acc.br.repository.PoupancaRepository;
import acc.br.repository.TransacoesRepository;
import acc.br.util.TipoConta;
import acc.br.util.TipoDeposito;
import acc.br.util.TipoTransacao;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe de serviço para a entidade Transacoes.
 */
@ApplicationScoped
public class TransacoesService {

    @Inject
    TransacoesRepository transacoesRepository;
    
    @Inject
    ContaCorrenteRepository contaCorrenteRepository;
    
    @Inject
    ContasConjuntasRepository contasConjuntasRepository;
    
    @Inject
    PoupancaRepository poupancaRepository;
    
    @Inject
    AlertasGastosExcessivos alertasGastosExcessivos;

    @Inject
    AlertasGastosExcessivosService alertasGastosExcessivosService;
    
    @Inject
    Notificacoes notificacoes;
    
    @Inject 
    NotificacoesService notificacoesService;

    @Inject
    EntityManager entityManager;
    
   
    /**
     * Cria uma nova transação financeira.
     *
     * @param transacao A transação a ser criada.
     */
    @Transactional
    public void criarTransacao(Transacoes transacao) {
        transacao.setDataHoraTransacao(LocalDate.now());
        Transacoes transacaoGerenciado = entityManager.merge(transacao); // Mescla a entidade no contexto de persistência
        entityManager.persist(transacaoGerenciado); // Persiste a entidade
    }

    /**
     * Atualiza uma transação financeira existente.
     *
     * @param id       O ID da transação a ser atualizada.
     * @param transacao A transação com as atualizações.
     * @throws TransacoesNaoEncontradaException Se a transação não for encontrada.
     */
    @Transactional
    public void atualizarTransacao(Long id, Transacoes transacao) throws TransacoesNaoEncontradaException {
        Transacoes entity = transacoesRepository.findById(id);
        if (entity == null) {
            throw new TransacoesNaoEncontradaException("Transação financeira não encontrada com o ID: " + id);
        }

        entity.setTipoTransacao(transacao.getTipoTransacao());
        entity.setValor(transacao.getValor());
        entity.setContaID(transacao.getContaID());
        entity.setContaDestinoID(transacao.getContaDestinoID());
        entity.setNumeroCheque(transacao.getNumeroCheque());

        Transacoes transacaoGerenciado = entityManager.merge(transacao); // Mescla a entidade no contexto de persistência
        entityManager.persist(transacaoGerenciado); // Persiste a entidade
    }

    /**
     * Obtém uma transação financeira pelo ID.
     *
     * @param id O ID da transação a ser obtida.
     * @return A transação financeira correspondente ao ID.
     * @throws TransacoesNaoEncontradaException Se a transação não for encontrada.
     */
    public Transacoes obterTransacao(Long id) throws TransacoesNaoEncontradaException {
        Transacoes transacao = transacoesRepository.findById(id);
        if (transacao == null) {
            throw new TransacoesNaoEncontradaException("Transação financeira não encontrada com o ID: " + id);
        }
        return transacao;
    }

    /**
     * Lista todas as transações financeiras.
     *
     * @return Uma lista de todas as transações financeiras.
     */
    public List<Transacoes> listarTransacoes() {
        return transacoesRepository.listAll();
    }

    /**
     * Remove uma transação financeira pelo ID.
     *
     * @param id O ID da transação a ser removida.
     * @throws TransacoesNaoEncontradaException Se a transação não for encontrada.
     */
    @Transactional
    public void removerTransacao(Long id) throws TransacoesNaoEncontradaException {
        Transacoes transacao = transacoesRepository.findById(id);
        if (transacao == null) {
            throw new TransacoesNaoEncontradaException("Transação financeira não encontrada com o ID: " + id);
        }
        transacoesRepository.delete(transacao);
    }

    /**
     * Lista todas as transferências de uma determinada conta com base no ID da conta.
     *
     * @param contaID O ID da conta para a qual deseja listar as transferências.
     * @return Uma lista de transferências associadas à conta.
     */
    public List<Transacoes> listarTransferenciasPorConta(Long contaID) {
        return transacoesRepository.find("tipoTransacao = ?1 and contaID = ?2",
                TipoTransacao.TRANSFERENCIA, contaID).list();
    }
    
    /**
     * Lista todos os saques de uma determinada conta com base no ID da conta.
     *
     * @param contaID O ID da conta para a qual deseja listar os saques.
     * @return Uma lista de saques associadas à conta.
     */
    public List<Transacoes> listarSaquesPorConta(Long contaID) {
        return transacoesRepository.find("tipoTransacao = ?1 and contaID = ?2",
                TipoTransacao.SAQUE, contaID).list();
    }
    
    /**
     * Lista todos os depositos de uma determinada conta com base no ID da conta.
     *
     * @param contaID O ID da conta para a qual deseja listar os saques.
     * @return Uma lista de depositos associadas à conta.
     */
    public List<Transacoes> listarDepositosPorConta(Long contaID) {
        return transacoesRepository.find("tipoTransacao = ?1 and contaID = ?2",
                TipoTransacao.DEPOSITO, contaID).list();
    }

    /**
     * Lista todas as transferências a partir de um valor mínimo.
     *
     * @param valorMinimo O valor mínimo das transferências desejadas.
     * @return Uma lista de transferências com valores maiores ou iguais ao valor mínimo.
     */
    public List<Transacoes> listarTransferenciasPorValorMinimo(BigDecimal valorMinimo) {
        return transacoesRepository.find("tipoTransacao = ?1 and valor >= ?2",
                TipoTransacao.TRANSFERENCIA, valorMinimo).list();
    }
    
    /**
     * Lista todos os saques a partir de um valor mínimo.
     *
     * @param valorMinimo O valor mínimo dos saques desejadas.
     * @return Uma lista de saques com valores maiores ou iguais ao valor mínimo.
     */
    public List<Transacoes> listarSaquesPorValorMinimo(BigDecimal valorMinimo) {
        return transacoesRepository.find("tipoTransacao = ?1 and valor >= ?2",
                TipoTransacao.SAQUE, valorMinimo).list();
    }
    
    /**
     * Lista todos os depositos a partir de um valor mínimo.
     *
     * @param valorMinimo O valor mínimo dos saques desejadas.
     * @return Uma lista de depositos com valores maiores ou iguais ao valor mínimo.
     */
    public List<Transacoes> listarDepositosPorValorMinimo(BigDecimal valorMinimo) {
        return transacoesRepository.find("tipoTransacao = ?1 and valor >= ?2",
                TipoTransacao.DEPOSITO, valorMinimo).list();
    }
    
    /**
     * Realiza um saque em uma conta e verifica o limite permitido.
     *
     * @param contaID O ID da conta.
     * @param valor   O valor a ser sacado.
     * @throws SaqueExcedeLimiteException Se o saque exceder o limite da conta.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     * @throws NotificacaoNaoEncontradaException Se houver um problema com o serviço de notificações.
     */
    @Transactional
    public void realizarSaque(Long contaID, @NotNull BigDecimal valor, @NotNull TipoConta tipoConta) throws SaqueExcedeLimiteException, ContaNaoEncontradaException, NotificacaoNaoEncontradaException {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }

        if (contaID == null) {
            throw new IllegalArgumentException("O ID da conta não pode ser nulo.");
        }
        
        if (tipoConta == null) {
            throw new IllegalArgumentException("O Tipo Conta não pode ser nulo.");
        }
        
        if(tipoConta.equals(TipoConta.CONTA_CORRENTE)) {
            ContaCorrente contaCorrente = contaCorrenteRepository.findById(contaID);
            if (contaCorrente != null) {
                realizarSaqueContaCorrente(contaCorrente, valor);
                return;
            }
        }

        else if(tipoConta.equals(TipoConta.CONTA_CONJUNTA)) {
            ContasConjuntas contasConjuntas = contasConjuntasRepository.findById(contaID);
            if (contasConjuntas != null) {
                realizarSaqueContasConjuntas(contasConjuntas, valor);
                return;
            }
        }
        
        else if(tipoConta.equals(TipoConta.CONTA_POUPANCA))
        {
            Poupanca poupanca = poupancaRepository.findById(contaID);
            if (poupanca != null) {
                realizarSaquePoupanca(poupanca, valor);
                return;
            }
        }
        
        throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
    }
    
    /**
     * Realiza um depósito em uma conta.
     *
     * @param contaID O ID da conta.
     * @param valor O valor a ser depositado.
     * @param tipoDeposito O tipo de depósito (DINHEIRO ou CHEQUE).
     * @param numeroCheque O número do cheque (somente se tipoDeposito for CHEQUE).
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     * @throws IllegalArgumentException Se o valor do depósito for negativo ou nulo,
     *                                  ou se o tipoDeposito for inválido.
     */
    @Transactional
    public void realizarDeposito(Long contaID, @NotNull BigDecimal valor, @NotNull TipoConta tipoConta, @NotNull TipoDeposito tipoDeposito, String numeroCheque)
            throws ContaNaoEncontradaException {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }

        if (tipoDeposito == null) {
            throw new IllegalArgumentException("O tipo de depósito deve ser especificado (DINHEIRO ou CHEQUE).");
        }
        
        if (tipoConta == null) {
            throw new IllegalArgumentException("O tipo de conta deve ser especificado.");
        }


        if (tipoDeposito == TipoDeposito.CHEQUE && (numeroCheque == null || numeroCheque.isEmpty())) {
            throw new IllegalArgumentException("O número do cheque deve ser especificado para depósitos em cheque.");
        }

        if (contaID == null) {
            throw new IllegalArgumentException("O ID da conta não pode ser nulo.");
        }

        
        if(tipoConta.equals(TipoConta.CONTA_CORRENTE)) {
            ContaCorrente contaCorrente = contaCorrenteRepository.findById(contaID);
            if (contaCorrente != null) {
                realizarDepositoContaCorrente(contaCorrente, valor, tipoDeposito, numeroCheque);
                return;
            }
        }

        else if(tipoConta.equals(TipoConta.CONTA_CONJUNTA)) {
            ContasConjuntas contasConjuntas = contasConjuntasRepository.findById(contaID);
            if (contasConjuntas != null) {
                realizarDepositoContasConjuntas(contasConjuntas, valor, tipoDeposito, numeroCheque);
                return;
            }
        }
        
        else if(tipoConta.equals(TipoConta.CONTA_POUPANCA))
        {
            Poupanca poupanca = poupancaRepository.findById(contaID);
            if (poupanca != null) {
                realizarDepositoPoupanca(poupanca, valor, tipoDeposito, numeroCheque);
                return;
            }
        }
        
        throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
    }
    
    /**
     * Realiza uma transferência entre duas contas.
     *
     * @param contaOrigemID O ID da conta de origem.
     * @param contaDestinoID O ID da conta de destino.
     * @param valor O valor a ser transferido.
     * @param tipoConta o tipo de conta da origem.
     * @throws ContaNaoEncontradaException Se uma das contas não for encontrada.
     * @throws IllegalArgumentException Se o valor da transferência for negativo ou nulo.
     * @throws SaldoInsuficienteException Se a conta de origem não tiver saldo suficiente para a transferência.
     */
    @Transactional
    public void realizarTransferencia(Long contaOrigemID, Long contaDestinoID, @NotNull BigDecimal valor, @NotNull TipoConta tipoContaOrigem, @NotNull TipoConta tipoContaDestino)
            throws ContaNaoEncontradaException {
    	
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        if (contaOrigemID == null || contaDestinoID == null) {
            throw new IllegalArgumentException("Os IDs da conta de origem e destino não podem ser nulos.");
        }
        
        if (tipoContaOrigem == null && tipoContaDestino == null) {
            throw new IllegalArgumentException("O tipo de conta deve ser especificado.");
        }
        
		if (tipoContaOrigem.equals(TipoConta.CONTA_CORRENTE)) {
			ContaCorrente contaCorrenteOrigem = contaCorrenteRepository.findById(contaOrigemID);
			ContaCorrente contaCorrenteDestino = contaCorrenteRepository.findById(contaDestinoID);

			if (contaCorrenteOrigem == null || contaCorrenteDestino == null) {
				throw new ContaNaoEncontradaException("Uma das contas não foi encontrada.");
			} else {
				realizarTransferenciaContaCorrente(contaOrigemID, contaDestinoID, valor, tipoContaDestino);
				return;
			}
		}
    
        else if(tipoContaOrigem.equals(TipoConta.CONTA_CONJUNTA)) {
        	ContasConjuntas contaConjuntaOrigem = contasConjuntasRepository.findById(contaOrigemID);
			ContasConjuntas contaConjuntaDestino = contasConjuntasRepository.findById(contaDestinoID);
			
			if (contaConjuntaOrigem == null || contaConjuntaDestino == null) {
				throw new ContaNaoEncontradaException("Uma das contas não foi encontrada.");
			} else {
				realizarTransferenciaContaConjunta(contaOrigemID, contaDestinoID, valor, tipoContaDestino);
				return;
			}
        }
        
        else if(tipoContaOrigem.equals(TipoConta.CONTA_POUPANCA)){
        	Poupanca contaPoupancaOrigem = poupancaRepository.findById(contaOrigemID);
        	Poupanca contaPoupancaDestino = poupancaRepository.findById(contaDestinoID);
			
			if (contaPoupancaOrigem == null || contaPoupancaDestino == null) {
				throw new ContaNaoEncontradaException("Uma das contas não foi encontrada.");
			} else {
				realizarTransferenciaPoupanca(contaOrigemID, contaDestinoID, valor, tipoContaDestino);
				return;
			}
        }
        
        throw new ContaNaoEncontradaException("Conta origem não encontrada com ID: " + contaOrigemID);
    }
    
    
    /**
     * Cria uma notificação de gasto excessivo.
     *
     * @param conta       A conta do cliente que realizou o saque.
     * @param valorSaque  O valor do saque que excede o limite.
     * @throws NotificacaoNaoEncontradaException Se houver um problema com o serviço de notificações.
     */
    private void criarNotificacaoGastoExcessivo(Contas conta, BigDecimal valorSaque) throws AlertaNaoEncontradoException {
        String mensagem = "Alerta: Saque de R$ " + valorSaque + " excede o limite da conta.";
        
        alertasGastosExcessivos.setClienteID(conta.getClienteID());
        alertasGastosExcessivos.setTipoAlerta(mensagem);

        try {
            alertasGastosExcessivosService.criarAlerta(alertasGastosExcessivos); 
        } catch (AlertaNaoEncontradoException e) {
            throw e;
        }
    }

    /**
     * Realiza o saque em uma conta corrente.
     *
     * @param contaCorrente A conta corrente.
     * @param valor O valor a ser sacado.
     * @throws SaqueExcedeLimiteException Se o saque exceder o limite da conta corrente.
     * @throws NotificacaoNaoEncontradaException Se houver um problema com o serviço de notificações.
     */
    private void realizarSaqueContaCorrente(ContaCorrente contaCorrente, BigDecimal valor) throws SaqueExcedeLimiteException, NotificacaoNaoEncontradaException {
        BigDecimal saldoConta = contaCorrente.getSaldo();
        BigDecimal limiteCredito = contaCorrente.getLimiteCredito();
        

        // Verifica se o saque excede o saldo e usa o limite de crédito
        if (saldoConta.compareTo(valor) < 0) {
            BigDecimal valorRestante = valor.subtract(saldoConta);
            saldoConta = BigDecimal.ZERO; // Esvazia o saldo
            limiteCredito = limiteCredito.subtract(valorRestante);
            contaCorrente.setLimiteCredito(limiteCredito);

            // Verifica se o saque excede o limite de crédito
            if (limiteCredito.compareTo(BigDecimal.ZERO) < 0) {
                throw new SaqueExcedeLimiteException("Saque excede o limite de crédito da conta corrente.");
            }
        } else {
            saldoConta = saldoConta.subtract(valor);
        }

        // Atualiza o saldo da conta
        contaCorrente.setSaldo(saldoConta);
        contaCorrenteRepository.persist(contaCorrente);

        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.SAQUE);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setContaID(contaCorrente.getcontaCorrenteID());
        transacao.setTipoConta(TipoConta.CONTA_CORRENTE);
        transacoesRepository.persist(transacao);

        notificacoes.setClienteID(contaCorrente.getClienteID());
        notificacoes.setDataHoraNotificacao(LocalDateTime.now());
        notificacoes.setEnviada(1);
        notificacoes.setMensagemNotificacao("O Cliente: " + contaCorrente.getClienteID() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());
        
        
        //notificacoesService.criarNotificacao(notificacoes);

    }


    /**
     * Realiza o saque em uma conta conjunta.
     *
     * @param contasConjuntas A conta conjunta.
     * @param valor O valor a ser sacado.
     * @throws SaqueExcedeSaldoException Se o saque exceder o saldo da conta conjunta.
     * @throws NotificacaoNaoEncontradaException Se houver um problema com o serviço de notificações.
     */
    private void realizarSaqueContasConjuntas(ContasConjuntas contasConjuntas, BigDecimal valor) throws SaqueExcedeSaldoException, NotificacaoNaoEncontradaException {
        BigDecimal saldoConta = contasConjuntas.getSaldo();

        // Verifica se o saque excede o saldo
        if (saldoConta.compareTo(valor) < 0) {
            throw new SaqueExcedeSaldoException("Saque excede o saldo da conta conjunta.");
        } else {
            saldoConta = saldoConta.subtract(valor);
        }

        // Atualiza o saldo da conta conjunta
        contasConjuntas.setSaldo(saldoConta);
        contasConjuntasRepository.persist(contasConjuntas);

        BigDecimal limiteSaque = alertasGastosExcessivos.getValorLimite();

        if (valor.compareTo(limiteSaque) > 0) {
            // O saque excede o limite, crie uma notificação de gasto excessivo
            criarNotificacaoGastoExcessivo(contasConjuntas, valor);
        }

        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.SAQUE);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setContaID(contasConjuntas.getContaConjuntaID());
        transacao.setTipoConta(TipoConta.CONTA_CONJUNTA);
        transacoesRepository.persist(transacao);

        notificacoes.setClienteID(contasConjuntas.getClienteID());
        notificacoes.setDataHoraNotificacao(LocalDateTime.now());
        notificacoes.setEnviada(1);
        notificacoes.setMensagemNotificacao("O Cliente: " + contasConjuntas.getClienteID().toString() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());

        //notificacoesService.criarNotificacao(notificacoes);
    }


    /**
     * Realiza o saque em uma conta poupança.
     *
     * @param poupanca A conta poupança.
     * @param valor O valor a ser sacado.
     * @throws SaqueExcedeSaldoException Se o saque exceder o saldo da conta poupança.
     * @throws NotificacaoNaoEncontradaException Se houver um problema com o serviço de notificações.
     */
    private void realizarSaquePoupanca(Poupanca poupanca, BigDecimal valor) throws SaqueExcedeSaldoException, NotificacaoNaoEncontradaException {
        BigDecimal saldoConta = poupanca.getSaldo();

        // Verifica se o saque excede o saldo
        if (saldoConta.compareTo(valor) < 0) {
            throw new SaqueExcedeSaldoException("Saque excede o saldo da conta poupança.");
        } else {
            saldoConta = saldoConta.subtract(valor);
        }

        // Atualiza o saldo da conta poupança
        poupanca.setSaldo(saldoConta);
        poupancaRepository.persist(poupanca);

        BigDecimal limiteSaque = alertasGastosExcessivos.getValorLimite();

        if (valor.compareTo(limiteSaque) > 0) {
            // O saque excede o limite, crie uma notificação de gasto excessivo
            criarNotificacaoGastoExcessivo(poupanca, valor);
        }

        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.SAQUE);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setContaID(poupanca.getPoupancaID());
        transacao.setTipoConta(TipoConta.CONTA_POUPANCA);
        transacoesRepository.persist(transacao);

        notificacoes.setClienteID(poupanca.getClienteID());
        notificacoes.setDataHoraNotificacao(LocalDateTime.now());
        notificacoes.setEnviada(1);
        notificacoes.setMensagemNotificacao("O Cliente: " + poupanca.getClienteID() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());

        //notificacoesService.criarNotificacao(notificacoes);
    }

    /**
     * Realiza um depósito em uma conta corrente.
     *
     * @param contaCorrente A conta corrente.
     * @param valor O valor a ser depositado.
     * @param tipoDeposito O tipo de depósito (DINHEIRO ou CHEQUE).
     * @param numeroCheque O número do cheque (somente se tipoDeposito for CHEQUE).
     */
    private void realizarDepositoContaCorrente(ContaCorrente contaCorrente, BigDecimal valor,
            TipoDeposito tipoDeposito, String numeroCheque) {
        BigDecimal saldoConta = contaCorrente.getSaldo();

        if (tipoDeposito == TipoDeposito.CHEQUE) {
            contaCorrente.setSaldo(contaCorrente.getSaldo().add(valor));
            
        } else if (tipoDeposito == TipoDeposito.DINHEIRO) {
            BigDecimal novoSaldo = saldoConta.add(valor);
            contaCorrente.setSaldo(novoSaldo);
        }

        // Persiste a conta corrente após o depósito
        contaCorrenteRepository.persist(contaCorrente);

        // Registre a transação de depósito
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setTipoConta(TipoConta.CONTA_CORRENTE);
        transacao.setNumeroCheque(tipoDeposito == TipoDeposito.CHEQUE ? numeroCheque : null);
        transacao.setContaID(contaCorrente.getcontaCorrenteID());

        // Persiste a transação
        transacoesRepository.persist(transacao);

        notificacoes.setMensagemNotificacao("O Cliente: " + contaCorrente.getClienteID() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());
    }

    /**
     * Realiza um depósito em uma conta conjunta.
     *
     * @param contaConjunta A conta conjunta.
     * @param valor O valor a ser depositado.
     * @param tipoDeposito O tipo de depósito (DINHEIRO ou CHEQUE).
     * @param numeroCheque O número do cheque (somente se tipoDeposito for CHEQUE).
     */
    private void realizarDepositoContasConjuntas(ContasConjuntas contasConjuntas, BigDecimal valor,
            TipoDeposito tipoDeposito, String numeroCheque) {
        BigDecimal saldoConta = contasConjuntas.getSaldo();

        if (tipoDeposito == TipoDeposito.CHEQUE) {
        	contasConjuntas.setSaldo(contasConjuntas.getSaldo().add(valor));
            
        } else if (tipoDeposito == TipoDeposito.DINHEIRO) {
            BigDecimal novoSaldo = saldoConta.add(valor);
            contasConjuntas.setSaldo(novoSaldo);
        }

        // Persiste a conta corrente após o depósito
        contasConjuntasRepository.persist(contasConjuntas);

        // Registre a transação de depósito
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setTipoConta(TipoConta.CONTA_CONJUNTA);
        transacao.setNumeroCheque(tipoDeposito == TipoDeposito.CHEQUE ? numeroCheque : null);
        transacao.setContaID(contasConjuntas.getContaConjuntaID());

        // Persiste a transação
        transacoesRepository.persist(transacao);

        notificacoes.setMensagemNotificacao("O Cliente: " + contasConjuntas.getClienteID().toString() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());
    }

    /**
     * Realiza um depósito em uma conta poupanca.
     *
     * @param contaConjunta A conta poupanca.
     * @param valor O valor a ser depositado.
     * @param tipoDeposito O tipo de depósito (DINHEIRO ou CHEQUE).
     * @param numeroCheque O número do cheque (somente se tipoDeposito for CHEQUE).
     */
    private void realizarDepositoPoupanca(Poupanca contaPoupanca, BigDecimal valor,
            TipoDeposito tipoDeposito, String numeroCheque) {
        BigDecimal saldoConta = contaPoupanca.getSaldo();

        if (tipoDeposito == TipoDeposito.CHEQUE) {
        	contaPoupanca.setSaldo(contaPoupanca.getSaldo().add(valor));
            
        } else if (tipoDeposito == TipoDeposito.DINHEIRO) {
            BigDecimal novoSaldo = saldoConta.add(valor);
            contaPoupanca.setSaldo(novoSaldo);
        }

        // Persiste a conta corrente após o depósito
        poupancaRepository.persist(contaPoupanca);

        // Registre a transação de depósito
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setTipoConta(TipoConta.CONTA_POUPANCA);
        transacao.setNumeroCheque(tipoDeposito == TipoDeposito.CHEQUE ? numeroCheque : null);
        transacao.setContaID(contaPoupanca.getPoupancaID());

        // Persiste a transação
        transacoesRepository.persist(transacao);

        notificacoes.setMensagemNotificacao("O Cliente: " + contaPoupanca.getClienteID().toString() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());
    }
    
    /**
     * Realiza uma transferência entre duas contas correntes.
     *
     * @param contaOrigemID O ID da conta de origem.
     * @param contaDestinoID O ID da conta de destino.
     * @param valor O valor a ser transferido.
     * @throws ContaNaoEncontradaException Se uma das contas não for encontrada.
     * @throws IllegalArgumentException Se o valor da transferência for negativo ou nulo.
     * @throws SaldoInsuficienteException Se a conta de origem não tiver saldo suficiente para a transferência.
     */
    @Transactional
    public void realizarTransferenciaContaCorrente(Long contaOrigemID, Long contaDestinoID, @NotNull BigDecimal valor, @NotNull TipoConta tipoContaDestino)
            throws ContaNaoEncontradaException, IllegalArgumentException, SaldoInsuficienteException {

        ContaCorrente contaOrigem = contaCorrenteRepository.findById(contaOrigemID);
        
        BigDecimal saldoOrigem = contaOrigem.getSaldo();
        BigDecimal limiteCreditoOrigem = contaOrigem.getLimiteCredito();

        // Verifica se a transferência excede o saldo e o limite de crédito da conta de origem
        if (valor.compareTo(saldoOrigem.add(limiteCreditoOrigem)) > 0) {
            throw new SaldoInsuficienteException("Saldo e limite de crédito insuficientes na conta de origem.");
        }

        BigDecimal novoSaldoOrigem = saldoOrigem.subtract(valor);
        contaOrigem.setSaldo(novoSaldoOrigem);
        contaCorrenteRepository.persist(contaOrigem);
        
        if(tipoContaDestino.equals(TipoConta.CONTA_CONJUNTA)) {
        	ContasConjuntas contaDestino = contasConjuntasRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	contasConjuntasRepository.persist(contaDestino);
        }
        else if(tipoContaDestino.equals(TipoConta.CONTA_POUPANCA)) {
        	Poupanca contaDestino = poupancaRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	poupancaRepository.persist(contaDestino);
        }
        else if(tipoContaDestino.equals(TipoConta.CONTA_CORRENTE)) {
        	ContaCorrente contaDestino = contaCorrenteRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	contaCorrenteRepository.persist(contaDestino);
        }

        // Registre a transação de depósito
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setTipoConta(TipoConta.CONTA_CORRENTE);
        transacao.setContaID(contaOrigem.getcontaCorrenteID());

        // Persiste a transação
        transacoesRepository.persist(transacao);

        notificacoes.setMensagemNotificacao("O Cliente: " + contaOrigem.getClienteID().toString() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());
    
    }

    /**
     * Realiza uma transferência entre duas contas conjuntas.
     *
     * @param contaOrigemID O ID da conta de origem.
     * @param contaDestinoID O ID da conta de destino.
     * @param valor O valor a ser transferido.
     * @throws ContaNaoEncontradaException Se uma das contas não for encontrada.
     * @throws IllegalArgumentException Se o valor da transferência for negativo ou nulo.
     * @throws SaldoInsuficienteException Se a conta de origem não tiver saldo suficiente para a transferência.
     */
    @Transactional
    public void realizarTransferenciaContaConjunta(Long contaOrigemID, Long contaDestinoID, @NotNull BigDecimal valor, @NotNull TipoConta tipoContaDestino)
            throws ContaNaoEncontradaException, IllegalArgumentException, SaldoInsuficienteException {
        ContasConjuntas contasConjuntasOrigem = contasConjuntasRepository.findById(contaOrigemID);

        BigDecimal saldoOrigem = contasConjuntasOrigem.getSaldo();
        
        // Verifica se a transferência excede o saldo
        if (valor.compareTo(saldoOrigem) > 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta de origem.");
        }

        BigDecimal novoSaldoOrigem = saldoOrigem.subtract(valor);
        contasConjuntasOrigem.setSaldo(novoSaldoOrigem);
        contasConjuntasRepository.persist(contasConjuntasOrigem);
        
        if(tipoContaDestino.equals(TipoConta.CONTA_CONJUNTA)) {
        	ContasConjuntas contaDestino = contasConjuntasRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	contasConjuntasRepository.persist(contaDestino);
        }
        else if(tipoContaDestino.equals(TipoConta.CONTA_POUPANCA)) {
        	Poupanca contaDestino = poupancaRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	poupancaRepository.persist(contaDestino);
        }
        else if(tipoContaDestino.equals(TipoConta.CONTA_CORRENTE)) {
        	ContaCorrente contaDestino = contaCorrenteRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	contaCorrenteRepository.persist(contaDestino);
        }

        // Registre a transação de depósito
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setTipoConta(TipoConta.CONTA_CONJUNTA);
        transacao.setContaID(contasConjuntasOrigem.getContaConjuntaID());

        // Persiste a transação
        transacoesRepository.persist(transacao);

        notificacoes.setMensagemNotificacao("O Cliente: " + contasConjuntasOrigem.getClienteID().toString() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());
    }

    /**
     * Realiza uma transferência entre uma conta poupança e outra conta.
     *
     * @param contaOrigemID O ID da conta de origem (poupança).
     * @param contaDestinoID O ID da conta de destino.
     * @param valor O valor a ser transferido.
     * @throws ContaNaoEncontradaException Se uma das contas não for encontrada.
     * @throws IllegalArgumentException Se o valor da transferência for negativo ou nulo.
     * @throws SaldoInsuficienteException Se a conta de origem não tiver saldo suficiente para a transferência.
     */
    @Transactional
    public void realizarTransferenciaPoupanca(Long contaOrigemID, Long contaDestinoID, @NotNull BigDecimal valor, @NotNull TipoConta tipoContaDestino)
            throws ContaNaoEncontradaException, IllegalArgumentException, SaldoInsuficienteException {
    	
        Poupanca poupancaOrigem = poupancaRepository.findById(contaOrigemID);
        BigDecimal saldoOrigem = poupancaOrigem.getSaldo();
        
        // Verifica se a transferência excede o saldo
        if (valor.compareTo(saldoOrigem) > 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente na conta de origem.");
        }

        BigDecimal novoSaldoOrigem = saldoOrigem.subtract(valor);
        poupancaOrigem.setSaldo(novoSaldoOrigem);
        poupancaRepository.persist(poupancaOrigem);
        
        if(tipoContaDestino.equals(TipoConta.CONTA_CONJUNTA)) {
        	ContasConjuntas contaDestino = contasConjuntasRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	contasConjuntasRepository.persist(contaDestino);
        }
        else if(tipoContaDestino.equals(TipoConta.CONTA_POUPANCA)) {
        	Poupanca contaDestino = poupancaRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	poupancaRepository.persist(contaDestino);
        }
        else if(tipoContaDestino.equals(TipoConta.CONTA_CORRENTE)) {
        	ContaCorrente contaDestino = contaCorrenteRepository.findById(contaDestinoID);
        	BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        	contaDestino.setSaldo(novoSaldoDestino);
        	contaCorrenteRepository.persist(contaDestino);
        }

        // Registre a transação de depósito
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setTipoConta(TipoConta.CONTA_POUPANCA);
        transacao.setContaID(poupancaOrigem.getPoupancaID());

        // Persiste a transação
        transacoesRepository.persist(transacao);

        notificacoes.setMensagemNotificacao("O Cliente: " + poupancaOrigem.getClienteID().toString() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + transacao.getDataHoraTransacao() + " no valor de: R$ " + transacao.getValor());
    }

}
package acc.br.service;

import acc.br.exception.AlertaNaoEncontradoException;
import acc.br.exception.ContaNaoEncontradaException;
import acc.br.exception.NotificacaoNaoEncontradaException;
import acc.br.exception.TransacoesNaoEncontradaException;
import acc.br.model.AlertasGastosExcessivos;
import acc.br.model.ContaCorrente;
import acc.br.model.Contas;
import acc.br.model.Notificacoes;
import acc.br.model.Transacoes;
import acc.br.repository.ContasRepository;
import acc.br.repository.NotificacoesRepository;
import acc.br.repository.TransacoesRepository;
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
    ContasRepository contasRepository;
    
    @Inject
    AlertasGastosExcessivos alertasGastosExcessivos;

    @Inject
    AlertasGastosExcessivosService alertasGastosExcessivosService;
    
    @Inject
    Notificacoes notificacoes;
    
    @Inject
    NotificacoesRepository notificacoesRepository;
    
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
     * Realiza um saque em uma conta e verifica o limite permitido.
     *
     * @param transacao A transação de saque a ser realizada.
     * @throws SaqueExcedeLimiteException Se o saque exceder o limite da conta.
     * @throws NotificacaoNaoEncontradaException Se houver um problema com o serviço de notificações.
     */
    @Transactional
    public void realizarSaque(Long contaID, @NotNull BigDecimal valor) throws NotificacaoNaoEncontradaException {
    	
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }

        Contas conta = contasRepository.findById(contaID);
 
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
        }

        BigDecimal saldoConta = conta.getSaldo();

        // Verifica se a conta é do tipo ContaCorrente
        if (conta instanceof ContaCorrente) {
            ContaCorrente contaCorrente = (ContaCorrente) conta;
            BigDecimal limiteCredito = contaCorrente.getLimiteCredito();

            if (saldoConta.compareTo(valor) < 0 && saldoConta.add(limiteCredito).compareTo(valor) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
            }

            // Verifica se o saque excede o saldo e usa o limite de crédito
            if (saldoConta.compareTo(valor) < 0) {
                BigDecimal valorRestante = valor.subtract(saldoConta);
                saldoConta = BigDecimal.ZERO; // Esvazia o saldo
                contaCorrente.setLimiteCredito(limiteCredito.subtract(valorRestante));
            } else {
                saldoConta = saldoConta.subtract(valor);
            }

            // Atualiza o saldo da conta
            contaCorrente.setSaldo(saldoConta);
            contasRepository.persist(contaCorrente);
        } else {
            if (saldoConta.compareTo(valor) < 0) {
                throw new IllegalArgumentException("Saldo insuficiente para realizar o saque.");
            }

            saldoConta = saldoConta.subtract(valor);
            conta.setSaldo(saldoConta);
            contasRepository.persist(conta);
        }
        
        BigDecimal limiteSaque = alertasGastosExcessivos.getValorLimite();

        if (valor.compareTo(limiteSaque) > 0) {
            // O saque excede o limite, crie uma notificação de gasto excessivo
        	criarNotificacaoGastoExcessivo(conta, valor);
        }

        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.SAQUE);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setContaID(contaID); 
        transacoesRepository.persist(transacao);
        
        notificacoes.setClienteID(conta.getClienteID());
        notificacoes.setDataHoraNotificacao(LocalDateTime.now());
        notificacoes.setEnviada(1);
        notificacoes.setMensagemNotificacao("O Cliente: " + notificacoes.getClienteID() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + notificacoes.getDataHoraNotificacao() + " no valor de: R$ " + transacao.getValor());
        
        notificacoesService.criarNotificacao(notificacoes);
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
     * Realiza um depósito em uma conta.
     *
     * @param contaID O ID da conta a ser depositado.
     * @param valor   O valor a ser depositado.
     * @throws ContaNaoEncontradaException Se a conta não for encontrada.
     * @throws IllegalArgumentException   Se o valor do depósito for negativo.
     */
    @Transactional
    public void depositar(Long contaID, @NotNull BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }

        Contas conta = contasRepository.findById(contaID);
        if (conta == null) {
            throw new ContaNaoEncontradaException("Conta não encontrada com ID: " + contaID);
        }

        BigDecimal novoSaldo = conta.getSaldo().add(valor);
        conta.setSaldo(novoSaldo);
        
        contasRepository.persist(conta);
        
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setContaID(contaID); 

        Transacoes transacaoGerenciado = entityManager.merge(transacao); // Mescla a entidade no contexto de persistência
        entityManager.persist(transacaoGerenciado); // Persiste a entidade
                
        notificacoes.setClienteID(conta.getClienteID());
        notificacoes.setDataHoraNotificacao(LocalDateTime.now());
        notificacoes.setEnviada(1);
        notificacoes.setMensagemNotificacao("O Cliente: " + notificacoes.getClienteID() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + notificacoes.getDataHoraNotificacao() + " no valor de: R$ " + transacao.getValor());
        
        //notificacoesService.criarNotificacao(notificacoes);
        //Teste gravando notificacão sem chamar NotificacaoService
        //Não funcionou
        // Notificacoes notificacoesGerenciado = entityManager.merge(notificacoes); // Mescla a entidade no contexto de persist�ncia
        // entityManager.persist(notificacoesGerenciado); // Persiste a entidade
        
    }

    /**
     * Realiza uma transferência entre duas contas.
     *
     * @param contaOrigemID O ID da conta de origem.
     * @param contaDestinoID O ID da conta de destino.
     * @param valor O valor a ser transferido.
     * @throws ContaNaoEncontradaException Se uma das contas não for encontrada.
     * @throws IllegalArgumentException Se o valor da transferência for negativo ou maior que o saldo disponível na conta de origem.
     */
    @Transactional
    public void transferir(Long contaOrigemID, Long contaDestinoID, @NotNull BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        Contas contaOrigem = contasRepository.findById(contaOrigemID);
        Contas contaDestino = contasRepository.findById(contaDestinoID);

        if (contaOrigem == null || contaDestino == null) {
            throw new ContaNaoEncontradaException("Uma das contas não foi encontrada.");
        }

        if (valor.compareTo(contaOrigem.getSaldo()) > 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência.");
        }
        
        BigDecimal novoSaldoOrigem = contaOrigem.getSaldo().subtract(valor);
        BigDecimal novoSaldoDestino = contaDestino.getSaldo().add(valor);
        
        contaOrigem.setSaldo(novoSaldoOrigem);
        contaDestino.setSaldo(novoSaldoDestino);
        
        contasRepository.persist(contaOrigem);
        contasRepository.persist(contaDestino);
        
        Transacoes transacao = new Transacoes();
        transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
        transacao.setDataHoraTransacao(LocalDate.now());
        transacao.setValor(valor);
        transacao.setContaID(contaOrigemID); 
        transacao.setContaDestinoID(contaDestinoID);

        Transacoes transacaoGerenciado = entityManager.merge(transacao); // Mescla a entidade no contexto de persistência
        entityManager.persist(transacaoGerenciado); // Persiste a entidade
                
        notificacoes.setClienteID(contaOrigemID);
        notificacoes.setDataHoraNotificacao(LocalDateTime.now());
        notificacoes.setEnviada(1);
        notificacoes.setMensagemNotificacao("O Cliente: " + notificacoes.getClienteID() + " realizou um Tipo de Transação: " + transacao.getTipoTransacao() + " na data e hora: " + notificacoes.getDataHoraNotificacao() + " no valor de: R$ " + transacao.getValor() + " para a conta destino: " + transacao.getContaDestinoID());
        
        //Eliminado pois estava impedindo a transação de persistir.
        //notificacoesService.criarNotificacao(notificacoes);
    }
}
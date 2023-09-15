package acc.br.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;

import acc.br.exception.ContaCorrenteNaoEncontradaException;
import acc.br.exception.ContaExistenteException;
import acc.br.exception.ContaNaoEncontradaException;
import acc.br.exception.PoupancaNaoEncontradaException;
import acc.br.interfaces.Conta;
import acc.br.model.ContaCorrente;
import acc.br.model.Contas;
import acc.br.repository.ContaCorrenteRepository;
import acc.br.repository.ContasRepository;
import acc.br.util.TipoConta;

@ApplicationScoped 
public class ContaCorrenteService extends ContasService{
	
	@Inject
	ContaCorrenteRepository contaCorrenteRepository;

	public ContaCorrenteService() {
	    // Construtor sem argumentos
	}
	
	public ContaCorrenteService(ContasRepository contasRepository) {
		super(contasRepository);
	}
	
    /**
     * Cria uma nova conta Corrente.
     *
     * @param conta A conta corrente a ser criada.
     */
    @Transactional
    @Override
    public <T extends Conta> T criarConta(@Valid T conta) throws ContaExistenteException {
        if (contasRepository.findById(conta.getContaID()) != null) {
            throw new ContaExistenteException("Conta corrente já existe com ID: " + conta.getContaID());
        }
        conta.setTipoConta(TipoConta.CONTA_CORRENTE.toString());
        
        super.criarConta(conta);
        
        return conta;
    }

    /**
     * Atualiza uma conta corrente existente.
     *
     * @param id       O ID da conta corrente a ser atualizada.
     * @param poupanca A conta corrente com as atualizações.
     * @throws PoupancaNaoEncontradaException Se a conta corrente não for encontrada.
     */
    @Transactional
    @Override
    public <T extends Conta> T atualizarConta(Long contaID, @Valid T conta) throws ContaNaoEncontradaException{
        Contas contaCorrenteExistente = contasRepository.findById(contaID);
        if (contaCorrenteExistente == null) {
            throw new ContaCorrenteNaoEncontradaException("Conta corrente não encontrada com ID: " + contaID);
        }
        
        conta.setTipoConta(TipoConta.CONTA_CORRENTE.toString());

        super.atualizarConta(contaID, conta);
        
        return conta;
    }

    /**
     * Lista todas as contas poupança.
     *
     * @return Uma lista de todas as contas poupança.
     */
    public List<ContaCorrente> listarContasCorrentes() {
        return contaCorrenteRepository.listAll();
    }

}

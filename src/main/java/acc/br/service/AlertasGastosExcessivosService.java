package acc.br.service;

import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import acc.br.exception.AlertaNaoEncontradoException;
import acc.br.exception.ClienteNaoEncontradoException;
import acc.br.model.AlertasGastosExcessivos;
import acc.br.model.Clientes;
import acc.br.repository.AlertasGastosExcessivosRepository;
import acc.br.repository.ClientesRepository;

/**
 * Serviço para gerenciar operações relacionadas a alertas de gastos excessivos.
 */
@ApplicationScoped
public class AlertasGastosExcessivosService {

    @Inject
    AlertasGastosExcessivosRepository repository;
    
    @Inject
    ClientesRepository clientesRepository;

    /**
     * Cria um novo alerta de gastos excessivos.
     *
     * @param alerta O alerta a ser criado
     */
    @Transactional
    public void criarAlerta(@Valid @NotNull AlertasGastosExcessivos alerta) {
        validarCliente(alerta.getClienteID());
        repository.persist(alerta);
    }

    /**
     * Atualiza as informações de um alerta existente.
     *
     * @param alerta O alerta a ser atualizado
     */
    @Transactional
    public void atualizarAlerta(@Valid @NotNull AlertasGastosExcessivos alerta) {
        AlertasGastosExcessivos alertaExistente = repository.findById(alerta.getAlertaID());
        if (alertaExistente == null) {
            throw new AlertaNaoEncontradoException("Alerta não encontrado.");
        }

        validarCliente(alerta.getClienteID()); 

        alertaExistente.setTipoAlerta(alerta.getTipoAlerta());
        alertaExistente.setValorLimite(alerta.getValorLimite());
        alertaExistente.setClienteID(alerta.getClienteID());
        alertaExistente.setAtivo(alerta.getAtivo());

        repository.persist(alertaExistente); 
    }

    /**
     * Deleta um alerta.
     *
     * @param alertaId ID do alerta a ser deletado
     */
    @Transactional
    public void deletarAlerta(@NotNull Long alertaId) {
        AlertasGastosExcessivos alerta = repository.findById(alertaId);
        if (alerta == null) {
            throw new AlertaNaoEncontradoException("Alerta não encontrado.");
        }

        repository.delete(alerta);
    }

    /**
     * Lista todos os alertas de um cliente.
     *
     * @param clienteId ID do cliente
     * @return Lista de alertas do cliente
     */
    public List<AlertasGastosExcessivos> listarAlertasPorCliente(@NotNull Long clienteId) {
        return repository.find("cliente.id", Sort.by("tipoAlerta"), clienteId).list();
    }

    /**
     * Busca um alerta por ID.
     *
     * @param alertaId ID do alerta
     * @return O alerta encontrado ou null se não encontrado
     */
    public AlertasGastosExcessivos buscarAlertaPorId(@NotNull Long alertaId) {
        return repository.findById(alertaId);
    }


    /**
     * Valida a existência e status de um cliente com base no seu código.
     *
     * @param codigoCliente O código do cliente a ser validado.
     * @throws ClienteNaoEncontradoException Se o cliente não for encontrado.
     */
    @Transactional
    public void validarCliente(@NotNull Long codigoCliente) {
        if (codigoCliente == null) {
            throw new IllegalArgumentException("Código de cliente inválido.");
        }

        Clientes cliente = clientesRepository.findById(codigoCliente);

        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado.");
        }
    }
}

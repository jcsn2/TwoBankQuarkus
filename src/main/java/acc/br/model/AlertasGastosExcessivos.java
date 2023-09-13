package acc.br.model;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Representa um alerta de gastos excessivos para um cliente.
 */
@Entity
@Table(name = "AlertasGastosExcessivos")
@ApplicationScoped
public class AlertasGastosExcessivos extends PanacheEntityBase {

    /**
     * ID único do alerta.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlertaID")
    private Long alertaID;

    /**
     * Tipo de alerta (por exemplo, "Limite de Gastos").
     */
    @Size(max = 50, message = "{quarkus.hibernate-validator.message.size.alertasGastosExcessivos.tipoAlerta}")
    @Column(name = "TipoAlerta")
    private String tipoAlerta;

    /**
     * Valor limite para o alerta.
     */
    @DecimalMax(value = "9999999.99", message = "{quarkus.hibernate-validator.message.decimalMax.alertasGastosExcessivos.valorLimite}")
    @Column(name = "ValorLimite")
    private BigDecimal valorLimite;

    /**
     * ID do cliente associado ao alerta.
     */
    @Column(name = "ClienteID")
    private Long clienteID;

    /**
     * Indica se o alerta está ativo (1 para ativo, 0 para inativo).
     */
    @Column(name = "Ativo")
    private Integer ativo;

    /**
     * Obtém o ID único do alerta.
     *
     * @return O ID do alerta.
     */
    public Long getAlertaID() {
        return alertaID;
    }

    /**
     * Define o ID único do alerta.
     *
     * @param alertaID O ID do alerta.
     */
    public void setAlertaID(Long alertaID) {
        this.alertaID = alertaID;
    }

    /**
     * Obtém o tipo de alerta.
     *
     * @return O tipo de alerta.
     */
    public String getTipoAlerta() {
        return tipoAlerta;
    }

    /**
     * Define o tipo de alerta.
     *
     * @param tipoAlerta O tipo de alerta.
     */
    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    /**
     * Obtém o valor limite para o alerta.
     *
     * @return O valor limite para o alerta.
     */
    public BigDecimal getValorLimite() {
        return valorLimite;
    }

    /**
     * Define o valor limite para o alerta.
     *
     * @param valorLimite O valor limite para o alerta.
     */
    public void setValorLimite(BigDecimal valorLimite) {
        this.valorLimite = valorLimite;
    }

    /**
     * Obtém o ID do cliente associado ao alerta.
     *
     * @return O ID do cliente associado ao alerta.
     */
    public Long getClienteID() {
        return clienteID;
    }

    /**
     * Define o ID do cliente associado ao alerta.
     *
     * @param clienteID O ID do cliente associado ao alerta.
     */
    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    /**
     * Verifica se o alerta está ativo.
     *
     * @return true se o alerta estiver ativo, false caso contrário.
     */
    public Integer getAtivo() {
        return ativo;
    }

    /**
     * Define se o alerta está ativo.
     *
     * @param ativo 1 para ativo, 0 para inativo.
     */
    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }
}



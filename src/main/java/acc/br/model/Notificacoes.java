package acc.br.model;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Classe que representa as notificações no sistema.
 */
@Entity
@Table(name = "Notificacoes")
@ApplicationScoped
public class Notificacoes extends PanacheEntityBase {

    /**
     * ID único da notificação.
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificacaoID")
    private Long notificacaoID;

    /**
     * Mensagem da notificação.
     */
    @Size(max = 255, message = "{quarkus.hibernate-validator.message.size.notificacoes.mensagemNotificacao}")
    @Column(name = "MensagemNotificacao")
    private String mensagemNotificacao;

    /**
     * Data e hora da notificação.
     */
    @PastOrPresent(message = "{quarkus.hibernate-validator.message.pastOrPresent.notificacoes.dataHoraNotificacao}")
    @Column(name = "DataHoraNotificacao")
    private LocalDateTime dataHoraNotificacao;

    /**
     * ID do cliente associado à notificação.
     */
    @Column(name = "ClienteID")
    private Long clienteID;

    /**
     * Flag que indica se a notificação foi enviada.
     */
    @Column(name = "Enviada")
    private Integer enviada;

    // Getters e setters

    /**
     * Obtém o ID da notificação.
     *
     * @return O ID da notificação.
     */
    public Long getNotificacaoID() {
        return notificacaoID;
    }

    /**
     * Define o ID da notificação.
     *
     * @param notificacaoID O ID da notificação.
     */
    public void setNotificacaoID(Long notificacaoID) {
        this.notificacaoID = notificacaoID;
    }

    /**
     * Obtém a mensagem da notificação.
     *
     * @return A mensagem da notificação.
     */
    public String getMensagemNotificacao() {
        return mensagemNotificacao;
    }

    /**
     * Define a mensagem da notificação.
     *
     * @param mensagemNotificacao A mensagem da notificação.
     */
    public void setMensagemNotificacao(String mensagemNotificacao) {
        this.mensagemNotificacao = mensagemNotificacao;
    }

    /**
     * Obtém a data e hora da notificação.
     *
     * @return A data e hora da notificação.
     */
    public LocalDateTime getDataHoraNotificacao() {
        return dataHoraNotificacao;
    }

    /**
     * Define a data e hora da notificação.
     *
     * @param dataHoraNotificacao A data e hora da notificação.
     */
    public void setDataHoraNotificacao(LocalDateTime dataHoraNotificacao) {
        this.dataHoraNotificacao = dataHoraNotificacao;
    }

    /**
     * Obtém o ID do cliente associado à notificação.
     *
     * @return O ID do cliente associado à notificação.
     */
    public Long getClienteID() {
        return clienteID;
    }

    /**
     * Define o ID do cliente associado à notificação.
     *
     * @param clienteID O ID do cliente associado à notificação.
     */
    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    /**
     * Obtém a flag que indica se a notificação foi enviada.
     *
     * @return A flag que indica se a notificação foi enviada.
     */
    public Integer getEnviada() {
        return enviada;
    }

    /**
     * Define a flag que indica se a notificação foi enviada.
     *
     * @param enviada A flag que indica se a notificação foi enviada.
     */
    public void setEnviada(Integer enviada) {
        this.enviada = enviada;
    }
}
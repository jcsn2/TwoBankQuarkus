package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.time.LocalDateTime;

@Entity
@Table(name = "AuditoriaBancoDados") 
public class AuditoriaBancoDados extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "O nome da entidade não pode estar em branco")
    @Column(name = "NomeEntidade", nullable = false)
    private String nomeEntidade;

    @NotBlank(message = "A ação não pode estar em branco")
    @Column(name = "Acao", nullable = false)
    private String acao;

    @Column(name = "DataHora")
    private LocalDateTime dataHora;

    // Getters e setters
    
    /**
     * Obtém o ID da auditoria.
     *
     * @return O ID da auditoria.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o ID da auditoria.
     *
     * @param id O ID da auditoria.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome da entidade associada à auditoria.
     *
     * @return O nome da entidade.
     */
    public String getNomeEntidade() {
        return nomeEntidade;
    }

    /**
     * Define o nome da entidade associada à auditoria.
     *
     * @param nomeEntidade O nome da entidade.
     */
    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    /**
     * Obtém a ação realizada na auditoria (por exemplo, "inserção", "atualização", "exclusão").
     *
     * @return A ação da auditoria.
     */
    public String getAcao() {
        return acao;
    }

    /**
     * Define a ação realizada na auditoria.
     *
     * @param acao A ação da auditoria.
     */
    public void setAcao(String acao) {
        this.acao = acao;
    }

    /**
     * Obtém a data e hora em que a auditoria foi registrada.
     *
     * @return A data e hora da auditoria.
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Define a data e hora da auditoria.
     *
     * @param dataHora A data e hora da auditoria.
     */
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}


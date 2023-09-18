package acc.br.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Classe que representa os parâmetros de configuração no sistema.
 */
@Entity
@Table(name = "ParametrosConfiguracao")
public class ParametrosConfiguracao extends PanacheEntityBase {

    /**
     * ID único do parâmetro de configuração.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ParametroID")
    private Long parametroID;

    /**
     * Nome do parâmetro de configuração.
     */
    @NotBlank(message = "{quarkus.hibernate-validator.message.notblank.parametrosConfiguracao.nomeParametro}")
    @Size(max = 100, message = "{quarkus.hibernate-validator.message.size.parametrosConfiguracao.nomeParametro}")
    @Column(name = "NomeParametro")
    private String nomeParametro;

    /**
     * Valor do parâmetro de configuração.
     */
    @NotBlank(message = "{quarkus.hibernate-validator.message.notblank.parametrosConfiguracao.valorParametro}")
    @Size(max = 100, message = "{quarkus.hibernate-validator.message.size.parametrosConfiguracao.valorParametro}")
    @Column(name = "ValorParametro")
    private String valorParametro;

    /**
     * Descrição do parâmetro de configuração.
     */
    @Size(max = 255, message = "{quarkus.hibernate-validator.message.size.parametrosConfiguracao.descricaoParametro}")
    @Column(name = "DescricaoParametro")
    private String descricaoParametro;

    // Getters e setters

    /**
     * Obtém o ID do parâmetro de configuração.
     *
     * @return O ID do parâmetro de configuração.
     */
    public Long getParametroID() {
        return parametroID;
    }

    /**
     * Define o ID do parâmetro de configuração.
     *
     * @param parametroID O ID do parâmetro de configuração.
     */
    public void setParametroID(Long parametroID) {
        this.parametroID = parametroID;
    }

    /**
     * Obtém o nome do parâmetro de configuração.
     *
     * @return O nome do parâmetro de configuração.
     */
    public String getNomeParametro() {
        return nomeParametro;
    }

    /**
     * Define o nome do parâmetro de configuração.
     *
     * @param nomeParametro O nome do parâmetro de configuração.
     */
    public void setNomeParametro(String nomeParametro) {
        this.nomeParametro = nomeParametro;
    }

    /**
     * Obtém o valor do parâmetro de configuração.
     *
     * @return O valor do parâmetro de configuração.
     */
    public String getValorParametro() {
        return valorParametro;
    }

    /**
     * Define o valor do parâmetro de configuração.
     *
     * @param valorParametro O valor do parâmetro de configuração.
     */
    public void setValorParametro(String valorParametro) {
        this.valorParametro = valorParametro;
    }

    /**
     * Obtém a descrição do parâmetro de configuração.
     *
     * @return A descrição do parâmetro de configuração.
     */
    public String getDescricaoParametro() {
        return descricaoParametro;
    }

    /**
     * Define a descrição do parâmetro de configuração.
     *
     * @param descricaoParametro A descrição do parâmetro de configuração.
     */
    public void setDescricaoParametro(String descricaoParametro) {
        this.descricaoParametro = descricaoParametro;
    }
}
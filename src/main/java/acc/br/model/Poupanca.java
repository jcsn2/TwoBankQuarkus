package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Classe que representa uma conta poupança no sistema.
 */
@Entity
@Table(name = "Poupanca")
@DiscriminatorValue("Conta Poupança")
public class Poupanca extends Contas {

    /**
     * Data de aniversário da conta poupança.
     */
    @Size(max = 10, message = "{quarkus.hibernate-validator.message.size.poupanca.dataAniversario}")
    @Column(name = "DataAniversario")
    private String dataAniversario;

    // Getters e setters

    /**
     * Obtém a data de aniversário da conta poupança.
     *
     * @return A data de aniversário da conta poupança.
     */
    public String getDataAniversario() {
        return dataAniversario;
    }

    /**
     * Define a data de aniversário da conta poupança.
     *
     * @param dataAniversario A data de aniversário da conta poupança.
     */
    public void setDataAniversario(String dataAniversario) {
        this.dataAniversario = dataAniversario;
    }
}
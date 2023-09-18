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
     * ID único da conta.
     */
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poupancaID")
    private Long poupancaID;

    /**
     * Data de aniversário da conta poupança.
     */
    @Size(max = 10, message = "{quarkus.hibernate-validator.message.size.poupanca.dataAniversario}")
    @Column(name = "DataAniversario")
    private String dataAniversario;

    // Getters e setters

    /**
     * Obtém o ID da conta.
     *
     * @return O ID da conta.
     */
    public Long getPoupancaID() {
        return poupancaID;
    }

    /**
     * Define o ID da conta.
     *
     * @param contaID O ID da conta.
     */
    public void setPoupancaID(Long poupancaID) {
        this.poupancaID = poupancaID;
    }
    
    
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
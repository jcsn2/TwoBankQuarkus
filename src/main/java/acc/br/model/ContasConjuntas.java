package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Classe que representa uma conta conjunta no sistema.
 */
@Entity
@Table(name = "ContasConjuntas")
@DiscriminatorValue("Conta Conjunta")
public class ContasConjuntas extends Contas {

	/**
	 * Lista de titulares da conta conjunta.
	 */
	@Size(max = 2000, message = "{quarkus.hibernate-validator.message.size.contasConjuntas.titulares}")
	@Column(name = "Titulares")
	private String titulares;

	// Getters e setters

	/**
	 * Obtém a lista de titulares da conta conjunta.
	 *
	 * @return A lista de titulares da conta conjunta.
	 */
	public String getTitulares() {
		return titulares;
	}

	/**
	 * Define a lista de titulares da conta conjunta.
	 *
	 * @param titulares A lista de titulares da conta conjunta.
	 */
	public void setTitulares(String titulares) {
		this.titulares = titulares;
	}

}
package acc.br.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

/**
 * Classe que representa um cliente no sistema.
 */
@Entity
@Table(name = "Clientes")
public class Clientes extends PanacheEntityBase {

    /**
     * ID único do cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClienteID")
    private Long clienteID;

    /**
     * Nome do cliente.
     */
    @NotBlank(message = "{quarkus.hibernate-validator.message.not-blank.cliente.nome}")
    @Size(max = 100, message = "{quarkus.hibernate-validator.message.size.cliente.nome}")
    @Column(name = "Nome", nullable = false)
    private String nome;

    /**
     * Endereço de e-mail do cliente.
     */
    @NotBlank(message = "{quarkus.hibernate-validator.message.not-blank.cliente.email}")
    @Email(message = "{quarkus.hibernate-validator.message.email.cliente.email}")
    @Size(max = 100, message = "{quarkus.hibernate-validator.message.size.cliente.email}")
    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    /**
     * Senha do cliente.
     */
    @NotBlank(message = "{quarkus.hibernate-validator.message.not-blank.cliente.senha}")
    @Size(min = 6, max = 255, message = "{quarkus.hibernate-validator.message.size.cliente.senha}")
    @Column(name = "Senha", nullable = false)
    private String senha;

    /**
     * Data de nascimento do cliente.
     */
    @Past(message = "{quarkus.hibernate-validator.message.past.cliente.dataNascimento}")
    @Column(name = "DataNascimento")
    private LocalDate dataNascimento;

    /**
     * CPF do cliente.
     */
    @NotBlank(message = "{quarkus.hibernate-validator.message.not-blank.cliente.cpf}")
    @Size(min = 11, max = 11, message = "{quarkus.hibernate-validator.message.size.cliente.cpf}")
    @Column(name = "CPF", nullable = false, unique = true)
    private String cpf;

    /**
     * Endereço do cliente.
     */
    @Size(max = 255, message = "{quarkus.hibernate-validator.message.size.cliente.endereco}")
    @Column(name = "Endereco")
    private String endereco;

    /**
     * Número de telefone do cliente.
     */
    @Size(max = 15, message = "{quarkus.hibernate-validator.message.size.cliente.telefone}")
    @Column(name = "Telefone")
    private String telefone;

    /**
     * Nacionalidade do cliente.
     */
    @Size(max = 50, message = "{quarkus.hibernate-validator.message.size.cliente.nacionalidade}")
    @Column(name = "Nacionalidade")
    private String nacionalidade;

    /**
     * Estado civil do cliente.
     */
    @Size(max = 20, message = "{quarkus.hibernate-validator.message.size.cliente.estadoCivil}")
    @Column(name = "EstadoCivil")
    private String estadoCivil;

    /**
     * Gênero do cliente.
     */
    @Size(max = 20, message = "{quarkus.hibernate-validator.message.size.cliente.genero}")
    @Column(name = "Genero")
    private String genero;

    /**
     * Profissão do cliente.
     */
    @Size(max = 100, message = "{quarkus.hibernate-validator.message.size.cliente.profissao}")
    @Column(name = "Profissao")
    private String profissao;

    /**
     * Data de registro do cliente no sistema.
     */
    @PastOrPresent(message = "{quarkus.hibernate-validator.message.past-or-present.cliente.dataRegistro}")
    @Column(name = "DataRegistro")
    private LocalDate dataRegistro;
    
    /**
     * Indica se o cliente está ativo.
     */
    @Column(name = "Ativo")
    private boolean ativo;

    // Getters e setters

    /**
     * Obtém o ID do cliente.
     *
     * @return O ID do cliente.
     */
    public Long getClienteID() {
        return clienteID;
    }

    /**
     * Define o ID do cliente.
     *
     * @param clienteID O ID do cliente.
     */
    public void setClienteID(Long clienteID) {
        this.clienteID = clienteID;
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     *
     * @param nome O nome do cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o email do cliente.
     *
     * @return O email do cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do cliente.
     *
     * @param email O email do cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a senha do cliente.
     *
     * @return A senha do cliente.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do cliente.
     *
     * @param senha A senha do cliente.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Obtém a data de nascimento do cliente.
     *
     * @return A data de nascimento do cliente.
     */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Define a data de nascimento do cliente.
     *
     * @param dataNascimento A data de nascimento do cliente.
     */
    public void setDataNascimento(LocalDate dataNascimento) {
    	this.dataNascimento = dataNascimento;
    }

    /**
     * Obtém o CPF do cliente.
     *
     * @return O CPF do cliente.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF do cliente.
     *
     * @param cpf O CPF do cliente.
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Obtém o endereço do cliente.
     *
     * @return O endereço do cliente.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do cliente.
     *
     * @param endereco O endereço do cliente.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Obtém o telefone do cliente.
     *
     * @return O telefone do cliente.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do cliente.
     *
     * @param telefone O telefone do cliente.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Obtém a nacionalidade do cliente.
     *
     * @return A nacionalidade do cliente.
     */
    public String getNacionalidade() {
        return nacionalidade;
    }

    /**
     * Define a nacionalidade do cliente.
     *
     * @param nacionalidade A nacionalidade do cliente.
     */
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    /**
     * Obtém o estado civil do cliente.
     *
     * @return O estado civil do cliente.
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Define o estado civil do cliente.
     *
     * @param estadoCivil O estado civil do cliente.
     */
    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * Obtém o gênero do cliente.
     *
     * @return O gênero do cliente.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Define o gênero do cliente.
     *
     * @param genero O gênero do cliente.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtém a profissão do cliente.
     *
     * @return A profissão do cliente.
     */
    public String getProfissao() {
        return profissao;
    }

    /**
     * Define a profissão do cliente.
     *
     * @param profissao A profissão do cliente.
     */
    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    /**
     * Obtém a data de registro do cliente.
     *
     * @return A data de registro do cliente.
     */
    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    /**
     * Define a data de registro do cliente.
     *
     * @param dataRegistro A data de registro do cliente.
     */
    public void setDataRegistro(LocalDate dataRegistro) {
    	this.dataRegistro = dataRegistro;
    }
    
    /**
     * Verifica se o cliente está ativo.
     *
     * @return true se o cliente estiver ativo, caso contrário, false.
     */
    public boolean isAtivo() {
        return ativo;
    }

}


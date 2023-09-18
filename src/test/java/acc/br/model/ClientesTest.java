package acc.br.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientesTest {

    @Test
    void getClienteID() {
        Clientes cliente = new Clientes();
        cliente.setClienteID(320L);
        Long clienteId = cliente.getClienteID();
        assertEquals(320L,clienteId);
    }

    @Test
    void setClienteID() {
        Clientes cliente = new Clientes();
        cliente.setClienteID(320L);
        Long clienteId = cliente.getClienteID();
        assertEquals(320L,clienteId);
    }

    @Test
    void getNome() {
        Clientes cliente = new Clientes();
        cliente.setNome("Rafael");
        String clienteNome = cliente.getNome();
        assertEquals("Rafael",clienteNome);
    }

    @Test
    void setNome() {
        Clientes cliente = new Clientes();
        cliente.setNome("Rafael");
        String clienteNome = cliente.getNome();
        assertEquals("Rafael",clienteNome);
    }

    @Test
    void getEmail() {
        Clientes cliente = new Clientes();
        cliente.setEmail("rafael@acc.br");
        String clienteEmail = cliente.getEmail();
        assertEquals("rafael@acc.br",clienteEmail);
    }

    @Test
    void setEmail() {
        Clientes cliente = new Clientes();
        cliente.setEmail("rafael@acc.br");
        String clienteEmail = cliente.getEmail();
        assertEquals("rafael@acc.br",clienteEmail);
    }

    @Test
    void getSenha() {
        Clientes cliente = new Clientes();
        cliente.setSenha("asdf");
        String clienteSenha = cliente.getSenha();
        assertEquals("asdf",clienteSenha);
    }

    @Test
    void setSenha() {
        Clientes cliente = new Clientes();
        cliente.setSenha("asdf");
        String clienteSenha = cliente.getSenha();
        assertEquals("asdf",clienteSenha);
    }

    @Test
    void getDataNascimento() {
        Clientes cliente = new Clientes();
        cliente.setDataNascimento(LocalDate.parse("4/2"));
        LocalDate clienteDataNascimento = cliente.getDataNascimento();
        assertEquals(LocalDate.parse("4/2"),clienteDataNascimento);
    }

    @Test
    void setDataNascimento() {
        Clientes cliente = new Clientes();
        cliente.setDataNascimento(LocalDate.parse("4/2"));
        LocalDate clienteDataNascimento = cliente.getDataNascimento();
        assertEquals(LocalDate.parse("4/2"),clienteDataNascimento);
    }

    @Test
    void getCpf() {
        Clientes cliente = new Clientes();
        cliente.setCpf("123.465.789-01");
        String clienteCpf = cliente.getCpf();
        assertEquals("123.465.789-01",clienteCpf);
    }

    @Test
    void setCpf() {
        Clientes cliente = new Clientes();
        cliente.setCpf("123.465.789-01");
        String clienteCpf = cliente.getCpf();
        assertEquals("123.465.789-01",clienteCpf);
    }

    @Test
    void getEndereco() {
        Clientes cliente = new Clientes();
        cliente.setEndereco("asdf");
        String clienteEndereco = cliente.getEndereco();
        assertEquals("asdf",clienteEndereco);
    }

    @Test
    void setEndereco() {        
        Clientes cliente = new Clientes();
        cliente.setEndereco("asdf");
        String clienteEndereco = cliente.getEndereco();
        assertEquals("asdf",clienteEndereco);
    }

    @Test
    void getTelefone() {
        Clientes cliente = new Clientes();
        cliente.setTelefone("asdf");
        String clienteTelefone = cliente.getTelefone();
        assertEquals("asdf",clienteTelefone);
    }

    @Test
    void setTelefone() {
        Clientes cliente = new Clientes();
        cliente.setTelefone("asdf");
        String clienteTelefone = cliente.getTelefone();
        assertEquals("asdf",clienteTelefone);
    }

    @Test
    void getNacionalidade() {
        Clientes cliente = new Clientes();
        cliente.setNacionalidade("asdf");
        String clienteNacionalidade = cliente.getNacionalidade();
        assertEquals("asdf",clienteNacionalidade);
    }

    @Test
    void setNacionalidade() {
        Clientes cliente = new Clientes();
        cliente.setNacionalidade("asdf");
        String clienteNacionalidade = cliente.getNacionalidade();
        assertEquals("asdf",clienteNacionalidade);
    }

    @Test
    void getEstadoCivil() {
        Clientes cliente = new Clientes();
        cliente.setEstadoCivil("asdf");
        String clienteEstadoCivil = cliente.getEstadoCivil();
        assertEquals("asdf",clienteEstadoCivil);
    }

    @Test
    void setEstadoCivil() {
        Clientes cliente = new Clientes();
        cliente.setEstadoCivil("asdf");
        String clienteEstadoCivil = cliente.getEstadoCivil();
        assertEquals("asdf",clienteEstadoCivil);
    }

    @Test
    void getGenero() {
        Clientes cliente = new Clientes();
        cliente.setGenero("asdf");
        String clienteGenero = cliente.getGenero();
        assertEquals("asdf",clienteGenero);
    }

    @Test
    void setGenero() {
        Clientes cliente = new Clientes();
        cliente.setGenero("asdf");
        String clienteGenero = cliente.getGenero();
        assertEquals("asdf",clienteGenero);
    }

    @Test
    void getProfissao() {
        Clientes cliente = new Clientes();
        cliente.setProfissao("asdf");
        String clienteProfissao = cliente.getProfissao();
        assertEquals("asdf",clienteProfissao);
    }

    @Test
    void setProfissao() {
        Clientes cliente = new Clientes();
        cliente.setProfissao("asdf");
        String clienteProfissao = cliente.getProfissao();
        assertEquals("asdf",clienteProfissao);
    }

    @Test
    void getDataRegistro() {
        Clientes cliente = new Clientes();
        cliente.setDataRegistro(LocalDate.parse("4/2"));
        LocalDate clienteDataRegistro = cliente.getDataRegistro();
        assertEquals(LocalDate.parse("4/2"),clienteDataRegistro);
    }

    @Test
    void setDataRegistro() {
        Clientes cliente = new Clientes();
        cliente.setDataRegistro(LocalDate.parse("4/2"));
        LocalDate clienteDataRegistro = cliente.getDataRegistro();
        assertEquals(LocalDate.parse("4/2"),clienteDataRegistro);
    }

    @Test
    void setAtivo() {
        Clientes cliente = new Clientes();
        cliente.setAtivo(true);
        assertEquals(true,cliente.isAtivo());
    }
    @Test
    void isAtivo() {
        Clientes cliente = new Clientes();
        cliente.setAtivo(true);
        assertEquals(true,cliente.isAtivo());
    }
}
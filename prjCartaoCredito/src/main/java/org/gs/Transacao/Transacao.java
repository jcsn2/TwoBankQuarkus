package org.gs.Transacao;

import java.sql.Date;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Transacao extends PanacheMongoEntity {

    public String ClienteId;
    public String Estabelecimenro;
    public String TipoEstabelecimento;
    public Date Data;
    public Long Valor;

    public Transacao() {

    }

    public Transacao(String clienteId, String estabelecimenro, String tipoEstabelecimento, Date data, Long valor) {
        this.ClienteId = clienteId;
        this.Estabelecimenro = estabelecimenro;
        this.TipoEstabelecimento = tipoEstabelecimento;
        this.Data = data;
        this.Valor = valor;
    } 
}

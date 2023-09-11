package org.rs.Transacao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class TransacaoRepository<Transacao> implements PanacheMongoRepository<Transacao> {

    public Transacao findById(String id) {
        return find("_id", id).firstResult();
    }
    

    public List<Transacao> findOrderedName() {
        return listAll(Sort.by("name"));
    }
    
}

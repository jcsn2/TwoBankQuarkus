package acc.br.Extrato;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.persistence.Entity;

/**
 * Example JPA entity defined as a Panache Entity.
 * An ID field of Long type is provided, if you want to define your own ID field extends <code>PanacheEntityBase</code> instead.
 *
 * This uses the active record pattern, you can also use the repository pattern instead:
 * .
 *
 * Usage (more example on the documentation)
 *
 * {@code
 *     public void doSomething() {
 *         MyEntity entity1 = new MyEntity();
 *         entity1.field = "field-1";
 *         entity1.persist();
 *
 *         List<MyEntity> entities = MyEntity.listAll();
 *     }
 * }
 */
@Entity
public class ExtratoRepository implements PanacheRepository<Extrato> {

    public Extrato findByConta(String num) {
        return find("NumConta", num).firstResult();
    }

//toDo: findBy numConta + periodo
    
}

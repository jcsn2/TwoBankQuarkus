package acc.br.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Exceção personalizada para representar um saldo insuficiente.
 */
public class SaldoInsuficienteException extends WebApplicationException {

    public SaldoInsuficienteException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(message)
                .type("text/plain")
                .build());
    }
}
package acc.br.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Exceção personalizada para representar um valor mínimo invalido.
 */
public class ValorMinimoInvalidoException extends WebApplicationException {

    public ValorMinimoInvalidoException(String message) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(message)
                .type("text/plain")
                .build());
    }
}
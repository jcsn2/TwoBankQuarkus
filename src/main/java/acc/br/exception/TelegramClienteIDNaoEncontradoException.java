package acc.br.exception;

/**
 * Exceção lançada quando uma associação entre um cliente e seu ID no Telegram não é encontrada.
 */
public class TelegramClienteIDNaoEncontradoException extends Exception {

    public TelegramClienteIDNaoEncontradoException(String message) {
        super(message);
    }
}
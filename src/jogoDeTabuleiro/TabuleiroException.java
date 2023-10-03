package jogoDeTabuleiro;

public class TabuleiroException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	// Construtor para receber a mensagem e passar para a superClasse
	public TabuleiroException(String msg) {
		super(msg);
	}

}

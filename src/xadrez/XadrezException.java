package xadrez;

import jogoDeTabuleiro.TabuleiroException;

public class XadrezException extends TabuleiroException{
	private static final long serialVersionUID = 1L;
	
	// Construtor para pegar a msg e enviar para a superclasse.
	public XadrezException(String msg) {
		super(msg);
	}

}

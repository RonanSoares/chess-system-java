package xadrez;

import jogoDeTabuleiro.Peca;
import jogoDeTabuleiro.Tabuleiro;

public class XadrezPeca extends Peca{
	private Cor cor;

	public XadrezPeca(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	// Get Set sem o set, pois a cor de uma peça não pode ser modificada.
	public Cor getCor() {
		return cor;
	}

	
	
	
}

package xadrez;

import jogoDeTabuleiro.Peca;
import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;

public abstract class XadrezPeca extends Peca{
	private Cor cor;

	public XadrezPeca(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	// Get Set sem o set, pois a cor de uma peça não pode ser modificada.
	public Cor getCor() {
		return cor;
	}
	
	// Verifica se ha peça do oponente
	protected boolean haPecaOponente(Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
		
	}
}

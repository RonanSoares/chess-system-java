package xadrez;

import jogoDeTabuleiro.Peca;
import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;

public abstract class XadrezPeca extends Peca{
	private Cor cor;
	private int movimentoContar;

	public XadrezPeca(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	// Get Set sem o set, pois a cor de uma peça não pode ser modificada.
	public Cor getCor() {
		return cor;
	}
	
	// Incrementar contagemMovimentos
	public void incrementarMovimentoContar() {
		movimentoContar++;
	}
	
	public int getMovimentoContar() {
		return movimentoContar;
	}
		
	// Decrementar contagemMovimentos
		public void decrementarMovimentoContar() {
			movimentoContar--;
		}
	
	public XadrezPosicao getXadrezPosicao() {
		return XadrezPosicao.fromPosicao(posicao);
		
	}
	
	// Verifica se ha peça do oponente
	protected boolean haPecaOponente(Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
		
	}
}

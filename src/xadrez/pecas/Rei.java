package xadrez.pecas;

import jogoDeTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca{

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		// Cria uma matriz boleana do mesmo tamanho do tabuleiro. Toda posições iniciam como false
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	}

}

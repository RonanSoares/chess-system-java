package xadrez.pecas;

import jogoDeTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

// Cria classe da peça torre.
public class Torre extends PecaDeXadrez{

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return "T"; // Peça torre.
	}
}

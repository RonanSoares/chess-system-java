package xadrez;

import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez(){ 
		tabuleiro = new Tabuleiro(8, 8);
		initialSetup(); // Quando for criada uma partida, cria o tabuleiro e chama o método initialSetup
		
	}
	
	//Método retorna uma matriz de peças de xadrez para essa partida.
	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		// Percorrer a matriz
		for(int i = 0; i < tabuleiro.getLinhas(); i++) {
			for(int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez)tabuleiro.peca(i, j);
			}
		}
		return mat; // Retorna a matriz de peças da partida de xadrez.
	}
	
	// Cria o método Inicio da partida
	private void initialSetup() {
		tabuleiro.lugarPeca(new Torre(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
		tabuleiro.lugarPeca(new Rei(tabuleiro, Cor.PRETO), new Posicao(0, 4));
		tabuleiro.lugarPeca(new Rei(tabuleiro, Cor.BRANCO), new Posicao(7, 4));
		
	}
}

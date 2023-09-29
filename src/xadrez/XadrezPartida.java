package xadrez;

import jogoDeTabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class XadrezPartida {
	private Tabuleiro tabuleiro;
	
	public XadrezPartida(){ 
		tabuleiro = new Tabuleiro(8, 8);
		initialSetup(); // Quando for criada uma partida, cria o tabuleiro e chama o método initialSetup
		
	}
	
	//Método retorna uma matriz de peças de xadrez para essa partida.
	public XadrezPeca[][] getPecas(){
		XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		// Percorrer a matriz
		for(int i = 0; i < tabuleiro.getLinhas(); i++) {
			for(int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (XadrezPeca)tabuleiro.peca(i, j);
			}
		}
		return mat; // Retorna a matriz de peças da partida de xadrez.
	}
	
	// Método para receber as coordenadas do xadrez.
	private void lugarNovaPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
	}
	
	// Cria o método Inicio da partida
	private void initialSetup() {
		lugarNovaPeca('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
		
	}
}

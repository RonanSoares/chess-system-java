package xadrez.pecas;

import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

// Cria classe da peça torre.
public class Bispo extends XadrezPeca {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "B"; // Peça torre.
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		// Cria uma matriz boleana do mesmo tamanho do tabuleiro. Toda posições iniciam como false
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];		
		
		Posicao p = new Posicao(0, 0);
	
		// Verifica se tem movimentos livres.
		
		// Noroeste
		p.attValores(posicao.getLinha() - 1, posicao.getColuna() -1 );
		// Enquanto a posição estiver vaga, será marcado como verdadeiro.
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {			
			mat[p.getLinha()][p.getColuna()] = true;
			p.attValores(p.getLinha() -1 , p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) { // Se houver uma peça adversária...
			mat[p.getLinha()][p.getColuna()] = true; // avança mais uma casa.
		}

		// Nordeste
		p.attValores(posicao.getLinha() -1 , posicao.getColuna() + 1);
		// Enquanto a posição estiver vaga, será marcado como verdadeiro.
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {			
			mat[p.getLinha()][p.getColuna()] = true;
			p.attValores(p.getLinha() - 1 , p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) { // Se houver uma peça adversária...
			mat[p.getLinha()][p.getColuna()] = true; // avança mais uma casa.
		}

		// Sudeste
		p.attValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		// Enquanto a posição estiver vaga, será marcado como verdadeiro.
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.attValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) { // Se houver uma peça adversária...
			mat[p.getLinha()][p.getColuna()] = true; // avança mais uma casa.
		}

		// Sudoeste
		p.attValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		// Enquanto a posição estiver vaga, será marcado como verdadeiro.
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
		mat[p.getLinha()][p.getColuna()] = true;
		p.attValores(p.getLinha() + 1, p.getColuna() - 1 );
		}
		if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) { // Se houver uma peça adversária...
			mat[p.getLinha()][p.getColuna()] = true; // avança mais uma casa.
		}

		return mat;
	}
}


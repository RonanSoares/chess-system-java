package xadrez.pecas;

import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPeca;

// Cria classe da peça torre.
public class Torre extends XadrezPeca {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "T"; // Peça torre.
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		// Cria uma matriz boleana do mesmo tamanho do tabuleiro. Toda posições iniciam
		// como false
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
	/*	// Verifica se tem movimentos livres.
		Posicao p = new Posicao(0, 0);
	
		// Acima
		p.attValores(posicao.getLinha() - 1, posicao.getColuna());
		// Enquanto a posição estiver vaga, será marcado como verdadeiro.
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p))
			;
		mat[p.getLinha()][p.getColuna()] = true;
		p.setLinha(p.getLinha() - 1); // Avança mais uma casa, enquanto a casa estiver vazia.
		if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) { // Se houver uma peça adversária...
			mat[p.getLinha()][p.getColuna()] = true; // avança mais uma casa.
		}

		// A esquerda
		p.attValores(posicao.getLinha(), posicao.getColuna() - 1);
		// Enquanto a posição estiver vaga, será marcado como verdadeiro.
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p))
			;
		mat[p.getLinha()][p.getColuna()] = true;
		p.setColuna(p.getColuna() - 1); // Avança mais uma casa, enquanto a casa estiver vazia.
		if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) { // Se houver uma peça adversária...
			mat[p.getLinha()][p.getColuna()] = true; // avança mais uma casa.
		}

		// A Direita
		p.attValores(posicao.getLinha(), posicao.getColuna() + 1);
		// Enquanto a posição estiver vaga, será marcado como verdadeiro.
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p));
		mat[p.getLinha()][p.getColuna()] = true;
		p.setColuna(p.getColuna() + 1); // Avança mais uma casa, enquanto a casa estiver vazia.
		if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) { // Se houver uma peça adversária...
			mat[p.getLinha()][p.getColuna()] = true; // avança mais uma casa.
		}

		// Abaixo
		p.attValores(posicao.getLinha() + 1, posicao.getColuna());
		// Enquanto a posição estiver vaga, será marcado como verdadeiro.
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p));
		mat[p.getLinha()][p.getColuna()] = true;
		p.setLinha(p.getLinha() + 1); // Avança mais uma casa, enquanto a casa estiver vazia.
		if (getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) { // Se houver uma peça adversária...
			mat[p.getLinha()][p.getColuna()] = true; // avança mais uma casa.
		}
*/
		return mat;
	}
}

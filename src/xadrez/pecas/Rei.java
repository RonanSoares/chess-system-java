package xadrez.pecas;

import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;

public class Rei extends XadrezPeca{
	
	//Associação do Rei com XadrezPartida
	private XadrezPartida xadrezPartida;

	public Rei(Tabuleiro tabuleiro, Cor cor, XadrezPartida xadrezPartida) {
		super(tabuleiro, cor);
		this.xadrezPartida = xadrezPartida;
	}
	
	// Método auxiliar para testar se o Rei está apto a realizar a jogada especial Roque
	private boolean testarTorreCastling(Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getMovimentoContar() == 0;
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	// Método para verificar as posições que o rei pode mover
	private boolean podeMover(Posicao posicao) {
		XadrezPeca p = (XadrezPeca)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
		
	}

	@Override
	public boolean[][] possiveisMovimentos() {
		// Cria uma matriz boleana do mesmo tamanho do tabuleiro. Toda posições iniciam como false
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Acima
		p.attValores(posicao.getLinha() - 1 , posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Abaixo
		p.attValores(posicao.getLinha() + 1 , posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//A Esquerda
		p.attValores(posicao.getLinha(), posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//A Direita
		p.attValores(posicao.getLinha(), posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Noroeste - Diagonal a esquerda
		p.attValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Nordeste - Diagonal a direita
		p.attValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Sudoeste - Diagonal a abaixo a esquerda
		p.attValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Sudeste - Diagonal a abaixo a direita
		p.attValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Método para testar se o Rei está apto a realizar a jogada especial Roque
		if(getMovimentoContar() == 0 && !xadrezPartida.getCheck()) {
			// Movimento especial torre ao Lado do Rei.
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if(testarTorreCastling(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1); // Testando posicão ao lado
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
					
				}
			}
			
			// Movimento especial Rainha ao Lado da Torre.
			Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if(testarTorreCastling(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1); // Testando posicão ao lado
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna()] = true;
					if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
						mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
						
					}					
				}
			}			
		}
		
		return mat;
		}
	}

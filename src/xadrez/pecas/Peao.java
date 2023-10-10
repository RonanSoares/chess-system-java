package xadrez.pecas;

import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;

public class Peao extends XadrezPeca{
	
	// Associação com XadrezPartida
	private XadrezPartida xadrezPartida;

	public Peao(Tabuleiro tabuleiro, Cor cor, XadrezPartida xadrezPartida) {
		super(tabuleiro, cor);
		this.xadrezPartida = xadrezPartida;
	}

	@Override
	public boolean[][] possiveisMovimentos() {		
		// Cria uma matriz auxiliar boleana do mesmo tamanho do tabuleiro. Toda posições iniciam como false
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];		
				
		Posicao p = new Posicao(0, 0);
		
		// Movimentar o peão 
		if(getCor() == Cor.BRANCO) {
			p.attValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.attValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao (posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2)&& getMovimentoContar() == 0){
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.attValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
		}
			p.attValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// Movimento especial passo vulnerável branco
			if(posicao.getLinha() == 3){
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(esquerda) && haPecaOponente(esquerda) && getTabuleiro().peca(esquerda) == xadrezPartida.getPassoVulneravel()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}				
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(direita) && haPecaOponente(direita) && getTabuleiro().peca(direita) == xadrezPartida.getPassoVulneravel()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}			
		}
		else {
			p.attValores(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.attValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao (posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2)&& getMovimentoContar() == 0){
				mat[p.getLinha()][p.getColuna()] = true;
			}			
			p.attValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
		}
			p.attValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExiste(p) && haPecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			// Movimento especial passo vulnerável preto
			if(posicao.getLinha() == 4){
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(esquerda) && haPecaOponente(esquerda) && getTabuleiro().peca(esquerda) == xadrezPartida.getPassoVulneravel()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}				
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(direita) && haPecaOponente(direita) && getTabuleiro().peca(direita) == xadrezPartida.getPassoVulneravel()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		}		
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";		
	}
}

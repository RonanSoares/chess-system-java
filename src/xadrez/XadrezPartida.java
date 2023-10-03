package xadrez;

import jogoDeTabuleiro.Peca;
import jogoDeTabuleiro.Posicao;
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
	
	// Método para movimentar as peças
	public XadrezPeca performXadrezMover(XadrezPosicao origemPosicao, XadrezPosicao destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao(); // Converte para posição matriz.
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem); // Operação para validar a posição de origem.
		Peca pecaCapturada = fazerMovimento(origem, destino);
		return (XadrezPeca) pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem); // Retira a peça
		Peca pecaCapturada = tabuleiro.removePeca(destino); // Remove a possível peça de destino
		tabuleiro.colocarPeca(p, destino); // Coloca a peça na posição de destino.
		return pecaCapturada;
	}
	
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Nao existe peça na posicao de origem");
		}
	}	
	
	// Método para receber as coordenadas do xadrez.
	private void colocarNovaPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
	}
	
	// Cria o método Inicio da partida
	private void initialSetup() {
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
		
	}
}

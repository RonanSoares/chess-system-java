package xadrez;

import jogoDeTabuleiro.Posicao;

public class XadrezPosicao {
	private char coluna;
	private int linha;
	
	public XadrezPosicao(char coluna, int linha) {
		// Programação defensiva
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezException("Erro de instanciação. As posicoes são de a1 a h8");
		}
		this.coluna = coluna;
		this.linha = linha;
	}
	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}

	// Métodos get e set de linhas e colunas são apagadas. Pois não podem ser alteradas pelo programador
	
	// Método toPosition para converter o método XadrezPosição para o Posição normal.
	protected Posicao toPosicao() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	// Dada uma posição na matriz, converte para a posicao xadrez.
	protected static XadrezPosicao fromPosicao(Posicao posicao) {
		return new XadrezPosicao((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override	
	public String toString() {
		return "" + coluna + linha; // As aspas são para forçar o compilador a entender que é uma
		                            // concatenação de strings.
	}
}

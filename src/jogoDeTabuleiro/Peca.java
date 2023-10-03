package jogoDeTabuleiro;

public abstract class Peca {
	
	protected Posicao posicao; // Protected pq é uma posição de matriz. Não é visível a camada de xadrez
	private Tabuleiro tabuleiro; //Associaçao
	
	// Construtor. posicao não inclui no construtor pois o tabuleiro iniciará sem peças(vazio).
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
		
	}
	
	// Get Set sem o atributo posicão e sem o método Set. Protected: só classes do mesmo pacote e subclasses tem acesso
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	// Método para retornar uma matriz boleana.
	public abstract boolean[][] possiveisMovimentos();
	
	// Método para retornar possivel movimento em uma linha e uma coluna.
	public boolean possivelMovimento(Posicao posicao) {
		return possiveisMovimentos()[posicao.getLinha()][posicao.getColuna()];
	}
	
	// Método que verifica se a peça tem pelo menos um movimento.
	public boolean existeAlgumMovimentoPossivel() {
		boolean[][] mat = possiveisMovimentos();
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}			
		}
		return false;
	}
}

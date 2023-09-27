package jogoDeTabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	// Construtor sem o atributo pecas;
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}
	
	// Get Set sem o atributo pecas.
	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
	
	// Cria um método para retornar uma peça em uma determinada linha e coluna.
	public Peca peca(int linha, int coluna) {
		return pecas[linha][coluna]; // Retorna a matriz pecas nas linhas e colunas informadas.
	}
	
	// Sobrecarga do metodo peca. Retorna a peça pela posicão indicada
	public Peca peca(Posicao posicao) {
		return pecas[posicao.getLinha()][posicao.getColuna()];
		
	}
}
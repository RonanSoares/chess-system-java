package jogoDeTabuleiro;

public class Tabuleiro {
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	// Construtor sem o atributo pecas;
	public Tabuleiro(int linhas, int colunas) {
		// Programação defensiva
		if(linhas < 1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar tabuleiro: Tem que ter ao menos uma linha e uma coluna" );
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}
	
	// Get Set sem o atributo pecas.
	// Métodos set linha e set colunas são tirados(depois do tabuleiro criado não pode mudar as linhas e colunas)
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	// Cria um método para retornar uma peça em uma determinada linha e coluna.
		public Peca peca(int linha, int coluna) {
			if(!posicaoExiste(linha, coluna)) {
				throw new TabuleiroException("Não há posicão no tabuleiro");
			}
			return pecas[linha][coluna]; // Retorna a matriz pecas nas linhas e colunas informadas.
		}

	// Sobrecarga do metodo peca. Retorna a peça pela posicão indicada
	public Peca peca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Não há posicão no tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
		
	}
	
	// Método para colocar uma peça em determinada posicão no tabuleiro.
	public void colocarPeca(Peca peca, Posicao posicao) {
		if(temUmaPeca(posicao)) {
			throw new TabuleiroException("Já existe uma peça na posição" + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca; // Atribui a peça na matriz
		peca.posicao = posicao; // A peça não esta na posição nula e sim na posicão atribuida.
		
	}
	
	public Peca removePeca(Posicao posicao) {
		// Prog. defensiva
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Não há posicão no tabuleiro");
		}if(peca(posicao) == null){
			return null;
		}
			Peca aux = peca(posicao);
			aux.posicao = null;
			pecas[posicao.getLinha()][posicao.getColuna()] = null;
			return aux;
		}
	
	
	// Método auxiliar para verificar se a posicao existe.
	public boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
		
	}
	
	// Método para verificar se a posicao existe.
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	// método para verificar se tem uma peça na posicão.
	public boolean temUmaPeca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Não há posicão no tabuleiro");
		}
		return peca(posicao) != null;
	}
}

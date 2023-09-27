package jogoDeTabuleiro;

public class Peca {
	
	protected Posicao posicao;
	private Tabuleiro tabuleiro; //Associaçao
	
	// Construtor. posicao não inclui no construtor pois o tabuleiro iniciará sem peças(vazio).
	public Peca(Tabuleiro tabuleiro) {
		super();
		this.tabuleiro = tabuleiro;
		posicao = null;
	}
	
	// Get Set sem o atributo posicão e sem o método Set. Protected: só classes do mesmo pacote e subclasses tem acesso
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	
	
	

}

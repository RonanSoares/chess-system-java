package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogoDeTabuleiro.Peca;
import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class XadrezPartida {

	private int vez;
	private Cor atualJogador;
	private Tabuleiro tabuleiro;
	private static boolean check;
	private static boolean checkMate;
	private XadrezPeca passoVulneravel;
	private XadrezPeca promocao;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public XadrezPartida() {
		tabuleiro = new Tabuleiro(8, 8);
		vez = 1;
		atualJogador = Cor.BRANCO;
		//check = false;             Não é necessário declarar pois por padrão inicia como false.
		//passoVulneravel = null;    Não é necessário declarar pois por padrão inicia como nulo.
		initialSetup(); // Quando for criada uma partida, cria o tabuleiro e chama o método initialSetup
	}

	public int getVez() {
		return vez;
	}

	public Cor getAtualJogador() {
		return atualJogador;
	}

	public static boolean getCheck() {
		return check;
	}

	public static boolean getCheckMate() {
		return checkMate;
	}

	public XadrezPeca getPassoVulneravel() {
		return passoVulneravel;
	}	

	public XadrezPeca getPromocao() {
		return promocao;
	}

	// Método retorna uma matriz de peças de xadrez para essa partida.
	public XadrezPeca[][] getPecas() {
		XadrezPeca[][] mat = new XadrezPeca[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		// Percorrer a matriz
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (XadrezPeca) tabuleiro.peca(i, j);
			}
		}
		return mat; // Retorna a matriz de peças da partida de xadrez.
	}

	public boolean[][] possiveisMovimentos(XadrezPosicao origemPosicao) {
		Posicao posicao = origemPosicao.toPosicao(); // Converter
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();

	}

	// Método para movimentar as peças
	public XadrezPeca performXadrezMover(XadrezPosicao origemPosicao, XadrezPosicao destinoPosicao) {
		Posicao origem = origemPosicao.toPosicao(); // Converte para posição matriz.
		Posicao destino = destinoPosicao.toPosicao();
		validarOrigemPosicao(origem); // Operação para validar a posição de origem.
		validarDestinoPosicao(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);

		// Se estiver em check, defazer movimento
		if (testarCheck(atualJogador)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Voce nao pode se colocar em xeque");
		}

		XadrezPeca moveuPeca = (XadrezPeca) tabuleiro.peca(destino);
		
		// Movimento especial promoção
		promocao = null;
		if(moveuPeca instanceof Peao) {
			if ((moveuPeca.getCor() == Cor.BRANCO && destino.getLinha() == 0) || (moveuPeca.getCor() == Cor.PRETO && destino.getLinha() == 7)) {
				promocao = (XadrezPeca)tabuleiro.peca(destino);
				promocao = substituirPromocaoPeca("Q");				
				
			}
		}		
		
		check = (testarCheck(oponente(atualJogador))) ? true : false;

		if (testarCheckMate(oponente(atualJogador))) {
			checkMate = true;
		} else {
			proximaVez();
		}

		// Movimento especial passoVulnerável
		if (moveuPeca instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)){
			passoVulneravel = moveuPeca;
		} else {
			passoVulneravel = null;
		}
		return (XadrezPeca) pecaCapturada;
	}
	
	// Método para substituir a peça jogada especial promoção.
	public XadrezPeca substituirPromocaoPeca(String tipo) {
		if(promocao == null) {
			throw new IllegalStateException("Nao ha peca para ser promovida");
		}
		if(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("R") && !tipo.equals("Q")){
			throw new InvalidParameterException("Tipo invalido para promocao");
		}
		
		Posicao pos = promocao.getXadrezPosicao().toPosicao(); // Removeu a peça na posicao
		Peca p = tabuleiro.removePeca(pos);                    // Guardou na variável p;
		pecasNoTabuleiro.remove(p);                            // Remove peça do tabuleiro.		
		
		XadrezPeca novaPeca = novaPeca(tipo, promocao.getCor()); // Instancia a peça.
		tabuleiro.colocarPeca(novaPeca, pos); // Coloca a peça na posicão pos da peça promovida.
		pecasNoTabuleiro.add(novaPeca);       // Adiciona a peça na lista peças no tabuleiro
		
		return novaPeca;                      // Retorna a peça instanciada.
		
	}
	
	// Método auxiliar para receber a peça a ser substituida na posicao pos.
	private XadrezPeca novaPeca(String tipo, Cor cor) {
		if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if(tipo.equals("Q")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
		
	}

	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		XadrezPeca p = (XadrezPeca) tabuleiro.removePeca(origem); // Retira a peça do tabuleiro
		p.incrementarMovimentoContar(); // Incrementa movimento
		Peca pecaCapturada = tabuleiro.removePeca(destino); // Remove a possível peça de destino
		tabuleiro.colocarPeca(p, destino); // Coloca a peça na posição de destino.
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada); // Retira a peça da lista do tabuleiro
			pecasCapturadas.add(pecaCapturada);// E adiciona na lista de peças capturadas.
		}
		// Movimento especial Rei ao lado da torre. Roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			XadrezPeca torre = (XadrezPeca) tabuleiro.removePeca(origemT); // Retira a torre de onde está.
			tabuleiro.colocarPeca(torre, destinoT); // Move a torre para a posicao de destino
			torre.incrementarMovimentoContar(); // Incrementa o movimento da torre.

		}

		// Movimento especial Rei ao lado da torre. Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			XadrezPeca torre = (XadrezPeca) tabuleiro.removePeca(origemT); // Retira a torre de onde está.
			tabuleiro.colocarPeca(torre, destinoT); // Move a torre para a posicao de destino
			torre.incrementarMovimentoContar(); // Incrementa o movimento da torre.
		}

		// Movimento especial passo vulnerável
		if (p instanceof Peao) {
			// se peão andou na diagonal e não capturou peça
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		return pecaCapturada;
	}

	// Desfazer movimento
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		XadrezPeca p = (XadrezPeca) tabuleiro.removePeca(destino); // Remove peça
		p.decrementarMovimentoContar(); // Decrementa movimento.
		tabuleiro.colocarPeca(p, origem); // Coloca a peça no local de origem.

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino); // Volta a peça posição de destino.
			pecasCapturadas.remove(pecaCapturada);// tira a peça da lista de peças capturadas e volta para a origem no tabuleiro.
			pecasNoTabuleiro.add(pecaCapturada);// Adiciona peça no tabuleiro

		}
		// Movimento especial Rei ao lado da torre. Roque pequeno. Desfazendo movimento
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			XadrezPeca torre = (XadrezPeca) tabuleiro.removePeca(destinoT); // Retira a torre de onde está.
			tabuleiro.colocarPeca(torre, origemT); // Move a torre para a posicao de destino
			torre.decrementarMovimentoContar(); // Incrementa o movimento da torre.

		}
		// Movimento especial Rei ao lado da torre. Roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			XadrezPeca torre = (XadrezPeca) tabuleiro.removePeca(destinoT); // Retira a torre de onde está.
			tabuleiro.colocarPeca(torre, origemT); // Move a torre para a posicao de destino
			torre.decrementarMovimentoContar(); // Decrementa o movimento da torre.
		}
		// Movimento especial passo vulnerável
		if (p instanceof Peao) {
			// se peão andou na diagonal e não capturou peça
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == passoVulneravel) {
				XadrezPeca peao = (XadrezPeca)tabuleiro.removePeca(destino);
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, peaoPosicao);
			}
		}		
	}

	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Nao existe peça na posicao de origem");
		}
		if (atualJogador != ((XadrezPeca) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida nao e sua.");
		}
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
			throw new XadrezException("Nao ha movimentos possiveis para a peca escolhida.");
		}
	}

	// Validar posicao destino
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException("A peca escolhida nao pode mover para o local de destino.");
		}
	}

	private void proximaVez() {
		vez++;
		atualJogador = (atualJogador == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	// Devolve a cor do oponente ao contrário.
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	// Método para procurar na lista, o rei de uma determinada cor.
	private XadrezPeca rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (XadrezPeca) p;
			}
		}
		throw new IllegalStateException("Nao existe " + cor + "rei no tabuleiro");

	}

	// Método para testar se o rei de uma determinada cor está em check
	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getXadrezPosicao().toPosicao();
		List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	// Método para testar o checkMate
	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) { // Se movimento possível
						Posicao origem = ((XadrezPeca) p).getXadrezPosicao().toPosicao(); // Movim. p para matriz
						Posicao destino = new Posicao(i, j);
						Peca capturadaPeca = fazerMovimento(origem, destino); // Faz o movimento
						boolean testarCheck = testarCheck(cor); // Testa se o Rei ainda esta em check
						desfazerMovimento(origem, destino, capturadaPeca); // Desfaz o movimento
						if (!testarCheck) { // Se não estiver em check, o movimento tirou o Rei do check.
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	// Método para receber as coordenadas do xadrez.
	private void colocarNovaPeca(char coluna, int linha, XadrezPeca peca) {
		tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao()); // Coloca peça tabuleiro
		pecasNoTabuleiro.add(peca); // Coloca peça na lista de tabuleiro.
	}

	// Cria o método Inicio da partida
	private void initialSetup() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));

	}
}
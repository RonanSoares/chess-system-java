package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogoDeTabuleiro.Peca;
import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;
import jogoDeTabuleiro.TabuleiroException;
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
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public XadrezPartida(){ 
		tabuleiro = new Tabuleiro(8, 8);
		vez = 1;
		atualJogador = Cor.BRANCO;
	   check = false;    //Não é necessário declarar pois por padrão inicia como false.
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
	
	public boolean[][] possiveisMovimentos(XadrezPosicao origemPosicao){
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
		if(testarCheck(atualJogador)) {  
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezException("Voce nao pode se colocar em xeque");
		}
		
		check = (testarCheck(oponente(atualJogador))) ? true : false;
		
		if(testarCheckMate(oponente(atualJogador))) {
			checkMate = true;
		}else {
			proximaVez();
		}		
		return (XadrezPeca)pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		XadrezPeca p = (XadrezPeca)tabuleiro.removePeca(origem); // Retira a peça do tabuleiro
		p.incrementarMovimentoContar(); // Incrementa movimento
		Peca pecaCapturada = tabuleiro.removePeca(destino); // Remove a possível peça de destino
		tabuleiro.colocarPeca(p, destino); // Coloca a peça na posição de destino.
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada); // Retira a peça da lista do tabuleiro
			pecasCapturadas.add(pecaCapturada);// E adiciona na lista de peças capturadas.
			
		}
		return pecaCapturada;
	}
	
	// Desfazer movimento
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca capturadaPeca) {
		XadrezPeca p = (XadrezPeca)tabuleiro.removePeca(destino); // Remove peça
		p.decrementarMovimentoContar(); // Decrementa movimento.
		tabuleiro.colocarPeca(p, origem);       // Coloca a peça no local de origem.
		
		if(capturadaPeca != null) {
			tabuleiro.colocarPeca(capturadaPeca, destino); // Volta a peça posição de destino.
			pecasCapturadas.remove(capturadaPeca);// tira a peça da lista de peças capturadas e volta para a origem no tabuleiro.
			pecasNoTabuleiro.add(capturadaPeca);// Adiciona peça no tabuleiro
			
		}
	}
	
	private void validarOrigemPosicao(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Nao existe peça na posicao de origem");
		}
		if(atualJogador != ((XadrezPeca)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida nao e sua.");
		}
		if(!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()){
			throw new XadrezException("Nao ha movimentos possiveis para a peca escolhida.");
		}
	}
		
		// Validar posicao destino
		private void validarDestinoPosicao(Posicao origem, Posicao destino) {
			if(!tabuleiro.peca(origem).possivelMovimento(destino)) {
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
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if(p instanceof Rei) {
				return(XadrezPeca)p;
			}
		}
		throw new IllegalStateException("Nao existe " + cor + "rei no tabuleiro");
		
	}
	
	// Método para testar se o rei de uma determinada cor está em check
	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getXadrezPosicao().toPosicao();
		List<Peca> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p : oponentePecas) {
			boolean[][] mat = p.possiveisMovimentos();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	// Método para testar o checkMate
	private boolean testarCheckMate(Cor cor) {
		if(!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((XadrezPeca)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for(int i = 0; i < tabuleiro.getLinhas(); i++) {
				for(int j = 0; j < tabuleiro.getColunas(); j++) {
					if(mat[i][j]) { // Se movimento possível
						Posicao origem = ((XadrezPeca)p).getXadrezPosicao().toPosicao(); //Movim. p para matriz
						Posicao destino = new Posicao(i,j);
						Peca capturadaPeca = fazerMovimento(origem, destino); // Faz o movimento
						boolean testarCheck = testarCheck(cor); //  Testa se o Rei ainda esta em check
						desfazerMovimento(origem, destino, capturadaPeca); // Desfaz o movimento
						if(!testarCheck) { // Se não estiver em check, o movimento tirou o Rei do check.
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
		tabuleiro.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao()); //Coloca peça tabuleiro
		pecasNoTabuleiro.add(peca); // Coloca peça na lista de tabuleiro.
	}
	
	// Cria o método Inicio da partida
	private void initialSetup() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO));
		
		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO));
		
				
	}
}
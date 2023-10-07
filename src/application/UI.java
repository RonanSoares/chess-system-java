package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import jogoDeTabuleiro.TabuleiroException;
import xadrez.Cor;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// Método para limpar a tela.
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	// Método para ler uma posição do usuário. Recebe o Scanner do programa
	// principal
	public static XadrezPosicao lerXadrezPosicao(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));// Recorta a String a partir da posicao 1 e converte para
															// Integer
			return new XadrezPosicao(coluna, linha);
		} catch (TabuleiroException e) {
			throw new InputMismatchException("Erro entrada de dados. Posicao e a1 a h8.");
		}
	}

	public static void imprimirPartida(XadrezPartida xadrezPartida, List<XadrezPeca> capturada) {
		imprimirTabuleiro(xadrezPartida.getPecas());
		System.out.println();
		imprimirPecasCapturadas(capturada);
		System.out.println();
		System.out.println("Vez : " + xadrezPartida.getVez());
		if(!xadrezPartida.getCheckMate()) {
			System.out.println("Aguardando jogador: " + xadrezPartida.getAtualJogador());
			if (xadrezPartida.getCheck()) {
			System.out.println("CHECK!");
			}			
		}
		else {
			System.out.println("CHECK MATE!!!");
			System.out.println("Vencedor da partida: " + xadrezPartida.getAtualJogador());
		}
	}

	public static void imprimirTabuleiro(XadrezPeca[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j], false); // Imprimir as peças
			}
			System.out.println(); // Quebra de linha.
		}
		System.out.println("  a b c d e f g h");
	}

	public static void imprimirTabuleiro(XadrezPeca[][] pecas, boolean[][] possiveisMovimentos) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j], possiveisMovimentos[i][j]); // Imprimir as peças
			}
			System.out.println(); // Quebra de linha.
		}
		System.out.println("  a b c d e f g h");
	}

	// Cria um método para imprimir uma peça de xadrez
	private static void imprimirPeca(XadrezPeca peca, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}

		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Cor.BRANCO) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	// Método para imprimir as peças capturadas
	private static void imprimirPecasCapturadas(List<XadrezPeca> capturada) {
		List<XadrezPeca> branca = capturada.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
		List<XadrezPeca> preta = capturada.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
		System.out.println("Pecas Capturadas:");
		System.out.print("Branca: ");
		System.out.print(ANSI_WHITE);// Para garantir que a impressão seja branca
		System.out.println(Arrays.toString(branca.toArray()));// Padrão de imprimir um array em java
		System.out.print(ANSI_RESET); // Para resetar a cor da impressão.

		System.out.print("Preta : ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preta.toArray()));
		System.out.print(ANSI_RESET);
	}

}

package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import jogoDeTabuleiro.Posicao;
import xadrez.XadrezException;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		XadrezPartida xadrezPartida = new XadrezPartida();
		List<XadrezPeca> capturada = new ArrayList<>();
		
		while(true) {
			try {
		UI.limparTela();
		//Função para imprimir as peças da partida. Cria a classe UI UserInterface
		UI.imprimirPartida(xadrezPartida, capturada); // esse metodo recebe a matriz de peças da partida.
		System.out.println();
		System.out.print("Origem : ");
		XadrezPosicao origem = UI.lerXadrezPosicao(sc);
		
		boolean[][] possiveisMovimentos = xadrezPartida.possiveisMovimentos(origem);
		UI.limparTela();
		UI.imprimirTabuleiro(xadrezPartida.getPecas(), possiveisMovimentos);		
		System.out.println();
		System.out.print("Destino: ");
		XadrezPosicao destino = UI.lerXadrezPosicao(sc);
		
		XadrezPeca capturadaPeca = xadrezPartida.performXadrezMover(origem, destino);
		
		if(capturadaPeca != null) {
			capturada.add(capturadaPeca);
		}
		
			}
			catch(XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Para aguardar o programa dar um ENTER
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Para aguardar o programa dar um ENTER
			}
		
		}
	}
}

package application;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.XadrezException;
import xadrez.XadrezPartida;
import xadrez.XadrezPeca;
import xadrez.XadrezPosicao;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		XadrezPartida xadrezPartida = new XadrezPartida();
		List<XadrezPeca> capturada = new ArrayList<>();

		while (!XadrezPartida.getCheckMate()) {
			try {
				UI.limparTela();
				// Função para imprimir as peças da partida. Cria a classe UI UserInterface
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

				if (capturadaPeca != null) {
					capturada.add(capturadaPeca);
				}
				
				// Testar movimento especial promoção.
				if(xadrezPartida.getPromocao() != null) {
					System.out.print("Entre com a peça promovida (B/C/R/Q): ");
					String tipo = sc.nextLine().toUpperCase(); // Para maiuscula
					if(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("R") && !tipo.equals("Q")){
						System.out.println("Valor invalido. Entre com (B/C/R/Q): ");
						tipo = sc.nextLine().toUpperCase();
						
					}
					xadrezPartida.substituirPromocaoPeca(tipo);
				}
				
			}
			
		
			catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Para aguardar o programa dar um ENTER
			} 
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); // Para aguardar o programa dar um ENTER
			}
		}
		UI.limparTela();
		UI.imprimirPartida(xadrezPartida, capturada);
	}
}

package application;

import java.util.Locale;
import java.util.Scanner;

import xadrez.XadrezPartida;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		XadrezPartida xadrezPartida = new XadrezPartida();
		
		//Função para imprimir as peças da partida. Cria a classe UI UserInterface
		UI.imprimirTabuleiro(xadrezPartida.getPecas()); // esse metodo recebe a matriz de peças da partida.
		
		
		sc.close();

	}

}

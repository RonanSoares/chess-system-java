package application;

import java.util.Locale;
import java.util.Scanner;

import jogoDeTabuleiro.Posicao;
import jogoDeTabuleiro.Tabuleiro;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		Tabuleiro tab = new Tabuleiro(8, 8);
		System.out.println(tab);
		
		sc.close();

	}

}

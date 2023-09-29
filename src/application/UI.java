package application;

import xadrez.XadrezPeca;

public class UI {
	
	public static void imprimirTabuleiro(XadrezPeca[][] pecas) {
		
		for(int i = 0; i < pecas.length; i++) {
			System.out.print((8-i) + " ");
			for(int j = 0; j < pecas.length; j++) {
				imprimirPeca(pecas[i][j]); // Imprimir as peças				
			}
			System.out.println(); // Quebra de linha.
		}
		System.out.println("  a b c d e f g h");
	}
	
	// Cria um método para imprimir uma peça de xadrez
	public static void imprimirPeca(XadrezPeca peca) {
		if(peca == null) {
			System.out.print("-");
		}else {
			System.out.print(peca);
		}
		System.out.print(" "); // Imprime espaço em branco para as peças não ficarem juntas.
	}

}

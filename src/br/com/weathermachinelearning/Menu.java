package br.com.weathermachinelearning;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Menu {

	public static void main(String[] args) {

		boolean loop = true;
		ModeloTreinamento modeloTreinamento = new ModeloTreinamento();
		ModeloTeste predictMin = new ModeloTeste();
		ModeloTeste predictMax = new ModeloTeste();

		while (loop) {

			Scanner read = new Scanner(System.in);

			System.out.println("\nEscolha uma opcao:");
			System.out.println("1 - Montar modelo de treinamento");
			System.out.println("2 - Realizar previsao");
			System.out.println("9 - Sair");

			int opcao = 0;

			try {
				opcao = read.nextInt();

			} catch (Exception e) {
				System.out.println("\nOpcao invalida!\n");
			}

			switch (opcao) {
			case 1:
				PrintStream oldErr = System.err;
				PrintStream newErr = new PrintStream(new ByteArrayOutputStream());
				System.setErr(newErr);
				modeloTreinamento.build();
				System.setErr(oldErr);
				break;
			case 2:
				try {

					System.out.println("\nEntre com o nome da cidade desejada:");
					String cidade = read.next();

					predictMin.predict(cidade, false);
					predictMax.predict(cidade, true);

					while (opcao != 9) {

						System.out.println("\nEscolha uma opcao:");
						System.out.println("1 - Mostrar resultados previstos");
						System.out.println("2 - Mostrar variacao entre resultados reais e previstos");
						System.out.println("3 - Mostrar a quantidade de acertos por margem de erro");
						System.out.println("4 - Mostrar pesos para previsao da temperatura");
						System.out.println("9 - Retornar ao menu inicial");
						opcao = read.nextInt();

						switch (opcao) {
						case 1:
							clearScreen();
							System.out.println("\nTEMPERATURA MINIMA");
							predictMin.mostrarResultados();
							System.out.println("\nTEMPERATURA MAXIMA");
							predictMax.mostrarResultados();
							break;
						case 2:
							clearScreen();
							System.out.println("\nTEMPERATURA MINIMA");
							predictMin.mostrarVariacao();
							System.out.println("\nTEMPERATURA MAXIMA");
							predictMax.mostrarVariacao();
							break;

						case 3:
							clearScreen();
							System.out.println("\nTEMPERATURA MINIMA");
							predictMin.mostrarMargemErro();
							System.out.println("\nTEMPERATURA MAXIMA");
							predictMax.mostrarMargemErro();
							break;
						case 4:
							clearScreen();
							System.out.println("\nTEMPERATURA MINIMA");
							predictMin.mostrarPesos();
							System.out.println("\nTEMPERATURA MAXIMA");
							predictMax.mostrarPesos();
							break;
						case 9:
							break;

						default:
							System.out.println("\nOpçao invalida!");
							break;
						}

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 9:
				loop = false;
				break;
			default:
				clearScreen();
				System.out.println("\nOpcao invalida!");
				break;
			}

		}

	}

	public static void clearScreen() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

}

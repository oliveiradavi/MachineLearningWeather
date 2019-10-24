package br.com.weathermachinelearning;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Scanner;

import weka.classifiers.functions.SMOreg;

public class ModeloTreinamento {

	public void build() {

		String path = "data/";
		String fileName;
		DataSource source;
		Instances trainDataset;
		SMOreg smo;

		Scanner read = new Scanner(System.in);

		System.out.println("\nEntre com o nome da cidade desejada:");

		fileName = read.nextLine();

		try {

			System.out.println("\nAguarde enquanto os modelos sao processados...");

			// Criação do modelo para temperatura máxima

			// Carrega dados de treinamento
			source = new DataSource(path + fileName + "_max.arff");
			trainDataset = source.getDataSet();

			// Determina que o último atributo deve ser o índice da classe
			trainDataset.setClassIndex(trainDataset.numAttributes() - 1);

			// Monta o modelo de treinamento
			smo = new SMOreg();
			smo.buildClassifier(trainDataset);

			// Escreve o modelo em um arquivo
			weka.core.SerializationHelper.write(path + fileName + "_max.model", smo);

			// Criação do modelo para temperatura mínima

			source = new DataSource(path + fileName + "_min.arff");
			trainDataset = source.getDataSet();
			trainDataset.setClassIndex(trainDataset.numAttributes() - 1);

			smo = new SMOreg();
			smo.buildClassifier(trainDataset);

			weka.core.SerializationHelper.write(path + fileName + "_min.model", smo);

			System.out.println("Modelos criados com sucesso!");

		} catch (Exception e) {
			System.out.println("Erro na criacao dos modelos de treinamento");
		}

	}

}

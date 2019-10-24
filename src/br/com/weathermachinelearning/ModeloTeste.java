package br.com.weathermachinelearning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import weka.classifiers.functions.SMOreg;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ModeloTeste {

	private int margemDeErro[];
	private ArrayList<Double> diffArray;
	private double[] predicted;
	SMOreg smo;

	public void predict(String fileName, boolean tipo) throws Exception {

		String path = "data/";

		if (tipo == true) {
			fileName += "_max";
		} else {
			fileName += "_min";
		}

		smo = (SMOreg) weka.core.SerializationHelper.read(path + fileName + ".model");
		// System.out.println(smo);

		// Carrega dados para testes
		DataSource source = new DataSource(path + fileName + "_teste.arff");
		Instances testDataset = source.getDataSet();

		// Determina que o último atributo deve ser o índice da classe
		testDataset.setClassIndex(testDataset.numAttributes() - 1);

		// loop through the new dataset and make predictions

		double[] real = new double[testDataset.numInstances()];
		predicted = new double[testDataset.numInstances()];

		for (int i = 0; i < testDataset.numInstances(); i++) {
			// get class double value for current instance
			real[i] = testDataset.instance(i).classValue();

			// get Instance object of current instance
			Instance newInst = testDataset.instance(i);

			// call classifyInstance, which returns a double value for the class
			predicted[i] = smo.classifyInstance(newInst);

		}

		margemDeErro = new int[4];

		diffArray = new ArrayList();

		for (int i = 0; i < testDataset.numInstances(); i++) {

			if (real[i] > 0) {

				double diferenca = (Math.abs(real[i] - predicted[i]));

				diffArray.add(diferenca);

				if (diferenca < 1) {
					margemDeErro[0]++;
				} else if (diferenca < 2) {
					margemDeErro[1]++;
				} else if (diferenca < 3) {
					margemDeErro[2]++;
				} else {
					margemDeErro[3]++;
				}

			}

		}

	}

	public void mostrarResultados() {

		System.out.println("TEMPERATURAS PREVISTAS:\n");

		for (int i = 0; i < predicted.length; i++) {
			System.out.printf("%.4f\n", predicted[i]);
		}
	}

	public void mostrarMargemErro() {

		System.out.println("QUANTIDADE DE ACERTOS PARA CADA FAIXA DE TEMPERATURA:\n");

		int total = 0;

		for (int i = 0; i < margemDeErro.length; i++) {

			if ((i + 1) < margemDeErro.length) {
				System.out.println("Entre " + i + "°C e " + (i + 1) + "°C: " + margemDeErro[i]);
			} else {
				System.out.println("Acima de " + i + "°C:    " + margemDeErro[i]);
			}

			total += margemDeErro[i];
		}
		System.out.println("Total: " + (total));
	}

	public void mostrarVariacao() {

		System.out.println("VARIACAO EM GRAUS CELSIUS ENTRE TEMPERATURA REAL E PREVISTA:\n");

		Collections.sort(diffArray);

		for (double n : diffArray) {
			System.out.printf("%.4f\n", n);
		}
	}

	public void mostrarPesos() {
		System.out.println(smo);
	}

}
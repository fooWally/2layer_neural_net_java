package run_main;


import java.util.ArrayList;
import java.util.Arrays;

import core.IrisList;
import core.Neuralnet;

public class runIris {

	public static void main(String[]args) throws Exception {
		
		IrisList irises = new IrisList();
		String srcFile = "iris.txt";
		ArrayList<Double[][]> finalIrisData = irises.getScaledData(irises, srcFile);
		
		Neuralnet nn = new Neuralnet(4, 4, 3);
		Double N = 0.5, M = 0.1;
		int iterations = 1000;
		ArrayList<Double[][]> trainingData = finalIrisData;
		nn.train(trainingData, iterations, N, M);

		ArrayList<Double[][]> testData = new ArrayList<Double[][]>();
		Double[][] iris = finalIrisData.get(110);
		testData.add(iris);
		System.out.println("Answer : "+ Arrays.toString(iris[0])+" is "+Arrays.toString(iris[1]));
		nn.predict(testData);
		System.exit(0);
	}

}

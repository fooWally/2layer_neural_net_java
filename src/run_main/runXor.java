package run_main;

import java.util.ArrayList;

import core.Neuralnet;

public class runXor {

	public static void main(String[] args) {

		Neuralnet nn = new Neuralnet(2, 2, 1);

		Double[][] e0 = {{0.,0.},{0.}};
		Double[][] e1 = {{0.,1.},{1.}};
		Double[][] e2 = {{1.,0.},{1.}};
		Double[][] e3 = {{1.,1.},{0.}};
		ArrayList<Double[][]> trainData = new ArrayList<Double[][]>();
		trainData.add(e0);
		trainData.add(e1);
		trainData.add(e2);
		trainData.add(e3);
		
		//System.out.println(trainData);
		//N : LEARNING RATE, M : MOMENTUTM FACTOR
		double N = 0.5, M = 0.1;
		int iterations = 1000;
		nn.train(trainData, iterations, N, M);
		ArrayList<Double[][]> DataUnknown = new ArrayList<Double[][]>();
		DataUnknown.add(e3);
		nn.predict(DataUnknown);

		System.exit(0);
	}
}

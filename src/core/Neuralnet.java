package core;

import java.util.ArrayList;
import java.util.Arrays;

import core.Utility;

public class Neuralnet {

	private int ni, nh, no;//number of input nodes, hidden nodes and output node
	private Double[][] wi, wo;
	private Double[] ai, ah, ao;
	private Double[][] ci, co;

	public Neuralnet() {}

	public Neuralnet(int ni, int nh, int no) {
		this.ni = ni+1; this.nh = nh; this.no = no;
		// activations for input/hidden/output nodes
		this.ai = new Double[this.ni];
		this.ah = new Double[this.nh];
		this.ao = new Double[this.no];
		Arrays.fill(this.ai, 1.0);
		Arrays.fill(this.ah, 1.0);
		Arrays.fill(this.ao, 1.0);

		// create weight matrices of zeros of size ni by nh and nh by no.
		// set their entries with ranodm numbers btw 0 and 2.
//		Random r = new Random();
		this.wi = new Double[this.ni][this.nh];
		this.wo = new Double[this.nh][this.no];
		for (int i = 0; i < this.ni; i++) {
			for (int j = 0; j < this.nh; j++) {
				this.wi[i][j] = Math.random()*2.0;
			}
		}
		for (int j = 0; j < this.nh; j++) {
			for (int k = 0; k < this.no; k++) {
				this.wo[j][k] = Math.random()*2;
			}
		}
		// new Double[1] is filled with null. cf. new double[1] is filled with 0.0
		this.ci = new Double[this.ni][this.nh];
		this.co = new Double[this.nh][this.no];
		for(Double[] row: this.ci) {
			Arrays.fill(row, 0.0);
		}
		for(Double[] row: this.co) {
			Arrays.fill(row, 0.0);
		}
	}

	public Double[] forwardPropagate(Double[] xTrain) {
		if(xTrain.length != this.ni-1) {
			System.out.println("wrong number of inputs");
		}
		for (int i = 0; i < this.ni-1 ; i++) {
			this.ai[i] = xTrain[i];
		}
		for (int j = 0; j < this.nh ; j++) {
			double sum = 0.0;
			for (int i = 0; i < this.ni; i++) {
				sum += this.ai[i]*this.wi[i][j];
			}
			this.ah[j] = Utility.sigmoid(sum);
		}
		for (int k = 0; k < this.no; k++) {
			double sum = 0.0;
			for (int j = 0; j < this.nh; j++) {
				sum += this.ah[j]*this.wo[j][k];
			}
			this.ao[k] = Utility.sigmoid(sum);
		}
		return this.ao;
	}

	public Double backPropagate(Double[] yTrain, double N, double M) {
		if(yTrain.length != this.no) {
			System.out.println("wrong number of target values");
		}
		//calculate error for output/hidden
		Double[] outputDeltas = new Double[this.no];
		for (int k = 0; k < this.no; k++) {
			double error = yTrain[k] - this.ao[k];
			outputDeltas[k] = Utility.dsigmoid(this.ao[k])*error;
		}
		Double[] hiddenDeltas = new Double[this.nh];
		for (int j = 0; j < this.nh; j++) {
			double error = 0.0;
			for (int k = 0; k < this.no; k++) {
				error += outputDeltas[k]*this.wo[j][k];
			}
			hiddenDeltas[j] = Utility.dsigmoid(this.ah[j])*error;
		}
		//update output/input weights
		for (int j = 0; j < this.nh ; j++) {
			for (int k = 0; k < this.no ; k++) {
				double change = outputDeltas[k]*this.ah[j];
				this.wo[j][k] += N*change + M*this.co[j][k];
				this.co[j][k] = change;
				
			}
		}
		for (int i = 0; i < this.ni ; i++) {
			for (int j = 0; j < this.nh ; j++) {
				double change = hiddenDeltas[j]*this.ai[i];
				this.wi[i][j] +=  N*change + M*this.ci[i][j];
				this.ci[i][j] = change;
			}
		}
		double error = 0.0;
		for (int k = 0; k < yTrain.length; k++) {
			error += 0.5*Math.pow((yTrain[k]- this.ao[k]),2);
		}
		return error;
	}
	
	public void predict(ArrayList<Double[][]> DataUnknown) {
		for(Double[][] datum : DataUnknown) {
			Double[] prediction = forwardPropagate(datum[0]);
//			DecimalFormat df = new DecimalFormat("#.##");
//			Arrays.asList(prediction).forEach(x->df.format(x));
			for(int i=0 ; i<prediction.length ; i++) {
				Long roundVal = Math.round(prediction[i]);
				prediction[i] = (double) roundVal; //replace vals with rounded vals in prediction
			}
			System.out.println(Arrays.toString(datum[0]) + "->" + Arrays.toString(prediction));
		}
	}
	
	public void train(ArrayList<Double[][]> trainData, int iterations, double N, double M) {
		//N: learning rate, M: momentum factor
		for (int i = 0; i < iterations;i++) {
			double error = 0.0;
			for(Double[][] datum: trainData) {
				Double[] xTrain = datum[0]; 
				Double[] yTrain = datum[1];
				forwardPropagate(xTrain);
				error += backPropagate(yTrain, N, M);
			}
			if ((i+1)%10 == 0) {
				System.out.println("numIters : "+ (i+1));
				System.out.println("error = "+ error);
			}
//		return error;
		}
	}

	public Double[][] getWi() { return wi;}
	public Double[][] getWo() {	return wo;}

}

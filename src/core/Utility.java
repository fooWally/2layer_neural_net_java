package core;

import java.util.Random;
public class Utility {

	//Using these static methods, we don't need to create an object from this class 
	public static double sigmoid(double x) {
		return Math.tanh(x);
	}

	public static double dsigmoid(double y) {
		return 1.0-y*y ;
	}
	
	public static double sigmoid1(double x) {
		return (1/( 1 + Math.exp(-x)));
	}
	public static double dsigmoid1(double y) {
		return y*(1-y) ;
	}
	
	public static String arraytoString(double[] arr) {
		String str = "[";
		for(double e: arr) {
			str += e +",";
		}
		return str.substring(0, str.length()-1) + "]";
	}

	public static double D(double a, int power) {
		return Math.pow(a, power);
	}
	
	public static double[] arrayOne(int n) {
		double [] arr = new double[n];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = 1.0;
		}
		return arr;
	}

	public static void C() {
		//4d array
		int[][][][] arr = new int[4][2][1][3];
		for(int[][][] x : arr) {
			for(int[][] y : x) {
				for(int[] z : y) {
					for(int e : z) {
						System.out.println(e);
					}
				}
			}
		}
	}
	//4 by 5 - 2D array 
	public static void B() {
		int[] arrInner = {10,11,12,13,14};
		int[][] arrOuter = new int[4][arrInner.length];
		for (int i=0;i<arrOuter.length;i++) {
			arrOuter[i] = arrInner;
		}
	}
	public static void A() {
		Random random = new Random();
		double[][] arr2d = new double[3][];//{arr0, arr1, arr2};
		for (int i = 0; i < arr2d.length; i++) {
			System.out.println(i);
			arr2d[i] = random.doubles(10, 0, 2).toArray();
			System.out.println(arr2d[i]);
			for (int j = 0; j < arr2d[i].length; j++) {
				System.out.println(arr2d[i][j]);
			}
		}
	}





}
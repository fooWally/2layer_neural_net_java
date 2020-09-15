package core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ListIterator;

public class IrisList implements Serializable {
	/*
	 * build an arrayList of the iris data set by reading iris.txt
	 * create Object of IrisList and store it to irisList.txt
	 * irisList object consists of arrays for data and oneHotEncoder 
	 * for 3 sepecies.
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Double[][]> list3D = new ArrayList<Double[][]>();

	public IrisList() {}
	public IrisList(ArrayList<Double[][]> irisList) {
		this.list3D = irisList;
	}

	public ArrayList<Double[][]> getIrisList() {
		return list3D;
	}
	public void setIrisList(ArrayList<Double[][]> irisList) {
		this.list3D = irisList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//srcFile = "iris.txt";
	//IrisList irises = new IrisList(); obj = irises
	public ArrayList<Double[][]> getScaledData(IrisList obj, String srcFile) throws Exception{
		ArrayList<Double[][]> list3D = obj.build3DList(srcFile);
		ArrayList<Double[]> scaledIrisData = obj.scaleData(list3D);
		ListIterator<Double[]> iter1 = scaledIrisData.listIterator();
		ArrayList<Double[][]> finalIrisData = new ArrayList<Double[][]>() ;
		for (Double[][] iris : list3D) {
			Double[] label = iris[1];
			Double[] datum = iter1.next();
			Double[][] elem = {datum, label}; //make a pair
			finalIrisData.add(elem);
		}
		
		String filePath = "E:\\java_practice\\Neuralnet\\irisObj.txt";
		writeObjToFile(obj, filePath);
		return finalIrisData;
	}

	//srcFile = "iris.txt"
	public ArrayList<Double[][]> build3DList(String srcFile) throws Exception{
		//build 3d list from srcFile and return it to scaleData method
		FileInputStream fstream = new FileInputStream(srcFile);// Open the file
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line;
		while((line = br.readLine()) != null){ //Read File Line By Line
			Double[] label = new Double[3];	   //{0,0,0} for oneHotEncoder 
			Arrays.fill(label, 0.0);
			String irisName = line.substring(line.lastIndexOf(',')+1);
			if (irisName.contains("setosa")){
				label[0] = 1.0;
			}else if(irisName.contains("versicolor")){
				label[1] = 1.0;
			}else {
				label[2] = 1.0;
			}
			//e.g label = {1,0,0}, {0,1,0}, {0,0,1}
			String str = line.substring(0,15);	//"4.7,3.2,1.3,0.2"
			String[] strArray = str.split(",");	//{"4.7","3.2","1.3","0.2"}
			Double[] datum = new Double[strArray.length];//{0.0,0.0,0.0,0.0}
			for(int i=0; i<strArray.length; i++) {
				datum[i] = Double.valueOf(strArray[i]);	 //{4.7,3.2,1.3,0.2}
			}
			Double[][] elem = {datum, label};	//elem = {{4.7,3.2,1.3,0.2},{0,0,1}}
			list3D.add(elem);//list3D is a list of 150 elements = {elem0,elem1,...,elem149} 
		}
		fstream.close();//close the input stream
		return list3D;
	}

	public ArrayList<Double[]> scaleData(ArrayList<Double[][]>  list3d) throws Exception{
		ArrayList<Double> col0 = new ArrayList<Double>();
		ArrayList<Double> col1 = new ArrayList<Double>();
		ArrayList<Double> col2 = new ArrayList<Double>();
		ArrayList<Double> col3 = new ArrayList<Double>();
		for (Double[][] iris : list3d) {
			//System.out.println(Arrays.toString(iris[0]) + ";" + Arrays.toString(iris[1]));
			col0.add(iris[0][0]);
			col1.add(iris[0][1]);
			col2.add(iris[0][2]);
			col3.add(iris[0][3]);
		}
		Double max0 = Collections.max(col0);
		Double max1 = Collections.max(col1);
		Double max2 = Collections.max(col2);
		Double max3 = Collections.max(col3);

		ArrayList<Double[]> scaledIrisList = new ArrayList<Double[]>();
		for(int i=0; i < col0.size() ;i++) {
			Double[] arr= new Double[4];
			arr[0] = col0.get(i)/max0;
			arr[1] = col1.get(i)/max1;
			arr[2] = col2.get(i)/max2;
			arr[3] = col3.get(i)/max3;
			scaledIrisList.add(arr);
		}
		return scaledIrisList;
	}
	//String filePath = "E:\\java_practice\\Neuralnet\\irisObj.txt";
	public void writeObjToFile(IrisList irisObj, String filePath) throws Exception {
		FileOutputStream fileOut = new FileOutputStream(filePath);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(irisObj);
		objOut.close();
		System.out.println("obj successfully written to a file");
	}

	public IrisList readObjFromFile(String filePath) throws Exception {
		FileInputStream fileIn = new FileInputStream(filePath);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		IrisList irisList = (IrisList) objIn.readObject();
		objIn.close();
		return irisList;
	}

}


import Jama.*;
import java.util.Scanner;
import java.util.ArrayList;

public class CSFinal {

public static void main(String[] args) {
    

Scanner s = new Scanner (System.in);
System.out.println("How many people?");
    int m = s.nextInt(); // mxm matrix
    
System.out.println("What are their names?");
    ArrayList<String> name = new ArrayList<String>(); //people anmes
    for(int i = 0;i<=m;i++){
      name.add(s.nextLine());}
    
String[] names = new String[m];
for(int i = 0;i<m;i++){
    names[i] = name.get(i+1);
}


double[][] input = new double[m][m];

System.out.println("Enter values for the 'A' matrix:");
for(int i=0;i<m;i++){
      for (int j =0;j<m;j++)
          input [i][j] =s.nextDouble();}

System.out.println("How many groups would you like the people to be clustered into?");
int n = s.nextInt();

    Cluster one = new Cluster(input); // create cluster w input
    double[][] inputLap = one.lap(input); // a --> lap matrix
    Matrix matrix = new Matrix(inputLap); //lap --> matrix object (a)
    EigenvalueDecomposition e = new EigenvalueDecomposition(matrix); 
    double[][] eigenVectors = e.getV().getArray(); //find eigenvectors
    double[][] totalE = one.findF(eigenVectors, (n/2));//number of fielder vectors used
    System.out.println("Here are your clusters:");
    
    Cluster two = new Cluster(totalE,names); //new cluster object with eigenvector matrix
    two.partitions(totalE, n); 
}
}





package csfinal;
import java.util.ArrayList;

public class Cluster {
    private double[][] lapMatrix;
    private double[][] eigMatrix;
    private int row;
    private int col;
    private String[] names;
    
    public Cluster(double[][] a){ //original cluster to lapmatrix
        row = a.length;
        col = a[0].length;
    }
    
    public Cluster(double[][] a, String[] names){ //eigenvalue matrix to clustered names
        eigMatrix = a;
        this.names = names;
    }
    
    
    public double[][] lap(double[][] a){ //a matrix to lap matrix
        int count = 0;
        double[][] d = new double[row][col];
        double[][] l = new double[row][col];
        for(int i = 0;i<row-1;i++){ //find degree matrix
            for(int j = 0; j<col;j++){
                if(a[i][j] != 0)
                count+=a[i][j];
            }
            d[i][i] = count;
            count = 0;
        }
        
        for (int i = 0;i<row;i++){ //subtract a matrix from d matrix
            for(int j = 0; j<col;j++){
                l[i][j] = d[i][j] - a[i][j];
            }
        } 
        lapMatrix = l; //results in laplacian matrix

        return lapMatrix;
    }
    
    public double[][] findF (double[][] input, int num){ //finds the 1+n/2 number of fielder vectors from eigenvector 2D array
        double[][] totalF = new double [input.length][num];
        for(int i = 0;i<input.length;i++){
            for(int j = 0; j<num;j++){
                totalF[i][j] = input[i][j+1];
            }
        }
        return totalF;
    }
    
    public void partitions (double[][] matrix, int numPartitions){ //partitions fielder vectors based on positive/nevative
    
    int x = (int)(Math.log(numPartitions)/(Math.log(2))); // n = number of partitions x = number of vectors considered
    ArrayList<Integer>[] partitions = new ArrayList[numPartitions];
    for (int i = 0; i<numPartitions;i++){
        partitions[i] = new ArrayList<>();
    }
    
    for (int i = 0; i<matrix.length; i++){
        int sum = 0;
        for (int j = 0; j<x; j++){
            if (matrix[i][j]>0)
                sum+= Math.pow(2,j);
        }
        partitions[sum].add(i);
    }
    
    for(int i = 0; i<partitions.length; i++){
        for(int j = 0; j<partitions[i].size();j++)
        System.out.print(names[(partitions[i].get(j))] + " ");
    System.out.println();
    }
    }
    

}



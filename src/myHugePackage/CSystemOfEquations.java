package myHugePackage;
import javax.sound.midi.SysexMessage;
import java.text.DecimalFormat;
import java.util.*;
public class CSystemOfEquations {
    private int width;
    private int height;
    private double[][] systemOfEquations;
    private double[] solutionsOfEquations;
    //private DecimalFormat df = new DecimalFormat(#)

    private double findDeterminant(double Matrix[][]){
        return (Matrix[0][0] * Matrix[1][1] * Matrix[2][2]
        + Matrix[0][1] * Matrix[1][2] * Matrix[2][0]
        + Matrix[0][2] * Matrix[1][0] * Matrix[2][1]
        - (Matrix[0][2] * Matrix[1][1] * Matrix[2][0]
        + Matrix[0][1] * Matrix[1][0] * Matrix[2][2]
        + Matrix[0][0] * Matrix[1][2] * Matrix[2][1]));
    }
    public CSystemOfEquations(int width1, int height1){
        width = width1;
        height = height1;
        systemOfEquations = new double[height][width];
        solutionsOfEquations = new double[width - 1];
    }

    public void printSolutions(){
        System.out.println("Solutions of system of equations:");
        for (var solution:
             solutionsOfEquations) {
            System.out.print(String.format("%.2f", solution) + " ");
        }
    }

    public double[] solveTheEquations() {
        //Creating normal matrix
	    double[][] normalMatrix = new double[width - 1][width];

        //Finding normal matrix
        for (int i = 0; i < width - 1; i++) { //which element to define row
            for (int j = 0; j < width - 1; j++) { //which element to define column
                //defining i j element
                //i j element is the sum
                for (int k = 0; k < height; k++) {
                    normalMatrix[i][j] += systemOfEquations[k][i] * systemOfEquations[k][j];
                }
            }
        }

        //Finding normal vector
        for (int i = 0; i < width - 1; i++) {
            //Each i element is a sum
            for (int j = 0; j < height; j++) {
                normalMatrix[i][width - 1] += systemOfEquations[j][i] * systemOfEquations[j][width -1];
            }
        }

        //testing print
        System.out.print("Normal matrix with normal vector:\n");
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(normalMatrix[i][j] + " ");
            }
            System.out.println();
        }

        if (width - 1 == 3) {
            //find determinant
            double det = findDeterminant(normalMatrix);
            System.out.println("Determinant of normal matrix: " + det);

            //Solving using square roots
            double[][] lMatrix = new double[width - 1][width - 1];

            lMatrix[0][0] = Math.sqrt(normalMatrix[0][0]);
            lMatrix[1][0] = normalMatrix[1][0] / lMatrix[0][0];
            lMatrix[2][0] = normalMatrix[2][0] / lMatrix[0][0];

            lMatrix[1][1] = Math.sqrt(normalMatrix[1][1] - lMatrix[1][0] * lMatrix[1][0]);
            lMatrix[2][1] = (normalMatrix[2][1] - lMatrix[2][0] * lMatrix[1][0]) / lMatrix[1][1];

            lMatrix[2][2] = Math.sqrt(normalMatrix[2][2] - lMatrix[2][0] * lMatrix[2][0] - lMatrix[2][1] * lMatrix[2][1]);

            //testing print
            System.out.println("L-Matrix:");
            for (int i = 0; i < width - 1; i++) {
                for (int j = 0; j < width -1; j++) {
                    System.out.print(String.format("%.2f", lMatrix[i][j]) + " ");
                }
                System.out.println();
            }


            //finding Y-vector
            double[] yVector = new double[width - 1];

            for (int i = 0; i < width - 1; i++) {
                double sum = 0;
                for (int j = 0; j < width - 1; j++) {
                    sum += -lMatrix[i][j] * yVector[j];
                }
                yVector[i] = (normalMatrix[i][width - 1] + sum) / lMatrix[i][i];
            }

            //testing Y-vector
            System.out.println("Y-vector:");
            for (int i = 0; i < width - 1; i++) {
                System.out.print(String.format("%.2f",yVector[i]) + ((i == width - 2)? "\n" : ", "));
            }

            //finding X-vector
            for (int i = width - 2; i >= 0; i--) {
                double sum = 0;
                for (int j = 0; j < width - 1; j++) {
                    sum += -lMatrix[j][i] * solutionsOfEquations[j];
                }
                solutionsOfEquations[i] = (yVector[i] + sum) / lMatrix[i][i];
            }
        }
        return solutionsOfEquations;
    }

    public void fillTheMatrix() {
        System.out.println("Enter the matrix of equations:");
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        for (int i = 0; i < systemOfEquations.length; i++) {
            //System.out.print("Enter " + (i+1) + " row: ");
            for (int j = 0; j < systemOfEquations[i].length; j++) {
                systemOfEquations[i][j] = sc.nextDouble();
            }
        }
        sc.close();
    }
}

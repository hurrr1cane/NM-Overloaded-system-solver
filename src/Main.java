import myHugePackage.CSystemOfEquations;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        int cols, rows;
        System.out.print("Enter the count of unknowns: ");
        cols = sc.nextInt();
        System.out.print("Enter the count of equations: ");
        rows = sc.nextInt();
        CSystemOfEquations mySystem = new CSystemOfEquations(cols + 1, rows);
        mySystem.fillTheMatrix();
        mySystem.solveTheEquations();
        mySystem.printSolutions();
        //System.out.print("\nPress any key to exit");
        Thread.sleep(999999999);
    }
}
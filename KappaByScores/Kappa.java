import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Shad
 */
 class CohenKappa 
{
    private int[][] confusionMat;  
    public void ReadConfusionMatFromFile(String fileName)
    {
        try
        {
            int i = 0;
            Scanner sc = new Scanner(new File(fileName));
            String Line = "";
            String[] strList;
            while(sc.hasNextLine())
            {
                Line = sc.nextLine();
                if(!Line.isEmpty())
                {
                    strList = Line.split("\t");
                    for(int j = 0; j < strList.length; ++j)
                    {
                        confusionMat[i][j] = Integer.valueOf(strList[j]);
                    }
                    i++;
                }
            }
            sc.close();
        }
        catch(Exception e)
        {
                    
        }
        
    }
    public CohenKappa(String args[])
    {
        int numClass = Integer.valueOf(args[1]);
        confusionMat = new int[numClass][numClass];
        ReadConfusionMatFromFile(args[0]);
        double totSum = 0.0;
        double trueCase = 0.0;
        double observedAgreement = 0.0;
        double byChanceAgrrement = 0.0;
        
        for(int i = 0; i < numClass; ++i)
        {
            for(int j = 0; j < numClass; ++j)
            {
                totSum += confusionMat[i][j];
            }
            trueCase +=  confusionMat[i][i];
        }
        observedAgreement = trueCase / totSum;
        
        double[] ClassAnnotator1 = new double[numClass]; 
        double[] ClassAnnotator2 = new double[numClass];
        double[] PercentAnnotator1 = new double[numClass]; 
        double[] PercentAnnotator2 = new double[numClass];
        
        //Class-wise summation: Annotator 1
        for(int i = 0; i < numClass; ++i)
        {
            for(int j = 0; j < numClass; ++j)
            {
                ClassAnnotator1[i] += confusionMat[i][j];
            }
        }
        
        //Class-wise summation: Annotator 2
        for(int i = 0; i < numClass; ++i)
        {
            for(int j = 0; j < numClass; ++j)
            {
                ClassAnnotator2[j] += confusionMat[i][j];
            }
        }

        //Class-wise percentage: Annotator 1 & Annotator 2
        for(int i = 0; i < numClass; ++i)
        {
            PercentAnnotator1[i] = ClassAnnotator1[i]/totSum;
            PercentAnnotator2[i] = ClassAnnotator2[i]/totSum;
        }
        
        //By chance agreement
        for(int i = 0; i < numClass; ++i)
        {
            byChanceAgrrement += (PercentAnnotator1[i] * PercentAnnotator2[i]); 
        }
        
        //Kappa value
        double k = (observedAgreement - byChanceAgrrement) / (1 - byChanceAgrrement);
        System.out.println("Cohen's Kappa inter-rater agreement: " + k);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
//        String confusionFile = "/media/M S AKHTAR/ABSA-Hindi/Code/Confusion";
//        String numClass = "2";
//        String input = confusionFile + "\t" + numClass;
//        new CohenKappa(input.split("\t"));
	new CohenKappa(args);
        // TODO code application logic here
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kappabyscores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Double.min;
import java.util.ArrayList;

/**
 *
 * @author My Pc
 */
public class AgreementByScore {
    static ArrayList<String> user1=new ArrayList<String>(5417);
    static ArrayList<String> user2=new ArrayList<String>(5417);
    static int count=0;
    static double agreement_score=0.0;
    static double total_agreement_score=0.0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        //reading two files for finding the agreement between them
        /*
        BufferedReader br1=new BufferedReader(new FileReader(args[0]));
        BufferedReader br2=new BufferedReader(new FileReader(args[1]));
        */
        BufferedReader br1=new BufferedReader(new FileReader("SEMI.txt"));
        BufferedReader br2=new BufferedReader(new FileReader("SEMIEVAL.txt"));
        String l1="";
        String l2="";
        
        while((l1=br1.readLine())!=null&&(l2=br2.readLine())!=null)
        {
          AgreementByScore kps=new AgreementByScore();
          String[] st1=l1.split("\t");
          String[] st2=l2.split("\t");
          for(int i=2;i<st1.length;i++)
          {
            user1.add(st1[i]);  
          }
           for(int i=2;i<st2.length;i++)
          {
            user2.add(st2[i]);  
          }
          
          kps.score(user1,user2);
          
        }
        
        System.out.println("count is "+count);
        
        System.out.println("Total Agreement score is "+total_agreement_score/count);
        //writing into file
        BufferedWriter bw=new BufferedWriter(new FileWriter("Output.txt"));
        bw.write("Total Agreement score is "+total_agreement_score/count);
        bw.close();
                
        
    }

    private void score(ArrayList<String> user1, ArrayList<String> user2) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        double max_score=(int) min(user1.size(),user2.size())*3;
        double min_score=(user1.size()+user2.size())*(-3);
        int yy=0;
        int yn=0;
        int ny=0;
        int nn=0;
        System.out.println(user1);
        System.out.println("------");
        
        System.out.println(user2);
        
        for(int i=0;i<user1.size();i++)
        {
            boolean isMatch=false;
            String[] u1=(user1.get(i)).split(":");
            String u_ote=u1[0];
            String u_cat=u1[1];
            String u_pol=u1[2];
            for(int j=0;j<user2.size();j++)
            {
                String[] u2=(user2.get(j)).split(":");
                String v_ote=u2[0];
                String v_cat=u2[1];
                String v_pol=u2[2];
                if(u_ote.contains(v_ote)||v_ote.contains(u_ote))
                {
                    isMatch=true;
                  
                    user1.remove(i);
                    user2.remove(j);  
                    i=i-1;
                   
                    //System.out.println("OTE matches");
                    //OTE matches
                    yy=yy+1;
                    //System.out.println("scores"+yy+" "+yn+" "+ny+" "+nn);
                    if(u_cat.equals(v_cat))
                    {
                        //CATegory matches
                        //System.out.println("CAT matches");
                        yy=yy-1;
                        yy=yy+2;
                        //System.out.println("scores"+yy+" "+yn+" "+ny+" "+nn);
                        if(u_pol.equals(v_pol))
                        {
                            //CATegory matches
                            //System.out.println("POL matches");
                            yy=yy-2;
                            yy=yy+3;
                            //System.out.println("scores"+yy+" "+yn+" "+ny+" "+nn);


                        }

                    }
                    break;
                }
                
                
            } 
            if(isMatch==false)
                {
                   yn=yn-3; 
                   
                    user1.remove(i);  
                    i=i-1;
                   //System.out.println("scores"+yy+" "+yn+" "+ny+" "+nn);
                }
        }
        
        if(user1.size()>0)
        {
            yn=yn-3;  
           
        }
        if(user2.size()>0)
        {
            ny=ny-3;
        }
        if(user1.size()==0&&user2.size()==0)
        {
            nn=nn+0;
        }
        
        System.out.println(yy+" "+yn+" "+ny+" "+nn);
        int total_score=(yy+yn+ny+nn);
        System.out.println("Total score "+total_score);
        System.out.println("Minimum score "+min_score);
        System.out.println("Maximum score "+max_score);
        if(total_score==0&&min_score==0&&max_score==0)
        {
            System.out.println("Agreement is update1");
            count=count+1;
            agreement_score=1.0;
            
        }
        else
        {
            System.out.println("Agreement is update2");
           
           
            count=count+1;
            
            agreement_score=((total_score-min_score)/(max_score-min_score)); 
            
        }
        total_agreement_score+=agreement_score;
        System.out.println("Agreement is "+agreement_score);
        user1.clear();
        user2.clear();
    }

    
}

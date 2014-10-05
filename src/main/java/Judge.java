import java.awt.image.SampleModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.stanford.nlp.tagger.maxent.History;


public class Judge {

   public static void main(String[] args) throws FileNotFoundException{
     File sampleout = new File("src/main/resources/SampleData/sample.out");
     File ourout = new File("src/main/resources/myout");
     ArrayList<String> sampleArrayList = new ArrayList<String>();
     ArrayList<String> hit = new ArrayList<String>();
     ArrayList<String> miss = new ArrayList<String>();
     Scanner s1 = new Scanner(sampleout);
     while(s1.hasNext()){
       String sfs = s1.nextLine();
       String[] c = sfs.split("\\|");
       sampleArrayList.add(c[2]);
       //sampleArrayList.add(sfs);
     }     
     Scanner s2 = new Scanner(ourout);
     while(s2.hasNext()){
       String temp = s2.nextLine();
       if(sampleArrayList.contains(temp)){
         hit.add(temp);
       }else{
     //    System.out.println(temp);
         miss.add(temp);
       }
     }
     double a = hit.size();
     double b = miss.size();
     double c = sampleArrayList.size();
     double precison = (a / (b + a));
     double recall = (a / c);
     System.out.println("Hit  " + hit.size() + "\n" + "miss " + miss.size() + "\n");
     System.out.println("Presion: " + precison);
     System.out.println("Recall: " + recall);
     System.out.println("F1 measure " + (2 * (precison * recall)) / (precison + recall));
   }  
 }
 

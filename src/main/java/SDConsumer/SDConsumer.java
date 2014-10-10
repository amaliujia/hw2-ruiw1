package SDConsumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.wsdl.Output;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.springframework.scripting.support.StaticScriptSource;

import SDAnnotator.SDSentenceAnnotator;
import Types.ABNERAnnotation;
import Types.SDGeneEntity;
import Types.SentenceAnnotation;

/**
 * This is a printer which print gene terms.
 * It also integrate statistic module
 * @author Rui Wang
 *
 */
public class SDConsumer extends CasConsumer_ImplBase{

  public String output;
   
  private Writer fileWriter = null;
  private HashMap<String, String> map;
  
  /**
   * Initialize output file.
   * @throws ResourceInitializationException
   *          throws when resource initialization fails.
   */
  public void initialize(){
    output = (String)getConfigParameterValue("OutputFile");
    try {
      fileWriter = new FileWriter(new File(output));//new BufferedWriter(new FileWriter(output));
    } catch (IOException e) {
      e.printStackTrace();
    }
    map = new HashMap<String, String>();
  }
  
  /**
   * @param aCAS
   *        CAS corresponding to complete processing
   *        
   * @throws ResourceProcessException
   *        throws when resource process fails.
   * @see org.apache.uima.collection.CasConsumer#processCas(CAS aCAS)
   */  
  public void processCas(CAS aCAS) throws ResourceProcessException {
    //FileWriter fileWriter;
    JCas jcas = null;
    try {
      jcas = aCAS.getJCas();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    
    FSIterator<Annotation> sentenceIterator = jcas.getAnnotationIndex(SentenceAnnotation.type).iterator();
    while(sentenceIterator.hasNext()){
      SentenceAnnotation aSentenceTag = (SentenceAnnotation)sentenceIterator.get();
      map.put(aSentenceTag.getSentenceID(), aSentenceTag.getText());
      sentenceIterator.moveToNext();
    }
    
    try {
      //fileWriter = new FileWriter(new File(output), true);
      FSIterator<Annotation> geneIterator = jcas.getAnnotationIndex(SDGeneEntity.type).iterator();
      while (geneIterator.hasNext()) {
        SDGeneEntity abnerAnnotation = (SDGeneEntity)geneIterator.get();
        String sentenceContent = map.get(abnerAnnotation.getSentenceID());
        // calculate proper positions in sentence for each gene term 
        int start = abnerAnnotation.getBegin();
        int end = abnerAnnotation.getEnd();
        String temp = sentenceContent.substring(0, start);
        int parta = countBlanks(temp);
        int partb = countBlanks(sentenceContent.substring(start, end));
        start = start - parta;
        end = end - parta - partb - 1;
        fileWriter.append(abnerAnnotation.getSentenceID() + "|" + start+ " " + end + "|" + abnerAnnotation.getEntity() + "\n");
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

 
  /**
   * dealloc and close everything
   */
  public void destroy() {
    try {
      fileWriter.close();
      this.statictics();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * This function is used to count blanks in string.
   * @param s
   *        target string to be judges blanks.
   * @return int
   *         return the numbers of blanks in input string
   */
  private int countBlanks(String s){
    int count = 0;
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i) == ' '){
         count++;
      }
    }
    return count;
    
  }
  
  /**
   * This is math function where I calculate Pricision, Recall and F1 score.
   * @throws FileNotFoundException
   */
  private void statictics() throws FileNotFoundException{
    File sampleout = new File("src/main/resources/SampleData/sample.out");
    File ourout = new File(output);
    ArrayList<String> sampleArrayList = new ArrayList<String>();
    ArrayList<String> hit = new ArrayList<String>();
    ArrayList<String> miss = new ArrayList<String>();
    Scanner s1;
    try {
      s1 = new Scanner(sampleout);
    } catch (FileNotFoundException e) { // if fail to find standard file, then return
     return;
    }
    while(s1.hasNext()){
      String sfs = s1.nextLine();
      sampleArrayList.add(sfs);
    }     
    Scanner s2;
    try {
      s2 = new Scanner(ourout);
    } catch (FileNotFoundException e) {// if fail to find output file.
      return;
    }
    while(s2.hasNext()){
      String temp = s2.nextLine();
      if(sampleArrayList.contains(temp)){
        hit.add(temp);
      }else{
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

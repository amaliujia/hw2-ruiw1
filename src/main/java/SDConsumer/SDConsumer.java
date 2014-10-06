package SDConsumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

import javax.wsdl.Output;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceProcessException;

import SDAnnotator.SDSentenceAnnotator;
import Types.ABNERAnnotation;
import Types.SDGeneEntity;
import Types.SentenceAnnotation;

public class SDConsumer extends CasConsumer_ImplBase{

  public final static String output = "src/main/resources/myout";
   
  private Writer fileWriter = null;
  private HashMap<String, String> map;
  
  public void initialize(){
    try {
      fileWriter = new FileWriter(new File(output));//new BufferedWriter(new FileWriter(output));
    } catch (IOException e) {
      e.printStackTrace();
    }
    map = new HashMap<String, String>();
  }
  
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
//        fileWriter.append(abnerAnnotation.getEntity() + "\n");
        //fileWriter.write(abnerAnnotation.getEntity() + "\n");
        geneIterator.moveToNext(); 
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  
  public void destroy() {
    try {
      fileWriter.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
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
}

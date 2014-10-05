package SDConsumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.wsdl.Output;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceProcessException;

import Types.ABNERAnnotation;
import Types.SDGeneEntity;
import Types.SentenceAnnotation;

public class SDConsumer extends CasConsumer_ImplBase{

  public final static String output = "src/main/resources/myout";
   
  private Writer fileWriter = null;
  
  public void initialize(){
    try {
      fileWriter = new FileWriter(new File(output));//new BufferedWriter(new FileWriter(output));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void processCas(CAS aCAS) throws ResourceProcessException {
    //FileWriter fileWriter;
    JCas jcas = null;
    try {
      jcas = aCAS.getJCas();
    } catch (Exception e) {
      e.printStackTrace();
    }   
    try {
      //fileWriter = new FileWriter(new File(output), true);
      FSIterator<Annotation> geneIterator = jcas.getAnnotationIndex(SDGeneEntity.type).iterator();
      while (geneIterator.hasNext()) {
        SDGeneEntity abnerAnnotation = (SDGeneEntity)geneIterator.get();
        fileWriter.append(abnerAnnotation.getEntity() + "\n");
        //fileWriter.write(abnerAnnotation.getEntity() + "\n");
        geneIterator.moveToNext(); 
      }
//      try {
//        fileWriter.close(); 
//      } catch (Exception e) {
//        System.out.println("writer close wrongly");
//        e.printStackTrace();
//      }
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
}

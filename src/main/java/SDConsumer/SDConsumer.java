package SDConsumer;

import java.io.File;
import java.io.FileWriter;

import javax.wsdl.Output;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceProcessException;

import Types.ABNERAnnotation;
import Types.SentenceAnnotation;

public class SDConsumer extends CasConsumer_ImplBase{

  public final static String output = "src/main/resources/myout";
   

  public void processCas(CAS aCAS) throws ResourceProcessException {
    FileWriter fileWriter;
    JCas jcas = null;
    try {
      jcas = aCAS.getJCas();
    } catch (Exception e) {
      e.printStackTrace();
    }   
    try {
      fileWriter = new FileWriter(new File(output), true);
      FSIterator<Annotation> geneIterator = jcas.getAnnotationIndex(ABNERAnnotation.type).iterator();
      while (geneIterator.hasNext()) {
        ABNERAnnotation abnerAnnotation = (ABNERAnnotation)geneIterator.get();
        fileWriter.append(abnerAnnotation.getEntity() + "\n");
        geneIterator.moveToNext(); 
      }

      fileWriter.close();
    }catch(Exception e){
      
    }
  }

}

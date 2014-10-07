package SDAnnotator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Vector;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.context.support.StaticApplicationContext;

import Types.ABNERAnnotation;
import Types.SentenceAnnotation;
import abner.Tagger;

/**
 * ABNER stands for a Biomedical Name Entity Recognizer.
 * @author Rui Wang
 */
public class SDABNERAnnotator extends JCasAnnotator_ImplBase{

  private  Tagger aABNERTagger;
  private int count = 0;
  
  /**
   * Overwrite initialize function to get a chance.
   * @param aContexct 
   */
  public void initialize(UimaContext aContext)
          throws ResourceInitializationException{

    System.out.println("I am in ABNERTagger-------------------------");
    aABNERTagger = new Tagger(0);
  }
  
  /**
   * This process runs ABNER
   * 
   * @param aJCas
   *        CAS corresponding to the complete processing
   * @throws AnalysisEngineProcessException
   *         happen when wrongly use UIMA objects
   *
   */
  public void process(JCas aJCas) throws AnalysisEngineProcessException {    
     FSIterator<Annotation> sentenceIterator = aJCas.getAnnotationIndex(SentenceAnnotation.type).iterator();
     while(sentenceIterator.hasNext()){
       //get sentence annotation, including sentence id and sentence content
       SentenceAnnotation aSentenceTag = (SentenceAnnotation)sentenceIterator.get();
       String s = aSentenceTag.getText();
       
       // utilzie ABNER tagger
       String[][] result = aABNERTagger.getEntities(s);
       for(int i = 0; i < result[1].length; i++){
           ABNERAnnotation abnerAnnotation = new ABNERAnnotation(aJCas);
           abnerAnnotation.setSentenceID(aSentenceTag.getSentenceID());
           abnerAnnotation.setEntity(result[0][i]);
           abnerAnnotation.setScore(1.0);
           abnerAnnotation.addToIndexes(aJCas);
           count++;
       }        
       sentenceIterator.moveToNext();
    }
  }
  
  public void destroy(){
   System.out.println("Final product ABNER  " + count); 
  }
}

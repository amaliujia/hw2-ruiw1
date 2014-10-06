package SDAnnotator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import com.aliasi.chunk.HmmChunker;
import com.aliasi.util.AbstractExternalizable;

import Types.ABNERAnnotation;
import Types.SDGeneEntity;
import Types.SentenceAnnotation;
import Types.lingpileAnnotation;


/**
 * 
 * This evaluator is used to compare two algorithms/models and
 * utilize strategy to combine output of these tow algorithms.
 * Strategy:
 * a) For a specific sentence, if ABNER and lingpipe mark the same gene tag. 
 * I think this is a strong confidence.
 * 
 * b) For a specific sentence, if lingpipe mark a gene tag but ABNER does not. 
 * I consider this is a suspicion, so I need to double check confidence of this gene tag. 
 * If lingpipe gives a confidence which is greater than my threshold, 
 * I think this tag should be correct. (I will discuss how to set this threshold in the following section)
 * 
 * c)For a specific sentence, if ABNER mark a gene tag but lingpipe does not. 
 * I consider this is not a strong confidence. So I give this tag up.
 *  
 * @author Rui Wang
 *
 */
public class SDEvaluatorAnnotator extends JCasAnnotator_ImplBase{
  private static final double threshold = 1.0;
  private static HashMap<String, ArrayList<String>> abnerHashMap;
  private int count = 0;
  
  /**
   * Overwrite initialize function to get a chance.
   * @param aContexct 
   */
  public void initialize(UimaContext aContext)
          throws ResourceInitializationException{
    abnerHashMap = new HashMap<String, ArrayList<String>>();
    System.out.println("I am in SDEvaluatorAnnotator-------------------------");
  }

  
  /**
   * The place where comparison and combination happens.
   * @param aJCas
   *            CAS corresponding to the completed processing
   * @throws AnalysisEngineProcessException
   *            Happens when wrongly use UIMA objects
   */
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // save output of ABNERAnnotator into hashmap
    FSIterator<Annotation> it = aJCas.getAnnotationIndex(ABNERAnnotation.type).iterator();
    while(it.hasNext()){
      ABNERAnnotation abnerAnnotation = (ABNERAnnotation)it.get();
      String sentenceID = abnerAnnotation.getSentenceID();
      ArrayList<String> arrayList;
      if(!abnerHashMap.containsKey(sentenceID)){
        arrayList = new ArrayList<String>();
        arrayList.add(abnerAnnotation.getEntity());
        abnerHashMap.put(sentenceID, arrayList);
      }else{
        arrayList = abnerHashMap.get(sentenceID);
        arrayList.add(abnerAnnotation.getEntity());
        abnerHashMap.put(sentenceID, arrayList);
      }
      it.moveToNext();
    }
    
    it = aJCas.getAnnotationIndex(lingpileAnnotation.type).iterator();
    while(it.hasNext()){
      lingpileAnnotation alingAnnotation = (lingpileAnnotation)it.get();
      String sentenceID = alingAnnotation.getSentenceID();
      String geneString = alingAnnotation.getText();
      //if lingpipe and ABNER mark same gene tag
      if(abnerHashMap.containsKey(sentenceID)){
        ArrayList<String> array = abnerHashMap.get(sentenceID);
        int i = 0;
        for(; i < array.size(); i++){
          if(array.get(i).equals(geneString)){ // they have something in common
            SDGeneEntity aSDGeneEntity = new SDGeneEntity(aJCas);
            aSDGeneEntity.setSentenceID(sentenceID);
            aSDGeneEntity.setEntity(geneString);
            aSDGeneEntity.setBegin(alingAnnotation.getBegin());
            aSDGeneEntity.setEnd(alingAnnotation.getEnd());
            aSDGeneEntity.addToIndexes(aJCas);
            count++;
            break;
          }
        }
        //lingpipe mark a tag that ABNER doesn't
        if(i >= array.size()){
          if(alingAnnotation.getScore() >= this.threshold){
            SDGeneEntity aSDGeneEntity = new SDGeneEntity(aJCas);
            aSDGeneEntity.setSentenceID(sentenceID);
            aSDGeneEntity.setEntity(geneString);
            aSDGeneEntity.setBegin(alingAnnotation.getBegin());
            aSDGeneEntity.setEnd(alingAnnotation.getEnd());
            aSDGeneEntity.addToIndexes(aJCas);
           // count++;
          }
        }
      }else{
       //  System.out.println("Size of map " + abnerHashMap.size() + "  " + alingAnnotation.getSentenceID() + "  " + alingAnnotation.getText() + "  " + alingAnnotation.getScore());         
      }
      it.moveToNext();
    }
    
  }
  public void destroy(){
    System.out.println("Final product Combination  " + count); 
   }
}

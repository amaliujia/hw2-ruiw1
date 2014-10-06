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

public class SDEvaluatorAnnotator extends JCasAnnotator_ImplBase{
  private static final double threshold = 0.1;
  private static HashMap<String, ArrayList<String>> abnerHashMap;
  private int count = 0;
  
  public void initialize(UimaContext aContext)
          throws ResourceInitializationException{
    abnerHashMap = new HashMap<String, ArrayList<String>>();
    System.out.println("I am in SDEvaluatorAnnotator-------------------------");
  }

  
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
   // System.out.println("I am in SDEvaluatorAnnotator-------------------------");
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
      if(abnerHashMap.containsKey(sentenceID)){
        ArrayList array = abnerHashMap.get(sentenceID);
        int i = 0;
        for(; i < array.size(); i++){
          if(array.get(i) == geneString){
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
        if(i >= array.size()){
          if(alingAnnotation.getScore() >= this.threshold){
            SDGeneEntity aSDGeneEntity = new SDGeneEntity(aJCas);
            aSDGeneEntity.setSentenceID(sentenceID);
            aSDGeneEntity.setEntity(geneString);
            aSDGeneEntity.setBegin(alingAnnotation.getBegin());
            aSDGeneEntity.setEnd(alingAnnotation.getEnd());
            aSDGeneEntity.addToIndexes(aJCas);
            count++;
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

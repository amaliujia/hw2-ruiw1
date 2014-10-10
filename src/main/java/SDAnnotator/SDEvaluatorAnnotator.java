package SDAnnotator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import com.aliasi.chunk.HmmChunker;
import com.aliasi.util.AbstractExternalizable;

import edu.cmu.deiis.types.GeneAnnotation;
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
  private static final double threshold = 0.6;
  private int count = 0;
  /**
   * Overwrite initialize function to get a chance.
   * @param aContexct 
   */
  public void initialize(UimaContext aContext){
    System.out.println("I am in SDEvaluatorAnnotator");
  }

  
  /**
   * The place where comparison and combination happens.
   * @param aJCas
   *            CAS corresponding to the completed processing
   * @throws AnalysisEngineProcessException
   *            Happens when wrongly use UIMA objects
   */
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    HashMap<String, Annotation> abnerHashMap = new HashMap<String, Annotation>();
    HashMap<String, Annotation> lingpipeHashMap = new HashMap<String, Annotation>();
    HashMap<String, Annotation> intersectionHashMap = new HashMap<String, Annotation>();
    int lingpipec = 0;
    int abener = 0;
    //save lingpipe annotator and abner annotator into different hashmap
    FSIterator<Annotation> it = aJCas.getAnnotationIndex(GeneAnnotation.type).iterator();
    while (it.hasNext()) {
        GeneAnnotation geneAnnotation = (GeneAnnotation) it.next();
        if(geneAnnotation.getCasProcessorId().equals("0")){
            lingpipeHashMap.put(geneAnnotation.getEntity(), geneAnnotation);
            lingpipec++;
        }else{
            abnerHashMap.put(geneAnnotation.getEntity(), geneAnnotation);
            abener++;
        }
    }
    
    //return gene tag marked by lingpipe if confidence is greater than 0.6
    Iterator iterator = lingpipeHashMap.keySet().iterator();
    while (iterator.hasNext()) {
      GeneAnnotation gene = (GeneAnnotation) lingpipeHashMap.get(iterator.next());
      if(gene.getConfidence() >= 0.6){
        SDGeneEntity aSDGeneEntity = new SDGeneEntity(aJCas);
        aSDGeneEntity.setSentenceID(gene.getSentenceID());
        aSDGeneEntity.setEntity(gene.getEntity());
        aSDGeneEntity.setBegin(gene.getBegin());
        aSDGeneEntity.setEnd(gene.getEnd());
        aSDGeneEntity.addToIndexes(aJCas);
        intersectionHashMap.put("" + gene.getBegin(), gene);
        intersectionHashMap.put("" + gene.getEnd(), gene);
        intersectionHashMap.put(gene.getEntity(), gene);
      }
    }
    
    iterator = abnerHashMap.keySet().iterator();
    while(iterator.hasNext()){
      GeneAnnotation gene = (GeneAnnotation) abnerHashMap.get(iterator.next());
      if((!intersectionHashMap.containsKey(gene.getBegin())) 
              && !(intersectionHashMap.containsKey(gene.getEnd()))
              && !(intersectionHashMap.containsKey(gene.getEntity()))
              ){
        SDGeneEntity aSDGeneEntity = new SDGeneEntity(aJCas);
        aSDGeneEntity.setSentenceID(gene.getSentenceID());
        aSDGeneEntity.setEntity(gene.getEntity());
        aSDGeneEntity.setBegin(gene.getBegin());
        aSDGeneEntity.setEnd(gene.getEnd());
        aSDGeneEntity.addToIndexes(aJCas); 
      }
    }
    
   // System.out.println(lingpipec + "  " + abener);
  }
  public void destroy(){
    System.out.println("Final product Combination  " + count); 
   }
}

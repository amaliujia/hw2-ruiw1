package SDAnnotator;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import Types.SDRuleBasedRecAnnotation;
import Types.SentenceAnnotation;


public class SDRuleBasedAnnotator extends JCasAnnotator_ImplBase {

  public void initialize(UimaContext aContext)
          throws ResourceInitializationException{

    System.out.println("I am in RuleBasedAnnotator-------------------------");
  }
  
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
//    PosTagNamedEntityRecognizer posTagNamedEntityRecognizer;
//    try {
//      posTagNamedEntityRecognizer = new PosTagNamedEntityRecognizer();
//      FSIterator<Annotation> it = aJCas.getAnnotationIndex(SentenceAnnotation.type).iterator();
//      while(it.hasNext()){
//        SentenceAnnotation tag =  (SentenceAnnotation)it.get();
//         String s = tag.getText();
//         // Call recognizer.
//         Map<Integer, Integer>map = posTagNamedEntityRecognizer.getGeneSpans(s);
//         Iterator mapIterator = map.keySet().iterator();
//         while(mapIterator.hasNext()){
//           Map.Entry entry = (Map.Entry) mapIterator.next(); 
//           SDRuleBasedRecAnnotation gene = new SDRuleBasedRecAnnotation(aJCas);
//           gene.setSentenceID(tag.getSentenceID());
//           gene.setBegin((Integer) entry.getKey());
//           gene.setEnd((Integer) entry.getValue());
//           gene.setEntity(s.substring(gene.getBegin(), gene.getEnd()));
//           gene.addToIndexes(aJCas);
//         }
//      } 
//    } catch (ResourceInitializationException e) {
//      e.printStackTrace();
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    }
    
  }

}

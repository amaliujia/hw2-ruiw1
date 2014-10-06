package SDAnnotator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import Types.SentenceAnnotation;


/**
 * Description: SentenceAnnotator is in charge of breaking every sentence into two parts: 
 * id and content, and utilize sentenceTag to record these two kinds of information.
 * @author Rui Wang
 * @version 1.0
 */
public class SDSentenceAnnotator extends JCasAnnotator_ImplBase{

  /**
   * This is a core part of Sentence Annotator
   * @param aJCas
   *            CAS corresponding to the completed processing
   * @throws AnalysisEngineProcessException
   *            Happens when wrongly use UIMA objects
   */
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
      String temp = aJCas.getDocumentText();
      String[] contents = temp.split(" ", 2);
      SentenceAnnotation tag = new SentenceAnnotation(aJCas);
      tag.setSentenceID(contents[0]);
      tag.setText(contents[1]);
      tag.addToIndexes(aJCas);
  }

}

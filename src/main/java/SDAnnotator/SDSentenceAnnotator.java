package SDAnnotator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import Types.SentenceAnnotation;

public class SDSentenceAnnotator extends JCasAnnotator_ImplBase{

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
      String temp = aJCas.getDocumentText();
      String[] contents = temp.split(" ", 2);
      SentenceAnnotation tag = new SentenceAnnotation(aJCas);
      tag.setSentenceID(contents[0]);
      tag.setText(contents[1]);
      tag.addToIndexes(aJCas);
  }

}

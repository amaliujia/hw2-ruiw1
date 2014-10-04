package SDAnnotator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Vector;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.context.support.StaticApplicationContext;

import abner.Tagger;

public class SDABNERAnnotator extends JCasAnnotator_ImplBase{

   private static Tagger aABNERTagger;

  
  public void initialize() throws ResourceInitializationException {
    //System.out.println("I am in ABNERTagger");
   // this.aABNERTagger = new Tagger();
  }
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
//    try {
//      this.initialize();
//    } catch (ResourceInitializationException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    String s = aJCas.getDocumentText();
//    Vector result = aABNERTagger.getWords(s);
//    System.out.println(result.toString());
  }

}

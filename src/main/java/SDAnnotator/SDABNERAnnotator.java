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
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.context.support.StaticApplicationContext;

import abner.Tagger;

public class SDABNERAnnotator extends JCasAnnotator_ImplBase{

   //private static Tagger aABNERTagger;
 // static final String
  private File file;
  private BufferedWriter bufferedWriter;
  private Tagger aABNERTagger;
 
  private FileWriter fileWriter;
  
  public void initialize(UimaContext aContext)
          throws ResourceInitializationException{

    System.out.println("I am in ABNERTagger-------------------------");
    aABNERTagger = new Tagger();
  }
  
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(new File("src/main/resources/myout"), true);
      Tagger aABNERTagger = new Tagger();
      String s = aJCas.getDocumentText();
      String[][] result = aABNERTagger.getEntities(s);
      for(int i = 0; i < result[1].length; i++){
        if(result[1][i].equals("DNA") || result[1][i].equals("RNA") || result[1][i].equals("Protein")){
          fileWriter.append(result[0][i] + "\n");
        }
      }
      fileWriter.close();
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }

}

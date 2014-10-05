package SDAnnotator;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.apache.uima.resource.ResourceInitializationException;

import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


/**
 * PosTagNamedEntityRecognizer uses stanford corenlp library to identify
 * Nouns in input text.
 * 
 * @author Rui Wang
 *
 */
public class PosTagNamedEntityRecognizer {

  private StanfordCoreNLP pipeline;
  private RuleBasedRecognizer ruleBasedRecognizer;

  /**
   * This function use stanford-nlp algorithms identify Nouns 
   * 
   * @throws ResourceInitializationException
   *          happens when cannot initialize file
   * @throws FileNotFoundException
   *          happens when file cannot be find.
   */
  
  public PosTagNamedEntityRecognizer() throws ResourceInitializationException, FileNotFoundException {
    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos");
    pipeline = new StanfordCoreNLP(props);
    ruleBasedRecognizer = new RuleBasedRecognizer();
  }
  /**
   * This function use stanford-nlp algorithms identify Nouns in input text
   * 
   * @param text
   *        the String we want to process
   * @return Map<Integer, Integer>
   *         This map saves the <begin position, end position> of name entities.
   */
  
  public Map<Integer, Integer> getGeneSpans(String text) {
    Map<Integer, Integer> begin2end = new HashMap<Integer, Integer>();
    Annotation document = new Annotation(text);
    pipeline.annotate(document);
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    for (CoreMap sentence : sentences) {
      List<CoreLabel> candidate = new ArrayList<CoreLabel>();
      for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
        String pos = token.get(PartOfSpeechAnnotation.class);
        if (pos.startsWith("NN")) {
             candidate.add(token);
        } else if (candidate.size() > 0) {
          ruleBasedRecognizer.deep(candidate, begin2end);      
          candidate.clear();
        }
      }
      if (candidate.size() > 0) {
        ruleBasedRecognizer.deep(candidate, begin2end);        
        candidate.clear();
      }
    }
    return begin2end;
  }
 
}
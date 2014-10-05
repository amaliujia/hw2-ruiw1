package SDAnnotator;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.stanford.nlp.ling.CoreLabel;


/**
 * Rule based recognizer accepts output of PosTagNamedEntityRecognizer and uses
 * a set of rules to judge gene mention.
 * @author Rui Wang
 *
 */
public class RuleBasedRecognizer {

  private HashMap<String, Integer> general;
  
  /**
   * Initialize term dictionary.
   * @throws FileNotFoundException
   *          thorws when cannot find target file.
   */
  public RuleBasedRecognizer () throws FileNotFoundException {
 //   Scanner scanner = new Scanner(new File("src/main/resources/SampleData/corpus.txt"));
    general = new HashMap<String, Integer>();
//    while(scanner.hasNext()){
//      String temp = scanner.nextLine();
//      if(temp.endsWith("~withdrawn")){
//        temp = temp.split("~")[0];
//      }
//      general.put(temp, 1);
//    }
  }
  
  /**
   *  This algorithm is designed by me based on regular expression.
   * 
   * @param candidate
   *        The vector saves candidates to be judged if they are gene term.
   * @param begin2end
   *        The Container saves our result.
   */
  public void deep(List<CoreLabel> candidate, Map<Integer, Integer> begin2end){
     // every if sentence is a rule
     int size = candidate.size();
     for(int i = 0; i < size; i++){ // // if word starts with mutant or mutants
       if(candidate.get(i).originalText().matches("mutants*") && (i - 1) >= 0 && (!isDomain(candidate.get(i - 1).originalText()))){
         int begin = candidate.get(i - 1).beginPosition();
         int end = candidate.get(i).endPosition();
         begin2end.put(begin, end);
       }else if(candidate.get(i).originalText().matches("[a-z]*receptors*") && (i - 1) >= 0 && !isDomain(candidate.get(i - 1).originalText())){
         int begin = candidate.get(i - 1).beginPosition();
         int end = candidate.get(i).endPosition();
         begin2end.put(begin, end);         
       }else if(isDomain(candidate.get(i).originalText()) && (i - 1) >= 0){
         int begin = candidate.get(i - 1).beginPosition();
         int end = candidate.get(i).endPosition();
         begin2end.put(begin, end);   
       }else if(isligthWords(candidate.get(i).originalText()) && (i - 1) >= 0){
         int begin = candidate.get(i - 1).beginPosition();
         int end = candidate.get(i).endPosition();
         begin2end.put(begin, end);         
       }else if(isNum(candidate.get(i).originalText()) && (i - 1) >= 0){
         int begin = candidate.get(i - 1).beginPosition();
         int end = candidate.get(i).endPosition();
         begin2end.put(begin, end);         
       }else if(bruteforce(candidate.get(i).originalText())){
         int begin = candidate.get(i).beginPosition();
         int end = candidate.get(i).endPosition();
         begin2end.put(begin, end);           
       }
     }
  }
  
  /**
   * 
   * @param x
   *        target string to be processed
   * @return boolean
   *        show if this word is a gene term.
   */
  private boolean isDomain(String x){
    if(x.equals("domain") || x.equals("element") || x.equals("enhancer")){
      return true;
    }
    return false;
  }
  
  /**
   * This rule is based on high frequency words in gene terms.
   * @param x
   *        target string to be processed
   * @return boolean
   *        show if this word is a gene term.
   */ 
  private boolean isligthWords(String x){
    if(x.matches("monomer[a-z]*")){// if word starts with monomer
      return true;
    }else if(x.matches("codons*")){// if word starts with codon or codons
      return true;
    }else if(x.matches("regions*")){// if word starts with regions
      return true;
    }else if(x.matches("exon[a-z]*")){ // if word starts with exon
      return true;
    }else if(x.matches("[a-zA-Z0-9]*orf[a-zA-Z0-9]*")||x.matches("[a-zA-Z0-9]*ORF[a-zA-Z0-9]*")){ // if word contain ORF
      return true;
    }else if(x.matches("cDNAs*")){  // if word starts with cDNA or cDNAs.
      return true;
    }else if(x.matches("[a-zA-Z0-9]*complex(es)*")){ // if word ends with complex or complexes.
      return true;
    }else if(x.matches("mRNA")){ //if word is mRNA
      return true;
    }else if(x.matches("oligomer")){ //if word is oligomer
      return true;
    }
    return false;
  }
  
  /**
   * This rule is based on that gene terms may include "I" "II" or "III".
   * @param x
   *        target string to be processed
   * @return boolean
   *        show if this word is a gene term.
   */ 
  private boolean isNum(String x){
    if(x.matches("I")){
      return true;
    }else if(x.matches("II")){
      return true;
    }else if(x.matches("III")){
      return true;
    }
    return false;
  }
 
  /**
   * This is a quick dictionary search
   * @param text
   *        target string to be processed
   * @return boolean
   *        show if this word is a gene term.
   */
  private boolean bruteforce(String text){
    if(general.containsKey(text)){
      return true;
    }
    return false;
  }
  
  /**
   * This rule is based on that gene terms have high probabilities to have upper words.
   * @param text
   *        target string to be processed
   * @return boolean
   *        show if this word is a gene term.
   */
  private boolean guess(String text){
    int i;
    for(i = 0; i < text.length(); i++){
      if(!(text.charAt(0) >= 'A' && text.charAt(0) <= 'B')){
          break;
      }
    }  
    if(i == text.length()){
      return true;
    }
    return false;
    
  }
}

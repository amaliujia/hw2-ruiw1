package SDAnnotator;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.awt.print.Printable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.Buffer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.net.URL;

import java_cup.runtime.lr_parser;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.examples.tokenizer.Sentence_Type;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.tools.cvd.control.NewTextEventHandler;

import Types.SentenceAnnotation;
import Types.lingpileAnnotation;
import abner.Tagger;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunker;
import com.aliasi.chunk.Chunking;
import com.aliasi.chunk.ConfidenceChunker;
import com.aliasi.chunk.HmmChunker;
import com.aliasi.util.AbstractExternalizable;

import edu.cmu.deiis.types.GeneAnnotation;


/**
 * Description: GeneAnnotator is responsible for record 
 * every gene mention and the id of this gene name's sentence.
 * 
 * @author Rui Wang
 *
 */

public class SDGeneAnnotator extends JCasAnnotator_ImplBase {
  
  //private final static String dataset = "src/main/resources/SampleData/trainer"; //"src/main/resources/SampleData/ne-en-bio-genetag.HmmChunker";
  private File chunkerFile;
 // private Chunker chunker;
  private ConfidenceChunker chunker;
  private int i = 0;
  
  public void initialize(UimaContext aContext)
          throws ResourceInitializationException{
  // chunkerFile = new File(dataset);
    super.initialize(aContext);
   try {
    //chunker = (ConfidenceChunker) AbstractExternalizable.readObject(chunkerFile);
     System.out.println((String) aContext.getConfigParameterValue("Gene"));
     chunker = (ConfidenceChunker)AbstractExternalizable.readResourceObject(SDGeneAnnotator.class, (String) aContext.getConfigParameterValue("Gene"));
 
  } catch (ClassNotFoundException e) {
    e.printStackTrace();
  } catch (IOException e) {
    e.printStackTrace();
  }
    System.out.println("I am in SDGeneAnnotator");
  }
  
  
  /**
   * This process runs  LingPipe
   * 
   * @param aJCas
   *        CAS corresponding to the complete processing
   * @throws AnalysisEngineProcessException
   *         happen when wrongly use UIMA objects
   *
   */
   public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // Use UMM chunker to extract gene terms
     int thisTime = 0;
      FSIterator<Annotation> it = aJCas.getAnnotationIndex(SentenceAnnotation.type).iterator();
      while(it.hasNext()){
        SentenceAnnotation tag =  (SentenceAnnotation)it.get();
         String s = tag.getText();
         //use confidence chunker of lingpipe
         Iterator<Chunk> iterator = chunker.nBestChunks(s.toCharArray(), 0, s.length(), 60);
         Chunk chunk;
         while(iterator.hasNext()){
           chunk = iterator.next();
           if(chunk != null){
             GeneAnnotation gene = new GeneAnnotation(aJCas);
             gene.setSentenceID(tag.getSentenceID());
             gene.setBegin(chunk.start());
             gene.setEnd(chunk.end());
             gene.setEntity(s.substring(chunk.start(),chunk.end()));
             gene.setCasProcessorId("0");
             gene.setConfidence(Math.pow(2.0, chunk.score()));
             gene.addToIndexes(aJCas);
             i++;
           }
         }
         it.moveToNext();
      }
  }
   public void destroy(){
     System.out.println("Final product lingpipe  " + i); 
    }
   
   
  /**
   * This function is used to count blanks in string.
   * @param s
   *        target string to be judges blanks.
   * @return int
   *         return the numbers of blanks in input string
   */
  private int countBlanks(String s){
    int count = 0;
    for(int i = 0; i < s.length(); i++){
      if(s.charAt(i) == ' '){
         count++;
      }
    }
    return count;
    
  }
}


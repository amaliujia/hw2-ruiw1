package SDCollectionReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;


/**
 * This file collection reader is in charge of dealing input file
 * @author Rui Wang
 * 
 * <ul>
 * <li><code>InputFile</code> - path to file containing sentences.</li>
 * </ul>
 */
public class SDFilesCollectionReader extends CollectionReader_ImplBase {

  private File inputfile;
  private boolean isFirst = true;
  private int index;
  private int num;
  private String[] sentencs;
  private BufferedReader bufferedReader;
  
  /**
   * To initialize everything necessary.
   */
  public void initialize(){
    inputfile = new File((String)getConfigParameterValue("InputFile"));
    System.out.println(inputfile);
    String text = "";
    try {
      //Scanner scanner = new Scanner(inputfile);
      FileReader reader = new FileReader(inputfile);
      bufferedReader = new BufferedReader(reader);
      int i = 0;
      String s = "";
      while((s = bufferedReader.readLine()) != null){
         text += s;
         text += "\n";
         i++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    sentencs = text.split("\\n");
    num = sentencs.length;
    index = 0;
  }
  
  /**
   *  Reads the next sentence from the input file, and outputs into the CAS.
   *  @param  aCAS 
   *        CAS corresponding to the completed processing
   *        
   *  @throws IOException
   *          throws if there is a IO error
   *  @throws CollectionException
   *          throws if collection operations fails
   */
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (Exception e) {
      throw new CollectionException(e);
    }
    // read a sentence into a string and put it into CAS
    jcas.setDocumentText(sentencs[index]);
    index++;
  }

  /**
   * @see org.apache.uima.collection.CollectionReader#hasNext()
   */
  public boolean hasNext() throws IOException, CollectionException {
    return index < num;
  }

  /**
   * @see org.apache.uima.collection.CollectionReader#hasNext()
   */
  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see org.apache.uima.collection.CollectionReader#hasNext()
   */
  public void close() throws IOException {
    // TODO Auto-generated method stub
    
  }

}

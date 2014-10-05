package SDCollectionReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Progress;


public class SDFilesCollectionReader extends CollectionReader_ImplBase {

  private File inputfile;
  private boolean isFirst = true;
  private int index;
  private int num;
  private String[] sentencs;
  
  /**
   * 
   */
  public void initialize(){
    inputfile = new File((String)getConfigParameterValue("InputFile"));
    String text = "";
    try {
      Scanner scanner = new Scanner(inputfile);
      int i = 0;
      while(scanner.hasNext()){
         text += scanner.nextLine();
         text += "\n";
         i++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    sentencs = text.split("\\n");
    num = sentencs.length;
    index = 0;
  }
  
  /**
   * 
   */
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (Exception e) {
      throw new CollectionException(e);
    }
    // read all sentences into a string and put it into CAS
    

   // System.out.println("Reader");
    jcas.setDocumentText(sentencs[index]);
    index++;
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
    return index < num;
  }

  @Override
  public Progress[] getProgress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub
    
  }

}

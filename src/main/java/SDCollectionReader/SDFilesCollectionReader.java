package SDCollectionReader;

import java.io.File;
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

  public static String inputDirectory;
  public ArrayList<File> files;
  public ArrayList<String> contents;
  public int fileindex, currentindex;
  public boolean hasFiles;
  
  /**
   * 
   */
  public void initialize(){
    inputDirectory = (String)getConfigParameterValue("InputFileDirectory");
    files = new ArrayList<File>();
    contents = new ArrayList<String>();
    File directorFile = new File(inputDirectory);
    File[] fs = directorFile.listFiles();
    for(int i = 0; i < fs.length; i++){
      if(!fs[i].isDirectory()){
        files.add(fs[i]);
      }
    }
    fileindex = 0;
    currentindex = 0;
    if(files.size() == 0){
      hasFiles = false;
    }
  }
  
  /**
   * 
   */
  public void getNext(CAS aCAS) throws IOException, CollectionException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }
    if(currentindex == 0){
      File thisFile = files.get(fileindex);
      Scanner scanner = new Scanner(thisFile);
      String cache = new String();
      while(scanner.hasNext()){
        cache += (scanner.nextLine() + "\n");
      }
      String[] strings = cache.split("\\n");
      for(int i = 0; i < strings.length; i++){
        contents.add(strings[i]);
      }
      // Do something.
      
      currentindex++;
      return;
    }
    
    //TODO: do something 
    
    currentindex++;
    return;
  }

  @Override
  public boolean hasNext() throws IOException, CollectionException {
   if(!hasFiles){
     return false;
   }
   
   if(currentindex >= files.get(fileindex).length()){
     fileindex++;
     currentindex = 0;
     if(fileindex >= files.size()){
       return false;
     }else{
       return true;
     }
   }
   
    return true;
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

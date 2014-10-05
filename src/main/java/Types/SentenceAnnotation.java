

/* First created by JCasGen Fri Oct 03 21:33:01 EDT 2014 */
package Types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Oct 05 14:41:48 EDT 2014
 * XML source: /Users/amaliujia/Documents/workspace/hw2-ruiw1/src/main/resources/TypeSystems/SDTypeSystem.xml
 * @generated */
public class SentenceAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SentenceAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected SentenceAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SentenceAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SentenceAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SentenceAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: sentenceID

  /** getter for sentenceID - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSentenceID() {
    if (SentenceAnnotation_Type.featOkTst && ((SentenceAnnotation_Type)jcasType).casFeat_sentenceID == null)
      jcasType.jcas.throwFeatMissing("sentenceID", "Types.SentenceAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SentenceAnnotation_Type)jcasType).casFeatCode_sentenceID);}
    
  /** setter for sentenceID - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentenceID(String v) {
    if (SentenceAnnotation_Type.featOkTst && ((SentenceAnnotation_Type)jcasType).casFeat_sentenceID == null)
      jcasType.jcas.throwFeatMissing("sentenceID", "Types.SentenceAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SentenceAnnotation_Type)jcasType).casFeatCode_sentenceID, v);}    
   
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets 
   * @generated
   * @return value of the feature 
   */
  public String getText() {
    if (SentenceAnnotation_Type.featOkTst && ((SentenceAnnotation_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "Types.SentenceAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SentenceAnnotation_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setText(String v) {
    if (SentenceAnnotation_Type.featOkTst && ((SentenceAnnotation_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "Types.SentenceAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SentenceAnnotation_Type)jcasType).casFeatCode_text, v);}    
  }

    
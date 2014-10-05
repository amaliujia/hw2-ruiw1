

/* First created by JCasGen Sat Oct 04 23:03:21 EDT 2014 */
package Types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Oct 05 00:32:52 EDT 2014
 * XML source: /Users/amaliujia/Documents/workspace/hw2-ruiw1/src/main/resources/TypeSystems/SDTypeSystem.xml
 * @generated */
public class ABNERAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ABNERAnnotation.class);
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
  protected ABNERAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ABNERAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ABNERAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public ABNERAnnotation(JCas jcas, int begin, int end) {
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
    if (ABNERAnnotation_Type.featOkTst && ((ABNERAnnotation_Type)jcasType).casFeat_sentenceID == null)
      jcasType.jcas.throwFeatMissing("sentenceID", "Types.ABNERAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ABNERAnnotation_Type)jcasType).casFeatCode_sentenceID);}
    
  /** setter for sentenceID - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentenceID(String v) {
    if (ABNERAnnotation_Type.featOkTst && ((ABNERAnnotation_Type)jcasType).casFeat_sentenceID == null)
      jcasType.jcas.throwFeatMissing("sentenceID", "Types.ABNERAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((ABNERAnnotation_Type)jcasType).casFeatCode_sentenceID, v);}    
   
    
  //*--------------*
  //* Feature: entity

  /** getter for entity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getEntity() {
    if (ABNERAnnotation_Type.featOkTst && ((ABNERAnnotation_Type)jcasType).casFeat_entity == null)
      jcasType.jcas.throwFeatMissing("entity", "Types.ABNERAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((ABNERAnnotation_Type)jcasType).casFeatCode_entity);}
    
  /** setter for entity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEntity(String v) {
    if (ABNERAnnotation_Type.featOkTst && ((ABNERAnnotation_Type)jcasType).casFeat_entity == null)
      jcasType.jcas.throwFeatMissing("entity", "Types.ABNERAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((ABNERAnnotation_Type)jcasType).casFeatCode_entity, v);}    
  }

    
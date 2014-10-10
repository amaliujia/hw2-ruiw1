

/* First created by JCasGen Fri Oct 10 11:17:37 EDT 2014 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Fri Oct 10 11:17:37 EDT 2014
 * XML source: /Users/amaliujia/Documents/workspace/hw2-test/src/main/resources/TypeSystems/deiis_types.xml
 * @generated */
public class GeneAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GeneAnnotation.class);
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
  protected GeneAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public GeneAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public GeneAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public GeneAnnotation(JCas jcas, int begin, int end) {
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
  //* Feature: entity

  /** getter for entity - gets 
   * @generated
   * @return value of the feature 
   */
  public String getEntity() {
    if (GeneAnnotation_Type.featOkTst && ((GeneAnnotation_Type)jcasType).casFeat_entity == null)
      jcasType.jcas.throwFeatMissing("entity", "edu.cmu.deiis.types.GeneAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneAnnotation_Type)jcasType).casFeatCode_entity);}
    
  /** setter for entity - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setEntity(String v) {
    if (GeneAnnotation_Type.featOkTst && ((GeneAnnotation_Type)jcasType).casFeat_entity == null)
      jcasType.jcas.throwFeatMissing("entity", "edu.cmu.deiis.types.GeneAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneAnnotation_Type)jcasType).casFeatCode_entity, v);}    
   
    
  //*--------------*
  //* Feature: sentenceID

  /** getter for sentenceID - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSentenceID() {
    if (GeneAnnotation_Type.featOkTst && ((GeneAnnotation_Type)jcasType).casFeat_sentenceID == null)
      jcasType.jcas.throwFeatMissing("sentenceID", "edu.cmu.deiis.types.GeneAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GeneAnnotation_Type)jcasType).casFeatCode_sentenceID);}
    
  /** setter for sentenceID - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentenceID(String v) {
    if (GeneAnnotation_Type.featOkTst && ((GeneAnnotation_Type)jcasType).casFeat_sentenceID == null)
      jcasType.jcas.throwFeatMissing("sentenceID", "edu.cmu.deiis.types.GeneAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((GeneAnnotation_Type)jcasType).casFeatCode_sentenceID, v);}    
  }

    
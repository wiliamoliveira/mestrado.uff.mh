//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.01 at 04:32:01 PM BRT 
//

package br.uff.mh.mestrado.excel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for lsItem complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lsItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="del" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="add" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lsItem", propOrder = { "del", "add" })
public class LsItem {

	protected int del;
	protected int add;

	/**
	 * Gets the value of the del property.
	 * 
	 */
	public int getDel() {
		return del;
	}

	/**
	 * Sets the value of the del property.
	 * 
	 */
	public void setDel(int value) {
		this.del = value;
	}

	/**
	 * Gets the value of the add property.
	 * 
	 */
	public int getAdd() {
		return add;
	}

	/**
	 * Sets the value of the add property.
	 * 
	 */
	public void setAdd(int value) {
		this.add = value;
	}

	@Override
	public String toString() {
		return "[del=" + del + ", add=" + add + "]";
	}

}

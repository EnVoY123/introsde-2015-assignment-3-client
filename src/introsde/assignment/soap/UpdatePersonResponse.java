
package introsde.assignment.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for updatePersonResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="updatePersonResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uperson" type="{http://soap.assignment.introsde/}person" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updatePersonResponse", propOrder = {
    "uperson"
})
public class UpdatePersonResponse {

    protected Person uperson;

    /**
     * Gets the value of the uperson property.
     * 
     * @return
     *     possible object is
     *     {@link Person }
     *     
     */
    public Person getUperson() {
        return uperson;
    }

    /**
     * Sets the value of the uperson property.
     * 
     * @param value
     *     allowed object is
     *     {@link Person }
     *     
     */
    public void setUperson(Person value) {
        this.uperson = value;
    }

}

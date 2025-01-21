/**
 * WsAbarcativa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

public class WsAbarcativa  implements java.io.Serializable {
    private java.lang.String deudaAbarcativa;

    private java.lang.String periodo;

    private java.lang.String mascara;

    public WsAbarcativa() {
    }

    public WsAbarcativa(
           java.lang.String deudaAbarcativa,
           java.lang.String periodo,
           java.lang.String mascara) {
           this.deudaAbarcativa = deudaAbarcativa;
           this.periodo = periodo;
           this.mascara = mascara;
    }


    /**
     * Gets the deudaAbarcativa value for this WsAbarcativa.
     * 
     * @return deudaAbarcativa
     */
    public java.lang.String getDeudaAbarcativa() {
        return deudaAbarcativa;
    }


    /**
     * Sets the deudaAbarcativa value for this WsAbarcativa.
     * 
     * @param deudaAbarcativa
     */
    public void setDeudaAbarcativa(java.lang.String deudaAbarcativa) {
        this.deudaAbarcativa = deudaAbarcativa;
    }


    /**
     * Gets the periodo value for this WsAbarcativa.
     * 
     * @return periodo
     */
    public java.lang.String getPeriodo() {
        return periodo;
    }


    /**
     * Sets the periodo value for this WsAbarcativa.
     * 
     * @param periodo
     */
    public void setPeriodo(java.lang.String periodo) {
        this.periodo = periodo;
    }


    /**
     * Gets the mascara value for this WsAbarcativa.
     * 
     * @return mascara
     */
    public java.lang.String getMascara() {
        return mascara;
    }


    /**
     * Sets the mascara value for this WsAbarcativa.
     * 
     * @param mascara
     */
    public void setMascara(java.lang.String mascara) {
        this.mascara = mascara;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsAbarcativa)) return false;
        WsAbarcativa other = (WsAbarcativa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.deudaAbarcativa==null && other.getDeudaAbarcativa()==null) || 
             (this.deudaAbarcativa!=null &&
              this.deudaAbarcativa.equals(other.getDeudaAbarcativa()))) &&
            ((this.periodo==null && other.getPeriodo()==null) || 
             (this.periodo!=null &&
              this.periodo.equals(other.getPeriodo()))) &&
            ((this.mascara==null && other.getMascara()==null) || 
             (this.mascara!=null &&
              this.mascara.equals(other.getMascara())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getDeudaAbarcativa() != null) {
            _hashCode += getDeudaAbarcativa().hashCode();
        }
        if (getPeriodo() != null) {
            _hashCode += getPeriodo().hashCode();
        }
        if (getMascara() != null) {
            _hashCode += getMascara().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsAbarcativa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsAbarcativa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deudaAbarcativa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deudaAbarcativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mascara");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mascara"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
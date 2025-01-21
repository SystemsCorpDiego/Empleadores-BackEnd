/**
 * WsResultadoAlta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package  ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

public class WsResultadoAlta  implements java.io.Serializable {
    private java.lang.String idDeuda;

    private java.lang.String concepto;

    private java.lang.String codigoResultadoAlta;

    private java.lang.String descripcionResultadoAlta;

    public WsResultadoAlta() {
    }

    public WsResultadoAlta(
           java.lang.String idDeuda,
           java.lang.String concepto,
           java.lang.String codigoResultadoAlta,
           java.lang.String descripcionResultadoAlta) {
           this.idDeuda = idDeuda;
           this.concepto = concepto;
           this.codigoResultadoAlta = codigoResultadoAlta;
           this.descripcionResultadoAlta = descripcionResultadoAlta;
    }


    /**
     * Gets the idDeuda value for this WsResultadoAlta.
     * 
     * @return idDeuda
     */
    public java.lang.String getIdDeuda() {
        return idDeuda;
    }


    /**
     * Sets the idDeuda value for this WsResultadoAlta.
     * 
     * @param idDeuda
     */
    public void setIdDeuda(java.lang.String idDeuda) {
        this.idDeuda = idDeuda;
    }


    /**
     * Gets the concepto value for this WsResultadoAlta.
     * 
     * @return concepto
     */
    public java.lang.String getConcepto() {
        return concepto;
    }


    /**
     * Sets the concepto value for this WsResultadoAlta.
     * 
     * @param concepto
     */
    public void setConcepto(java.lang.String concepto) {
        this.concepto = concepto;
    }


    /**
     * Gets the codigoResultadoAlta value for this WsResultadoAlta.
     * 
     * @return codigoResultadoAlta
     */
    public java.lang.String getCodigoResultadoAlta() {
        return codigoResultadoAlta;
    }


    /**
     * Sets the codigoResultadoAlta value for this WsResultadoAlta.
     * 
     * @param codigoResultadoAlta
     */
    public void setCodigoResultadoAlta(java.lang.String codigoResultadoAlta) {
        this.codigoResultadoAlta = codigoResultadoAlta;
    }


    /**
     * Gets the descripcionResultadoAlta value for this WsResultadoAlta.
     * 
     * @return descripcionResultadoAlta
     */
    public java.lang.String getDescripcionResultadoAlta() {
        return descripcionResultadoAlta;
    }


    /**
     * Sets the descripcionResultadoAlta value for this WsResultadoAlta.
     * 
     * @param descripcionResultadoAlta
     */
    public void setDescripcionResultadoAlta(java.lang.String descripcionResultadoAlta) {
        this.descripcionResultadoAlta = descripcionResultadoAlta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsResultadoAlta)) return false;
        WsResultadoAlta other = (WsResultadoAlta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idDeuda==null && other.getIdDeuda()==null) || 
             (this.idDeuda!=null &&
              this.idDeuda.equals(other.getIdDeuda()))) &&
            ((this.concepto==null && other.getConcepto()==null) || 
             (this.concepto!=null &&
              this.concepto.equals(other.getConcepto()))) &&
            ((this.codigoResultadoAlta==null && other.getCodigoResultadoAlta()==null) || 
             (this.codigoResultadoAlta!=null &&
              this.codigoResultadoAlta.equals(other.getCodigoResultadoAlta()))) &&
            ((this.descripcionResultadoAlta==null && other.getDescripcionResultadoAlta()==null) || 
             (this.descripcionResultadoAlta!=null &&
              this.descripcionResultadoAlta.equals(other.getDescripcionResultadoAlta())));
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
        if (getIdDeuda() != null) {
            _hashCode += getIdDeuda().hashCode();
        }
        if (getConcepto() != null) {
            _hashCode += getConcepto().hashCode();
        }
        if (getCodigoResultadoAlta() != null) {
            _hashCode += getCodigoResultadoAlta().hashCode();
        }
        if (getDescripcionResultadoAlta() != null) {
            _hashCode += getDescripcionResultadoAlta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsResultadoAlta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsResultadoAlta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDeuda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDeuda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concepto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concepto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoResultadoAlta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoResultadoAlta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionResultadoAlta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionResultadoAlta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
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

	@Override
	public String toString() {
		return "WsResultadoAlta [idDeuda=" + idDeuda + ", concepto=" + concepto + ", codigoResultadoAlta="
				+ codigoResultadoAlta + ", descripcionResultadoAlta=" + descripcionResultadoAlta +  "]";
	}

}

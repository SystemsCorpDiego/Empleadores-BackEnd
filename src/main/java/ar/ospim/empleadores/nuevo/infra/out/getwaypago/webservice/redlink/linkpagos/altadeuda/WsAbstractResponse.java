/**
 * WsAbstractResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

public class WsAbstractResponse  implements java.io.Serializable {
    private  WsHeaderResponse cabecera;

    private java.lang.String codigoResultado;

    private java.lang.String descripcionResultado;

    public WsAbstractResponse() {
    }

    public WsAbstractResponse(
    		 ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsHeaderResponse cabecera,
           java.lang.String codigoResultado,
           java.lang.String descripcionResultado) {
           this.cabecera = cabecera;
           this.codigoResultado = codigoResultado;
           this.descripcionResultado = descripcionResultado;
    }


    /**
     * Gets the cabecera value for this WsAbstractResponse.
     * 
     * @return cabecera
     */
    public WsHeaderResponse getCabecera() {
        return cabecera;
    }


    /**
     * Sets the cabecera value for this WsAbstractResponse.
     * 
     * @param cabecera
     */
    public void setCabecera(WsHeaderResponse cabecera) {
        this.cabecera = cabecera;
    }


    /**
     * Gets the codigoResultado value for this WsAbstractResponse.
     * 
     * @return codigoResultado
     */
    public java.lang.String getCodigoResultado() {
        return codigoResultado;
    }


    /**
     * Sets the codigoResultado value for this WsAbstractResponse.
     * 
     * @param codigoResultado
     */
    public void setCodigoResultado(java.lang.String codigoResultado) {
        this.codigoResultado = codigoResultado;
    }


    /**
     * Gets the descripcionResultado value for this WsAbstractResponse.
     * 
     * @return descripcionResultado
     */
    public java.lang.String getDescripcionResultado() {
        return descripcionResultado;
    }


    /**
     * Sets the descripcionResultado value for this WsAbstractResponse.
     * 
     * @param descripcionResultado
     */
    public void setDescripcionResultado(java.lang.String descripcionResultado) {
        this.descripcionResultado = descripcionResultado;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsAbstractResponse)) return false;
        WsAbstractResponse other = (WsAbstractResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cabecera==null && other.getCabecera()==null) || 
             (this.cabecera!=null &&
              this.cabecera.equals(other.getCabecera()))) &&
            ((this.codigoResultado==null && other.getCodigoResultado()==null) || 
             (this.codigoResultado!=null &&
              this.codigoResultado.equals(other.getCodigoResultado()))) &&
            ((this.descripcionResultado==null && other.getDescripcionResultado()==null) || 
             (this.descripcionResultado!=null &&
              this.descripcionResultado.equals(other.getDescripcionResultado())));
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
        if (getCabecera() != null) {
            _hashCode += getCabecera().hashCode();
        }
        if (getCodigoResultado() != null) {
            _hashCode += getCodigoResultado().hashCode();
        }
        if (getDescripcionResultado() != null) {
            _hashCode += getDescripcionResultado().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsAbstractResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsAbstractResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cabecera");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.ws.linkpagoservices.redlink.com.ar/", "cabecera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsHeaderResponse"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigoResultado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcionResultado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcionResultado"));
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
		return "WsAbstractResponse [cabecera=" + cabecera.toString() + ", codigoResultado=" + codigoResultado
				+ ", descripcionResultado=" + descripcionResultado +  "]";
	}

}
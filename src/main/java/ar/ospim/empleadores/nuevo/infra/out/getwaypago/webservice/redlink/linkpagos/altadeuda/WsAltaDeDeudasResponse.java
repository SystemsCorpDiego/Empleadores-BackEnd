/**
 * WsAltaDeDeudasResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

import java.util.Arrays;

public class WsAltaDeDeudasResponse  extends  ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsAbstractResponse  implements java.io.Serializable {
    private java.lang.String cpe;

    private WsResultadoAlta[] resultadoAltas;
   
    public WsAltaDeDeudasResponse() {
    }

    public WsAltaDeDeudasResponse(
    		 WsHeaderResponse cabecera,
           java.lang.String codigoResultado,
           java.lang.String descripcionResultado,
           java.lang.String cpe,
           WsResultadoAlta[] resultadoAltas) {
        super(
            cabecera,
            codigoResultado,
            descripcionResultado);
        this.cpe = cpe;
        this.resultadoAltas = resultadoAltas;
    }


    /**
     * Gets the cpe value for this WsAltaDeDeudasResponse.
     * 
     * @return cpe
     */
    public java.lang.String getCpe() {
        return cpe;
    }


    /**
     * Sets the cpe value for this WsAltaDeDeudasResponse.
     * 
     * @param cpe
     */
    public void setCpe(java.lang.String cpe) {
        this.cpe = cpe;
    }


    /**
     * Gets the resultadoAltas value for this WsAltaDeDeudasResponse.
     * 
     * @return resultadoAltas
     */
    public WsResultadoAlta[] getResultadoAltas() {
        return resultadoAltas;
    }


    /**
     * Sets the resultadoAltas value for this WsAltaDeDeudasResponse.
     * 
     * @param resultadoAltas
     */
    public void setResultadoAltas( WsResultadoAlta[] resultadoAltas) {
        this.resultadoAltas = resultadoAltas;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsAltaDeDeudasResponse)) return false;
        WsAltaDeDeudasResponse other = (WsAltaDeDeudasResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cpe==null && other.getCpe()==null) || 
             (this.cpe!=null &&
              this.cpe.equals(other.getCpe()))) &&
            ((this.resultadoAltas==null && other.getResultadoAltas()==null) || 
             (this.resultadoAltas!=null &&
              java.util.Arrays.equals(this.resultadoAltas, other.getResultadoAltas())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCpe() != null) {
            _hashCode += getCpe().hashCode();
        }
        if (getResultadoAltas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getResultadoAltas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getResultadoAltas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsAltaDeDeudasResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsAltaDeDeudasResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cpe");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.ws.linkpagoservices.redlink.com.ar/", "cpe"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultadoAltas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultadoAltas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://response.ws.linkpagoservices.redlink.com.ar/", "resultadoAlta"));
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
		return "WsAltaDeDeudasResponse [cpe=" + cpe + ", resultadoAltas=" + Arrays.toString(resultadoAltas)
				+ ", toString()=" + super.toString() + "]";
	}

}

/**
 * WsAbstractRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

public abstract class WsAbstractRequest  implements java.io.Serializable {
    private WsHeaderRequest cabecera;

    public WsAbstractRequest() {
    }

    public WsAbstractRequest(
    		WsHeaderRequest cabecera) {
           this.cabecera = cabecera;
    }


    /**
     * Gets the cabecera value for this WsAbstractRequest.
     * 
     * @return cabecera
     */
    public WsHeaderRequest getCabecera() {
        return cabecera;
    }


    /**
     * Sets the cabecera value for this WsAbstractRequest.
     * 
     * @param cabecera
     */
    public void setCabecera(WsHeaderRequest cabecera) {
        this.cabecera = cabecera;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsAbstractRequest)) return false;
        WsAbstractRequest other = (WsAbstractRequest) obj;
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
              this.cabecera.equals(other.getCabecera())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsAbstractRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsAbstractRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cabecera");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.ws.linkpagoservices.redlink.com.ar/", "cabecera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsHeaderRequest"));
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
		return "WsAbstractRequest [cabecera=" + cabecera + "]";
	}

}

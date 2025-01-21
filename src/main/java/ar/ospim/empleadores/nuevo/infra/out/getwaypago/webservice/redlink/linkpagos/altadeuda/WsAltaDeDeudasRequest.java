/**
 * WsAltaDeDeudasRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

import java.util.Arrays;

public class WsAltaDeDeudasRequest extends WsAbstractRequest implements java.io.Serializable {
    private java.lang.String ente;

    private java.lang.String usuario;

    private  WsDeuda[] datosDeuda;

    public WsAltaDeDeudasRequest() {
    }

    public WsAltaDeDeudasRequest(
    		 WsHeaderRequest cabecera,
           java.lang.String ente,
           java.lang.String usuario,
           WsDeuda[] datosDeuda) {
        super(
            cabecera);
        this.ente = ente;
        this.usuario = usuario;
        this.datosDeuda = datosDeuda;
    }


    /**
     * Gets the ente value for this WsAltaDeDeudasRequest.
     * 
     * @return ente
     */
    public java.lang.String getEnte() {
        return ente;
    }


    /**
     * Sets the ente value for this WsAltaDeDeudasRequest.
     * 
     * @param ente
     */
    public void setEnte(java.lang.String ente) {
        this.ente = ente;
    }


    /**
     * Gets the usuario value for this WsAltaDeDeudasRequest.
     * 
     * @return usuario
     */
    public java.lang.String getUsuario() {
        return usuario;
    }


    /**
     * Sets the usuario value for this WsAltaDeDeudasRequest.
     * 
     * @param usuario
     */
    public void setUsuario(java.lang.String usuario) {
        this.usuario = usuario;
    }


    /**
     * Gets the datosDeuda value for this WsAltaDeDeudasRequest.
     * 
     * @return datosDeuda
     */
    public WsDeuda[] getDatosDeuda() {
        return datosDeuda;
    }


    /**
     * Sets the datosDeuda value for this WsAltaDeDeudasRequest.
     * 
     * @param datosDeuda
     */
    public void setDatosDeuda( WsDeuda[] datosDeuda) {
        this.datosDeuda = datosDeuda;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsAltaDeDeudasRequest)) return false;
        WsAltaDeDeudasRequest other = (WsAltaDeDeudasRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ente==null && other.getEnte()==null) || 
             (this.ente!=null &&
              this.ente.equals(other.getEnte()))) &&
            ((this.usuario==null && other.getUsuario()==null) || 
             (this.usuario!=null &&
              this.usuario.equals(other.getUsuario()))) &&
            ((this.datosDeuda==null && other.getDatosDeuda()==null) || 
             (this.datosDeuda!=null &&
              java.util.Arrays.equals(this.datosDeuda, other.getDatosDeuda())));
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
        if (getEnte() != null) {
            _hashCode += getEnte().hashCode();
        }
        if (getUsuario() != null) {
            _hashCode += getUsuario().hashCode();
        }
        if (getDatosDeuda() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDatosDeuda());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDatosDeuda(), i);
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
        new org.apache.axis.description.TypeDesc(WsAltaDeDeudasRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsAltaDeDeudasRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.ws.altadeuda.linkpagoservices.redlink.com.ar/", "ente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.ws.altadeuda.linkpagoservices.redlink.com.ar/", "usuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosDeuda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datosDeuda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsDeuda"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "deuda"));
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
		return "WsAltaDeDeudasRequest [ente=" + ente + ", usuario=" + usuario + ", datosDeuda="
				+ Arrays.toString(datosDeuda) + "]";
	}

}
/**
 * WsHeaderRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package  ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

public class WsHeaderRequest  implements java.io.Serializable {
    private java.lang.String idRequerimiento;

    private java.lang.String ipCliente;

    private java.lang.String timeStamp;

    private java.lang.String idEntidad;

    private java.lang.String canal;

    public WsHeaderRequest() {
    }

    public WsHeaderRequest(
           java.lang.String idRequerimiento,
           java.lang.String ipCliente,
           java.lang.String timeStamp,
           java.lang.String idEntidad,
           java.lang.String canal) {
           this.idRequerimiento = idRequerimiento;
           this.ipCliente = ipCliente;
           this.timeStamp = timeStamp;
           this.idEntidad = idEntidad;
           this.canal = canal;
    }


    /**
     * Gets the idRequerimiento value for this WsHeaderRequest.
     * 
     * @return idRequerimiento
     */
    public java.lang.String getIdRequerimiento() {
        return idRequerimiento;
    }


    /**
     * Sets the idRequerimiento value for this WsHeaderRequest.
     * 
     * @param idRequerimiento
     */
    public void setIdRequerimiento(java.lang.String idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }


    /**
     * Gets the ipCliente value for this WsHeaderRequest.
     * 
     * @return ipCliente
     */
    public java.lang.String getIpCliente() {
        return ipCliente;
    }


    /**
     * Sets the ipCliente value for this WsHeaderRequest.
     * 
     * @param ipCliente
     */
    public void setIpCliente(java.lang.String ipCliente) {
        this.ipCliente = ipCliente;
    }


    /**
     * Gets the timeStamp value for this WsHeaderRequest.
     * 
     * @return timeStamp
     */
    public java.lang.String getTimeStamp() {
        return timeStamp;
    }


    /**
     * Sets the timeStamp value for this WsHeaderRequest.
     * 
     * @param timeStamp
     */
    public void setTimeStamp(java.lang.String timeStamp) {
        this.timeStamp = timeStamp;
    }


    /**
     * Gets the idEntidad value for this WsHeaderRequest.
     * 
     * @return idEntidad
     */
    public java.lang.String getIdEntidad() {
        return idEntidad;
    }


    /**
     * Sets the idEntidad value for this WsHeaderRequest.
     * 
     * @param idEntidad
     */
    public void setIdEntidad(java.lang.String idEntidad) {
        this.idEntidad = idEntidad;
    }


    /**
     * Gets the canal value for this WsHeaderRequest.
     * 
     * @return canal
     */
    public java.lang.String getCanal() {
        return canal;
    }


    /**
     * Sets the canal value for this WsHeaderRequest.
     * 
     * @param canal
     */
    public void setCanal(java.lang.String canal) {
        this.canal = canal;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsHeaderRequest)) return false;
        WsHeaderRequest other = (WsHeaderRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idRequerimiento==null && other.getIdRequerimiento()==null) || 
             (this.idRequerimiento!=null &&
              this.idRequerimiento.equals(other.getIdRequerimiento()))) &&
            ((this.ipCliente==null && other.getIpCliente()==null) || 
             (this.ipCliente!=null &&
              this.ipCliente.equals(other.getIpCliente()))) &&
            ((this.timeStamp==null && other.getTimeStamp()==null) || 
             (this.timeStamp!=null &&
              this.timeStamp.equals(other.getTimeStamp()))) &&
            ((this.idEntidad==null && other.getIdEntidad()==null) || 
             (this.idEntidad!=null &&
              this.idEntidad.equals(other.getIdEntidad()))) &&
            ((this.canal==null && other.getCanal()==null) || 
             (this.canal!=null &&
              this.canal.equals(other.getCanal())));
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
        if (getIdRequerimiento() != null) {
            _hashCode += getIdRequerimiento().hashCode();
        }
        if (getIpCliente() != null) {
            _hashCode += getIpCliente().hashCode();
        }
        if (getTimeStamp() != null) {
            _hashCode += getTimeStamp().hashCode();
        }
        if (getIdEntidad() != null) {
            _hashCode += getIdEntidad().hashCode();
        }
        if (getCanal() != null) {
            _hashCode += getCanal().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsHeaderRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsHeaderRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRequerimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRequerimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipCliente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipCliente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeStamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeStamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEntidad");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEntidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("canal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "canal"));
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
		return "WsHeaderRequest [idRequerimiento=" + idRequerimiento + ", ipCliente=" + ipCliente + ", timeStamp="
				+ timeStamp + ", idEntidad=" + idEntidad + ", canal=" + canal + "]";
	}

    
}

/**
 * WsVencimiento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package  ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

public class WsVencimiento  implements java.io.Serializable {
    private java.lang.String ordenVencimiento;

    private java.lang.String fechaVencimiento;

    private java.lang.String importe;

    public WsVencimiento() {
    }

    public WsVencimiento(
           java.lang.String ordenVencimiento,
           java.lang.String fechaVencimiento,
           java.lang.String importe) {
           this.ordenVencimiento = ordenVencimiento;
           this.fechaVencimiento = fechaVencimiento;
           this.importe = importe;
    }


    /**
     * Gets the ordenVencimiento value for this WsVencimiento.
     * 
     * @return ordenVencimiento
     */
    public java.lang.String getOrdenVencimiento() {
        return ordenVencimiento;
    }


    /**
     * Sets the ordenVencimiento value for this WsVencimiento.
     * 
     * @param ordenVencimiento
     */
    public void setOrdenVencimiento(java.lang.String ordenVencimiento) {
        this.ordenVencimiento = ordenVencimiento;
    }


    /**
     * Gets the fechaVencimiento value for this WsVencimiento.
     * 
     * @return fechaVencimiento
     */
    public java.lang.String getFechaVencimiento() {
        return fechaVencimiento;
    }


    /**
     * Sets the fechaVencimiento value for this WsVencimiento.
     * 
     * @param fechaVencimiento
     */
    public void setFechaVencimiento(java.lang.String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }


    /**
     * Gets the importe value for this WsVencimiento.
     * 
     * @return importe
     */
    public java.lang.String getImporte() {
        return importe;
    }


    /**
     * Sets the importe value for this WsVencimiento.
     * 
     * @param importe
     */
    public void setImporte(java.lang.String importe) {
        this.importe = importe;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsVencimiento)) return false;
        WsVencimiento other = (WsVencimiento) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ordenVencimiento==null && other.getOrdenVencimiento()==null) || 
             (this.ordenVencimiento!=null &&
              this.ordenVencimiento.equals(other.getOrdenVencimiento()))) &&
            ((this.fechaVencimiento==null && other.getFechaVencimiento()==null) || 
             (this.fechaVencimiento!=null &&
              this.fechaVencimiento.equals(other.getFechaVencimiento()))) &&
            ((this.importe==null && other.getImporte()==null) || 
             (this.importe!=null &&
              this.importe.equals(other.getImporte())));
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
        if (getOrdenVencimiento() != null) {
            _hashCode += getOrdenVencimiento().hashCode();
        }
        if (getFechaVencimiento() != null) {
            _hashCode += getFechaVencimiento().hashCode();
        }
        if (getImporte() != null) {
            _hashCode += getImporte().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsVencimiento.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsVencimiento"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordenVencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ordenVencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaVencimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fechaVencimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importe");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importe"));
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
		return "WsVencimiento [ordenVencimiento=" + ordenVencimiento + ", fechaVencimiento=" + fechaVencimiento
				+ ", importe=" + importe + "]";
	}

}

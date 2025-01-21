/**
 * WsDeuda.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package  ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

import java.util.Arrays;

public class WsDeuda  implements java.io.Serializable {
    private java.lang.String idDeuda;

    private java.lang.String concepto;

    private WsAbarcativa abarcativa;

    private WsVencimiento[] vencimientos;

    private java.lang.String importeMinimo;

    private java.lang.String importeMaximo;

    private java.lang.String discrecional;

    public WsDeuda() {
    }

    public WsDeuda(
           java.lang.String idDeuda,
           java.lang.String concepto,
           WsAbarcativa abarcativa,
           WsVencimiento[] vencimientos,
           java.lang.String importeMinimo,
           java.lang.String importeMaximo,
           java.lang.String discrecional) {
           this.idDeuda = idDeuda;
           this.concepto = concepto;
           this.abarcativa = abarcativa;
           this.vencimientos = vencimientos;
           this.importeMinimo = importeMinimo;
           this.importeMaximo = importeMaximo;
           this.discrecional = discrecional;
    }


    /**
     * Gets the idDeuda value for this WsDeuda.
     * 
     * @return idDeuda
     */
    public java.lang.String getIdDeuda() {
        return idDeuda;
    }


    /**
     * Sets the idDeuda value for this WsDeuda.
     * 
     * @param idDeuda
     */
    public void setIdDeuda(java.lang.String idDeuda) {
        this.idDeuda = idDeuda;
    }


    /**
     * Gets the concepto value for this WsDeuda.
     * 
     * @return concepto
     */
    public java.lang.String getConcepto() {
        return concepto;
    }


    /**
     * Sets the concepto value for this WsDeuda.
     * 
     * @param concepto
     */
    public void setConcepto(java.lang.String concepto) {
        this.concepto = concepto;
    }


    /**
     * Gets the abarcativa value for this WsDeuda.
     * 
     * @return abarcativa
     */
    public WsAbarcativa getAbarcativa() {
        return abarcativa;
    }


    /**
     * Sets the abarcativa value for this WsDeuda.
     * 
     * @param abarcativa
     */
    public void setAbarcativa( WsAbarcativa abarcativa) {
        this.abarcativa = abarcativa;
    }


    /**
     * Gets the vencimientos value for this WsDeuda.
     * 
     * @return vencimientos
     */
    public WsVencimiento[] getVencimientos() {
        return vencimientos;
    }


    /**
     * Sets the vencimientos value for this WsDeuda.
     * 
     * @param vencimientos
     */
    public void setVencimientos(  WsVencimiento[] vencimientos) {
        this.vencimientos = vencimientos;
    }


    /**
     * Gets the importeMinimo value for this WsDeuda.
     * 
     * @return importeMinimo
     */
    public java.lang.String getImporteMinimo() {
        return importeMinimo;
    }


    /**
     * Sets the importeMinimo value for this WsDeuda.
     * 
     * @param importeMinimo
     */
    public void setImporteMinimo(java.lang.String importeMinimo) {
        this.importeMinimo = importeMinimo;
    }


    /**
     * Gets the importeMaximo value for this WsDeuda.
     * 
     * @return importeMaximo
     */
    public java.lang.String getImporteMaximo() {
        return importeMaximo;
    }


    /**
     * Sets the importeMaximo value for this WsDeuda.
     * 
     * @param importeMaximo
     */
    public void setImporteMaximo(java.lang.String importeMaximo) {
        this.importeMaximo = importeMaximo;
    }


    /**
     * Gets the discrecional value for this WsDeuda.
     * 
     * @return discrecional
     */
    public java.lang.String getDiscrecional() {
        return discrecional;
    }


    /**
     * Sets the discrecional value for this WsDeuda.
     * 
     * @param discrecional
     */
    public void setDiscrecional(java.lang.String discrecional) {
        this.discrecional = discrecional;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsDeuda)) return false;
        WsDeuda other = (WsDeuda) obj;
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
            ((this.abarcativa==null && other.getAbarcativa()==null) || 
             (this.abarcativa!=null &&
              this.abarcativa.equals(other.getAbarcativa()))) &&
            ((this.vencimientos==null && other.getVencimientos()==null) || 
             (this.vencimientos!=null &&
              java.util.Arrays.equals(this.vencimientos, other.getVencimientos()))) &&
            ((this.importeMinimo==null && other.getImporteMinimo()==null) || 
             (this.importeMinimo!=null &&
              this.importeMinimo.equals(other.getImporteMinimo()))) &&
            ((this.importeMaximo==null && other.getImporteMaximo()==null) || 
             (this.importeMaximo!=null &&
              this.importeMaximo.equals(other.getImporteMaximo()))) &&
            ((this.discrecional==null && other.getDiscrecional()==null) || 
             (this.discrecional!=null &&
              this.discrecional.equals(other.getDiscrecional())));
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
        if (getAbarcativa() != null) {
            _hashCode += getAbarcativa().hashCode();
        }
        if (getVencimientos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVencimientos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVencimientos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getImporteMinimo() != null) {
            _hashCode += getImporteMinimo().hashCode();
        }
        if (getImporteMaximo() != null) {
            _hashCode += getImporteMaximo().hashCode();
        }
        if (getDiscrecional() != null) {
            _hashCode += getDiscrecional().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsDeuda.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsDeuda"));
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
        elemField.setFieldName("abarcativa");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.ws.altadeuda.linkpagoservices.redlink.com.ar/", "abarcativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsAbarcativa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vencimientos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vencimientos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://altadeuda.service.ws.linkpagos.redlink.com.ar/", "wsVencimiento"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("", "vencimiento"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importeMinimo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importeMinimo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importeMaximo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importeMaximo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discrecional");
        elemField.setXmlName(new javax.xml.namespace.QName("", "discrecional"));
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

	@Override
	public String toString() {
		return "WsDeuda [idDeuda=" + idDeuda + ", concepto=" + concepto + ", abarcativa=" + abarcativa
				+ ", vencimientos=" + Arrays.toString(vencimientos) + ", importeMinimo=" + importeMinimo
				+ ", importeMaximo=" + importeMaximo + ", discrecional=" + discrecional + "]";
	}

}

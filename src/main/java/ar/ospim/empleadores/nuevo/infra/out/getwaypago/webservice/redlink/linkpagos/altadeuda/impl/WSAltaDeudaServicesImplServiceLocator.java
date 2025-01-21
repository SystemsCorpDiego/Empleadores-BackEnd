/**
 * WSAltaDeudaServicesImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.impl;

import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WSAltaDeudaServices;

public class WSAltaDeudaServicesImplServiceLocator extends org.apache.axis.client.Service implements ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.impl.WSAltaDeudaServicesImplService {

    public WSAltaDeudaServicesImplServiceLocator() {
    }


    public WSAltaDeudaServicesImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSAltaDeudaServicesImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSAltaDeudaServicesImplPort
    
    //private java.lang.String WSAltaDeudaServicesImplPort_address = "https://linkpagosservices.redlink.com.ar/linkpagosservices/AltaDeudasServices";
    private java.lang.String WSAltaDeudaServicesImplPort_address = "https://linkpagosservices.redlink.com.ar:443/linkpagosservices/AltaDeudasServices";
    //private java.lang.String WSAltaDeudaServicesImplPort_address = "https://200.45.17.143:443/linkpagosservices/AltaDeudasServices";
    
    public java.lang.String getWSAltaDeudaServicesImplPortAddress() {
        return WSAltaDeudaServicesImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSAltaDeudaServicesImplPortWSDDServiceName = "WSAltaDeudaServicesImplPort";

    public java.lang.String getWSAltaDeudaServicesImplPortWSDDServiceName() {
        return WSAltaDeudaServicesImplPortWSDDServiceName;
    }

    public void setWSAltaDeudaServicesImplPortWSDDServiceName(java.lang.String name) {
        WSAltaDeudaServicesImplPortWSDDServiceName = name;
    }

    public ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WSAltaDeudaServices getWSAltaDeudaServicesImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSAltaDeudaServicesImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSAltaDeudaServicesImplPort(endpoint);
    }

    public WSAltaDeudaServices getWSAltaDeudaServicesImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	WSAltaDeudaServicesImplServiceSoapBindingStub _stub = new WSAltaDeudaServicesImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSAltaDeudaServicesImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSAltaDeudaServicesImplPortEndpointAddress(java.lang.String address) {
        WSAltaDeudaServicesImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WSAltaDeudaServices.class.isAssignableFrom(serviceEndpointInterface)) {
            	WSAltaDeudaServicesImplServiceSoapBindingStub _stub = new WSAltaDeudaServicesImplServiceSoapBindingStub(new java.net.URL(WSAltaDeudaServicesImplPort_address), this);
                _stub.setPortName(getWSAltaDeudaServicesImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSAltaDeudaServicesImplPort".equals(inputPortName)) {
            return getWSAltaDeudaServicesImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://impl.altadeuda.service.ws.linkpagos.redlink.com.ar/", "WSAltaDeudaServicesImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://impl.altadeuda.service.ws.linkpagos.redlink.com.ar/", "WSAltaDeudaServicesImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSAltaDeudaServicesImplPort".equals(portName)) {
            setWSAltaDeudaServicesImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

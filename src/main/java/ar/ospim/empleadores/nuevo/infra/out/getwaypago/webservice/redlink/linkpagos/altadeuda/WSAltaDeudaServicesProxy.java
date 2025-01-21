package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda;

import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.impl.WSAltaDeudaServicesImplServiceLocator;

public class WSAltaDeudaServicesProxy implements WSAltaDeudaServices {
  private String _endpoint = null;
  private WSAltaDeudaServices wSAltaDeudaServices = null;
  
  public WSAltaDeudaServicesProxy() {
    _initWSAltaDeudaServicesProxy();
  }
  
  public WSAltaDeudaServicesProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSAltaDeudaServicesProxy();
  }
  
  private void _initWSAltaDeudaServicesProxy() {
    try {
      wSAltaDeudaServices = (new WSAltaDeudaServicesImplServiceLocator()).getWSAltaDeudaServicesImplPort();
      if (wSAltaDeudaServices != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSAltaDeudaServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSAltaDeudaServices)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSAltaDeudaServices != null)
      ((javax.xml.rpc.Stub)wSAltaDeudaServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public WSAltaDeudaServices getWSAltaDeudaServices() {
    if (wSAltaDeudaServices == null)
      _initWSAltaDeudaServicesProxy();
    return wSAltaDeudaServices;
  }
  
  public WsAltaDeDeudasResponse altaDeDeudas( WsAltaDeDeudasRequest requerimientoAltaDeDeudas) throws java.rmi.RemoteException{
    if (wSAltaDeudaServices == null)
      _initWSAltaDeudaServicesProxy();
    return wSAltaDeudaServices.altaDeDeudas(requerimientoAltaDeDeudas);
  }
  
  
}
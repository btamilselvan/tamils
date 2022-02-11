package cmecf.programs;

import java.util.Iterator;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import com.ibm.wsdl.extensions.soap.SOAPAddressImpl;
import com.ibm.wsdl.extensions.soap12.SOAP12AddressImpl;

public class Wsdl4JTest {

	public static void main(String[] args) {
		WSDLFactory wsdlFactory;
		try {
			String wsdlContentsFileName = "C:\\Tamil\\temp\\wsdl\\wsdl14170952372221785306.xml";
			wsdlFactory = WSDLFactory.newInstance();
			WSDLReader wsdlReader = wsdlFactory.newWSDLReader();
			wsdlReader.setFeature("javax.wsdl.verbose", true);
			wsdlReader.setFeature("javax.wsdl.importDocuments", true);
			Definition definition = wsdlReader.readWSDL(wsdlContentsFileName);
			String endpointAddress = getEndpointAddress(definition);
			System.out.println(endpointAddress);
		} catch (WSDLException e) {
			e.printStackTrace();
		}

	}

	private static String getEndpointAddress(Definition definition) {
		try {
			// get all services
			Map<?, ?> serviceMap = definition.getServices();
			Iterator<?> serviceItr = serviceMap.entrySet().iterator();

			while (serviceItr.hasNext()) {
				Map.Entry<?, ?> serviceEntry = (Map.Entry<?, ?>) serviceItr.next();
				Service service = (Service) serviceEntry.getValue();
				// get all ports from the service
				Map<?, ?> portMap = service.getPorts();
				Iterator<?> portItr = portMap.entrySet().iterator();
				while (portItr.hasNext()) {
					Map.Entry<?, ?> portEntry = (Map.Entry<?, ?>) portItr.next();
					Port port = (Port) portEntry.getValue();
					ExtensibilityElement extensibilityElement = (ExtensibilityElement) port.getExtensibilityElements()
							.get(0);
					// fetch the address from port
					String endpointAddress = getAddressUrl(extensibilityElement);
					if (endpointAddress != null) {
						return endpointAddress;
					}
				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	private static String getAddressUrl(ExtensibilityElement extensibilityElement) {
		if (extensibilityElement instanceof SOAP12AddressImpl) {
			return ((SOAP12AddressImpl) extensibilityElement).getLocationURI();
		} else if (extensibilityElement instanceof SOAPAddressImpl) {
			return ((SOAPAddressImpl) extensibilityElement).getLocationURI();
		}
		return null;
	}
}

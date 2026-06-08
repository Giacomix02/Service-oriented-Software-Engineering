package it.univaq.sose.LegacyBoxOfficeProviderSOAP;
import jakarta.servlet.ServletConfig;
import jakarta.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

public class SimpleCXFNonSpringServlet extends CXFNonSpringServlet {

	private static final long serialVersionUID = -6420470370401020050L;

	@Override
	public void loadBus(ServletConfig servletConfig) {
		
		super.loadBus(servletConfig);
		
		Bus bus = getBus();
		BusFactory.setDefaultBus(bus);
		Endpoint.publish("/sum", new SumImpl());
	}
}

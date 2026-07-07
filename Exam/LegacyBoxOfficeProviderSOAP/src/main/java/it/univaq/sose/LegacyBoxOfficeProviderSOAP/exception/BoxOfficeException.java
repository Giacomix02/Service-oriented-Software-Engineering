package it.univaq.sose.LegacyBoxOfficeProviderSOAP;

import jakarta.xml.ws.WebFault;

@WebFault(name = "BoxOfficeFault")
public class BoxOfficeException extends Exception {
	private static final long serialVersionUID = 1L;

	public BoxOfficeException(String message) {
		super(message);
	}
}
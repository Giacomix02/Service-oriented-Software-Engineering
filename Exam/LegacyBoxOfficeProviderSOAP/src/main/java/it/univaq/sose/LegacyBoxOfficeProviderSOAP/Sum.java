package it.univaq.sose.LegacyBoxOfficeProviderSOAP;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface Sum {

	@WebMethod
	public int sum(int a, int b);

	@WebMethod
	public int safeSum(int a, int b) throws SumException;
	
}

package it.univaq.sose.LegacyBoxOfficeProviderSOAP;

public class SumImpl implements Sum {

	@Override
	public int sum(int a, int b) {
		return a + b;
	}

	@Override
	public int safeSum(int a, int b) throws SumException {
		
//		try {
			return Math.addExact(a, b);
//	    } catch (ArithmeticException e) {
//	    	throw new SumException(e.getMessage());
//	    }
		
	}

}

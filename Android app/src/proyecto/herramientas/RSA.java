package proyecto.herramientas;

import java.math.BigInteger;

public class RSA{
	
	BigInteger e_cine,n_cine,e_server,n_server;
	
	private static final String CLAVE_PUBLICA_CINE="74BF";
	private static final String MODULO_CINE="B37E004127BB763AE8A0BBCDB984DEEBDBC9D939F9B686075BBC9FEA3B281E6A89CBAFBA8CC71833BA2953C1C1DFF265E9C99F6FB5AB6F33B8CF9751DA964ECD3B08210421944B4D560FA99610D9486122FB9009EE25A8FCCF266D349C819EA58B03BFC33A07EAA7207FDB3F2D1CEDC107D5235B99B9263EAC828EF42F420B09";
	private static final String CLAVE_PUBLICA_SERVER="0FBB";
	private static final String MODULO_SERVER="C3FEF9D332561305135E2C0AEFE4AB689C943E502697617F15D8E8A47DC95580790E573147FA38317467786CC30FD3E722142E6C6FDF3539F9199BF5D459EB43DA11F6BC428FE2B4ECC3D41C67D0D20C056C9483F22CFC8A91E8790CADED27EA0DE26666A41C4EF179203B5CA09C22D3180E8B5C99AA4EB420DE1006FC206E07";
	
	public BigInteger getE_server() {
		return e_server;
	}

	public void setE_server(BigInteger eServer) {
		e_server = eServer;
	}

	public BigInteger getN_server() {
		return n_server;
	}

	public void setN_server(BigInteger nServer) {
		n_server = nServer;
	}
	public BigInteger getE_cine() {
		return e_cine;
	}

	public void setE_cine(BigInteger eCine) {
		e_cine = eCine;
	}

	public BigInteger getN_cine() {
		return n_cine;
	}

	public void setN_cine(BigInteger nCine) {
		n_cine = nCine;
	}
	

	public static RSA genRSACine (){
		
		RSA rsa= new RSA();
	
		BigInteger clavePublica = new BigInteger(CLAVE_PUBLICA_CINE,16);
		BigInteger modulo = new BigInteger(MODULO_CINE,16);
		
		rsa.setE_cine(clavePublica);
		rsa.setN_cine(modulo);
		
		return rsa;
	}
	public static RSA getRSA(){
		RSA rsa1 = new RSA();
		
		BigInteger clavePublicaServer = new BigInteger(CLAVE_PUBLICA_SERVER,16);
		BigInteger moduloServer = new BigInteger(MODULO_SERVER,16);
		
		rsa1.setE_server(clavePublicaServer);
		rsa1.setN_server(moduloServer);
		return rsa1;
	}
	
}

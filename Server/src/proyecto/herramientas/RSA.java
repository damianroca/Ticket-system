package proyecto.herramientas;

import java.math.BigInteger;

public class RSA{
	
	BigInteger e,d,n,e_cine,d_cine,n_cine;

	private static final String CLAVE_PRIVADA="AE566F7A5122EC7DEF686D1A9324A77852D35355B96F5276A2D959C5FF5DB5C59E172E78A108661C9B089163186ED56811B9D78A9A0DADA8FB73A8083EA2331EBB91AEAEA8B88E1304AA13C723BA4AEDEE8DCCA8D393AFB4EFFDE3C73DBB7B4C11C94882D4759458326F49D6C4CA3370CE7CD8E8174463B0E1B528A44FD20583";
	private static final String CLAVE_PUBLICA="0FBB";
	private static final String MODULO="C3FEF9D332561305135E2C0AEFE4AB689C943E502697617F15D8E8A47DC95580790E573147FA38317467786CC30FD3E722142E6C6FDF3539F9199BF5D459EB43DA11F6BC428FE2B4ECC3D41C67D0D20C056C9483F22CFC8A91E8790CADED27EA0DE26666A41C4EF179203B5CA09C22D3180E8B5C99AA4EB420DE1006FC206E07";
	private static final String CLAVE_PRIVADA_CINE="3475B19D0F6A635EE604335C20B222D8FB48B2E01A72344B7479F2F79B8C00F99C9EA4B9B8C39D85060C2E6EDB96EB8066D27A99C2119927DF07D4BA7034576B2855ADEDA92FA05D71F7ADBF2D54429767ECDE3B4C054673A874D5B325A6E94423DCA2DBFDB39103F0708F2D4F1BFBB7A6F1AE23C187B3BD1F3C6531E4A6BDBF";
	private static final String CLAVE_PUBLICA_CINE="74BF";
	private static final String MODULO_CINE="B37E004127BB763AE8A0BBCDB984DEEBDBC9D939F9B686075BBC9FEA3B281E6A89CBAFBA8CC71833BA2953C1C1DFF265E9C99F6FB5AB6F33B8CF9751DA964ECD3B08210421944B4D560FA99610D9486122FB9009EE25A8FCCF266D349C819EA58B03BFC33A07EAA7207FDB3F2D1CEDC107D5235B99B9263EAC828EF42F420B09";
	
	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getD() {
		return d;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}
	public BigInteger getE_cine() {
		return e_cine;
	}

	public void setE_cine(BigInteger eCine) {
		e_cine = eCine;
	}

	public BigInteger getD_cine() {
		return d_cine;
	}

	public void setD_cine(BigInteger dCine) {
		d_cine = dCine;
	}

	public BigInteger getN_cine() {
		return n_cine;
	}

	public void setN_cine(BigInteger nCine) {
		n_cine = nCine;
	}

	public static RSA genRSA (){
		
		RSA rsa= new RSA();
	
		BigInteger clavePrivada = new BigInteger(CLAVE_PRIVADA,16);
		BigInteger clavePublica = new BigInteger(CLAVE_PUBLICA,16);
		BigInteger modulo = new BigInteger(MODULO,16);
		
		rsa.setD(clavePrivada);
		rsa.setE(clavePublica);
		rsa.setN(modulo);
		
		return rsa;
	}
	
	public static RSA genRSACine (){
		
		RSA rsa= new RSA();
	
		BigInteger clavePrivada = new BigInteger(CLAVE_PRIVADA_CINE,16);
		BigInteger clavePublica = new BigInteger(CLAVE_PUBLICA_CINE,16);
		BigInteger modulo = new BigInteger(MODULO_CINE,16);
		
		rsa.setD_cine(clavePrivada);
		rsa.setE_cine(clavePublica);
		rsa.setN_cine(modulo);
		
		return rsa;
	}
	
}


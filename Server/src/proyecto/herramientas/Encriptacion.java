package proyecto.herramientas;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class Encriptacion {
	
	static BigInteger modp= new BigInteger("1620539",10);
	static BigInteger alpha1= new BigInteger("2",10);
	
	public static String genR() {
		Random r=new Random();
		r.setSeed(new Date().getTime());
		Integer ru = new Integer(r.nextInt(1000000));
		return ru.toString();
		}
	
	public static String hashR (String valor){
		Sha1 s = new Sha1();

        try {
        	return s.getHash(valor);
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public static BigInteger firma (String t, RSA rsa){
	
        BigInteger firma= new BigInteger(hashR(t),16);
		return firma.modPow(rsa.getD(), rsa.getN());
	
	}
	
	public static BigInteger firmacine (String t, RSA rsa){
		
        BigInteger firma= new BigInteger(t,16);
		return firma.modPow(rsa.getD_cine(), rsa.getN_cine());
	
	}
	
	public static BigInteger desfirmacine (String t, RSA rsa){
		
        BigInteger firma= new BigInteger(t,16);
		return firma.modPow(rsa.getE_cine(), rsa.getN_cine());
	
	}

	public static BigInteger cAlphaRuRe(String alpharu, String re) {
		//b es alphaRu i b2 es Re
		//cap dels dos va firmat
		BigInteger i1= new BigInteger(alpharu,16);
		BigInteger i2= new BigInteger(re,10);
		BigInteger calculo= i1.modPow(i2, modp);
		return calculo;
		//hem calculat alphaRuRe per poder-ho emprar per calcular Ru		
	}

	public static BigInteger cAlphaRe(String re) {
		
		//calculamos alphaRe, lo cifrmos con la Prp. Se devuelve la cadena ya firmada con Prp
		BigInteger r= new BigInteger(re);
		BigInteger calculo =alpha1.modPow(r, modp);
		//ahora la vamos a firmar
		calculo=firmacine(calculo.toString(16),RSA.genRSACine());
		return calculo;
	}

	public static BigInteger cRu(String au, String aRuRe) {
		//Calcula ru con los dos parametros que me han pasado.
		BigInteger i1= new BigInteger(au,16);
		BigInteger i2= new BigInteger(aRuRe,16);
		BigInteger calculo= i1.xor(i2);
		return calculo;
	}

	public static boolean compHash(String ruCalculado, String hashRu) {
		//calculamos el hash del ru calculado y lo comparamos con el que ya teniamos
		String hRu= hashR(ruCalculado);
		if(hRu.equals(hashRu)){
			return true;
		}else{
			return false;
		}
	}

	public static BigInteger cAp(String alphaRuRe, String re) {
		// TODO Auto-generated method stub
		BigInteger i1=new BigInteger(alphaRuRe,16);
		BigInteger i2=new BigInteger(re,10);
		BigInteger calculo= i1.xor(i2);
		calculo=firmacine(calculo.toString(16),RSA.genRSACine());
		//calculamos Ap segun la formula
		//Ap se tiene que enviar cifrado
		return calculo;
	}

	public static BigInteger devolverRu(String ru) {
		BigInteger i = new BigInteger(ru);
		//firmo el parametro con mi clave privada
		BigInteger calculo = firmacine(i.toString(16),RSA.genRSACine());
		return calculo;
	}


	
}

class Sha1 {
    private MessageDigest md;
    private byte[] buffer, digest;
    private String hash = "";

    public String getHash(String message) throws NoSuchAlgorithmException {
    	//El resultado siempre sera una cadena de 40 caracteres, o 160 bits es lo mismo
        buffer = message.getBytes();
        //buffer = message.getBytes("UTF-8");//si se quiere codificacion utf-8
        md = MessageDigest.getInstance("SHA1");
        md.update(buffer);
        digest = md.digest();

        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }

        return hash;
    }
}




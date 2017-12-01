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
	
	public static BigInteger cfirma (String t, RSA rsa){
	
       	BigInteger f = new BigInteger(t,16);
		return f.modPow(rsa.getE_server(), rsa.getN_server());
	}
	
	public static BigInteger desfirmacine (String t, RSA rsa){
		
        BigInteger firma= new BigInteger(t,16);
		return firma.modPow(rsa.getE_cine(), rsa.getN_cine());
	
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
	
	public static boolean comprobarFirma(String campos, String firma){
			
		BigInteger comprobacion= cfirma(firma,RSA.getRSA());
		BigInteger hashcampos=new BigInteger(hashR(campos),16);
		return comprobacion.equals(hashcampos);
	}

	public static BigInteger cAu(String alphaRuRe, String ru) {
		//dins el camp alpharure ve el alpha elevat a re que ha enviat el servidor
		//descifrar la info de alphaRuRe, elevarlo a Ru, despues xor Ru
		BigInteger info= desfirmacine(alphaRuRe,RSA.genRSACine());
		BigInteger r= new BigInteger(ru);
		BigInteger calculo = info.modPow(r, modp);
		calculo=calculo.xor(r);
		//dins calculo hi ha el valor de Au
		return calculo;
	}

	public static BigInteger cRe(String ap, String alphaRuRe, String ru) {
		// TODO Auto-generated method stub
		//aqui dintre hi ha ap xifrat
		BigInteger i1= new BigInteger(ap,16);
		//aqui dintre hi ha alpha elevat a Re xifrat
		BigInteger i2= new BigInteger(alphaRuRe,16);
		//calcula el valor de Re segun la formula
		BigInteger r= new BigInteger(ru,10);
		//desxifram els dos camps
		i1=desfirmacine(i1.toString(16),RSA.genRSACine());
		i2=desfirmacine(i2.toString(16),RSA.genRSACine());
		//calculam alpha elevat a Re i Ru
		BigInteger calculo = i2.modPow(r, modp);
		//feim Ap xor alphaRuRe
		calculo=calculo.xor(i1);
		//dins calculo tenim el valor de Re
		return calculo;
	}

	public static boolean compHashRe(String re, String hashRe) {
		String hre= hashR(re);
		if(hre.equals(hashRe)){
			return true;
		}else {
			return false;
		}
		//comprueba que el hash del re calculado es igual al hash del que teniamos
	}

	public static String cAlphaRu(String ru) {
		BigInteger r = new BigInteger(ru,10);
		return alpha1.modPow(r, modp).toString(16);
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
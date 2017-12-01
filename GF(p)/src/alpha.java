

import java.math.BigInteger;
import java.util.Random;

public class alpha
{
    private static final Random rnd = new Random();

    private static boolean miller_rabin_pass(BigInteger a, BigInteger n) {
        BigInteger n_minus_one = n.subtract(BigInteger.ONE);
        BigInteger d = n_minus_one;
	int s = d.getLowestSetBit();
	d = d.shiftRight(s);

        BigInteger a_to_power = a.modPow(d, n);
        if (a_to_power.equals(BigInteger.ONE)) return true;
        for (int i = 0; i < s-1; i++) {
            if (a_to_power.equals(n_minus_one)) return true;
            a_to_power = a_to_power.multiply(a_to_power).mod(n);
        }
        if (a_to_power.equals(n_minus_one)) return true;
        return false;
    }


    public static boolean miller_rabin(BigInteger n, int comp) {
        for (int repeat = 0; repeat < comp; repeat++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), rnd);
            } while (a.equals(BigInteger.ZERO));
            if (!miller_rabin_pass(a, n)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {

            int ite=0;
            int max=2;
            int comp = 10;
            int nbits =100;
            BigInteger p1= new BigInteger("1");
            BigInteger p2= new BigInteger("6");
            BigInteger p3= new BigInteger("5");
            BigInteger p4= new BigInteger("2");
            BigInteger q= new BigInteger(nbits, rnd);
            BigInteger p,y;
            BigInteger alpha=new BigInteger("2");
            BigInteger resto=q.mod(p2);
            q=q.subtract(resto);
            q=q.add(p3);
            do{
               while (!miller_rabin(q,comp)){
                q=q.add(p2);
               // if (q.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) continue;
				//if (q.mod(BigInteger.valueOf(3)).equals(BigInteger.ZERO)) continue;
				if (q.mod(BigInteger.valueOf(5)).equals(BigInteger.ZERO)) continue;
				if (q.mod(BigInteger.valueOf(7)).equals(BigInteger.ZERO)) continue;
                }
             p=q;
             p=p.add(p);//p=2*p
             p=p.add(p1);//p=2*p+1
            } while (!miller_rabin(p,comp));
            System.out.println("el nombre q es :" + q);
            System.out.println("el nombre p es :" + p);
            //Podem dividir (p-1)/q i dona un nombre sense decimals que és 2.
            //Són els dos divisors del nombre p
           
            //Les seguents lienes nomes són per comprovar els generadors de nombre petits
             /*p=new BigInteger("17");
            q=new BigInteger("8");*/
            
            y=p;
            y=y.subtract(p1);//y=p-1
            bucle:
            while(!alpha.equals(y))  {
               if((alpha.modPow(p4,p)).equals(p1)){
                   alpha=alpha.add(p1);
                   continue bucle;}
               else if ((alpha.modPow(q,p)).equals(p1)){
                   alpha=alpha.add(p1);
                   continue bucle;}
               else {
                   System.out.println(alpha + " es un generador");
                   alpha=alpha.add(p1);
                   ite++;}
                   if(ite==max){break bucle;}
             }

            
           

         }
    }



package com.exampleservlet;

import java.io.*;
import java.math.BigInteger;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import proyecto.herramientas.Encriptacion;
import proyecto.herramientas.RSA;

public class HTMLPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Taquilla taquilla;   

    public HTMLPage() {
        super();
        taquilla = new Taquilla();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Se ha conectado al servlet con get");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//String input;
		/*InputStreamReader isr = new InputStreamReader( request.getInputStream() );
		BufferedReader br= new BufferedReader(isr);
		while((input=br.readLine())!=null){
			s=s+input;	
			System.out.println(input);
		}
		br.close();
		*/
		
		
		Ticket t= new Ticket();
		DatosPersonales datos= new DatosPersonales();
		DatosEvento evento= new DatosEvento();
		Propiedades prop = new Propiedades();
		t.setDp(datos);
        t.setDe(evento);
        t.setP(prop);
		t.decodificarC(request.getInputStream());

		if(prop.getOrigen().equals("Aplicativo")){
			System.out.println("Se ha solicitado un ticket");
			prop.setNumSerie(taquilla.genNumeroSerie());
			taquilla.sellaTicket(t);
			String re = Encriptacion.genR();
			prop.setHashRe(Encriptacion.hashR(re));
			prop.setFirmaTicket(Encriptacion.firma(t.toString(false),RSA.genRSA()).toString(16));
			t.enviarTicket(response.getOutputStream());
			prop.setRe(re);
			t.serializarTicket("Proveedor");
			t.serializarTicket("Cine");
			System.out.println(t.toString());
		
		}else if(prop.getOrigen().equals("Uso")){
			System.out.println("Se ha entregado un ticket");
			System.out.println(t.toString());
			//en t tengo el ticket con los parametros que necesito para verficarlo cargado
			//en t2  es el ticket que deserializo para comrpovar si es el que se vendio
			Ticket t2 = new Ticket();
			File dir =new File("C:/Documents and Settings/Damian/workspace/Cine_Tickets");
			String []ficheros = dir.list();
			for(int i=0;i<ficheros.length;i++){
				System.out.println("entro en el for");
				System.out.println(ficheros[i]);
				if(ficheros[i].substring(ficheros[i].indexOf("_")+1, ficheros[i].length()-4).equals(t.getP().getNumSerie())){
					//el -4 es para quitarle la extension .tkt al nombre del ticket
					System.out.println("he encontrado el nombre del fichero");
					try {
						FileInputStream fis = new FileInputStream("C:/Documents and Settings/Damian/workspace/Cine_Tickets/"+ficheros[i]);
						ObjectInputStream ois= new ObjectInputStream(fis);
						t2=(Ticket)ois.readObject();
					} catch (Exception e) {
						e.printStackTrace();
					}
					boolean tiempo=new Date().before(t2.getP().gettValidez());
					//la var tiempo nos srive para comprobar si el evento no ha tenido lugar.
					if((!t2.getP().getTicketUsado())&&(tiempo)){
						System.out.println("Vamos a calcular AlphaRuRe");
						//si ticket no usado, calculamos alpha elevado a Re
						BigInteger bg = Encriptacion.cAlphaRe(t2.getP().getRe());
						System.out.println("tengo AlphaRuRe");
						//se lo enviamos alp aplicativo dentro de alphaRuRe
						t.getP().setAlphaRuRe(bg.toString(16));
						t2.getP().setAlphaRuRe(bg.toString(16));
						//le envio el ticket que me habia enviado, no el que he deserializado
						t.enviarTicket(response.getOutputStream());
						t2.serializarTicket("Cine");
						//se cierra esta connexion, y cuando el usuario vuelva, se ira a uso2
					}else{
						System.out.println("el ticket ya se habia usado o ya ha empezado el evento");
						//enviar mensaje de que no puede entrar
						//BigInteger bg = Encriptacion.cAlphaRuRe(t.getP().getAlphaRu(),t2.getP().getRe());
						BigInteger bg;
						if(tiempo){
							bg = Encriptacion.devolverRu(t2.getP().getRu());
							//si el tiempo es ok pero ya habia sido usado, enviamos el valor de ru
						}else{
							bg= new BigInteger("0");
							//Si se devuelve un 0 significa que ya no se puede entrar porqueel evento ya ha empezado
						}
						//el flag es busado que hasta que no que acabe la recpcion del ticket no sera true
						//si el U recibe false, el ticket no habia sido usado.
						//cuando reciba esto vera que P ya conoce el ru de este ticket, aparte de que busudo es true
						t.getP().setAlphaRuRe(bg.toString(16));
						t2.getP().setAlphaRuRe(bg.toString(16));
						//basta posar dins el ticket que va a l'usuari el ru del ticket desirialitzat.
						t.enviarTicket(response.getOutputStream());//le envio el que me habia enviado, no el que he deserializado
					}
					break;
				}
			}
		}else if(prop.getOrigen().equals("Entrega")){
			System.out.println("Se ha entregado un ticket con Entrega");
			Ticket t2 = new Ticket();
			File dir =new File("C:/Documents and Settings/Damian/workspace/Cine_Tickets");
			String []ficheros = dir.list();
			for(int i=0;i<ficheros.length;i++){
				if(ficheros[i].substring(ficheros[i].indexOf("_")+1, ficheros[i].length()-4).equals(t.getP().getNumSerie())){
					try {
						FileInputStream fis = new FileInputStream("C:/Documents and Settings/Damian/workspace/Cine_Tickets/"+ficheros[i]);
						ObjectInputStream ois= new ObjectInputStream(fis);
						t2=(Ticket)ois.readObject();
					} catch (Exception e) {
						e.printStackTrace();
					}
					//dins bg hi ha el alpha elevat a re i ru
					BigInteger bg = Encriptacion.cAlphaRuRe(t.getP().getAlphaRu(),t2.getP().getRe());
					BigInteger bg1=Encriptacion.cRu(t.getP().getAu(),bg.toString(16));
					//se pone en base 10 porque el el hashru de antes se habia calculado con base 10
					System.out.println("vamos a comparar lo hash");
					if(Encriptacion.compHash(bg1.toString(10),t2.getP().getHashRu())){
						//guardo el valor de Ru en el cine
						System.out.println("los hash son iguales");
						t2.getP().setRu(bg1.toString(10));
						//marco el ticket como usado
						t2.getP().setTicketUsado(true);
						BigInteger bg2=Encriptacion.cAp(bg.toString(16),t2.getP().getRe());
						
						t2.getP().setAp(bg2.toString(16));
						t.getP().setAp(bg2.toString(16));
												
						t.getP().settUso(new Date());
						t2.getP().settUso(new Date());
						
						t.getP().setPuerta(true);
						System.out.println("Abro la puerta del cine");
						//le abrimos la puerta
						t.enviarTicket(response.getOutputStream());
						t2.serializarTicket("Cine");
					}else{
						//si el hash fuese diferente, no le dejo entrar
						//nuevo campo en el ticket que sea puerta abierta o cerrada
						t.getP().setPuerta(false);
						System.out.println("Cierro la puerta del cine");
						//no  hace ponerlo, por defecto es false
						t.enviarTicket(response.getOutputStream());
					}
				}
			}
		}
		
	}
}

package proyecto.aplicativo;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import proyecto.herramientas.Encriptacion;
import android.app.Activity;
import android.content.Context;

public class Ticket implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DatosPersonales dp;
	private DatosEvento de;
	private Propiedades p;

	public Propiedades getP() {
		return p;
	}

	public void setP(Propiedades p) {
		this.p = p;
	}

	public DatosEvento getDe() {
		return de;
	}

	public void setDe(DatosEvento de) {
		this.de = de;
	}

	public DatosPersonales getDp() {
		return dp;
	}

	public void setDp(DatosPersonales dp) {
		this.dp = dp;
	}
	public String toString(){
		return this.toString(true);
	}
	
	public String toString(boolean confidencial){
		String resultado="";
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
		if(dp.getNombre().length()==0){	resultado+="nombre: "+"null";}
		else{resultado +="nombre: "+dp.getNombre();}
		resultado +="\n";
		if(dp.getEmail().length()==0){resultado+="email: "+"null";}
		else{resultado +="email: "+dp.getEmail();}
		resultado +="\n";
		resultado +="cine: "+de.getCine();
		resultado +="\n";
		resultado +="pelicula: "+de.getPelicula();
		resultado +="\n";
		resultado +="hora: "+de.getHora();
		resultado +="\n";
		resultado +="fecha: "+de.getFecha();
		resultado +="\n";
		resultado +="numSerie: "+p.getNumSerie();
		resultado +="\n";
		if (confidencial){
			resultado += "Re: " + p.getRe();
			resultado += "\n";
			resultado += "Ru: " + p.getRu();
			resultado += "\n";
			resultado += "alphaRu: " + p.getAlphaRu();
			resultado += "\n";
			resultado += "Origen: " + p.getOrigen();
			resultado += "\n";
			resultado += "AlphaRuRe: " + p.getAlphaRuRe();
			resultado += "\n";
			resultado += "tUso: " + p.gettUso();
			resultado += "\n";
			resultado += "Ap: " + p.getAp();
			resultado += "\n";
			resultado += "Au: " + p.getAu();
			resultado += "\n";
			resultado += "ticketUsado: " + p.getTicketUsado().toString();
			resultado += "\n";
			resultado += "firmaTicket: " + p.getFirmaTicket();
			resultado += "\n";
		}
		resultado +="hashRe: "+p.getHashRe();
		resultado +="\n";
		resultado +="hashRu: "+p.getHashRu();
		resultado +="\n";
		resultado +="tExpedicion: "+sdf.format(p.gettExpedicion());
		resultado +="\n";
		resultado +="tValidez: "+sdf.format(p.gettValidez());
		
		return resultado;		
	}
	
	public void serializarTicket(Activity a){

		String fticket="Ticket_"+p.getNumSerie();//si el file no esta creado, lo creara al hacer el write
		//la fecha no sirve porque lleva /
		try {
			FileOutputStream fos = a.openFileOutput(fticket, Context.MODE_PRIVATE);
			//Al ser modo privado estos tickets solo se ven desde dentro de mi aplicativo
			//, y estan guardados dentro de su sistema de archivo. Cuando se borra el aplicativo
			//se borran todos los ficheros que hay dentro de ese aplicativo
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			oos.writeObject(this);
		} catch (Exception e) {
			e.printStackTrace();
			p.setRe(e.toString());
		}
		
	}
	
	public void deserializar(Activity a, String name) {
		// TODO Auto-generated method stub
		ObjectInputStream ois;
		Ticket t = null;
		try {
			FileInputStream fis =a.openFileInput(name);
			ois= new ObjectInputStream(fis);
			t= (Ticket) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			p.setRe(e.toString());
		}
		this.setDe(t.getDe());
		this.setDp(t.getDp());
		this.setP(t.getP());
		//deserializo y pongo los parametros en mi ticket 
	}

	public void cargarTicket(String url){
		
		String fecha="";
		Scanner s = new Scanner(url);
		s.useDelimiter("[=&]");
		//Se usa el delimitador = o bien &
		
		if (s.next().equals("nombre")) {
			dp.setNombre(s.next());
		}
		if (s.next().equals("correo")) {
			dp.setEmail(s.next());
		}
		if (s.next().equals("cine")) {
			de.setCine(s.next());
		}
		if (s.next().equals("pelicula")) {
			de.setPelicula(s.next());
		}
		if (s.next().equals("hora")) {
			de.setHora(s.next());
		}
		if (s.next().equals("dia")) {
			fecha+=s.next();
			fecha+="/";
		}
		if (s.next().equals("mes")) {
			fecha+=s.next();
			fecha+="/";
		}
		if (s.next().equals("temporada")) {
			fecha+=s.next();
			de.setFecha(fecha);
		}
	}
	
	public void enviarTicket() {
		//String response="";
		// Este metodo enviara el ticket con los datos de la peticion al
		// servlet, recogera el ticket generado por este y lo guardara 
		//Esto crea una etiqueta con el nombre que se le pasa 
		try {
			Element ticket = new Element("ticket");
			
			Element datos = new Element("Datos_Personales");
			Element nombre = new Element("Nombre");
			nombre.addContent(dp.getNombre());
			datos.addContent(nombre);
			Element email = new Element("Email");
			email.addContent(dp.getEmail());
			datos.addContent(email);
			ticket.addContent(datos);
			
			Element evento = new Element("Datos_Evento");
			Element cine = new Element("Cine");
			cine.addContent(de.getCine());
			evento.addContent(cine);
			Element pelicula = new Element("Pelicula");
			pelicula.addContent(de.getPelicula());
			evento.addContent(pelicula);
			Element hora = new Element("Hora");
			hora.addContent(de.getHora());
			evento.addContent(hora);
			Element fecha = new Element("Fecha");
			fecha.addContent(de.getFecha());
			evento.addContent(fecha);
			
			Element propiedades = new Element("Propiedades");
			Element hashRu = new Element("hashRu");
			hashRu.addContent(p.getHashRu());
			propiedades.addContent(hashRu);
			Element hashRe = new Element("hashRe");
			hashRe.addContent(p.getHashRe());
			propiedades.addContent(hashRe);
			Element alphaRu = new Element("alphaRu");
			alphaRu.addContent(p.getAlphaRu());
			propiedades.addContent(alphaRu);
			Element origen = new Element("Origen");
			origen.addContent(p.getOrigen());
			propiedades.addContent(origen);
			Element num = new Element("NumSerie");
			num.addContent(p.getNumSerie());
			propiedades.addContent(num);
			Element rure = new Element("alphaRuRe");
			rure.addContent(p.getAlphaRuRe());
			propiedades.addContent(rure);
			Element au = new Element("Au");
			au.addContent(p.getAu());
			propiedades.addContent(au);
			Element ap = new Element("Ap");
			ap.addContent(p.getAp());
			propiedades.addContent(ap);
			
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			if(p.gettExpedicion()!=null){
				Element texp = new Element("tExpedicion");
				texp.addContent(sdf.format(p.gettExpedicion()));
				propiedades.addContent(texp);
			}
			if(p.gettValidez()!=null){
				Element tval = new Element("tValidez");
				tval.addContent(sdf.format(p.gettValidez()));
				propiedades.addContent(tval);
			}
			if(p.gettUso()!=null){
				Element tuso = new Element("tUso");
				tuso.addContent(sdf.format(p.gettUso()));
				propiedades.addContent(tuso);
			}
			
			ticket.addContent(propiedades);
			
			ticket.addContent(evento);
			
			Document doc = new Document(ticket);
			
			Format f = Format.getPrettyFormat();
			f.setIndent(" ");
			f.setLineSeparator("\n");
			XMLOutputter out = new XMLOutputter(f);
			
			URL url = new URL(p.getURL());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			OutputStreamWriter outs= new OutputStreamWriter(con.getOutputStream());				
			out.output(doc, outs);
			outs.flush();
			outs.close();
			
			DefaultHandler handler = new Interpretador();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
		    saxParser.parse(con.getInputStream(), handler);
	
			 /*InputStreamReader isr=new InputStreamReader(con.getInputStream());
			 BufferedReader br= new BufferedReader(isr);
			 String input;
			 while((input=br.readLine())!=null){
			 response=response+input;				
			}
			br.close();
		*/
			
		} catch (Exception e) {
			e.printStackTrace();
			//response+=e.toString();
		}
		//return response;
	}

	public boolean usarTicket(Activity a) {
		//TextView view = new TextView(a);
		p.setURL("http://192.168.1.33:8080/ExampleServlet1/HTMLPage");
		p.setOrigen("Uso");
		this.enviarTicket();
		//se recibe el ticket con los nuevos parametros para su utilizacion
		if(!p.getTicketUsado()){
			//el campoRu sigue estando en mi ticket
			BigInteger bg = Encriptacion.cAu(p.getAlphaRuRe(),p.getRu());
			p.setAu(bg.toString(16));
			p.setURL("http://192.168.1.33:8080/ExampleServlet1/HTMLPage");
			p.setOrigen("Entrega");
			this.enviarTicket();
			//se recibe el ticket con los nuevos parametros para su utilizacion
			//aqui hi havi el calcul de re
			//els hash d'abans s'havien calculat en base 10, per aixo si passen
			//si el cine ens ha dit que podem passar, hem de fer aixo
			if (p.getPuerta()) {
				BigInteger bg1=Encriptacion.cRe(p.getAp(),p.getAlphaRuRe(),p.getRu());
				if (Encriptacion.compHashRe(bg1.toString(10), p.getHashRe())) {
					p.setRe(bg1.toString(10));
					this.serializarTicket(a);
					// guardam el ticket que hem emprat mab els nous parametres,com Re, data d'utilitzacio ,etc
					//la puerta esta abierta
					return true;
				} else {
					return false;
					// el cine no es de confianza, desconoze Re
				}
			} else {
				return false;
				// el cine no nos ha abierto la entrada
			}
		}else{
			return false;//el ticket ya habia sido usado
		}
	}

	public void decodificarC (InputStream is){
		try {
					
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} 			
	}
	
	class Interpretador extends DefaultHandler{
		
		StringBuffer textBuffer = null;
		
		boolean bticket=false;
		boolean bdatospersonales=false;
		boolean bdatosevento = false;
		boolean bpropiedades=false;
		boolean bnombre=false;
		boolean bpelicula=false;
		boolean bemail=false;
		boolean bcine=false;
		boolean bhora=false;
		boolean bfecha=false;
		boolean bnumserie=false;
		boolean bhashru=false;
		boolean bhashre=false;
		boolean bfirmaticket=false;
		boolean btexpedicion=false;
		boolean btvalidez=false;
		boolean busado=false;
		boolean brure=false;
		boolean bau=false;
		boolean bap=false;
		boolean btuso=false;
		boolean bpuerta=false;
		
		public void startElement(String namespaceURI,String sName,String qName, Attributes attrs)throws SAXException{
			
		  String eName = sName; //eName es el nombre del elemento que hemos abierto
		  if ("".equals(eName)){ eName = qName;} 
		  
		  if (eName == "ticket"){bticket=true;}
		  if (eName == "Datos_Personales"){bdatospersonales=true;}
		  if (eName == "Nombre"){bnombre=true;}
		  if (eName == "Email"){bemail=true;}
		  if (eName == "Datos_Evento"){bdatosevento=true;}
		  if (eName == "Cine"){bcine=true;}
		  if (eName == "Pelicula"){bpelicula=true;}
		  if (eName == "Hora"){bhora=true;}
		  if (eName == "Fecha"){bfecha=true;}
		  if (eName == "Propiedades"){bpropiedades=true;}
		  if (eName == "NumSerie"){bnumserie=true;}
		  if (eName == "hashRu"){bhashru=true;}
		  if (eName == "hashRe"){bhashre=true;}
		  if (eName == "firmaTicket"){bfirmaticket=true;}
		  if (eName == "tExpedicion"){btexpedicion=true;}
		  if (eName == "tValidez"){btvalidez=true;}
		  if (eName == "ticketUsado"){busado=true;}
		  if (eName == "alphaRuRe"){brure=true;}
		  if (eName == "tUso"){btuso=true;}
		  if (eName == "Ap"){bap=true;}
		  if (eName == "Au"){bau=true;} 
		  if (eName == "Puerta"){bpuerta=true;} 
		} 
		
		public void endElement(String namespaceURI,String sName, String qName )throws SAXException{
			
		  String eName = sName; 
		  if ("".equals(eName)){ eName = qName;}
		  
		  if (eName == "ticket"){bticket=false;}
		  if (eName == "Datos_Personales"){bdatospersonales=false;}
		  if (eName == "Nombre"){bnombre=false;}
		  if (eName == "Email"){bemail=false;}
		  if (eName == "Datos_Evento"){bdatosevento=false;}
		  if (eName == "Cine"){bcine=false;}
		  if (eName == "Pelicula"){bpelicula=false;}
		  if (eName == "Hora"){bhora=false;}
		  if (eName == "Fecha"){bfecha=false;}
		  if (eName == "Propiedades"){bpropiedades=false;}
		  if (eName == "NumSerie"){bnumserie=false;}
		  if (eName == "hashRu"){bhashru=false;}
		  if (eName == "hashRe"){bhashre=false;}
		  if (eName == "firmaTicket"){bfirmaticket=false;}
		  if (eName == "tExpedicion"){btexpedicion=false;}
		  if (eName == "tValidez"){btvalidez=false;}
		  if (eName == "ticketUsado"){busado=false;}
		  if (eName == "alphaRuRe"){brure=false;}
		  if (eName == "tUso"){btuso=false;}
		  if (eName == "Ap"){bap=false;}
		  if (eName == "Au"){bau=false;} 
		  if (eName == "Puerta"){bpuerta=false;}
		  textBuffer=null;
		} 
		
		public void characters(char buf[], int offset, int len)throws SAXException	{
			
			String s = new String(buf, offset, len);
			if (textBuffer == null) {
				textBuffer = new StringBuffer(s);
			} else {
				textBuffer.append(s);
			}
			
			if(bticket&bdatospersonales&bnombre){
				dp.setNombre(textBuffer.toString());
			}
			if(bticket&bdatospersonales&bemail){
				dp.setEmail(textBuffer.toString());
			}
			if(bticket&bdatosevento&bcine){
				de.setCine(textBuffer.toString());
			}
			if(bticket&bdatosevento&bpelicula){
				de.setPelicula(textBuffer.toString());
			}
			if(bticket&bdatosevento&bhora){
				de.setHora(textBuffer.toString());
			}
			if(bticket&bdatosevento&bfecha){
				de.setFecha(textBuffer.toString());
			}
			if(bticket&bpropiedades&bnumserie){
				p.setNumSerie(textBuffer.toString());
			}	
			if(bticket&bpropiedades&bhashru){
				p.setHashRu(textBuffer.toString());
			}	
			if(bticket&bpropiedades&bhashre){
				p.setHashRe(textBuffer.toString());
			}
			if(bticket&bpropiedades&bpuerta&!(textBuffer.equals(""))){
				p.setPuerta(new Boolean(textBuffer.toString()));
			}
			if(bticket&bpropiedades&bfirmaticket){
				p.setFirmaTicket(textBuffer.toString());
			}	
			if(bticket&bpropiedades&busado&!(textBuffer.equals(""))){
				p.setTicketUsado(new Boolean(textBuffer.toString()));
			}
			if(bticket&bpropiedades&bau){
				p.setAu(textBuffer.toString());
			}
			if(bticket&bpropiedades&bap){
				p.setAp(textBuffer.toString());
			}
			if(bticket&bpropiedades&brure){
				p.setAlphaRuRe(textBuffer.toString());
			}
			if(bticket&bpropiedades&btexpedicion){
				SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date d=null;
				try {
					d = sdf.parse(textBuffer.toString());
				} catch (ParseException e) {
					e.printStackTrace();
					d=null;
					
				}
				p.settExpedicion(d);
			}	
			if(bticket&bpropiedades&btvalidez){
				SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date d=null;
				try {
					d = sdf.parse(textBuffer.toString());
				} catch (ParseException e) {
					e.printStackTrace();
					d=null;
				}
				p.settValidez(d);
			}	
			if(bticket&bpropiedades&btuso){
				SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date d=null;
				try {
					d = sdf.parse(textBuffer.toString());
				} catch (ParseException e) {
					e.printStackTrace();
					d=null;
				}
				p.settUso(d);
			}	
			
		} 
	}

	/*public void cAlphaRu() {
		BigInteger modp= new BigInteger("1620539",10);
		BigInteger alpha1= new BigInteger("2",10);
		BigInteger r = new BigInteger(p.getRu(),10);
		p.setAlphaRu(alpha1.modPow(r, modp).toString());
	}*/
}

class DatosPersonales implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Nombre;
	private String Email;

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = adaptaEspacios(nombre);
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = adaptaEmail(email);
	}

	private String adaptaEspacios(String cadena) {
		// Se adapatan las cadenas recogidas en el html para su correcta interpretacion
		cadena = cadena.replace('+', ' ');
		return cadena;
	}

	private String adaptaEmail(String cadena) {
		// Se adapta el email para darle un formato correcto
		cadena = cadena.replaceFirst("%40", "@");
		return cadena;
	}
}

class DatosEvento  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Cine;
	private String Pelicula;
	private String Hora;
	private String Fecha;

	public String getHora() {
		return Hora;
	}

	public void setHora(String hora) {
		this.Hora = adaptaHora(hora);
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		this.Fecha = fecha;
	}

	public String getCine() {
		return Cine;
	}

	public void setCine(String cine) {
		Cine = adaptaEspacios(cine);
	}

	public String getPelicula() {
		return Pelicula;
	}

	public void setPelicula(String pelicula) {
		Pelicula = adaptaEspacios(pelicula);
	}

	private String adaptaEspacios(String cadena) {
		cadena = cadena.replace('+', ' ');
		// Se adapatan las cadenas recogidas en el html para su correcta
		// interpretacion
		return cadena;
	}
	private String adaptaHora(String cadena) {
		cadena = cadena.replaceFirst("%3A", ":");
		// Se adapta el email para darle un formato correcto
		return cadena;
	}
}

class Propiedades implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numSerie="";
	private Date tValidez;
	private Date tExpedicion;
	private String firmaTicket="";
	private String re="";
	private String hashRe="";
	private String ru="";
	private String hashRu="";
	private String alphaRu="";
	private String URL="";	
	private String Origen="";
	private Boolean ticketUsado=false;
	private String alphaRuRe="";
	private String Au="";
	private String Ap="";
	private Date tUso;
	private Boolean puerta=false;
	private boolean mostrado=false;
	
	
	public boolean isMostrado() {
		return mostrado;
	}

	public void setMostrado(boolean mostrado) {
		this.mostrado = mostrado;
	}

	public Boolean getPuerta() {
		return puerta;
	}

	public void setPuerta(Boolean puerta) {
		this.puerta = puerta;
	}

	public String getAlphaRuRe() {
		return alphaRuRe;
	}

	public void setAlphaRuRe(String alphaRuRe) {
		this.alphaRuRe = alphaRuRe;
	}

	public String getAu() {
		return Au;
	}

	public void setAu(String au) {
		Au = au;
	}

	public String getAp() {
		return Ap;
	}

	public void setAp(String ap) {
		Ap = ap;
	}

	public Date gettUso() {
		return tUso;
	}

	public void settUso(Date tUso) {
		this.tUso = tUso;
	}

	public Boolean getTicketUsado() {
		return ticketUsado;
	}

	public void setTicketUsado(Boolean ticketUsado) {
		this.ticketUsado = ticketUsado;
	}

	public String getOrigen() {
		return Origen;
	}

	public void setOrigen(String origen) {
		Origen = origen;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getAlphaRu() {
		return alphaRu;
	}

	public void setAlphaRu(String alphaRu) {
		this.alphaRu = alphaRu;
	}

	public String getFirmaTicket() {
		return firmaTicket;
	}

	public void setFirmaTicket(String firmaTicket) {
		this.firmaTicket = firmaTicket;
	}
	public String getRe() {
		return re;
	}
	public void setRe(String re) {
		this.re = re;
	}
	public String getHashRe() {
		return hashRe;
	}
	public void setHashRe(String hashRe) {
		this.hashRe = hashRe;
	}
	public String getRu() {
		return ru;
	}
	public void setRu(String ru) {
		this.ru = ru;
	}
	public String getHashRu() {
		return hashRu;
	}
	public void setHashRu(String hashRu) {
		this.hashRu = hashRu;
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public Date gettValidez() {
		return tValidez;
	}
	public void settValidez(Date tValidez) {
		this.tValidez = tValidez;
	}
	public Date gettExpedicion() {
		return tExpedicion;
	}
	public void settExpedicion(Date tExpedicion) {
		this.tExpedicion = tExpedicion;
	}
}








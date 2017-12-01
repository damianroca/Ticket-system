package com.exampleservlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;


public class Ticket implements Serializable{
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
		
		resultado +="nombre: "+dp.getNombre();
		resultado +="\n";
		resultado +="email: "+dp.getEmail();
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
		if (confidencial) {
			resultado += "Re: " + p.getRe();
			resultado += "\n";
			resultado += "Ru: " + p.getRu();
			resultado += "\n";
			resultado += "firmaTicket: " + p.getFirmaTicket();
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
		}
		resultado +="hashRe: "+p.getHashRe();
		resultado +="\n";
		resultado +="hashRu: "+p.getHashRu();
		resultado +="\n";
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
		resultado +="tExpedicion: "+sdf.format(p.gettExpedicion());
		resultado +="\n";
		resultado +="tValidez: "+sdf.format(p.gettValidez());
		
		return resultado;	
	}
	
	public void serializarTicket(String name){
		
		File carpetaTickets = new File("C:/Documents and Settings/Damian/workspace/"+name+"_Tickets");//carpeta que contiene los tickets
		if (!carpetaTickets.exists()) {
			carpetaTickets.mkdir();
		}
		//En el caso de ya exista otro fichero con el mismo nombre lo sobreecribe
		File fticket=new File("C:/Documents and Settings/Damian/workspace/"+name+"_Tickets/Ticket_"+p.getNumSerie()+".tkt");//si el file no esta creado, lo creara al hacer el write
		//Se indica la ruta y el nombre de fichero que va a guardar, ademas de la extension
		try {
			FileOutputStream fos = new FileOutputStream(fticket);
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			oos.writeObject(this);
		} catch (Exception e) {
			e.printStackTrace();
			p.setRu(e.toString());
		}
		
	}
	
	public void enviarTicket(OutputStream os) {

		// Este metodo enviara el ticket con los datos de la peticion al
		// servlet, recogera el ticket generado por este y lo guardara 
		//Esto crea una etiqueta con el nombre que se le pasa 
		//STAX
		try {
			XMLOutputFactory xof = XMLOutputFactory.newInstance();
			XMLStreamWriter xtw =xof.createXMLStreamWriter(os);
			
			xtw.writeStartDocument();
			xtw.writeStartElement("ticket");
			
			xtw.writeStartElement("Datos_Personales");
			xtw.writeStartElement("Nombre");
			xtw.writeCharacters(dp.getNombre());
			xtw.writeEndElement();
			xtw.writeStartElement("Pelicula");
			xtw.writeCharacters(dp.getEmail());
			xtw.writeEndElement();
			xtw.writeEndElement();
			
			xtw.writeStartElement("Datos_Evento");
			xtw.writeStartElement("Cine");
			xtw.writeCharacters(de.getCine());
			xtw.writeEndElement();
			xtw.writeStartElement("Pelicula");
			xtw.writeCharacters(de.getPelicula());
			xtw.writeEndElement();
			xtw.writeStartElement("Hora");
			xtw.writeCharacters(de.getHora());
			xtw.writeEndElement();
			xtw.writeStartElement("Fecha");
			xtw.writeCharacters(de.getFecha());
			xtw.writeEndElement();
			xtw.writeEndElement();
			
			xtw.writeStartElement("Propiedades");
			xtw.writeStartElement("NumSerie");
			xtw.writeCharacters(p.getNumSerie());
			xtw.writeEndElement();
			xtw.writeStartElement("hashRu");
			xtw.writeCharacters(p.getHashRu());
			xtw.writeEndElement();
			xtw.writeStartElement("hashRe");
			xtw.writeCharacters(p.getHashRe());
			xtw.writeEndElement();
			xtw.writeStartElement("firmaTicket");
			xtw.writeCharacters(p.getFirmaTicket());
			xtw.writeEndElement();
			xtw.writeStartElement("Re");
			xtw.writeCharacters(p.getRe());
			xtw.writeEndElement();
			xtw.writeStartElement("Origen");
			xtw.writeCharacters(p.getOrigen());
			xtw.writeEndElement();
			xtw.writeStartElement("alphaRuRe");
			xtw.writeCharacters(p.getAlphaRuRe());
			xtw.writeEndElement();
			xtw.writeStartElement("Ap");
			xtw.writeCharacters(p.getAp());
			xtw.writeEndElement();
			xtw.writeStartElement("Au");
			xtw.writeCharacters(p.getAu());
			xtw.writeEndElement();
			xtw.writeStartElement("ticketUsado");
			xtw.writeCharacters(p.getTicketUsado().toString());
			xtw.writeEndElement();
			xtw.writeStartElement("Puerta");
			xtw.writeCharacters(p.getPuerta().toString());
			xtw.writeEndElement();
					
					
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			if(p.gettExpedicion()!=null){
			xtw.writeStartElement("tExpedicion");
			xtw.writeCharacters(sdf.format(p.gettExpedicion()));
			xtw.writeEndElement();
			}
			if(p.gettValidez()!=null){
			xtw.writeStartElement("tValidez");
			xtw.writeCharacters(sdf.format(p.gettValidez()));
			xtw.writeEndElement();
			}
			if(p.gettUso()!=null){
			xtw.writeStartElement("tUso");
			xtw.writeCharacters(sdf.format(p.gettUso()));
			xtw.writeEndElement();
			}
			xtw.writeEndElement();
			
			xtw.writeEndElement();
			xtw.writeEndDocument();
			xtw.flush();
			xtw.close(); 
					
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void decodificarC (InputStream is){
		try {
			XMLInputFactory xmlif = null;
			xmlif = XMLInputFactory.newInstance();
			xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
					Boolean.TRUE);
			xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
					Boolean.FALSE);
			xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.FALSE);
			XMLStreamReader xmlr = xmlif.createXMLStreamReader(is);
			int eventType ;
			boolean bticket = false;
			boolean bdatospersonales = false;
			boolean bdatosevento = false;
			boolean bpropiedades=false;
			boolean bnombre=false;
			boolean bpelicula=false;
			boolean bemail=false;
			boolean bcine=false;
			boolean bhora=false;
			boolean bfecha=false;
			boolean bhashru=false;
			boolean bhashre=false;
			boolean balpharu=false;
			boolean borigen=false;
			boolean busado=false;
			boolean brure=false;
			boolean bau=false;
			boolean bap=false;
			boolean btuso=false;
			boolean btvalidez=false;
			boolean btexpedicion=false;
			boolean bnumserie=false;
			
			while(xmlr.hasNext()){
				eventType = xmlr.next();
				switch (eventType){
				case XMLEvent.START_ELEMENT:
					if (xmlr.getName().toString() == "ticket"){	bticket=true;}
					if (xmlr.getName().toString() == "Datos_Personales"){bdatospersonales=true;}
					if (xmlr.getName().toString() == "Nombre"){	bnombre=true;}
					if (xmlr.getName().toString() == "Email"){	bemail=true;}
					if (xmlr.getName().toString() == "Datos_Evento"){bdatosevento=true;}
					if (xmlr.getName().toString() == "Cine"){bcine=true;}
					if (xmlr.getName().toString() == "Pelicula"){bpelicula=true;}
					if (xmlr.getName().toString() == "Hora"){bhora=true;}
					if (xmlr.getName().toString() == "Fecha"){bfecha=true;}
					if (xmlr.getName().toString() == "Propiedades"){bpropiedades=true;}
					if (xmlr.getName().toString() == "hashRu"){bhashru=true;}
					if (xmlr.getName().toString() == "alphaRu"){balpharu=true;}
					if (xmlr.getName().toString() == "Origen"){borigen=true;}
					if (xmlr.getName().toString() == "ticketUsado"){busado=true;}
					if (xmlr.getName().toString() == "alphaRuRe"){brure=true;}
					if (xmlr.getName().toString() == "tUso"){btuso=true;}
					if (xmlr.getName().toString() == "Ap"){bap=true;}
					if (xmlr.getName().toString() == "Au"){bau=true;} 
					if (xmlr.getName().toString() == "tExpedicion"){btexpedicion=true;} 
					if (xmlr.getName().toString() == "tValidez"){btvalidez=true;} 
					if (xmlr.getName().toString() == "NumSerie"){bnumserie=true;} 
					if (xmlr.getName().toString() == "hashRe"){bhashre=true;} 
					break;
				case XMLEvent.END_ELEMENT:
					if (xmlr.getName().toString() == "ticket"){	bticket=false;}
					if (xmlr.getName().toString() == "Datos_Personales"){bdatospersonales=false;}
					if (xmlr.getName().toString() == "Nombre"){	bnombre=false;}
					if (xmlr.getName().toString() == "Email"){	bemail=false;}
					if (xmlr.getName().toString() == "Datos_Evento"){bdatosevento=false;}
					if (xmlr.getName().toString() == "Cine"){bcine=false;}
					if (xmlr.getName().toString() == "Pelicula"){bpelicula=false;}
					if (xmlr.getName().toString() == "Hora"){bhora=false;}
					if (xmlr.getName().toString() == "Fecha"){bfecha=false;}
					if (xmlr.getName().toString() == "Fecha"){bfecha=false;}
					if (xmlr.getName().toString() == "Propiedades"){bpropiedades=false;}
					if (xmlr.getName().toString() == "hashRu"){bhashru=false;}
					if (xmlr.getName().toString() == "alphaRu"){balpharu=false;}
					if (xmlr.getName().toString() == "Origen"){borigen=false;}
					if (xmlr.getName().toString() == "ticketUsado"){busado=false;}
					if (xmlr.getName().toString() == "alphaRuRe"){brure=false;}
					if (xmlr.getName().toString() == "tUso"){btuso=false;}
					if (xmlr.getName().toString() == "Ap"){bap=false;}
					if (xmlr.getName().toString() == "Au"){bau=false;} 
					if (xmlr.getName().toString() == "tExpedicion"){btexpedicion=false;} 
					if (xmlr.getName().toString() == "tValidez"){btvalidez=false;} 
					if (xmlr.getName().toString() == "NumSerie"){bnumserie=false;} 
					if (xmlr.getName().toString() == "hashRe"){bhashre=false;} 
					break;
				case XMLEvent.CHARACTERS:
					if(bticket&bdatospersonales&bnombre){
						dp.setNombre(xmlr.getText());
					}
					if(bticket&bdatospersonales&bemail){
						dp.setEmail(xmlr.getText());
					}
					if(bticket&bdatosevento&bcine){
						de.setCine(xmlr.getText());
					}
					if(bticket&bdatosevento&bpelicula){
						de.setPelicula(xmlr.getText());
					}
					if(bticket&bdatosevento&bhora){
						de.setHora(xmlr.getText());
					}
					if(bticket&bdatosevento&bfecha){
						de.setFecha(xmlr.getText());
					}
					if(bticket&bpropiedades&bhashru){
						p.setHashRu(xmlr.getText());
					}
					if(bticket&bpropiedades&balpharu){
						p.setAlphaRu(xmlr.getText());
					}
					if(bticket&bpropiedades&borigen){
						p.setOrigen(xmlr.getText());
					}
					if(bticket&bpropiedades&bhashre){
						p.setHashRe(xmlr.getText());
					}
					if(bticket&bpropiedades&busado){
						p.setTicketUsado(new Boolean(xmlr.getText()));
					}
					if(bticket&bpropiedades&bau){
						p.setAu(xmlr.getText());
					}
					if(bticket&bpropiedades&bnumserie){
						p.setNumSerie(xmlr.getText());
					}
					if(bticket&bpropiedades&bap){
						p.setAp(xmlr.getText());
					}
					if(bticket&bpropiedades&brure){
						p.setAlphaRuRe(xmlr.getText());
					}
					if(bticket&bpropiedades&btuso){
						SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
						Date d=null;
						try {
							d = sdf.parse(xmlr.getText());
						} catch (ParseException e) {
							e.printStackTrace();
							d = null; 
						}
						p.settUso(d);
					}	
					if(bticket&bpropiedades&btvalidez){
						SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
						Date d=null;
						try {
							d = sdf.parse(xmlr.getText());
						} catch (ParseException e) {
							e.printStackTrace();
							d = null; 
						}
						p.settValidez(d);
					}	
					if(bticket&bpropiedades&btexpedicion){
						SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
						Date d=null;
						try {
							d = sdf.parse(xmlr.getText());
						} catch (ParseException e) {
							e.printStackTrace();
							d = null; 
						}
						p.settExpedicion(d);
					}	
					break;					
			}
			}
		} catch (XMLStreamException ex) {
			System.out.println(ex.getMessage());
			if (ex.getNestedException() != null)
				ex.getNestedException().printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
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
		cadena = cadena.replace('+', ' ');
		// Se adapatan las cadenas recogidas en el html para su correcta
		// interpretacion
		return cadena;
	}

	private String adaptaEmail(String cadena) {
		cadena = cadena.replaceFirst("%40", "@");
		// Se adapta el email para darle un formato correcto
		return cadena;
	}
}

class DatosEvento implements Serializable{
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
	
	private String adaptaHora(String cadena) {
		cadena = cadena.replaceFirst("%3A", ":");
		// Se adapta el email para darle un formato correcto
		return cadena;
	}

	private String adaptaEspacios(String cadena) {
		cadena = cadena.replace('+', ' ');
		// Se adapatan las cadenas recogidas en el html para su correcta
		// interpretacion
		return cadena;
	}
}
class Propiedades implements Serializable{
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


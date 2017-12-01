package com.exampleservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servicios
 */
public class Servicios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servicios() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Se ha conectado al servlet con get");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//System.out.println("Se ha conectado");
		String cine= request.getParameter("cine");
		String pelicula =request.getParameter("pelicula");
		String hora = request.getParameter("hora");
		String imagen= request.getParameter("imagen");
		String boton ="";

		if(cine.charAt(0)=='F'){
			boton="festival.html";
		}else {
			boton="ocimax.html";
		}

		Date date=new Date(); 
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy"); 
        String fecha = sdf.format(date);
        String dia=fecha.substring(0,2);
        String mes=fecha.substring(3,5);
        String año=fecha.substring(6,fecha.length());
		        
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//Pagina que se cargara con la confirmacion de lo que se ha escogido.
		//NOTA: las " de dentro del html se tienen que poner como \" , sino da un error
		
		out.println("<html><head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
		out.println("<meta http-equiv=\"cache-control\" content=\"max-age=200\" />");
		out.println("<link href=\"http://192.168.1.33:8080/ExampleServlet1/style.css\" media=\"handheld, screen\" rel=\"stylesheet\" type=\"text/css\" />");
		out.println("<title>Confirmaci&oacute;n</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"mainwrapper\">");
		out.println("<div id=\"content\">");
		out.println("<div class=\"min-width\">");
		out.println("<h2 align=\"center\">Confirmaci&oacute;n</h2>");
		out.println("<p align=\"center\"><img alt=\"\" src=\"" + imagen +"\" /></p><br />");
		out.println("<br />");
		out.println("<form name=\"formulario\" action=\"http://192.168.1.33:8080/ExampleServlet1/fantasma.html\" method=\"get\" >");
		out.println("<em>Nombre: </em><br /><br />");
		out.println("<input type=\"text\" id=\"textinput\" name=\"nombre\" size=\"25\"/><br /><br />");
		out.println("<em>Correo electr&oacute;nico:</em><br /><br />");
		out.println("<input type=\"text\" id=\"textinput\" name=\"correo\" size=\"25\"/><br /><br />");
		out.println("<input type=\"hidden\" name=\"cine\" value=\""+cine+"\">");
		out.println("<input type=\"hidden\" name=\"pelicula\" value=\""+pelicula+"\">");
		out.println("<input type=\"hidden\" name=\"hora\" value=\""+hora+"\">");
		out.println("<em>Cine: </em>"+cine);
		out.println("<br /><br />");
		out.println("<em>Pel&iacute;cula: </em>"+pelicula);
		out.println("<br /><br />");
		out.println("<em>Hora: </em>"+hora);
		out.println("<br /><br />");
		out.println("<em>Fecha: </em>");
		out.println("<br /><br />");
		out.println("<table border=\"0\"><tr>");
		out.println("<td height=\30%\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<em>D&iacute;a</em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
		out.println("<td height=\30%\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<em>Mes</em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
		out.println("<td height=\40%\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<em>A&ntilde;o</em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
		out.println("</tr><tr>");
		out.println("<td></td><td></td><td></td>");
		out.println("</tr><tr>");
		out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select size=\"1\" id=\"Dia\" name=\"dia\"><option selected=\"selected\" value=\""+dia+"\">"+dia+"</option><option value=\"01\">01</option><option value=\"02\">02</option><option value=\"03\">03</option><option value=\"04\">04</option><option value=\"05\">05</option><option value=\"06\">06</option><option value=\"07\">07</option><option value=\"08\">08</option><option value=\"09\">09</option><option value=\"10\">10</option><option value=\"11\">11</option><option value=\"12\">12</option><option value=\"13\">13</option><option value=\"14\">14</option><option value=\"15\">15</option><option value=\"16\">16</option><option value=\"17\">17</option><option value=\"18\">18</option><option value=\"19\">19</option><option value=\"20\">20</option><option value=\"21\">21</option><option value=\"22\">22</option><option value=\"23\">23</option><option value=\"24\">24</option><option value=\"25\">25</option><option value=\"26\">26</option><option value=\"27\">27</option><option value=\"28\">28</option><option value=\"29\">29</option><option value=\"30\">30</option><option value=\"31\">31</option></td>");
		out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select size=\"1\" id=\"Mes\" name=\"mes\"><option selected=\"selected\" value=\""+mes+"\">"+mes+"</option><option value=\"01\">01</option><option value=\"02\">02</option><option value=\"03\">03</option><option value=\"04\">04</option><option value=\"05\">05</option><option value=\"06\">06</option><option value=\"07\">07</option><option value=\"08\">08</option><option value=\"09\">09</option><option value=\"10\">10</option><option value=\"11\">11</option><option value=\"12\">12</option></td>");
		out.println("<td>&nbsp;&nbsp;&nbsp;&nbsp;<select size=\"1\" id=\"Año\" name=\"temporada\"><option selected=\"selected\" value=\""+año+"\">"+año+"</option><option value=\"2011\">2011</option></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<br /><br />");
		out.println("<ul id=\"navmenu\"><li>");
		out.println("<input type=\"submit\" name=\"nombre\" value=\"Comprar\">");
		out.println("</li></ul>");
		out.println("</form>");
		out.println("<br /><br />Opciones de navegaci&oacute;n:");
		out.println(" <br /><table border=\"0\" ><tr>");
		out.println("<td><form name=\"form1\" action=\"servicios.html\"><input type=\"submit\" name=\"nombre\" value=\"Servicios\"></form></td>");
		out.println("<td><form name=\"form2\" action=\"cines.html\"><input type=\"submit\" name=\"nombre\" value=\"Cines\"></form></td>");
		out.println("<td><form name=\"form3\" action=\""+boton+"\"><input type=\"submit\" name=\"nombre\" value=\"Atr&aacute;s\"></form></td>");
		out.println("</tr></table>");
		out.println("</div></div></div></body>");
		out.println("</html>");
				
	}

}


package proyecto.aplicativo;

import proyecto.herramientas.Encriptacion;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Proyecto extends Activity {
    /** Called when the activity is first created. */
	
	Ticket t= new Ticket();
	DatosPersonales datos= new DatosPersonales();
	DatosEvento evento= new DatosEvento();
	Propiedades prop = new Propiedades();
	
	@Override
	
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        final TextView tv = new TextView( this );
        final WebView wv = new WebView (this);
        
        t.setDp(datos);
        t.setDe(evento);
        t.setP(prop);
        
        wv.setWebViewClient(new WebViewClient() {   
             /* On Android 1.1 shouldOverrideUrlLoading() will be called every time the user clicks a link,  
              * but on Android 1.5 it will be called for every page load, even if it was caused by calling loadUrl()! */  
             @Override  
 			public boolean shouldOverrideUrlLoading(WebView view, String url) {
 				/* intercept all page load attempts */
 				String myAlternativeURL = "http://192.168.1.33:8080/ExampleServlet1/fantasma.html";
 				String comp = url.substring(0, url.indexOf("?"));
            	String parametros =url.substring(url.indexOf("?") + 1,url.length() - 1) ; 
            	            	
 				if (comp.equals(myAlternativeURL)) {

 					t.cargarTicket(parametros);
 					
 					String ru=Encriptacion.genR();
 					prop.setHashRu(Encriptacion.hashR(ru));
 					prop.setURL("http://192.168.1.33:8080/ExampleServlet1/HTMLPage");
 					prop.setOrigen("Aplicativo");
 					t.enviarTicket();
 					
 					prop.setRu(ru);

 					prop.setAlphaRu(Encriptacion.cAlphaRu(prop.getRu()));

 					boolean bfirma=Encriptacion.comprobarFirma(t.toString(false),prop.getFirmaTicket());
 					if(bfirma){
 						if(!prop.isMostrado()){
 							ProgressDialog.show(
 								Proyecto.this, "Se ha recibido un ticket",
 								"Se está guardando en el móvil", true);
 							prop.setMostrado(true);	
 						}
 						prop.setOrigen("Uso");
 						t.serializarTicket(Proyecto.this);
 						Intent i = new Intent(Proyecto.this, Aplicativo.class);
 	 			        startActivity(i);
 					}else{
 						String error="<html><head><h1>ERROR</h1></head></html>";
 						wv.loadData(error, "text/html", "utf-8");
 					}
 					 					
					return true;
 				}
				return false;
 			}
 		});
            try
         {
            wv.loadUrl("http://192.168.1.33:8080/ExampleServlet1/servicios.html");
         }
         catch( Exception e ){
        	 e.printStackTrace();
        	 tv.setText(e.getMessage());
        	 setContentView(tv);
        	 }
         setContentView( wv );  
     }  
 }
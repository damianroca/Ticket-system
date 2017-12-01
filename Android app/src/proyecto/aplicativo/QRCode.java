package proyecto.aplicativo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class QRCode extends Activity {
    /** Called when the activity is first created. */
	
	Intent iRecogido=null;
	Ticket t= new Ticket();
	ImageView imageView;
    Bitmap loadedImage;
    String imageHttpAddress ="http://chart.apis.google.com/chart?cht=qr&chs=350x350&chl=";
  
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.qr);
        imageView = (ImageView) findViewById(R.id.image_view); 
        iRecogido = this.getIntent();
        t= (Ticket)iRecogido.getSerializableExtra("ticket");

        URL imageUrl = null;
        try {
        	String datos="Cine: "+t.getDe().getCine();
        	datos += " Pelicula: " + t.getDe().getPelicula();
        	datos+=" Fecha: "+t.getDe().getFecha();
        	datos+=" Hora: "+t.getDe().getHora();
        	datos+=" NumSerie: "+t.getP().getNumSerie();
        	datos+=" tValidez: "+t.getP().gettValidez();
        	datos+=" tExpedicion: "+t.getP().gettExpedicion();
			datos += " alphaRu: " + t.getP().getAlphaRu();
        	datos=datos.replaceAll("@", "%40");
        	datos=datos.replaceAll(" ", "+");
        	datos=datos.replaceAll("=", "%3A");
        	datos=datos.replaceAll("\n","%0A");
        	datos=datos.replaceAll(":","%3A");
        	datos=datos.replaceAll("/", "%2F");
        	imageHttpAddress=imageHttpAddress.concat(datos);
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
            imageView.setBackgroundColor(0xFFFFFF);
            imageView.setImageBitmap(loadedImage);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
     }  
 }

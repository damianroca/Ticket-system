package proyecto.aplicativo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Mostrar extends Activity {
    /** Called when the activity is first created. */
	Intent iRecogido=null;
	Ticket t= new Ticket();
    
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        iRecogido = this.getIntent();
        
        final TextView tv = new TextView( this );
        t= (Ticket)iRecogido.getSerializableExtra("ticket");
        String datos=t.toString(false);
        datos+="\nRu: "+t.getP().getRu();
        datos+="\nRe: "+t.getP().getRe();
        datos+="\nFirma: "+t.getP().getFirmaTicket();
        tv.setText(datos);
        setContentView( tv );  
     }  
 }

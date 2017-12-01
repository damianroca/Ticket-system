package proyecto.aplicativo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public final class Uso extends Activity
{
	Intent recogido=null;
    private Button detalleButton;
    private Button qrButton;
    private Button usarButton;
    private Button atrasButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	recogido = this.getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uso);
        // Obtain handles to UI objects
        detalleButton = (Button) findViewById(R.id.detalle);
        qrButton = (Button) findViewById(R.id.qr);
        usarButton = (Button) findViewById(R.id.usar);
        atrasButton = (Button) findViewById(R.id.atras);

        // Register handler for UI elements
        detalleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchDetalleButton();
            }
        });
        
        qrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchQRButton();
            }
        });
        
        usarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchUsarButton();
            }
        });
     
        atrasButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchAtrasButton();
            }
        });
    }
     /**
     * Launches the ContactAdder activity to add a new contact to the selected accont.
     */
    protected void launchDetalleButton() {
        Intent i = new Intent(this, Mostrar.class);
        i.putExtra("ticket",recogido.getSerializableExtra("ticket"));
        startActivity(i);
    }
    
    protected void launchQRButton() {
        Intent i = new Intent(this, QRCode.class);
        i.putExtra("ticket",recogido.getSerializableExtra("ticket"));
        startActivity(i);
    }
    
    protected void launchUsarButton() {
    	//TextView mtexto= new TextView(this);
    	//poner un dialogo que diga que se esta preocesando su ticket o no?
        Ticket t = (Ticket) recogido.getSerializableExtra("ticket");
        boolean puerta=t.usarTicket(this);
        if(puerta){
        	//mtexto.setText("Disfrute de su peli\n\nPuerta abierta");
        	//la puerta esta abierta
        	setContentView(R.layout.abierto);
        }else{
        	//mtexto.setText("Su ticket no es correcto.\n\nNo puede entrar");
        	//la puerta esta cerrada
        	setContentView(R.layout.cerrado);
        }
        //setContentView(mtexto);
    }
    
    protected void launchAtrasButton() {
    	Uso.this.finish();
    }
}

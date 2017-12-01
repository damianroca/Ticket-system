package proyecto.aplicativo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public final class Aplicativo extends Activity
{

    private Button eventosButton;
    private Button ticketsButton;
    private Button salirButton;
    /**
     * Called when the activity is first created. Responsible for initializing the UI.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Obtain handles to UI objects
        eventosButton = (Button) findViewById(R.id.eventos);
        ticketsButton = (Button) findViewById(R.id.tickets);
        salirButton = (Button) findViewById(R.id.salir);

        // Register handler for UI elements
        eventosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchEventosButton();
            }
        });
        
        ticketsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchTicketsButton();
            }
        });
        
        salirButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchSalirButton();
            }
        });
     
    }

     /**
     * Launches the ContactAdder activity to add a new contact to the selected accont.
     */
    protected void launchEventosButton() {
        Intent i = new Intent(this, Proyecto.class);
        startActivity(i);
    }
    
    protected void launchTicketsButton() {
        Intent i = new Intent(this, Gestion.class);
        startActivity(i);
    }
    
    protected void launchSalirButton() {
    	TextView ayuda= new TextView(this);
    	String info="                            INFORMACIÓN"+"\n\n"+"Compra: \n"+"Usted solo tiene que ir a Eventos y escoger el tipo de evento que desee. Una vez dentro de la web seleccione el lugar donde se realiza, el evento que desea y la hora. Posteriormente, le saldrá una página de confirmación en la que elegirá la fecha(por defecto la del dia en cuestión) y comprará el servicio.Acto seguido recibirá el ticket en su terminal móvil.";
    	info+="\n\nUso: \nDentro del aplicativo seleccione el boton Tickets, y le saldrá una lista con los tickets que usted tiene disponible en su terminal. Cuando seleccione uno, irá a otro menú donde se le muetran la opciones disponibles a realizar, entre las que se encuentran ver el ticket, visulaizarlo como un código QR o enviarlo al cine mediante internet.Si seleccciona Usar Ticket, se enviará al cine y este le dirá si puede entrar o no";
    	info+="\n\n\nGracias por confiar en nuestro aplicativo para comprar sus tickets";
    	ayuda.setText(info);
    	setContentView(ayuda);
    }
}

package proyecto.aplicativo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Gestion extends ListActivity  {
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  String[] listaTickets=null;
	  
	  try {
			listaTickets = fileList();//devuelve los nombres de todos los ficheros privados que se han guardado

		} catch (Exception e) {
			e.printStackTrace();
		}
	  
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, listaTickets));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);
	  
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	      // When clicked, show a toast with the TextView text
	      //Toast.makeText(getApplicationContext(), ((TextView) view).getText(),Toast.LENGTH_SHORT).show();
	    	Intent i=new Intent(Gestion.this, Uso.class);
	    	
	    	Ticket ticket= new Ticket();
	    	ticket.deserializar(Gestion.this,((TextView) view).getText().toString());  	
	    	i.putExtra("ticket", ticket);
	    	startActivity(i);
	    }
	  });
	}
	
}

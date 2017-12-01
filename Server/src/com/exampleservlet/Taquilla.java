package com.exampleservlet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class Taquilla {
	
	private Hashtable <String,Boolean> bloc = new Hashtable <String,Boolean>();
	
	public Taquilla(){
		//el constructor inicializa el map con los numeros de serie que habra y el boolean para saber si se ha utilizado
		consigueBloc();
	}
	private void consigueBloc(){
		bloc.put("0000000001", false);
		bloc.put("0000000002", false);
		bloc.put("0000000003", false);
		bloc.put("0000000004", true);
		bloc.put("0000000005", false);
		bloc.put("0000000006", false);
		bloc.put("0000000007", false);
		bloc.put("0000000008", false);
		bloc.put("0000000009", false);
		
	}
	
	public String genNumeroSerie (){
		String numSerie=null;
		Set <String> s = bloc.keySet();
		Iterator <String> i = s.iterator();
		while(i.hasNext()){
			numSerie = i.next();
			if(!bloc.get(numSerie)){
				bloc.put(numSerie, true);
				return numSerie;
			}
		}
		return numSerie; 
	}
	
	public void sellaTicket(Ticket t){
		
		 SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		 Date d=null;
		 try {
			d = sdf.parse(t.getDe().getFecha()+" "+t.getDe().getHora());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		t.getP().settExpedicion(new Date());
		t.getP().settValidez(d);
	}
	
}


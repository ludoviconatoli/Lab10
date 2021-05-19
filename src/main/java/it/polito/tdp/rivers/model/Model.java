package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.model.Event.EventType;

public class Model {

	public List<River> fiumi;
	public RiversDAO dao;
	
	public Model() {
		fiumi = new ArrayList<>();
		dao = new RiversDAO();
	}
	
	private PriorityQueue<Event> queue;
	
	//Parametri statici
	double portata;
	double capacitaIniziale;
	double flussoUscitaMinimo;
	LocalDate primoGiorno;
	LocalDate ultimoGiorno;
	
	//Stato del sistema
	int contaGiorni;
	double sommaCapacita;
	
	//Uscita
	int giorniFalliti;
	double capacitaMedia;
	
	
	public int getGiorniFalliti() {
		return this.giorniFalliti ;
	}

	public double getCapacitaMedia() {
		return this.capacitaMedia ;
	}

	public void run(LocalDate inizio, LocalDate fine, double fattore, double mediaFlusso){
		
		this.queue = new PriorityQueue<>();
		portata = fattore*mediaFlusso*30;
		capacitaIniziale = portata/2;
		flussoUscitaMinimo = 0.8*mediaFlusso;
		
		contaGiorni = 1;
		capacitaMedia = capacitaIniziale/contaGiorni;
		sommaCapacita = capacitaIniziale;
		
		giorniFalliti=0;
		
		while(inizio.isBefore(fine)) {
			this.queue.add(new Event(inizio, EventType.RIEMPIMENTO));
			inizio = inizio.plusDays(1);
		}
		
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}
	
	private void processEvent(Event e) {
		switch(e.getType()) {
		case RIEMPIMENTO:
			this.capacitaIniziale = capacitaIniziale + Math.random()*50;
			if(capacitaIniziale > this.portata)
				capacitaIniziale = portata;
			
			this.queue.add(new Event(e.getDay(), EventType.PRELIEVO));
			sommaCapacita += capacitaIniziale;
			this.contaGiorni++;
			this.capacitaMedia = this.sommaCapacita/this.contaGiorni;
			
			break;
			
		case PRELIEVO:
			if(this.capacitaIniziale < this.flussoUscitaMinimo) {
				this.capacitaIniziale = 0;
				this.giorniFalliti++;
				
			}else {
				this.capacitaIniziale -= this.flussoUscitaMinimo;
			}
			break;
		}
	}
}

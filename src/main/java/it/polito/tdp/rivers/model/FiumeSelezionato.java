package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FiumeSelezionato {

	private LocalDate prima;
	private LocalDate fine;
	private int totMisurazioni;
	private double media;
	
	public FiumeSelezionato(LocalDate prima, LocalDate fine, int totMisurazioni, double media) {
		super();
		this.prima = prima;
		this.fine = fine;
		this.totMisurazioni = totMisurazioni;
		this.media = media;
	}

	public LocalDate getPrima() {
		return prima;
	}

	public void setPrima(LocalDate prima) {
		this.prima = prima;
	}

	public LocalDate getFine() {
		return fine;
	}

	public void setFine(LocalDate fine) {
		this.fine = fine;
	}

	public int getTotMisurazioni() {
		return totMisurazioni;
	}

	public void setTotMisurazioni(int totMisurazioni) {
		this.totMisurazioni = totMisurazioni;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}
	
}

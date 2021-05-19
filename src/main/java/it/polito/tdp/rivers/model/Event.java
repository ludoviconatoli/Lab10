package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event implements Comparable<Event>{

	public enum EventType{
		RIEMPIMENTO,
		PRELIEVO
	}
	
	private LocalDate day;
	private EventType type;
	
	public Event(LocalDate day, EventType type) {
		super();
		this.day = day;
		this.type = type;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Event o) {
		
		return this.getDay().compareTo(o.getDay());
	}
	
	
}

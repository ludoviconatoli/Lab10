package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	public List<River> fiumi;
	public RiversDAO dao = new RiversDAO();
	private PriorityQueue<Flow> queue;
	
	public Model() {
		
		fiumi = dao.getAllRivers();
		
		for(River r: fiumi) {
			dao.getFlows(r);
		}
	}
	
	public List<River> getRivers(){
		return fiumi;
	}
	
	public LocalDate getStartDate(River river) {
		if(!river.getFlows().isEmpty()) {
			return river.getFlows().get(0).getDay();
		}
		
		return null;
	}
	
	public LocalDate getEndDate(River river) {
		if(!river.getFlows().isEmpty()) {
			return river.getFlows().get(river.getFlows().size()-1).getDay();
		}
		
		return null;
	}
	
	public int getNumMeasurements(River river) {
		return river.getFlows().size();
	}
	
	public double getFMed(River river) {
		double avg = 0;
		
		for(Flow f: river.getFlows())
			avg += f.getFlow();
		
		avg /= river.getFlows().size();
		river.setFlowAvg(avg);
		
		return avg;
	}
	
	public SimulationResult simulate(River river, double k) {
		
		this.queue = new PriorityQueue<Flow>();
		this.queue.addAll(river.getFlows());
		
		List<Double> capacity = new ArrayList<Double>();
		double Q = k*30* convertM3SecToM3Day(river.getFlowAvg());
		double C = Q/2;
		double fOutMin = convertM3SecToM3Day(0.8 * river.getFlowAvg());
		int numberOfDays = 0;
		
		System.out.println("Q: " + Q);
		
		Flow flow;
		while((flow = this.queue.poll()) != null) {
			System.out.println("Date: " + flow.getDay());
			
			double fOut = fOutMin;
			
			if(Math.random() > 0.95) {
				fOut = 10*fOutMin;
				System.out.println("10xfOutMin");
			}
			
			System.out.println("fOut: " + fOut);
			System.out.println("fIn: " + convertM3SecToM3Day(flow.getFlow()));
			
			C += convertM3SecToM3Day(flow.getFlow());
			
			if(C>Q) {
				C = Q;
			}
			
			if(C < fOut) {
				numberOfDays++;
				C=0;
			}else {
				C -= fOut; 
			}
			
			System.out.println("C: " + C + "\n");
			capacity.add(C);
		}
		
		double CAvg = 0;
		for(Double d: capacity) {
			CAvg += d;
		}
		
		CAvg = CAvg/capacity.size();
		return new SimulationResult(CAvg, numberOfDays);
	}
	
	public double convertM3SecToM3Day(double input) {
		return input * 60 * 60 *24;
	}
	
	public double convertM3DayToM3Sec(double input) {
		return input / 60 / 60 / 24;
	}
	
}

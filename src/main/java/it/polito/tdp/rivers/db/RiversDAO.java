package it.polito.tdp.rivers.db;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public List<Flow> getFlows(River river){
		String sql="SELECT id, day, flow FROM flow WHERE river=?";
		
		List<Flow> flows = new LinkedList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			
			ResultSet res = st.executeQuery();
			while(res.next()) {
				LocalDate d1 = res.getDate("day").toLocalDate();
					
				flows.add(new Flow(d1, res.getDouble("flow"), river));
				
			}
			
			Collections.sort(flows);
			river.setFlows(flows);
			
			res.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			System.out.println("Errore in getFiume()");
		}
		return flows;
	}
	
}

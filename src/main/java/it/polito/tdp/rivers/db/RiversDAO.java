package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.FiumeSelezionato;
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
	
	public FiumeSelezionato getFiume(River r) {
		String sql="SELECT f.day, MAX(f.day) as m, count(*) AS c, AVG(f.flow) AS fl "
				+ "FROM flow f "
				+ "WHERE river = ? "
				+ "ORDER BY f.day";
		
		FiumeSelezionato f ;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			
			ResultSet res = st.executeQuery();
			if(res.next()) {
				LocalDate d1 = res.getDate("f.day").toLocalDate();
				LocalDate d2 = res.getDate("m").toLocalDate();	
				f = new FiumeSelezionato(d1, d2, res.getInt("c"), res.getDouble("fl"));
				res.close();
				st.close();
				conn.close();
				return f;
			}
			res.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			System.out.println("Errore in getFiume()");
		}
		return null;
	}
}

package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {



	/**Permette di ottenere i corsi presenti nel DB
	 * @return List<String> contenente i corsi
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				Corso c = new Corso(codins,numeroCrediti,nome,periodoDidattico);
				corsi.add(c);
			}
			conn.close();
			rs.close();
			st.close();
			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	
	
	/**
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		final String sql = "SELECT matricola FROM iscrizione WHERE codins=?";
		List<Studente> iscritti = new LinkedList<Studente>();
		StudenteDAO studente = new StudenteDAO();
		try {
			
			
			
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/iscritticorsi?user=root");
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,corso.getCodins());
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				int matricola = rs.getInt("matricola");
				iscritti.add(studente.getStudenteFromMatricola(matricola));
			}
			conn.close();
			rs.close();
			st.close();
			return iscritti;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Errore Db");
		}
	}

	/**
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		final String sql="INSERT INTO iscrizione(matricola,codins) VALUES(?,?)";
		final String sqlC="SELECT codins FROM iscrizione WHERE matricola=?";
		try {
			
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);		
			st.setInt(1, studente.getMatricola());		
			st.setString(2, corso.getCodins());		
			st.execute();

			PreparedStatement stC = conn.prepareStatement(sqlC);
			stC.setInt(1, studente.getMatricola());
			ResultSet rs= stC.executeQuery();
			while(rs.next()) {
				if(rs.getString("codins").equals(corso.getCodins())) {
					conn.close();
					rs.close();
					st.close();
					return true;
				}
			}
					
			conn.close();
			rs.close();
			st.close();
				
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
		
		return false;
	}
	
	public Corso getCorsofromCod(String cod){
		
		final String sql="SELECT * FROM corso WHERE codins=?";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st =conn.prepareStatement(sql);
			st.setString(1, cod);
			
			ResultSet rs=st.executeQuery();
			if(!rs.next())
				return null;
			String codins = rs.getString("codins");
			int numeroCrediti = rs.getInt("crediti");
			String nome = rs.getString("nome");
			int periodoDidattico = rs.getInt("pd");
			Corso c = new Corso(codins,numeroCrediti,nome,periodoDidattico);
			conn.close();
			rs.close();
			st.close();
			return c;
			
		}
		catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
		
	}

}

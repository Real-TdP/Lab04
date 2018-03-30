package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	
	/** restituisce lo studente sapendo la matricola
	 * @param matr matricola passata da utente
	 * @return studente associato
	 */
	public Studente getStudenteFromMatricola(int matr){
		
		final String sql="SELECT * FROM studente WHERE matricola=?";
		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st =conn.prepareStatement(sql);
			st.setInt(1, matr);	
			ResultSet rs=st.executeQuery();
			if(!rs.next())
				return null;
			int matricola=rs.getInt("matricola");
			String cognome=rs.getString("cognome");
			String nome=rs.getString("nome");
			String cds = rs.getString("CDS");
			Studente s = new Studente(matricola,cognome,nome,cds);
			conn.close();
			rs.close();
			st.close();
			return s;
			
		}
		catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	
	public List<Corso> getCorsi(int matr){
		final String sql="SELECT codins FROM iscrizione WHERE matricola=?";
		List<Corso> iscrizioni = new LinkedList<Corso>();
		CorsoDAO cors = new CorsoDAO();
		
		try {
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/iscritticorsi?user=root");
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, matr);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				String codC =rs.getString("codins");
				iscrizioni.add(cors.getCorsofromCod(codC));
			}
			conn.close();
			rs.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Errore Db");
		}
		
		return iscrizioni;
		
	}
	
}

package it.polito.tdp.lab04.model;

import java.util.*;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	CorsoDAO corso = new CorsoDAO();
	StudenteDAO studente= new StudenteDAO();
	private List<Corso> corsi = new LinkedList<Corso>();
	
	
	/**Permette di ottenere i nomi dei corsi presenti nel DB
	 * @return List nomiCorsi
	 */
	public List<String> getCodCorsi(){
		List<String> corsiS = new LinkedList<String>();
		
		corsi.addAll(corso.getTuttiICorsi());
		for(Corso c:corsi)
			corsiS.add(c.getCodins());
		return corsiS;
	}
	
	public String getNomeCorso(String cod) {
		for(Corso c:corsi) {
			if(c.getCodins().equals(cod))
				return c.getNome();
		}
			
		return "Corso senza nome";
	}
	
	/**consente di ottenere uno studente data matricola
	 * @param matricola
	 * @return studente
	 */
	public Studente getStudenteFromMatricola(int matricola) {
		return studente.getStudenteFromMatricola(matricola);
	}
	
	
	/** permette di ottenere gli studenti iscritti ad un determinato corso
	 * @param corso
	 * @return
	 */
	public List<Studente>  getIscritti(String corso){
		for (Corso c:corsi)
			if(c.getCodins().equals(corso)) 
				return this.corso.getStudentiIscrittiAlCorso(c);
					
		
		return null;
	}
	
	
	public List<Corso> getCorsiFromMatricola(int matricola){	
		return studente.getCorsi(matricola);
	}
	
	public boolean studenteIscritto(String codC,int matricola) {
		for(Corso c:corsi)
			if(c.getCodins().equals(codC))
				if(this.getCorsiFromMatricola(matricola).contains(c))
					return true;
		return false;
	}
	

	public boolean iscriviStudente(int matricola,String codins) {
		Studente s= this.getStudenteFromMatricola(matricola);
		for(Corso c:corsi)
			if(c.getCodins().equals(codins))
				return corso.inscriviStudenteACorso(s, c);
		
		return false;
	}
}

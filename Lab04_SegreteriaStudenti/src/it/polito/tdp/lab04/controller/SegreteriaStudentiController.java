package it.polito.tdp.lab04.controller;


import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class SegreteriaStudentiController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> choiceC;
    
    @FXML
    private TextField txtNomeCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextArea txtArea;

    @FXML
    void btnCercaCorsi(ActionEvent event) {
    	String corso=choiceC.getValue();
    	Matcher m = Pattern.compile("^[0-9]+$").matcher(txtMatricola.getText());
    	if(txtMatricola.getText().isEmpty()||!m.find()) {
    		txtArea.appendText("Errore, inserire una matricola\n");
    		return;
    	}
    	int matricola= Integer.parseInt(txtMatricola.getText());
    	if((model.getStudenteFromMatricola(matricola))==null) {
    		txtArea.appendText("Errore, Studente non presente nel DataBase\n");
    		return;
    	}
    		
    	if(corso.equals("Generico")) {
    		for(Corso c:model.getCorsiFromMatricola(matricola))
    			txtArea.appendText(c.toString());
    	}
    	else {
    		if(model.studenteIscritto(corso, matricola))
    			txtArea.appendText("Studente già iscritto.\n");
    		else
    			txtArea.appendText("Studente non sicritto.\n");
    	}
    }

    @FXML
    void btnComplete(ActionEvent event) {
    	
    	Matcher m = Pattern.compile("^[0-9]+$").matcher(txtMatricola.getText());
    	if(txtMatricola.getText().isEmpty()||!m.find()) {
    		txtArea.appendText("Errore, inserire una matricola\n");
    		return;
    	}
    	int matr=Integer.parseInt(txtMatricola.getText());
    	Studente s= model.getStudenteFromMatricola(matr);
    	if(s==null) {
    		txtArea.appendText("Errore, Studente non presente nel DataBase\n");
    		return;
    	}
    	txtNome.setText(s.getNome());
    	txtCognome.setText(s.getCognome());
    }

    @FXML
    void btnFindIscritti(ActionEvent event) {
    	String corso=choiceC.getValue();
    	if(corso.equals("Generico"))
    		txtArea.appendText("Errore, specificare un corso\n");
    	else {
    		List<Studente> iscritti=new LinkedList<Studente>(model.getIscritti(corso));
    		if(iscritti.isEmpty()) {
    			txtArea.appendText("Corso senza iscritti\n");
    			return;
    		}
        	for(Studente s:iscritti)
        		txtArea.appendText(s.toString());	
    	}
    }

    @FXML
    void btnIscrivi(ActionEvent event) {
    	Matcher m = Pattern.compile("^[0-9]+$").matcher(txtMatricola.getText());
    	if(txtMatricola.getText().isEmpty()||!m.find()) {
    		txtArea.appendText("Errore, inserire una matricola\n");
    		return;
    	}
    	int matr=Integer.parseInt(txtMatricola.getText());
    	String corso=choiceC.getValue();
    	if(model.iscriviStudente(matr, corso))
    		txtArea.appendText("Studente Iscritto con successo\n");
    	else
    		txtArea.appendText("Iscrizione fallita\n"); 
    	

    }

    @FXML
    void btnReset(ActionEvent event) {
    	txtArea.clear();
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	choiceC.setValue("Generico");
    }
    

   

    @FXML
    void initialize() {
        assert choiceC != null : "fx:id=\"choiceC\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNomeCorso != null : "fx:id=\"txtNomeCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	choiceC.getItems().addAll(model.getCodCorsi());
    	choiceC.getItems().add("Generico");
    	choiceC.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() 
    			{
					@Override
					public void changed(ObservableValue arg0, String arg1, String newC) {
						if(choiceC.getValue().equals("Generico"))
				    		txtNomeCorso.clear();
				    	else 
				    		txtNomeCorso.setText(model.getNomeCorso(newC));					
					}
    			}
    	);
    }
}

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Arco;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare ai branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField distanzaMinima;

    @FXML
    private Button btnAnalizza;

    @FXML
    private ComboBox<Airport> cmbBoxAeroportoPartenza;

    @FXML
    private Button btnAeroportiConnessi;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    private Button btnCercaItinerario;

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {

    	int distance ;
    	try {
    	distance = Integer.parseInt(distanzaMinima.getText());
    	this.model.creaGrafo(distance);
    	this.txtResult.appendText("GRAFO CREATO !! \n");
    	this.txtResult.appendText("numero VERTICI "+this.model.numeroVertici()+"\n");
    	this.txtResult.appendText("numero ARCHI "+this.model.numeroArchi()+"\n");
    	this.cmbBoxAeroportoPartenza.getItems().addAll(this.model.getVertici());
    	}
    	catch (NumberFormatException e) {
    		this.txtResult.appendText("devi inserire un numero");
    	}
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {

    	Airport a = this.cmbBoxAeroportoPartenza.getValue();
    	this.txtResult.clear();
    	for (Arco ar : this.model.getAdicenti(a))
    	    this.txtResult.appendText(ar.getA1().toString()+" "+ar.getPeso()+"\n");
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	this.txtResult.clear();
    	int miglia;
    	Airport a = this.cmbBoxAeroportoPartenza.getValue();
    	try {
    	 miglia = Integer.parseInt(numeroVoliTxtInput.getText());
    	for (Airport air : this.model.getPercorsoMigliore(miglia, a))
    	     this.txtResult.appendText(air.toString()+"\n");
    	
    	this.txtResult.appendText("DISTANZA "+this.model.distanzaRaggiunte());
    	     
    	}
    	catch (NumberFormatException e) {
    		this.txtResult.appendText("devi inserire un numero");
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}

/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;

import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Itinerario;
import it.polito.tdp.meteo.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	
    	int mese=this.boxMese.getValue();
    	
    	Itinerario bestItinerario=model.trovaSequenza(mese);
		this.txtResult.setText(bestItinerario.getCosto()+"\n");
		for(Citta c: bestItinerario.getTragitto())		
			this.txtResult.appendText(c.toString()+"\n");

    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {

    	int mese=this.boxMese.getValue();
    	
    	double[] esito=model.getUmiditaMedia(mese);
    	this.txtResult.setText("Torino: "+esito[0]+"\n");
    	this.txtResult.appendText("Genova: "+esito[1]+"\n");
    	this.txtResult.appendText("Milano: "+esito[2]+"\n");
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        ObservableList<Integer> list=FXCollections.observableArrayList();
    	for(int i=1; i<13; i++)
        	list.add(i);
        
        this.boxMese.setItems(list);
        this.boxMese.setValue(1);
    }
    
    public void setModel(Model m) {
    	this.model=m;
    }
}


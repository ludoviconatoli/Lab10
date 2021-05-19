/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.model.FiumeSelezionato;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private RiversDAO dao;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	private double media;
	

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doInsericsci(ActionEvent event) {
    	
    	River fiume = this.boxRiver.getValue();
    	
    	FiumeSelezionato fs = dao.getFiume(fiume);
    	double d = fs.getMedia()*3600*24;
    	DecimalFormat df = new DecimalFormat("#.##");
    	
    	this.dataInizio = fs.getPrima();
    	this.dataFine = fs.getFine();
    	this.media = fs.getMedia();
    	
    	this.txtStartDate.setText(fs.getPrima().toString());
    	this.txtEndDate.setText(fs.getFine().toString());
    	this.txtNumMeasurements.setText(fs.getTotMisurazioni()+"");
    	this.txtFMed.setText(df.format(d));
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	River r = this.boxRiver.getValue();
    	String s = this.txtK.getText();
    	if(s == null) {
    		this.txtResult.setText("Devi inserire un numero");
    	}
    	
    	double k;
    	try {
    		k = Double.parseDouble(s);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire un numero");
    		return;
    	}
    	
    	if(r == null ) {
    		this.txtResult.setText("Devi selezionare un fiume");
    		return;
    	}
    	
    	this.model.run(this.dataInizio, this.dataFine, k, this.media);
    	this.txtResult.appendText("Considerando il fiume " + this.boxRiver.getValue().toString() +"\n\n");
    	this.txtResult.appendText("I giorni in cui non si è riusciti a soddisfare la richiesta sono: " + this.model.getGiorniFalliti() +"\n");
    	this.txtResult.appendText("La capacità media del fiume è stata: " + this.model.getCapacitaMedia());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	dao = new RiversDAO();
    	this.boxRiver.getItems().addAll(dao.getAllRivers());
    	
    }
}

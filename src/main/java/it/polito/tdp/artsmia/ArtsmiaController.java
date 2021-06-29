package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi");
    	
    	for(Adiacenza a : this.model.artistiConnessi(this.boxRuolo.getValue())) {
    		this.txtResult.appendText("\n"+a.getA1()+" - "+a.getA2()+" - "+a.getPeso());
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso");
    	
    	int id = Integer.parseInt(this.txtArtista.getText());
    	
    	for(Artist a : this.model.cercaPercorso(id)) {
    		this.txtResult.appendText("\n"+a);
    	}
    	
    	this.txtResult.appendText("\nNumero di esposizioni: "+model.getPesoMax());
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo");
    	
    	if(this.boxRuolo.getValue()!=null) {
    		this.model.creaGrafo(this.boxRuolo.getValue());
    	}
    	
    	int vertici = this.model.getGrafo().vertexSet().size();
    	int archi = this.model.getGrafo().edgeSet().size();
    	
    	txtResult.setText("Grafo creato:\n#Vertici: "+vertici+"\n#Archi: "+archi);
    }

    public void setModel(Model model) {
    	this.model = model;
    	
    	this.boxRuolo.getItems().addAll(this.model.getRoles());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}

package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumLettere;

    @FXML
    private TextField txtParola;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	int numLettere = Integer.parseInt(txtNumLettere.getText());
    	this.model.createGraph(numLettere);
    	txtResult.appendText(String.format("Grafo creato: %d vertici e %d archi\n", 
    			model.getGraph().vertexSet().size(), model.getGraph().edgeSet().size()));
    	
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtNumLettere.clear();
    	txtParola.clear();
    	txtResult.clear();
    }

    @FXML
    void doTrovaGradoMax(ActionEvent event) {
    	txtResult.appendText(this.model.findMaxDegree());
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	String parola = txtParola.getText();
    	try {
    		if(parola.length()==0 || parola==null) {
    			txtResult.appendText("parola inserita non valida");
    			return;
    		}
    		txtResult.appendText(String.format("Neighbours di %s:\n", parola));
    		txtResult.appendText(this.model.displayNeighbours(parola).toString()+"\n");
    	}catch(RuntimeException re) {
    		txtResult.appendText(re.getMessage());
    	}
    }

    @FXML
    void initialize() {
        assert txtNumLettere != null : "fx:id=\"txtNumLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

        
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

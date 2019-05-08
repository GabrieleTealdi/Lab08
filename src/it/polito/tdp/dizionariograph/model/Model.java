package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private Graph<String, DefaultEdge> graph;
	private int numLettere = 0;
	private List<String> parole;

	public void createGraph(int numeroLettere) {

		graph = new SimpleGraph<>(DefaultEdge.class);
		WordDAO dao = new WordDAO();
		this.numLettere = numeroLettere;
		this.parole = dao.getAllWordsFixedLength(numeroLettere);
		
		Graphs.addAllVertices(graph, this.parole);
		
		
		for(String parola: parole) {
			List<String> paroleSimili = trovaSimili(parola);
			for(String ps: paroleSimili)
				graph.addEdge(parola, ps);
		}
		
		//System.out.println(graph);
		
	}


	public List<String> displayNeighbours(String parolaInserita) {

		if(!parole.contains(parolaInserita))
			throw new RuntimeException("parola non esistente nel vocabolario");
		if(parolaInserita.length()!=numLettere)
			throw new RuntimeException("la parola non coincide con il numero di lettere inserite");
		
		return Graphs.neighborListOf(graph, parolaInserita);
	}

	public String findMaxDegree() {
		
		String record = null;
		int gradoRecord = 0;
		
		for(String parola: graph.vertexSet()) {
			int grado = Graphs.neighborListOf(graph, parola).size();
			if(grado>gradoRecord) {
				record = parola;
				gradoRecord = Graphs.neighborListOf(graph, parola).size();
			}
		}
		
		if(gradoRecord!=0) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Massimo grado: %d\nVertice con Massimo grado: %s\n", gradoRecord, record));
			sb.append(String.format("Neighbours di %s: \n", record));
			for(String s: Graphs.neighborListOf(graph, record)) {
				sb.append(s +"\n");
			}
			return sb.toString();
		}
		
		return null;
	}
	

	private List<String> trovaSimili(String parola) {
		List<String> simili = new ArrayList<>();
		for(String s: parole) {
			int diff = 1;
			for(int i=0; i<numLettere; i++) {
				if(s.charAt(i)!=parola.charAt(i))
					diff--;
			}
			if(diff==0)
				simili.add(s);	
		}
		return simili;
	}


	public Graph<String, DefaultEdge> getGraph() {
		return graph;
	}
	
}

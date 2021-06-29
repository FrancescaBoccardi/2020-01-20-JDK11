package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;



public class Model {
	
	private Graph<Artist,DefaultWeightedEdge> grafo;
	private Map<Integer,Artist> idMap;
	private ArtsmiaDAO dao;
	private List<Artist> risultato;
	private double pesoMax;
	private double pesoCorrente;
	private int maxPercorso;

	
	public Model() {
		this.dao = new ArtsmiaDAO();
		
	}
	
	public void creaGrafo(String role) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap = new HashMap<Integer,Artist>();
		
		// aggiungo i vertici
		
		this.dao.getVertici(idMap, role);
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		// aggiungo gli archi
		
		for(Adiacenza a : this.dao.getArchi(idMap,role)) {
			Graphs.addEdge(grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		
	}
	
	public List<Adiacenza> artistiConnessi(String role){
		List<Adiacenza> result = this.dao.getArchi(idMap, role);
		Collections.sort(result);
		return result;
	}
	
	public List<Artist> cercaPercorso(int idPartenza){
		Artist partenza = idMap.get(idPartenza);
		List<Artist> parziale = new ArrayList<>();
		this.pesoMax=0;
		this.maxPercorso=0;
		ricorsione(parziale,0,partenza);
		return risultato;
	}
	
	private void ricorsione(List<Artist> parziale, int livello, Artist partenza) {
		
		
		if(parziale.size()>maxPercorso) {
			maxPercorso = parziale.size();
			risultato = new ArrayList<>(parziale);
			pesoMax = pesoCorrente;
		}
		
		for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(partenza)) {
			
			if(livello==0) {
				pesoCorrente = grafo.getEdgeWeight(e);
			}
			
			if(grafo.getEdgeWeight(e)==pesoCorrente) {
				Artist next = Graphs.getOppositeVertex(grafo, e, partenza);
				if(!parziale.contains(next)) {
					parziale.add(next);
					this.ricorsione(parziale, livello+1, next);
					parziale.remove(next);
				}
			}
		}
		
	}
	
	public List<String> getRoles(){
		return this.dao.getRoles();
	}

	public Graph<Artist, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public double getPesoMax() {
		return pesoMax;
	}
	
	
	
}

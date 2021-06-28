package it.polito.tdp.extflightdelays.model;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
public class Model {

	private ExtFlightDelaysDAO dao ;
	private Graph <Airport, DefaultWeightedEdge> grafo;
	private List<Arco> archi;
	private Map<Integer, Airport> idMap;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
	}
	
	public void creaGrafo(int distance) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		dao.loadAllAirports(idMap);
		archi = new ArrayList<>(dao.getArchi(idMap, distance));
		
		for (Arco a : archi) {
			DefaultWeightedEdge e = this.grafo.getEdge(a.getA1(), a.getA2());
			if (e!=null) {
			double peso = this.grafo.getEdgeWeight(e);
			this.grafo.setEdgeWeight(e,(a.getPeso()+peso)/2);
			}
			else {
			this.grafo.addVertex(a.getA1());
			this.grafo.addVertex(a.getA2());
			Graphs.addEdge(this.grafo, a.getA1(), a.getA2(), a.getPeso());
			}
			
		}
		}
	
	public int numeroVertici() {
		return this.grafo.vertexSet().size();
	}
	public int numeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Airport> getVertici(){
		List<Airport> vertici= new ArrayList<>();
		for (Airport a : this.grafo.vertexSet())
			vertici.add(a);
		return vertici;
	}
	
	public List<Arco> getAdicenti(Airport a){
		List<Airport> adiacenti = Graphs.neighborListOf(this.grafo, a);
		List<Arco> arc = new ArrayList<>();
		for (Airport x : adiacenti)
			{
			DefaultWeightedEdge e = this.grafo.getEdge(x, a);
			double peso = this.grafo.getEdgeWeight(e);
			arc.add(new Arco(x, a, peso));
			}
		Collections.sort(arc, new Comparator<Arco>() {

			@Override
			public int compare(Arco a1, Arco a2) {
				// TODO Auto-generated method stub
				return -Double.compare(a1.getPeso(), a2.getPeso());
			}

			
			
		});
		return arc;
	}
	private List<Airport> parziale;
	private List<Airport> soluzione;
	private double migliaSoluzione;
	public List<Airport> getPercorsoMigliore(int miglia, Airport a){
		parziale = new ArrayList<>();
		soluzione = new ArrayList<>();
		parziale.add(a);
		cerca(parziale, 0, miglia);
		return soluzione;
	}

	private void cerca(List<Airport> parziale, int livello, int miglia) {
		
		double distanza = calcolaMiglia(parziale);
		// condizioni di terminazione
		if (distanza > miglia) 
			return;
		if (parziale.size()>soluzione.size()) {
			soluzione = new ArrayList<>(parziale);
			migliaSoluzione = distanza;
		}
		
		List<Airport> adiacenti = Graphs.neighborListOf(this.grafo, parziale.get(livello));
		for (Airport a : adiacenti)
		{
			if(!parziale.contains(a)) {
				parziale.add(a);
				cerca(parziale, livello+1, miglia);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	private double calcolaMiglia(List<Airport> parziale) {
		double distanza = 0.0;
		for (int i=1; i<parziale.size(); i++) {
			DefaultWeightedEdge e = this.grafo.getEdge(parziale.get(i-1), parziale.get(i));
			double peso = this.grafo.getEdgeWeight(e);
			distanza += peso;
		}
		return distanza;
	}
	
	public double distanzaRaggiunte() {
		return migliaSoluzione;
	}
}

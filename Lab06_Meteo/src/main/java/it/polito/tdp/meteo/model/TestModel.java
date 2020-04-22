package it.polito.tdp.meteo.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		//System.out.println(m.getUmiditaMedia(12));
		
		Itinerario bestItinerario=m.trovaSequenza(5);
		System.out.println(bestItinerario.getCosto());
		for(Citta c: bestItinerario.getTragitto())		
			System.out.println(c.toString());
		

	}

}

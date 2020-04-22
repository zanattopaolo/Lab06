package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private int bestCost;
	private Itinerario bestItinerario;
	private MeteoDAO mDao;
	private List<Citta> listCitta;

	public Model() {
		mDao=new MeteoDAO();
		
	}

	// of course you can change the String output with what you think works best
	public double[] getUmiditaMedia(int mese) {
		List<Rilevamento> listTo=mDao.getAllRilevamentiLocalitaMese(mese, "Torino");
		Double dTo=0.0;
		for(Rilevamento r:listTo)
			dTo+=r.getUmidita();
		
		List<Rilevamento> listGe=mDao.getAllRilevamentiLocalitaMese(mese, "Genova");
		Double dGe=0.0;
		for(Rilevamento r:listGe)
			dGe+=r.getUmidita();
		
		List<Rilevamento> listMi=mDao.getAllRilevamentiLocalitaMese(mese, "Milano");
		Double dMi=0.0;
		for(Rilevamento r:listMi)
			dMi+=r.getUmidita();
		
		double[] esito=new double[3];
		esito[0]=dTo/listTo.size();
		esito[1]=dGe/listGe.size();
		esito[2]=dMi/listMi.size();
		
		//return "Torino: "+dTo/listTo.size()+"%\nGenova: "+dGe/listGe.size()+"%\nMilano: "+dMi/listMi.size()+"%";
		return esito;
	}
	
	// of course you can change the String output with what you think works best
	public Itinerario trovaSequenza(int mese) {
		
		this.bestCost=0;
		this.bestItinerario=new Itinerario(Model.COST);
		listCitta=new ArrayList<Citta>();
		this.listCitta.add(new Citta("Torino", this.mDao.getFirst15RilevamentiLocalitaMese(mese, "Torino")));
		this.listCitta.add(new Citta("Genova", this.mDao.getFirst15RilevamentiLocalitaMese(mese, "Genova")));
		this.listCitta.add(new Citta("Milano", this.mDao.getFirst15RilevamentiLocalitaMese(mese, "Milano")));
		
		/*this.listCitta.add(new Citta("Torino", mDao.getAllRilevamentiLocalitaMese(mese, "Torino")));
		this.listCitta.add(new Citta("Genova", mDao.getAllRilevamentiLocalitaMese(mese, "Genova")));
		this.listCitta.add(new Citta("Milano", mDao.getAllRilevamentiLocalitaMese(mese, "Milano")));*/
		
		Itinerario parziale=new Itinerario(Model.COST);
		
		this.cercaCombinazione(parziale, 0);
		
		return this.bestItinerario;
	}
	
	/*
	 * 1. Il livello corrisponde al numero di giorni totali dell'itinerario
	 * 2. La soluzione parziale è composta da una combinazione di città che a loro volta contengono i giorni trascorsi al suo interno
	 * 3. Per riconoscere una soluzione parziale da una completa basta contare i giorni dell'itinerario
	 * 4. Per valutare se è una soluzione valida devo verificare che il costo totale sia minore di quello migliore trovato fino a quel momento
	 * 5. ""
	 * 6. Per generare una soluzione i+1, dopo aver messo una serie di controlli, si tratta di aggiungere una città all'itinerario
	 * 7. La struttura dati per memorizzare la soluzione è un'ArrayList di Citta
	 * 8. ??
	 * 
	 */
	
	
	
	/**
	 * Trova la combinazione delle città per minimizzare i costi
	 * @param parziale: combinazione parziale trovata
	 * @param livello: numero di giorni della durata delle combinazione
	 */
	private void cercaCombinazione(Itinerario parziale, int livello) {
		
		//condizione di terminazione
		if(livello == Model.NUMERO_GIORNI_TOTALI) {
			int cost = parziale.getCosto();
			if(this.bestCost==0 || cost<this.bestCost) {
				this.bestItinerario=new Itinerario(parziale);
				this.bestCost=cost;
				
				/*System.out.println(bestItinerario.getCosto());
				for(Citta c: bestItinerario.getTragitto())		
					System.out.println(c.toString());*/
				
				return;
			}
		}
		
		
		for(Citta c:this.listCitta) {
		//filtri sulla nuova generazione
		if(!(parziale.ricorrenzeCitta(c)>(Model.NUMERO_GIORNI_CITTA_MAX-1)) && parziale.canAdd(c, Model.NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) && (this.bestCost==0 || parziale.getCosto()<this.bestCost)) {
			//return;
		//generazione nuova soluzione
		parziale.addCitta(c);
		
		//filtri sulla nuova generazione
		//if(!(parziale.ricorrenzeCitta(c)>6 || !parziale.canAdd(c)))
		//	return;
			
		//filtri sulla ricorsione
		//if(this.bestCost!=0 && parziale.getCosto()>this.bestCost)
		//	return;
		
		//chiamata ricorsiva
		this.cercaCombinazione(parziale, parziale.getLunghezza());
		
		//backtracking
		parziale.removeLast();
		/*if(parziale.getLunghezza()>0)
			parziale.removeLast();*/
		}
		}
		
	}
	

}

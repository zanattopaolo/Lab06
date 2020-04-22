package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

public class Itinerario {
	
	private List<Citta> tragitto;
	private int costoTraferimento;
	
	public Itinerario(int n) {
		this.tragitto=new ArrayList<Citta>();
		this.costoTraferimento=n;
	}
	
	public Itinerario(Itinerario i) {
		this.tragitto=new ArrayList<Citta>();
		for(Citta c:i.getTragitto())
			this.tragitto.add(c);
	}
	
	public int getLunghezza() {
		return this.tragitto.size();
	}
	
	public int getCosto() {
		int cost=0;
		if(this.tragitto.size()!=0) {
		Citta x = tragitto.get(0);
		cost+=tragitto.get(0).getRilevamenti().get(0).getUmidita();
		for(int i=1; i<tragitto.size(); i++) {
			if(!tragitto.get(i).equals(x)) {
				cost+=100;
				x=tragitto.get(i);
			}
			
			cost+=tragitto.get(i).getRilevamenti().get(i).getUmidita();
		}
		}
		return cost;
	}
	
	public int ricorrenzeCitta(Citta c) {
		int k=0;
		if(this.tragitto.contains(c)) {
			for(Citta c1:this.tragitto)
				if(c1.equals(c))
					k++;
			return k;
		}
		else
			return 0;
	}

	public List<Citta> getTragitto() {
		return tragitto;
	}
	
	public void addCitta(Citta c) {
		tragitto.add(c);
	}
	
	public void removeLast() {
		tragitto.remove(tragitto.size()-1);
	}
	
	public boolean canAdd(Citta c, int n) {
		boolean ret=false;
		List<Citta> list=new ArrayList<Citta>();
		
		/*switch(this.tragitto.size()) {
		case 0:
			ret = true;
		break;
		case 1:
			Citta last=this.tragitto.get(tragitto.size()-1);
			if(last.equals(c))
				ret=true;
			else
				ret=false;
		break;
		case 2:
			last=this.tragitto.get(tragitto.size()-1);
			if(last.equals(c))
				ret=true;
			else
				ret=false;
		break;
		default:
			last=this.tragitto.get(tragitto.size()-1);
			Citta secondToLast=this.tragitto.get(tragitto.size()-2);
			Citta thirdToLast=this.tragitto.get(tragitto.size()-3);
			//if(last.equals(c) || (last.equals(c) && secondToLast.equals(c)) || (last.equals(secondToLast) && last.equals(thirdToLast)))
			if(last.equals(c) || (last.equals(secondToLast) && last.equals(thirdToLast)))
				ret = true;
			else
				ret = false;
		break;
		
		}
		return ret;*/
		
		if(this.tragitto.size()==0)
			return true;
		
		Citta last=this.tragitto.get(tragitto.size()-1);
		if(this.tragitto.size()>=n) {
			ret=true;
			for(int i=this.tragitto.size()-1; (i>this.tragitto.size()-n && ret==true); i--) {
				if(!this.tragitto.get(i).equals(this.tragitto.get(i-1)))
					ret=false;
			}
		}
		
		
		return (last.equals(c) || ret);
	}
	
	

	
	
}

package it.polito.tdp.extflightdelays.model;

public class Arco {

	Airport a1;
	Airport a2;
	double peso;
	public Arco(Airport a1, Airport a2, double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Airport getA1() {
		return a1;
	}
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	public Airport getA2() {
		return a2;
	}
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  a1+" "+a2+"peso= "+peso ;
	}
	
	
	
}

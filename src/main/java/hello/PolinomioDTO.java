/**
 * 
 */
package hello;

import java.io.Serializable;

/**
 * @author japf0
 *
 */
public class PolinomioDTO implements Serializable{
	
	int [] p;
	
	
	public PolinomioDTO (int [] coeficientes) {
		
		if (coeficientes == null) {
			p = new int[0];
		}
		
		p = new int [coeficientes.length];
		for (int i = 0; i < p.length; i++) {
			p[i] = coeficientes [i];
		}
	}
	
	public double getValor(double x){			
		double sum = 0;
		double monomio = 1;
		for (int i = 1; i < p.length; i++){
			if (p[i] != 0){
				for (int j = 1; j <= i; j++){
					monomio *= x;
				}
				monomio *= p[i];
				sum += monomio;
				monomio = 1;
			}
		}
		return p[0] + sum;
	}
	
	public PolinomioDTO sumar (PolinomioDTO pol){

		int [] aa = pol.p;
		int [] bb = p;
		
		int [] result = new int[aa.length > bb.length?aa.length:bb.length ];
		
		if (aa.length <= bb.length){
			for (int i = 0; i < aa.length; i++){
				result[i] = aa[i] + bb[i];
			}
			for (int i = aa.length; i < result.length; i++){
				result[i] = bb[i];
			}
		}
		else{
			for (int i = 0; i < bb.length; i++){
				result[i] = aa[i] + bb[i];
			}
			for (int i = bb.length; i < result.length; i++){
				result[i] = aa[i];
			}
		}
		return new PolinomioDTO(result);
	}

	public PolinomioDTO multiplicar (PolinomioDTO pol){
		
	   int grado = (p.length-1) + (pol.p.length-1);
	   int [] result = new int[grado+1];
	   for ( int i = 0; i <= (p.length - 1); ++i )
		  for ( int j = 0; j <= (pol.p.length - 1); ++j )
			result[i + j] += getCoeficiente(i) * pol.getCoeficiente(j);
			
		return new PolinomioDTO(result);
	}
	
	public int [] getCoeficientes() {
		return p;
	}

	public int getCoeficiente(int n) {
		return p[n];
	}


	public void imprimir()
	{
	   for ( int i = (p.length - 1); i >= 0; --i )
	   {
		  if ( getCoeficientes()[i] != 0 )
		  {
			 if ( i != (p.length - 1) ) System.out.print(" + ");
			 if ( getCoeficientes()[i] != 1 || i == 0 ) System.out.print(getCoeficientes()[i]);
			 if ( i > 0 ) System.out.print("x");
			 if ( i > 1 ) System.out.print("^" + i);
		  }
	   }
	}
	
	

	/**
	 * @param args
	 */
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PolinomioDTO pol1 = new PolinomioDTO(new int [] {1,1});
		PolinomioDTO pol2 = new PolinomioDTO(new int [] {1,1,1,1});
		PolinomioDTO pol3 = pol1.multiplicar(pol2);
		pol3.imprimir();
	}
	*/
}
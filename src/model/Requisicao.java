package model;

/**
 * T1 - T�cnicas de Programa��o
 * 
 * @author Andrei
 * @author Rodrigo Scorsatto
 * @author Alexandre Baptista
 * @version 25/09/09
 */

public class Requisicao {

	private boolean cheque=false;
/**
 * 
 * @param cliente 
 * @param cheque - Por default � false ou seja, n�o � opera��o com cheque
 */
	Requisicao(boolean cheque) {
		this.cheque = cheque;
	}

	public boolean isCheque() {
		return cheque;
	}
}

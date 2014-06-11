package views;
import core.simulations.SimuladorAgencia;

/**
 * T1 - T�cnicas de Programa��o
 * 
 * @author Andrei
 * @author Rodrigo Scorsatto
 * @author Alexandre Baptista
 * @version 25/09/09
 */
public class Interface {
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		SimuladorAgencia simulador = new SimuladorAgencia();
		System.out.println("Iniciando...");
		simulador.executarSimulacao();
		System.out.println("Terminou!");
	}
}

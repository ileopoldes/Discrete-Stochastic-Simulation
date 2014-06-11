package views;
import core.simulations.SimuladorLoja;

public class Interface {
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		SimuladorLoja simulador = new SimuladorLoja();
		System.out.println("Iniciando...");
		simulador.executarSimulacao();
		System.out.println("Terminou!");
	}
}

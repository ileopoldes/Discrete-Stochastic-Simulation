package core.simulations;


import java.util.Random;

import core.util.LoaderPropriedades;
import core.util.StatisticsCollector;
import model.Agencia;
import model.Caixa;
import model.Cliente;
import model.Tipo;

/**
 * T1 - T�cnicas de Programa��o
 * 
 * @author Andrei
 * @author Rodrigo Scorsatto
 * @author Alexandre Baptista
 * @version 25/09/09
 */

public class SimuladorAgencia {
	private LoaderPropriedades loader = null;
	private Agencia agencia = null;
	private Random random;
	private int totalClientes;
	private int totalPreferencial;
	private StatisticsCollector collector;
	
	public SimuladorAgencia() {
		this.loader = new LoaderPropriedades();
		loader.Load();
		random = new Random();
		totalClientes = 0;
		totalPreferencial = 0;
		collector = new StatisticsCollector();
		this.agencia = new Agencia(loader.gettRMin(), loader.gettRMax(), collector);
	}
	
	/**
	 * @precondition loader.getnP() > 0 && loader.getnC() > 0
	 * @postcondition agencia != null
	 * @throws InterruptedException
	 */
	public void executarSimulacao() throws InterruptedException {
		assert loader.getnP() > 0 && loader.getnC() > 0;
		
		for (int i = 0; i < loader.getnP(); i++) {
			agencia.adicionaCaixa(new Caixa(Tipo.Preferencial, loader.gettRMin(), loader.gettRMax(), collector));
		}
	
		for (int i = 0; i < loader.getnC() - loader.getnP(); i++) {
			agencia.adicionaCaixa(new Caixa(Tipo.Normal, loader.gettRMin(), loader.gettRMax(), collector));
		}
		
		agencia.iniciarAtendimento();
		
		long inicio = System.currentTimeMillis();
		
		do {
			agencia.adicionaCliente(new Cliente(defineTipoCliente(), loader.getqRMin(), loader.getqRMax(), collector));
			Thread.sleep(random.nextInt(loader.gettCmax() - loader.gettCMin()) + loader.gettCMin());
		} while (System.currentTimeMillis() - inicio < loader.gettSim() * 100);
		
		agencia.encerrarAtendimento();
		
		collector.gerarEstatisticas();
		
		assert agencia != null;
	}

	private Tipo defineTipoCliente() {
		if (totalClientes == 0) {
			totalClientes++;
			return Tipo.Normal;
		}
		
		if (totalPreferencial/totalClientes < 0.4) {
			totalClientes++;
			totalPreferencial++;
			return Tipo.Preferencial;
		}
		
		totalClientes++;
		return Tipo.Normal;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public int getTotalClientes() {
		return totalClientes;
	}

	public void setTotalClientes(int totalClientes) {
		this.totalClientes = totalClientes;
	}

	public int getTotalPreferencial() {
		return totalPreferencial;
	}

	public void setTotalPreferencial(int totalPreferencial) {
		this.totalPreferencial = totalPreferencial;
	}

	public StatisticsCollector getCollector() {
		return collector;
	}
	
	
	
	
}

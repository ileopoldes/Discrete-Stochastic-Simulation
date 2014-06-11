package core.simulations;


import java.util.Random;

import core.util.LoaderPropriedades;
import core.util.StatisticsCollector;
import model.Loja;
import model.Atendente;
import model.Cliente;
import model.Tipo;

public class SimuladorLoja {
	private LoaderPropriedades loader = null;
	private Loja loja = null;
	private Random random;
	private int totalClientes;
	private int totalPreferencial;
	private StatisticsCollector collector;
	
	public SimuladorLoja() {
		this.loader = new LoaderPropriedades();
		loader.Load();
		random = new Random();
		totalClientes = 0;
		totalPreferencial = 0;
		collector = new StatisticsCollector();
		this.loja = new Loja(loader.gettRMin(), loader.gettRMax(), collector);
	}
	
	/**
	 * @precondition loader.getnP() > 0 && loader.getnC() > 0
	 * @postcondition loja != null
	 * @throws InterruptedException
	 */
	public void executarSimulacao() throws InterruptedException {
		assert loader.getnP() > 0 && loader.getnC() > 0;
		
		for (int i = 0; i < loader.getnP(); i++) {
			loja.adicionaAtendente(new Atendente(Tipo.Preferencial, loader.gettRMin(), loader.gettRMax(), collector));
		}
	
		for (int i = 0; i < loader.getnC() - loader.getnP(); i++) {
			loja.adicionaAtendente(new Atendente(Tipo.Normal, loader.gettRMin(), loader.gettRMax(), collector));
		}
		
		loja.iniciarAtendimento();
		
		long inicio = System.currentTimeMillis();
		
		do {
			loja.adicionaCliente(new Cliente(defineTipoCliente(), loader.getqRMin(), loader.getqRMax(), collector));
			Thread.sleep(random.nextInt(loader.gettCmax() - loader.gettCMin()) + loader.gettCMin());
		} while (System.currentTimeMillis() - inicio < loader.gettSim() * 100);
		
		loja.encerrarAtendimento();
		
		collector.gerarEstatisticas();
		
		assert loja != null;
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

	
	
	
	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
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

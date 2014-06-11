package model;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import core.util.StatisticsCollector;

/**
 * T1 - T�cnicas de Programa��o
 * 
 * @author Andrei
 * @author Rodrigo Scorsatto
 * @author  Alexandre Baptista
 * @version 25/09/09
 */
public class Agencia {
	
	private ArrayList<Caixa> caixas=null;
	private ArrayBlockingQueue<Cliente> filaPrincipal=null;
	private ArrayBlockingQueue<Cliente> filaPreferencial=null;
	private Lock lock;
	private Lock lockCheques;
	private ExecutorService executor;
	private StatisticsCollector collector;
	public static final int MAX=100;
	/**
	 * Construtor da Classe Agencia
	 * 
	 * @param tRmin - tempo minimo da requisi��o
	 * @param tRmax - tempo m�ximo da requisi��o
	 * @postcondition caixas !=null
	 * @postcondition filaPreferencial !=null
	 * @postcondition filaPrincipal !=null
	 * @postcondition cheques !=null  
	 * @postcondition collector != null
	 */
	public Agencia(int tRmin, int tRmax, StatisticsCollector collector){
		caixas = new ArrayList<Caixa>();
		filaPreferencial = new ArrayBlockingQueue<Cliente>(MAX, true);
		filaPrincipal = new ArrayBlockingQueue<Cliente>(MAX, true);
		lock = new ReentrantLock();
		lockCheques = new ReentrantLock();
		executor = Executors.newCachedThreadPool();
		this.collector = collector;
		
		assert caixas !=null;
		assert filaPreferencial !=null;
		assert filaPrincipal !=null;
	}
	/**
	 * 
	 * @param caixa
	 * @precondition caixa !=null
	 * @postcondition caixas.size() > 0
	 */
	public void adicionaCaixa(Caixa caixa) {
		assert caixa !=null;
		caixa.setFilaPrincipal(filaPrincipal);
		caixa.setLock(lock);
		caixa.setLockCheques(lockCheques);
		if(caixa.getTipo() == Tipo.Preferencial)
			caixa.setFilaPreferencial(filaPreferencial);
		caixas.add(caixa);
		
		assert caixas != null;
		assert caixas.size()>0;
	}
	/**
	 * M�todo respons�vel por colocar um cliente em sua respectiva fila
	 * @param cliente
	 */
	public void adicionaCliente(Cliente cliente) {
		if (cliente.getTipo() == Tipo.Preferencial)	{
			try {
				cliente.enterQueue();
				filaPreferencial.put(cliente);
				assert filaPreferencial!=null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				cliente.enterQueue();
				filaPrincipal.put(cliente);
				assert filaPrincipal!=null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		collector.informaTamanhoFilaClientes(filaPrincipal.size(), filaPreferencial.size());
	}
	
	public void iniciarAtendimento() {
		for (Caixa caixa : this.caixas) {
			executor.execute(caixa);
		}
		executor.shutdown();
	}
	
	public void encerrarAtendimento() {
		try {
			if (!executor.awaitTermination(10, TimeUnit.SECONDS))
				executor.shutdownNow();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayBlockingQueue<Cliente> getFilaPrincipal() {
		return filaPrincipal;
	}
	
	public ArrayBlockingQueue<Cliente> getFilaPreferencial() {
		return filaPreferencial;
	}
	
	public ArrayList<Caixa> getCaixas() {
		return caixas;
	}
}

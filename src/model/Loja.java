package model;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import core.util.StatisticsCollector;

public class Loja {
	
	private ArrayList<Atendente> atendentes=null;
	private ArrayBlockingQueue<Cliente> filaBalcao=null;
	private ArrayBlockingQueue<Cliente> filaTelefone=null;
	private Lock lock;
	private Lock lockCheques;
	private ExecutorService executor;
	private StatisticsCollector collector;
	public static final int MAX=100;
	
	/**
	 * Construtor da Classe Loja
	 * 
	 * @param tRmin - tempo minimo da requisicao
	 * @param tRmax - tempo maximo da requisicao
	 */
	public Loja(int tRmin, int tRmax, StatisticsCollector collector){
		atendentes = new ArrayList<Atendente>();
		filaTelefone = new ArrayBlockingQueue<Cliente>(MAX, true);
		filaBalcao = new ArrayBlockingQueue<Cliente>(MAX, true);
		lock = new ReentrantLock();
		lockCheques = new ReentrantLock();
		executor = Executors.newCachedThreadPool();
		this.collector = collector;
		
		assert atendentes !=null;
		assert filaTelefone !=null;
		assert filaBalcao !=null;
	}
	/**
	 * 
	 * @param atendente
	 */
	public void adicionaAtendente(Atendente atendente) {
		assert atendente !=null;
		atendente.setFilaPrincipal(filaBalcao);
		atendente.setLock(lock);
		atendente.setLockCheques(lockCheques);
		if(atendente.getTipo() == Tipo.Preferencial)
			atendente.setFilaPreferencial(filaTelefone);
		atendentes.add(atendente);
		
		assert atendentes != null;
		assert atendentes.size()>0;
	}
	
	
	public void adicionaCliente(Cliente cliente) {
		if (cliente.getTipo() == Tipo.Preferencial)	{
			try {
				cliente.enterQueue();
				filaTelefone.put(cliente);
				assert filaTelefone!=null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				cliente.enterQueue();
				filaBalcao.put(cliente);
				assert filaBalcao!=null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		collector.informaTamanhoFilaClientes(filaBalcao.size(), filaTelefone.size());
	}
	
	public void iniciarAtendimento() {
		for (Atendente atendente : this.atendentes) {
			executor.execute(atendente);
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
	
	
	
	
	
	// ::: Getters and Setters :::
	
	public ArrayBlockingQueue<Cliente> getFilaPrincipal() {
		return filaBalcao;
	}
	
	public ArrayBlockingQueue<Cliente> getFilaPreferencial() {
		return filaTelefone;
	}
	
	public ArrayList<Atendente> getAtendentes() {
		return atendentes;
	}
}

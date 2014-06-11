package model;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;

import core.util.StatisticsCollector;

/**
 * T1 - T�cnicas de Programa��o
 * 
 * @author Andrei 
 * @author Rodrigo Scorsatto
 * @author Alexandre Baptista
 * @version 25/09/09
 */

public class Caixa implements Runnable {
	/**
	 * Tipo do caixa, pode ser normal ou prefer�ncial
	 */
	private Tipo tipo;
	private ArrayBlockingQueue<Cliente> filaPrincipal=null;
	private ArrayBlockingQueue<Cliente> filaPreferencial=null;
	private Lock lock;
	private Lock lockCheques;
	private int tRmin;
	private int tRmax;
	private Random random;
	private StatisticsCollector collector;
	
	public Caixa(Tipo tipo, int tRmin, int tRmax, StatisticsCollector collector) {
		this.tRmin = tRmin;
		this.tRmax = tRmax;
		this.tipo = tipo;
		random = new Random();
		this.collector = collector;
	}

	public Tipo getTipo(){
		return tipo;
	}
	
	public void setTipo(Tipo tipo){
		this.tipo = tipo;
	}

	@Override
	public void run() {
		boolean continua = true;
		while(continua) {
			int sleepTime = 0;
			// Se for caixa normal, n�o atende cliente preferencial...
			// Logo, tranca se a fila estiver vazia e fica esperando!
			if (this.tipo == Tipo.Normal) {
				try {
					Cliente temp = filaPrincipal.take();
					temp.leaveQueue();
					while (!temp.getRequisicoes().isEmpty()) {
						Requisicao req = temp.getRequisicoes().iterator().next();
						if (req.isCheque()) {
							lockCheques.lock();
							sleepTime = random.nextInt(tRmax-tRmin)+tRmin; 
							Thread.sleep(sleepTime);
							collector.informaCheque(sleepTime);
							lockCheques.unlock();
						} else {
							sleepTime = random.nextInt(tRmax-tRmin)+tRmin;
							Thread.sleep(sleepTime);
							collector.informaAtendimentoCliente(sleepTime);
						}
						temp.getRequisicoes().remove(req);
					}
				} catch (InterruptedException e) {
					continua = false;
				}
			}
			else {
				// P�ra todo mundo, a fim de garantir que n�o aconte�a
				// De uma thread entrar no if de uma fila n�o vazia
				// E a outra thread consumir antes e deixar essa trancada.
				lock.lock();
				// Se for caixa preferencial, fica testando as duas filas.
				// Atende primeiro a preferencial, se tiver cliente.
				if (!filaPreferencial.isEmpty()) {
					try {
						Cliente temp = filaPreferencial.take();
						temp.leaveQueue();
						while (!temp.getRequisicoes().isEmpty()) {
							Requisicao req = temp.getRequisicoes().iterator().next();
							if (req.isCheque()) {
								lockCheques.lock();
								sleepTime = random.nextInt(tRmax-tRmin)+tRmin; 
								Thread.sleep(sleepTime);
								collector.informaCheque(sleepTime);
								lockCheques.unlock();
							} else {
								sleepTime = random.nextInt(tRmax-tRmin)+tRmin;
								Thread.sleep(sleepTime);
								collector.informaAtendimentoCliente(sleepTime);
							}
							temp.getRequisicoes().remove(req);
						}
					} catch (InterruptedException e) {
						continua = false;
					}
				} else if (!filaPrincipal.isEmpty()) {
					try {
						Cliente temp = filaPrincipal.take();
						temp.leaveQueue();
						while (!temp.getRequisicoes().isEmpty()) {
							Requisicao req = temp.getRequisicoes().iterator().next();
							if (req.isCheque()) {
								lockCheques.lock();
								sleepTime = random.nextInt(tRmax-tRmin)+tRmin; 
								Thread.sleep(sleepTime);
								collector.informaCheque(sleepTime);
								lockCheques.unlock();
							} else {
								sleepTime = random.nextInt(tRmax-tRmin)+tRmin;
								Thread.sleep(sleepTime);
								collector.informaAtendimentoCliente(sleepTime);
							}
							temp.getRequisicoes().remove(req);
						}
					} catch (InterruptedException e) {
						continua = false;
					}
				}
				// D� um tempo pro processador.
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					continua = false;
				}
				// Libera o lock para os outros.
				lock.unlock();
			}
		}
	}

	public void setFilaPrincipal(ArrayBlockingQueue<Cliente> filaPrincipal) {
		this.filaPrincipal = filaPrincipal;
	}

	public void setFilaPreferencial(ArrayBlockingQueue<Cliente> filaPreferencial) {
		this.filaPreferencial = filaPreferencial;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public void setLockCheques(Lock lockCheques) {
		this.lockCheques = lockCheques;
	}
}

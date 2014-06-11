package model;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;

import core.util.StatisticsCollector;


public class Atendente implements Runnable {
	
	private Tipo tipo;
	private ArrayBlockingQueue<Cliente> filaPrincipal=null;
	private ArrayBlockingQueue<Cliente> filaPreferencial=null;
	private Lock lock;
	private Lock lockCheques;
	private int tRmin;
	private int tRmax;
	private Random random;
	private StatisticsCollector collector;
	
	public Atendente(Tipo tipo, int tRmin, int tRmax, StatisticsCollector collector) {
		this.tRmin = tRmin;
		this.tRmax = tRmax;
		this.tipo = tipo;
		random = new Random();
		this.collector = collector;
	}

	@Override
	public void run() {
		boolean continua = true;
		while(continua) {
			int sleepTime = 0;
			// Se for atendente normal, não atende cliente preferencial...
			// Logo, tranca se a fila estiver vazia e fica esperando!
			if (this.tipo == Tipo.Normal) {
				try {
					Cliente temp = filaPrincipal.take();
					temp.leaveQueue();
					while (!temp.getRequisicoes().isEmpty()) {
						Requisicao req = temp.getRequisicoes().iterator().next();
						if (req.isCartao()) {
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
				
				// Para todo mundo, a fim de garantir que nao aconteca
				// De uma thread entrar no if de uma fila nao vazia
				// E a outra thread consumir antes e deixar essa trancada.
				lock.lock();
				
				// Se for atendente preferencial, fica testando as duas filas.
				// Atende primeiro a preferencial, se tiver cliente.
				
				if (!filaPreferencial.isEmpty()) {
					try {
						Cliente temp = filaPreferencial.take();
						temp.leaveQueue();
						while (!temp.getRequisicoes().isEmpty()) {
							Requisicao req = temp.getRequisicoes().iterator().next();
							if (req.isCartao()) {
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
							if (req.isCartao()) {
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
				// um tempo pro processador.
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

	
	
	
	
	
	public Tipo getTipo(){
		return tipo;
	}
	
	public void setTipo(Tipo tipo){
		this.tipo = tipo;
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

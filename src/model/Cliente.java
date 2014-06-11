package model;

import java.util.ArrayList;
import java.util.Random;

import core.util.StatisticsCollector;

public class Cliente {
	private ArrayList<Requisicao> requisicoes=null;
	private Tipo tipo=null;
	private StatisticsCollector collector;
	long time;
	
	public Cliente(Tipo tipo, int qRMin, int qRMax, StatisticsCollector collector){
		assert tipo!=null;
		
		requisicoes = new ArrayList<Requisicao>();
		this.collector = collector;
		
		this.setTipo(tipo);
		this.geraRequisicoes(qRMin, qRMax);
		time = 0;
		collector.informaCliente();
	}
	
	protected void geraRequisicoes(int qRMin, int qRMax) {
		int totalRequisicoes = 0;
		int totalCheques = 0;
		Random random = new Random();
		int total = random.nextInt(qRMax - qRMin) + qRMin;
		collector.informaRequisicao(total);
		
		for (int i = 0; i < total; i++) {
			if (totalRequisicoes == 0) {
				requisicoes.add(new Requisicao(false));
				totalRequisicoes++;
			} else {
				if (totalCheques / totalRequisicoes < 0.8) {
					requisicoes.add(new Requisicao(true));
					totalRequisicoes++;
					totalCheques++;
				} else {
					requisicoes.add(new Requisicao(false));
					totalRequisicoes++;
				}
			}
		}
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public ArrayList<Requisicao> getRequisicoes() {
		return requisicoes;
	}
	
	public void enterQueue() {
		time = System.currentTimeMillis();
	}
	
	public long leaveQueue() {
		time = System.currentTimeMillis() - time;
		
		if (this.getTipo() == Tipo.Normal)
			collector.informaTempoEsperaClienteNormal(time);
		else collector.informaTempoEsperaClientePreferencial(time);
		
		return time;
	}
}

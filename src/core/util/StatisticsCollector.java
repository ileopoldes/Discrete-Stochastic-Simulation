package core.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class StatisticsCollector {
	private Properties properties;
	private FileOutputStream outputStream;
	private int qCS;
	private double qMSC;
	private double tMAT;
	private double tMAC;
	private int tMFC;
	private double tMEN;
	private double tMEP;
	private int cont_tMAT;
	private int cont_tMAC;
	private int cont_tMEN;
	private int cont_tMEP;
	
	public StatisticsCollector() {
		qCS =  tMFC = cont_tMAT = cont_tMAC = cont_tMEN = cont_tMEP = 0;
		qMSC = tMAT = tMAC = tMEN = tMEP = 0.0;
		try {
			outputStream = new FileOutputStream("execucao.resultado");
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		properties = new Properties();
	}

	public void informaCliente() {
		qCS++;
	}

	public void informaRequisicao(int numReq) {
		qMSC += numReq;
	}
	
	public synchronized void informaCheque(int tempo) {
		tMAT += tempo;
		cont_tMAT++;
	}
	
	public synchronized void informaAtendimentoCliente(int tempo) {
		tMAC += tempo;
		cont_tMAC++;
	}
	
	public synchronized void informaTamanhoFilaClientes(int tamPrinc, int tamPref) {
		if (tMFC < tamPrinc + tamPref)
			tMFC = tamPrinc + tamPref;
	}
	
	public synchronized void informaTempoEsperaClienteNormal(long time) {
		tMEN += time;
		cont_tMEN++;
	}
	
	public synchronized void informaTempoEsperaClientePreferencial(long time) {
		tMEP += time;
		cont_tMEP++;
	}
	
	public synchronized boolean gerarEstatisticas() {
		qMSC = qMSC / qCS;
		tMAT = tMAT / cont_tMAT;
		tMAC = tMAC / cont_tMAC;
		tMEN = tMEN / cont_tMEN;
		tMEP = tMEP / cont_tMEP;
		
		properties.setProperty("qCS", String.valueOf(qCS));
		properties.setProperty("qMSC", String.valueOf(qMSC));
		properties.setProperty("tMAT", String.valueOf(tMAT));
		properties.setProperty("tMAC", String.valueOf(tMAC));
		properties.setProperty("tMFC", String.valueOf(tMFC));
		properties.setProperty("tMEN", String.valueOf(tMEN));
		properties.setProperty("tMEP", String.valueOf(tMEP));
		
		try {
			properties.store(outputStream, "Resultados");
			outputStream.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}

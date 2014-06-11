package core.util;
/**
 * T1 - T�cnicas de Programa��o
 * 
 * @author Andrei
 * @author Rodrigo Scorsatto
 * @author Alexandre Baptista
 * @version 25/09/09
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoaderPropriedades {
	private Properties properties;
	private FileInputStream inputStream;
	private int tSim, tCMin, tCmax, qRMin, qRMax, tRMin, tRMax, nC, nP;

	/**
	 * 
	 * Classe repons�vel por carregar as configura��es do arquivo
	 * simulador.config
	 * 
	 */
	public LoaderPropriedades() {
		properties = new Properties();
		try {
			inputStream = new FileInputStream("simulador.config");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	LoaderPropriedades(String arquivo) {
		try {
			inputStream = new FileInputStream(arquivo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * Classe repons�vel por carregar as configura��es do simulador
	 * 
	 */
	public void Load() {
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		tSim = Integer.parseInt(properties.getProperty("tSim"));
		tCMin = Integer.parseInt(properties.getProperty("tCMin"));
		tCmax = Integer.parseInt(properties.getProperty("tCmax"));
		qRMin = Integer.parseInt(properties.getProperty("qRMin"));
		qRMax = Integer.parseInt(properties.getProperty("qRmax"));
		tRMin = Integer.parseInt(properties.getProperty("tRmin"));
		tRMax = Integer.parseInt(properties.getProperty("tRmax"));
		nC = Integer.parseInt(properties.getProperty("NC"));
		nP = Integer.parseInt(properties.getProperty("NP"));

		if (tCmax < tCMin) {
			tCmax = tCMin;
		}

		if (qRMax < qRMin) {
			qRMax = qRMin;
		}

		if (tRMax < tRMin) {
			tRMax = tRMin;
		}

		if (nC < nP) {
			nC = nP;
		}
	}
	
	public FileOutputStream geraArquivo(){
		return null;
	}

	public int gettSim() {
		return tSim;
	}

	public void settSim(int tSim) {
		this.tSim = tSim;
	}

	public int gettCMin() {
		return tCMin;
	}

	public void settCMin(int tCMin) {
		this.tCMin = tCMin;
	}

	public int gettCmax() {
		return tCmax;
	}

	public void settCmax(int tCmax) {
		this.tCmax = tCmax;
	}

	public int getqRMin() {
		return qRMin;
	}

	public void setqRMin(int qRMin) {
		this.qRMin = qRMin;
	}

	public int getqRMax() {
		return qRMax;
	}

	public void setqRMax(int qRMax) {
		this.qRMax = qRMax;
	}

	public int gettRMin() {
		return tRMin;
	}

	public void settRMin(int tRMin) {
		this.tRMin = tRMin;
	}

	public int gettRMax() {
		return tRMax;
	}

	public void settRMax(int tRMax) {
		this.tRMax = tRMax;
	}

	public int getnC() {
		return nC;
	}

	public void setnC(int nC) {
		this.nC = nC;
	}

	public int getnP() {
		return nP;
	}

	public void setnP(int nP) {
		this.nP = nP;
	}

	@Override
	public String toString() {
		return "LoaderPropriedades: " + properties.toString();
	}
}

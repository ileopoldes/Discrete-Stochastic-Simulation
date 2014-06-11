//package model.test;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import model.Tipo;
//
//import org.junit.Before;
//import org.junit.Test;
//
//public class AgenciaTest {
//	private Agencia agencia = null;
//	public static final int TRMIN = 1, TRMAX = 15;
//	public static final int QRMIN = 2, QRMAX = 20;
//	private Caixa caixa = null;
//	private Cliente clienteNormal = null, clientePreferencial = null;
//
//	@Before
//	public void initcomponents() {
//		agencia = new Agencia(1, 15, new StatisticsCollector());
//		caixa = new Caixa(Tipo.Preferencial, TRMIN, TRMAX, new StatisticsCollector());
//		clienteNormal = new Cliente(Tipo.Normal, QRMIN, QRMAX, new StatisticsCollector());
//		clientePreferencial = new Cliente(Tipo.Preferencial, QRMIN, QRMAX, new StatisticsCollector());
//	}
//
//	@Test
//	public void adicionaCaixaTest() {
//		assertNotNull(caixa);
//		agencia.adicionaCaixa(caixa);
//		assertTrue(agencia.getCaixas().size() != 0);
//	}
//
//	@Test
//	public void adicionaClienteNormalTest() {
//		agencia.adicionaCliente(clienteNormal);
//		assertTrue(agencia.getFilaPrincipal().size() != 0);
//	}
//
//	@Test
//	public void adicionaClientePreferencialTest() {
//		agencia.adicionaCliente(clientePreferencial);
//		assertTrue(agencia.getFilaPreferencial().size() != 0);
//	}
//}

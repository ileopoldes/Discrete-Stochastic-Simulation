//package core.simulations.test;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
//import core.simulations.SimuladorAgencia;
//
//public class SimuladorAgenciaTest {
//	private SimuladorAgencia simulador;
//	
//	@Test
//	public void testaSimulador(){
//		simulador = new SimuladorAgencia();
//		assertEquals(simulador.getTotalClientes(), 0);
//		assertEquals(simulador.getTotalPreferencial(), 0);
//		assertNotNull(simulador.getAgencia());
//	}
//	
//	@Test
//	public void testaSimulacao() throws InterruptedException{
//		simulador = new SimuladorAgencia();
//		simulador.executarSimulacao();
//		assertTrue(simulador.getCollector().gerarEstatisticas());
//	}
//}

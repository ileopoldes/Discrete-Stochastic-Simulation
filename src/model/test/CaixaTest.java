//package model.test;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//import model.Tipo;
//
//import org.junit.Test;
//
//public class CaixaTest {
//	private Caixa caixa;
//	
//	@Test
//	public void testaCaixa(){
//		caixa = new Caixa(Tipo.Normal, 1, 2, new StatisticsCollector());
//		assertNotNull(caixa);
//	}
//	
//	@Test
//	public void testaRun(){
//		Thread t = new Thread(caixa);
//		t.start();
//		assertTrue(t.isAlive());
//	}
//}

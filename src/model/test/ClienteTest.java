//package model.test;
//import static org.junit.Assert.assertTrue;
//import model.Tipo;
//
//import org.junit.Test;
//
//public class ClienteTest {
//	private Cliente cliente;
//	
//	@Test
//	public void testaGerarRequisicoes(){
//		cliente = new Cliente(Tipo.Normal, 10, 20, new StatisticsCollector());
//		cliente.geraRequisicoes(10, 20);
//		assertTrue(cliente.getRequisicoes().size() >= 10 || cliente.getRequisicoes().size() <= 20);
//	}
//}

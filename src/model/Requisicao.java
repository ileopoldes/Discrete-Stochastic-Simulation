package model;


public class Requisicao {

	private boolean cartao=false;

	Requisicao(boolean cartao) {
		this.cartao = cartao;
	}

	public boolean isCartao() {
		return cartao;
	}
}

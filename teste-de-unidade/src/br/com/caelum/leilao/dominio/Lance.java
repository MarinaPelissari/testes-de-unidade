package br.com.caelum.leilao.dominio;

public class Lance {

	private Usuario usuario;
	private double valor;
	
	public Lance(Usuario usuario, double valor) {
		this.usuario = usuario;
		this.valor = valor;
	}

	public Usuario usuario() {
		return usuario;
	}

	public double valor() {
		return valor;
	}
}

package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private Calendar data;
	private List<Lance> lances;
	private boolean encerrado;
	private int id;
	
	public Leilao(String descricao) {
		this(descricao, Calendar.getInstance());
	}
	
	public Leilao(String descricao, Calendar data) {
		this.descricao = descricao;
		this.data = data;
		this.lances = new ArrayList<Lance>();
	}
	
	public void propoe(Lance lance) {
		if(lances.isEmpty() || podeDarLance(lance.usuario())) {
			lances.add(lance);
		}
	}

	private boolean podeDarLance(Usuario usuario) {
		return !ultimoLanceDado().usuario().equals(usuario) && qtdDeLancesDo(usuario) < 5;
	}

	private int qtdDeLancesDo(Usuario usuario) {
		int total = 0;
		for(Lance l : lances) {
			if(l.usuario().equals(usuario)) total++;
		}
		return total;
	}

	public void dobraLance(Usuario usuario) {
		Lance ultimoLance = ultimoLanceDo(usuario);
		if (ultimoLance != null) {
			propoe(new Lance(usuario, ultimoLance.valor() * 2));
		}
	}

	private Lance ultimoLanceDo(Usuario usuario) {
		Lance ultimoLance = null;
		for (Lance lance : lances) {
			if (lance.usuario().equals(usuario))
				ultimoLance = lance;
		}

		return ultimoLance;
	}

	private Lance ultimoLanceDado() {
		return lances.get(lances.size()-1);
	}

	public String descricao() {
		return descricao;
	}

	public List<Lance> lances() {
		return Collections.unmodifiableList(lances);
	}

	public Calendar data() {
		return (Calendar) data.clone();
	}

	public void encerra() {
		this.encerrado = true;
	}
	
	public boolean isEncerrado() {
		return encerrado;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int id() {
		return id;
	}
}

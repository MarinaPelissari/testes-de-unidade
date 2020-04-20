package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
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
	
	public void propoe(Lance lance) {
		if(lances.isEmpty() || podeDarUmLance(lance.usuario())) {
			lances.add(lance);
		}
	}

	private boolean podeDarUmLance(Usuario usuario) {
		return !ultimoLanceDado().usuario().equals(usuario) && quantidadeDeLancesDo(usuario) < 5;
	}

	private int quantidadeDeLancesDo(Usuario usuario) {
		int totalLancesPorUsuario = 0;

		for(Lance lancePorUsuario : lances) {
			if(lancePorUsuario.usuario().equals(usuario)) totalLancesPorUsuario++;
		}
		return totalLancesPorUsuario;
	}

	public void dobraLance(Usuario usuario) {
		Lance ultimoLance = ultimoLanceDo(usuario);
		if(ultimoLance != null) {
			propoe(new Lance(usuario, ultimoLance.valor() * 2));
		}
    }

    private Lance ultimoLanceDo(Usuario usuario) {
        Lance ultimoLance = null;
        for(Lance lance : lances) {
            if(lance.usuario().equals(usuario)) ultimoLance = lance;
        }

        return ultimoLance;
    }
}

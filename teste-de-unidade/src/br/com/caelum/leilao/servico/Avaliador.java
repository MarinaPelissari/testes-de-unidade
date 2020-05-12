package br.com.caelum.leilao.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private List<Lance> maiores;
	private double media;

	public void avalia(Leilao leilao) {
		if(leilao.lances().size() == 0) {
			throw new RuntimeException("Não é possível avaliar um leilão sem lances!");
		}
		
		for(Lance lance : leilao.lances()) {
			if(lance.valor() > maiorDeTodos) maiorDeTodos = lance.valor();
			if (lance.valor() < menorDeTodos) menorDeTodos = lance.valor();
		}
		
		tresMaioresLances(leilao);
	}

	private void tresMaioresLances(Leilao leilao) {
		maiores = new ArrayList<Lance>(leilao.lances());
		Collections.sort(maiores, new Comparator<Lance>() {

			public int compare(Lance o1, Lance o2) {
				if(o1.valor() < o2.valor()) return 1;
				if(o1.valor() > o2.valor()) return -1;
				return 0;
			}
		});
		maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
	}

	public List<Lance> tresMaioresLances() {
		return maiores;
	}
	
	public double maiorLance() {
		return maiorDeTodos;
	}
	
	public double menorLance() {
		return menorDeTodos;
    }

	public double mediaDosLances(Leilao leilao) {
        for(Lance lance : leilao.lances()) {
            media = Double.sum(media, lance.valor());
        }

        media = media / leilao.lances().size();

        return media;
    }
}

package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {
    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private double media = 0;

    public void avalia(Leilao leilao) {


        double total = 0;
        for(Lance lance : leilao.lances()) {
            if(lance.valor() > maiorDeTodos) maiorDeTodos = lance.valor();
            if(lance.valor() < menorDeTodos) menorDeTodos = lance.valor();
            total += lance.valor();
        }
        if(total == 0) {
            media = 0;
            return;
        }
        media = total / leilao.lances().size();
    }

    public double maiorLance() {
        return maiorDeTodos;
    }

    public double menorLance() {
        return menorDeTodos;
    }

    public double mediaDosLances() {
        return media;
    }

    public void mediaDosLances(Leilao leilao) {
        for(Lance lance : leilao.lances()) {
            media = Double.sum(media, lance.valor());
        }

        media = media / leilao.lances().size();
    }
}
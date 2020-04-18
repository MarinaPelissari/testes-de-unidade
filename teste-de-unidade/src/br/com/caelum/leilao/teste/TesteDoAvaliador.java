package br.com.caelum.leilao.teste;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class TesteDoAvaliador {

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 250.0));
        leilao.propoe(new Lance(jose, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        Assert.assertEquals(400.0, leiloeiro.maiorLance(), 0.00001);
        Assert.assertEquals(250.0, leiloeiro.menorLance(), 0.00001);
    }

    @Test
    public void mediaDosLances() {
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Xbox 360");

        leilao.propoe(new Lance(joao, 250.0));
        leilao.propoe(new Lance(jose, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        Assert.assertEquals(316.66, leiloeiro.mediaDosLances(), 1);
    }

    @Test
    public void mediaDosLancesZero() {
        Usuario joao = new Usuario("João");

        Leilao leilao = new Leilao("Nintendo Switch");

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        Assert.assertEquals(0, leiloeiro.mediaDosLances(), 0.0001);
    }
}
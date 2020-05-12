package br.com.caelum.leilao.teste;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class TesteDoAvaliador {
    private Avaliador leiloeiro;
    private Usuario maria;
    private Usuario jose;
    private Usuario joao;

    @Before
    public void criaAvaliador() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.maria = new Usuario("Maria");
    }

    @Test(expected = RuntimeException.class)
    public void naoDeveAvaliarLeilaoSemLance() {
        Leilao leilao = new CriadorDeLeilao().para("PlayStation 3").constroi();

        leiloeiro.avalia(leilao);
    }

    @Test
    public void lancesEmOrdemCrescente() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3").lance(joao, 1000.0).lance(maria, 2000.0)
                .lance(jose, 3000.0).constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.maiorLance(), is(3000.0));
        assertThat(leiloeiro.menorLance(), is(1000.0));
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescenteComOutrosValores() {
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 1000.0).lance(jose, 2000.0)
                .lance(maria, 3000.0).constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.maiorLance(), is(3000.0));
        assertThat(leiloeiro.menorLance(), is(1000.0));
    }

    @Test
    public void lancesEmOrdemRandomica() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3").lance(joao, 200).lance(maria, 450).lance(joao, 120)
                .lance(maria, 700).lance(maria, 630).lance(maria, 230).constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.maiorLance(), is(700.0));
        assertThat(leiloeiro.menorLance(), is(120.0));
    }

    @Test
    public void lancesEmOrdemDecrescente() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3").lance(joao, 400).lance(maria, 200).lance(joao, 300)
                .lance(maria, 100).constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.menorLance(), is(100.0));
        assertThat(leiloeiro.maiorLance(), is(400.0));
    }

    @Test
    public void mediaDosLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3").lance(joao, 250).lance(maria, 300).lance(joao, 400)
                .constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.mediaDosLances(leilao), is(316.6666666666667));
    }

    @Test
    public void mediaDosLancesZero() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3").constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.mediaDosLances(leilao), is(0));
    }

    @Test
    public void leilaoComApenasUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3").lance(joao, 1000).constroi();

        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.maiorLance(), is(1000.0));
        assertThat(leiloeiro.menorLance(), is(1000.0));
        assertThat(leiloeiro.mediaDosLances(leilao), is(1000.0));
    }

    @Test
    public void tresMaioresLancesLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 100).lance(maria, 200)
                .lance(joao, 300).lance(maria, 400).constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.tresMaioresLances();
        assertThat(maiores.size(), is(3));

        assertThat(maiores, hasItems(new Lance(maria, 400), new Lance(joao, 300), new Lance(maria, 200)));
    }

    @Test
    public void tresMaioresLancesLancesEmUmLeilaoComDoisLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3").lance(joao, 100).lance(maria, 200).constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.tresMaioresLances();

        assertThat(maiores.size(), is(2));
        assertThat(maiores.get(0).valor(), is(200.0));
        assertThat(maiores.get(1).valor(), is(100.0));
    }

    @Test
    public void nenhumMaiorLanceEmUmLeilaoSemLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3").constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.tresMaioresLances();

        assertThat(maiores.size(), is(0));
    }
}
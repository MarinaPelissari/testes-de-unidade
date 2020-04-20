package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;

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
    private Usuario joao;
    private Usuario maria;

    @Before
    public void criaAvaliador() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.maria = new Usuario("Maria");
    }

    @Test
    public void lancesEmOrdemCrescente() {
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 250.0));
        leilao.propoe(new Lance(jose, 300.0));
        leilao.propoe(new Lance(maria, 400.0));
    @Test
    public void lancesEmOrdemCrescente() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .lance(joao, 100)
                .lance(maria, 200)
                .lance(joao, 300)
                .lance(maria, 400)
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(400.0, leiloeiro.maiorLance(), 0.00001);
        assertEquals(100.0, leiloeiro.menorLance(), 0.00001);
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .lance(joao, 200)
                .lance(maria, 450)
                .lance(joao, 120)
                .lance(maria, 700)
                .lance(maria, 630)
                .lance(maria, 230)
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(700.0, leiloeiro.maiorLance(), 0.0001);
        assertEquals(120.0, leiloeiro.menorLance(), 0.0001);
    }

    @Test 
    public void lancesEmOrdemDescrescente() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .lance(joao, 400)
                .lance(maria, 200)
                .lance(joao, 300)
                .lance(maria, 100)
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(100.0, leiloeiro.menorLance(), 0.0001);
        assertEquals(400.0, leiloeiro.maiorLance(), 0.0001);
    }

    @Test
    public void mediaDosLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .lance(joao, 250)
                .lance(maria, 300)
                .lance(joao, 400)
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(316.66, leiloeiro.mediaDosLances(), 1);
    }

    @Test
    public void mediaDosLancesZero() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(0, leiloeiro.mediaDosLances(), 0.0001);
    }

    @Test
    public void leilaoComApenasUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .lance(joao, 1000)
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(1000.0, leiloeiro.maiorLance(), 0.00001);
        assertEquals(1000.0, leiloeiro.menorLance(), 0.00001);
        assertEquals(1000.0, leiloeiro.mediaDosLances(), 0.00001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .lance(joao, 100)
                .lance(maria, 200)
                .lance(joao, 300)
                .lance(maria, 400)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.tresMaioresLances();

        assertEquals(3, maiores.size());
        assertEquals(400.0, maiores.get(0).valor(), 0.00001);
        assertEquals(300.0, maiores.get(1).valor(), 0.00001);
        assertEquals(200.0, maiores.get(2).valor(), 0.00001);
    }

    @Test
    public void deveEncontrarOsMaioresLancesEmUmLeilaoComDoisLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .lance(joao, 100)
                .lance(maria, 200)
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.tresMaioresLances();

        assertEquals(2, maiores.size());
        assertEquals(200.0, maiores.get(0).valor(), 0.00001);
        assertEquals(100.0, maiores.get(1).valor(), 0.00001);
    }

    @Test
    public void naoDeveEncontrarNenhumMaiorLanceEmUmLeilaoSemLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation3")
                .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.tresMaioresLances();

        assertEquals(0, maiores.size());
    }
}
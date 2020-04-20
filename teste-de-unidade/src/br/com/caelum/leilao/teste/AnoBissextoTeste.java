package br.com.caelum.leilao.teste;

import org.junit.Test;

import br.com.caelum.leilao.dominio.AnoBissexto;

public class AnoBissextoTeste {

    @Test
    public void retornaAnoBissexto() {
        AnoBissexto anoAtual = new AnoBissexto();

        anoAtual.ehAnoBissexto(2020);
    }

    @Test
    public void naoRetornaAnoBissexto() {
        AnoBissexto anoAtual = new AnoBissexto();

        anoAtual.ehAnoBissexto(2021);
    }
}
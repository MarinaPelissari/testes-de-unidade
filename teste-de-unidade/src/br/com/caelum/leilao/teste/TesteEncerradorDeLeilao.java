package br.com.caelum.leilao.teste;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import br.com.caelum.leilao.servico.EncerradorDeLeilao;

public class TesteEncerradorDeLeilao {
    
    @Test
    public void deveEncerrarLeiloesQueComecaramHaUmaSemana() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao = new CriadorDeLeilao().para("TV").naData(antiga).constroi();

        LeilaoDao dao = new LeilaoDao();
        dao.salva(leilao);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao();
        encerrador.encerra();

        assertTrue(leilao.isEncerrado());
    }
}
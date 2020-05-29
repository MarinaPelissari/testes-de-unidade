package br.com.caelum.leilao.teste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.dao.RepositorioDePagamentos;
import br.com.caelum.leilao.infra.relogio.Relogio;
import br.com.caelum.leilao.servico.Avaliador;
import br.com.caelum.leilao.servico.GeradorDePagamento;

public class TesteGeradorDePagamento {
    private Avaliador avaliador;
    private Usuario maria;
    private Usuario joao;
    private RepositorioDeLeiloes leiloes;
    private RepositorioDePagamentos pagamentos;

    @Before
    public void criaRepositorioDeLeiloes() {
        this.leiloes = mock(RepositorioDeLeiloes.class);
    }

    @Before
    public void criaRepositorioDePagamentos() {
        this.pagamentos = mock(RepositorioDePagamentos.class);
    }

    @Before
    public void criaAvaliador() {
        this.avaliador = new Avaliador();
        this.joao = new Usuario("João");
        this.maria = new Usuario("Maria");
    }

    @Test
    public void deveGerarPagamentoParaUmLeilaoEncerrado() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation").lance(joao, 2000.0).lance(maria, 2500.0).constroi();

        when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));

        GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, avaliador);
        gerador.gera();

        ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentos).salva(argumento.capture());
        Pagamento pagamentoGerado = argumento.getValue();
        assertEquals(2500.0, pagamentoGerado.valor());
    }

    @Test
    public void deveGerarPagamentoDoSabadoParaOProximoDiaUtil() {
        Relogio relogio = mock(Relogio.class);

        Calendar sabado = Calendar.getInstance();
        sabado.set(2012, Calendar.APRIL, 7);

        when(relogio.hoje()).thenReturn(sabado);

        Leilao leilao = new CriadorDeLeilao().para("Playstation").lance(joao, 2000.0).lance(maria, 2500.0).constroi();

        when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));

        GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, new Avaliador(), relogio);
        gerador.gera();

        ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentos).salva(argumento.capture());
        Pagamento pagamentoGerado = argumento.getValue();

        assertEquals(Calendar.MONDAY, pagamentoGerado.data().get(Calendar.DAY_OF_WEEK));
        assertEquals(9, pagamentoGerado.data().get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void deveGerarPagamentoDoDomingoParaOProximoDiaUtil() {
        Relogio relogio = mock(Relogio.class);

        Calendar sabado = Calendar.getInstance();
        sabado.set(2012, Calendar.APRIL, 8);

        when(relogio.hoje()).thenReturn(sabado);

        Leilao leilao = new CriadorDeLeilao().para("Playstation").lance(joao, 2000.0).lance(maria, 2500.0).constroi();

        when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));

        GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, new Avaliador(), relogio);
        gerador.gera();

        ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentos).salva(argumento.capture());
        Pagamento pagamentoGerado = argumento.getValue();

        assertEquals(Calendar.MONDAY, pagamentoGerado.data().get(Calendar.DAY_OF_WEEK));
        assertEquals(9, pagamentoGerado.data().get(Calendar.DAY_OF_MONTH));
    }
}
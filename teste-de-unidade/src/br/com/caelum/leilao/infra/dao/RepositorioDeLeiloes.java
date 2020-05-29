package br.com.caelum.leilao.infra.dao;

import java.util.List;

import br.com.caelum.leilao.dominio.Leilao;

public interface RepositorioDeLeiloes {
    List<Leilao> encerrados();
    List<Leilao> correntes();
    
    void salva(Leilao leilao);
    void atualiza(Leilao leilao);
}
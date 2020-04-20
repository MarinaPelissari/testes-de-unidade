package br.com.caelum.leilao.teste;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class TesteLeilao {

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new Leilao("Macbook Pro");

        assertEquals(0, leilao.lances().size());

        leilao.propoe(new Lance(new Usuario("Vicente"), 2000));

        assertEquals(1, leilao.lances().size());
        assertEquals(2000, leilao.lances().get(0).valor());
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new Leilao("Macbook Pro");

        leilao.propoe(new Lance(new Usuario("Vicente"), 2000.0));
        leilao.propoe(new Lance(new Usuario("Steve Jobs"), 3000.0));

        assertEquals(2, leilao.lances().size());
        assertEquals(2000.0, leilao.lances().get(0).valor());
        assertEquals(3000.0, leilao.lances().get(1).valor());
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro");

        Usuario steve = new Usuario("Steve Jobs");

        leilao.propoe(new Lance(steve, 2000.0));
        leilao.propoe(new Lance(steve, 3000.0));

        assertEquals(1, leilao.lances().size());
        assertEquals(2000.0, leilao.lances().get(0).valor());
    }

	@Test
	public void naoDeveAceitarMaisDoQue5LancesDoMesmoUsuario() {
        Leilao leilao = new Leilao("Macbook Pro");

        Usuario steve = new Usuario("Steve Jobs");
        Usuario vicente = new Usuario("Vicente");

        leilao.propoe(new Lance(steve, 2000.0));
        leilao.propoe(new Lance(vicente, 3000.0));
        
        leilao.propoe(new Lance(steve, 4000.0));
        leilao.propoe(new Lance(vicente, 5000.0));

        leilao.propoe(new Lance(steve, 6000.0));
        leilao.propoe(new Lance(vicente, 7000.0));

        leilao.propoe(new Lance(steve, 8000.0));
        leilao.propoe(new Lance(vicente, 9000.0));

        leilao.propoe(new Lance(steve, 10000.0));
        leilao.propoe(new Lance(vicente, 11000.0));

        leilao.propoe(new Lance(steve, 12000.0));

        assertEquals(10, leilao.lances().size());
        assertEquals(11000.0, leilao.lances().get(leilao.lances().size()-1).valor());
    }
    
    @Test
    public void deveDobrarOUltimoLanceDado() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.dobraLance(steveJobs);

        assertEquals(4000, leilao.lances().get(2).valor(), 0.00001);
    }

    @Test
    public void naoDobraLanceSeNaoHouverLance() {
        Leilao leilao = new Leilao("Macbook Pro");
        
        Usuario steve = new Usuario("Steve Jobs");

        leilao.dobraLance(steve);

        assertEquals(0, leilao.lances().size());
	}
}
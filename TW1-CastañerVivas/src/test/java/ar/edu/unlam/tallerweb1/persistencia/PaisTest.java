package ar.edu.unlam.tallerweb1.persistencia;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Continente;
import ar.edu.unlam.tallerweb1.modelo.Pais;

public class PaisTest extends SpringTest{
	
	@Test
	@Transactional
	@Rollback(true)
	public void testPaisesDeHablaInglesa() {
		Pais pais1 = new Pais();
		Pais pais2 = new Pais();
		Pais pais3 = new Pais();
		
		pais1.setNombre("Reino Unido");
		pais1.setIdioma("Ingles");
		getSession().save(pais1);
		
		pais2.setNombre("Irlanda");
		pais2.setIdioma("Ingles");
		getSession().save(pais2);
		
		pais3.setNombre("España");
		pais3.setIdioma("Español");
		getSession().save(pais3);
		
		List<Pais> resultado = getSession().createCriteria(Pais.class)
				.add(Restrictions.like("idioma", "Ingles"))
				.list();
		
		assertThat(resultado).hasSize(2);
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testBusquedaPaisesEuropeos() {
		Pais pais1 = new Pais();
		Pais pais2 = new Pais();
		Pais pais3 = new Pais();
		Continente cont = new Continente();
		cont.setNombre("Europa");
		getSession().save(cont);
		
		pais1.setNombre("Albania");
		pais1.setContinente(cont);
		getSession().save(pais1);
		
		pais2.setNombre("Belgica");
		pais2.setContinente(cont);
		getSession().save(pais2);
		
		pais3.setNombre("Italia");
		pais3.setContinente(cont);
		getSession().save(pais3);
		
		List<Pais> resultado = getSession().createCriteria(Pais.class)
				.createAlias("continente", "ContinenteBuscado")
				.add(Restrictions.like("ContinenteBuscado.nombre", "Europa"))
				.list();
		assertThat(resultado).hasSize(3);
		
	}

}

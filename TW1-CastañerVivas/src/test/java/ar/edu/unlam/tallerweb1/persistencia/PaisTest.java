package ar.edu.unlam.tallerweb1.persistencia;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Ciudad;
import ar.edu.unlam.tallerweb1.modelo.Continente;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import ar.edu.unlam.tallerweb1.modelo.Ubicacion;

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
	
	@Test
	@Transactional
	@Rollback(true)
	public void testBusquedaPaisesAlNorteDelTropico() {
		Pais pais1 = new Pais();
		Pais pais2 = new Pais();
		Pais pais3 = new Pais();
		
		Ubicacion ubic1 = new Ubicacion();
		Ubicacion ubic2 = new Ubicacion();
		Ubicacion ubic3 = new Ubicacion();
		
		Ciudad ciu1 = new Ciudad();
		Ciudad ciu2 = new Ciudad();
		Ciudad ciu3 = new Ciudad();
		
		//Tropico de cancer 23°26'13"
		ubic1.setLatitud(27.56);
		ubic2.setLatitud(45.25);
		ubic3.setLatitud(22.12);
		
		ciu1.setNombre("Mexico");
		ciu1.setUbicacionGeografica(ubic1);
		pais1.setCapital(ciu1);
		
		ciu2.setNombre("Seattle");
		ciu2.setUbicacionGeografica(ubic2);
		pais2.setCapital(ciu2);
		
		ciu3.setNombre("Jujuy");
		ciu3.setUbicacionGeografica(ubic3);
		pais3.setCapital(ciu3);
		
		getSession().save(pais1);
		getSession().save(pais2);
		getSession().save(pais3);
		
		List<Pais> paisesAlNorte = getSession().createCriteria(Pais.class)
				.createAlias("capital", "capBusqueda")
				.createAlias("capBusqueda.ubicacionGeografica", "ubicacionBusqueda")
				.add(Restrictions.gt("ubicacionBusqueda.latitud", 23.16))
				.list();
		for(Pais resultado: paisesAlNorte) {
			assertThat(resultado.getCapital().getUbicacionGeografica().getLatitud()).isGreaterThan(23.16);
		}
		
		
	}
}

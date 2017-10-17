package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sun.org.glassfish.gmbal.ManagedObjectManager.RegistrationDebugLevel;

import br.com.caelum.livraria.interceptador.LogInterceptador;
import br.com.caelum.livraria.modelo.Autor;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER) //container é opcional
//@Interceptors({LogInterceptador.class})
public class AutorDao {

	@PersistenceContext
	private EntityManager manager;
	
	@PostConstruct
	void aposCriacao() {
		System.out.println("AutorDao foi criado");
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY) //require é opcional
	public void salva(Autor autor) {
		
		System.out.println("Salvando Autor " + autor.getNome());
		
//		try {
//			Thread.sleep(20000); //20s
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} 
		
		manager.persist(autor);
		
	
		System.out.println("Salvou Autor " + autor.getNome());
	
		//chamada ao service externo que gera um erro
		
		//throw new RuntimeException("Serviço externo deu erro!");
	
	}
	
	public List<Autor> todosAutores() {
		return manager.createQuery("select a from Autor a", Autor.class).getResultList();
	}

	public Autor buscaPelaId(Integer autorId) {
		Autor autor = manager.find(Autor.class, autorId);
		return autor;
	}
	
}

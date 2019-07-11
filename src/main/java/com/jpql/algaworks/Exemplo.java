package com.jpql.algaworks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.jpql.algaworks.model.Cliente;

public class Exemplo {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Clientes-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//		Cliente cliente = entityManager.find(Cliente.class, 1);
//		System.out.println(cliente.getNome());
		Cliente c = new Cliente();
		//c.setId(1);
		c.setNome("Autopecas estrada");
		entityManager.getTransaction().begin();
		entityManager.persist(c);
		entityManager.getTransaction().commit();
		
		
		entityManager.close();
		entityManagerFactory.close();
	}

}

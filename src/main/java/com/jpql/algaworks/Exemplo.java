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
// Inserte
//		Cliente cliente = entityManager.find(Cliente.class, 1);
//		Cliente c = new Cliente();
//		c.setId(1);
//		c.setNome("Autopecas estrada");
//		entityManager.getTransaction().begin();
//		entityManager.persist(c);
//		entityManager.getTransaction().commit();

        
//      Delete  
//		Cliente c = entityManager.find(Cliente.class, 2);
//		entityManager.getTransaction().begin();
//		entityManager.remove(c);
//		entityManager.getTransaction().commit();
        
        //Update
//        Cliente c = entityManager.find(Cliente.class, 3);
//        entityManager.getTransaction().begin();
//        c.setNome(c.getNome() + " Alterado");
//        entityManager.getTransaction().commit();
//        
//		entityManager.close();
//		entityManagerFactory.close();
        
        Cliente c = new Cliente();
        c.setId(3);
        c.setNome("Construtora alterado");
        
        
        
        entityManager.getTransaction().begin();
        entityManager.merge(c);
        entityManager.getTransaction().commit();
        
		entityManager.close();
		entityManagerFactory.close();
        
	}

}

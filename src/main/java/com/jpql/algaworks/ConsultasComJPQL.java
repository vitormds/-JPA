package com.jpql.algaworks;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jpql.algaworks.sistemausuario.Configuracao;
import com.jpql.algaworks.sistemausuario.Dominio;
import com.jpql.algaworks.sistemausuario.Usuario;
import com.jpql.algaworks.sistemausuario.dto.UsuarioDTO;

public class ConsultasComJPQL {

    public static void main(String[] args) {
       EntityManagerFactory entityManagerFactory = Persistence
               .createEntityManagerFactory("Usuarios-PU");
       EntityManager entityManager = entityManagerFactory.createEntityManager();

//       primeirasConsultas(entityManager);
//       esconhendoRetorno(entityManager);
//       fazendoProjecoes(entityManager);
//       passandoParametros(entityManager);
//       fazendoJoins(entityManager);
//       fazendoLeftJoin(entityManager);
//       carregamentoComJoinFetch(entityManager);
//       filtrandoRegistros(entityManager);
//       ultilizandoOperadoresLogicos(entityManager);
//       ultilizandoOperadorIn(entityManager);
//          ordenandoResultados(entityManager);
       		paginacaoResultados(entityManager);
       
          
         entityManager.close();
         entityManagerFactory.close();
   }
    
    
    
    private static void paginacaoResultados(EntityManager entityManager) {
    	
    		//String jpql = "select u from Usuario u where u.dominio.nome like :nomeDominio order by u.dominio.nome";
        	String jpql = "select u from Usuario u order by u.nome";
    		TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
    				.setFirstResult(4)
    				.setMaxResults(2);
    		List<Usuario> lista = typedQuery.getResultList();
    		lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    		
    	}
    private static void ordenandoResultados(EntityManager entityManager) {	
		//String jpql = "select u from Usuario u where u.dominio.nome like :nomeDominio order by u.dominio.nome";
    	String jpql = "select u from Usuario u order by u.nome";
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();
		lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
		
	}
    
    private static void ultilizandoOperadorIn (EntityManager entityManager) {
		String jpql = "select u from Usuario u where u.id  in (:ids)";
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class).setParameter("ids", Arrays.asList(1,2));
		List<Usuario> lista = typedQuery.getResultList();
		lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
		
	}
    private static void ultilizandoOperadoresLogicos(EntityManager entityManager) {
    	
    			String jpql = "select u from Usuario u where (u.ultimoAcesso > :ontem and u.ultimoAcesso < :hoje) "
    					+ "or u.ultimoAcesso is null";
    			TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
    	    	.setParameter("ontem", LocalDateTime.now().minusDays(1))
    	    	.setParameter("hoje", LocalDateTime.now());
    	    	List<Usuario> lista = typedQuery.getResultList();
    	    	lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
		
	}
	private static void filtrandoRegistros(EntityManager entityManager) {
		// Like, Is null, is empty, between, >, <, >=, <=, =, <>
    	//is null = select u from Usuario u where u.senha is null
    	// is empty = select d from Dominio d where u.usuarios is empty
    	// like =String jpql = "select u from Usuario u where u.nome like :nomeUsuario";
       	// between = 
    	
    	String jpql = "select u from Usuario u where u.ultimoAcesso between :ontem and :hoje";
    	TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class)
    	.setParameter("ontem", LocalDateTime.now().minusDays(1))
    	.setParameter("hoje", LocalDateTime.now());
    	List<Usuario> lista = typedQuery.getResultList();
    	lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
    	
		
	}
	// para não trazer tudo usar fetch
	private static void carregamentoComJoinFetch(EntityManager entityManager) {
		String jpql = "select u from Usuario u join fetch u.configuracao join fetch u.dominio d";
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();
		lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
		
	}

	private static void fazendoLeftJoin(EntityManager entityManager) {
		String jpql = "select u, c from Usuario u left join u.configuracao c";
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		lista.forEach(arr -> {
			// arr[0] == Usuario
			// arr[1] == Configuracao
			String out = ((Usuario) arr[0]).getNome();
			if(arr[1] == null) {
				out += ", null";
			}else {
				out += ", "+((Configuracao) arr[1]).getId();
			}
			System.out.println(out);
			
		}
		 		
				
				
		);
		
	}

	private static void fazendoJoins(EntityManager entityManager) {	
		 String jpql = "select u from Usuario u join u.dominio d where d.id =1";
		 TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
		 List<Usuario> lista = typedQuery.getResultList();
		 lista.forEach(u -> System.out.println(u.getId() +", "+ u.getNome()));
		
		 
	}

	private static void passandoParametros(EntityManager entityManager) {
		String jpql = "select u from Usuario u where u.id = :idUsuario";
		TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class)
		.setParameter("idUsuario", 1);
		Usuario u = query.getSingleResult();
		System.out.println(u.getId() + ", " + u.getNome());
 		
		
		String jpqlLogin = "select u from Usuario u where u.login = :idLogin";
		TypedQuery<Usuario> queryLogin = entityManager.createQuery(jpqlLogin, Usuario.class)
		.setParameter("idLogin", "ria");
		Usuario uLogin = queryLogin.getSingleResult();
		System.out.println(uLogin.getId() + ", " + uLogin.getNome());
	}

	private static void fazendoProjecoes(EntityManager entityManager) {
		
		 String jpqlArr = "select id, login, nome from Usuario";
		 TypedQuery<Object[]> queryArr = entityManager.createQuery(jpqlArr, Object[].class);
		 List<Object[]> listArr = queryArr.getResultList();
		 listArr.forEach(arr -> System.out.println(String.format("%s, %s, %s", arr)));
		 
		 
		 String jpqlDto = "select new com.jpql.algaworks.sistemausuario.dto.UsuarioDTO(id, login, nome) from Usuario";
		 TypedQuery<UsuarioDTO> typedQueryDto = entityManager.createQuery(jpqlDto, UsuarioDTO.class);
		 List<UsuarioDTO> listDto =typedQueryDto.getResultList();
		 listDto.forEach(u -> System.out.println("DTO: "+u.getId() + ", "+u.getLogin()+", " + u.getNome()));
		
	}

	private static void esconhendoRetorno(EntityManager entityManager) {
		
		String jpql = "select u.dominio from Usuario u where u.id =1";
		TypedQuery<Dominio> typedQuery = entityManager.createQuery(jpql, Dominio.class);
		Dominio dominio = typedQuery.getSingleResult();
		System.out.println(dominio.getId() + ", " + dominio.getNome());
		
		
		String jpqlNomes = "select u.nome from Usuario u";
		TypedQuery<String> typedQueryNomes = entityManager.createQuery(jpqlNomes, String.class);
		List<String> lista = typedQueryNomes.getResultList();
		lista.forEach(nome -> System.out.println(nome));
		
	}

	private static void primeirasConsultas(EntityManager entityManager) {
		
		

		String jpql = "select u from Usuario u";
		TypedQuery<Usuario> typedQuery = entityManager.createQuery(jpql, Usuario.class);
		List<Usuario> lista = typedQuery.getResultList();
		lista.forEach(u -> System.out.println(u.getId() + ", " + u.getNome()));
		
		String jpqlSingle = "select u from Usuario u where u.id = 1";
		TypedQuery<Usuario> typedQuerySingle = entityManager.createQuery(jpqlSingle, Usuario.class);
		Usuario u= typedQuerySingle.getSingleResult();
		System.out.println(u.getId()+ ", " + u.getNome());
		
		
		//Query antiga
		String jpqlSing = "select u from Usuario u where u.id = 1";
		Query query = entityManager.createQuery(jpqlSing);
		Usuario usuario = (Usuario) query.getSingleResult();
		System.out.println(usuario.getId()+ ", " + usuario .getNome());
		
	}
}
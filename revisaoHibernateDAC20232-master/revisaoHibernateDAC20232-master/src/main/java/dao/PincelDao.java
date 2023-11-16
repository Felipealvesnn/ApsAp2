package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Pincel;
import util.JPAUtil;

public class PincelDao {
	public static void salvar(Pincel pincel) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(pincel);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void editar(Pincel pincel) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.merge(pincel);
		em.getTransaction().commit();
		em.close();
	}
	
	public static void excluir(Pincel pincel) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		pincel = em.find(Pincel.class, pincel.getId());
		em.remove(pincel);
		em.getTransaction().commit();
		em.close();
	}
	
	public static Pincel acharPorId(Integer id) {
		EntityManager em = JPAUtil.criarEntityManager();
		Pincel p = em.find(Pincel.class, id);
		em.close();
		return p;
	}
	
	public static List<Pincel> listar(){
		EntityManager em = JPAUtil.criarEntityManager();
		Query query = em.createQuery("select p from Pincel p");
		List<Pincel> resutado = query.getResultList();
		em.close();
		return resutado;
	}
}

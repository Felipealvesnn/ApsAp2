package dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidades.Jogo;
import util.JPAUtil;

public class JogoDAO {

	private EntityManager entityManager;

    public JogoDAO() {
        this.entityManager = JPAUtil.criarEntityManager();
    }

    public void salvar(Jogo jogo) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(jogo);
            transaction.commit();
            
          
            
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void editar(Jogo jogo) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void excluir(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Jogo jogo = entityManager.find(Jogo.class, id);
            if (jogo != null) {
                entityManager.remove(jogo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Jogo> listar() {
        Query query = entityManager.createQuery("SELECT j FROM Jogo j", Jogo.class);
        return query.getResultList();
    }
}

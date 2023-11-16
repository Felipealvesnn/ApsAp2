package dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import entidades.Jogo;

import java.util.List;

public class JogoDAO {

    private EntityManager entityManager;

    public JogoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void salvarJogo(Jogo jogo) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void editarJogo(Jogo jogo) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void excluirJogo(int jogoId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Jogo jogo = entityManager.find(Jogo.class, jogoId);
            entityManager.remove(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Jogo> listarJogos() {
        TypedQuery<Jogo> query = entityManager.createQuery("SELECT j FROM Jogo j", Jogo.class);
        return query.getResultList();
    }
}

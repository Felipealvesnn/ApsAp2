package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entidades.Jogo;
import util.JPAUtil;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JogoDAO {

    public void salvarJogo(Jogo jogo) {
        EntityManager em = JPAUtil.criarEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public void atualizarJogo(Jogo jogo) {
        EntityManager em = JPAUtil.criarEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


    public void editarJogo(Jogo jogo) {
        EntityManager em = JPAUtil.criarEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void excluirJogo(Jogo jogo) {
        EntityManager em = JPAUtil.criarEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            jogo = em.find(Jogo.class, jogo.getId());
            em.remove(jogo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Jogo> listarJogos() {
    	EntityManager em = JPAUtil.criarEntityManager();
        try {
            TypedQuery<Jogo> query = em.createQuery("SELECT j FROM Jogo j", Jogo.class);
            List<Jogo> jogos = query.getResultList();

            // Se a lista estiver vazia, cria jogos aleatórios
            if (jogos.isEmpty()) {
                jogos = criarJogosAleatorios(em);
            }

            return jogos;
        } finally {
            em.close();
        }
    }

    private List<Jogo> criarJogosAleatorios(EntityManager em) {
        Random random = new Random();
        int numeroJogosAleatorios = 5; // Defina o número desejado de jogos aleatórios

        return IntStream.range(0, numeroJogosAleatorios)
                .mapToObj(i -> criarJogoAleatorio(random))
                .peek(em::persist)
                .collect(Collectors.toList());
    }

    private Jogo criarJogoAleatorio(Random random) {
        Date dataPartida = new Date();
        Date dataCadastro = new Date();
        String time1 = "TimeA";
        String time2 = "TimeB";
        int golsTime1 = random.nextInt(5); // Gols aleatórios entre 0 e 4
        int golsTime2 = random.nextInt(5);

        return new Jogo(dataPartida, dataCadastro, time1, time2, golsTime1, golsTime2);
    }
}

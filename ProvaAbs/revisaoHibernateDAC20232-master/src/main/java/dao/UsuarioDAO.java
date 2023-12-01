package dao;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import entidades.Usuario;
import util.JPAUtil;

public class UsuarioDAO {

    private EntityManager entityManager;

    public UsuarioDAO() {
        this.entityManager = JPAUtil.criarEntityManager();
    }

    public void salvar(Usuario usuario) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(usuario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Usuario buscarPorId(Long id) {
        try {
            return entityManager.find(Usuario.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public void editar(Usuario usuario) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(usuario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Usuario autenticarUsuario(String nomeUsuario, String senha) {
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.nome = :nomeUsuario", Usuario.class);
            query.setParameter("nomeUsuario", nomeUsuario);
            Usuario usuario = (Usuario) query.getSingleResult();

            // Verificar se a senha corresponde
            if (usuario != null && Objects.equals(usuario.getSenha(), senha)) {
                return usuario; // Autenticação bem-sucedida
            } else {
                return null; // Usuário não encontrado ou senha incorreta
            }
        } catch (NoResultException e) {
            return null; // Usuário não encontrado
        }
    }
    
    public void excluir(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Usuario usuario = entityManager.find(Usuario.class, id);
            if (usuario != null) {
                entityManager.remove(usuario);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Usuario> listar() {
        Query query = entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class);
        return query.getResultList();
    }

    public Usuario buscarPorNomeUsuario(String nomeUsuario) {
        try {
            Query query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :nomeUsuario", Usuario.class);
            query.setParameter("nomeUsuario", nomeUsuario);
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

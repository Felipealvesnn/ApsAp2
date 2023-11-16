/*
 * package dao;
 * 
 * import javax.persistence.EntityManager; import
 * javax.persistence.EntityTransaction; import javax.persistence.Query; import
 * javax.persistence.TypedQuery;
 * 
 * import entidades.Jogo; import util.JPAUtil;
 * 
 * import java.util.Date; import java.util.List; import java.util.Random; import
 * java.util.stream.Collectors; import java.util.stream.IntStream;
 * 
 * public class JogoDAO {
 * 
 * public void salvarJogo(Jogo jogo) { EntityManager em =
 * JPAUtil.criarEntityManager(); EntityTransaction transaction =
 * em.getTransaction();
 * 
 * try { transaction.begin(); em.persist(jogo); transaction.commit(); } catch
 * (Exception e) { if (transaction != null && transaction.isActive()) {
 * transaction.rollback(); } e.printStackTrace(); } finally { em.close(); } }
 * public void atualizarJogo(Jogo jogo) { EntityManager em =
 * JPAUtil.criarEntityManager(); EntityTransaction transaction =
 * em.getTransaction();
 * 
 * try { transaction.begin(); em.merge(jogo); transaction.commit(); } catch
 * (Exception e) { if (transaction != null && transaction.isActive()) {
 * transaction.rollback(); } e.printStackTrace(); } finally { em.close(); } }
 * 
 * 
 * public void editarJogo(Jogo jogo) { EntityManager em =
 * JPAUtil.criarEntityManager(); EntityTransaction transaction =
 * em.getTransaction();
 * 
 * try { transaction.begin(); em.merge(jogo); transaction.commit(); } catch
 * (Exception e) { if (transaction != null && transaction.isActive()) {
 * transaction.rollback(); } e.printStackTrace(); } finally { em.close(); } }
 * 
 * public void excluirJogo(Jogo jogo) { EntityManager em =
 * JPAUtil.criarEntityManager(); EntityTransaction transaction =
 * em.getTransaction();
 * 
 * try { transaction.begin(); jogo = em.find(Jogo.class, jogo.getId());
 * em.remove(jogo); transaction.commit(); } catch (Exception e) { if
 * (transaction != null && transaction.isActive()) { transaction.rollback(); }
 * e.printStackTrace(); } finally { em.close(); } }
 * 
 * public List<Jogo> listarJogos() { EntityManager em =
 * JPAUtil.criarEntityManager(); try { TypedQuery<Jogo> query =
 * em.createQuery("SELECT j FROM Jogo j", Jogo.class); List<Jogo> jogos =
 * query.getResultList();
 * 
 * // Se a lista estiver vazia, cria jogos aleatórios if (jogos.isEmpty()) {
 * jogos = criarJogosAleatorios(em); }
 * 
 * return jogos; } finally { em.close(); } }
 * 
 * private List<Jogo> criarJogosAleatorios(EntityManager em) { Random random =
 * new Random(); int numeroJogosAleatorios = 5; // Defina o número desejado de
 * jogos aleatórios
 * 
 * return IntStream.range(0, numeroJogosAleatorios) .mapToObj(i ->
 * criarJogoAleatorio(random)) .peek(em::persist) .collect(Collectors.toList());
 * }
 * 
 * private Jogo criarJogoAleatorio(Random random) { Date dataPartida = new
 * Date(); Date dataCadastro = new Date(); String time1 = "TimeA"; String time2
 * = "TimeB"; int golsTime1 = random.nextInt(5); // Gols aleatórios entre 0 e 4
 * int golsTime2 = random.nextInt(5);
 * 
 * return new Jogo(dataPartida, dataCadastro, time1, time2, golsTime1,
 * golsTime2); } }
 */

package dao;

import entidades.Jogo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JogoDAO {

    private static List<Jogo> jogosEmMemoria = new ArrayList<>();
    private Jogo jogoSelecionado; // Adicione esta variável para armazenar temporariamente o jogo selecionado

    public void salvarJogo(Jogo jogo) {
        jogosEmMemoria.add(jogo);
    }

    public void atualizarJogo(Jogo jogo) {
        for (int i = 0; i < jogosEmMemoria.size(); i++) {
            Jogo jogoExistente = jogosEmMemoria.get(i);
            if (jogoExistente.getId().equals(jogo.getId())) {
                jogosEmMemoria.set(i, jogo);
                break;
            }
        }
    }

    public void editarJogo(Jogo jogo) {
        // Armazena temporariamente o jogo selecionado para uso posterior no método salvarEdicao()
        this.jogoSelecionado = jogo;
    }

    public void excluirJogo(Jogo jogo) {
        jogosEmMemoria.removeIf(j -> j.getId().equals(jogo.getId()));
    }

    public List<Jogo> listarJogos() {
        if (jogosEmMemoria.isEmpty()) {
            jogosEmMemoria = criarJogosAleatorios();
        }
        return new ArrayList<>(jogosEmMemoria);
    }

    public void salvarEdicao() {
        // Implemente a lógica para salvar as alterações feitas no método editarJogo
        // Certifique-se de chamar este método após realizar as edições necessárias
        if (jogoSelecionado != null) {
            // Realize as alterações no jogoSelecionado conforme necessário
            // Por exemplo, você pode exibir um formulário de edição na interface do usuário e
            // permitir que o usuário faça alterações nos campos do jogoSelecionado
            // Após as alterações, chame o método atualizarJogo para salvar as alterações na lista
            atualizarJogo(jogoSelecionado);

            // Limpe a variável de jogoSelecionado após salvar as alterações
            jogoSelecionado = null;
        }
    }

    private List<Jogo> criarJogosAleatorios() {
        Random random = new Random();
        int numeroJogosAleatorios = 5;

        return IntStream.range(0, numeroJogosAleatorios)
                .mapToObj(i -> criarJogoAleatorio(random))
                .collect(Collectors.toList());
    }

    private Jogo criarJogoAleatorio(Random random) {
        Date dataPartida = new Date();
        Date dataCadastro = new Date();
        String time1 = "TimeA";
        String time2 = "TimeB";
        int golsTime1 = random.nextInt(5);
        int golsTime2 = random.nextInt(5);

        return new Jogo(dataPartida, dataCadastro, time1, time2, golsTime1, golsTime2);
    }
}

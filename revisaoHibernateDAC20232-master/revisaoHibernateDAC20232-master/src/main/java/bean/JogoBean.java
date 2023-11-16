package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import dao.JogoDAO;
import entidades.Jogo;

import java.util.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class JogoBean {

    private String time1;
    private String time2;
    private Integer golsTime1;
    private Integer golsTime2;
    private Jogo jogoSelecionado;
    private List<Jogo> listaJogos;

    public String salvarJogo() {
        if (time1.equals(time2)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não é permitido salvar um jogo com times iguais."));
            return null;
        }

        Jogo jogo = new Jogo(new Date(), new Date(), time1, time2, golsTime1, golsTime2);
        JogoDAO jogoDAO = new JogoDAO();
        jogoDAO.salvarJogo(jogo);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo salvo com sucesso!"));

        // Atualiza a lista de jogos após salvar
        listaJogos = jogoDAO.listarJogos();

        // Limpa os campos após salvar
        limparCampos();

        return null;
    }

    public void editarJogo(Jogo jogo) {
        try {
            this.jogoSelecionado = (Jogo) jogo.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void salvarEdicao() {
        if (jogoSelecionado != null) {
            JogoDAO jogoDAO = new JogoDAO();
            
            // Lógica para salvar as alterações no jogoSelecionado
            try {
                jogoDAO.atualizarJogo(jogoSelecionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Edição salva com sucesso!"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar edição do jogo."));
                e.printStackTrace(); // Considere logar o erro de alguma forma mais apropriada para seu aplicativo
            }

            // Certifique-se de atualizar a lista de jogos após salvar as alterações
            listaJogos = jogoDAO.listarJogos();

            // Limpa a variável de jogoSelecionado após salvar
            jogoSelecionado = null;
        }
    }


    public List<Jogo> getListaJogos() {
        if (listaJogos == null) {
            // Se a lista ainda não foi carregada, carregue-a agora
            JogoDAO jogoDAO = new JogoDAO();
            listaJogos = jogoDAO.listarJogos();
        }
        return listaJogos;
    }

    private void limparCampos() {
        time1 = null;
        time2 = null;
        golsTime1 = null;
        golsTime2 = null;
    }

    // Outros métodos conforme necessário
}

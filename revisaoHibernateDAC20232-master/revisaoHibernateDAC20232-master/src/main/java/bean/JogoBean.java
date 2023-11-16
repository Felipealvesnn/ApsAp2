package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.JogoDAO;
import entidades.Jogo;

import java.util.Date;
import java.util.List;

@ViewScoped
@ManagedBean
public class JogoBean {

    private String time1;
    private String time2;
    private Integer golsTime1;
    private Integer golsTime2;
    private  Jogo jogoSelecionado;
    private List<Jogo> listaJogos;
    private String resumoTime;
    private int resumoPontuacao;

    


    public void calcularResumo() {
        int totalPontuacao = 0;
        int totalVitorias = 0;
        int totalEmpates = 0;
        int totalDerrotas = 0;
        int totalGolsMarcados = 0;
        int totalGolsSofridos = 0;

        for (Jogo jogo : listaJogos) {
            totalGolsMarcados += jogo.getGolsTime1() + jogo.getGolsTime2();
            totalGolsSofridos += jogo.getGolsTime1() + jogo.getGolsTime2();

            if (jogo.getGolsTime1() > jogo.getGolsTime2()) {
                totalPontuacao += 3; // Vitória
                totalVitorias++;
            } else if (jogo.getGolsTime1() < jogo.getGolsTime2()) {
                totalDerrotas++;
            } else {
                totalPontuacao += 1; // Empate
                totalEmpates++;
            }
        }

        resumoPontuacao = totalPontuacao;
        resumoTime = "Resumo Geral";
        // Atribua outros campos conforme necessário
    }
    public void exibirResumo() {
        calcularResumo();
    }


    
   
    public void salvarJogo() {
        if (time1.equals(time2)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não é permitido salvar um jogo com times iguais."));
          //  return null;
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

      //  return null;
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

    public void excluirJogo(Jogo jogo) {
        try {
            JogoDAO jogoDAO = new JogoDAO();
            jogoDAO.excluirJogo(jogo);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo excluído com sucesso!"));

            // Atualiza a lista de jogos após excluir
            listaJogos = jogoDAO.listarJogos();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao excluir o jogo."));
            e.printStackTrace(); // Considere logar o erro de alguma forma mais apropriada para seu aplicativo
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
  
    public Jogo getJogoSelecionado() {
        return jogoSelecionado;
    }

    public void setJogoSelecionado(Jogo jogoSelecionado) {
        this.jogoSelecionado = jogoSelecionado;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public Integer getGolsTime1() {
        return golsTime1;
    }

    public void setGolsTime1(Integer golsTime1) {
        this.golsTime1 = golsTime1;
    }

    public Integer getGolsTime2() {
        return golsTime2;
    }

    public void setGolsTime2(Integer golsTime2) {
        this.golsTime2 = golsTime2;
    }
    public String getResumoTime() {
        return resumoTime;
    }

    public int getResumoPontuacao() {
        return resumoPontuacao;
    }


    // Outros métodos conforme necessário
}


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
    private Integer golsTime1;i
    private Integer golsTime2;

    // Getters e setters para as variáveis

    public String salvarJogo() {
        if (time1.equals(time2)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não é permitido salvar um jogo com times iguais."));
            return null; // Permanece na mesma página
        }

        Jogo jogo = new Jogo(new Date(), new Date(), time1, time2, golsTime1, golsTime2);
        JogoDAO jogoDAO = new JogoDAO();
        jogoDAO.salvarJogo(jogo);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo salvo com sucesso!"));

        return null; // Permanece na mesma página, você pode redirecionar se necessário
    }
    
    public List<Jogo> getListaJogos() {
        JogoDAO jogoDAO = new JogoDAO();
        return jogoDAO.listarJogos();
    }
}

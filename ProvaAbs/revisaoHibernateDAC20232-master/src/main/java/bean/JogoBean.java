package bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.JogoDAO;
import entidades.Jogo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@ManagedBean
@ViewScoped
public class JogoBean {

    private Jogo jogo = new Jogo();
    private Jogo JogoParaEditarOuexcluir;
    JogoDAO jogoDAO ;
    private List<Jogo> listaJogos;
    private String listaDePares;
    private String ConcatenatedValues;

   
    @PostConstruct
    public void init() {
      
    	jogoDAO = new JogoDAO();
    
        listaJogos = jogoDAO.listar();
    
 
 
    }
    public String SetarPares(String Pares) {
    	  this.listaDePares = Pares;
    	  return  this.listaDePares;
    }
    
    public String GetPares() {
      return this.listaDePares;
    }
    public Jogo GetJogoParaEditarOuexcluir() {
    	
    	return JogoParaEditarOuexcluir;
    }
    
    public void  setarJogoEdits(Jogo jogo) {
    	this.JogoParaEditarOuexcluir = jogo;
    }
    public void excluirJogo() {
    	jogoDAO.excluir(JogoParaEditarOuexcluir.getId());
    }
    

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    // Método para salvar o jogo
    public String salvarJogo() {
        // Lógica para preencher automaticamente a dataCriacao e variáveis v1 até v10
        jogo.setDataCriacao(new Date());
        preencherVariaveisAleatorias();
        
        JogoDAO conext = new JogoDAO();
        conext.salvar(jogo);
        
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Jogo salvo com sucesso!"));
        
        return "paginaF?faces-redirect=true";
    }
    
   

 // Método para preencher variáveis com valores aleatórios
    private void preencherVariaveisAleatorias() {
        Random random = new Random();
        
        for (int i = 1; i <= 10; i++) {
            // Use reflection para obter o método setVX dinamicamente
            try {
                jogo.getClass().getMethod("setV" + i, Integer.class).invoke(jogo, random.nextInt(30) + 1);
            } catch (Exception e) {
                e.printStackTrace(); // Lide com exceções apropriadamente
            }
        }
    }
  
    public List<Jogo> getListaJogos() {
    	listaJogos = new ArrayList<>();
    	listaJogos = jogoDAO.listar();
        return listaJogos; 
    }

    public void salvarEdicao() {
     
        
        jogoDAO.editar(this.JogoParaEditarOuexcluir);
    }
    
    
}

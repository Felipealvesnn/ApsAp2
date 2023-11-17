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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@ViewScoped
@ManagedBean
public class JogoBean {

    private String time1;
    private String time2;
    private Integer golsTime1;
    private Integer golsTime2;
    private  Jogo jogoSelecionado;
    private List<Jogo> listaJogos;
    private List<String> listaTimes;
    private List<Jogo> listaJogosFiltrados;
    
    
    private int pontuacao;
    private int numeroVitorias;
    private int numeroDerrotas;
    private int numeroEmpates;
    private int golsMarcados;
    private int golsSofridos;
    private int saldoGols;

    // Getters e Setters para os novos atributos

    
  
    @PostConstruct
    public void init() {
        // Inicialize a lista de times a partir da lista de jogos
        JogoDAO jogoDAO = new JogoDAO();
        listaJogos = jogoDAO.listarJogos();
        setListaTimes(extrairNomesTimes(listaJogos));
    }
    
    private List<String> extrairNomesTimes(List<Jogo> listaJogos) {
        Set<String> nomesTimes = new HashSet<String>();

        for (Jogo jogo : listaJogos) {
            nomesTimes.add(jogo.getTime1());
            nomesTimes.add(jogo.getTime2());
        }

        return new ArrayList<>(nomesTimes);
    }

    
    private static AtomicInteger idGenerator = new AtomicInteger(1);
   
    public void salvarJogo() {
        if (time1.equals(time2)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não é permitido salvar um jogo com times iguais."));
          //  return null;
        }

        int novoId = idGenerator.getAndIncrement();
        Jogo jogo = new Jogo(novoId, new Date(), new Date(), time1, time2, golsTime1, golsTime2);
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

    
    public void exibirResumo(Jogo jogo) {
        jogoSelecionado = jogo;

        // Calcule as informações necessárias
        calcularInformacoesTime(jogoSelecionado, listaJogos);
    }

    private void calcularInformacoesTime(Jogo jogo, List<Jogo> listaJogos) {
        // Inicialize as variáveis para as informações
        int pontuacao = 0;
        int vitorias = 0;
        int derrotas = 0;
        int empates = 0;
        int golsMarcados = 0;
        int golsSofridos = 0;

        // Itera sobre a lista de jogos para calcular as informações do time
        for (Jogo j : listaJogos) {
            // Verifica se o time1 é igual ao time do jogo selecionado
            if (j.getTime1().equals(jogo.getTime1())) {
                golsMarcados += j.getGolsTime1();
                golsSofridos += j.getGolsTime2();

                if (j.getGolsTime1() > j.getGolsTime2()) {
                    pontuacao += 3; // Vitória
                    vitorias++;
                } else if (j.getGolsTime1() < j.getGolsTime2()) {
                    derrotas++;
                } else {
                    pontuacao += 1; // Empate
                    empates++;
                }
            }
            // Verifica se o time2 é igual ao time do jogo selecionado
            else if (j.getTime2().equals(jogo.getTime1())) {
                golsMarcados += j.getGolsTime2();
                golsSofridos += j.getGolsTime1();

                if (j.getGolsTime2() > j.getGolsTime1()) {
                    pontuacao += 3; // Vitória
                    vitorias++;
                } else if (j.getGolsTime2() < j.getGolsTime1()) {
                    derrotas++;
                } else {
                    pontuacao += 1; // Empate
                    empates++;
                }
            }
        }

        // Calcule o saldo de gols
        int saldoGols = golsMarcados - golsSofridos;

        // Configure as informações no jogoSelecionado para exibição no diálogo
        setPontuacao(pontuacao);
        setNumeroVitorias(vitorias);
        setNumeroDerrotas(derrotas);
        setNumeroEmpates(empates);
        setGolsMarcados(golsMarcados);
        setGolsSofridos(golsSofridos);
        setSaldoGols(saldoGols);
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
    
    // resultado jogos
    
    
    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getNumeroVitorias() {
        return numeroVitorias;
    }

    public void setNumeroVitorias(int numeroVitorias) {
        this.numeroVitorias = numeroVitorias;
    }

    public int getNumeroDerrotas() {
        return numeroDerrotas;
    }

    public void setNumeroDerrotas(int numeroDerrotas) {
        this.numeroDerrotas = numeroDerrotas;
    }

    public int getNumeroEmpates() {
        return numeroEmpates;
    }

    public void setNumeroEmpates(int numeroEmpates) {
        this.numeroEmpates = numeroEmpates;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    public void setGolsMarcados(int golsMarcados) {
        this.golsMarcados = golsMarcados;
    }

    public int getGolsSofridos() {
        return golsSofridos;
    }

    public void setGolsSofridos(int golsSofridos) {
        this.golsSofridos = golsSofridos;
    }

    public int getSaldoGols() {
        return saldoGols;
    }

    public void setSaldoGols(int saldoGols) {
        this.saldoGols = saldoGols;
    }

	public List<String> getListaTimes() {
		return listaTimes;
	}

	public void setListaTimes(List<String> listaTimes) {
		this.listaTimes = listaTimes;
	}
    
    
 

    // Outros métodos conforme necessário
}

package bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.JogoDAO;
import entidades.Jogo;

@ViewScoped
@ManagedBean
public class FiltroBean {

    private String timeSelecionado;
    private List<String> listaTimes;
    private List<Jogo> listaJogos;
    private List<Jogo> listaJogosFiltrados;

    // Injete a instância de JogoDAO (ou use CDI para injeção de dependência)

    @PostConstruct
    public void init() {
        // Inicialize a lista de times ao iniciar o bean
    	  JogoDAO jogoDAO = new JogoDAO();
          setListaJogos(jogoDAO.listarJogos());
          setListaTimes(extrairNomesTimes(listaJogos));
    }
   

    public void localizarJogosPorTime() {

    	  listaJogosFiltrados = listaJogos.stream()
    		        .filter(jogo -> jogo.getTime1().equals(timeSelecionado) || jogo.getTime2().equals(timeSelecionado))
    		        .collect(Collectors.toList());
    }

    private List<String> extrairNomesTimes(List<Jogo> listaJogos) {
        Set<String> nomesTimes = new HashSet<>();

        for (Jogo jogo : listaJogos) {
            nomesTimes.add(jogo.getTime1());
            nomesTimes.add(jogo.getTime2());
        }

        return new ArrayList<>(nomesTimes);
    }

	public List<Jogo> getListaJogos() {
		return listaJogos;
	}

	public void setListaJogos(List<Jogo> listaJogos) {
		this.listaJogos = listaJogos;
	}

	public List<String> getListaTimes() {
		return listaTimes;
	}

	public void setListaTimes(List<String> listaTimes) {
		this.listaTimes = listaTimes;
	}

	public String getTimeSelecionado() {
		return timeSelecionado;
	}

	public void setTimeSelecionado(String timeSelecionado) {
		this.timeSelecionado = timeSelecionado;
	}


	public List<Jogo> getListaJogosFiltrados() {
		return listaJogosFiltrados;
	}


	public void setListaJogosFiltrados(List<Jogo> listaJogosFiltrados) {
		this.listaJogosFiltrados = listaJogosFiltrados;
	}

}

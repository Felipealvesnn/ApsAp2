package bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dao.PincelDao;
import entidades.Pincel;

@ManagedBean
public class PincelBean {
	private Pincel pincel = new Pincel();
	private List<Pincel> lista = new ArrayList<Pincel>();
	
	public String salvar() {
		try {
			PincelDao.salvar(pincel);
			pincel = new Pincel();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pincel salvo com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Culpe o programador"));
		}
		
		return null;
	}

	public Pincel getPincel() {
		return pincel;
	}

	public void setPincel(Pincel pincel) {
		this.pincel = pincel;
	}

	public List<Pincel> getLista() {
		if (lista != null) {
			lista = PincelDao.listar();
		}
		return lista;
	}

	public void setLista(List<Pincel> lista) {
		this.lista = lista;
	}
}

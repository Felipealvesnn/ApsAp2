package bean;



import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import entidades.Usuario;

import java.util.List;

@ManagedBean
@ViewScoped
public class UsuarioBean {

    private Usuario usuario = new Usuario();
    private Usuario usuarioParaEditarOuExcluir;
    private UsuarioDAO usuarioDAO;
    private List<Usuario> listaUsuarios;

    @PostConstruct
    public void init() {
        usuarioDAO = new UsuarioDAO();
        listaUsuarios = usuarioDAO.listar();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioParaEditarOuExcluir() {
        return usuarioParaEditarOuExcluir;
    }

    public void setUsuarioParaEditarOuExcluir(Usuario usuario) {
        this.usuarioParaEditarOuExcluir = usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void salvarUsuario() {
        usuarioDAO.salvar(usuario);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário salvo com sucesso!"));
        limparUsuario();
        atualizarListaUsuarios();
    }
    public void salvarEdicao() {
     
        
    	usuarioDAO.editar(this.usuarioParaEditarOuExcluir);
    }
    

    public void editarUsuario() {
        usuarioDAO.editar(usuarioParaEditarOuExcluir);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário editado com sucesso!"));
        limparUsuario();
        atualizarListaUsuarios();
    }

    public void excluirUsuario() {
        usuarioDAO.excluir(usuarioParaEditarOuExcluir.getId());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário excluído com sucesso!"));
        limparUsuario();
        atualizarListaUsuarios();
    }

    private void limparUsuario() {
        usuario = new Usuario();
    }

    private void atualizarListaUsuarios() {
        listaUsuarios = usuarioDAO.listar();
    }
}


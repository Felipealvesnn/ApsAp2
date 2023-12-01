package bean;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

import dao.UsuarioDAO;
import entidades.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable {

    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private List<Usuario> listaUsuarios;

    public UsuarioBean() {
        this.usuarioDAO = new UsuarioDAO();
        this.usuario = new Usuario();
        this.listaUsuarios = usuarioDAO.listar();
    }

    public void cadastrarUsuario() {
        usuarioDAO.salvar(usuario);
        limparUsuario();
        atualizarListaUsuarios();
    }

    public void editarUsuario() {
        usuarioDAO.editar(usuario);
        limparUsuario();
        atualizarListaUsuarios();
    }

    public void excluirUsuario(Long id) {
        usuarioDAO.excluir(id);
        atualizarListaUsuarios();
    }

    public void editarUsuario(Long id) {
        usuario = usuarioDAO.buscarPorId(id);
    }

    private void limparUsuario() {
        usuario = new Usuario();
    }

    private void atualizarListaUsuarios() {
        listaUsuarios = usuarioDAO.listar();
    }

    // Getters e Setters

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
}


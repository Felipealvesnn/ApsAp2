// LoginBean.java
package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.UsuarioDAO;
import entidades.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioAutenticado = usuarioDAO.autenticarUsuario(username, password);

        if (usuarioAutenticado != null) {
            // Login bem-sucedido
            // Você pode armazenar o usuário autenticado na sessão se necessário
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("usuarioLogado", usuarioAutenticado);

            return "opcoes.xhtml?faces-redirect=true";
        } else {
            // Exibir mensagem de erro
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login inválido", "Usuário ou senha incorretos"));

            return null; // Permanecer na mesma página de login
        }
    }
}

package servlets;

import java.io.Serializable;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.DAOUsuarioRepository;
import model.ModelLogin;



public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	
	public Long getUserLogado(javax.servlet.http.HttpServletRequest request) throws Exception {
		
		HttpSession session =   request.getSession();

		String usuarioLogado = (String) session.getAttribute("usuario");

		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado).getId();
	}
	
	//metodo que retorna um objeto para o objeto logado de telefone
	public ModelLogin getUserLogadoObjt(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();

		String usuarioLogado = (String) session.getAttribute("usuario");

		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado);
	}

}

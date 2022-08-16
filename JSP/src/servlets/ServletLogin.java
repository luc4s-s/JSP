package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;

/*O chamando Controller s�o as servlets ou ServletLoginController*/
@WebServlet(urlPatterns = {"/principal/ServletLogin", "/ServletLogin"}) /*Mapeamento de URL que vem da tela*/
public class ServletLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

  
    public ServletLogin() {
    }


    /*Recebe os dados pela url em parametros*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		 String acao = request.getParameter("acao");
		 
		 if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			 request.getSession().invalidate();// invalida a sess�o
			 RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			 redirecionar.forward(request, response);
		 }else {
		  doPost(request, response);
		 }
		 
	}

	
	/*recebe os dados enviados por um formulario*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");
		
		try {
		
				if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
					
					ModelLogin modelLogin = new ModelLogin();
					modelLogin.setLogin(login);
					modelLogin.setSenha(senha);
					
					if (daoLoginRepository.validarAutenticacao(modelLogin)) { /*Simulando login*/
						
						modelLogin = daoUsuarioRepository.consultarUsuarioLogado(login);
						
						request.getSession().setAttribute("usuario", modelLogin.getLogin());
						request.getSession().setAttribute("perfil", modelLogin.getPerfil());
						
						request.getSession().setAttribute("imagemUser", modelLogin.getFotouser());
						
						if (url == null || url.equals("null")) {
							url = "principal/principal.jsp";
						}
						
						RequestDispatcher redirecionar = request.getRequestDispatcher(url);
						redirecionar.forward(request, response);
						
					}else {
						RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
						request.setAttribute("msg", "Informe o login e senha corretamente!");
						redirecionar.forward(request, response);
					}
					
				}else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
					request.setAttribute("msg", "Informe o login e senha corretamente!");
					redirecionar.forward(request, response);
				}
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
	}

}






////os controles so as Servlets ou ServletsLoginControle
//@WebServlet(urlPatterns = { "/principal/ServletLogin", "/ServletLogin" }) // mapeamento de URL que vem da tela
//public class ServletLogin extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
//	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
//
//	public ServletLogin() {
//	}
//
//	// Recebe os dados pela url em parametro
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
//		
//		//tratamento para validar o logout da pagina
//		String acao = request.getParameter("acao");
//		
//		if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
//			request.getSession().invalidate();//invalida a sessao
//			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
//			redirecionar.forward(request, response);//redirecionando para pagina index para fazer o logout
//			
//		}else {
//			doPost(request, response);//para nao da tela branca qundo der enter na URL
//		}
//	}
//
//	// Recebe os dados enviados por um formulario
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		// fazendo a validacao do login e senha
//		String login = request.getParameter("login");
//		String senha = request.getParameter("senha");
//		String url = request.getParameter("url");
//
//		try {
//					if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
//		
//						ModelLogin modelLogin = new ModelLogin();
//						modelLogin.setLogin(login);
//						modelLogin.setSenha(senha);
//		
//						// simulando um login
//						if (daoLoginRepository.validarAutenticacao(modelLogin)) {
//							
//							modelLogin = daoUsuarioRepository.consultarUsuarioLogado(login);
//		
//							// colocando usuario na sesao
//							request.getSession().setAttribute("usuario", modelLogin.getLogin());
//							request.getSession().setAttribute("perfil", modelLogin.getPerfil());
//							request.getSession().setAttribute("imagemUser", modelLogin.getFotouser());//para colocar a imagem do usuario logado 
//		
//							// coloca a tela inicial no sitema
//							if (url == null || url.equals("null")) {
//								url = "principal/principal.jsp";
//							}
//		
//							// fazendo o redirecinamento para a principal
//							// retornando para a paginaprincipal caso o login esenha estaja certo
//							RequestDispatcher redirecionar = request.getRequestDispatcher(url);
//							redirecionar.forward(request, response);
//		
//							// caso nao seja admin e admin retorna para o index.jsp
//						} else {
//							RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
//							request.setAttribute("msg", "Informe o login e a senha corretamente!");
//							redirecionar.forward(request, response);
//						}
//		
//					} else { // retornando para a mesma pagina index.jsp caso o login e senhaesteja errado ounao informado
//						RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
//						request.setAttribute("msg", "Informe o login e a senha corretamente!");
//						redirecionar.forward(request, response);
//					}
//		} catch (Exception e) {
//			e.printStackTrace();//mensagen de erro da tela erro.jsp
//			//jakarta.servlet.RequestDispatcher
//			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
//			request.setAttribute("msg", e.getMessage());//mensagem de erro da tela erro.jsp
//			redirecionar.forward(request, response);
//		}
//	}
//}

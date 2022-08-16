package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import model.ModelTelefone;

@WebServlet("/ServletTelefone")
public class ServletTelefone extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();

	public ServletTelefone() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
		
		//variavel acao para excluir
		String acao = request.getParameter("acao");
		
		//condicao para excluuir
		if(acao != null && !acao.isEmpty() && acao.equals("excluir")) {
			
			String idfone = request.getParameter("id");//pegando telefone como parametro
			
			daoTelefoneRepository.deleteFone(Long.parseLong(idfone));//deletando telefone
			
			String userpai = request.getParameter("userpai");//pegando o usuario pai
			
			//consulto objeto para retorna para a tela 
			ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(userpai));
			
			List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());//consultou para lista o telefoen
			request.setAttribute("modelTelefones", modelTelefones);
			
			request.setAttribute("msg", "Telefone Excluido com Sucesso");
			request.setAttribute("modelLogin", modelLogin);// setando o atributo
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);//retornando a tela para um novo cadastro
		
			return;
		}
		
		
		String iduser = request.getParameter("iduser");

		// se informa o usario valido
		if (iduser != null && !iduser.isEmpty()) {

			
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(iduser));
				
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(modelLogin.getId());//para listando o telefone na tela 
				request.setAttribute("modelTelefones", modelTelefones);
				
				request.setAttribute("modelLogin", modelLogin);// setando o atributo
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);// redirecionando para a pagina de telefone telefone
			
				
		}else { //se nao informa o usuario valido
			//listando todos usuarios
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);// redirecionando para mesma pagina
			
		}
		 
		}catch (Exception e) {
			e.printStackTrace();//imprimir no console qual quer erro
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			String usuario_pai_id = request.getParameter("id");
			String numero = request.getParameter("numero");
			
			//condicao para verificaçao se existe ou nao telefone igual no banco 
			if(!daoTelefoneRepository.existeFone(numero, Long.valueOf(usuario_pai_id))) {
			
				ModelTelefone modelTelefone = new ModelTelefone();
				
				modelTelefone.setNumero(numero);//vem da tela 
				modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultarUsuarioID(Long.parseLong(usuario_pai_id)));
				modelTelefone.setUsuario_cad_id(super.getUserLogadoObjt(request));
				
				daoTelefoneRepository.gravaTelefone(modelTelefone);
				request.setAttribute("msg", "Salvo com Sucesso");//quando for salvo retorna essa mensagem  
	
			} else {// retorno se existir telefoen igual
				request.setAttribute("msg", "Telefone ja existe");
			}
				
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(Long.parseLong(usuario_pai_id));//para listando o telefone na tela 
				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(usuario_pai_id));
				
				request.setAttribute("modelLogin", modelLogin);
				request.setAttribute("modelTelefones", modelTelefones);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);//retornando para a tela de telefone

			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

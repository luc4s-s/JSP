package servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDtoGraficoSalarioUser;
import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletContext;
import model.ModelLogin;
import util.ReportUtil;

@MultipartConfig
@WebServlet( urlPatterns =  {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
    
    public ServletUsuarioController() {
    
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// para exluir
			String acao = request.getParameter("acao");

			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUser = request.getParameter("id");
				daoUsuarioRepository.deletarUser(idUser);
				
				//listando todos usuarios
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Excluido com sucesso!!");
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);// redirecionando para mesma pagina
			}
			
			// deletando por ajax
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {

				String idUser = request.getParameter("id");
				daoUsuarioRepository.deletarUser(idUser); 

				response.getWriter().write("Excluido com sucesso!!");
				
				//continuando o flux se nao for nehu dos dois 
			}
			
			//buscando usuario por ajax
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserajax")) {

				String nomeBusca = request.getParameter("nomeBusca");
				
			List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(dadosJsonUser);//retornando a lista de dados
			
			response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));//retornando o total da pagiana
			response.getWriter().write(json);
				
				
			}
			
			
			//buscando usuario por ajax do modal
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

				String nomeBusca = request.getParameter("nomeBusca");
				String pagina = request.getParameter("pagina");

				
			List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioListOffSet(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(dadosJsonUser);//retornando a lista de dados
			
			response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));//retornando o total da pagiana
			response.getWriter().write(json);
				
				
			}
			
			//bucar usuario
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				String id = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(id,super.getUserLogado(request));
				
				//listando todos usuarios
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modelLogin", modelLogin);//redirecionando para a tela  com os dados se der erro 
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);//redirecionando para editar
				
			}
			//Listar os usuarios na tela 
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				
				request.setAttribute("msg", "Usuário carregados");
				request.setAttribute("modelLogins", modelLogins);//redirecionando para a tela  com os dados se der erro 
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);//redirecionando para tela com os usuarios
				
			}
			//Download da foto
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				String idUser = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(idUser, super.getUserLogado(request));
				if (modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					
					response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
					response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
				}
				
			}
			
			//paginação
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
				
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
			/**condicao para imprimir relatorio na tela**/
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {

				// pegando os dois paramentro que vem da tela
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				
			//se nao informa a data vai carregar todos na tela de relatorio
			if(dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()){
				
				request.setAttribute("listarUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
				
			}else {//se informa a data vai imprimir apenas os dados conforme a data na tela de relatorio
				
				request.setAttribute("listarUser", daoUsuarioRepository
						.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal));
			}
				
				
				//pra manter os dados na tela 
				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal);

				request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);// redirecionando para a pagian reluser.jsp

			}
			
			/** condicao para imprimir relatorio PDF ou Excel**/
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF")
			|| acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
				
				// pegando os dois paramentro que vem da tela
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				
				List<ModelLogin> modelLogins = null;
				
				//verificando se as datas foram informadas
				if(dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()){
			
					modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request));
				
				}else {
					
					modelLogins = daoUsuarioRepository
							.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal);
					
			} 
				
				 HashMap<String, Object> params = new HashMap<String, Object>();
				 params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relatorio") + File.separator);
				 
				 byte[] relatorio = null;
				 String extensao = "";
				 
				 //condiçao para imprimir PDF
				 if(acao.equalsIgnoreCase("imprimirRelatorioPDF")) {
				 relatorio = new ReportUtil().geraReltorioPDF(modelLogins, "resl-user-jsp", params ,(ServletContext) request.getServletContext());
				 extensao = "pdf";
				 
				 }else// condicao para imprimir Excel
					 if(acao.equalsIgnoreCase("imprimirRelatorioExcel")) {
						 relatorio = new ReportUtil().geraReltorioExcel(modelLogins, "resl-user-jsp", params ,(ServletContext) request.getServletContext());
						 extensao = "xls";
					 }
				
				
				//jogando a respota na tela 
				response.setHeader("Content-Disposition", "attachment;filename=arquivo." + extensao);
				response.getOutputStream().write(relatorio);
			}
			
			
			//condiçao que chama o Ajax graficoSalario
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")) {

				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");

				// verificando se as datas foram informadas se sao null ou vazias
				if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {
					
					
					BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser  = daoUsuarioRepository.
							montarGraficoMediaSalarial(super.getUserLogado(request));
					
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);//transformando o objeto em um json
					
					response.getWriter().write(json);
					
				} else {// se as datas forem informada cai nessa condiçao

					BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser  = daoUsuarioRepository.
							montarGraficoMediaSalarial(super.getUserLogado(request), dataInicial, dataFinal);
					
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);//transformando o objeto em um json
					
					response.getWriter().write(json);
				
				}
				
			}
			
			
			else {
				//listando todos usuarios
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);// redirecionando para mesma pagina
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();// mensagen de erro da tela erro.jsp
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());// mensagem de erro da tela erro.jsp
			redirecionar.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			//mensagem para verificar se o usuario foi gravado ou nao 
			String msg = "Operação realizada com sucesso!!!";
		
		//pegando os paramentros da tela 
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String perfil = request.getParameter("perfil");
		String sexo = request.getParameter("sexo");
		
		String cep = request.getParameter("cep");
		String logradouro = request.getParameter("logradouro");
		String bairro = request.getParameter("bairro");
		String localidade = request.getParameter("localidade");
		String uf = request.getParameter("uf");
		String numero = request.getParameter("numero");
		String dataNascimento = request.getParameter("dataNascimento");
		String rendaMensal = request.getParameter("rendamensal");

		//para tirar as virgula o sifrao e os espaço para salvar no banco
 		rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");
		
		
		//setando os parametros
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);//fazendo a conversao do id string para Long
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil(perfil);
		modelLogin.setSexo(sexo);
		
		modelLogin.setCep(cep);
		modelLogin.setLogradouro(logradouro);
		modelLogin.setBairro(bairro);
		modelLogin.setLocalidade(localidade);
		modelLogin.setUf(uf);
		modelLogin.setNumero(numero);
		modelLogin.setDataNascimento(Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento))));
		modelLogin.setRendamensal(Double.valueOf(rendaMensal));
		
		
		
		if(ServletFileUpload.MULTIPART != null) {
			Part part = request.getPart("fileFoto");
			
			if(part.getSize() > 0) {
			byte[] foto = IOUtils.toByteArray(part.getInputStream()) ;
			@SuppressWarnings("static-access")
			String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
			
			modelLogin.setFotouser(imagemBase64);
			modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
			}
			
		}
		
		// 	ESTA CERTO ESSE
//		if(ServletFileUpload.MULTIPART != null) {
//			Part part = request.getPart("fileFoto");
//			byte[] foto = org.apache.commons.io.IOUtils.toByteArray(part.getInputStream()) ;
//			@SuppressWarnings("static-access")
//			String imagemBase64 = "data:image/" + part.getContentType().split("\\/")[1] + ";base64," + new Base64().encodeBase64String(foto);
//			
//			modelLogin.setFotouser(imagemBase64);
//			modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);
//			
//			
//		}
		
		
		
		//verificando se existe ou nao para poder gravar 
		if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "Já existe usuário  com o mesmo login, informe outro login.";
			
		}else {
			
			if (modelLogin.isNovo()) {
				msg = "Gravado com Sucesso!!";
			} else {
				msg = "Atualizado com Sucesso!!";

			}
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin,super.getUserLogado(request));
			
		}
		
		//listando todos usuarios
		List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
		request.setAttribute("modelLogins", modelLogins);
		
		request.setAttribute("msg", msg);
		request.setAttribute("modelLogin", modelLogin);//redirecionando para a tela  com os dados se der erro 
		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);//redirecionando apos ter cadastrado usuario
		
		}catch (Exception e) {

			e.printStackTrace();//mensagen de erro da tela erro.jsp
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());//mensagem de erro da tela erro.jsp
			redirecionar.forward(request, response);
		}
	}

	@SuppressWarnings("unused")
	private boolean ServletFileUpload(HttpServletRequest request) {
		return false;
	}

}

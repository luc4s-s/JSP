package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnectionBanco;
import dao.DaoVersionadorBanco;

@WebFilter(urlPatterns = { "/principal/*" }) // intercepta todas as requisicoes que vierem do projeto ou mapeamento
public class FilterAutenticacao implements Filter {

	private static Connection connection;

	public FilterAutenticacao() {
	}

	// encerra os processo quando o servidor é parado
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// intercepta todas as requisicoes a a das resposta no sistema
	// tudo que fizer no sistema vai passar aqui
	// EX: validação de autenticação, Dar commit e rolback de transaçoes do banco
	// validar e fazer redirecionamento de paginas
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
				HttpServletRequest req = (HttpServletRequest)request;
				HttpSession session = (HttpSession) req.getSession();
	
				String usuarioLogado = (String) session.getAttribute("usuario");
	
				String urlParaAutenticar = req.getServletPath();// URL que esta sendo acessada
	
				// validar se esta logado senao redireciona para a tela de login
				if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {// não esta logado
																												
	
					RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
					request.setAttribute("msg", "Por favor realize o login");
					redireciona.forward(request, response);
					return;// para a execuçao e redireciona para o login
	
				} else {
					chain.doFilter(request, response);
				}
				
				connection.commit();//deu tudo certo comita as alteraçoes no banco de dados 

		}catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());//mensagem de erro da tela erro.jsp
			redirecionar.forward(request, response);
			try {
				connection.rollback();
			}catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	// inicia o sprocessos quando o servidor sobe o projeto
	// inicia conexao com o banco
	public void init(FilterConfig fConfig) throws ServletException {

		connection = SingleConnectionBanco.getConnection();

		DaoVersionadorBanco daoVersionadorBanco = new DaoVersionadorBanco();

		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadobancosql") + File.separator;

		File[] filesSql = new File(caminhoPastaSQL).listFiles();

		try {
			
			
			for (File file : filesSql) {

				boolean arquivoJaRodado = daoVersionadorBanco.arquivoSqlRodado(file.getName());
				
				if(!arquivoJaRodado) {
					
					FileInputStream entradaArquivo = new FileInputStream(file);
					
					Scanner lerArquivo = new Scanner(entradaArquivo, "UTF-8");
					
					StringBuilder sql = new StringBuilder();
					
					while (lerArquivo.hasNext()){//vai ler linha por linha enquanto tiver dados no  arquivo vai ler ele ate o final
						
						sql.append(lerArquivo.nextLine());
						sql.append("\n");
					}
					
					connection.prepareStatement(sql.toString()).execute();//rodando o conteudo do sql 
					daoVersionadorBanco.gravaAquivoSqlRodado(file.getName());//grava o aquivo que foi rodado
					connection.commit();//comita
					lerArquivo.close();//fecha pra pegar outro aquivo
				}
			}
			
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}

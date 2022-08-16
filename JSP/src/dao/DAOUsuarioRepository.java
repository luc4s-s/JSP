package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDtoGraficoSalarioUser;
import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

public class DAOUsuarioRepository {

	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//metodo que recebe a usuario a datainicial e data Final 
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalarial(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		//sql que faz o calculo da media salaria ADM, SECRETARIA e AUXILIAR
				String sql = "select avg(rendamensal) as media_salarial, perfil from model_login where usuario_id = ? and datanascimento >=? and datanascimento >=? GROUP BY perfil";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				
				preparedStatement.setLong(1, userLogado);
				preparedStatement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));// formantando as data para o sql entender
				preparedStatement.setDate(3, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));
				
				ResultSet resultSet = preparedStatement.executeQuery();
				
				List<String> perfils = new ArrayList<String>();
				List<Double> salarios = new ArrayList<Double>();
				
				BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
				
				while (resultSet.next()) {//enquanto tiver dados ele vai processar
					Double media_salarial = resultSet.getDouble("media_salarial");// pegando o campo do banco
					String perfil = resultSet.getString("perfil");// pegando o campo perfil

					perfils.add(perfil);
					salarios.add(media_salarial);
				}

				beanDtoGraficoSalarioUser.setPerfils(perfils);
				beanDtoGraficoSalarioUser.setSalarios(salarios);

				return beanDtoGraficoSalarioUser;
	}
	

	//metodo que recebe a usuario
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalarial(Long userLogado)throws Exception{
		
		//sql que faz o calculo da media salaria ADM, SECRETARIA e AUXILIAR
		String sql = "select avg(rendamensal) as media_salarial, perfil from model_login where usuario_id = ? GROUP BY perfil";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while (resultSet.next()) {//enquanto tiver dados ele vai processar
			Double media_salarial = resultSet.getDouble("media_salarial");//pegando o campo do banco
			String perfil = resultSet.getString("perfil");//pegando o campo perfil
			 
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}

	
	// metodo para inserir dados na tabela model_login
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {
		
		if(objeto.isNovo()) {//grava um novo login

		String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero, datanascimento, rendamensal)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement preparaSql = connection.prepareStatement(sql);

		preparaSql.setString(1, objeto.getLogin());
		preparaSql.setString(2, objeto.getSenha());
		preparaSql.setString(3, objeto.getNome());
		preparaSql.setString(4, objeto.getEmail());
		preparaSql.setLong(5, userLogado);
		preparaSql.setString(6, objeto.getPerfil());
		preparaSql.setString(7, objeto.getSexo());
		
		preparaSql.setString(8, objeto.getCep());
		preparaSql.setString(9, objeto.getLogradouro());
		preparaSql.setString(10, objeto.getBairro());
		preparaSql.setString(11, objeto.getLocalidade());
		preparaSql.setString(12, objeto.getUf());
		preparaSql.setString(13, objeto.getNumero());
		preparaSql.setDate(14, objeto.getDataNascimento());
		preparaSql.setDouble(15, objeto.getRendamensal());


		preparaSql.execute();// executando o sql
		connection.commit();// gravando no banco
		
		//condicao para salvar a foto ou nao 
		if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
			sql = "update model_login set fotouser =?, extensaofotouser =? where login=?";
			
			preparaSql = connection.prepareStatement(sql);
			
			preparaSql.setString(1, objeto.getFotouser());
			preparaSql.setString(2, objeto.getExtensaofotouser());
			preparaSql.setString(3, objeto.getLogin());
			
			preparaSql.execute();
			connection.commit();
		}
		
		}else {//atualizando 
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=?, datanascimento=?, rendamensal=?  WHERE id = "+objeto.getId() +";";
			PreparedStatement preparaSql = connection.prepareStatement(sql);
			preparaSql.setString(1, objeto.getLogin());
			preparaSql.setString(2, objeto.getSenha());
			preparaSql.setString(3, objeto.getNome());
			preparaSql.setString(4, objeto.getEmail());
			preparaSql.setString(5, objeto.getPerfil());
			preparaSql.setString(6, objeto.getSexo());
			
			preparaSql.setString(7, objeto.getCep());
			preparaSql.setString(8, objeto.getLogradouro());
			preparaSql.setString(9, objeto.getBairro());
			preparaSql.setString(10, objeto.getLocalidade());
			preparaSql.setString(11, objeto.getUf());
			preparaSql.setString(12, objeto.getNumero());
			preparaSql.setDate(13, objeto.getDataNascimento());
			preparaSql.setDouble(14, objeto.getRendamensal());


			
			preparaSql.executeUpdate();
			connection.commit();
			
			if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser=?, extensaofotouser=? where id=?";
				
				preparaSql = connection.prepareStatement(sql);
				
				preparaSql.setString(1, objeto.getFotouser());
				preparaSql.setString(2, objeto.getExtensaofotouser());
				preparaSql.setLong(3, objeto.getId());
				
				preparaSql.execute();
				connection.commit();
			}
		}
		
		return this.consultarUsuario(objeto.getLogin(), userLogado);// consultando o usuario pelo login

	}
	
	
	//listando de 5 em 5 
public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + " order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {//vai percorre as linhas de resultados do SQL
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			
			retorno.add(modelLogin);
		}
		
		return retorno;
	}

	//metodo que mostra o total de paginas 
	public int totalPagina(Long userLogado) throws Exception {
		String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();
		resultado.next();
		Double cadastro = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		Double pagina = cadastro / porpagina;
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina++;
		}
		
		return pagina .intValue();

	}
		
	
	// metodo para lista os Relatorio e imprimir na tela 
	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {// vai percorre as linhas de resultados do SQL
			ModelLogin modelLogin = new ModelLogin();

			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));//vai carregar os telefones

			retorno.add(modelLogin);
		}

		return retorno;
	}
	
	
	// metodo pra listar os Relatorio e imprimi na tela por faixa de data 
		public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {

			List<ModelLogin> retorno = new ArrayList<ModelLogin>();

			String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + "and datanascimento >= ? and datanascimento <= ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataInicial))));
			statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataFinal))));

			ResultSet resultado = statement.executeQuery();

			while (resultado.next()) {// vai percorre as linhas de resultados do SQL
				ModelLogin modelLogin = new ModelLogin();

				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				modelLogin.setDataNascimento(resultado.getDate("datanascimento"));

				
				modelLogin.setTelefones(this.listFone(modelLogin.getId()));//vai carregar os telefones

				retorno.add(modelLogin);
			}

			return retorno;
		}
	
	
	//lista usuario na tela 
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception{
			
			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			
			String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + "limit 5";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			ResultSet resultado = statement.executeQuery();
			
			while (resultado.next()) {//vai percorre as linhas de resultados do SQL
				ModelLogin modelLogin = new ModelLogin();
				
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				
				retorno.add(modelLogin);
			}
			
			return retorno;
		}
	
	
	//metodo para a paginacao do modal
	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception{
		
		
		String sql = "select count(1) as total from model_login Where upper(nome) like upper(?) and useradmin is false and usuario_id = ? " ;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%"); 
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		Double cadastro = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		Double pagina = cadastro / porpagina;
		Double resto = pagina % 2;
		
		if (resto > 0) {
			pagina++;
		}
		
		return pagina .intValue();
		
	}
	
	
		//metodo para buscar usuario no modal sem redirecional a pagina
		public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception{
			
			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			
			String sql = "select * from model_login Where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5" ;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, "%" + nome + "%"); 
			statement.setLong(2, userLogado);

			
			ResultSet resultado = statement.executeQuery();
			
			while (resultado.next()) {//vai percorre as linhas de resultados do SQL
				ModelLogin modelLogin = new ModelLogin();
				
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));

				
				retorno.add(modelLogin);
				
			}
			
			
			return retorno;
			
		}
	

	//metodo para buscar usuario no modal
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login Where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5" ;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%"); 
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {//vai percorre as linhas de resultados do SQL
			ModelLogin modelLogin = new ModelLogin();
			
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			
			retorno.add(modelLogin);
			
		}
		
		
		return retorno;
		
	}
	
	
	public ModelLogin consultarUsuarioLogado(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();// iniciando um objeto

		String sql = "select * from model_login where upper(login) = upper('" + login + "')" ;// preparado o sql

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();// executando o sql

		while (resultado.next()) {// se tem resultado

			// preenchendo o objeto
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));



		}
		// retornando o objeto
		return modelLogin;
	}
	
	
	
	public ModelLogin consultarUsuario(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();// iniciando um objeto

		String sql = "select * from model_login where upper(login) = upper('" + login + "') and useradmin is false " ;// preparado o sql

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();// executando o sql

		while (resultado.next()) {// se tem resultado
 
			// preenchendo o objeto
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));


		}
		// retornando o objeto
		return modelLogin;
	}
	
	
	
	
	// metodo para consulta
	public ModelLogin consultarUsuario(String login, Long userLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();// iniciando um objeto

		String sql = "select * from model_login where upper(login) = upper('" + login + "') and useradmin is false and usuario_id = " + userLogado ;// preparado o sql

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();// executando o sql

		while (resultado.next()) {// se tem resultado

			// preenchendo o objeto
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));


		}
		// retornando o objeto
		return modelLogin;
	}
	
	
		//consulta usuario do telefone
		public ModelLogin consultarUsuarioID(Long id) throws Exception {
			ModelLogin modelLogin = new ModelLogin();// iniciando um objeto

			String sql = "select * from model_login where id = ? and useradmin is false ";// preparado o sql

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, (id));

			ResultSet resultado = statement.executeQuery();// executando o sql

			while (resultado.next()) {// se tem resultado

				// preenchendo o objeto
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setSenha(resultado.getString("senha"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				modelLogin.setFotouser(resultado.getString("fotouser"));
				modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
				
				modelLogin.setCep(resultado.getString("cep"));
				modelLogin.setLogradouro(resultado.getString("logradouro"));
				modelLogin.setBairro(resultado.getString("bairro"));
				modelLogin.setLocalidade(resultado.getString("localidade"));
				modelLogin.setUf(resultado.getString("uf"));
				modelLogin.setNumero(resultado.getString("numero"));
				modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
				modelLogin.setRendamensal(resultado.getDouble("rendamensal"));


			}
			// retornando o objeto
			return modelLogin;
		}
	
	
	// metodo para consulta usuario por id
	public ModelLogin consultarUsuarioID(String id, Long userLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();// iniciando um objeto

		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";// preparado o sql

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();// executando o sql

		while (resultado.next()) {// se tem resultado

			// preenchendo o objeto
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));


		}
		// retornando o objeto
		return modelLogin;
	}

	// metodo para validar se o login existe ou nao
	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(1) > 0 as existe from model_login where upper(login) = upper('"+login+"');";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();// executando o sql

		resultado.next();// para ele entrar nos resultados do sql
		return resultado.getBoolean("existe"); // aquiiiiiiiii

	}
	
	//metodo para deletar usuario 
	public void deletarUser(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		prepareSql.setLong(1, Long.parseLong(idUser));//setando o parametro
		
		prepareSql.executeUpdate();
		
		connection.commit();
	}
	
	

	//metodo para lista os telefones no relatorio
	public List<ModelTelefone> listFone(Long idUserPai) throws Exception{
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
		
		String sql = "select * from telefone where usuario_pai_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, idUserPai);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		while (rs.next()) {
			ModelTelefone modelTelefone = new ModelTelefone();
			
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuario_cad_id(this.consultarUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuario_pai_id(this.consultarUsuarioID(rs.getLong("usuario_pai_id")));
			
			retorno.add(modelTelefone);
		}
		
		return retorno;
	}

	
}

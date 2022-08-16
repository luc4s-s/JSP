package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnectionBanco;

public class DaoVersionadorBanco implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection connection;
	
	public DaoVersionadorBanco() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	//metodo para grava sql rodado
	public void gravaAquivoSqlRodado(String nome_File) throws Exception{
		
		String sql = "INSERT INTO public.versionadorbanco(arquivo_sql) VALUES (?);"; //SQL
		PreparedStatement preparedStatement = connection.prepareStatement(sql);//preparando o SQL
		preparedStatement.setString(1, nome_File);//setando o parametro
		preparedStatement.execute();//executando o sql
				
	}
	
	//metodo que pega os aquivo rodado
	public boolean arquivoSqlRodado(String nome_do_arquivo) throws Exception{
		
		String sql = "select count(1) > 0 as rodado from versionadorbanco where arquivo_sql = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, nome_do_arquivo);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		resultSet.next();
		
		return resultSet.getBoolean("rodado");
	}
}

package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

//	/** Para a conexao Remota */
//	private static String banco = "jdbc:postgresql://ec2-52-204-157-26.compute-1.amazonaws.com:5432/daplnqsthaegjr?sslmode=require&autoReconnect=true";
//	private static String user = "ithfbdshctxouk";
//	private static String senha = "c1b9bdc2dba58b30ccafa618006509aadd15a357b0a8fd235d7db76c9a07a00e";
//	private static Connection connection = null; //connection do javasql
	
	
	/** para conexao local **/
	private static String banco = "jdbc:postgresql://localhost:5434/ProjetoJSP?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null; //connection do javasql

	public static Connection getConnection() {
		return connection;
	}
	
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() { //quando tiver uma instancia vai connectar
		conectar();
	}
	
	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");//carrega o driver de connexao do banco 
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false); // para nao efetuar alteraçoes no banco sem nosso comando
			}
			
		} catch (Exception e) {
			e.printStackTrace(); //mostra qualquer erro no momento de connectar 
		}
	}
}

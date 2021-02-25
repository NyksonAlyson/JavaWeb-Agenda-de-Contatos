package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DAO {
	// Módulo de conexão
	//parametro de conexao
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	private String user = "root";
	private String password = "@Bboy1504";
	
	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;

		}
	}
	// teste de conexão
	public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	// Crud Create
	public void inserirContato(JavaBeans contato) {
		String sql = "insert into contatos(nome,fone,email) values(?,?,?)";
		try {
			//abri a conexao
			Connection con = conectar();
			// preparar a query para execução no banco de dados
			PreparedStatement pst= con.prepareStatement(sql);
			// Substituir os parametros (?) pelo conteudo das variaveis javaBeans
		pst.setString(1, contato.getNome());
		pst.setString(2, contato.getFone());
		pst.setString(3, contato.getEmail());
		// Executar a query
		pst.executeUpdate();
		// Encerrar a conexao com o banco
		con.close();
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
	// Módulo de conexão
	/** The driver. */
	// parametro de conexao
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";

	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "@Bboy1504";

	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
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

	/**
	 * Teste conexao.
	 */
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

	/**
	 * Inserir contato.
	 *
	 * @param contato the contato
	 */
	// Crud Create Metodo para inserir no banco de dados
	public void inserirContato(JavaBeans contato) {
		String insert = "insert into contatos(nome,fone,email) values(?,?,?)";
		try {
			// abri a conexao
			Connection con = conectar();
			// preparar a query para execução no banco de dados
			PreparedStatement pst = con.prepareStatement(insert);
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
	
	/**
	 * Deletar contato.
	 *
	 * @param contato the contato
	 */
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon =?";
		try {
			// abri a conexao
			Connection con = conectar();
			// preparar a query para execução no banco de dados
			PreparedStatement pst = con.prepareStatement(delete);
			// substituir os paramentros (?)pelo conteudo da variavel
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			// encerrar a conexao com banco
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	/**
	 * Listar contatos.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> listarContatos() {
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String listaNome = "select * from contatos order by nome";
		try {
			// conexão com banco de dados
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(listaNome);
			ResultSet rs = pst.executeQuery();
			// o laço abaixo será executado enquanto houver contatos
			while (rs.next()) {
				// variaveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
	
	/**
	 * Selecionar contato.
	 *
	 * @param contato the contato
	 */
	// CRUD UPDATE
	public void selecionarContato(JavaBeans contato) {
		String listarID= "select * from contatos where idcon =?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(listarID);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				// setar as variaveis javaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Alterar contato.
	 *
	 * @param contato the contato
	 */
	public void alterarContato(JavaBeans contato) {
		String update = "update contatos set nome=?, fone=?, email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1,contato.getNome());
			pst.setString(2,contato.getFone());
			pst.setString(3,contato.getEmail());
			pst.setString(4,contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

}

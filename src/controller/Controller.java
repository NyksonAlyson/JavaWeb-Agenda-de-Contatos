package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main","/insert" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		}else if(action.equals("/insert")) {
			novoContato(request, response);
		}else {
			response.sendRedirect("index.html");
		}
	}

	// Lista de contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// Teste de recebimento da lista de contatos do banco
		/*
		 * for (int i = 0; i < lista.size(); i++) {
		 * System.out.println(lista.get(i).getIdcon());
		 * System.out.println(lista.get(i).getNome());
		 * System.out.println(lista.get(i).getFone());
		 * System.out.println(lista.get(i).getEmail());
		 * 
		 * }
		 */
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		
	}
	// Novo contato
		protected void novoContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// teste de recebiemnto dos dados do formulario
			//System.out.println(request.getParameter("nome"));
			//System.out.println(request.getParameter("fone"));
			//System.out.println(request.getParameter("email"));
			// setar as variaveis javabeans
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			// invocar o metodo inserirContato passando o objeto
			dao.inserirContato(contato);
			// redirecionar para o documento agenda.jsp
			response.sendRedirect("main");
		}
}

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

@WebServlet(urlPatterns = { "/Controller", "/main","/insert","/select","/delete","/update"})
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
		}		
		else if(action.equals("/select")) {
			listarContato(request, response);
		}
		else if(action.equals("/delete")) {
			deletarContato(request, response);	
		}
		else if(action.equals("/update")) {
			editarContato(request, response);	
		}

		else {
			response.sendRedirect("index.html");
		}
	}
	
	// inserirContato
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

	// Lista de contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// Teste de recebimento da lista de contatos do banco
		/*
		 * for (int i = 0; i < lista.size(); i++) {]
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

		// Deletar usuarlo do banco 
		protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {	
			contato.setIdcon(request.getParameter("idcon"));
			dao.deletarContato(contato);
			response.sendRedirect("main");  
		}

		// eidtar contato
		protected void listarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String idcon = request.getParameter("idcon");
			contato.setIdcon(idcon);
			// Executar o método selecionarContato (DAO)
			dao.selecionarContato(contato);
			// teste de recebimento
//			System.out.println(contato.getIdcon());
//			System.out.println(contato.getNome());
//			System.out.println(contato.getFone());
//			System.out.println(contato.getEmail());
			request.setAttribute("idcon",contato.getIdcon());
			request.setAttribute("nome",contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email",contato.getEmail());
			RequestDispatcher rd = request.getRequestDispatcher("Editar.jsp");
			rd.forward(request, response);
			
		}
		protected void editarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TEXTE DE RECEBIMENTO DOS PARAMETROS
			/*
			 * System.out.println(request.getParameter("idcon"));
			 * System.out.println(request.getParameter("nome"));
			 * System.out.println(request.getParameter("fone"));
			 * System.out.println(request.getParameter("email"));
			 */
			contato.setIdcon(request.getParameter("idcon"));
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			// executar o método alterarContato
			dao.alterarContato(contato);
			// redirecionar para o documento agenda.jsp atualizando alterações
			response.sendRedirect("main");
			
		}
		
}

package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main","/insert","/select","/delete","/update","/report"})
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
			adicionarContato(request, response);
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
		else if(action.equals("/report")) {
			gerarRelatorio(request, response);	
		}
		else {
			response.sendRedirect("index.html");
		}
	}
	// gerar relatorios PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Document documento = new Document();
			try {
				//TIPO DE CONTEUDO
				response.setContentType("apllication/pdf");
				//nome do documento
				response.addHeader("Content-Disposition", "inline; filename= contatos.pdf");
				// criar o documento
				PdfWriter.getInstance(documento, response.getOutputStream());
				//abri o documento -> conteudo
				documento.open();
				// nome do relatorio em PDF
				documento.add(new Paragraph("Lista de contatos:"));
				documento.add(new Paragraph(" "));
				//criar uma tabela com 3 colunas
				PdfPTable tabela = new PdfPTable(3);
				// CABEÇALHO
				PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				
				// popular a tabela com os contatos
				ArrayList<JavaBeans> lista = dao.listarContatos();
				for (int i = 0; i < lista.size(); i++) {
					tabela.addCell(lista.get(i).getNome());
					tabela.addCell(lista.get(i).getFone());
					tabela.addCell(lista.get(i).getEmail());
				}
				documento.add(tabela);
				documento.close();
			} catch (Exception e) {
				System.out.println(e);
				documento.close();
			}
		
	}

	// inserirContato
	protected void adicionarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
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
	
			request.setAttribute("idcon",contato.getIdcon());
			request.setAttribute("nome",contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email",contato.getEmail());
			RequestDispatcher rd = request.getRequestDispatcher("Editar.jsp");
			rd.forward(request, response);
			
		}
		protected void editarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			contato.setIdcon(request.getParameter("idcon"));
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			// executar o método alterarContato
			dao.alterarContato(contato);
			// redirecionar para o documento agenda.jsp atualizando alterações
			response.sendRedirect("main");
			
		}
		protected void cancelarContato(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			String idcon = request.getParameter("idcon");
			contato.setIdcon(idcon);
			// Executar o método selecionarContato (DAO)
			dao.selecionarContato(contato);
	
			request.setAttribute("idcon",contato.getIdcon());
			request.setAttribute("nome",contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email",contato.getEmail());
			RequestDispatcher rd = request.getRequestDispatcher("Editar.jsp");
			rd.forward(request, response);
		response.sendRedirect("main");
			
		}
		
}

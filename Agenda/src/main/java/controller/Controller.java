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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

/**
 * The Class Controller.
 * @author Lucas da Cunha da Silva
 */
@WebServlet(urlPatterns = {"/Controller", "/main", "/insert", "/select", "/update", "/delete","/report"})
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID 
	= 1L;
	
	/** The dao. */
	private DAO dao = new DAO();
	
	/** The contato. */
	private JavaBeans contato = new JavaBeans();
	
    /**
     * Instantiates a new controller.
     */
    public Controller() {
        super();
    }

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
			case "/main": {
				selectContatos(request, response);		
				break;
			}
			case "/insert":{
				insertContato(request, response);
				break;
			}
			case "/select":{
				selectContato(request, response);
				break;
			}
			case "/update":{
				editContato(request, response);
				break;
			}
			case "/delete":{
				removeContato(request, response);
				break;
			}
			case "/report":{
				createPDF(request, response);
				break;
			}
			default:{
				response.sendRedirect("index.html");
				break;
			}
		}		
	}
	
	/**
	 * Select contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void selectContatos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ArrayList<JavaBeans> beans = dao.select();
		request.setAttribute("contatos", beans);
		
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}	
	
	/**
	 * Select contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void selectContato(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		JavaBeans contato = dao.select(id);
		
		request.setAttribute("contato", contato);
		
		RequestDispatcher rd = request.getRequestDispatcher("edit.jsp");
		rd.forward(request, response);
	}
	
	/**
	 * Insert contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void insertContato(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		contato.setName(request.getParameter("name"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		dao.insert(contato);
		
		response.sendRedirect("main");
	}

	/**
	 * Edits the contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	private void editContato(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		JavaBeans contato = new JavaBeans();
		contato.setId(Integer.parseInt(request.getParameter("id")));
		contato.setName(request.getParameter("name"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.update(contato);
		
		response.sendRedirect("main");
	}
	
	/**
	 * Removes the contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void removeContato(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));		
		dao.remove(id);
		
		response.sendRedirect("main");
		
	}
	
	/**
	 * Creates the PDF.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void createPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Document doc = new Document();
		
		response.setContentType("apllication/pdf");
		response.setHeader("Content-Disposition", "inline; filename=contatos.pdf");		
	
		try {
			@SuppressWarnings("unused")
			PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());

			doc.open();
			
			doc.add(new Paragraph("Lista de Contatos: "));
			doc.add(new Paragraph(" "));
			
			

			//CABEÇALHO
			PdfPTable table = new PdfPTable(4);

			table.addCell(new PdfPCell(new Paragraph("ID")));
			table.addCell(new PdfPCell(new Paragraph("Nome")));
			table.addCell(new PdfPCell(new Paragraph("Telefone")));
			table.addCell(new PdfPCell(new Paragraph("Email"))); 

			
			//CONTEUDO
			ArrayList<JavaBeans> contatos = dao.select();
			for(JavaBeans contato : contatos) {
				table.addCell(new PdfPCell(new Paragraph(
								String.valueOf(contato.getId()))));
				table.addCell(new PdfPCell(new Paragraph(contato.getName())));
				table.addCell(new PdfPCell(new Paragraph(contato.getFone())));
				table.addCell(new PdfPCell(new Paragraph(contato.getEmail()))); 
			}
			
			doc.add(table);								
			doc.close();	
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

}

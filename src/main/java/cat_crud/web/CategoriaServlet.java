package cat_crud.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cat_crud.dao.CategoriaDAO;
import cat_crud.model.Categoria;

@WebServlet("/")
public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoriaDAO categoriaDAO;
	
	public void init() {
		categoriaDAO = new CategoriaDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertCategoria(request, response);
				break;
			case "/delete":
				deleteCategoria(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateCategoria(request, response);
				break;
			default:
				listCategoria(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listCategoria(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Categoria> listCategoria = categoriaDAO.selectAllCategorias();
		request.setAttribute("listCategoria", listCategoria);
		RequestDispatcher dispatcher = request.getRequestDispatcher("categoria-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("categoria-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Categoria existingCategoria = categoriaDAO.selectCategoria(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("categoria-form.jsp");
		request.setAttribute("categoria", existingCategoria);
		dispatcher.forward(request, response);

	}

	private void insertCategoria(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		Categoria newCategoria = new Categoria(nombre, descripcion);
		categoriaDAO.insertCategoria(newCategoria);
		response.sendRedirect("list");
	}

	private void updateCategoria(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");

		Categoria categoria = new Categoria(id, nombre, descripcion);
		categoriaDAO.updateCategoria(categoria);
		response.sendRedirect("list");
	}

	private void deleteCategoria(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		categoriaDAO.deleteCategoria(id);
		response.sendRedirect("list");

	}

}

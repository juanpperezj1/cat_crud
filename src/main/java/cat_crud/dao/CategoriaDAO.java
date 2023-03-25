package cat_crud.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cat_crud.model.Categoria;

public class CategoriaDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Password$1";

	private static final String INSERT_CATEGORIA_SQL = "INSERT INTO categoria" + "  (nombre, descripcion) VALUES "
			+ " (?, ?);";

	private static final String SELECT_CATEGORIA_BY_ID = "select id,nombre,descripcion from categoria where id =?";
	private static final String SELECT_ALL_CATEGORIAS = "select * from categoria";
	private static final String DELETE_CATEGORIA_SQL = "delete from categoria where id = ?;";
	private static final String UPDATE_CATEGORIA_SQL = "update categoria set nombre = ?,descripcion= ? where id = ?;";

	public CategoriaDAO() {
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {              
			Class.forName("com.mysql.cj.jdbc.Driver");
		    // Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertCategoria(Categoria categoria) throws SQLException {
		System.out.println(INSERT_CATEGORIA_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORIA_SQL)) {
			preparedStatement.setString(1, categoria.getNombre());
			preparedStatement.setString(2, categoria.getDescripcion());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Categoria selectCategoria(int id) {
		Categoria categoria = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORIA_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				categoria = new Categoria(id, nombre, descripcion);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return categoria;
	}

	public List<Categoria> selectAllCategorias() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Categoria> categorias = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIAS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String descripcion = rs.getString("descripcion");
				categorias.add(new Categoria(id, nombre, descripcion));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return categorias;
	}

	public boolean deleteCategoria(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORIA_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateCategoria(Categoria categoria) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_CATEGORIA_SQL);) {
			statement.setString(1, categoria.getNombre());
			statement.setString(2, categoria.getDescripcion());
			statement.setInt(3, categoria.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}


}

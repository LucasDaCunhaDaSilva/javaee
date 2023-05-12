package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;

/**
 * The Class DAO.
 * @author Lucas da Cunha da Silva
 */
public class DAO {

/** The host. */
//Connection	
	private final String host = "192.168.1.102";
	
	/** The port. */
	private final int port = 5432;

/** The database. */
//Credentials
	private final String database = "dbagenda";
	
	/** The user. */
	private final String user = "postgres";
	
	/** The pass. */
	private final String pass = "01001100";

	
	/** The url. */
	private final String url = 
			"jdbc:postgresql://"+host+":"+port+"/"+database+"?useTimezone=true&serverTimeZone=UTC";

	
	/** The con. */
	private Connection con;

	/**
	 * Connect.
	 *
	 * @return the connection
	 */
	private Connection connect() {
		Connection connection = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, pass);
		
		}catch (SQLTimeoutException timeoutException) {
			System.err.println("Tempo para conectar com o banco de dados esgotado.\n"
					+timeoutException.getMessage());
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}

	/**
	 * Connection test.
	 *
	 * @return the string
	 */
	public String connectionTest() {
		return connect().toString();
	}


	/**
	 * Insert.
	 *
	 * @param contato the contato
	 * @return true, if successful
	 */
	public boolean insert(JavaBeans contato) {
		String sql = "INSERT INTO contatos(name,fone,email) values(?,?,?)";
		
		try {
			if(con == null || con.isClosed()) con = connect();

			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, contato.getName());
			stm.setString(2, contato.getFone());
			stm.setString(3, contato.getEmail());
						
			
			return stm.executeUpdate() == 1;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Select.
	 *
	 * @return the array list
	 */
	public ArrayList<JavaBeans> select() {
		String sql = "SELECT * FROM contatos ORDER BY name";
		
		ArrayList<JavaBeans> contatos = new ArrayList<JavaBeans>();
		
		try {
			if(con == null || con.isClosed()) con = connect();
			
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet set = stm.executeQuery();
			
			while(set.next()) {
				JavaBeans bean = new JavaBeans();
				bean.setId(set.getInt("id"));
				bean.setName(set.getString("name"));
				bean.setFone(set.getString("fone"));
				bean.setEmail(set.getString("email"));
				
				contatos.add(bean);
				
			}
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}
		
		return contatos;

	}
	
	/**
	 * Select.
	 *
	 * @param id the id
	 * @return the java beans
	 */
	public JavaBeans select(int id) {
		String sql = "SELECT * FROM contatos WHERE id = "+id;
		
		JavaBeans bean = new JavaBeans();
		bean.setId(id);
		
		try {
			if(con == null || con.isClosed()) con = connect();
			
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet set = stm.executeQuery();
			if(set.next()) {
				bean.setName(set.getString("name"));
				bean.setFone(set.getString("fone"));
				bean.setEmail(set.getString("email"));
			}
			
			return bean;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Update.
	 *
	 * @param contato the contato
	 * @return true, if successful
	 */
	public boolean update(JavaBeans contato) {
		String sql = "Update contatos set name = ?, fone = ?, email = ? WHERE id = ?";
	
		try {
			if(con == null || con.isClosed()) con = connect();
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, contato.getName());
			stm.setString(2, contato.getFone());
			stm.setString(3, contato.getEmail());
			stm.setInt(4, contato.getId());
			
			return stm.executeUpdate() == 1;
		}	catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	
	
	}

	/**
	 * Removes the.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean remove(int id) {
		String sql = "DELETE FROM contatos WHERE id = ?";
		
		try {
			if(con == null || con.isClosed()) con = connect();
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);		
			
			return stm.executeUpdate() == 1;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}

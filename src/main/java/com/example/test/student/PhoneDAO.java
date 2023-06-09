package com.example.test.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class PhoneDAO {
	private String jdbcUrl="jdbc:mysql://localhost:3306/test";
	private String jdbcUsername="root";
	private String jdbcPassword="toan@3101";
	private static final String SELECT_ALL_STUDENTS="SELECT * from phone";
	private static final String SELECT_STUDENT_BY_ID="SELECT * from phone  where id= ?";
	private static final String INSERT_STUDENT_SQL="INSERT INTO phone (id,name,price,brand,sold) VALUES (?,?,?,?,?)";
	private static final String UPDATE_STUDENT_SQL="UPDATE phone SET name = ?,price =?,brand=?,sold=? WHERE id = ?";
	private static final String DELETE_STUDENT_SQL="DELETE FROM phone WHERE id = ?";
	public PhoneDAO() {
		// TODO Auto-generated constructor stub
	}
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(jdbcUrl,jdbcUsername,jdbcPassword);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} return connection;
	}
	public List<Phone> selectAllstudents() {
		List<Phone> students= new ArrayList<>();
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS);
				){
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				float price = resultSet.getFloat("price");
				String brand= resultSet.getString("brand");
				int sold = resultSet.getInt("sold");
				students.add(new Phone(id,name,price,brand,sold==0? false:true));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} return students;
	}
	public Phone selectstudent(String id) {
		Phone student = new Phone();
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID);
				){
				preparedStatement.setString(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				student.setId(resultSet.getString("id"));
				student.setName( resultSet.getString("name"));
				student.setPrice(resultSet.getFloat("price"));
				student.setBrand(resultSet.getString("brand"));
				student.setSold( resultSet.getInt("sold")==0?false:true);
			
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} return student;
	}
	public void insertstudent(Phone student) throws IOException {
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL);
				){
			preparedStatement.setString(1, student.getId());
			preparedStatement.setString(2, student.getName());
			preparedStatement.setFloat(3, student.getPrice());
			preparedStatement.setString(4, student.getBrand());
			preparedStatement.setInt(5, student.isSold() ? 1:0);
			preparedStatement.executeUpdate();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	public void updatestudent(Phone student) throws IOException{
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT_SQL);
				){
			preparedStatement.setString(1, student.getName());
			preparedStatement.setFloat(2, student.getPrice());
			preparedStatement.setString(3, student.getBrand());
			preparedStatement.setInt(4, student.isSold() ? 1:0);
			preparedStatement.setString(5, student.getId());
			preparedStatement.executeUpdate();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	public void deletestudent(String id) throws IOException{
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_SQL);
				){
			preparedStatement.setString(1, id);
			preparedStatement.executeUpdate();
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	public boolean check(String id) {
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID);
				){
			preparedStatement.setString(1, id);
			ResultSet resultSet =preparedStatement.executeQuery();
			if(resultSet.next()) return false;
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		return true;
	}
}

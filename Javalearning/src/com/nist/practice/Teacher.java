package com.nist.practice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class Teacher {
	PreparedStatement ps=null;
	public void Insert(String name,String address,double salary,String gender,int age) {
		String sql="insert into teacher_table(name,address,salary,gender,age) values(?,?,?,?,?)";
		try {
			ps=getConnection().prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2,address);
			ps.setDouble(3,salary);
			ps.setString(4,gender);
			ps.setInt(5,age);
			ps.executeUpdate();
			System.out.println("data successfully saved");
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	public void updateStudent(int id,String name,String address,double salary,String gender,int age) {
		String sql="update teacher_table set name=?,address=?,salary=?,gender=?,age=? where id=?";
		try {
			ps=getConnection().prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2,address);
			ps.setDouble(3,salary);
			ps.setString(4,gender);
			ps.setInt(5,age);
			ps.setInt(6,id);
			ps.executeUpdate();
			System.out.println("data successfully updated");
		}catch(Exception ex) {
			System.out.println(ex);
		}		
}
	
	public void deleteStudentById(int id) {
		String sql="delete from teacher_table where id=?";
		try {
			ps=getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			System.out.println("data successfully deleted");
		}catch(Exception ex) {
			System.out.println(ex);
		}
		}
	
	public void displayData() {
		String sql="Select * from teacher_table";
		try {
			ps=getConnection().prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				int id=rs.getInt("id");
				String name=rs.getString("name");
				double salary=rs.getDouble("salary");
				String gender=rs.getString("gender");
				String address=rs.getString("address");
				int age=rs.getInt("age");
				System.out.println(id+" "+name +" "+gender +" "+address+ " "+age+" "+salary+" ");
			}
		}catch(Exception ex) {
			System.out.println(ex);
		}
		}
	
	public Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/csit_seventh","root","");
	}
	
	public static void main(String[] args) {
		Teacher obj=new Teacher();
		obj.displayData();
		//obj.Insert("prashna","nala" ,12000,"female",21);
		obj.deleteStudentById(1);
	}
}
	
	
	
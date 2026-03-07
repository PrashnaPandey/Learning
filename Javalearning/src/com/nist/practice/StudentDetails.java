package com.nist.practice;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class StudentDetails {

    public StudentDetails() {

        JFrame frame = new JFrame("Student Details");
        frame.setSize(500, 400);
        frame.setLayout(null);

        JLabel l1 = new JLabel("Search");
        l1.setBounds(300, 10, 70, 30);
        frame.add(l1);

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(380, 10, 100, 30);
        frame.add(searchTextField);

        String columnName[] = {"id", "name", "age", "gender", "faculty", "semester"};

        DefaultTableModel tableModel = new DefaultTableModel(columnName, 0);
        JTable t1 = new JTable(tableModel);
        t1.setBounds(30, 50, 450, 250);

        JScrollPane scrollPane = new JScrollPane(t1);
        scrollPane.setBounds(30, 50, 450, 250);
        frame.add(scrollPane);

        JButton b1 = new JButton("New");
        b1.setBounds(40, 320, 100, 30);
        frame.add(b1);

        JButton b2 = new JButton("Delete");
        b2.setBounds(160, 320, 100, 30);
        frame.add(b2);

        JButton b3 = new JButton("Edit");
        b3.setBounds(280, 320, 100, 30);
        frame.add(b3);
        
        ActionListener al=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				StudentForm s1=new StudentForm();
			}
		};
		b1.addActionListener(al);
		String sql="select * from student";
		try {
			PreparedStatement ps=DatabaseConnection.getConnection().prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				tableModel.addRow(new Object[] {rs.getInt("id"),rs.getString("name"),rs.getString("address"),rs.getInt("age"),rs.getString("gender"),rs.getString("faculty"),rs.getString("semester")
				
				});
			}
		}catch(Exception ex) {
					System.out.println(ex);
				
			
		}

		ActionListener al2 = new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {

		        String search = searchTextField.getText();

		        String sql = "select * from student where name like ?";

		        try {

		            tableModel.setRowCount(0);

		            PreparedStatement ps =
		                    DatabaseConnection.getConnection().prepareStatement(sql);

		            ps.setString(1, "%" + search + "%");

		            ResultSet rs = ps.executeQuery();

		            while (rs.next()) {

		                tableModel.addRow(new Object[]{
		                        rs.getInt("id"),
		                        rs.getString("name"),
		                        rs.getString("address"),
		                        rs.getInt("age"),
		                        rs.getString("gender"),
		                        rs.getString("faculty"),
		                        rs.getString("semester")
		                });

		            }

		        } catch(Exception ex) {
		            System.out.println(ex);
		        }
		    }
		};

		searchTextField.addActionListener(al2);
		
		b2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = t1.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frame, "Please select a student to delete!");
		            return;
		        }

		        int confirm = JOptionPane.showConfirmDialog(frame,
		                "Are you sure you want to delete this student?", "Confirm Delete",
		                JOptionPane.YES_NO_OPTION);

		        if (confirm == JOptionPane.YES_OPTION) {
		            int studentId = (int) tableModel.getValueAt(selectedRow, 0);

		            try {
		                String sql = "DELETE FROM student WHERE id = ?";
		                PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
		                ps.setInt(1, studentId);
		                int result = ps.executeUpdate();

		                if (result > 0) {
		                    tableModel.removeRow(selectedRow);
		                    JOptionPane.showMessageDialog(frame, "Student deleted successfully!");
		                }
		            } catch (Exception ex) {
		                ex.printStackTrace();
		                JOptionPane.showMessageDialog(frame, "Error deleting student!");
		            }
		        }
		    }
		});
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = t1.getSelectedRow();

				if(selectedRow == -1){
				    JOptionPane.showMessageDialog(frame,"Please select a student to edit");
				    return;
				}
			    int id = (int) tableModel.getValueAt(selectedRow,0);
		        String name = tableModel.getValueAt(selectedRow,1).toString();
		        String address = tableModel.getValueAt(selectedRow,2).toString();
		        int age = (int) tableModel.getValueAt(selectedRow,3);
		        String gender = tableModel.getValueAt(selectedRow,4).toString();
		        String faculty = tableModel.getValueAt(selectedRow,5).toString();
		        String semester = tableModel.getValueAt(selectedRow,6).toString();

		        new StudentForm();
			}
		});
	
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new StudentDetails();
    }
}
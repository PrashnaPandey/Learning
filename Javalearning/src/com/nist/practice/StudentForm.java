package com.nist.practice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
public class StudentForm {
	public StudentForm() {
		JFrame frame=new JFrame();
		frame.setSize(800,500);
		frame.setLayout(null);
		frame.setResizable(false);
		
		JLabel l1=new JLabel("Name");
		l1.setBounds(20,10,100,40);
		frame.add(l1);
		
		JTextField t1=new JTextField();
		t1.setBounds(180,10,150,40);
		frame.add(t1);
		
		JLabel l2=new JLabel("Address");
		l2.setBounds(20,60,100,40);
		frame.add(l2);
		
		JTextField t2=new JTextField();
		t2.setBounds(180,60,150,40);
		frame.add(t2);
		
		
		JLabel l3=new JLabel("Age");
		l3.setBounds(20,110,100,40);
		frame.add(l3);
		
		JTextField t3=new JTextField();
		t3.setBounds(180,110,150,40);
		frame.add(t3);
		
		
		JLabel l4=new JLabel("Gender");
		l4.setBounds(20,160,100,40);
		frame.add(l4);
		JRadioButton maleRd=new JRadioButton("Male");
		maleRd.setBounds(180,160,150,40);
		frame.add(maleRd);
		JRadioButton femaleRd=new JRadioButton("Female");
		femaleRd.setBounds(340,160,150,40);
		frame.add(femaleRd);
		JRadioButton othersRd=new JRadioButton("Others");
		othersRd.setBounds(500,160,150,40);
		frame.add(othersRd);
		
		ButtonGroup bg=new ButtonGroup();
		bg.add(femaleRd);
		bg.add(maleRd);
		bg.add(othersRd);
		
		JLabel l5=new JLabel("Faculty");
		l5.setBounds(20,210,100,40);
		frame.add(l5);
		String facultyList[]= {"CSIT","BCA","BIM"};
		JComboBox <String>facultyCombo=new JComboBox<String>(facultyList);
		facultyCombo.setBounds(180,210,100,40);
		frame.add(facultyCombo);
		
		JLabel l6=new JLabel("Semester");
		l6.setBounds(20,260,100,40);
		frame.add(l6);
		String semesterList[]= {"First","Second","Third","Fourth","Fifth","Sixth","Seventh","Eighth"};
		JComboBox <String>semCombo=new JComboBox<String>(semesterList);
		semCombo.setBounds(180,260,100,40);
		frame.add(semCombo);
		
		JCheckBox checkbox=new JCheckBox("Accept terms and condition");
		checkbox.setBounds(180,310,150,40);
		frame.add(checkbox);
		
		JButton submit=new JButton("Submit");
		submit.setBounds(50,360,100,40);
		frame.add(submit);
		
		
		ActionListener al =new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name=t1.getText();
				String address=t2.getText();
				int age=Integer.parseInt(t3.getText());
				String gender="";
				if (maleRd.isSelected()) {
					gender=maleRd.getText();
				}else if(femaleRd.isSelected()) {
					gender="female";
				}else if (othersRd.isSelected()) {
					gender="Others";
				}
				
				String faculty=(String)facultyCombo.getSelectedItem();
				String semester=(String)semCombo.getSelectedItem();
				if(checkbox.isSelected()) {
					PreparedStatement ps=null;
					String sql="insert into student (name,address,age,gender,faculty,semester) values (?,?,?,?,?,?)";
					try {
						ps=DatabaseConnection.getConnection().prepareStatement(sql);
						ps.setString(1, name);
						ps.setString(2,address);
						ps.setInt(3, age);
						ps.setString(4,gender);
						ps.setString(5, faculty);
						ps.setString(6, semester);
						ps.executeUpdate();
						
						JOptionPane.showMessageDialog(
								null,
								"Data successfully added",
								"Success",
								JOptionPane.INFORMATION_MESSAGE
								);
						
						frame.dispose();
						new StudentDetails();
					
						
					}catch(Exception ex) {
						System.out.println(ex);
					}
				}else {
					JOptionPane.showMessageDialog(null, "You must accept the terms and conditions to continue","Warning",JOptionPane.WARNING_MESSAGE);
				}
				
			}
		};
		submit.addActionListener(al);
		frame.setVisible(true);
	}
public static void main(String[] args) {
	new StudentForm();
}
}

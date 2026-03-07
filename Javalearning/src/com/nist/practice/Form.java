package com.nist.practice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.channels.Pipe.SourceChannel;

import javax.swing.*;
public class Form {
public Form() {
	JFrame frame=new JFrame("Student Form");
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
	
	ButtonGroup bg=new ButtonGroup();
	bg.add(femaleRd);
	bg.add(maleRd);
	
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
	
	JButton submit=new JButton("Submit");
	submit.setBounds(50,310,100,40);
	frame.add(submit);
		
	ActionListener al=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name=t1.getText();
			String address=t2.getText();
			String age=t3.getText();
			String gender="";
			if (maleRd.isSelected()) {
				gender=maleRd.getText();
			}
			if(femaleRd.isSelected()) {
				gender=femaleRd.getText();
			}
			
			String faculty=(String)facultyCombo.getSelectedItem();
			String semester=(String)semCombo.getSelectedItem();
			
			System.out.println("Name is "+name);
			System.out.println("Address is "+address);
			System.out.println("age is "+age);
			System.out.println("Gender is "+gender);
			System.out.println("Faculty is "+faculty);
			System.out.println("Semester is "+semester);
		
		}
	};
	submit.addActionListener(al);
    frame.setVisible(true);
}
public static void main(String[] args) {
	new Form();
}
}

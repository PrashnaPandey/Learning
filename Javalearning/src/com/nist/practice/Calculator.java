package com.nist.practice;
import javax.swing.*;
import java.awt.event.*;


public class Calculator {

   /*
    JFrame frame;
    JTextField t1, t2;
*/
   
    public Calculator() {
        JFrame frame = new JFrame("Calculator");
        frame.setSize(500, 300);
        frame.setLayout(null);

        JLabel l1 = new JLabel("First Number");
        l1.setBounds(10, 10, 100, 30);
        frame.add(l1);

        JTextField t1 = new JTextField();
        t1.setBounds(120, 10, 100, 30);
        frame.add(t1);

        JLabel l2 = new JLabel("Second Number");
        l2.setBounds(10, 50, 120, 30); 
        frame.add(l2);

        JTextField t2 = new JTextField();
        t2.setBounds(120, 50, 100, 30); 
        frame.add(t2);
        
        JLabel l3 = new JLabel("Total");
        l3.setBounds(10, 90, 100, 30); 
        frame.add(l3);

        JTextField t3 = new JTextField();
        t3.setBounds(120, 90, 100, 30); 
        t3.setEditable(false);
        frame.add(t3);
        
        JButton button = new JButton("Submit");
        button.setBounds(120,140, 100,30);
        frame.add(button);
        
        ActionListener al=new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent e) {
        	 int a=Integer.parseInt(t1.getText());
        	 int b=Integer.parseInt(t2.getText());
        	 int c=a+b;
        	 String result=String.valueOf(c);
        	 t3.setText(result);
         }
        };
        button.addActionListener(al);
        
        MouseAdapter m1 = new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		int a=Integer.parseInt(t1.getText());
              	 int b=Integer.parseInt(t2.getText());
              	 int c=a*b;
              	 String result=String.valueOf(c);
              	 t3.setText(result);
        	}
		};
		button.addMouseListener(m1);

        frame.setVisible(true);
    }

 
    public static void main(String[] args) {
        new Calculator(); 
    }
}

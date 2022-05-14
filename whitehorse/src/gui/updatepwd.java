package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import dao.impl.UserDaoSqlImpl;
import entity.User;

public class updatepwd extends JFrame implements ActionListener{
    User user;
    UserDaoSqlImpl userDaoSqlImpl;
    JPanel contentPane;
    JPasswordField passwordField,passwordField_1;
    JButton btnNewButton;
    public static void main(String[] args) {
        //new updatepwd();
    }
    public updatepwd(User user)
    {
        userDaoSqlImpl = new UserDaoSqlImpl();
        this.user=user;
        init();
        setTitle("修改密码");
        setBounds(100, 100, 450, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        setVisible(true);
    }
    public void init()
    {
        contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(170, 63, 182, 35);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(170, 129, 182, 35);
		contentPane.add(passwordField_1);
		
		JLabel lblNewLabel = new JLabel("输入新密码：");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setBounds(57, 68, 103, 30);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("再次输入密码：");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(41, 130, 112, 30);
		contentPane.add(lblNewLabel_1);
		
		btnNewButton = new JButton("确认修改");
        btnNewButton.setBounds(159, 199, 112, 38);
        btnNewButton.addActionListener(this);
		contentPane.add(btnNewButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==btnNewButton)
        {
            if(passwordField.getPassword().toString().length()>0&&passwordField_1.getPassword().toString().length()>0)
            {
                if(String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordField_1.getPassword())))
                {
                    user.setUpwd(String.valueOf(passwordField.getPassword()));
                    if(userDaoSqlImpl.Updatepwd(user))
                    {
                        JOptionPane.showMessageDialog(null,"修改密码成功！");
                        this.setVisible(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"修改密码失败!");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"两次输入的密码不一致!");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"密码未输入!");
            }
        }
    }
    
}

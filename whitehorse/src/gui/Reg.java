package gui;

import javax.swing.*;
import javax.swing.JSpinner.DateEditor;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.impl.DepartDaoSqllmpl;
import dao.impl.UserDaoSqlImpl;
import dao.impl.WorkerDaoSqlImpl;
import entity.Depart;
import entity.User;
import entity.Worker;

public class Reg extends JFrame implements ActionListener{
    User user;
    JPanel contentPane;
    UserDaoSqlImpl userDaoSqlImpl;
    DepartDaoSqllmpl departDaoSqllmpl;
    WorkerDaoSqlImpl workerDaoSqllmpl;
    Depart qxz=new Depart(0,"<-!请选择!->");
    JLabel l1,l2,l3,l4,l3_1;
    JButton b1,b2;
    JPasswordField pwd;
    JTextField name,phone;
    JComboBox comboBox,comboBox_1;
    String admin;
    public static void main(String[] args) {
        //new Reg("");
    }
    public Reg(String qx)
    {
        user =new User();
        departDaoSqllmpl=new DepartDaoSqllmpl();
        userDaoSqlImpl = new UserDaoSqlImpl();
        workerDaoSqllmpl=new WorkerDaoSqlImpl();
        init();
        setTitle("注册");
        setSize(500,400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        if("amdin".equals(qx))
        {
            this.admin=qx;
            comboBox.setVisible(false);
            l3.setVisible(false);
        }
        else
        {
            comboBox_1.addItem(qxz);
            ResultSet rs=departDaoSqllmpl.SelectDepart();
                if(rs!=null)
                {
				try
				{
					while(rs.next())
					{
						//Depart depart=new Depart(rs.getInt(1),rs.getString(2));
						comboBox_1.addItem(new Depart(rs.getInt(1),rs.getString(2)));
					}
				}
				catch (SQLException a) {
					a.printStackTrace();
				}
			}
        }
        setVisible(true);
    }
    public void init()
    {
		contentPane = new JPanel();
		setContentPane(contentPane);
        contentPane.setLayout(null);
        
        l1 = new JLabel("用户名：");
		l1.setFont(new Font("Dialog", Font.PLAIN, 16));
		l1.setBounds(111, 60, 69, 24);
		contentPane.add(l1);
		
		JLabel l2 = new JLabel("密码：");
		l2.setFont(new Font("Dialog", Font.PLAIN, 16));
		l2.setBounds(124, 106, 56, 24);
		contentPane.add(l2);
		
		name = new JTextField();
		name.setBounds(173, 59, 175, 32);
		contentPane.add(name);
		
		pwd = new JPasswordField();
		pwd.setBounds(173, 105, 175, 32);
		contentPane.add(pwd);
		
        b1 = new JButton("注册");
        b1.setBounds(111, 289, 112, 32);
        b1.addActionListener(this);
		contentPane.add(b1);
		
		b2 = new JButton("取消");
        b2.setBounds(251, 289, 112, 32);
        b2.addActionListener(this);
		contentPane.add(b2);
		
        comboBox = new JComboBox();
        comboBox.addItem("<-!请选择!->");
		comboBox.addItem("顾客");
        comboBox.addItem("员工");
        comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
                if("员工".equals(comboBox.getSelectedItem()))
                {
                    comboBox_1.setVisible(true);
                    l3_1.setVisible(true);
                }
                else
                {
                    comboBox_1.setVisible(false);
                    l3_1.setVisible(false);
                }
			}
		  });
		comboBox.setBounds(173, 208, 175, 32);
		contentPane.add(comboBox);
		
        l3 = new JLabel("权限：");
		l3.setFont(new Font("Dialog", Font.PLAIN, 16));
		l3.setBounds(124, 210, 56, 24);
		contentPane.add(l3);
		
		phone = new JTextField();
		phone.setBounds(173, 154, 175, 32);
		contentPane.add(phone);
		
		l4  = new JLabel("手机号：");
		l4.setFont(new Font("Dialog", Font.PLAIN, 16));
		l4.setBounds(109, 155, 69, 24);
		contentPane.add(l4);

        comboBox_1 = new JComboBox();
        comboBox_1.setBounds(173, 16, 175, 32);
        comboBox_1.setVisible(false);
        contentPane.add(comboBox_1);
        
        l3_1 = new JLabel("部门：");
        l3_1.setFont(new Font("Dialog", Font.PLAIN, 16));
        l3_1.setBounds(124, 18, 56, 24);
        l3_1.setVisible(false);
        contentPane.add(l3_1);
		
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1)
        {
            if(name.getText().length()>0&&pwd.getPassword().length>0&&phone.getText().length()>0)
            {
                user.setUname(name.getText());
                user.setUpwd(String.valueOf(pwd.getPassword()));
                user.setUphone(phone.getText());
                if(userDaoSqlImpl.CountUser(user))
                {
                    JOptionPane.showMessageDialog(null,"用户名已被注册！");
                }
                else
                {
                    if("amdin".equals(admin))
                    {
                        user.setUide("3");
                        admin="";
                    }
                    else if("员工".equals(comboBox.getSelectedItem())&&!qxz.equals(comboBox_1.getSelectedItem()))
                    {
                        user.setUide("2");
                    }
                    else if("顾客".equals(comboBox.getSelectedItem()))
                    {
                        user.setUide("1");
                    }
                    else 
                    {
                        JOptionPane.showMessageDialog(null,"资料尚未填写完整!");
                        return;
                    }
                    if(userDaoSqlImpl.InsertUser(user))
                    {
                        if("2".equals(user.getUide()))
                        {
                            userDaoSqlImpl.SelectUser(user);
                            ResultSet rs=userDaoSqlImpl.value();
                            try {
                                if(rs.next())
                                {
                                    Depart depart= (Depart) comboBox_1.getSelectedItem();
                                    user.setUserid(rs.getString(1));
                                    workerDaoSqllmpl.InsertWorker(user, String.valueOf(depart.getDepartid()));
                                }
                            } 
                            catch (SQLException a) {
                                JOptionPane.showMessageDialog(null,"注册失败！");
                                a.printStackTrace();
                                return;
                            }
                        }
                        JOptionPane.showMessageDialog(null,"注册成功！");
                        this.setVisible(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"注册失败!");
                    }
                }
            }
            else 
            {
                JOptionPane.showMessageDialog(null,"资料尚未填写完整!");
                return;
            }
        }
        else if(e.getSource()==b2)
        {
            this.setVisible(false);
        }
    }
}

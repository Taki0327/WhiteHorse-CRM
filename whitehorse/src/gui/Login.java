package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import dao.impl.UserDaoSqlImpl;
import entity.User;

public class Login extends JFrame implements ActionListener{
    User user;
    JPanel bgPanel,contentPane;
    UserDaoSqlImpl userDaoSqlImpl;
    JLayeredPane layeredPane = new JLayeredPane();     // 面板层
    ImageIcon bgImage = new ImageIcon("./lib/bg.jpg");
    JLabel l1,l2,l3;
    JButton b1,b2;
    JPasswordField pwd;
    JTextField name;
    String uname="";
    public static void main(String[] args) {
        new Login();
    }
    public Login(String uname)
    {
        this.uname=uname;
        LoginMethod();
        JOptionPane.showMessageDialog(null,"由于您长时间未操作 系统自动返回登录页面！");
    }
    public void LoginMethod()
    {
        user =new User();
        userDaoSqlImpl = new UserDaoSqlImpl();
        init();
        setResizable(false);
        setTitle("帝王雅趣福 乐足走天下");
        //setSize(450,300);
        this.setBounds(200, 100, 800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
        setVisible(true);
    }
    public Login()
    {
       LoginMethod();
    }
    public void init()
    {
        layeredPane.setBounds(0, 0, 800, 600);
        this.add(layeredPane);
        bgPanel = new JPanel();
        bgPanel.setBounds(0, 0, 800, 600);
        layeredPane.add(bgPanel, Integer.valueOf(Integer.MIN_VALUE));
        // 背景图片，添加到背景Panel里面
        JLabel bgLabel = new JLabel(bgImage);
        bgPanel.add(bgLabel);
        JPanel jp = (JPanel) this.getContentPane();
        jp.setOpaque(false);

		contentPane = new JPanel();
        contentPane.setBounds(0, 300, 350, 200);
		//setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setOpaque(false);
	  
        l1 = new JLabel("白马会所 欢迎您!");
		l1.setFont(new Font("宋体", Font.PLAIN, 26));
		l1.setBounds(23, 0, 225, 76);
		contentPane.add(l1);
		
		b1 = new JButton("登录");
        b1.setBounds(138, 166, 69, 24);
        b1.addActionListener(this);
		contentPane.add(b1);
		
		name = new JTextField(uname);
		name.setBounds(73, 67, 175, 32);
		contentPane.add(name);
		l2 = new JLabel("用户名：");
		l2.setFont(new Font("宋体", Font.PLAIN, 16));
		l2.setBounds(10, 75, 69, 24);
		contentPane.add(l2);
		
		l3 = new JLabel("密码：");
		l3.setFont(new Font("宋体", Font.PLAIN, 16));
		l3.setBounds(23, 111, 69, 24);
		contentPane.add(l3);
		
		pwd = new JPasswordField();
		pwd.setBounds(72, 109, 176, 32);
		contentPane.add(pwd);
		
		b2 = new JButton("注册");
		b2.setBounds(59, 166, 69, 24);
        b2.addActionListener(this);
        contentPane.add(b2);
        layeredPane.add(contentPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1)
        {
            if(name.getText().length()>0&pwd.getPassword().toString().length()>0)
            {
                try
                {
                    user.setUname(name.getText());
                    user.setUpwd(String.valueOf(pwd.getPassword()));
                    if(userDaoSqlImpl.SelectUser(user))
                    {
                        ResultSet uservalue=userDaoSqlImpl.value();
                        try {
                            if (uservalue.next()) {
                                user.setUide(uservalue.getString(5));
                                user.setUserid(uservalue.getString(1));
                                user.setUphone(uservalue.getString(4));
                            }
                        } catch (SQLException a) {
                            a.printStackTrace();
                        }
                        /*HashMap uservalue=userDaoSqlImpl.value();
                        Set set=uservalue.keySet();
                        Iterator<String> it = set.iterator();
                        while (it.hasNext()) {
                            String n = it.next();
                            if("uide".equals(n))
                            {
                                user.setUide(uservalue.get("uide").toString());
                            }
                            if("userid".equals(n))
                            {
                                user.setUserid(uservalue.get("userid").toString());
                            }
                            if("uphone".equals(n))
                            {
                                user.setUphone(uservalue.get("uphone").toString());
                            }
                        }*/
                        JOptionPane.showMessageDialog(null,"登陆成功！");
                        if("1".equals(user.getUide()))
                        {
                            new Client(user);
                        }
                        else if("2".equals(user.getUide()))
                        {
                            new Employee(user);
                        }
                        else if("3".equals(user.getUide()))
                        {
                            new admin(user);
                        }
                        this.setVisible(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"用户名或密码输入错误!");
                    }
                }
                catch(Exception a)
                {
                    JOptionPane.showMessageDialog(null,"登陆失败!");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"用户名或密码未输入!");
            }
        }
        else if(e.getSource()==b2)
        {
            new Reg("Reg");
        }
    }
}

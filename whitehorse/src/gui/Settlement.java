package gui;

import javax.swing.*;

import dao.impl.BillDaoSqllmpl;
import dao.impl.CustomDaoSqllmpl;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.Custom;
import entity.User;
public class Settlement extends JFrame implements ActionListener{
   
    private JPanel contentPane;
    User user;
    Custom custom;
    BillDaoSqllmpl billDaoSqllmpl;
    JLabel money;
    JButton btnNewButton;
    CustomDaoSqllmpl customDaoSqllmpl;
    Date date;
    int inttext;
    Client client;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) {
        //new Settlement();
    }
    public Settlement(User user,Custom custom,Client client)
    {
        this.user=user;
        this.custom=custom;
        this.client=client;
        billDaoSqllmpl=new BillDaoSqllmpl();
        customDaoSqllmpl=new CustomDaoSqllmpl();
        init();
        inttext=(int)Double.parseDouble(customDaoSqllmpl.SelectCustomSumBill(user));
        money.setText(String.valueOf(inttext));
        if(inttext==0)
        {
            JOptionPane.showMessageDialog(null,"您当前未有待支付订单 无需支付！");
            return;
        }
        setTitle("账单结算");
        setSize(450,420);
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

        JLabel lblNewLabel = new JLabel("尊敬的贵宾，您此次消费：");
		lblNewLabel.setBounds(48, 44, 252, 18);
		contentPane.add(lblNewLabel);
		
		money = new JLabel("0");
		money.setBounds(236, 44, 79, 18);
		contentPane.add(money);
		
		JLabel lblNewLabel_2 = new JLabel("￥");
		lblNewLabel_2.setBounds(314, 44, 72, 18);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("支付方式：");
		lblNewLabel_1.setBounds(48, 75, 86, 18);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel(" ");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		Icon icon=new ImageIcon("lib/erweima.jpg");
		lblNewLabel_3.setBounds(119, 75, 72, 18);
		lblNewLabel_3.setIcon(icon);
		lblNewLabel_3.setSize(200,200);
		contentPane.add(lblNewLabel_3);
		
		btnNewButton = new JButton("确认");
        btnNewButton.setBounds(119, 312, 113, 27);
        btnNewButton.addActionListener(this);
		contentPane.add(btnNewButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnNewButton)
        {
            date=new Date();
            if(isNum(custom.getCusconsum()))
            {
                inttext+=Integer.parseInt(custom.getCusconsum());
            }
            custom.setCusconsum(String.valueOf(inttext));
            custom.setCustime(sdf.format(date));
            billDaoSqllmpl.UpdateBill(user);
            if(customDaoSqllmpl.UpdateCustomInfo(user, custom))
            {
                JOptionPane.showMessageDialog(null,"支付成功！");
                client.selectbill();
                this.setVisible(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"支付失败！");
            }
		}
    }
    public boolean isNum(String s)
    {
        for(int i=0;i<s.length();i++)
        {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }
}

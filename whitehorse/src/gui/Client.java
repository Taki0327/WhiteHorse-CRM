package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.impl.BillDaoSqllmpl;
import dao.impl.CustomDaoSqllmpl;
import dao.impl.FeedbackDaoSqllmpl;
import dao.impl.ProductDaoSqllmpl;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import entity.Bill;
import entity.Custom;
import entity.Feedback;
import entity.Product;
import entity.User;

public class Client extends JFrame implements ActionListener {
	User user;
	Custom custom;
	Bill bill;
	Product product;
	Feedback feedback;
	CustomDaoSqllmpl customDaoSqllmpl;
	BillDaoSqllmpl billDaoSqllmpl;
	FeedbackDaoSqllmpl feedbackDaoSqllmpl;
	ProductDaoSqllmpl productDaoSqllmpl;
	CRMThread t1;
	JTable table, table1;
	JTextField textField, textField_1, textField_2, textField_3, textField_4, Productname;
	JPanel contentPane;
	JComboBox comboBox;
	JLabel yhm, scxf, ljxf, time,Time;
	JButton UserInformation, userinforupdate, UserConsume, ConsumeQuery, UserComplaint, Workend, Productbuy,
			EmployeeWorknow, settlement;
	JTabbedPane tabbedPane;
	String[] columnNames = { "账单编号", "产品名","部门", "服务员", "下单时间", "完成时间", "账单 " };
	String[] procolumnNames = { "产品编号", "产品名", "价格","部门"};
	DefaultTableCellRenderer r = new DefaultTableCellRenderer();
	int productnum;

	public static void main(String[] args) {
		// new Client();
	}

	public Client(User user) {
		this.user = user;
		billDaoSqllmpl = new BillDaoSqllmpl();
		customDaoSqllmpl = new CustomDaoSqllmpl();
		feedbackDaoSqllmpl = new FeedbackDaoSqllmpl();
		productDaoSqllmpl = new ProductDaoSqllmpl();
		custom = new Custom();
		r.setHorizontalAlignment(JLabel.CENTER);
		init();
		t1=new CRMThread(this,user.getUname(),Time);
		//t1=new CRMThread(this,user.getUname());
		t1.start();
		
		setTitle("贵宾-" + user.getUname());
		setBounds(100, 100, 800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.white);
		userinformationset();

		setVisible(true);
	}

	public void userinformationset()// 获取用户资料
	{
		if (customDaoSqllmpl.SelectCustomInfo(user)) {
			ResultSet uservalue = customDaoSqllmpl.value();
			try {
				if (uservalue.next()) {
					if(uservalue.getString(3)==null)
					{
						custom.setCusname("");
					}
					else
					{
						custom.setCusname(uservalue.getString(3));
					}
					custom.setCustime(uservalue.getString(4));
					custom.setCusconsum(uservalue.getString(5));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			/*HashMap uservalue=customDaoSqllmpl.value();
			Set set=uservalue.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String n = it.next();
				if("cusname".equals(n))
				{
					if(uservalue.get(n)==null)
					{
						custom.setCusname("");
					}
					else
					{
						custom.setCusname(uservalue.get(n).toString());
					}
				}
				if("custime".equals(n))
				{
					custom.setCustime(uservalue.get(n).toString());
				}
				if("cusconsum".equals(n))
				{
					custom.setCusconsum(uservalue.get(n).toString());
				}
			}*/
		}
		//控件赋值
		yhm.setText(user.getUname());
		scxf.setText(custom.getCustime());
		ljxf.setText(custom.getCusconsum());
		textField_3.setText(custom.getCusname());
		textField_4.setText(user.getUphone());
	}
    public void init()
    {
        contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Time = new JLabel("时间");
		Time.setBounds(5, 371, 128, 18);
		contentPane.add(Time);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    tabbedPane.setForeground(Color.GREEN);
	    tabbedPane.setBackground(Color.GREEN);
		tabbedPane.setBounds(138, -33, 644, 486);
		contentPane.add(tabbedPane);
		
		tabbedPane.addTab("个人信息",userinformation());
		tabbedPane.setBackground(Color.WHITE);
		UserInformation = new JButton("个人信息");
		UserInformation.addActionListener(this);
        //UserInformation.addActionListener(e->tabbedPane.setSelectedIndex(0));
        UserInformation.setBounds(0, 0, 140, 37);
        contentPane.add(UserInformation);
        
		tabbedPane.addTab("查看消费",userconsume());
		tabbedPane.setBackground(Color.WHITE);
        UserConsume = new JButton("查看消费");
		//UserConsume.addActionListener(e->tabbedPane.setSelectedIndex(1));
		UserConsume.addActionListener(this);
        UserConsume.setBounds(0, 34, 140, 37);
        contentPane.add(UserConsume);
        
		tabbedPane.addTab("购买产品",productbuy());
		tabbedPane.setBackground(Color.WHITE);
        EmployeeWorknow = new JButton("购买产品");
		//EmployeeWorknow.addActionListener(e->tabbedPane.setSelectedIndex(2));
		EmployeeWorknow.addActionListener(this);
        EmployeeWorknow.setBounds(0, 69, 140, 37);
        contentPane.add(EmployeeWorknow);
		
		tabbedPane.addTab("服务反馈",usercomplaint());
		tabbedPane.setBackground(Color.WHITE);
        UserComplaint = new JButton("服务反馈");
		//UserComplaint.addActionListener(e->tabbedPane.setSelectedIndex(3));
		UserComplaint.addActionListener(this);
        UserComplaint.setBounds(0, 103, 140, 37);
        contentPane.add(UserComplaint);
		
		JLabel updatepwd = new JLabel("修改密码");
		updatepwd.setBounds(32, 399, 72, 18);
		updatepwd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new updatepwd(user);
			}
		});
		contentPane.add(updatepwd);
		
        JLabel userexit = new JLabel("退出账户");
		userexit.setBounds(32, 422, 72, 18);
		userexit.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
				setVisible(false);
				new Login();
				t1.Stop();
				t1=null;
				dispose();
        	}
        });
        contentPane.add(userexit);
	}
	private Component userinformation() {
		JPanel c1 = new JPanel();
		c1.setBorder(null);
		c1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("用户名：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(223, 90, 75, 32);
		c1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("姓名：");
		lblNewLabel_2_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(239, 125, 55, 32);
		c1.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("电话号码：");
		lblNewLabel_2_1_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2_1_1.setBounds(211, 167, 87, 32);
		c1.add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("上次消费时间：");
		lblNewLabel_2_1_1_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2_1_1_1.setBounds(179, 208, 124, 32);
		c1.add(lblNewLabel_2_1_1_1);
		
		JLabel lblNewLabel_2_1_1_1_1 = new JLabel("累计消费金额：");
		lblNewLabel_2_1_1_1_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2_1_1_1_1.setBounds(179, 250, 124, 32);
		c1.add(lblNewLabel_2_1_1_1_1);
		
		yhm = new JLabel("用户名：");
		yhm.setFont(new Font("宋体", Font.PLAIN, 16));
		yhm.setBounds(299, 90, 190, 32);
		c1.add(yhm);
		
		scxf = new JLabel("scxf");
		scxf.setFont(new Font("宋体", Font.PLAIN, 16));
		scxf.setBounds(299, 208, 190, 32);
		c1.add(scxf);
		
		ljxf = new JLabel("累计消费金额：");
		ljxf.setFont(new Font("宋体", Font.PLAIN, 16));
		ljxf.setBounds(299, 250, 190, 32);
		c1.add(ljxf);
		
		textField_3 = new JTextField();
		textField_3.setBounds(299, 124, 144, 30);
		c1.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(299, 167, 144, 30);
		c1.add(textField_4);
		
		userinforupdate = new JButton("保存");
		userinforupdate.setFont(new Font("宋体", Font.PLAIN, 16));
		userinforupdate.setBounds(255, 350, 104, 38);
		userinforupdate.addActionListener(this);
		c1.add(userinforupdate);
		//c1.add();
		return c1;
	}
	private Component userconsume() {
		JPanel c2 = new JPanel();
		c2.setBorder(null);
		c2.setLayout(null);
		ConsumeQuery = new JButton("手动刷新");
		ConsumeQuery.addActionListener(this);
		ConsumeQuery.setBounds(172, 26, 123, 32);
		c2.add(ConsumeQuery);

		settlement = new JButton("结算");
		settlement.setBounds(320, 26, 123, 32);
		settlement.addActionListener(this);
		c2.add(settlement);

		table1 = new JTable();
		//JFrame table必须包在JScrollPane内否则不显示标题 setDefaultRenderer居中
		table1.setDefaultRenderer(Object.class, r);
		JScrollPane js = new JScrollPane(table1);
		js.setBounds(0, 70, 635, 388);
		c2.add(js);
		return c2;
	}
    private Component productbuy() {
		JPanel c3 = new JPanel();
		c3.setBorder(null);
		c3.setLayout(null);
		Productbuy = new JButton("下单");
		Productbuy.setBounds(409, 41, 123, 32);
		Productbuy.addActionListener(this);
		c3.add(Productbuy);
		
		table = new JTable();
		table.setDefaultRenderer(Object.class, r);
		table.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				Productname.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
			}
		});
		JScrollPane js2 = new JScrollPane(table);
		js2.setBounds(0, 113, 635, 345);
		c3.add(js2);
		
		JLabel lblNewLabel = new JLabel("产品编号：");
		lblNewLabel.setBounds(84, 48, 72, 18);
		c3.add(lblNewLabel);
		
		Productname = new JTextField();
		Productname.setBounds(142, 45, 186, 24);
		c3.add(Productname);
		Productname.setColumns(10);
		return c3;
	}
    private Component usercomplaint() {
		JPanel c4 = new JPanel();
		c4.setBorder(null);
		c4.setLayout(null);
		Workend = new JButton("提交");
		Workend.setBounds(179, 391, 123, 32);
		Workend.addActionListener(this);
		c4.add(Workend);
		
		JLabel lblNewLabel_1 = new JLabel("反馈内容：");
		lblNewLabel_1.setBounds(72, 108, 88, 18);
		c4.add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(147, 108, 472, 206);
		c4.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("反馈时间：");
		lblNewLabel_1_1.setBounds(72, 344, 88, 18);
		c4.add(lblNewLabel_1_1);
		
		time = new JLabel(Time.getText());
		time.setBounds(153, 340, 193, 26);
		c4.add(time);

		JLabel lblNewLabel_1_2 = new JLabel("反馈对象：");
		lblNewLabel_1_2.setBounds(72, 44, 88, 18);
		c4.add(lblNewLabel_1_2);
		
		comboBox = new JComboBox();
		comboBox.setBounds(147, 41, 173, 24);
		c4.add(comboBox);
		return c4;
	}
    @Override
    public void actionPerformed(ActionEvent e) {
		t1.cz();
		// TODO Auto-generated method stub
		if(e.getSource()==UserInformation)
        {
			tabbedPane.setSelectedIndex(0);
			userinformationset();
		}
		else if(e.getSource()==userinforupdate)
		{	
			if(textField_3.getText().length()>0&&textField_4.getText().length()>0)
			{
				custom.setCusname(textField_3.getText());
				user.setUphone(textField_4.getText());
				if(customDaoSqllmpl.UpdateCustomInfo(user, custom))
				{
					JOptionPane.showMessageDialog(null,"保存成功！");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"保存失败！");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"未输入内容！");
			}
		}
		else if(e.getSource()==UserConsume||e.getSource()==ConsumeQuery)
		{
			selectbill();
			tabbedPane.setSelectedIndex(1);
		}
		else if(e.getSource()==UserComplaint)
		{
			int i=0;
			time.setText(Time.getText());
			comboBox.removeAllItems();
			comboBox.addItem("管理员");
			ResultSet rs=billDaoSqllmpl.SelectBillworkname(user);
			if(rs!=null)
			{
				try
				{
					while(rs.next())
					{
						if("待接单".equals(rs.getString(1)))
						{
							i++;
						}
						else
						{
							i--;
							comboBox.addItem(rs.getString(1));
						}
					}
				}
				catch (SQLException a) {
					a.printStackTrace();
				}
				if(i>0)
				{
					JOptionPane.showMessageDialog(null,"您的订单暂未被接受 将默认反馈给管理员");
				}
			}
			/*List<HashMap> list=billDaoSqllmpl.SelectBillworkname(user);
			if(list!=null)
			{
				for (HashMap key : list) {
					Set set=key.keySet();
					Iterator<String> it = set.iterator();
					while (it.hasNext()) {
						if(it.next().equals("workname"))
						{
							if("待接单".equals(key.get("workname").toString()))
							{
								i++;
							}
							else
							{
								i--;
								comboBox.addItem(key.get("workname").toString());
							}
						}
					}
				}
				if(i>0)
				{
					JOptionPane.showMessageDialog(null,"您的订单暂未被接受 将默认反馈给管理员");
				}
			}*/
			else
			{
				JOptionPane.showMessageDialog(null,"您当前尚未消费 将默认反馈给管理员");
			}
			tabbedPane.setSelectedIndex(3);
		}
		else if(e.getSource()==Workend)
		{
			if(textField_2.getText().length()>0)
			{
				feedback = new Feedback(textField_2.getText(), time.getText(), comboBox.getSelectedItem().toString());
				if(feedbackDaoSqllmpl.InsertFeed(user, feedback))
				{
					JOptionPane.showMessageDialog(null,"反馈成功！");
					textField_2.setText("");
					time.setText(Time.getText());
				}
				else
				{
					JOptionPane.showMessageDialog(null,"反馈失败！");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"反馈内容未输入！");
			}
		}
		else if(e.getSource()==Productbuy)//1goumai
		{
			if(Productname.getText().length()>0&&isNum(Productname.getText()))
			{
				if(Integer.parseInt(Productname.getText())>0&&Integer.parseInt(Productname.getText())<=productnum)
				{
					product=productDaoSqllmpl.SelectProduct(Integer.parseInt(Productname.getText()));
					bill=new Bill(product.getProname(),Time.getText(),product.getPromoney(),product.getDepartid());
					if(billDaoSqllmpl.InsertBill(user,bill))
					{
						JOptionPane.showMessageDialog(null,"您已成功下单 "+product.getProname()+" !");
						Productname.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null,"下单失败！");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"产品编号未输入正确！");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"产品编号未输入正确！");
			}
		}
		else if(e.getSource()==EmployeeWorknow)
        {
			productnum=productDaoSqllmpl.SelectProduct("all",0);
			if(productnum>0)
			{
				table.setModel(new DefaultTableModel(productDaoSqllmpl.returntablevalue(), procolumnNames));
			}
			tabbedPane.setSelectedIndex(2);
		}
		else if(e.getSource()==settlement)//2
        {
			new Settlement(user,custom,this);
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
	public void selectbill()
	{
		if(billDaoSqllmpl.SelectBill(user))
			{
				table1.setModel(new DefaultTableModel(billDaoSqllmpl.returntablevalue(), columnNames));
			}
			else
			{
				table1.setModel(new DefaultTableModel(null, columnNames));
			}
	}
}

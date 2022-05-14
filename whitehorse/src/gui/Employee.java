package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.impl.BillDaoSqllmpl;
import dao.impl.EmployeeDaoSqlImpl;
import dao.impl.FeedbackDaoSqllmpl;
import dao.impl.WorkerDaoSqlImpl;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import entity.Bill;
import entity.Custom;
import entity.Depart;
import entity.User;
import entity.Worker;
public class Employee extends JFrame implements ActionListener{
    User user;
	Bill bill;
    JPanel contentPane;
	JTable table,table6;
	JButton Workend;
	private JTable endtable;
	private JTable starttable;
	private JTable salarytable;
	JLabel lblNewLabel_2;
	JLabel yhm;
	JLabel lblNewLabel_2_1;
	JLabel lblNewLabel_2_1_1,salary;
	JTextField textField,textField_1,employeeworkend;
	private JTextField workername;
	private JTextField userphone;
	JButton employeeinforupdate;
	JButton EmployeeSalary;
	JButton EmployeeInformation;
	JButton Workquery;
	JButton Workstart,SalaryQuery;
	JLabel depart,Time;
	EmployeeDaoSqlImpl employeedaosqlimpl;
	WorkerDaoSqlImpl workerdaosqlimpl;
	Worker worker;
	JTabbedPane tabbedPane;
	JButton EmployeeWorknow;
	JButton EmployeeWorkEnd,userfeed,selectfeed;
	BillDaoSqllmpl billDaoSqllmpl;
	FeedbackDaoSqllmpl feedbackDaoSqllmpl;
	DefaultTableCellRenderer r = new DefaultTableCellRenderer();
	String[] columnNames = { "账单编号", "产品名","部门", "服务员", "金额", "完成时间" };
	String[] columnNames1 = { "账单编号", "产品名","部门", "金额", "完成时间","是否支付" };
	String[] columnNames6={ "反馈编号", "反馈内容", "反馈时间","反馈人ID","反馈对象"};
	private JLabel lblNewLabel;
	private JTextField startbillid;
	CRMThread t1;
    public static void main(String[] args) {
        
    }
    public Employee(User user){
        this.user=user;
        init();
        employeedaosqlimpl=new EmployeeDaoSqlImpl();
        workerdaosqlimpl=new WorkerDaoSqlImpl();
		billDaoSqllmpl=new BillDaoSqllmpl();
		feedbackDaoSqllmpl=new FeedbackDaoSqllmpl();
        worker=new Worker();
        bill=new Bill();
        setTitle("员工-"+user.getUname());
        setBounds(100, 100, 800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.white);
		employeeinformationset();

		t1=new CRMThread(this,user.getUname(),Time);
		//t1=new CRMThread(this,user.getUname());
		t1.start();
        setVisible(true);

    }
    public void employeeinformationset()// 获取用户资料
	{
		if (workerdaosqlimpl.SelectWorkerInfo(user)) {
			ResultSet employeevalue = workerdaosqlimpl.value();
			try {
				if (employeevalue.next()) {
					if(employeevalue.getString(3)==null)
					{
						worker.setWorkname("");
					}
					else
					{
						worker.setWorkname(employeevalue.getString(2));
					}
					worker.setWorkdepart(employeevalue.getString(3));
					depart.setText(worker.getWorkdepart());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}		
			}
		//控件赋值
		yhm.setText(user.getUname());
		workername.setText(worker.getWorkname());
		userphone.setText(user.getUphone());

	}
    public void init()
    {
        contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    tabbedPane.setForeground(Color.GREEN);
	    tabbedPane.setBackground(Color.GREEN);
		tabbedPane.setBounds(138, -33, 644, 486);
		contentPane.add(tabbedPane);

		JLabel label1 = new JLabel("个人信息");
		tabbedPane.addTab("个人信息",employeeinformation());
		tabbedPane.setBackground(Color.WHITE);
		EmployeeInformation = new JButton("个人信息");
        EmployeeInformation.addActionListener(this);
        EmployeeInformation.setBounds(0, 0, 140, 37);
        contentPane.add(EmployeeInformation);
        
		JLabel label2 = new JLabel("查看工资记录");
		tabbedPane.addTab("查看工资记录",employeesalary());
		tabbedPane.setBackground(Color.WHITE);
		EmployeeSalary = new JButton("查看工资记录");
        EmployeeSalary.addActionListener(this);
        EmployeeSalary.setBounds(0, 34, 140, 37);
        contentPane.add(EmployeeSalary);
        
		JLabel label3 = new JLabel("待接订单");
		tabbedPane.addTab("待接订单",employeeworknow());
		tabbedPane.setBackground(Color.WHITE);
		EmployeeWorknow = new JButton("待接订单");
        EmployeeWorknow.addActionListener(this);
        EmployeeWorknow.setBounds(0, 69, 140, 37);
        contentPane.add(EmployeeWorknow);
        
		JLabel label4 = new JLabel("当前已接单");
		tabbedPane.addTab("当前已接单",employeeworkend());
		tabbedPane.setBackground(Color.WHITE);
		EmployeeWorkEnd = new JButton("当前已接单");
        EmployeeWorkEnd.addActionListener(this);
        EmployeeWorkEnd.setBounds(0, 103, 140, 37);
		contentPane.add(EmployeeWorkEnd);

		userfeed = new JButton("用户反馈");
		userfeed.setBounds(0, 139, 140, 37);
		tabbedPane.addTab("当前已接单",feed());
		tabbedPane.setBackground(Color.WHITE);
		userfeed.addActionListener(this);
        contentPane.add(userfeed);
		Time = new JLabel("时间");
		Time.setBounds(5, 371, 128, 18);
		contentPane.add(Time);
        JLabel updatepwd = new JLabel("修改密码");
        updatepwd.setBounds(32, 399, 72, 18);
        updatepwd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new updatepwd(user);
            }
        });
        contentPane.add(updatepwd);

        JLabel employeeexit = new JLabel("\u9000\u51FA\u8D26\u6237");
        employeeexit.setBounds(32, 422, 72, 18);
        employeeexit.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
                setVisible(false);
				new Login();
				t1.Stop();
				t1=null;
				dispose();
        	}
        });
        contentPane.add(employeeexit);
	}
    private Component feed() {
		JPanel c6 = new JPanel();
		c6.setBorder(null);
		c6.setLayout(null);
		selectfeed = new JButton("查询所有反馈");
		selectfeed.addActionListener(this);
		selectfeed.setBounds(411, 29, 150, 32);
		c6.add(selectfeed);
		
		
		table6 = new JTable();
		table6.setDefaultRenderer(Object.class, r);
		JScrollPane js = new JScrollPane(table6);
		js.setBounds(0, 70, 635, 388);
		c6.add(js);

		return c6;
    }
	private Component employeeinformation() {
		JPanel c1 = new JPanel();
		c1.setBorder(null);
		c1.setLayout(null);
		
		lblNewLabel_2 = new JLabel("用户名：");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(189, 109, 75, 32);
		c1.add(lblNewLabel_2);
		
		yhm = new JLabel((String) null);
		yhm.setFont(new Font("宋体", Font.PLAIN, 16));
		yhm.setBounds(265, 109, 190, 32);
		c1.add(yhm);
		
		lblNewLabel_2_1 = new JLabel("姓名：");
		lblNewLabel_2_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(205, 144, 55, 32);
		c1.add(lblNewLabel_2_1);
		
		workername = new JTextField();
		workername.setText((String) null);
		workername.setColumns(10);
		workername.setBounds(265, 143, 144, 30);
		c1.add(workername);
		
		lblNewLabel_2_1_1 = new JLabel("电话号码：");
		lblNewLabel_2_1_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2_1_1.setBounds(177, 186, 87, 32);
		c1.add(lblNewLabel_2_1_1);
		
		userphone = new JTextField();
		userphone.setText((String) null);
		userphone.setColumns(10);
		userphone.setBounds(265, 186, 144, 30);
		c1.add(userphone);
		
		employeeinforupdate = new JButton("保存");
		employeeinforupdate.setFont(new Font("宋体", Font.PLAIN, 16));
		employeeinforupdate.setBounds(248, 291, 104, 38);
		employeeinforupdate.addActionListener(this);
		c1.add(employeeinforupdate);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("所属部门：");
		lblNewLabel_2_1_1_1.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel_2_1_1_1.setBounds(177, 228, 87, 32);
		c1.add(lblNewLabel_2_1_1_1);

		depart = new JLabel((String) null);
		depart.setFont(new Font("宋体", Font.PLAIN, 16));
		depart.setBounds(265, 228, 190, 32);
		c1.add(depart);
		return c1;
	}
    private Component employeesalary() {
		JPanel c2 = new JPanel();
		c2.setBorder(null);
		c2.setLayout(null);
		SalaryQuery = new JButton("手动刷新");
		SalaryQuery.setBounds(349, 20, 123, 32);
		SalaryQuery.addActionListener(this);
		c2.add(SalaryQuery);
		JLabel lblNewLabel = new JLabel("总工资：");
		lblNewLabel.setBounds(46, 29, 54, 15);
		c2.add(lblNewLabel);
		
	    salary = new JLabel("0");
		salary.setBounds(109, 29, 230, 15);
		c2.add(salary);

		salarytable = new JTable();
		//JFrame table必须包在JScrollPane内否则不显示标题 setDefaultRenderer居中
		salarytable.setDefaultRenderer(Object.class,r);
		JScrollPane js2 = new JScrollPane(salarytable);
		js2.setBounds(0, 70, 635, 388);
		c2.add(js2);
		return c2;
	}
    private Component employeeworknow() {
		JPanel c3 = new JPanel();
		c3.setBorder(null);
		c3.setLayout(null);
		Workquery = new JButton("查询");
		Workquery.setBounds(302, 25, 123, 32);
		Workquery.addActionListener(this);
		c3.add(Workquery);

		Workstart = new JButton("接单");
		Workstart.setBounds(443, 25, 123, 32);
		Workstart.addActionListener(this);
		c3.add(Workstart);
		
		starttable = new JTable();
		//JFrame table必须包在JScrollPane内否则不显示标题 setDefaultRenderer居中
		starttable.setDefaultRenderer(Object.class,r);
		starttable.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				startbillid.setText(starttable.getValueAt(starttable.getSelectedRow(), 0).toString());
			}
		});
		JScrollPane js1 = new JScrollPane(starttable);
		js1.setBounds(0, 70, 635, 388);
		c3.add(js1);
		
		lblNewLabel = new JLabel("账单编号：");
		lblNewLabel.setBounds(74, 32, 82, 18);
		c3.add(lblNewLabel);
		
		startbillid = new JTextField();
		startbillid.setBounds(148, 27, 112, 28);
		c3.add(startbillid);
		startbillid.setColumns(10);

		return c3;
	}
    private Component employeeworkend() {
		JPanel c4 = new JPanel();
		c4.setBorder(null);
		c4.setLayout(null);
		
		Workend = new JButton("完成订单");
		Workend.setBounds(409, 25, 123, 32);
		Workend.addActionListener(this);
		c4.add(Workend);
		
		endtable = new JTable();
		//JFrame table必须包在JScrollPane内否则不显示标题 setDefaultRenderer居中
		endtable.setDefaultRenderer(Object.class, r);
		endtable.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				employeeworkend.setText(endtable.getValueAt(endtable.getSelectedRow(), 0).toString());
			}
		});
		JScrollPane js3= new JScrollPane(endtable);
		js3.setBounds(0, 70, 635, 388);
		c4.add(js3);
		
		JLabel lblNewLabel_1 = new JLabel("订单编号：");
		lblNewLabel_1.setBounds(78, 32, 88, 18);
		c4.add(lblNewLabel_1);
		
		employeeworkend = new JTextField();
		employeeworkend.setBounds(153, 25, 131, 32);
		c4.add(employeeworkend);
		employeeworkend.setColumns(10);
		return c4;
	}
    @Override
    public void actionPerformed(ActionEvent e) {
        t1.cz();
    	if(e.getSource()==EmployeeInformation)
        {
    		tabbedPane.setSelectedIndex(0);
			employeeinformationset();
		}
		if(e.getSource()==employeeinforupdate)
		{	
			if(workername.getText().length()>0&&userphone.getText().length()>0)
			{
				worker.setWorkname(workername.getText());
				user.setUphone(userphone.getText());
				if(workerdaosqlimpl.UpdateWorkerInfo(user, worker))
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
		else if(e.getSource()==EmployeeSalary || e.getSource()==SalaryQuery)
		{
			salary.setText(String.valueOf(workerdaosqlimpl.QueryEmployeeBill(worker)));
			if(!"-1".equals(salary.getText()))
			{
				salarytable.setModel(new DefaultTableModel(workerdaosqlimpl.returntablevalue(), columnNames1));
			}
			else
			{
				salarytable.setModel(new DefaultTableModel(null, columnNames1));
			}
			tabbedPane.setSelectedIndex(1);
		}
		else if(e.getSource()==EmployeeWorknow || e.getSource()==Workquery)
		{
			selectbill();
			tabbedPane.setSelectedIndex(2);
		}
		else if(e.getSource()==Workstart)
		{
			if(startbillid.getText().length()>0 && isNum(startbillid.getText())==true)
			{
				if(Integer.parseInt(startbillid.getText())>0)
				{
					bill.setBillid(Integer.parseInt(startbillid.getText()));
					if(billDaoSqllmpl.UpdateEmployeeBill(worker, bill))
					{
						JOptionPane.showMessageDialog(null,"您已成功接单，尽快完成任务!");
						selectbill();
						startbillid.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null,"接单失败！");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"账单编号未输入正确！");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"账单编号未输入正确！");
			}
		}
		else if(e.getSource()==EmployeeWorkEnd) {
			selectend();
			tabbedPane.setSelectedIndex(3);
		}
		else if(e.getSource()==Workend)
		{
			if(employeeworkend.getText().length()>0 && isNum(employeeworkend.getText())==true)
			{
				if(Integer.parseInt(employeeworkend.getText())>0)
				{
					bill.setBillid(Integer.parseInt(employeeworkend.getText()));
					if(billDaoSqllmpl.UpdateEmployeeBillend(bill))
					{
						selectend();
						JOptionPane.showMessageDialog(null,"恭喜你，你已经成功完成任务!");
						employeeworkend.setText("");
					}
					else
					{
						JOptionPane.showMessageDialog(null,"完单失败！");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"账单编号未输入正确！");
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"账单编号未输入正确！");
			}
		}
		else if(e.getSource()==userfeed||e.getSource()==selectfeed)
		{
			if(feedbackDaoSqllmpl.SelectFeed(worker.getWorkname()))
			{
				table6.setModel(new DefaultTableModel(feedbackDaoSqllmpl.returntablevalue(), columnNames6));
			}
			else
			{
				JOptionPane.showMessageDialog(null,"没有查到相关记录！");
			}
			tabbedPane.setSelectedIndex(4);
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
	public void selectend()
	{
		if(workerdaosqlimpl.QueryEmployeeworkend(worker))
		{
			endtable.setModel(new DefaultTableModel(workerdaosqlimpl.returntablevalue(), columnNames));
		}
		else
		{
			endtable.setModel(new DefaultTableModel(null, columnNames));
		}
	}
	public void selectbill()
	{
		if(workerdaosqlimpl.QueryCustomBill(worker))
		{
			starttable.setModel(new DefaultTableModel(workerdaosqlimpl.returntablevalue(), columnNames));
		}
		else
		{
			starttable.setModel(new DefaultTableModel(null, columnNames));
		}
	}
}

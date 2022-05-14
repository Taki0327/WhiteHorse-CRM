package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.impl.BillDaoSqllmpl;
import dao.impl.CustomDaoSqllmpl;
import dao.impl.DepartDaoSqllmpl;
import dao.impl.FeedbackDaoSqllmpl;
import dao.impl.ProductDaoSqllmpl;
import dao.impl.UserDaoSqlImpl;
import dao.impl.WorkerDaoSqlImpl;
import entity.Depart;
import entity.Product;
import entity.User;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class admin extends JFrame implements ActionListener {
	User user;
	JPanel contentPane;
	JTabbedPane tabbedPane;
	JTable table, table1, table2, table3, table4, table5, table6;
	JComboBox<Depart> comboBox;
	JTextField Client, textField, textField_1, textField_2, textField3, productname, productprice, feedinf, worktxt;
	JButton QueryClient, Clientshopping, QueryC, selectbill, printbill, selectbill2, selectuser, ClientRemove,
			ProductAdd, ProductRemove, selectProduct, Productinfo, selectfeed, WorkerMananger, userfeed, insertadmin,
			selectwork;
	CustomDaoSqllmpl customDaoSqllmpl;
	JLabel Time;
	BillDaoSqllmpl billDaoSqllmpl;
	UserDaoSqlImpl userDaoSqlImpl;
	ProductDaoSqllmpl productDaoSqllmpl;
	DepartDaoSqllmpl departDaoSqllmpl;
	FeedbackDaoSqllmpl feedbackDaoSqllmpl;
	WorkerDaoSqlImpl workerDaoSqlImpl;
	Depart qxz = new Depart(0, "<-!请选择!->");
	CRMThread t1;
	PrintStream ps;
	DefaultTableCellRenderer r = new DefaultTableCellRenderer();
	String[] columnNames = { "客户编号", "用户名", "姓名", "上次消费时间", "消费总额", "电话号码" };
	String[] columnNames2 = { "订单编号", "客户编号", "产品名", "部门编号", "负责人", "下单时间", "完成时间", "账单金额", "支付状态" };
	String[] columnNames3 = { "用户编号", "用户名", "密码", "电话号码", "用户分类" };
	String[] columnNames4 = { "产品编号", "产品名", "价格", "部门" };
	String[] columnNames5 = { "职工编号", "用户编号", "姓名", "部门", "电话", "工资" };
	String[] columnNames6 = { "反馈编号", "反馈内容", "反馈时间", "反馈人ID", "反馈对象" };

	public static void main(String[] args) {
		// new admin(null);
	}

	public admin(User user) {
		this.user = user;
		customDaoSqllmpl = new CustomDaoSqllmpl();
		billDaoSqllmpl = new BillDaoSqllmpl();
		userDaoSqlImpl = new UserDaoSqlImpl();
		productDaoSqllmpl = new ProductDaoSqllmpl();
		departDaoSqllmpl = new DepartDaoSqllmpl();
		feedbackDaoSqllmpl = new FeedbackDaoSqllmpl();
		workerDaoSqlImpl = new WorkerDaoSqlImpl();
		r.setHorizontalAlignment(JLabel.CENTER);
		init();
		setTitle("超级管理员-" + user.getUname());
		setBounds(100, 100, 800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.white);
		allcus("all");

		t1 = new CRMThread(this, user.getUname(), Time);
		// t1=new CRMThread(this,user.getUname());
		t1.start();

		setVisible(true);
	}

	// 全体用户
	public void allcus(String user) {
		if (customDaoSqllmpl.SelectAllCustomInfo(user)) {
			table1.setModel(new DefaultTableModel(customDaoSqllmpl.returntablevalue(), columnNames));
		} else {
			JOptionPane.showMessageDialog(null, "没有查到相关记录！");
		}
	}

	public void allcusbill(String user, int ispaid) {
		if (billDaoSqllmpl.SelectAllBill(user, ispaid)) {
			table2.setModel(new DefaultTableModel(billDaoSqllmpl.returntablevalue(), columnNames2));
		} else {
			JOptionPane.showMessageDialog(null, "没有查到相关记录！");
		}
	}

	public void alluser(String user) {
		if (userDaoSqlImpl.SelectAllUser(user)) {
			table3.setModel(new DefaultTableModel(userDaoSqlImpl.returntablevalue(), columnNames3));
		} else {
			JOptionPane.showMessageDialog(null, "没有查到相关记录！");
		}
	}

	public void init() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(Color.GREEN);
		tabbedPane.setBackground(Color.GREEN);
		tabbedPane.setBounds(138, -33, 644, 486);
		contentPane.add(tabbedPane);

		JLabel label1 = new JLabel("1");
		tabbedPane.addTab("1", queryclient());
		tabbedPane.setBackground(Color.WHITE);
		QueryClient = new JButton("查询客户基本信息");
		QueryClient.addActionListener(this);
		QueryClient.setBounds(0, 0, 140, 37);
		contentPane.add(QueryClient);

		JLabel label2 = new JLabel("2");
		tabbedPane.addTab("2", clientshopping());
		tabbedPane.setBackground(Color.WHITE);
		Clientshopping = new JButton("全部订单");
		Clientshopping.addActionListener(this);
		Clientshopping.setBounds(0, 35, 140, 37);
		contentPane.add(Clientshopping);

		JLabel label3 = new JLabel("3");
		tabbedPane.addTab("3", clientaddrem());
		tabbedPane.setBackground(Color.WHITE);
		ClientRemove = new JButton("用户信息");
		ClientRemove.addActionListener(this);
		ClientRemove.setBounds(0, 70, 140, 37);
		contentPane.add(ClientRemove);

		JLabel label4 = new JLabel("4");
		tabbedPane.addTab("4", producttaddrem());
		Productinfo = new JButton("产品管理");
		Productinfo.addActionListener(this);
		Productinfo.setBounds(0, 105, 140, 37);
		contentPane.add(Productinfo);

		JLabel label6 = new JLabel("5");
		tabbedPane.addTab("5", querycomplaint());
		WorkerMananger = new JButton("职工管理");
		WorkerMananger.addActionListener(this);
		WorkerMananger.setBounds(0, 141, 140, 37);
		contentPane.add(WorkerMananger);

		JLabel label7 = new JLabel("6");
		tabbedPane.addTab("6", feedback());
		userfeed = new JButton("用户反馈");
		userfeed.addActionListener(this);
		userfeed.setBounds(0, 176, 140, 37);
		contentPane.add(userfeed);

		printbill = new JButton("生成全部账单(txt)");
		printbill.addActionListener(this);
		printbill.setBounds(0, 211, 140, 37);
		contentPane.add(printbill);

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

		JLabel lblNewLabel = new JLabel("退出账户");
		lblNewLabel.setBounds(31, 422, 72, 18);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				new Login();
				t1.Stop();
				t1=null;
				dispose();
			}
		});
		contentPane.add(lblNewLabel);
	}

	private Component queryclient() {
		JPanel c1 = new JPanel();
		c1.setBorder(null);
		c1.setLayout(null);
		QueryC = new JButton("查询");
		QueryC.addActionListener(this);
		QueryC.setBounds(411, 29, 175, 32);
		c1.add(QueryC);

		table1 = new JTable();
		table1.setDefaultRenderer(Object.class, r);
		table1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Client.setText(table1.getValueAt(table1.getSelectedRow(), 0).toString());
				QueryC.setText("查询" + table1.getValueAt(table1.getSelectedRow(), 0).toString() + "客户的信息");
			}
		});
		JScrollPane js = new JScrollPane(table1);
		js.setBounds(0, 70, 635, 388);
		c1.add(js);

		Client = new JTextField();
		Client.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				if (Client.getText().length() > 0) {
					QueryC.setText("查询" + Client.getText() + "客户的信息");
				} else {
					QueryC.setText("查询所有客户信息");
				}
			}
		});
		Client.setBounds(155, 29, 225, 32);
		c1.add(Client);
		Client.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("用户名：");
		lblNewLabel_2.setBounds(82, 36, 72, 18);
		c1.add(lblNewLabel_2);
		return c1;
	}

	private Component clientshopping() {
		JPanel c2 = new JPanel();
		c2.setBorder(null);
		c2.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("客户编号：");
		lblNewLabel_2.setBounds(22, 35, 72, 18);
		c2.add(lblNewLabel_2);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(95, 28, 225, 32);
		textField_2.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				if (textField_2.getText().length() > 0) {
					selectbill.setText("查询" + textField_2.getText() + "的订单");
					selectbill2.setText(textField_2.getText() + "的未支付订单");
				} else {
					selectbill.setText("查询所有");
					selectbill2.setText("仅查询未支付");
				}
			}
		});
		c2.add(textField_2);

		selectbill = new JButton("查询");
		selectbill.setBounds(351, 28, 123, 32);
		selectbill.addActionListener(this);
		c2.add(selectbill);

		selectbill2 = new JButton("仅查询未支付");
		selectbill2.setBounds(488, 28, 123, 32);
		selectbill2.addActionListener(this);
		c2.add(selectbill2);
		table2 = new JTable();
		table2.setDefaultRenderer(Object.class, r);
		table2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField_2.setText(table2.getValueAt(table2.getSelectedRow(), 1).toString());
				selectbill.setText("查询" + table2.getValueAt(table2.getSelectedRow(), 1).toString() + "的订单");
				selectbill2.setText(table2.getValueAt(table2.getSelectedRow(), 1).toString() + "的未支付订单");
			}
		});
		JScrollPane js = new JScrollPane(table2);
		js.setBounds(0, 70, 635, 388);
		c2.add(js);
		return c2;
	}

	private Component clientaddrem() {
		JPanel c3 = new JPanel();
		c3.setBorder(null);
		c3.setLayout(null);

		table3 = new JTable();
		table3.setDefaultRenderer(Object.class, r);
		table3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField3.setText(table3.getValueAt(table3.getSelectedRow(), 1).toString());
				selectuser.setText("查询" + table3.getValueAt(table3.getSelectedRow(), 1).toString() + "用户的信息");
			}
		});
		JScrollPane js = new JScrollPane(table3);
		js.setBounds(0, 70, 635, 388);
		c3.add(js);

		selectuser = new JButton("查询所有");
		selectuser.addActionListener(this);
		selectuser.setBounds(358, 28, 123, 32);
		c3.add(selectuser);

		textField3 = new JTextField();
		textField3.setBounds(102, 28, 225, 32);
		textField3.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				if (textField3.getText().length() > 0) {
					selectuser.setText("查询" + textField3.getText() + "用户的信息");
				} else {
					selectuser.setText("查询所有");
				}
			}
		});
		c3.add(textField3);
		textField3.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("用户名：");
		lblNewLabel_2.setBounds(29, 35, 72, 18);
		c3.add(lblNewLabel_2);
		insertadmin = new JButton("新增管理员");
		insertadmin.setBounds(511, 28, 118, 32);
		insertadmin.addActionListener(this);
		c3.add(insertadmin);
		return c3;
	}

	private Component producttaddrem() {
		JPanel c4 = new JPanel();
		c4.setBorder(null);
		c4.setLayout(null);
		ProductAdd = new JButton("新增");
		ProductAdd.setBounds(239, 36, 123, 32);
		ProductAdd.addActionListener(this);
		ProductAdd.setEnabled(false);
		c4.add(ProductAdd);

		table4 = new JTable();
		table4.setDefaultRenderer(Object.class, r);
		table4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				productname.setText(table4.getValueAt(table4.getSelectedRow(), 1).toString());
				ProductRemove.setEnabled(true);
				selectProduct.setText("查询" + table4.getValueAt(table4.getSelectedRow(), 1).toString());
				ProductRemove.setText("删除" + table4.getValueAt(table4.getSelectedRow(), 1).toString());
			}
		});
		JScrollPane js = new JScrollPane(table4);
		js.setBounds(0, 113, 635, 345);
		c4.add(js);

		JLabel lblNewLabel_3 = new JLabel("产品名：");
		lblNewLabel_3.setBounds(37, 16, 72, 18);
		c4.add(lblNewLabel_3);

		productname = new JTextField();
		productname.setBounds(105, 13, 123, 24);
		productname.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				zsgc();
			}
		});
		c4.add(productname);
		productname.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("产品价格：");
		lblNewLabel_4.setBounds(37, 50, 99, 18);
		c4.add(lblNewLabel_4);

		productprice = new JTextField();
		productprice.setColumns(10);
		productprice.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				zsgc();
			}
		});
		productprice.setBounds(105, 48, 123, 24);
		c4.add(productprice);

		ProductRemove = new JButton("删除");
		ProductRemove.setBounds(376, 36, 123, 32);
		ProductRemove.setEnabled(false);
		ProductRemove.addActionListener(this);
		c4.add(ProductRemove);

		selectProduct = new JButton("查询所有");
		selectProduct.setBounds(512, 36, 123, 32);
		selectProduct.addActionListener(this);
		c4.add(selectProduct);

		JLabel lblNewLabel_1 = new JLabel("产品部门：");
		lblNewLabel_1.setBounds(37, 88, 65, 15);
		c4.add(lblNewLabel_1);

		comboBox = new JComboBox<Depart>();
		comboBox.setBounds(105, 80, 123, 23);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				if (qxz.equals(comboBox.getSelectedItem())) {
					table4.setModel(new DefaultTableModel(null, columnNames4));
				} else if (comboBox.getSelectedItem() != null && productname.getText().length() <= 0
						&& productprice.getText().length() <= 0) {
					allpro(comboBox.getSelectedItem().toString(), 2);
				} else {
					if (productname.getText().length() > 0 && productprice.getText().length() > 0
							&& !qxz.equals(comboBox.getSelectedItem())) {
						ProductAdd.setEnabled(true);
						ProductAdd.setText("新增" + productname.getText());
						ProductRemove.setEnabled(false);
						selectProduct.setText("查询" + productname.getText());
						ProductRemove.setText("删除" + productname.getText());
					}
				}
			}
		});
		c4.add(comboBox);
		return c4;
	}

	public void zsgc() {
		if (productname.getText().length() > 0 && productprice.getText().length() > 0
				&& !qxz.equals(comboBox.getSelectedItem())) {
			ProductAdd.setEnabled(true);
			ProductAdd.setText("新增" + productname.getText());
			ProductRemove.setEnabled(false);
			selectProduct.setText("查询" + productname.getText());
			ProductRemove.setText("删除" + productname.getText());
		} else if (productname.getText().length() > 0 && productprice.getText().length() <= 0) {
			ProductRemove.setText("删除" + productname.getText());
			ProductRemove.setEnabled(true);
			ProductAdd.setEnabled(false);
			selectProduct.setText("查询" + productname.getText());
		} else {
			selectProduct.setText("查询所有");
			ProductRemove.setEnabled(false);
			ProductAdd.setEnabled(false);
			ProductRemove.setText("删除");
			ProductAdd.setText("新增");
		}
	}

	private Component querycomplaint() {
		JPanel c5 = new JPanel();
		c5.setBorder(null);
		c5.setLayout(null);
		selectwork = new JButton("查询所有职工");
		selectwork.addActionListener(this);
		selectwork.setBounds(411, 29, 150, 32);
		c5.add(selectwork);

		worktxt = new JTextField();
		worktxt.setBounds(155, 29, 225, 32);
		c5.add(worktxt);
		worktxt.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				if (worktxt.getText().length() > 0) {
					selectwork.setText("查询" + worktxt.getText() + "职工的信息");
				} else {
					selectwork.setText("查询所有职工");
				}
			}
		});
		Client.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("模糊搜索：");
		lblNewLabel_2.setBounds(82, 36, 72, 18);
		c5.add(lblNewLabel_2);

		table5 = new JTable();
		table5.setDefaultRenderer(Object.class, r);
		table5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				worktxt.setText(table5.getValueAt(table5.getSelectedRow(), 0).toString());
				selectwork.setText("查询" + table5.getValueAt(table5.getSelectedRow(), 0).toString() + "职工的信息");
			}
		});
		JScrollPane js = new JScrollPane(table5);
		js.setBounds(0, 70, 635, 388);
		c5.add(js);
		return c5;
	}

	private Component feedback() {
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

		feedinf = new JTextField();
		feedinf.setBounds(155, 29, 225, 32);
		feedinf.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				if (feedinf.getText().length() > 0) {
					selectfeed.setText("查询包含" + feedinf.getText() + "的反馈");
				} else {
					selectfeed.setText("查询所有反馈");
				}
			}
		});
		c6.add(feedinf);
		feedinf.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("模糊搜索：");
		lblNewLabel_2.setBounds(82, 36, 72, 18);
		c6.add(lblNewLabel_2);
		return c6;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		t1.cz();
		if (e.getSource() == QueryClient) {
			allcus("all");
			tabbedPane.setSelectedIndex(0);
		} else if (e.getSource() == QueryC) {
			if (Client.getText().length() > 0) {
				allcus(Client.getText());
				Client.setText("");
				QueryC.setText("查询所有客户信息");
			} else {
				allcus("all");
			}
		} else if (e.getSource() == Clientshopping) {
			allcusbill("all", 1);
			tabbedPane.setSelectedIndex(1);
		} else if (e.getSource() == selectbill) {
			if (textField_2.getText().length() > 0) {
				allcusbill(textField_2.getText(), 1);
				textField_2.setText("");
				selectbill.setText("查询所有");
				selectbill2.setText("仅查询未支付");
			} else {
				allcusbill("all", 1);
			}
		} else if (e.getSource() == selectbill2) {
			if (textField_2.getText().length() > 0) {
				allcusbill(textField_2.getText(), 0);
				textField_2.setText("");
				selectbill.setText("查询所有");
				selectbill2.setText("仅查询未支付");
			} else {
				allcusbill("all2", 0);
			}
		} else if (e.getSource() == ClientRemove) {
			alluser("");
			tabbedPane.setSelectedIndex(2);
		} else if (e.getSource() == selectuser) {
			if (textField3.getText().length() > 0) {
				alluser(textField3.getText());
				textField3.setText("");
				selectuser.setText("查询所有");
				productname.setText("");
				ProductAdd.setEnabled(false);
				ProductAdd.setText("新增");
			} else {
				alluser("");
			}
		} else if (e.getSource() == ProductAdd) {
			Depart depart = (Depart) comboBox.getSelectedItem();
			Product product = new Product(productDaoSqllmpl.SelectProduct("all", 3) + 1, productname.getText(),
					productprice.getText(), String.valueOf(depart.getDepartid()));
			if (productDaoSqllmpl.InsertProduct(product)) {
				allpro("all", 0);
				JOptionPane.showMessageDialog(null, "添加成功!");
			} else {
				JOptionPane.showMessageDialog(null, "添加失败!");
			}
		} else if (e.getSource() == ProductRemove) {
			try {
				productDaoSqllmpl.DelProduct(productname.getText());
				allpro("all", 0);
				productname.setText("");
				ProductRemove.setEnabled(false);
				ProductRemove.setText("删除");
				selectProduct.setText("查询所有");
				JOptionPane.showMessageDialog(null, "删除成功!");
			} catch (Exception a) {
				JOptionPane.showConfirmDialog(null, "删除失败!");
			}
		} else if (e.getSource() == Productinfo) {

			comboBox.removeAllItems();
			comboBox.addItem(qxz);
			ResultSet rs = departDaoSqllmpl.SelectDepart();
			if (rs != null) {
				try {
					while (rs.next()) {
						// Depart depart=new Depart(rs.getInt(1),rs.getString(2));
						comboBox.addItem(new Depart(rs.getInt(1), rs.getString(2)));
					}
				} catch (SQLException a) {
					a.printStackTrace();
				}
			}
			allpro("all", 0);
			tabbedPane.setSelectedIndex(3);
		} else if (e.getSource() == selectProduct) {
			if (productname.getText().length() > 0) {
				allpro(productname.getText(), 1);
				productname.setText("");
				selectProduct.setText("查询所有");
				ProductRemove.setEnabled(false);
				ProductRemove.setText("删除");
			} else {
				allpro("all", 0);
			}
		} else if (e.getSource() == WorkerMananger) {
			allwork("");
			tabbedPane.setSelectedIndex(4);
		} else if (e.getSource() == selectwork) {
			allwork(worktxt.getText());
			selectwork.setText("查询所有职工");
			worktxt.setText("");
		} else if (e.getSource() == userfeed) {
			allfeed("");
			tabbedPane.setSelectedIndex(5);
		} else if (e.getSource() == selectfeed) {
			allfeed(feedinf.getText());
			selectfeed.setText("查询所有反馈");
			feedinf.setText("");
		} else if (e.getSource() == insertadmin) {
			new Reg("amdin");
		} else if (e.getSource() == printbill) {
			String filename = "D:/总账单.txt";
			File file = new File(filename);
			File fileparent = file.getParentFile();
			if (!fileparent.exists()) {
				fileparent.mkdirs();
			}
			try {
				// ps = new PrintStream(new FileOutputStream(file, true));
				ps = new PrintStream(new FileOutputStream(file));
				for (String title : columnNames2) {
					ps.print(title + "  ");
				}
				ps.println();
				if (billDaoSqllmpl.SelectAllBill("all", 1)) {
					for (String[] bill : billDaoSqllmpl.returntablevalue()) {
						for (String txt : bill) {
							ps.print(txt + " ");
						}
						ps.println();
					}
					JOptionPane.showMessageDialog(null, "生成成功！");
					Desktop.getDesktop().open(file);
				} else {
					JOptionPane.showMessageDialog(null, "没有查到相关记录！");
				}
				ps.close();
			} catch (FileNotFoundException e1) {
				ps.close();
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public void allpro(String pro,int method) {
		if(productDaoSqllmpl.SelectProduct(pro,method)>0)
		{
			table4.setModel(new DefaultTableModel(productDaoSqllmpl.returntablevalue(), columnNames4));
		}
		else
		{
			JOptionPane.showMessageDialog(null,"没有查到相关记录！");
		}
	}
	public void allfeed(String feedtxt) {
		if(feedbackDaoSqllmpl.SelectFeed(feedtxt))
		{
			table6.setModel(new DefaultTableModel(feedbackDaoSqllmpl.returntablevalue(), columnNames6));
		}
		else
		{
			JOptionPane.showMessageDialog(null,"没有查到相关记录！");
		}
	}
	public void allwork(String work) {
		if(workerDaoSqlImpl.SelectAllWorker(work))
		{
			table5.setModel(new DefaultTableModel(workerDaoSqlImpl.returntablevalue(), columnNames5));
		}
		else
		{
			JOptionPane.showMessageDialog(null,"没有查到相关记录！");
		}
	}
}

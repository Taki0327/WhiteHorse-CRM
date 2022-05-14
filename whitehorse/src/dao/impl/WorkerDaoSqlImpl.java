package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import dao.JDBCUtil;
import dao.WorkerDao;
import entity.Bill;
import entity.Custom;
import entity.User;
import entity.Worker;

public class WorkerDaoSqlImpl extends JDBCUtil implements WorkerDao {
	ResultSet employeeinformation;
	String[][] tablevalue;
	//查询客户信息
	public boolean SelectWorkerInfo(User user) {
		String sqlString="select workid,workname,departname,workprice from worker,depart where userid=? and worker.workdepart=depart.departid";
		Object [] params={user.getUserid()};
		ResultSet rs = this.executeQuery2(sqlString, params);
		try {
			rs.last();
			if(rs.getRow()>0)
			{
				rs.beforeFirst();
				employeeinformation=rs;
				return true;
			}
			else
			{
				return false;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//保存用户修改的资料
	@Override
	public boolean UpdateWorkerInfo(User user,Worker worker) {
		int list;
		String sqlString="update user,worker set uphone=?,workname=? where user.userid=worker.userid and user.userid=?";
		Object [] params={user.getUphone(),worker.getWorkname(),user.getUserid()};
		list=this.executeUpdate(sqlString, params);
		if(list>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public ResultSet value() {
		// TODO Auto-generated method stub
		return employeeinformation;
	}
	
   public String[][] returntablevalue()
   {
       
       return tablevalue;
   }
	
	@Override
	public boolean InsertWorker(User user, String departname) {
		int list;
		String sqlString="INSERT into worker(userid,workname,workdepart,workprice) VALUES('"+user.getUserid()+"','此处填入您的姓名','"+departname+"','0')";
		list=this.executeUpdate(sqlString);
		if(list>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	 
	//查询用户订单下单
    public boolean QueryCustomBill(Worker worker) {
        //List<HashMap> list=new ArrayList<>();
        String sqlString="select bill.billid,bill.proname,depart.departname,bill.workname,bill.billmoney,bill.finishtime from worker,bill,depart where worker.workdepart=depart.departid and worker.workdepart=bill.departid  and  bill.workname='待接单' and worker.workname=?";
        Object [] params={worker.getWorkname()};
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            tablevalue = new String[rs.getRow()][6];
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                tablevalue[i][0]=rs.getString(1);
                tablevalue[i][1]=rs.getString(2);
                tablevalue[i][2]=rs.getString(3);
                tablevalue[i][3]=rs.getString(4);
                tablevalue[i][4]=rs.getString(5);
                tablevalue[i][5]=rs.getString(6);
                i++;
            }
            return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
  //查询职工账单
    public int QueryEmployeeBill(Worker worker) {
        //List<HashMap> list=new ArrayList<>();
        String sqlString="select bill.billid,bill.proname,depart.departname,bill.billmoney,bill.finishtime,ispaid FROM bill,depart WHERE bill.workname = ? and bill.departid=depart.departid";
        Object [] params={worker.getWorkname()};
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            tablevalue = new String[rs.getRow()][6];
            rs.beforeFirst();
			int i=0,salary=0;
            while(rs.next())
            {
                tablevalue[i][0]=rs.getString(1);
                tablevalue[i][1]=rs.getString(2);
                tablevalue[i][2]=rs.getString(3);
                tablevalue[i][3]=rs.getString(4);
				tablevalue[i][4]=rs.getString(5);
				if("0".equals(rs.getString(6)))
				{
					tablevalue[i][5]="未支付";
				}
				else
				{
					tablevalue[i][5]="已支付";
					salary+=rs. getInt(4);
				}
                i++;
			}
			sqlString="update worker set workprice="+salary+" where workname='"+worker.getWorkname()+"'";
			if(this.executeUpdate(sqlString)>0)
			{
				return salary;
			}
			else
			{
				return -1;
			}
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
	//查询职工工作结束
	public boolean QueryEmployeeworkend(Worker worker) {
		//List<HashMap> list=new ArrayList<>();
		String sqlString = "select bill.billid,bill.proname,depart.departname,bill.workname,bill.billmoney,bill.finishtime from bill,depart where bill.workname=? and  finishtime='未完成'  and depart.departid=bill.departid";
		Object[] params = {worker.getWorkname() };
		ResultSet rs = this.executeQuery2(sqlString, params);
		try {
			rs.last();
			tablevalue = new String[rs.getRow()][6];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				tablevalue[i][0] = rs.getString(1);
				tablevalue[i][1] = rs.getString(2);
				tablevalue[i][2] = rs.getString(3);
				tablevalue[i][3] = rs.getString(4);
				tablevalue[i][4] = rs.getString(5);
				tablevalue[i][5] = rs.getString(6);
				i++;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean SelectAllWorker(String work) {
		String sqlString="";
		Object [] params={};
		if(!"".equals(work))
		{
			params=new Object[]{"%"+work+"%","%"+work+"%","%"+work+"%"}; 
			sqlString="select workid,`user`.userid,workname,departname,uphone,workprice from worker,depart,user where (worker.userid=user.userid and worker.workdepart=depart.departid) and (worker.userid like ? or workid like ? or workname like ?)";
		}
		else
		{
			sqlString="select workid,`user`.userid,workname,departname,uphone,workprice from worker,depart,user where worker.userid=user.userid and worker.workdepart=depart.departid";
		}
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            tablevalue = new String[rs.getRow()][6];
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                tablevalue[i][0]=rs.getString(1);
                tablevalue[i][1]=rs.getString(2);
                tablevalue[i][2]=rs.getString(3);
                tablevalue[i][3]=rs.getString(4);
                tablevalue[i][4]=rs.getString(5);
                tablevalue[i][5]=rs.getString(6);
                i++;
            }
            return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
}
 

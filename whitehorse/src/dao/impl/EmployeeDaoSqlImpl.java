package dao.impl;

import java.util.*;

import dao.JDBCUtil;
import dao.EmployeeDao;
import entity.User;
import entity.Worker;

public class EmployeeDaoSqlImpl extends JDBCUtil implements EmployeeDao{
	HashMap employeeinformation;
	String[][] tablevalue;
    public boolean Information(User user) {
        List<HashMap> list=new ArrayList<>();
        String sqlString="select * from worker where userid=?";
        Object [] params={user.getUserid()};
        list=this.executeQuery(sqlString, params);
        if(list.size()>0)
        {
            for (HashMap key : list) {
            	employeeinformation=key;
            }
            return true;
        }
        else
        {
            sqlString="INSERT into worker(userid,workname,workdepart,workprice) VALUES('"+user.getUserid()+"','此处填入您的姓名','2020年1月6日','0')";
            if(this.executeUpdate(sqlString)>0)
            {
                return Information(user);
            }
            {
                return false;
            }
        }
    }
    //返回用户资料的哈希表
    public HashMap value()
    {
        return employeeinformation;
    }
    //保存用户修改的资料
    public boolean Employeeinformationupdate(User user,Worker worker)
    {
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
    
        
}

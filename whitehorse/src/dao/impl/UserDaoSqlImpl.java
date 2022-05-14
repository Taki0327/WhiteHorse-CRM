package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


import dao.JDBCUtil;
import dao.UserDao;
import entity.User;

public class UserDaoSqlImpl extends JDBCUtil implements UserDao {
    //HashMap uservalue;
    ResultSet uservalue;
    String[][] tablevalue;
    @Override//登陆
    public boolean SelectUser(User user) {
        //List<HashMap> list=new ArrayList<>();
        String sqlString="select * from user where uname=? and upwd=?";
        Object [] params={user.getUname(),user.getUpwd()};
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            if(rs.getRow()>0)
            {
                rs.beforeFirst();
                uservalue=rs;
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
        /*list=this.executeQuery(sqlString, params);
        if(list.size()>0)
        {
            for (HashMap key : list) {
                uservalue=key;
            }
            return true;
        }
        else
        {
            return false;
        }*/
    }
    public boolean SelectAllUser(String user) {
        String sqlString="";
        Object [] params={};
        if("".equals(user))
        {
            sqlString="select * from user";
        }
        else
        {
            params=new Object[]{user,user}; 
            sqlString="select * from user where uname=? or userid=?";
        }
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            tablevalue = new String[rs.getRow()][5];
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                tablevalue[i][0]=rs.getString(1);
                tablevalue[i][1]=rs.getString(2);
                tablevalue[i][2]=rs.getString(3);
                tablevalue[i][3]=rs.getString(4);
                if("1".equals(rs.getString(5)))
                {
                   tablevalue[i][4]="客户";
                }
                else if("2".equals(rs.getString(5)))
                {
                   tablevalue[i][4]="职工";
                }
                else
                {
                   tablevalue[i][4]="管理员";
                }
                i++;
            }
            return true;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String[][] returntablevalue()
    {
        
        return tablevalue;
    }
    //返回登陆查询的哈希表
    /*public HashMap value()
    {
        return uservalue;
    }*/
    public ResultSet value()
    {
        return uservalue;
    }
    @Override//注册 插入数据
    public boolean InsertUser(User user) {
        int list;
        String sqlString="INSERT into user(uname,upwd,uphone,uide) VALUES(?,?,?,?)";
        Object [] params={user.getUname(),user.getUpwd(),user.getUphone(),user.getUide()};
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

    @Override//修改密码
    public boolean Updatepwd(User user) {
        int list;
        String sqlString="update user set upwd=? where userid=?";
        Object [] params={user.getUpwd(),user.getUserid()};
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

    @Override
    public boolean CountUser(User user) {
        String sqlString="select count(*) from user where uname=?";
        Object [] params={user.getUname()};
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            if(rs.next()&&rs.getInt(1)>0)
            {
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
}

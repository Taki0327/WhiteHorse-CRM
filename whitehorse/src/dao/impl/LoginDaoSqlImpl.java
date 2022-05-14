package dao.impl;

import java.util.*;

import dao.JDBCUtil;
import dao.LoginDao;
import entity.User;

public class LoginDaoSqlImpl extends JDBCUtil implements LoginDao {
    HashMap uservalue;
    @Override//登陆
    public boolean login(User user) {
        List<HashMap> list=new ArrayList<>();
        String sqlString="select * from user where uname=? and upwd=?";
        Object [] params={user.getUname(),user.getUpwd()};
        list=this.executeQuery(sqlString, params);
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
        }
    }
    //返回登陆查询的哈希表
    public HashMap value()
    {
        return uservalue;
    }
    @Override//注册 插入数据
    public boolean Reg(User user) {
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
}

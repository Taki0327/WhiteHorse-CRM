package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import dao.JDBCUtil;
import dao.CustomDao;
import entity.User;
import entity.Custom;

public class CustomDaoSqllmpl extends JDBCUtil implements CustomDao{
    //HashMap userinformation;
    ResultSet userinformation;
    String[][] tablevalue;
    //查询客户信息
    @Override
    public boolean SelectCustomInfo(User user) {
        //List<HashMap> list=new ArrayList<>();
        String sqlString="select * from custom where userid=?";
        Object [] params={user.getUserid()};
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            if(rs.getRow()>0)
            {
                rs.beforeFirst();
                userinformation=rs;
                return true;
            }
            else
            {
                sqlString="INSERT into custom(userid,cusname,custime,cusconsum) VALUES('"+user.getUserid()+"','此处填入您的姓名','暂无消费','0')";
                if(this.executeUpdate(sqlString)>0)
                {
                    return SelectCustomInfo(user);
                }
                else
                {
                    return false;
                }
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
                userinformation=key;
            }
            return true;
        }
        else
        {
            sqlString="INSERT into custom(userid,cusname,custime,cusconsum) VALUES('"+user.getUserid()+"','此处填入您的姓名','暂无消费','0')";
            if(this.executeUpdate(sqlString)>0)
            {
                return SelectCustomInfo(user);
            }
            {
                return false;
            }
        }*/
    }
    //查询全部客户信息
    @Override
    public boolean SelectAllCustomInfo(String user) {
        //List<HashMap> list=new ArrayList<>();
        String sqlString="";
        Object [] params={};
        if("all".equals(user))
        {
            sqlString="select cusid,user.uname,cusname,custime,cusconsum,uphone from custom,user where user.userid=custom.userid";
        }
        else
        {
            params=new Object[]{user,user,user}; 
            sqlString="select cusid,user.uname,cusname,custime,cusconsum,uphone from custom,user where user.userid=custom.userid and (user.uname=? or cusid=? or cusname=?)";
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
                if("此处填入您的姓名".equals(rs.getString(3)))
                {
                    tablevalue[i][2]="客户尚未登记";
                }
                else
                {
                    tablevalue[i][2]=rs.getString(3);
                }
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
        /*list=this.executeQuery(sqlString, params);
        if(list.size()>0)
        {
            tablevalue = new String[list.size()][6];
            Hashdouble(list);
            return true;
        }
        else
        {
            return false;
        }*/
    }
      //哈希表列表转二维数组 已弃用
      public void Hashdouble(List<HashMap> list)
      {
          int i=0,j=1;
          for (HashMap key : list) {
              Set keyset = key.keySet();
              for(Object kObject:keyset)
              {
                  if("cusid".equals(kObject))
                  {
                      tablevalue[i][0]=String.valueOf(j++);
                  }
                  else if("uname".equals(kObject))
                  {
                    tablevalue[i][1]=key.get(kObject).toString();
                  }
                  else if("cusname".equals(kObject))
                  {
                    if("此处填入您的姓名".equals(key.get(kObject).toString()))
                    {
                        tablevalue[i][2]="客户尚未登记";
                    }
                    else
                    {
                        tablevalue[i][2]=key.get(kObject).toString();
                    }
                  }
                  else if("custime".equals(kObject))
                  {
                      tablevalue[i][3]=key.get(kObject).toString();
                  }
                  else if("cusconsum".equals(kObject))
                  {
                      tablevalue[i][4]=key.get(kObject).toString();
                  }
                  else if("uphone".equals(kObject))
                  {
                      tablevalue[i][5]=key.get(kObject).toString();
                  }
              }
              i++;
          }
      }
      public String[][] returntablevalue()
      {
          return tablevalue;
      }  
    /*返回用户资料的哈希表
    public HashMap value()
    {
        return userinformation;
    }*/
    public ResultSet value()
    {
        return userinformation;
    }
    //保存用户修改的资料
    @Override
    public boolean UpdateCustomInfo(User user,Custom custom) {
        int list;
        String sqlString="update user,custom set uphone=?,cusname=?,custime=?,cusconsum=? where user.userid=custom.userid and user.userid=?";
        Object [] params={user.getUphone(),custom.getCusname(),custom.getCustime(),custom.getCusconsum(),user.getUserid()};
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
    //查询用户账单总金额
    @Override
    public String SelectCustomSumBill(User user) {
        //List<HashMap> list=new ArrayList<>();
        String money="0";
        String sqlString="select SUM(billmoney) from bill where userid=? and finishtime!='未完成' and ispaid='0'";
        Object [] params={user.getUserid()};
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            if(rs.getRow()>0)
            {
                rs.beforeFirst();
                if(rs.next())
                {
                    if (rs.getString(1) != null) {
                        money = rs.getString(1);
                    }
                }
            }
            return money;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return money;
        }
        /*list=this.executeQuery(sqlString, params);
        if(list.size()>0)
        {
            for (HashMap key : list) {
                Set keyset = key.keySet();
                for (Object kObject : keyset) {
                    if ("SUM(billmoney)".equals(kObject)) {
                        if(key.get(kObject)!=null)
                        {
                            money=key.get(kObject).toString();
                        }
                    }
                }
            }
            return money;
        }
        else
        {
            return money;
        }*/
    }

   
}

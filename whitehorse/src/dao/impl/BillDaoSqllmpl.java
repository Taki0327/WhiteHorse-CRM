package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import dao.BillDao;
import dao.JDBCUtil;
import entity.Bill;
import entity.User;
import entity.Worker;

public class BillDaoSqllmpl extends JDBCUtil implements BillDao {
    String[][] tablevalue;
    Date date;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override // 消费账单
    public boolean SelectBill(User user) {
       // List<HashMap> list = new ArrayList<>();
        String sqlString = "select billid,proname,departname,workname,spendtime,finishtime,billmoney from bill,depart where userid=? and ispaid=0 and depart.departid=bill.departid";
        Object[] params = { user.getUserid() };
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            tablevalue = new String[rs.getRow()][7];
            rs.beforeFirst();
            int i=0,j=1;
            while(rs.next())
            {
                tablevalue[i][0]=String.valueOf(j++);
                tablevalue[i][1]=rs.getString(2);
                tablevalue[i][2]=rs.getString(3);
                tablevalue[i][3]=rs.getString(4);
                tablevalue[i][4]=rs.getString(5);
                tablevalue[i][5]=rs.getString(6);
                tablevalue[i][6]=rs.getString(7);
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
    
    /*public String zh(String departid)//旧写法 已弃用
    {
        if("1".equals(departid))
        {
           return "洗浴部";
        }
        else if("2".equals(departid))
        {
            return "按摩部";
        }
        else
        {
            return "养生部";
        }
    }*/
    //查询全体客户账单
    public boolean SelectAllBill(String user,int ispaid) {
        // List<HashMap> list = new ArrayList<>();
         String sqlString = "";
         Object[] params = {};
         if("all".equals(user)&&ispaid==1)
         {
             sqlString="select billid,userid,proname,departname,workname,spendtime,finishtime,billmoney,ispaid from bill,depart where depart.departid=bill.departid";
         }
         else if("all2".equals(user)&&ispaid==0)
         {
             sqlString="select billid,userid,proname,departname,workname,spendtime,finishtime,billmoney,ispaid from bill,depart where ispaid=0 and depart.departid=bill.departid";
         }
         else if(ispaid==1)
         {
             params=new Object[]{user}; 
             sqlString="select billid,userid,proname,departname,workname,spendtime,finishtime,billmoney,ispaid from bill,depart where userid=? and depart.departid=bill.departid";
         }
         else if(ispaid==0)
         {
             params=new Object[]{user}; 
             sqlString="select billid,userid,proname,departname,workname,spendtime,finishtime,billmoney,ispaid from bill,depart where userid=? and ispaid=0 and depart.departid=bill.departid";
         }
         ResultSet rs = this.executeQuery2(sqlString, params);
         try {
             rs.last();
             tablevalue = new String[rs.getRow()][9];
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
                 tablevalue[i][6]=rs.getString(7);
                 tablevalue[i][7]=rs.getString(8);
                 if("0".equals(rs.getString(9)))
                 {
                    tablevalue[i][8]="未支付";
                 }
                 else
                 {
                    tablevalue[i][8]="已支付";
                 }
                 
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
     //哈希表列表转二维数组 旧写法 已弃用
    public void Hashdouble(List<HashMap> list)
    {
        int i=0,j=1;
        for (HashMap key : list) {
            Set keyset = key.keySet();
            for(Object kObject:keyset)
            {
                if("billid".equals(kObject))
                {
                    tablevalue[i][0]=String.valueOf(j++);
                }
                else if("proname".equals(kObject))
                {
                    tablevalue[i][1]=key.get(kObject).toString();
                }
                else if("workname".equals(kObject))
                {
                    tablevalue[i][2]=key.get(kObject).toString();
                }
                else if("spendtime".equals(kObject))
                {
                    tablevalue[i][3]=key.get(kObject).toString();
                }
                else if("finishtime".equals(kObject))
                {
                    tablevalue[i][4]=key.get(kObject).toString();
                }
                else if("billmoney".equals(kObject))
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

    @Override
    public boolean InsertBill(User user,Bill bill) {
        int list;
        String sqlString="INSERT into bill(userid,proname,departid,workname,spendtime,finishtime,billmoney,ispaid) VALUES(?,?,?,'待接单',?,'未完成',?,0)";
        Object [] params={user.getUserid(),bill.getProname(),bill.getDepartid(),bill.getSpendtime(),bill.getBillmoney()};
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

    @Override //加载反馈下拉框服务人员
    public ResultSet SelectBillworkname(User user) {
        String sqlString="select DISTINCT workname from bill where userid=?";
        Object [] params={user.getUserid()};
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            if(rs.getRow()>0)
            {
                rs.beforeFirst();
                return rs;
            }
            else
            {
                return null;
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*public List SelectBillworkname(User user) {
        List<HashMap> list=new ArrayList<>();
        String sqlString="select workname from bill where userid=?";
        Object [] params={user.getUserid()};
        list=this.executeQuery(sqlString, params);
        if(list.size()>0)
        {
            return list;
        }
        else
        {
            return null;
        }
    }*/
    //结算成功修改账单状态
    @Override
    public boolean UpdateBill(User user) {
        int list;
        String sqlString="update bill set ispaid=1 where userid=? and finishtime!='未完成' and ispaid='0'";
        Object [] params={user.getUserid()};
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
    public boolean UpdateEmployeeBill(Worker workname,Bill bill)
    {
        int list;
        String sqlString="update bill set workname=? where billid=?";
        Object [] params={workname.getWorkname(),bill.getBillid()};
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
    public boolean UpdateEmployeeBillend(Bill bill)
    {
        date =new Date();
        int list;
        String sqlString="update bill set finishtime=? where billid=?";
        Object [] params={sdf.format(date),bill.getBillid()};
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

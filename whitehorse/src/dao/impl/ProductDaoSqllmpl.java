package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import dao.JDBCUtil;
import dao.ProductDao;
import entity.Product;

public class ProductDaoSqllmpl extends JDBCUtil implements ProductDao {
    String[][] tablevalue;

    @Override // 产品列表
    public int SelectProduct(String pro,int method) {
        //List<HashMap> list = new ArrayList<>();
        String sqlString="";
        Object[] params = {};
        if("all".equals(pro)||method==0)
        {
            sqlString = "select proid,proname,promoney,departname from product,depart where product.departid=depart.departid";
        }
        else if(method==1)
        {
            params=new Object[]{"%"+pro+"%","%"+pro+"%"}; 
            sqlString = "select proid,proname,promoney,departname from product,depart where product.departid=depart.departid and (product.proname like ? or product.proid like ?)";
        }
        else if(method==2)
        {
            params=new Object[]{pro}; 
            sqlString = "select proid,proname,promoney,departname from product,depart where product.departid=depart.departid and depart.departname=?";
        }
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            rs.last();
            if(method==3)
            {
                return rs.getInt(1);
            }
            tablevalue = new String[rs.getRow()][4];
            rs.beforeFirst();
            int i=0;
            while(rs.next())
            {
                tablevalue[i][0]=rs.getString(1);
                tablevalue[i][1]=rs.getString(2);
                tablevalue[i][2]=rs.getString(3);
                tablevalue[i][3]=rs.getString(4);
                i++;
            }
            return i;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        /*list = this.executeQuery(sqlString, params);
        if (list.size() > 0) {
            tablevalue = new String[list.size()][3];
            Hashdouble(list);
            return list.size();
        } else {
            return 0;
        }*/
    }

    // 哈希表列表转二维数组 已弃用
    public void Hashdouble(List<HashMap> list) {
        int i = 0;
        for (HashMap key : list) {
            Set keyset = key.keySet();
            for (Object kObject : keyset) {
                if ("proid".equals(kObject)) {
                    tablevalue[i][0] = key.get(kObject).toString();
                } else if ("proname".equals(kObject)) {
                    tablevalue[i][1] = key.get(kObject).toString();
                } else if ("promoney".equals(kObject)) {
                    tablevalue[i][2] = key.get(kObject).toString();
                }
            }
            i++;
        }
    }

    // 返回数组
    public String[][] returntablevalue() {
        return tablevalue;
    }

    @Override//查询单个产品信息
    public Product SelectProduct(int proid) {
        //List<HashMap> list=new ArrayList<>();
        String sqlString="select * from product where proid=?";
        Object [] params={proid};
        ResultSet rs = this.executeQuery2(sqlString, params);
        try {
            Product product=new Product();
            if(rs.next())
            {
                product.setProid(rs.getInt(1));
                product.setProname(rs.getString(2));
                product.setPromoney(rs.getString(3));
                product.setDepartid(rs.getString(4));
            }
            return product;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        /*list=this.executeQuery(sqlString, params);
        if(list.size()>0)
        {
            Product product=new Product();
            for (HashMap key : list) {
                Set keyset = key.keySet();
                for (Object kObject : keyset) {
                    if ("proid".equals(kObject)) {
                        product.setProid(Integer.parseInt(key.get(kObject).toString()));
                    } else if ("proname".equals(kObject)) {
                        product.setProname(key.get(kObject).toString());
                    } else if ("promoney".equals(kObject)) {
                        product.setPromoney(key.get(kObject).toString());
                    }
                }
            }
            return product;
        }
        else
        {
            return null;
        }*/
        
    }

    @Override
    public boolean DelProduct(String pro) {
        int list;
        String sqlString="";
        Object [] params={pro};
        if(isNum(pro))
        {
            sqlString="delete from product where proid=?";
        }
        else
        {
            sqlString="delete from product where proname=?";
        }
        list=this.executeUpdate(sqlString, params);
        if(list>0)
        {
            sqlString="alter table product drop proid";
            if(this.executeUpdate2(sqlString))
            {
                sqlString="alter table product add proid int not null primary key auto_increment first";
                return this.executeUpdate2(sqlString);
            }
        }
        return false;
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
    @Override
    public boolean InsertProduct(Product product) {
        int list;
        String sqlString="INSERT INTO product VALUES (?,?,?,?);";
        Object [] params={product.getProid(),product.getProname(),product.getPromoney(),product.getDepartid()};
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

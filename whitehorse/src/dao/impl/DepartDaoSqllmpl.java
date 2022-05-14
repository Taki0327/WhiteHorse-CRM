package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DepartDao;
import dao.JDBCUtil;

public class DepartDaoSqllmpl extends JDBCUtil implements DepartDao {

    @Override//加载部门分类
    public ResultSet SelectDepart() {
        String sqlString="select * from depart";
        Object [] params={};
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
    
}

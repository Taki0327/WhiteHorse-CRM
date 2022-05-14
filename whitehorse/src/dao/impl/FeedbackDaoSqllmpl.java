package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import dao.FeedbackDao;
import dao.JDBCUtil;
import entity.Feedback;
import entity.User;

public class FeedbackDaoSqllmpl extends JDBCUtil implements FeedbackDao {
    String[][] tablevalue;
    @Override
    public boolean InsertFeed(User user, Feedback feedback) {
        int list;
        String sqlString="INSERT into feedback(feedtxt,feedtime,userid,feedworkname) VALUES(?,?,?,?)";
        Object [] params={feedback.getFeedtxt(),feedback.getFeedtime(),user.getUserid(),feedback.getFeedworkname()};
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
    public boolean SelectFeed(String feedtxt) {
        String sqlString = "";
        Object[] params = {};
        if("".equals(feedtxt))
        {
            sqlString = "select * from feedback";
        }
        else
        {
            params=new Object[]{"%"+feedtxt+"%","%"+feedtxt+"%","%"+feedtxt+"%","%"+feedtxt+"%","%"+feedtxt+"%"}; 
            sqlString = "select * from feedback where feedid like ? or feedtxt like ? or feedtime like ? or userid like ? or feedworkname like ?";
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
                tablevalue[i][4]=rs.getString(5);
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
}

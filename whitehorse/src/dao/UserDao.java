package dao;
import entity.User;
public interface   UserDao {
    public boolean SelectUser(User user);
    public boolean InsertUser(User user);
    public boolean CountUser(User user);
    public boolean Updatepwd(User user);
    public boolean SelectAllUser(String user);
}

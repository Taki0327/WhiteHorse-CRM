package dao;
import entity.User;
public interface LoginDao {
    public boolean login(User user);
    public boolean Reg(User user);
    public boolean Updatepwd(User user);
}

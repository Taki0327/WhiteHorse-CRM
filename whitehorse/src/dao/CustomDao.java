package dao;

import entity.Custom;
import entity.User;

public interface CustomDao {
    public boolean SelectCustomInfo(User user);
    public boolean SelectAllCustomInfo(String user);
    public boolean UpdateCustomInfo(User user,Custom custom);
    public String SelectCustomSumBill(User user);
}

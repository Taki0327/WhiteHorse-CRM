package dao;

import java.sql.ResultSet;
import java.util.List;

import entity.Bill;
import entity.User;
import entity.Worker;

public interface BillDao {
    public boolean SelectBill(User user);
    public boolean SelectAllBill(String user,int ispaid);
    public boolean InsertBill(User user,Bill bill);
    //public List SelectBillworkname(User user);
    public ResultSet SelectBillworkname(User user);
    public boolean UpdateBill(User user);
    public boolean UpdateEmployeeBill(Worker workname,Bill bill);
    public boolean UpdateEmployeeBillend(Bill bill);
}

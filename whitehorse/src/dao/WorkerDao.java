package dao;

import java.sql.ResultSet;

import entity.Bill;
import entity.User;
import entity.Worker;
public interface WorkerDao {
    public boolean InsertWorker(User user,String departname);
    public boolean SelectWorkerInfo(User user);
    public boolean SelectAllWorker(String work);
    public boolean UpdateWorkerInfo(User user,Worker worker);
    public boolean QueryCustomBill(Worker worker);
    public int QueryEmployeeBill(Worker worker);
    public boolean QueryEmployeeworkend(Worker worker);
}

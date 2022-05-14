package dao;

import entity.Product;

public interface ProductDao {
    public int SelectProduct(String pro,int method);
    public Product SelectProduct(int proid);
    public boolean DelProduct(String pro);
    public boolean InsertProduct(Product product);
}

package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductsDao {

    ConnectionMySQL cn = new ConnectionMySQL(); // instancia de la coneccion
    Connection conn;
    //las siguiente variables sirven para conectarnos con la DB
    PreparedStatement pst; //sirven para las consultas
    ResultSet rs; //sirve para recibir los datos de las consultas
    
    //Register Product
    public boolean registerProductQuery(Products product){
        String query = "INSER INTO products (code, name, description, unit_price, created, updated, category_id)"
                + "VALUES(?,?,?,?,?,?,?)";        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getCategory_id());
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar un Producto"+ e);
            return false;
        }
    }
    
    //List Products
    public List listProductsQuery(String value){
        List<Products> list_products = new ArrayList();
        String query = "SELECT pro.*, ca.name AS catagory_name FROM products pro, categories ca WHERE pro.category_id = ca.id";
        String query_search_product = "SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca "
                + "ON pro.category_id = ca.id WHERE pro.name LIKE '%" + value +"%'";
        
        try {
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_product);
                rs = pst.executeQuery();
            }
            
            while (rs.next()) {                
                Products product = new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getString("product_quantity"));
                product.setCategory_name(rs.getString("category_name"));
                list_products.add(product);                
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
         return list_products;
    } 
    
    //Update Product
    public boolean updateProductQuery(Products product){
        String query = "UPDATE products SET code=?, name=?, description=?, unit_price=?, updated=?,"
                + " category_id=? WHERE id=?";        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product.getCategory_id());
            pst.setInt(7, product.getId());
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar el Producto "+ e);
            return false;
        }
    }
    
    //Delete Product
    public boolean deleteProductQuery(int id){
        String query = "DELETE FROM products WHERE id = " + id;
        
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No puede eliminar un producto que tenga relacion con otra tabla ");
            return false;
        }
    }
 
    //Sarch Product on List
    public Products searchProduct(int id){
        String query = "SELECT pro.*, ca.name AS category_name FROM products pro "
                + "INNER JOIN categories ca "
                + "ON pro.category_id = ca.id WHERE pro.id=?";//se coloca el ? xq se pueden dar clicks a varios productos de la lista y c/u tiene su id q no conocemos
        Products product = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);//es el id q recibimos por parametros en el metodo
            rs = pst.executeQuery();//recordar q el rs almacena los datos que se obtuvieron en la consulta
            
            //Con esto se agrega a cada campo el resultado que trajo la consulta
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));
                
            }
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
    //Sarch Product for code
    public Products searchCode(int code) {
        String query = "SELECT pro.id, pro.name FROM products pro WHERE pro.code = ?";

        Products product = new Products();
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, code);//es el id q recibimos por parametros en el metodo
            rs = pst.executeQuery();//recordar q el rs almacena los datos que se obtuvieron en la consulta
            
            //Con esto se agrega a cada campo el resultado que trajo la consulta
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));   
            }
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return product;
    }
    
    //Search Product quantity
    public Products searchId(int id){
        String query = "SELECT pro.product_quantity FROM prooducts pro WHERE pro.id = ?";
        Products product = new Products();
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
            if(rs.next()){
                product.setProduct_quantity("product_quantity");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
       return product; 
    }
    
    //Update Stock 
    public boolean updateStockQuery(int amount, int product_id){
        String query = "UPDATE products SET product_quantity = ?, product_id = ?";
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();            
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
    
}

package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriesDao {

    ConnectionMySQL cn = new ConnectionMySQL(); // instancia de la coneccion
    Connection conn;
    //las siguiente variables sirven para conectarnos con la DB
    PreparedStatement pst; //sirven para las consultas
    ResultSet rs; //sirve para recibir los datos de las consultas
    
    //Register Categories
    public boolean registerCategoryQuery(Categories category){
        String query = "INSER INTO categories (name, created, updated)"
                + "VALUES (?.?.?)";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setTimestamp(3, datetime);
            pst.execute();
            return true;
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error registrando nueva categoria"+ e);
            return false;
        }
        
    }
    
    //Listar Categories
    public List listCategoriesQuery(String value){
        List<Categories> list_categories = new ArrayList();
        String query = "SELECT * FROM categories";
        String query_search_category = "SELECT * FROM categories WHERE name LIKE '%" + value + "%'";
        
           try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_category);
                rs = pst.executeQuery();
            }
            
            //para recorrer la lista de clientes
            while(rs.next()){
                Categories category = new Categories();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                list_categories.add(category);//pasar toda la informacion a la lista
            }      
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_categories;
    }
    
    //Update Categories
     //Register Categories
    public boolean updateCategoryQuery(Categories category){
        String query = "UPDATE categories SET name = ?, updated = ?"
                + "WHERE name = ?";
        Timestamp datetime = new Timestamp(new Date().getTime());
        
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            pst.setString(1, category.getName());
            pst.setTimestamp(2, datetime);
            pst.setInt(3, category.getId());
            pst.execute();
            return true;
            
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(null,"Error modificando categoria"+ e);
            return false;
        }
        
    }
    
    //Eliminar Categories
    public boolean deleteCategoryQuery(int id){
        String query = "DELETE FROM categories WHERE id =" +id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puedes eliminar una categoria que tenga relacion con otra tabla"+ e);
            return false;
        }
    }
    
}

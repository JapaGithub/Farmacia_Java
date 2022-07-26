package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class SuppliersDao {

    ConnectionMySQL cn = new ConnectionMySQL(); // instancia de la coneccion
    Connection conn;
    //las siguiente variables sirven para conectarnos con la DB
    PreparedStatement pst; //sirven para las consultas
    ResultSet rs; //sirve para recibir los datos de las consultas
    
    public static int id = 0;
    public static String name_user = "";
    public static String description_user = "";
    public static String telephone_user = "";
    public static String address_user = "";
    public static String email_user = "";
    public static String city_user  = "";
    
    
     //Registrar Suppliers 
    public boolean registerSupplierQuery(Suppliers supplier){
        String query = "INSER INTO suppliers (name, description, telephone, address, email, city, created, updated)"
                + "VALUES (?.?.?.?.?.?.?.?)";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            //Acceder a los metodos Setter del cliente 
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getTelephone());
            pst.setString(4, supplier.getAddress());         
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al registrar el Proveedor " + e);
            return false;
        }
    }
     
     //Listar Suppliers
    public List lsitSupplierQuery(String value){
        List<Suppliers> list_suppliers = new ArrayList();
        String query = "SELECT * FROM suppliers";
        String query_search_supplier = "SELECT * FROM suppliers WHERE name LIKE '%" + value + "%'";
    
        try{
            conn = cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst = conn.prepareStatement(query);
                rs = pst.executeQuery();
            }else{
                pst = conn.prepareStatement(query_search_supplier);
                rs = pst.executeQuery();
            }
            
            //para recorrer la lista de clientes
            while(rs.next()){
                Suppliers supplier = new Suppliers();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setAddress(rs.getString("address"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setEmail(rs.getString("email"));
                list_suppliers.add(supplier);//pasar toda la informacion a la lista
            }      
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, e.toString());
        }
        return list_suppliers;
    }
   
     //Update Suppliers 
    public boolean updateSupplierQuery(Suppliers supplier){
        String query = "UPDATE suppliers SET name = ?, description = ?, telephone = ?, address = ?, email = ?, city = ?, "
                + "updated = ? WHERE id = ?  ";
        
        Timestamp datetime = new Timestamp(new Date().getTime());
        try{
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            
            //Acceder a los metodos Setter del cliente 
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getTelephone());
            pst.setString(4, supplier.getAddress());         
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar la informacion del Proveedor " + e);
            return false;
        }
    }
    
    //Eliminar Suppliers
    public boolean deleteSupplierQuery(int id){
        String query = "DELETE FROM suppliers WHERE id =" +id;
        try {
            conn = cn.getConnection();
            pst = conn.prepareStatement(query);
            pst.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No puedes eliminar un proveedor que tenga relacion con otra tabla"+ e);
            return false;
        }
    }
}

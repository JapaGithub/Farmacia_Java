
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private String database_name = "pharmacy_database";
    private String user = "root";
    private String password = "1234";
    private String url = "jdbc:mysql://localhost:3306/" + database_name;
    Connection conn = null; 
    
    //metodo para conectar java con mysql
    public Connection getConnection(){
        try{
            //obtener valor del driver
            Class.forName("com.msql.cj.jdbc.Driver");
            
            //obtener la connection
            conn = DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException e){
            System.err.println("Ha ocurrido un ClassNotFoundException " + e.getMessage() );
        }catch(SQLException e){
            System.err.println("Ha ocurrido un error SQLException "+ e.getMessage() );
        }
        return conn;
    }
    
}

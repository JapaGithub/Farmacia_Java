package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;//es el conjunto de datos que obtenemos al enviar la consulta
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class EmployeesDao {
    //VAN TODOS LOS METODOS QUE PERMITEN INTERACTUAR A JAVA CON MYSQL
    
    ConnectionMySQL cn = new ConnectionMySQL(); // instancia de la coneccion
    Connection conn;
    //las siguiente variables sirven para conectarnos con la DB
    PreparedStatement pst; //sirven para las consultas
    ResultSet rs; //sirve para recibir los datos de las consultas
    
    //Variables para enviar datos entre interfaces
    public static int id_user = 0;
    public static String full_name_user = "";
    public static String username_user = "";
    public static String address_user = "";
    public static String telephone_user = "";
    public static String email_user = "";
    public static String rol_user = "";
    
    
    //METODO DEL LOGIN
    
    public Employees loginQuery(String user, String password){
        String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
        Employees employee = new Employees();
        try{
        //aca vamos a pasar la coneccion y pasar la consulta de query
            conn = cn.getConnection(); //con esto estoy llamando a la coneccion
            pst = conn.prepareStatement(query);
            //enviar parametros
            pst.setString(1, user);//para comparar 
            pst.setString(2, password);
            
            //ejecutamos la consulta
            rs = pst.executeQuery();//es un metodo de java para ejecutar la consulta
            
            //ahora vamos acceder a los metodos Getter and Setter del empleado (employee)
            //se crea el condicional para verificar q los datos de username y password coinciden
            if(rs.next()){
                employee.setId(rs.getInt("id"));//estamos almacenando en setId (Setter de id) lo que trae el rs (resultado de ejecutar la consulta)
                id_user = employee.getId();//se guarda la informacion en la variable id_user y queda guardada la informacion del usuario (en caso de que coincida con un registro de la DB)
                
                employee.setFull_name(rs.getString("full_name"));
                full_name_user = employee.getFull_name();
                
                employee.setUsername(rs.getString("username"));
                username_user = employee.getUsername();
            
                employee.setAddress(rs.getString("address"));
                address_user = employee.getAddress();
                
                employee.setTelephone(rs.getString("telephone"));
                telephone_user = employee.getTelephone();
                
                employee.setEmail(rs.getString("email"));
                email_user = employee.getEmail();
                
                employee.setRol(rs.getString("rol"));
                rol_user = employee.getRol();             
            }
            
        }catch(SQLException e){
            //aparece un msj en pantalla
            JOptionPane.showMessageDialog(null,"Error al obtener al empleado" + e);
        }
        
        return employee;
    }
}

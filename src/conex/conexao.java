
package conex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class conexao {
    private String db = "db_inventory";
    private String usuario = "root";
    private String password = "";
    private String servidor = "localhost";
    private String porta = "3306";
    private String url = "jdbc:mysql://" + servidor + ":" + porta + "/" + db;
    private Connection cvcon = null;
    
    public Connection getAbrirConexao() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cvcon = (Connection) DriverManager.getConnection(url, usuario, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Não foi possivel conectar ao banco de dados. Verifique a conexão\n", "Inventory System" , 0);
            System.exit(0);
        }
        return cvcon;
    }
    
    public void setFecharConexao(Connection con){
        try {
            con.close();
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "Não foi possivel encerrar a conexão\n" + e, "Inventory System", 0 );
        }
    }
}

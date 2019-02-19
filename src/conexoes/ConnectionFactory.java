package conexoes;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    //Atributos para conexão com o banco de dados
    private String host = "postgresacademy.postgres.database.azure.com";
    private String database = "bdjoao";
    private String user = "postgres@postgresacademy";
    private String password = "9137@luis";

    /*Atributos padrão para conexão
    Connection: Conecta com o banco
    Statement: Utilizado para tratar instruções SQL
    ResultSet: Recupera dados do banco de dados
     */
    public Connection conex;
    public Statement stmt;
    public ResultSet rs;

    public void getConnection() {
        try {
            //Procura a classe de driver postgres
            Class.forName("org.postgresql.Driver");
            /*
            * Cria uma variavel de url para adicionar o caminho para o banco de dados passando o host e o banco de dados
            * de acordo o formato da string
            */
            String url = String.format("jdbc:postgresql://%s/%s", host, database);

            // Seta as propriedadas da conexão
            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            //Inicia a conexão com o banco de dados
            conex = DriverManager.getConnection(url, properties);
            System.out.println("Conexão iniciada");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection() {
        try {
            //Fecha a conexão
            conex.close();
            System.out.println("Conexão finalizada");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void executeSql(String sql) {
        try {
            /*
            Atribui a váriavel stmt uma conexão e cria um tipo de "pesquisa", o resultado da pesquisa (rs)
            vai do inicio ao fim e é insensitive(tem que estar de acordo com o banco - maiusculas e minusculas)
             */

            stmt = conex.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);

            /*
            O Resultado da pesquisa (rs) recebe os dados do Statement(stmt) e executa uma lista de ações (SQL)
             */
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

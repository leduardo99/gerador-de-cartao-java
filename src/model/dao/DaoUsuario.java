
package model.dao;

import conexoes.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.beans.BeansUsuario;


public class DaoUsuario {
    //Istância da classe de conexão
    ConnectionFactory conex = new ConnectionFactory();
    
    //Adiciona um usuário no banco de dados passando a classe beans como parâmetro
    public void getUsuario(BeansUsuario modelUsuario) {
        //Conecta no banco de dados
        conex.getConnection();
        //Comando SQL armazenado em uma variavel - Insere na tabela_usuarios nos campos login e senha o que o usuário digitar
        String sql = "INSERT INTO tbl_usuarios(login, senha) VALUES (?,?)";
        try {
            /*
            A variável preparedStatement recebe uma conexão e prepara uma ação para o banco de dados,
            passando como parâmetro o comando SQL
            */
            PreparedStatement preparedStatement = conex.conex.prepareStatement(sql);
            //Pega os valores de login e senha se seta uma string no banco
            preparedStatement.setString(1, modelUsuario.getLogin());
            preparedStatement.setString(2, modelUsuario.getSenha());
            //Executa o comando e salva no banco
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o usuário no banco de dados!");
            ex.printStackTrace();
        }
        //Fecha conexão
        conex.closeConnection();
    }

    //Pesquisa um usuário no banco de dados retornando um valor do BeansUsuario e passando como parâmentro a classe beans
    public BeansUsuario searchUsuario(BeansUsuario modelUsuario) {
        //Conecta no banco de dados
        conex.getConnection();
        try {
            //Executa um SQL e busca no banco usando caracter coringa. Pega o valor da pesquisa na classe beans
            conex.executeSql("SELECT * FROM tbl_usuarios where nome like'%" + modelUsuario.getPesquisa() + "%'");
            //Pega o primeiro resultado do banco
            conex.rs.first();
            //Seta os resultados na classe beans passando como parâmetro a coluna da tabela no banco de dados
            modelUsuario.setIdUsuario(conex.rs.getInt("id_usuario"));
            modelUsuario.setLogin(conex.rs.getString("login"));
            modelUsuario.setSenha(conex.rs.getString("senha"));
            //Fecha a conexão
            conex.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Retorna o valor da pesquisa
        return modelUsuario;
    }

    //Edita um usuário no banco passando com parâmetro a classe beans
    public void editUsuario(BeansUsuario modelUsuario) {
        //Conecta no banco de dados
        conex.getConnection();
        /*Altera a tabela usuarios e seta nos campos login e senha o que o usuário digitar onde o id do usuario 
        for igual ao da tela*/
        String sql = "UPDATE tbl_usuarios SET login=?, senha=? where id_usuario=?";
        try {
            /*
            A variável preparedStatement recebe uma conexão e prepara uma ação para o banco de dados,
            passando como parâmetro o comando SQL
            */
            PreparedStatement preparedStatement = conex.conex.prepareStatement(sql);
            //Pega os valores de login, senha e id_usuario e seta uma string no banco
            preparedStatement.setString(1, modelUsuario.getLogin());
            preparedStatement.setString(2, modelUsuario.getSenha());
            preparedStatement.setInt(3, modelUsuario.getIdUsuario());
            //Executa a ação no banco
            preparedStatement.execute();
            conex.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Fecha a conexão com o banco de dados
        conex.closeConnection();
    }

    //Deleta um usuário no banco de dados passando a classe beans como parâmetro
    public void deleteUsuario(BeansUsuario modelUsuario) {
        //conecta no banco de dados
        conex.getConnection();
        //Deleta os dados da tabela usuario onde o id_usuario for igual ao que estiver na tela
        String sql = "DELETE FROM tbl_usuarios WHERE id_usuario=?";
        try {
             /*
            A variável preparedStatement recebe uma conexão e prepara uma ação para o banco de dados,
            passando como parâmetro o comando SQL
            */
            PreparedStatement preparedStatement = conex.conex.prepareStatement(sql);
            //Pega o valor id_usuario e seta uma string no banco
            preparedStatement.setInt(1, modelUsuario.getIdUsuario());
            //Executa o comando SQL
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conex.closeConnection();
    }

    //Cria uma lista de beans usuario e retorna uma lista de usuarios
    public List<BeansUsuario> getList() {
        //Criação a lista beans usuario e atribuindo a um novo array list
        List<BeansUsuario> listLogin = new ArrayList<>();
        //Comando SQL retorna tudo da tabela usuarios
        String sql = "SELECT * FROM tbl_usuarios";
        //Conecta no banco de dados
        conex.getConnection();
        try {
            /*
            A variável preparedStatement recebe uma conexão e prepara uma ação para o banco de dados,
            passando como parâmetro o comando SQL
            */
            PreparedStatement preparedStatement = conex.conex.prepareStatement(sql);
            /*
            A váriavel resultset pega todos os valores recebidos pelo statement e executa uma query
            */
            ResultSet resultSet = preparedStatement.executeQuery();

            //Enquanto houver valores no result set ele vai adicionando na lista
            while (resultSet.next()) {
                BeansUsuario modelUsuario = new BeansUsuario();
                //Pega os valores do banco de dados de acordo com a coluna no banco (parâmetro) e joga os valores no beans usuario
                modelUsuario.setIdUsuario(resultSet.getInt("id_usuario"));
                modelUsuario.setLogin(resultSet.getString("login"));
                modelUsuario.setSenha(resultSet.getString("senha"));
                //adiciona na lista os valores do beans
                listLogin.add(modelUsuario);
            }
            //Fechamento das conexões
            preparedStatement.close();
            resultSet.close();
            conex.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Retorna a lista
        return listLogin;
    }
}


package model.dao;

import conexoes.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.beans.BeansCliente;


public class DaoCliente {
    //Istância da classe de conexão
    ConnectionFactory conex = new ConnectionFactory();

    //Adiciona um usuário no banco de dados passando a classe beans como parâmetro
    public void getCliente(BeansCliente beansCliente) {
        //Conecta no banco de dados
        conex.getConnection();
        //Comando SQL armazenado em uma variavel - Insere na tabela_Clientes nos campos login e senha o que o usuário digitar
        String sql = "INSERT INTO tbl_clientes(nomeCliente, cpfCliente, cartaoCliente) VALUES (?,?, ?)";
        try {
            /*
            A variável preparedStatement recebe uma conexão e prepara uma ação para o banco de dados,
            passando como parâmetro o comando SQL
            */
            PreparedStatement preparedStatement = conex.conex.prepareStatement(sql);
            //Pega os valores de login e senha se seta uma string no banco
            preparedStatement.setString(1, beansCliente.getNomeCliente());
            preparedStatement.setString(2, beansCliente.getCpfCliente());
            preparedStatement.setString(3, beansCliente.getNumeroCartao());
            //Executa o comando e salva no banco
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível salvar o cliente no banco de dados!");
            ex.printStackTrace();
        }
        //Fecha conexão
        conex.closeConnection();
    }


    //Deleta um usuário no banco de dados passando a classe beans como parâmetro
    public void deleteCliente(BeansCliente beansCliente) {
        //conecta no banco de dados
        conex.getConnection();
        //Deleta os dados da tabela Cliente onde o id_Cliente for igual ao que estiver na tela
        String sql = "DELETE FROM tbl_clientes WHERE idCliente=?";
        try {
             /*
            A variável preparedStatement recebe uma conexão e prepara uma ação para o banco de dados,
            passando como parâmetro o comando SQL
            */
            PreparedStatement preparedStatement = conex.conex.prepareStatement(sql);
            //Pega o valor id_Cliente e seta uma string no banco
            preparedStatement.setInt(1, beansCliente.getIdCliente());
            //Executa o comando SQL
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conex.closeConnection();
    }

    //Cria uma lista de beans Cliente e retorna uma lista de Clientes
    public List<BeansCliente> getList() {
        //Criação a lista beans Cliente e atribuindo a um novo array list
        List<BeansCliente> listClientes = new ArrayList<>();
        //Comando SQL retorna tudo da tabela Clientes
        String sql = "SELECT * FROM tbl_clientes ORDER BY nomecliente";
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
                BeansCliente beansCliente = new BeansCliente();
                //Pega os valores do banco de dados de acordo com a coluna no banco (parâmetro) e joga os valores no beans Cliente
                beansCliente.setIdCliente(resultSet.getInt("idcliente"));
                beansCliente.setNomeCliente(resultSet.getString("nomecliente"));
                beansCliente.setCpfCliente(resultSet.getString("cpfcliente"));
                beansCliente.setNumeroCartao(resultSet.getString("cartaocliente"));
                //adiciona na lista os valores do beans
                listClientes.add(beansCliente);
            }
            //Fechamento das conexões
            preparedStatement.close();
            resultSet.close();
            conex.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Retorna a lista
        return listClientes;
    }
}

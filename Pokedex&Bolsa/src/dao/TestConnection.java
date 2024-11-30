package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) throws ClassNotFoundException {
        Connection connection = null;

        try {

            DatabaseConnection dao = new DatabaseConnection();


            connection = dao.getConnection();


            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexão bem-sucedida!");
            } else {
                System.out.println("Conexão falhou!");
            }

        } catch (SQLException e) {

            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexão fechada com sucesso.");
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar a conexão: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}

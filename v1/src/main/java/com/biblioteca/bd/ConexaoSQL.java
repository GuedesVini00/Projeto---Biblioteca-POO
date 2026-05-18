package com.biblioteca.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQL {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String SENHA ="guedes0410";

    public static Connection conectar() {
        try {

            Connection conexao = DriverManager.getConnection(URL, USER, SENHA);
            return conexao;
        }
        catch (SQLException e) {
            System.out.println("Erro ao se conectar no banco!");
            throw new RuntimeException(e);
        }
    }
}
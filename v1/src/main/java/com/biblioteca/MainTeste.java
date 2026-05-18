package com.biblioteca;

import java.sql.SQLException;

import com.biblioteca.service.EmprestimoService;

public class MainTeste {

    public static void main(String[] args) {

        try {



            EmprestimoService service =new EmprestimoService();
            service.realizarEmprestimo(12,5,5);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
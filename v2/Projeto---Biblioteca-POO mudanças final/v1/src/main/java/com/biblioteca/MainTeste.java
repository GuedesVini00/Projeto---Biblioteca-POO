package com.biblioteca;

import java.sql.SQLException;

import com.biblioteca.service.EmprestimoService;
import com.biblioteca.service.ReservaService;

public class MainTeste {

    public static void main(String[] args) {

        //OP DE EMPRESTIMOS:
        try {

            EmprestimoService service =new EmprestimoService();

            // REALIZAR EMPRÉSTIMO
           // service.realizarEmprestimo(idCopia,idLeitor ,idFuncionario );

            // REALIZAR DEVOLUÇÃO
           // service.realizarDevolucao(id_emprestimo);

        }
        catch(SQLException e){
            e.printStackTrace();

        }

        //OP DE RESERVAS:
        try {

            ReservaService service =new ReservaService();

            // REALIZAR RESERVA
            // service.realizarReserva(ID_OBRA,  ID_LEITOR  );


            // CANCELAR RESERVA
           //service.cancelarReserva(id_ Reserva);


            //FINALIZAR RESERVA
           //service.finalizarReserva(id_Reserva)

        }
        catch(SQLException e){
            e.printStackTrace();

        }
    }
}
package com.biblioteca.service;

import java.sql.SQLException;
import java.time.LocalDate;

import com.biblioteca.dao.CopiaDAO;
import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.FuncionarioDAO;
import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.dao.ObraDAO;
import com.biblioteca.dao.ReservaDAO;

import com.biblioteca.model.Reserva;

public class ReservaService {

    private EmprestimoDAO emprestimoDAO;
    private CopiaDAO copiaDAO;
    private ObraDAO obraDAO;
    private LeitorDAO leitorDAO;
    private FuncionarioDAO funcionarioDAO;
    private ReservaDAO reservaDAO;

    public ReservaService() throws SQLException {

        emprestimoDAO = new EmprestimoDAO();

        copiaDAO = new CopiaDAO();

        obraDAO = new ObraDAO();

        leitorDAO = new LeitorDAO();

        funcionarioDAO = new FuncionarioDAO();

        reservaDAO = new ReservaDAO();
    }

    public void realizarReserva(int idObra,int idLeitor)throws SQLException{

        int quantidade =reservaDAO.reservasAtivas(idLeitor);
        if(quantidade >= 2){
            System.out.println("Limite de reservas atingido!" );
            return;
        }

        if(copiaDAO.existeCopiaDisponivel(idObra)){
            System.out.println("Existe cópia disponível para empréstimo!");
            return;
        }

        var obra = obraDAO.buscarPorId(idObra);
        if(obra == null){
            System.out.println("Obra não encontrada!" );
            return;
        }

        var leitor = leitorDAO.buscarPorId(idLeitor);
        if(leitor == null){

            System.out.println( "Leitor não cadastrado!" );
            return;
        }

        Reserva reserva = new Reserva();

        LocalDate hoje = LocalDate.now();

        reserva.setDataReserva(hoje.toString());
        reserva.setStatus("ATIVA");
        reserva.setObra(obra);
        reserva.setLeitor(leitor);

        reservaDAO.inserir(reserva);
        System.out.println( "Reserva realizada!" );
    }

    public void cancelarReserva(int idReserva)throws SQLException{

        var reserva = reservaDAO.buscarPorId(idReserva);

        if(reserva == null){
            System.out.println("Reserva não encontrada!");
            return;
        }

        if(reserva.getStatus().equalsIgnoreCase("CANCELADA")){
            System.out.println("Reserva já cancelada!" );
            return;
        }

        reserva.setStatus("CANCELADA");
        reservaDAO.atualizar(reserva);
        System.out.println("Reserva cancelada!" );
    }

    public void finalizarReserva(int idReserva)throws SQLException{

        var reserva =reservaDAO.buscarPorId(idReserva);

        if(reserva == null){
            System.out.println(  "Reserva não encontrada!" );
            return;
        }

        if(reserva.getStatus() .equalsIgnoreCase("FINALIZADA")){
            System.out.println("Reserva já finalizada!");
            return;
        }

        reserva.setStatus("FINALIZADA");
        reservaDAO.atualizar(reserva);
        System.out.println( "Reserva finalizada!" );
    }
}
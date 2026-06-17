package com.biblioteca.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.dao.CopiaDAO;
import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.FuncionarioDAO;
import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.dao.ObraDAO;
import com.biblioteca.dao.ReservaDAO;
import com.biblioteca.enums.StatusReserva;
import com.biblioteca.exceptions.EntidadeNaoEncontradaException;
import com.biblioteca.exceptions.RegraNegocioException;
import com.biblioteca.model.Reserva;

public class ReservaService {

    private CopiaDAO copiaDAO;
    private ObraDAO obraDAO;
    private LeitorDAO leitorDAO;
    private ReservaDAO reservaDAO;
    private Connection conexao;

    public ReservaService() throws SQLException {

        this.conexao = ConexaoSQL.conectar();

        copiaDAO = new CopiaDAO(conexao);

        obraDAO = new ObraDAO(conexao);

        leitorDAO = new LeitorDAO(conexao);


        reservaDAO = new ReservaDAO(conexao);
    }

    public void realizarReserva(int idObra,int idLeitor)throws SQLException{

        try {

            conexao.setAutoCommit(false);

            int quantidade =reservaDAO.reservasAtivas(idLeitor);
            if(quantidade >= 2){
                throw new RegraNegocioException("Limite de reservas atingido!");
            }

            if(!copiaDAO.existeCopiaDisponivel(idObra)){
                throw new RegraNegocioException("Não existem cópias disponíveis para emprestimo!");
            }

            var obra = obraDAO.buscarPorId(idObra);
            if(obra == null){
                throw new EntidadeNaoEncontradaException("Obra não encontrada!");
            }

            var leitor = leitorDAO.buscarPorId(idLeitor);
            if(leitor == null){
                throw new EntidadeNaoEncontradaException("Leitor não encontrado!" );
            }

            Reserva reserva = new Reserva();

            LocalDate hoje = LocalDate.now();

            reserva.setDataReserva(hoje);
            reserva.setStatus(StatusReserva.ATIVA);
            reserva.setObra(obra);
            reserva.setLeitor(leitor);  

            reservaDAO.inserir(reserva);

            conexao.commit();
            System.out.println( "Reserva realizada!" );
                
        } catch (Exception e) {
            conexao.rollback();
            throw e;
        }
        finally{
            try {
                conexao.setAutoCommit(true);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void cancelarReserva(int idReserva)throws SQLException{

        try {

            conexao.setAutoCommit(false);

            var reserva = reservaDAO.buscarPorId(idReserva);

            if(reserva == null){
                throw new EntidadeNaoEncontradaException("Reserva não encontrada!");
            }

            if(reserva.getStatus() == StatusReserva.CANCELADA){
                throw new RegraNegocioException("Reserva já cancelada!" );
                
            }

            reserva.setStatus(StatusReserva.CANCELADA);
            reservaDAO.atualizar(reserva);

            conexao.commit();
            System.out.println("Reserva cancelada!" );

        } catch (Exception e) {
            conexao.rollback();
            throw e;
        }
        finally{
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void finalizarReserva(int idReserva)throws SQLException{
        try {

            conexao.setAutoCommit(false);

            var reserva =reservaDAO.buscarPorId(idReserva);

            if(reserva == null){
                throw new EntidadeNaoEncontradaException("Reserva não encontrada!");
            }

            if(reserva.getStatus() == StatusReserva.FINALIZADA){
                throw new RegraNegocioException("Reserva já finalizada!");
            }

            reserva.setStatus(StatusReserva.FINALIZADA);
            reservaDAO.atualizar(reserva);

            conexao.commit();
            System.out.println( "Reserva finalizada!" );

        } catch (Exception e) {
            conexao.rollback();
            throw e;
        }
        finally{
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
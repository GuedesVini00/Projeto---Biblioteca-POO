package com.biblioteca.dao;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.model.Leitor;
import com.biblioteca.model.Obra;
import com.biblioteca.model.Reserva;
import com.enums.StatusReserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    private Connection conexao;

    public ReservaDAO() throws SQLException{
        conexao = ConexaoSQL.conectar();
    }

    public void inserir(Reserva obj) throws SQLException{
        String sql = "INSERT INTO reserva (data_reserva, status, id_obra, id_leitor) VALUES (?,?,?,?) ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setString(1, obj.getDataReserva());
        comandoSql.setString(2, obj.getStatus().name());
        comandoSql.setInt(3, obj.getObra().getId());
        comandoSql.setInt(4, obj.getLeitor().getId());

        comandoSql.executeUpdate();
        System.out.println("Reserva cadastrada com sucesso!");
    }

    public void atualizar(Reserva obj) throws SQLException{
        String sql = "UPDATE reserva SET data_reserva=?, status=?, id_obra=?, id_leitor=? WHERE id=? ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setString(1, obj.getDataReserva());
        comandoSql.setString(2, obj.getStatus().name());
        comandoSql.setInt(3, obj.getObra().getId());
        comandoSql.setInt(4, obj.getLeitor().getId());
        comandoSql.setInt(5, obj.getId());

        comandoSql.executeUpdate();
        System.out.println("Reserva atualizada com sucesso!");
    }

    public void excluir(int id) throws SQLException{
        String sql = "DELETE FROM reserva WHERE id=? ";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setInt(1, id);

        comandoSql.executeUpdate();

        System.out.println("Reserva excluida com sucesso!");
    }

    public List<Reserva> listarTodos() throws SQLException{
        String sql = "SELECT * FROM reserva ORDER BY id;";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        ResultSet rs = comandoSql.executeQuery();

        List<Reserva> lista = new ArrayList<>();

        while(rs.next()){

            var reserva = new Reserva();

            reserva.setId(rs.getInt("id"));
            reserva.setDataReserva(rs.getString("data_reserva"));
            reserva.setStatus(StatusReserva.valueOf(rs.getString("status")));

            var obra = new Obra();
            obra.setId(rs.getInt("id_obra"));

            var leitor = new Leitor();
            leitor.setId(rs.getInt("id_leitor"));

            reserva.setObra(obra);
            reserva.setLeitor(leitor);

            lista.add(reserva);
        }

        return lista;
    }

    public Reserva buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM reserva WHERE id=?";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setInt(1, id);

        ResultSet rs = comandoSql.executeQuery();

        if(rs.next()){

            var reserva = new Reserva();
            reserva.setId(rs.getInt("id"));
            reserva.setDataReserva(rs.getString("data_reserva"));
            reserva.setStatus(StatusReserva.valueOf(rs.getString("status")));

            var obra = new Obra();
            obra.setId(rs.getInt("id_obra"));
            var leitor = new Leitor();
            leitor.setId(rs.getInt("id_leitor"));

            reserva.setObra(obra);
            reserva.setLeitor(leitor);

            return reserva;
        }
        else{
            return null;
        }
    }

    public int reservasAtivas(int id)throws SQLException{
        String sql = "SELECT COUNT(*)FROM reserva WHERE id_leitor = ? AND status = 'ATIVO'";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1,id);
        ResultSet rs = comandoSql.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
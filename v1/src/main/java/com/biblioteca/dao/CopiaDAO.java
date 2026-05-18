package com.biblioteca.dao;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Obra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CopiaDAO {

    private Connection conexao;

    public CopiaDAO() throws SQLException{
        conexao = ConexaoSQL.conectar();
    }

    public void inserir(Copia obj) throws SQLException{

        String sql = "INSERT INTO copia (codigo_patrimonio, status, id_obra) VALUES (?,?,?) ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1, obj.getCodigoPatrimonio().toUpperCase());
        comandoSql.setString(2, obj.getStatus().toUpperCase());
        comandoSql.setInt(3, obj.getObra().getId());
        comandoSql.executeUpdate();
        System.out.println("Copia cadastrada com sucesso!");
    }

    public void atualizar(Copia obj) throws SQLException{

        String sql = "UPDATE copia SET codigo_patrimonio=?, status=?, id_obra=? WHERE id=? ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1, obj.getCodigoPatrimonio().toUpperCase());
        comandoSql.setString(2, obj.getStatus().toUpperCase());
        comandoSql.setInt(3, obj.getObra().getId());
        comandoSql.setInt(4, obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Copia atualizada com sucesso!");
    }

    public void excluir(Copia obj) throws SQLException{

        String sql = "DELETE FROM copia WHERE id=? ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1, obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Copia excluida com sucesso!");
    }

    public List<Copia> listarTodos() throws SQLException{

        String sql = "SELECT * FROM copia ORDER BY id;";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        ResultSet rs = comandoSql.executeQuery();
        List<Copia> lista = new ArrayList<>();

        while(rs.next()){

            var copia = new Copia();
            copia.setId(rs.getInt("id"));
            copia.setCodigoPatrimonio(rs.getString("codigo_patrimonio"));
            copia.setStatus(rs.getString("status"));

            var obra = new Obra();
            obra.setId(rs.getInt("id_obra"));
            copia.setObra(obra);

            lista.add(copia);
        }

        return lista;
    }

    public Copia buscarPorId(int id) throws SQLException{

        String sql = "SELECT * FROM copia WHERE id=?";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1, id);
        ResultSet rs = comandoSql.executeQuery();

        if(rs.next()){

            var copia = new Copia();
            copia.setId(rs.getInt("id"));
            copia.setCodigoPatrimonio(rs.getString("codigo_patrimonio"));
            copia.setStatus(rs.getString("status"));

            var obra = new Obra();
            obra.setId(rs.getInt("id_obra"));
            copia.setObra(obra);
            return copia;
        }
        else{
            return null;
        }
    }
}
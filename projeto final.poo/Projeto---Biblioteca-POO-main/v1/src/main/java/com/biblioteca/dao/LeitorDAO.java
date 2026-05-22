package com.biblioteca.dao;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.model.Leitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeitorDAO {
    private Connection conexao;

    public LeitorDAO ()throws SQLException{
        conexao = ConexaoSQL.conectar();
    }

    public void inserir(Leitor obj) throws SQLException{
        String sql = "INSERT INTO leitor (nome, cpf, data_nascimento, telefone, email) VALUES (?,?,?,?,?) ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1,obj.getNome().toUpperCase());
        comandoSql.setString(2,obj.getCpf());
        comandoSql.setString(3,obj.getDataNascimento());
        comandoSql.setString(4,obj.getTelefone());
        comandoSql.setString(5,obj.getEmail().toUpperCase());
        comandoSql.executeUpdate();
        System.out.println("Leitor cadastrado com sucesso!");
    }

    public void atualizar (Leitor obj) throws SQLException{
        String sql = "UPDATE leitor SET nome=?, cpf=?, data_nascimento=?, telefone=?, email=? WHERE id=? ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1,obj.getNome().toUpperCase());
        comandoSql.setString(2,obj.getCpf());
        comandoSql.setString(3,obj.getDataNascimento());
        comandoSql.setString(4,obj.getTelefone());
        comandoSql.setString(5,obj.getEmail().toUpperCase());
        comandoSql.setInt(6,obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Leitor atualizado com sucesso!");
    }

    public void excluir (Leitor obj) throws SQLException{
        String sql = "DELETE FROM leitor WHERE id=? ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1,obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Leitor excluido com sucesso!");
    }

    public List<Leitor> listarTodos()throws SQLException{
        String sql = "SELECT * FROM leitor";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        ResultSet rs = comandoSql.executeQuery();
        List<Leitor> lista = new ArrayList<>();
        while(rs.next()){
            var leitor = new Leitor();
            leitor.setId(rs.getInt("id"));
            leitor.setNome(rs.getString("nome"));
            leitor.setCpf(rs.getString("cpf"));
            leitor.setDataNascimento(rs.getString("data_nascimento"));
            leitor.setTelefone(rs.getString("telefone"));
            leitor.setEmail(rs.getString("email"));
            lista.add(leitor);
        }
        return lista;
    }

    public Leitor buscarPorId(int id)throws SQLException{
        String sql = "SELECT * FROM leitor WHERE id=?";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1, id);
        ResultSet rs = comandoSql.executeQuery();
        if(rs.next()){
            var leitor = new Leitor();
            leitor.setId(rs.getInt("id"));
            leitor.setNome(rs.getString("nome"));
            leitor.setCpf(rs.getString("cpf"));
            leitor.setDataNascimento(rs.getString("data_nascimento"));
            leitor.setTelefone(rs.getString("telefone"));
            leitor.setEmail(rs.getString("email"));
            return leitor;
        }
        else{
            return null;
        }
    }
}
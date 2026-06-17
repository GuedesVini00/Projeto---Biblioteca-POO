package com.biblioteca.dao;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private Connection conexao;

    public FuncionarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Funcionario obj) throws SQLException{
        String sql = "INSERT INTO funcionario (nome, cpf, data_nascimento, telefone, email, cargo) VALUES (?,?,?,?,?,?) ";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setString(1, obj.getNome().toUpperCase());
        comandoSql.setString(2, obj.getCpf());
        comandoSql.setString(3, obj.getDataNascimento());
        comandoSql.setString(4, obj.getTelefone());
        comandoSql.setString(5, obj.getEmail().toUpperCase());
        comandoSql.setString(6, obj.getCargo().toUpperCase());
        comandoSql.executeUpdate();
        System.out.println("Funcionario cadastrado com sucesso!");
    }

    public void atualizar(Funcionario obj) throws SQLException{
        String sql = "UPDATE funcionario SET nome=?, cpf=?, data_nascimento=?, telefone=?, email=?, cargo=? WHERE id=? ";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1, obj.getNome().toUpperCase());
        comandoSql.setString(2, obj.getCpf());
        comandoSql.setString(3, obj.getDataNascimento());
        comandoSql.setString(4, obj.getTelefone());
        comandoSql.setString(5, obj.getEmail().toUpperCase());
        comandoSql.setString(6, obj.getCargo().toUpperCase());
        comandoSql.setInt(7, obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Funcionario atualizado com sucesso!");
    }

    public void excluir(Funcionario obj) throws SQLException{
        String sql = "DELETE FROM funcionario WHERE id=? ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1, obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Funcionario excluido com sucesso!");
    }

    public List<Funcionario> listarTodos() throws SQLException{
        String sql = "SELECT * FROM funcionario";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        ResultSet rs = comandoSql.executeQuery();
        List<Funcionario> lista = new ArrayList<>();

        while(rs.next()){
            var funcionario = new Funcionario();
            funcionario.setId(rs.getInt("id"));
            funcionario.setNome(rs.getString("nome"));
            funcionario.setCpf(rs.getString("cpf"));
            funcionario.setDataNascimento(rs.getString("data_nascimento"));
            funcionario.setTelefone(rs.getString("telefone"));
            funcionario.setEmail(rs.getString("email"));
            funcionario.setCargo(rs.getString("cargo"));
            lista.add(funcionario);
        }

        return lista;
    }

    public Funcionario buscarPorId(int id) throws SQLException{
        String sql = "SELECT * FROM funcionario WHERE id=?";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1, id);
        ResultSet rs = comandoSql.executeQuery();

        if(rs.next()){
            var funcionario = new Funcionario();
            funcionario.setId(rs.getInt("id"));
            funcionario.setNome(rs.getString("nome"));
            funcionario.setCpf(rs.getString("cpf"));
            funcionario.setDataNascimento(rs.getString("data_nascimento"));
            funcionario.setTelefone(rs.getString("telefone"));
            funcionario.setEmail(rs.getString("email"));
            funcionario.setCargo(rs.getString("cargo"));
            
            return funcionario;
        }
        else{
            return null;
        }
    }
}
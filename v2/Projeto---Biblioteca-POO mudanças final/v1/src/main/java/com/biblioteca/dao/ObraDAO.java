package com.biblioteca.dao;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.model.Obra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ObraDAO {
 private Connection conexao;

    public ObraDAO() throws SQLException{
        conexao = ConexaoSQL.conectar();
    }

    public void inserir(Obra obj) throws SQLException{
        String sql = "INSERT INTO obra (titulo, autor, data_publicacao, categoria, tipo) VALUES (?,?,?,?,?) ";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1,obj.getTitulo().toUpperCase());
        comandoSql.setString(2,obj.getAutor().toUpperCase());
        comandoSql.setString(3,obj.getDataPublicacao());
        comandoSql.setString(4,obj.getCategoria().toUpperCase());
        comandoSql.setString(5,obj.getTipo().toUpperCase());
        comandoSql.executeUpdate();
        System.out.println("Obra cadastrada com sucesso!");
    }

    public void atualizar (Obra obj) throws SQLException{
        String sql = "UPDATE obra SET titulo=?, autor=?, data_publicacao=?, categoria=?, tipo=? WHERE id=? ";;
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1,obj.getTitulo().toUpperCase());
        comandoSql.setString(2,obj.getAutor().toUpperCase());
        comandoSql.setString(3,obj.getDataPublicacao());
        comandoSql.setString(4,obj.getCategoria().toUpperCase());
        comandoSql.setString(5,obj.getTipo().toUpperCase());
        comandoSql.setInt(6,obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Obra atualizada com sucesso!");
    }

    public void excluir (int id) throws SQLException{
        String sql = "DELETE FROM obra WHERE id=? ";;
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1,id);
        comandoSql.executeUpdate();
        System.out.println("Obra excluida com sucesso!");
    }

    public List<Obra> listarTodos()throws SQLException{
        String sql = "SELECT * FROM obra";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        ResultSet rs = comandoSql.executeQuery();
        List<Obra> lista = new ArrayList<>();
        while(rs.next()){
            var obra = new Obra();
            obra.setId(rs.getInt("id"));
            obra.setTitulo(rs.getString("titulo"));
            obra.setAutor(rs.getString("autor"));
            obra.setDataPublicacao(rs.getString("data_publicacao"));
            obra.setCategoria(rs.getString("categoria"));
            obra.setTipo(rs.getString("tipo"));
            lista.add(obra);
        }
        return lista;
    }
    

    public Obra buscarPorId(int id)throws SQLException{
        String sql = "SELECT * FROM obra WHERE id=?";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1, id);
        ResultSet rs = comandoSql.executeQuery();
        if(rs.next()){
            var obra = new Obra();
            obra.setId(rs.getInt("id"));
            obra.setTitulo(rs.getString("titulo"));
            obra.setAutor(rs.getString("autor"));
            obra.setDataPublicacao(rs.getString("data_publicacao"));
            obra.setCategoria(rs.getString("categoria"));
            obra.setTipo(rs.getString("tipo"));
            return obra;
        }
        else{
            return null;
        }
    }
}
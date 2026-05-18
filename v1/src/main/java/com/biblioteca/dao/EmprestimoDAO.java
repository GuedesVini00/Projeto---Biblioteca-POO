package com.biblioteca.dao;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Funcionario;
import com.biblioteca.model.Leitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    private Connection conexao;

    public EmprestimoDAO() throws SQLException{
        conexao = ConexaoSQL.conectar();
    }

    public void inserir(Emprestimo obj) throws SQLException{

        String sql = """
                INSERT INTO emprestimo
                (data_saida, data_devolucao, status, id_copia, id_leitor, id_funcionario)
                VALUES (?,?,?,?,?,?)
                """;
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1, obj.getDataSaida());
        comandoSql.setString(2, obj.getDataDevolucao());
        comandoSql.setString(3, obj.getStatus().toUpperCase());
        comandoSql.setInt(4, obj.getCopia().getId());
        comandoSql.setInt(5, obj.getLeitor().getId());
        comandoSql.setInt(6, obj.getFuncionario().getId());
        comandoSql.executeUpdate();
        System.out.println("Emprestimo cadastrado com sucesso!");
    }

    public void atualizar(Emprestimo obj) throws SQLException{

        String sql = """
                UPDATE emprestimo
                SET data_saida=?, data_devolucao=?, status=?, 
                id_copia=?, id_leitor=?, id_funcionario=?
                WHERE id=?
                """;
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setString(1, obj.getDataSaida());
        comandoSql.setString(2, obj.getDataDevolucao());
        comandoSql.setString(3, obj.getStatus().toUpperCase());
        comandoSql.setInt(4, obj.getCopia().getId());
        comandoSql.setInt(5, obj.getLeitor().getId());
        comandoSql.setInt(6, obj.getFuncionario().getId());
        comandoSql.setInt(7, obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Emprestimo atualizado com sucesso!");
    }

    public void excluir(Emprestimo obj) throws SQLException{

        String sql = "DELETE FROM emprestimo WHERE id=?";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1, obj.getId());
        comandoSql.executeUpdate();
        System.out.println("Emprestimo excluido com sucesso!");
    }

    public List<Emprestimo> listarTodos() throws SQLException{

        String sql = "SELECT * FROM emprestimo ORDER BY id;";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        ResultSet rs = comandoSql.executeQuery();
        List<Emprestimo> lista = new ArrayList<>();

        while(rs.next()){

            var emprestimo = new Emprestimo();
            emprestimo.setId(rs.getInt("id"));
            emprestimo.setDataSaida(rs.getString("data_saida"));
            emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
            emprestimo.setStatus(rs.getString("status"));

            var copia = new Copia();
            copia.setId(rs.getInt("id_copia"));

            var leitor = new Leitor();
            leitor.setId(rs.getInt("id_leitor"));

            var funcionario = new Funcionario();
            funcionario.setId(rs.getInt("id_funcionario"));
            emprestimo.setCopia(copia);
            emprestimo.setLeitor(leitor);
            emprestimo.setFuncionario(funcionario);

            lista.add(emprestimo);
        }

        return lista;
    }

    public Emprestimo buscarPorId(int id) throws SQLException{

        String sql = "SELECT * FROM emprestimo WHERE id=?";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setInt(1, id);

        ResultSet rs = comandoSql.executeQuery();

        if(rs.next()){

            var emprestimo = new Emprestimo();

            emprestimo.setId(rs.getInt("id"));
            emprestimo.setDataSaida(rs.getString("data_saida"));
            emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
            emprestimo.setStatus(rs.getString("status"));

            var copia = new Copia();
            copia.setId(rs.getInt("id_copia"));

            var leitor = new Leitor();
            leitor.setId(rs.getInt("id_leitor"));

            var funcionario = new Funcionario();
            funcionario.setId(rs.getInt("id_funcionario"));

            emprestimo.setCopia(copia);
            emprestimo.setLeitor(leitor);
            emprestimo.setFuncionario(funcionario);

            return emprestimo;
        }
        else{
            return null;
        }
    }

    public int emprestimosAtivos(int id) throws SQLException{
        String sql = "SELECT COUNT(*)FROM emprestimo WHERE id_leitor = ? AND status = 'ATIVO'";
        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        comandoSql.setInt(1,id);
        ResultSet rs = comandoSql.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
}
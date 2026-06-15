package com.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.enums.StatusEmprestimo;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Funcionario;
import com.biblioteca.model.Leitor;

public class EmprestimoDAO {

    private Connection conexao;

    public EmprestimoDAO() throws SQLException {
        conexao = ConexaoSQL.conectar();
    }

    public void inserir(Emprestimo obj) throws SQLException {

        String sql = """
                INSERT INTO emprestimo
                (data_saida, data_devolucao, status, id_copia, id_leitor, id_funcionario)
                VALUES (?,?,?,?,?,?)
                """;

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setString(1, obj.getDataSaida());
        comandoSql.setString(2, obj.getDataDevolucao());
        comandoSql.setString(3, obj.getStatus().name());
        comandoSql.setInt(4, obj.getCopia().getId());
        comandoSql.setInt(5, obj.getLeitor().getId());
        comandoSql.setInt(6, obj.getFuncionario().getId());

        comandoSql.executeUpdate();
        comandoSql.close();

        System.out.println("Emprestimo cadastrado com sucesso!");
    }

    public void atualizar(Emprestimo obj) throws SQLException {

        String sql = """
                UPDATE emprestimo
                SET data_saida=?, data_devolucao=?, status=?,
                id_copia=?, id_leitor=?, id_funcionario=?
                WHERE id=?
                """;

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setString(1, obj.getDataSaida());
        comandoSql.setString(2, obj.getDataDevolucao());
        comandoSql.setString(3, obj.getStatus().name());
        comandoSql.setInt(4, obj.getCopia().getId());
        comandoSql.setInt(5, obj.getLeitor().getId());
        comandoSql.setInt(6, obj.getFuncionario().getId());
        comandoSql.setInt(7, obj.getId());

        comandoSql.executeUpdate();
        comandoSql.close();

        System.out.println("Emprestimo atualizado com sucesso!");
    }

    public void excluir(int id) throws SQLException {

        String sql = "DELETE FROM emprestimo WHERE id=?";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setInt(1, id);

        comandoSql.executeUpdate();
        comandoSql.close();

        System.out.println("Emprestimo excluido com sucesso!");
    }

    public List<Emprestimo> listarTodos() throws SQLException {

        String sql = """
                SELECT 
                    e.id,
                    e.data_saida,
                    e.data_devolucao,
                    e.status,
                    c.id AS id_copia,
                    c.codigo_patrimonio,
                    l.id AS id_leitor,
                    l.nome AS nome_leitor,
                    f.id AS id_funcionario,
                    f.nome AS nome_funcionario
                FROM emprestimo e
                INNER JOIN copia c ON e.id_copia = c.id
                INNER JOIN leitor l ON e.id_leitor = l.id
                INNER JOIN funcionario f ON e.id_funcionario = f.id
                ORDER BY e.id
                """;

        PreparedStatement comandoSql = conexao.prepareStatement(sql);
        ResultSet rs = comandoSql.executeQuery();

        List<Emprestimo> lista = new ArrayList<>();

        while (rs.next()) {

            Emprestimo emprestimo = new Emprestimo();

            emprestimo.setId(rs.getInt("id"));
            emprestimo.setDataSaida(rs.getString("data_saida"));
            emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
            emprestimo.setStatus(StatusEmprestimo.valueOf(rs.getString("status")));

            Copia copia = new Copia();
            copia.setId(rs.getInt("id_copia"));
            copia.setCodigoPatrimonio(rs.getString("codigo_patrimonio"));

            Leitor leitor = new Leitor();
            leitor.setId(rs.getInt("id_leitor"));
            leitor.setNome(rs.getString("nome_leitor"));

            Funcionario funcionario = new Funcionario();
            funcionario.setId(rs.getInt("id_funcionario"));
            funcionario.setNome(rs.getString("nome_funcionario"));

            emprestimo.setCopia(copia);
            emprestimo.setLeitor(leitor);
            emprestimo.setFuncionario(funcionario);

            lista.add(emprestimo);
        }

        rs.close();
        comandoSql.close();

        return lista;
    }

    public Emprestimo buscarPorId(int id) throws SQLException {

        String sql = "SELECT * FROM emprestimo WHERE id=?";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setInt(1, id);

        ResultSet rs = comandoSql.executeQuery();

        if (rs.next()) {

            Emprestimo emprestimo = new Emprestimo();

            emprestimo.setId(rs.getInt("id"));
            emprestimo.setDataSaida(rs.getString("data_saida"));
            emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
            emprestimo.setStatus(StatusEmprestimo.valueOf(rs.getString("status")));

            Copia copia = new Copia();
            copia.setId(rs.getInt("id_copia"));

            Leitor leitor = new Leitor();
            leitor.setId(rs.getInt("id_leitor"));

            Funcionario funcionario = new Funcionario();
            funcionario.setId(rs.getInt("id_funcionario"));

            emprestimo.setCopia(copia);
            emprestimo.setLeitor(leitor);
            emprestimo.setFuncionario(funcionario);

            rs.close();
            comandoSql.close();

            return emprestimo;

        } else {

            rs.close();
            comandoSql.close();

            return null;
        }
    }

    public int emprestimosAtivos(int id) throws SQLException {

        String sql = "SELECT COUNT(*) FROM emprestimo WHERE id_leitor = ? AND status = 'ATIVO'";

        PreparedStatement comandoSql = conexao.prepareStatement(sql);

        comandoSql.setInt(1, id);

        ResultSet rs = comandoSql.executeQuery();

        rs.next();

        int quantidade = rs.getInt(1);

        rs.close();
        comandoSql.close();

        return quantidade;
    }
}
package com.biblioteca.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import com.biblioteca.bd.ConexaoSQL;
import com.biblioteca.dao.CopiaDAO;
import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.FuncionarioDAO;
import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.exceptions.EntidadeNaoEncontradaException;
import com.biblioteca.exceptions.RegraNegocioException;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Emprestimo;

public class EmprestimoService {
    private EmprestimoDAO emprestimoDAO;
    private CopiaDAO copiaDAO;
    private LeitorDAO leitorDAO;
    private FuncionarioDAO funcionarioDAO;
    private Connection conexao;

    public EmprestimoService() throws SQLException {

        this.conexao = ConexaoSQL.conectar();

        emprestimoDAO = new EmprestimoDAO(conexao);

        copiaDAO = new CopiaDAO(conexao);

        leitorDAO = new LeitorDAO(conexao);

        funcionarioDAO = new FuncionarioDAO(conexao);
    }

    private void validarCopia(Copia copia){
        if(copia == null){
            throw new EntidadeNaoEncontradaException("Cópia não encontrada");
        }   

        if(!copia.estaDisponivel()){
            throw new RegraNegocioException("Cópia indisponivel!");
        }
    }

    public void realizarEmprestimo(int idCopia,int idLeitor,int idFuncionario)throws SQLException{
        try{
            conexao.setAutoCommit(false);

            int quantidade = emprestimoDAO.emprestimosAtivos(idLeitor);
            if(quantidade >= 3){
                throw new RegraNegocioException("Quantidade de emprestimos atingida!");
            }

            Copia copia = copiaDAO.buscarPorId(idCopia);
            validarCopia(copia);


            Emprestimo emp = new Emprestimo();

            LocalDate hoje = LocalDate.now();
            LocalDate dataDevolucao = hoje.plusDays(30);

            emp.setDataSaida(hoje);
            emp.setDataDevolucao(dataDevolucao);
            emp.setCopia(copia);

            var leitor = leitorDAO.buscarPorId(idLeitor);
            if(leitor == null){
                throw new EntidadeNaoEncontradaException("Leitor não encontrado!");
            }
            emp.setLeitor(leitor);

            var funcionario = funcionarioDAO.buscarPorId(idFuncionario);
            if(funcionario == null){
                throw new EntidadeNaoEncontradaException("Funcionário não encontrado!");
            }
            emp.setFuncionario(funcionario);

            emp.ativar();
            copia.emprestar();
            emprestimoDAO.inserir(emp);
            copiaDAO.atualizar(copia);


            conexao.commit();
            System.out.println("Empréstimo realizado!");
        }   
        catch(Exception e){
            conexao.rollback();
            throw e;
        }
        finally{
            try{
                conexao.setAutoCommit(true);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void realizarDevolucao(int idEmprestimo) throws SQLException{

        try{
            conexao.setAutoCommit(false);

            var emp = emprestimoDAO.buscarPorId(idEmprestimo);

            if(emp == null){
                throw new EntidadeNaoEncontradaException("Emprestimo não encontrado!");
            }
            if(emp.estaFinalizado()){
                throw new RegraNegocioException("Cópia ja foi devolvida!");
            }

            emp.finalizar();
            emp.getCopia().devolver();

            LocalDate hoje = LocalDate.now();
            LocalDate dataDevolucao = hoje;

            emp.setDataDevolucao(dataDevolucao);
            emprestimoDAO.atualizar(emp);
            copiaDAO.atualizar(emp.getCopia());

            conexao.commit();
            System.out.println("Devolução realizada!");
        }
        catch(Exception e){
            conexao.rollback();
            throw e;
            
        }
        finally{
            try{
                conexao.setAutoCommit(true);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}

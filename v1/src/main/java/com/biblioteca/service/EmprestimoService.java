package com.biblioteca.service;

import java.sql.SQLException;
import java.time.LocalDate;

import com.biblioteca.dao.CopiaDAO;
import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.FuncionarioDAO;
import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Emprestimo;

public class EmprestimoService {
    private EmprestimoDAO emprestimoDAO;
    private CopiaDAO copiaDAO;
    private LeitorDAO leitorDAO;
    private FuncionarioDAO funcionarioDAO;

    public EmprestimoService() throws SQLException {

        emprestimoDAO = new EmprestimoDAO();

        copiaDAO = new CopiaDAO();

        leitorDAO = new LeitorDAO();

        funcionarioDAO = new FuncionarioDAO();
        }

        public void realizarEmprestimo(int idCopia,int idLeitor,int idFuncionario)throws SQLException{

        Copia copia = copiaDAO.buscarPorId(idCopia);

        if(!copia.estaDisponivel()){
            System.out.println("Cópia indisponível");
            return;
        }

        Emprestimo emp = new Emprestimo();

        LocalDate hoje = LocalDate.now();
        String dataSaida = hoje.toString();

        String dataDevolucaoo = hoje.plusDays(15).toString();

        emp.setDataSaida(dataSaida);

        emp.setDataDevolucao(dataDevolucaoo);

        emp.setCopia(copia);

        emp.setLeitor(leitorDAO.buscarPorId(idLeitor));

        emp.setFuncionario(funcionarioDAO.buscarPorId(idFuncionario));

        emp.ativar();

        copia.emprestar();

        emprestimoDAO.inserir(emp);

        copiaDAO.atualizar(copia);

        System.out.println("Empréstimo realizado!");
    }   

    public void realizarDevolucao(int idEmprestimo) throws SQLException{
        var emp = emprestimoDAO.buscarPorId(idEmprestimo);

        if(emp == null){
            System.out.println("Emprestimo não encontrado!");
            return;
        }

        if(emp.estaFinalizado()){
            System.out.println("Copia ja foi devolvida!");
            return;
        }

        emp.finalizar();

        emp.getCopia().devolver();

        LocalDate hoje = LocalDate.now();
        String dataDevolucao = hoje.toString();

        emp.setDataDevolucao(dataDevolucao);

        emprestimoDAO.atualizar(emp);

        copiaDAO.atualizar(emp.getCopia());
    }

}

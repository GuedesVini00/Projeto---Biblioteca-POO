package com.biblioteca.ui;

import java.awt.GridLayout;
import java.text.ParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.biblioteca.dao.CopiaDAO;
import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.FuncionarioDAO;
import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Funcionario;
import com.biblioteca.model.Leitor;

public class TelaEmprestimo extends JFrame {

    private JFormattedTextField txtDataSaida;
    private JFormattedTextField txtDataDevolucao;

    private JComboBox<String> comboStatus;

    private JComboBox<Copia> comboCopia;
    private JComboBox<Leitor> comboLeitor;
    private JComboBox<Funcionario> comboFuncionario;

    public TelaEmprestimo() {

        setTitle("Cadastro de Empréstimo");

        setSize(700, 480);

        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(7,2,8,8));

        painel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        painel.add(new JLabel("Data Saída:"));

        txtDataSaida = criarCampoMascara("##/##/####");

        painel.add(txtDataSaida);

        painel.add(new JLabel("Data Devolução:"));

        txtDataDevolucao = criarCampoMascara("##/##/####");

        painel.add(txtDataDevolucao);

        painel.add(new JLabel("Status:"));

        comboStatus = new JComboBox<>(new String[]{
            "ATIVO",
            "FINALIZADO"
        });
        painel.add(comboStatus);

        painel.add(new JLabel("Cópia:"));

        comboCopia = new JComboBox<>();

        carregarCopias();

        painel.add(comboCopia);

        painel.add(new JLabel("Leitor:"));

        comboLeitor = new JComboBox<>();

        carregarLeitores();

        painel.add(comboLeitor);

        painel.add(new JLabel("Funcionário:"));

        comboFuncionario = new JComboBox<>();

        carregarFuncionarios();

        painel.add(comboFuncionario);

        JButton btnSalvar = Estilo.criarBotao("Salvar");

        btnSalvar.addActionListener(e -> salvarEmprestimo());

        painel.add(new JLabel(""));

        painel.add(btnSalvar);

        add(painel);
    }

    private JFormattedTextField criarCampoMascara(String mascara){

        try {

            MaskFormatter formato = new MaskFormatter(mascara);

            formato.setPlaceholderCharacter('_');

            return new JFormattedTextField(formato);

        }
        catch(ParseException e){

            return new JFormattedTextField();
        }
    }

    private void carregarCopias(){

        try {

            CopiaDAO dao = new CopiaDAO();

            List<Copia> lista = dao.listarTodos();

            for(Copia copia : lista){

                comboCopia.addItem(copia);
            }
        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar cópias");
        }
    }

    private void carregarLeitores(){

        try {

            LeitorDAO dao = new LeitorDAO();

            List<Leitor> lista = dao.listarTodos();

            for(Leitor leitor : lista){

                comboLeitor.addItem(leitor);
            }
        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar leitores");
        }
    }

    private void carregarFuncionarios(){

        try {

            FuncionarioDAO dao = new FuncionarioDAO();

            List<Funcionario> lista = dao.listarTodos();

            for(Funcionario funcionario : lista){

                comboFuncionario.addItem(funcionario);
            }
        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar funcionários");
        }
    }

    private void salvarEmprestimo(){

        try {

            Emprestimo emprestimo = new Emprestimo();

            emprestimo.setDataSaida(txtDataSaida.getText());

            emprestimo.setDataDevolucao(txtDataDevolucao.getText());

            emprestimo.setStatus(
                    comboStatus.getSelectedItem().toString()
            );

            emprestimo.setCopia(
                    (Copia) comboCopia.getSelectedItem()
            );

            emprestimo.setLeitor(
                    (Leitor) comboLeitor.getSelectedItem()
            );

            emprestimo.setFuncionario(
                    (Funcionario) comboFuncionario.getSelectedItem()
            );

            EmprestimoDAO dao = new EmprestimoDAO();

            dao.inserir(emprestimo);

            JOptionPane.showMessageDialog(this,
                    "Empréstimo cadastrado com sucesso!");

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar empréstimo: "
                            + erro.getMessage());
        }
    }
}

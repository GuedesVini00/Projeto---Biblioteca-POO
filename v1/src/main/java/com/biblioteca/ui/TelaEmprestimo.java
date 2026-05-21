package com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.biblioteca.dao.CopiaDAO;
import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.FuncionarioDAO;
import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Funcionario;
import com.biblioteca.model.Leitor;

public class TelaEmprestimo extends TelaBaseCadastro {

    private JFormattedTextField txtDataSaida;
    private JFormattedTextField txtDataDevolucao;

    private JComboBox<String> comboStatus;

    private JComboBox<Copia> comboCopia;
    private JComboBox<Leitor> comboLeitor;
    private JComboBox<Funcionario> comboFuncionario;

    private JTable tabela;

    private DefaultTableModel modeloTabela;

    public TelaEmprestimo() {

        setTitle("Gerenciar Empréstimos");

        setSize(1100, 650);

        setLocationRelativeTo(null);

        JPanel fundo =
                criarFundoCadastro(
                        "Gerenciar Empréstimos"
                );

        JPanel container =
                new JPanel(
                        new BorderLayout(10,10)
                );

        container.setOpaque(false);

        container.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        30,
                        25,
                        30
                )
        );

        JPanel painelFormulario =
                criarCardFormulario(6,2);

        painelFormulario.add(
                new JLabel("Data Saída:")
        );

        txtDataSaida =
                criarCampoMascara("##/##/####");

        painelFormulario.add(txtDataSaida);

        painelFormulario.add(
                new JLabel("Data Devolução:")
        );

        txtDataDevolucao =
                criarCampoMascara("##/##/####");

        LocalDate hoje = LocalDate.now();

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        txtDataSaida.setText(
                hoje.format(formato)
        );

        txtDataDevolucao.setText(
                hoje.plusDays(15).format(formato)
        );

        painelFormulario.add(txtDataDevolucao);

        painelFormulario.add(
                new JLabel("Status:")
        );

        comboStatus =
                new JComboBox<>(
                        new String[]{
                                "ATIVO",
                                "FINALIZADO"
                        }
                );

        painelFormulario.add(comboStatus);

        painelFormulario.add(
                new JLabel("Cópia:")
        );

        comboCopia =
                new JComboBox<>();

        carregarCopias();

        painelFormulario.add(comboCopia);

        painelFormulario.add(
                new JLabel("Leitor:")
        );

        comboLeitor =
                new JComboBox<>();

        carregarLeitores();

        painelFormulario.add(comboLeitor);

        painelFormulario.add(
                new JLabel("Funcionário:")
        );

        comboFuncionario =
                new JComboBox<>();

        carregarFuncionarios();

        painelFormulario.add(comboFuncionario);

        JPanel painelBotoes =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                20,
                                10
                        )
                );

        painelBotoes.setOpaque(false);

        JButton btnSalvar =
                Estilo.criarBotao("Salvar");

        JButton btnAtualizar =
                Estilo.criarBotao("Atualizar");

        JButton btnExcluir =
                Estilo.criarBotao("Excluir");

        btnSalvar.setPreferredSize(
                new java.awt.Dimension(160, 40)
        );

        btnAtualizar.setPreferredSize(
                new java.awt.Dimension(160, 40)
        );

        btnExcluir.setPreferredSize(
                new java.awt.Dimension(160, 40)
        );

        btnSalvar.addActionListener(
                e -> salvarEmprestimo()
        );

        btnAtualizar.addActionListener(
                e -> atualizarEmprestimo()
        );

        btnExcluir.addActionListener(
                e -> excluirEmprestimo()
        );

        painelBotoes.add(btnSalvar);

        painelBotoes.add(btnAtualizar);

        painelBotoes.add(btnExcluir);

        JPanel topo =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        topo.setOpaque(false);

        topo.add(
                painelFormulario,
                BorderLayout.CENTER
        );

        topo.add(
                painelBotoes,
                BorderLayout.SOUTH
        );

        modeloTabela =
                new DefaultTableModel();

        modeloTabela.addColumn("ID");

        modeloTabela.addColumn(
                "Data Saída"
        );

        modeloTabela.addColumn(
                "Data Devolução"
        );

        modeloTabela.addColumn("Status");

        modeloTabela.addColumn("Cópia");

        modeloTabela.addColumn("Leitor");

        modeloTabela.addColumn("Funcionário");

        tabela =
                new JTable(modeloTabela);

        tabela.setRowHeight(24);

        tabela.getSelectionModel().addListSelectionListener(
                e -> preencherCamposTabela()
        );

        JScrollPane scroll =
                new JScrollPane(tabela);

        container.add(
                topo,
                BorderLayout.NORTH
        );

        container.add(
                scroll,
                BorderLayout.CENTER
        );

        fundo.add(
                container,
                BorderLayout.CENTER
        );

        setContentPane(fundo);

        carregarTabela();
    }

    private JFormattedTextField criarCampoMascara(
            String mascara
    ){

        try {

            MaskFormatter formato =
                    new MaskFormatter(mascara);

            formato.setPlaceholderCharacter('_');

            return new JFormattedTextField(
                    formato
            );

        }
        catch(ParseException e){

            return new JFormattedTextField();
        }
    }

    private void carregarCopias(){

        try {

            comboCopia.removeAllItems();

            CopiaDAO dao =
                    new CopiaDAO();

            List<Copia> lista =
                    dao.listarTodos();

            for(Copia copia : lista){

                comboCopia.addItem(copia);
            }

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar cópias"
            );
        }
    }

    private void carregarLeitores(){

        try {

            comboLeitor.removeAllItems();

            LeitorDAO dao =
                    new LeitorDAO();

            List<Leitor> lista =
                    dao.listarTodos();

            for(Leitor leitor : lista){

                comboLeitor.addItem(leitor);
            }

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar leitores"
            );
        }
    }

    private void carregarFuncionarios(){

        try {

            comboFuncionario.removeAllItems();

            FuncionarioDAO dao =
                    new FuncionarioDAO();

            List<Funcionario> lista =
                    dao.listarTodos();

            for(Funcionario funcionario : lista){

                comboFuncionario.addItem(
                        funcionario
                );
            }

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar funcionários"
            );
        }
    }

    private void salvarEmprestimo(){

        try {

            Emprestimo emprestimo =
                    new Emprestimo();

            emprestimo.setDataSaida(
                    txtDataSaida.getText()
            );

            emprestimo.setDataDevolucao(
                    txtDataDevolucao.getText()
            );

            emprestimo.setStatus(
                    comboStatus
                            .getSelectedItem()
                            .toString()
            );

            emprestimo.setCopia(
                    (Copia)
                            comboCopia
                                    .getSelectedItem()
            );

            emprestimo.setLeitor(
                    (Leitor)
                            comboLeitor
                                    .getSelectedItem()
            );

            emprestimo.setFuncionario(
                    (Funcionario)
                            comboFuncionario
                                    .getSelectedItem()
            );

            EmprestimoDAO dao =
                    new EmprestimoDAO();

            dao.inserir(emprestimo);

            JOptionPane.showMessageDialog(
                    this,
                    "Empréstimo cadastrado!"
            );

            carregarTabela();

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao cadastrar: "
                            + erro.getMessage()
            );
        }
    }

    private void atualizarEmprestimo(){

        try {

            int linha = tabela.getSelectedRow();

            if(linha < 0){

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um empréstimo!"
                );

                return;
            }

            Emprestimo emprestimo =
                    new Emprestimo();

            emprestimo.setId(
                    Integer.parseInt(
                            modeloTabela
                                    .getValueAt(linha,0)
                                    .toString()
                    )
            );

            emprestimo.setDataSaida(
                    txtDataSaida.getText()
            );

            emprestimo.setDataDevolucao(
                    txtDataDevolucao.getText()
            );

            emprestimo.setStatus(
                    comboStatus
                            .getSelectedItem()
                            .toString()
            );

            emprestimo.setCopia(
                    (Copia)
                            comboCopia
                                    .getSelectedItem()
            );

            emprestimo.setLeitor(
                    (Leitor)
                            comboLeitor
                                    .getSelectedItem()
            );

            emprestimo.setFuncionario(
                    (Funcionario)
                            comboFuncionario
                                    .getSelectedItem()
            );

            EmprestimoDAO dao =
                    new EmprestimoDAO();

            dao.atualizar(emprestimo);

            JOptionPane.showMessageDialog(
                    this,
                    "Empréstimo atualizado!"
            );

            carregarTabela();

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao atualizar: "
                            + erro.getMessage()
            );
        }
    }

    private void excluirEmprestimo(){

        try {

            int linha = tabela.getSelectedRow();

            if(linha < 0){

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um empréstimo!"
                );

                return;
            }

            int id =
                    Integer.parseInt(
                            modeloTabela
                                    .getValueAt(linha,0)
                                    .toString()
                    );

            EmprestimoDAO dao =
                    new EmprestimoDAO();

            dao.excluir(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Empréstimo excluído!"
            );

            carregarTabela();

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao excluir: "
                            + erro.getMessage()
            );
        }
    }

    private void carregarTabela(){

        try {

            modeloTabela.setRowCount(0);

            EmprestimoDAO dao =
                    new EmprestimoDAO();

            List<Emprestimo> lista =
                    dao.listarTodos();

            for(Emprestimo emp : lista){

                modeloTabela.addRow(
                        new Object[]{
                                emp.getId(),
                                emp.getDataSaida(),
                                emp.getDataDevolucao(),
                                emp.getStatus(),
                                emp.getCopia(),
                                emp.getLeitor(),
                                emp.getFuncionario()
                        }
                );
            }

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar tabela: "
                            + erro.getMessage()
            );
        }
    }

    private void preencherCamposTabela(){

        int linha = tabela.getSelectedRow();

        if(linha >= 0){

            txtDataSaida.setText(
                    modeloTabela
                            .getValueAt(linha,1)
                            .toString()
            );

            txtDataDevolucao.setText(
                    modeloTabela
                            .getValueAt(linha,2)
                            .toString()
            );

            comboStatus.setSelectedItem(
                    modeloTabela
                            .getValueAt(linha,3)
                            .toString()
            );
        }
    }
}
package com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.ParseException;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.biblioteca.dao.FuncionarioDAO;
import com.biblioteca.model.Funcionario;

public class TelaFuncionario extends TelaBaseCadastro {

    private JTextField txtId;
    private JTextField txtNome;
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtDataNascimento;
    private JFormattedTextField txtTelefone;
    private JTextField txtEmail;

    private JComboBox<String> comboCargo;

    private JTable tabela;

    private DefaultTableModel modeloTabela;

    public TelaFuncionario() {

        setTitle("Gerenciar Funcionários");

        setSize(1000, 650);

        setLocationRelativeTo(null);

        JPanel fundo =
                criarFundoCadastro(
                        "Gerenciar Funcionários"
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
                criarCardFormulario(7,2);

        painelFormulario.add(
                new JLabel("ID:")
        );

        txtId = new JTextField();

        txtId.setEditable(false);

        painelFormulario.add(txtId);

        painelFormulario.add(
                new JLabel("Nome:")
        );

        txtNome = new JTextField();

        painelFormulario.add(txtNome);

        painelFormulario.add(
                new JLabel("CPF:")
        );

        txtCpf =
                criarCampoMascara(
                        "###.###.###-##"
                );

        painelFormulario.add(txtCpf);

        painelFormulario.add(
                new JLabel("Data Nascimento:")
        );

        txtDataNascimento =
                criarCampoMascara(
                        "##/##/####"
                );

        painelFormulario.add(
                txtDataNascimento
        );

        painelFormulario.add(
                new JLabel("Telefone:")
        );

        txtTelefone =
                criarCampoMascara(
                        "(##) #####-####"
                );

        painelFormulario.add(
                txtTelefone
        );

        painelFormulario.add(
                new JLabel("Email:")
        );

        txtEmail = new JTextField();

        painelFormulario.add(txtEmail);

        painelFormulario.add(
                new JLabel("Cargo:")
        );

        comboCargo =
                new JComboBox<>(
                        new String[]{
                                "Bibliotecário",
                                "Atendente",
                                "Administrador"
                        }
                );

        painelFormulario.add(comboCargo);

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

        JButton btnLimpar =
                Estilo.criarBotao("Limpar");

        btnSalvar.setPreferredSize(
                new java.awt.Dimension(160,40)
        );

        btnAtualizar.setPreferredSize(
                new java.awt.Dimension(160,40)
        );

        btnExcluir.setPreferredSize(
                new java.awt.Dimension(160,40)
        );

        btnLimpar.setPreferredSize(
                new java.awt.Dimension(160,40)
        );

        btnSalvar.addActionListener(
                e -> salvarFuncionario()
        );

        btnAtualizar.addActionListener(
                e -> atualizarFuncionario()
        );

        btnExcluir.addActionListener(
                e -> excluirFuncionario()
        );

        btnLimpar.addActionListener(
                e -> limparCampos()
        );

        painelBotoes.add(btnSalvar);

        painelBotoes.add(btnAtualizar);

        painelBotoes.add(btnExcluir);

        painelBotoes.add(btnLimpar);

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
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("CPF");
        modeloTabela.addColumn("Nascimento");
        modeloTabela.addColumn("Telefone");
        modeloTabela.addColumn("Email");
        modeloTabela.addColumn("Cargo");

        tabela =
                new JTable(modeloTabela);

        tabela.setRowHeight(24);

        tabela.getSelectionModel()
                .addListSelectionListener(
                        e -> preencherCamposDaTabela()
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

    private void salvarFuncionario(){

        try {

            if(txtNome.getText().trim().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Digite o nome!"
                );

                return;
            }

            Funcionario funcionario =
                    new Funcionario();

            funcionario.setNome(
                    txtNome.getText()
            );

            funcionario.setCpf(
                    txtCpf.getText()
            );

            funcionario.setDataNascimento(
                    txtDataNascimento.getText()
            );

            funcionario.setTelefone(
                    txtTelefone.getText()
            );

            funcionario.setEmail(
                    txtEmail.getText()
            );

            funcionario.setCargo(
                    comboCargo
                            .getSelectedItem()
                            .toString()
            );

            FuncionarioDAO dao =
                    new FuncionarioDAO();

            dao.inserir(funcionario);

            JOptionPane.showMessageDialog(
                    this,
                    "Funcionário cadastrado!"
            );

            limparCampos();

            carregarTabela();
        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro: " + erro.getMessage()
            );
        }
    }

    private void atualizarFuncionario(){

        try {

            if(txtId.getText().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um funcionário!"
                );

                return;
            }

            Funcionario funcionario =
                    new Funcionario();

            funcionario.setId(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            funcionario.setNome(
                    txtNome.getText()
            );

            funcionario.setCpf(
                    txtCpf.getText()
            );

            funcionario.setDataNascimento(
                    txtDataNascimento.getText()
            );

            funcionario.setTelefone(
                    txtTelefone.getText()
            );

            funcionario.setEmail(
                    txtEmail.getText()
            );

            funcionario.setCargo(
                    comboCargo
                            .getSelectedItem()
                            .toString()
            );

            FuncionarioDAO dao =
                    new FuncionarioDAO();

            dao.atualizar(funcionario);

            JOptionPane.showMessageDialog(
                    this,
                    "Funcionário atualizado!"
            );

            limparCampos();

            carregarTabela();
        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro: " + erro.getMessage()
            );
        }
    }

    private void excluirFuncionario(){

        try {

            if(txtId.getText().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione um funcionário!"
                );

                return;
            }

            Funcionario funcionario =
                    new Funcionario();

            funcionario.setId(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            FuncionarioDAO dao =
                    new FuncionarioDAO();

            dao.excluir(funcionario);

            JOptionPane.showMessageDialog(
                    this,
                    "Funcionário excluído!"
            );

            limparCampos();

            carregarTabela();
        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro: " + erro.getMessage()
            );
        }
    }

    private void carregarTabela(){

        try {

            modeloTabela.setRowCount(0);

            FuncionarioDAO dao =
                    new FuncionarioDAO();

            List<Funcionario> lista =
                    dao.listarTodos();

            for(Funcionario funcionario : lista){

                modeloTabela.addRow(
                        new Object[]{
                                funcionario.getId(),
                                funcionario.getNome(),
                                funcionario.getCpf(),
                                funcionario.getDataNascimento(),
                                funcionario.getTelefone(),
                                funcionario.getEmail(),
                                funcionario.getCargo()
                        }
                );
            }
        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar tabela!"
            );
        }
    }

    private void preencherCamposDaTabela(){

        int linha =
                tabela.getSelectedRow();

        if(linha >= 0){

            txtId.setText(
                    modeloTabela
                            .getValueAt(linha,0)
                            .toString()
            );

            txtNome.setText(
                    modeloTabela
                            .getValueAt(linha,1)
                            .toString()
            );

            txtCpf.setText(
                    modeloTabela
                            .getValueAt(linha,2)
                            .toString()
            );

            txtDataNascimento.setText(
                    modeloTabela
                            .getValueAt(linha,3)
                            .toString()
            );

            txtTelefone.setText(
                    modeloTabela
                            .getValueAt(linha,4)
                            .toString()
            );

            txtEmail.setText(
                    modeloTabela
                            .getValueAt(linha,5)
                            .toString()
            );

            comboCargo.setSelectedItem(
                    modeloTabela
                            .getValueAt(linha,6)
                            .toString()
            );
        }
    }

    private void limparCampos(){

        txtId.setText("");

        txtNome.setText("");

        txtCpf.setText("");

        txtDataNascimento.setText("");

        txtTelefone.setText("");

        txtEmail.setText("");

        comboCargo.setSelectedIndex(0);

        tabela.clearSelection();
    }
}
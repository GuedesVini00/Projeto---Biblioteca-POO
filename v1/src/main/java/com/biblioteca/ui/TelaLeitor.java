package com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.model.Leitor;

public class TelaLeitor extends TelaBaseCadastro {

    private JTextField txtId;
    private JTextField txtNome;
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtDataNascimento;
    private JFormattedTextField txtTelefone;
    private JTextField txtEmail;
    private JFormattedTextField txtPesquisarCpf;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaLeitor() {

        setTitle("Gerenciar Leitores");
        setSize(950, 620);
        setLocationRelativeTo(null);

        JPanel fundo = criarFundoCadastro("Gerenciar Leitores");

        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(10, 30, 25, 30));

        JPanel painelPesquisa = criarCardFormulario(1, 4);

        painelPesquisa.add(new JLabel("Pesquisar CPF:"));
        txtPesquisarCpf = criarCampoMascara("###.###.###-##");
        painelPesquisa.add(txtPesquisarCpf);

        JButton btnPesquisar = Estilo.criarBotao("Pesquisar");
        JButton btnListarTodos = Estilo.criarBotao("Listar Todos");

        btnPesquisar.addActionListener(e -> pesquisarPorCpf());
        btnListarTodos.addActionListener(e -> carregarTabela());

        painelPesquisa.add(btnPesquisar);
        painelPesquisa.add(btnListarTodos);

        JPanel painelFormulario = criarCardFormulario(6, 2);

        painelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelFormulario.add(txtId);

        painelFormulario.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelFormulario.add(txtNome);

        painelFormulario.add(new JLabel("CPF:"));
        txtCpf = criarCampoMascara("###.###.###-##");
        painelFormulario.add(txtCpf);

        painelFormulario.add(new JLabel("Data Nascimento:"));
        txtDataNascimento = criarCampoMascara("##/##/####");
        painelFormulario.add(txtDataNascimento);

        painelFormulario.add(new JLabel("Telefone:"));
        txtTelefone = criarCampoMascara("(##) #####-####");
        painelFormulario.add(txtTelefone);

        painelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painelFormulario.add(txtEmail);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        painelBotoes.setOpaque(false);

        JButton btnSalvar = Estilo.criarBotao("Salvar");
        JButton btnAtualizar = Estilo.criarBotao("Atualizar");
        JButton btnExcluir = Estilo.criarBotao("Excluir");
        JButton btnLimpar = Estilo.criarBotao("Limpar");

        btnSalvar.addActionListener(e -> salvarLeitor());
        btnAtualizar.addActionListener(e -> atualizarLeitor());
        btnExcluir.addActionListener(e -> excluirLeitor());
        btnLimpar.addActionListener(e -> limparCampos());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        JPanel topo = new JPanel(new BorderLayout(10, 10));
        topo.setOpaque(false);
        topo.add(painelPesquisa, BorderLayout.NORTH);
        topo.add(painelFormulario, BorderLayout.CENTER);
        topo.add(painelBotoes, BorderLayout.SOUTH);

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("CPF");
        modeloTabela.addColumn("Data Nasc.");
        modeloTabela.addColumn("Telefone");
        modeloTabela.addColumn("Email");

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(24);
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCamposDaTabela());

        JScrollPane scroll = new JScrollPane(tabela);

        container.add(topo, BorderLayout.NORTH);
        container.add(scroll, BorderLayout.CENTER);

        fundo.add(container, BorderLayout.CENTER);
        setContentPane(fundo);

        carregarTabela();
    }

    private JFormattedTextField criarCampoMascara(String mascara) {
        try {
            MaskFormatter formato = new MaskFormatter(mascara);
            formato.setPlaceholderCharacter('_');
            return new JFormattedTextField(formato);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private void salvarLeitor() {
        try {
            Leitor leitor = new Leitor();
            leitor.setNome(txtNome.getText());
            leitor.setCpf(txtCpf.getText());
            leitor.setDataNascimento(txtDataNascimento.getText());
            leitor.setTelefone(txtTelefone.getText());
            leitor.setEmail(txtEmail.getText());

            if(txtNome.getText().isBlank()){

                JOptionPane.showMessageDialog(this,"O nome é obrigatório!");
                return;
            }
            
            LeitorDAO dao = new LeitorDAO();
            dao.inserir(leitor);

            JOptionPane.showMessageDialog(this, "Leitor cadastrado com sucesso!");
            limparCampos();
            carregarTabela();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar leitor: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarLeitor() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pesquise ou selecione um leitor na tabela.");
                return;
            }

            Leitor leitor = new Leitor();
            leitor.setId(Integer.parseInt(txtId.getText()));
            leitor.setNome(txtNome.getText());
            leitor.setCpf(txtCpf.getText());
            leitor.setDataNascimento(txtDataNascimento.getText());
            leitor.setTelefone(txtTelefone.getText());
            leitor.setEmail(txtEmail.getText());

            LeitorDAO dao = new LeitorDAO();
            dao.atualizar(leitor);

            JOptionPane.showMessageDialog(this, "Leitor atualizado com sucesso!");
            limparCampos();
            carregarTabela();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao atualizar leitor: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirLeitor() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Pesquise ou selecione um leitor na tabela.");
                return;
            }

            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir este leitor?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (opcao != JOptionPane.YES_OPTION) {
                return;
            }

            Leitor leitor = new Leitor();
            leitor.setId(Integer.parseInt(txtId.getText()));

            LeitorDAO dao = new LeitorDAO();
            dao.excluir(leitor);

            JOptionPane.showMessageDialog(this, "Leitor excluído com sucesso!");
            limparCampos();
            carregarTabela();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao excluir leitor: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisarPorCpf() {
        try {
            String cpfPesquisado = txtPesquisarCpf.getText();

            modeloTabela.setRowCount(0);

            LeitorDAO dao = new LeitorDAO();
            List<Leitor> lista = dao.listarTodos();

            boolean encontrou = false;

            for (Leitor leitor : lista) {
                if (leitor.getCpf().equals(cpfPesquisado)) {
                    modeloTabela.addRow(new Object[]{
                            leitor.getId(),
                            leitor.getNome(),
                            leitor.getCpf(),
                            leitor.getDataNascimento(),
                            leitor.getTelefone(),
                            leitor.getEmail()
                    });

                    preencherCampos(leitor);
                    encontrou = true;
                    break;
                }
            }

            if (!encontrou) {
                JOptionPane.showMessageDialog(this, "Nenhum leitor encontrado com este CPF.");
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao pesquisar CPF: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTabela() {
        try {
            modeloTabela.setRowCount(0);

            LeitorDAO dao = new LeitorDAO();
            List<Leitor> lista = dao.listarTodos();

            for (Leitor leitor : lista) {
                modeloTabela.addRow(new Object[]{
                        leitor.getId(),
                        leitor.getNome(),
                        leitor.getCpf(),
                        leitor.getDataNascimento(),
                        leitor.getTelefone(),
                        leitor.getEmail()
                });
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar leitores: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCamposDaTabela() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
            txtCpf.setText(modeloTabela.getValueAt(linha, 2).toString());
            txtDataNascimento.setText(modeloTabela.getValueAt(linha, 3).toString());
            txtTelefone.setText(modeloTabela.getValueAt(linha, 4).toString());
            txtEmail.setText(modeloTabela.getValueAt(linha, 5).toString());
        }
    }

    private void preencherCampos(Leitor leitor) {
        txtId.setText(String.valueOf(leitor.getId()));
        txtNome.setText(leitor.getNome());
        txtCpf.setText(leitor.getCpf());
        txtDataNascimento.setText(leitor.getDataNascimento());
        txtTelefone.setText(leitor.getTelefone());
        txtEmail.setText(leitor.getEmail());
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtDataNascimento.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtPesquisarCpf.setText("");
        tabela.clearSelection();
    }
}

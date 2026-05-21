package com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.biblioteca.dao.CopiaDAO;
import com.biblioteca.dao.ObraDAO;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Obra;

public class TelaCopia extends TelaBaseCadastro {

    private JTextField txtId;

    private JTextField txtCodigoPatrimonio;

    private JComboBox<String> comboStatus;

    private JComboBox<Obra> comboObra;

    private JTable tabela;

    private DefaultTableModel modeloTabela;

    public TelaCopia() {

        setTitle("Gerenciar Cópias");

        setSize(1050, 650);

        setLocationRelativeTo(null);

        JPanel fundo =
                criarFundoCadastro(
                        "Gerenciar Cópias"
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
                criarCardFormulario(4,2);

        painelFormulario.add(
                new JLabel("ID:")
        );

        txtId = new JTextField();

        txtId.setEditable(false);

        painelFormulario.add(txtId);

        painelFormulario.add(
                new JLabel("Código Patrimônio:")
        );

        txtCodigoPatrimonio =
                new JTextField();

        painelFormulario.add(
                txtCodigoPatrimonio
        );

        painelFormulario.add(
                new JLabel("Status:")
        );

        comboStatus =
                new JComboBox<>(
                        new String[]{
                                "DISPONIVEL",
                                "EMPRESTADO",
                                "RESERVADO"
                        }
                );

        painelFormulario.add(comboStatus);

        painelFormulario.add(
                new JLabel("Obra:")
        );

        comboObra =
                new JComboBox<>();

        carregarObras();

        painelFormulario.add(comboObra);

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
                e -> salvarCopia()
        );

        btnAtualizar.addActionListener(
                e -> atualizarCopia()
        );

        btnExcluir.addActionListener(
                e -> excluirCopia()
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

        modeloTabela.addColumn(
                "Código Patrimônio"
        );

        modeloTabela.addColumn("Status");

        modeloTabela.addColumn("Obra");

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

    private void carregarObras(){

        try {

            comboObra.removeAllItems();

            ObraDAO dao =
                    new ObraDAO();

            List<Obra> lista =
                    dao.listarTodos();

            for(Obra obra : lista){

                comboObra.addItem(obra);
            }

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar obras!"
            );
        }
    }

    private void salvarCopia(){

        try {

            if(txtCodigoPatrimonio
                    .getText()
                    .trim()
                    .isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Digite o código patrimônio!"
                );

                return;
            }

            Copia copia =
                    new Copia();

            copia.setCodigoPatrimonio(
                    txtCodigoPatrimonio.getText()
            );

            copia.setStatus(
                    comboStatus
                            .getSelectedItem()
                            .toString()
            );

            copia.setObra(
                    (Obra)
                            comboObra
                                    .getSelectedItem()
            );

            CopiaDAO dao =
                    new CopiaDAO();

            dao.inserir(copia);

            JOptionPane.showMessageDialog(
                    this,
                    "Cópia cadastrada!"
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

    private void atualizarCopia(){

        try {

            if(txtId.getText().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma cópia!"
                );

                return;
            }

            Copia copia =
                    new Copia();

            copia.setId(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            copia.setCodigoPatrimonio(
                    txtCodigoPatrimonio.getText()
            );

            copia.setStatus(
                    comboStatus
                            .getSelectedItem()
                            .toString()
            );

            copia.setObra(
                    (Obra)
                            comboObra
                                    .getSelectedItem()
            );

            CopiaDAO dao =
                    new CopiaDAO();

            dao.atualizar(copia);

            JOptionPane.showMessageDialog(
                    this,
                    "Cópia atualizada!"
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

    private void excluirCopia(){

        try {

            if(txtId.getText().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma cópia!"
                );

                return;
            }

            int opcao =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Deseja excluir?",
                            "Confirmação",
                            JOptionPane.YES_NO_OPTION
                    );

            if(opcao != JOptionPane.YES_OPTION){

                return;
            }

            Copia copia =
                    new Copia();

            copia.setId(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            CopiaDAO dao =
                    new CopiaDAO();

            dao.excluir(copia);

            JOptionPane.showMessageDialog(
                    this,
                    "Cópia excluída!"
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

            CopiaDAO dao =
                    new CopiaDAO();

            List<Copia> lista =
                    dao.listarTodos();

            for(Copia copia : lista){

                modeloTabela.addRow(
                        new Object[]{
                                copia.getId(),
                                copia.getCodigoPatrimonio(),
                                copia.getStatus(),
                                copia.getObra()
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

            txtCodigoPatrimonio.setText(
                    modeloTabela
                            .getValueAt(linha,1)
                            .toString()
            );

            comboStatus.setSelectedItem(
                    modeloTabela
                            .getValueAt(linha,2)
                            .toString()
            );
        }
    }

    private void limparCampos(){

        txtId.setText("");

        txtCodigoPatrimonio.setText("");

        comboStatus.setSelectedIndex(0);

        tabela.clearSelection();
    }
}
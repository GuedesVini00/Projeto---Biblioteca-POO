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

import com.biblioteca.dao.ObraDAO;
import com.biblioteca.model.Obra;

public class TelaObra extends TelaBaseCadastro {

    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JFormattedTextField txtDataPublicacao;
    private JTextField txtCategoria;

    private JComboBox<String> comboTipo;

    private JTable tabela;

    private DefaultTableModel modeloTabela;

    public TelaObra() {

        setTitle("Gerenciar Obras");

        setSize(1050, 650);

        setLocationRelativeTo(null);

        JPanel fundo =
                criarFundoCadastro(
                        "Gerenciar Obras"
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
                new JLabel("ID:")
        );

        txtId = new JTextField();

        txtId.setEditable(false);

        painelFormulario.add(txtId);

        painelFormulario.add(
                new JLabel("Título:")
        );

        txtTitulo = new JTextField();

        painelFormulario.add(txtTitulo);

        painelFormulario.add(
                new JLabel("Autor:")
        );

        txtAutor = new JTextField();

        painelFormulario.add(txtAutor);

        painelFormulario.add(
                new JLabel("Data Publicação:")
        );

        txtDataPublicacao =
                criarCampoMascara(
                        "##/##/####"
                );

        painelFormulario.add(
                txtDataPublicacao
        );

        painelFormulario.add(
                new JLabel("Categoria:")
        );

        txtCategoria = new JTextField();

        painelFormulario.add(
                txtCategoria
        );

        painelFormulario.add(
                new JLabel("Tipo:")
        );

        comboTipo =
                new JComboBox<>(
                        new String[]{
                                "Livro",
                                "Revista",
                                "HQ"
                        }
                );

        painelFormulario.add(comboTipo);

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
                e -> salvarObra()
        );

        btnAtualizar.addActionListener(
                e -> atualizarObra()
        );

        btnExcluir.addActionListener(
                e -> excluirObra()
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
        modeloTabela.addColumn("Título");
        modeloTabela.addColumn("Autor");
        modeloTabela.addColumn("Publicação");
        modeloTabela.addColumn("Categoria");
        modeloTabela.addColumn("Tipo");

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

    private void salvarObra(){

        try {

            if(txtTitulo.getText().trim().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Digite o título!"
                );

                return;
            }

            Obra obra = new Obra();

            obra.setTitulo(
                    txtTitulo.getText()
            );

            obra.setAutor(
                    txtAutor.getText()
            );

            obra.setDataPublicacao(
                    txtDataPublicacao.getText()
            );

            obra.setCategoria(
                    txtCategoria.getText()
            );

            obra.setTipo(
                    comboTipo
                            .getSelectedItem()
                            .toString()
            );

            ObraDAO dao =
                    new ObraDAO();

            dao.inserir(obra);

            JOptionPane.showMessageDialog(
                    this,
                    "Obra cadastrada!"
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

    private void atualizarObra(){

        try {

            if(txtId.getText().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma obra!"
                );

                return;
            }

            Obra obra = new Obra();

            obra.setId(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            obra.setTitulo(
                    txtTitulo.getText()
            );

            obra.setAutor(
                    txtAutor.getText()
            );

            obra.setDataPublicacao(
                    txtDataPublicacao.getText()
            );

            obra.setCategoria(
                    txtCategoria.getText()
            );

            obra.setTipo(
                    comboTipo
                            .getSelectedItem()
                            .toString()
            );

            ObraDAO dao =
                    new ObraDAO();

            dao.atualizar(obra);

            JOptionPane.showMessageDialog(
                    this,
                    "Obra atualizada!"
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

    private void excluirObra(){

        try {

            if(txtId.getText().isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Selecione uma obra!"
                );

                return;
            }

            Obra obra = new Obra();

            obra.setId(
                    Integer.parseInt(
                            txtId.getText()
                    )
            );

            ObraDAO dao = new ObraDAO();
            dao.excluir(Integer.parseInt(txtId.getText()));

            JOptionPane.showMessageDialog(
                    this,
                    "Obra excluída!"
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

            ObraDAO dao =
                    new ObraDAO();

            List<Obra> lista =
                    dao.listarTodos();

            for(Obra obra : lista){

                modeloTabela.addRow(
                        new Object[]{
                                obra.getId(),
                                obra.getTitulo(),
                                obra.getAutor(),
                                obra.getDataPublicacao(),
                                obra.getCategoria(),
                                obra.getTipo()
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

            txtTitulo.setText(
                    modeloTabela
                            .getValueAt(linha,1)
                            .toString()
            );

            txtAutor.setText(
                    modeloTabela
                            .getValueAt(linha,2)
                            .toString()
            );

            txtDataPublicacao.setText(
                    modeloTabela
                            .getValueAt(linha,3)
                            .toString()
            );

            txtCategoria.setText(
                    modeloTabela
                            .getValueAt(linha,4)
                            .toString()
            );

            comboTipo.setSelectedItem(
                    modeloTabela
                            .getValueAt(linha,5)
                            .toString()
            );
        }
    }

    private void limparCampos(){

        txtId.setText("");

        txtTitulo.setText("");

        txtAutor.setText("");

        txtDataPublicacao.setText("");

        txtCategoria.setText("");

        comboTipo.setSelectedIndex(0);

        tabela.clearSelection();
    }
}

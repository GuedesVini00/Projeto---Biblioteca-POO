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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.dao.ObraDAO;
import com.biblioteca.dao.ReservaDAO;
import com.biblioteca.model.Leitor;
import com.biblioteca.model.Obra;
import com.biblioteca.model.Reserva;

public class TelaReserva extends TelaBaseCadastro {

    private JTextField txtId;
    private JFormattedTextField txtDataReserva;

    private JComboBox<String> comboStatus;
    private JComboBox<Obra> comboObra;
    private JComboBox<Leitor> comboLeitor;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaReserva() {

        setTitle("Gerenciar Reservas");
        setSize(1050, 650);
        setLocationRelativeTo(null);

        JPanel fundo = criarFundoCadastro("Gerenciar Reservas");

        JPanel container = new JPanel(new BorderLayout(10, 10));
        container.setOpaque(false);
        container.setBorder(BorderFactory.createEmptyBorder(10, 30, 25, 30));

        JPanel painelFormulario = criarCardFormulario(5, 2);

        painelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelFormulario.add(txtId);

        painelFormulario.add(new JLabel("Data Reserva:"));
        txtDataReserva = criarCampoMascara("##/##/####");

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtDataReserva.setText(hoje.format(formato));

        painelFormulario.add(txtDataReserva);

        painelFormulario.add(new JLabel("Status:"));
        comboStatus = new JComboBox<>(new String[]{
                "ATIVA",
                "CANCELADA",
                "FINALIZADA"
        });
        painelFormulario.add(comboStatus);

        painelFormulario.add(new JLabel("Obra:"));
        comboObra = new JComboBox<>();
        painelFormulario.add(comboObra);

        painelFormulario.add(new JLabel("Leitor:"));
        comboLeitor = new JComboBox<>();
        painelFormulario.add(comboLeitor);

        carregarObras();
        carregarLeitores();

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setOpaque(false);

        JButton btnSalvar = Estilo.criarBotao("Salvar");
        JButton btnAtualizar = Estilo.criarBotao("Atualizar");
        JButton btnExcluir = Estilo.criarBotao("Excluir");
        JButton btnLimpar = Estilo.criarBotao("Limpar");

        btnSalvar.setPreferredSize(new java.awt.Dimension(160, 40));
        btnAtualizar.setPreferredSize(new java.awt.Dimension(160, 40));
        btnExcluir.setPreferredSize(new java.awt.Dimension(160, 40));
        btnLimpar.setPreferredSize(new java.awt.Dimension(160, 40));

        btnSalvar.addActionListener(e -> salvarReserva());
        btnAtualizar.addActionListener(e -> atualizarReserva());
        btnExcluir.addActionListener(e -> excluirReserva());
        btnLimpar.addActionListener(e -> limparCampos());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        JPanel topo = new JPanel(new BorderLayout(10, 10));
        topo.setOpaque(false);
        topo.add(painelFormulario, BorderLayout.CENTER);
        topo.add(painelBotoes, BorderLayout.SOUTH);

        modeloTabela = new DefaultTableModel();

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Data Reserva");
        modeloTabela.addColumn("Status");
        modeloTabela.addColumn("Obra");
        modeloTabela.addColumn("Leitor");

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(24);
        tabela.getSelectionModel().addListSelectionListener(e -> preencherCamposTabela());

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

    private void carregarObras() {
        try {
            comboObra.removeAllItems();

            ObraDAO dao = new ObraDAO();
            List<Obra> lista = dao.listarTodos();

            for (Obra obra : lista) {
                comboObra.addItem(obra);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar obras: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarLeitores() {
        try {
            comboLeitor.removeAllItems();

            LeitorDAO dao = new LeitorDAO();
            List<Leitor> lista = dao.listarTodos();

            for (Leitor leitor : lista) {
                comboLeitor.addItem(leitor);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar leitores: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarReserva() {
        try {
            if (comboObra.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma obra.");
                return;
            }

            if (comboLeitor.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um leitor.");
                return;
            }

            Reserva reserva = new Reserva();

            reserva.setDataReserva(txtDataReserva.getText());
            reserva.setStatus(comboStatus.getSelectedItem().toString());
            reserva.setObra((Obra) comboObra.getSelectedItem());
            reserva.setLeitor((Leitor) comboLeitor.getSelectedItem());

            ReservaDAO dao = new ReservaDAO();
            dao.inserir(reserva);

            JOptionPane.showMessageDialog(this, "Reserva cadastrada!");

            limparCampos();
            carregarTabela();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar reserva: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarReserva() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione uma reserva.");
                return;
            }

            if (comboObra.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma obra.");
                return;
            }

            if (comboLeitor.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione um leitor.");
                return;
            }

            Reserva reserva = new Reserva();

            reserva.setId(Integer.parseInt(txtId.getText()));
            reserva.setDataReserva(txtDataReserva.getText());
            reserva.setStatus(comboStatus.getSelectedItem().toString());
            reserva.setObra((Obra) comboObra.getSelectedItem());
            reserva.setLeitor((Leitor) comboLeitor.getSelectedItem());

            ReservaDAO dao = new ReservaDAO();
            dao.atualizar(reserva);

            JOptionPane.showMessageDialog(this, "Reserva atualizada!");

            limparCampos();
            carregarTabela();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao atualizar reserva: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirReserva() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione uma reserva.");
                return;
            }

            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir esta reserva?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (opcao != JOptionPane.YES_OPTION) {
                return;
            }

            int id = Integer.parseInt(txtId.getText());

            ReservaDAO dao = new ReservaDAO();
            dao.excluir(id);

            JOptionPane.showMessageDialog(this, "Reserva excluída!");

            limparCampos();
            carregarTabela();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao excluir reserva: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarTabela() {
        try {
            modeloTabela.setRowCount(0);

            ReservaDAO reservaDAO = new ReservaDAO();
            ObraDAO obraDAO = new ObraDAO();
            LeitorDAO leitorDAO = new LeitorDAO();

            List<Reserva> lista = reservaDAO.listarTodos();

            for (Reserva reserva : lista) {

                Obra obraCompleta = obraDAO.buscarPorId(reserva.getObra().getId());
                Leitor leitorCompleto = leitorDAO.buscarPorId(reserva.getLeitor().getId());

                modeloTabela.addRow(new Object[]{
                        reserva.getId(),
                        reserva.getDataReserva(),
                        reserva.getStatus(),
                        obraCompleta.getTitulo(),
                        leitorCompleto.getNome()
                });
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar tabela: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCamposTabela() {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
            txtDataReserva.setText(modeloTabela.getValueAt(linha, 1).toString());
            comboStatus.setSelectedItem(modeloTabela.getValueAt(linha, 2).toString());

            String tituloObra = modeloTabela.getValueAt(linha, 3).toString();
            String nomeLeitor = modeloTabela.getValueAt(linha, 4).toString();

            selecionarObraPorTitulo(tituloObra);
            selecionarLeitorPorNome(nomeLeitor);
        }
    }

    private void selecionarObraPorTitulo(String tituloObra) {
        for (int i = 0; i < comboObra.getItemCount(); i++) {
            Obra obra = comboObra.getItemAt(i);

            if (obra.getTitulo().equals(tituloObra)) {
                comboObra.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selecionarLeitorPorNome(String nomeLeitor) {
        for (int i = 0; i < comboLeitor.getItemCount(); i++) {
            Leitor leitor = comboLeitor.getItemAt(i);

            if (leitor.getNome().equals(nomeLeitor)) {
                comboLeitor.setSelectedIndex(i);
                return;
            }
        }
    }

    private void limparCampos() {
        txtId.setText("");

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtDataReserva.setText(hoje.format(formato));

        comboStatus.setSelectedIndex(0);

        if (comboObra.getItemCount() > 0) {
            comboObra.setSelectedIndex(0);
        }

        if (comboLeitor.getItemCount() > 0) {
            comboLeitor.setSelectedIndex(0);
        }

        tabela.clearSelection();
    }
}
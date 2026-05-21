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

import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.dao.ObraDAO;
import com.biblioteca.dao.ReservaDAO;
import com.biblioteca.model.Leitor;
import com.biblioteca.model.Obra;
import com.biblioteca.model.Reserva;

public class TelaReserva extends JFrame {

    private JFormattedTextField txtDataReserva;
    private JComboBox<String> comboStatus;
    private JComboBox<Obra> comboObra;
    private JComboBox<Leitor> comboLeitor;

    public TelaReserva() {

        setTitle("Cadastro de Reserva");
        setSize(500, 320);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(5, 2, 8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Data Reserva:"));
        txtDataReserva = criarCampoMascara("##/##/####");
        painel.add(txtDataReserva);

        painel.add(new JLabel("Status:"));
        comboStatus = new JComboBox<>(new String[]{
                "ATIVO",
                "CANCELADA",
                "FINALIZADA"
        });
        painel.add(comboStatus);

        painel.add(new JLabel("Obra:"));
        comboObra = new JComboBox<>();
        carregarObras();
        painel.add(comboObra);

        painel.add(new JLabel("Leitor:"));
        comboLeitor = new JComboBox<>();
        carregarLeitores();
        painel.add(comboLeitor);

        JButton btnSalvar = Estilo.criarBotao("Salvar");
        btnSalvar.addActionListener(e -> salvarReserva());

        painel.add(new JLabel(""));
        painel.add(btnSalvar);

        add(painel);
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
            ObraDAO dao = new ObraDAO();
            List<Obra> obras = dao.listarTodos();

            for (Obra obra : obras) {
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
            LeitorDAO dao = new LeitorDAO();
            List<Leitor> leitores = dao.listarTodos();

            for (Leitor leitor : leitores) {
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
            Reserva reserva = new Reserva();

            reserva.setDataReserva(txtDataReserva.getText());
            reserva.setStatus(comboStatus.getSelectedItem().toString());
            reserva.setObra((Obra) comboObra.getSelectedItem());
            reserva.setLeitor((Leitor) comboLeitor.getSelectedItem());

            ReservaDAO dao = new ReservaDAO();
            dao.inserir(reserva);

            JOptionPane.showMessageDialog(this, "Reserva cadastrada com sucesso!");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar reserva: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

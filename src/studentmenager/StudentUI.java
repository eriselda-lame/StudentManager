/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmenager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
/**
 *
 * @author User
 */
public class StudentUI extends JFrame {
      private JTextField txtId,txtEmri,txtNota;
    private JTable tabela;
    private DefaultTableModel modeli;
    public StudentUI() {
        setTitle("Student Manager");
        setSize(650,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Paneli kryesor
        JPanel panel=new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        // Formulari
        JPanel formulari=new JPanel( new GridLayout(4,2,8,8));
        txtId = new JTextField();
        txtEmri = new JTextField();
        txtNota = new JTextField();
        JButton btnShto=new JButton("Shto student");
        JButton btnFshi=new JButton("Fshi student");
        formulari.add(new JLabel("ID:"));
        formulari.add(txtId);
        formulari.add(new JLabel("Emri:"));
        formulari.add(txtEmri);
        formulari.add(new JLabel("Nota:"));
        formulari.add(txtNota);
        formulari.add(btnShto);
        formulari.add(btnFshi);
        // Tabela
        modeli = new DefaultTableModel(new String[]{"ID","Emri","Nota"}, 0 );
        tabela = new JTable(modeli);
        // Renditja alfabetike sipas emrit
        javax.swing.table.TableRowSorter<DefaultTableModel>sorter =new javax.swing.table.TableRowSorter<>(modeli);
tabela.setRowSorter(sorter);
sorter.setSortKeys(java.util.Collections.singletonList(new javax.swing.RowSorter.SortKey
        ( 1, javax.swing.SortOrder.ASCENDING)
    )
);
        ngarkoStudentet();
        JScrollPane scroll = new JScrollPane(tabela);
        // Eventet e butonave
        btnShto.addActionListener(e -> shtoStudent());
        btnFshi.addActionListener(e -> fshiStudent());
        panel.add(formulari, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        add(panel);
    }
    private void shtoStudent() {
        try {
            int id = Integer.parseInt(
                txtId.getText().trim()
            );
            String emri = txtEmri.getText().trim();
            double nota = Double.parseDouble( txtNota.getText().trim());
            if (emri.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Shkruaj emrin e studentit!");
                return;
            }
            if (nota<4||nota>10) {
                JOptionPane.showMessageDialog(this, "Nota duhet te jete nga 4 deri ne 10!");
                return;
            }
            // Kontrolli i ID-se
            for (int i = 0; i < modeli.getRowCount(); i++) {
                int idEkzistues = Integer.parseInt(
                    modeli.getValueAt(i, 0).toString()
                );
                if (idEkzistues == id) {JOptionPane.showMessageDialog(this, "Ky ID ekziston!");
                    return;
                }
            }
            // Shtimi ne tabele
            modeli.addRow(new Object[]{id, emri, nota});
            ruajStudentet();
            // Pastro fushat
            txtId.setText("");
            txtEmri.setText("");
            txtNota.setText("");
        } catch (NumberFormatException ex) {
           JOptionPane.showMessageDialog( this,"ID duhet te jete numer i plote dhe nota numer!");
        }
    }   
private void ruajStudentet() {
    try (BufferedWriter writer = Files.newBufferedWriter(
            Paths.get(System.getProperty("user.dir"),"studentet.txt"),
            StandardCharsets.UTF_8)) {
         
for(int i=0;i<tabela.getRowCount();i++) {
    int rreshtiModelit=tabela.convertRowIndexToModel(i);
    String id = modeli.getValueAt(rreshtiModelit, 0).toString();
    String emri = modeli.getValueAt(rreshtiModelit, 1).toString();
    String nota = modeli.getValueAt(rreshtiModelit, 2).toString();
    writer.write(id+";"+emri+";"+nota);
    writer.newLine();
}
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this,"Gabim gjate ruajtjes se studenteve:"+ ex.getMessage());
    }
}
private void ngarkoStudentet() {
    Path skedari=Paths.get(System.getProperty("user.dir"),"studentet.txt");
    if (!Files.exists(skedari)) {
        return;
    }
    try (BufferedReader reader = Files.newBufferedReader(skedari, StandardCharsets.UTF_8)){
        String rreshti;
        while((rreshti=reader.readLine())!=null){
            String[] teDhena = rreshti.split(";",-1);
            if (teDhena.length != 3) {
                continue;
            }
            try {
                int id = Integer.parseInt(teDhena[0]);
                String emri=teDhena[1];
                double nota=Double.parseDouble(teDhena[2]);
                if (emri.trim().isEmpty()||nota<4||nota>10) {
                    continue;
                }
                modeli.addRow(new Object[]{id, emri, nota});
            } catch (NumberFormatException ex) {
                // Kalo rreshtat me te dhena te pavlefshme
            }
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this,"Gabim gjate leximit te skedarit:"+ ex.getMessage());
    }
}
    private void fshiStudent() {
        int rreshti=tabela.getSelectedRow();
        if (rreshti==-1) {
            JOptionPane.showMessageDialog(this, "Zgjidh nje student nga tabela!");
            return;
        }
        modeli.removeRow(rreshti);
        ruajStudentet();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentUI().setVisible(true);
        });
    }
}

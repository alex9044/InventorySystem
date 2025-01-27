    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAO.VendaDAO;
import conex.conexao;import java.awt.Dialog;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.ModParcela;
import model.ModVenda;
import util.ModHeader;
import util.Util;

/**
 *
 * @author ALEX PC
 */
public class frmParcelas extends javax.swing.JFrame {
    
    private int qtdParcelas;
    private int  nroParcelas = 1;
    private float porcetagem, valorVenda, valorParcela, valorTotal;
    
    Util utilidades = new Util();
    ModVenda modeloVenda;
    ModParcela modeloParcela = new ModParcela();;
    VendaDAO repositorio = new VendaDAO();
    conexao conn = new conexao();
    
    
    
    frmParcelasBaixa frmBaixaParcelas = new frmParcelasBaixa();
    
    
    
    //Formatar a data 
    SimpleDateFormat converterData = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date(); //data atual
    
    DefaultTableModel tabelaParcela;
    
    public frmParcelas(ModVenda modeloVenda) {
        initComponents();
        this.modeloVenda = modeloVenda;  
        this.txtIdVendaParcelas.setText(returnNro_factura(modeloVenda.getId_venda()));
        this.txtValorVendaParcelas.setText(utilidades.formatoPrecio(this.modeloVenda.getTotal()));
        this.txtDataVendaParcelas.setText(String.valueOf(this.converterData.format(data)));
        this.btnGerarDuplicatas.setEnabled(false);
        this.txtQtdeParcelas.requestFocus();
        this.setFocusableWindowState(true);
        ajustarTabelaParcelas();
    }

    private frmParcelas() {
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        txtIdVendaParcelas = new javax.swing.JTextField();
        txtValorVendaParcelas = new javax.swing.JTextField();
        txtDataVendaParcelas = new javax.swing.JTextField();
        btnGerarDuplicatas = new javax.swing.JButton();
        txtQtdeParcelas = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        txtValorTotalParcelas = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        txtJurosParcelas = new javax.swing.JTextField();
        btnParcelar = new javax.swing.JButton();
        jLabel85 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaParcelas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Formulario Parcelas");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 34, 57));

        jPanel45.setBackground(java.awt.Color.lightGray);
        jPanel45.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Parcelamento de Vendas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 14), new java.awt.Color(0, 34, 57))); // NOI18N
        jPanel45.setForeground(new java.awt.Color(0, 34, 57));

        jLabel77.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(0, 34, 57));
        jLabel77.setText("Nro. Nota");

        jLabel78.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(0, 34, 57));
        jLabel78.setText("Valor venda");

        jLabel79.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(0, 34, 57));
        jLabel79.setText("Data");

        txtIdVendaParcelas.setBackground(new java.awt.Color(255, 255, 255));
        txtIdVendaParcelas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtIdVendaParcelas.setForeground(new java.awt.Color(0, 34, 57));
        txtIdVendaParcelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdVendaParcelasKeyPressed(evt);
            }
        });

        txtValorVendaParcelas.setBackground(new java.awt.Color(255, 255, 255));
        txtValorVendaParcelas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtValorVendaParcelas.setForeground(new java.awt.Color(0, 34, 57));

        txtDataVendaParcelas.setBackground(new java.awt.Color(255, 255, 255));
        txtDataVendaParcelas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtDataVendaParcelas.setForeground(new java.awt.Color(0, 34, 57));

        btnGerarDuplicatas.setBackground(new java.awt.Color(0, 34, 57));
        btnGerarDuplicatas.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnGerarDuplicatas.setForeground(new java.awt.Color(255, 255, 255));
        btnGerarDuplicatas.setText("Gerar Parcelas");
        btnGerarDuplicatas.setBorder(null);
        btnGerarDuplicatas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarDuplicatasActionPerformed(evt);
            }
        });

        txtQtdeParcelas.setBackground(new java.awt.Color(255, 255, 255));
        txtQtdeParcelas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtQtdeParcelas.setForeground(new java.awt.Color(0, 34, 57));
        txtQtdeParcelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtdeParcelasKeyPressed(evt);
            }
        });

        jLabel82.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(0, 34, 57));
        jLabel82.setText("Qtd. Parcelas");

        txtValorTotalParcelas.setBackground(new java.awt.Color(255, 255, 255));
        txtValorTotalParcelas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtValorTotalParcelas.setForeground(new java.awt.Color(0, 34, 57));

        jLabel83.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(0, 34, 57));
        jLabel83.setText("Valor total");

        jLabel84.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(0, 34, 57));
        jLabel84.setText("Juros ");

        txtJurosParcelas.setBackground(new java.awt.Color(255, 255, 255));
        txtJurosParcelas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtJurosParcelas.setForeground(new java.awt.Color(0, 34, 57));
        txtJurosParcelas.setText("0");
        txtJurosParcelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtJurosParcelasKeyPressed(evt);
            }
        });

        btnParcelar.setBackground(new java.awt.Color(0, 34, 57));
        btnParcelar.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnParcelar.setForeground(new java.awt.Color(255, 255, 255));
        btnParcelar.setText("Parcelar");
        btnParcelar.setBorder(null);
        btnParcelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParcelarActionPerformed(evt);
            }
        });

        jLabel85.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(0, 34, 57));
        jLabel85.setText("%");

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdVendaParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel77)
                    .addComponent(jLabel84)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(txtJurosParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel85))
                    .addComponent(txtQtdeParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtValorTotalParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel83))
                        .addGap(242, 242, 242))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataVendaParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorVendaParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGerarDuplicatas, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnParcelar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdVendaParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(txtQtdeParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel84, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtJurosParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel85, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel45Layout.createSequentialGroup()
                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addComponent(txtValorVendaParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDataVendaParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel45Layout.createSequentialGroup()
                                .addComponent(btnParcelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(btnGerarDuplicatas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorTotalParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
        );

        tabelaParcelas.setBackground(new java.awt.Color(255, 255, 255));
        tabelaParcelas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tabelaParcelas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tabelaParcelas.setForeground(new java.awt.Color(0, 34, 57));
        tabelaParcelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro Parcela", "Data", "Valor"
            }
        ));
        tabelaParcelas.setEnabled(false);
        tabelaParcelas.setRowHeight(25);
        tabelaParcelas.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tabelaParcelas.setSelectionForeground(new java.awt.Color(0, 34, 57));
        jScrollPane1.setViewportView(tabelaParcelas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGerarDuplicatasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarDuplicatasActionPerformed
        int controladorParcelas = 1;
        String dataVenc = txtDataVendaParcelas.getText();
        String dia =  ""+dataVenc.charAt(0)+dataVenc.charAt(1);
        String mes = ""+dataVenc.charAt(3)+dataVenc.charAt(4);
        int anoInt = Integer.parseInt(""+dataVenc.charAt(6)+dataVenc.charAt(7)+dataVenc.charAt(8)+dataVenc.charAt(9));
        int mesInt = Integer.parseInt(mes);
        nroParcelas = Integer.parseInt(txtQtdeParcelas.getText());
        while(controladorParcelas <= nroParcelas){
            if(mesInt < 10){
                modeloParcela.setId_venda(Integer.parseInt(txtIdVendaParcelas.getText()));
                modeloParcela.setValorParcela(valorParcela + (valorParcela * (porcetagem/100)));
                modeloParcela.setValorTotal(utilidades.convertirFloat(txtValorTotalParcelas.getText()));
                modeloParcela.setValorVenda(utilidades.convertirFloat(txtValorVendaParcelas.getText()));
                modeloParcela.setNroParcelas(controladorParcelas);
                modeloParcela.setDataVenc(dia+"/"+"0"+mesInt+"/"+anoInt);
                repositorio.salvarParcelas(modeloParcela);
            }else{
                modeloParcela.setId_venda(Integer.parseInt(txtIdVendaParcelas.getText()));
                modeloParcela.setValorParcela(valorParcela + (valorParcela * (porcetagem/100)));
                modeloParcela.setValorTotal(utilidades.convertirFloat(txtValorTotalParcelas.getText()));
                modeloParcela.setValorVenda(utilidades.convertirFloat(txtValorVendaParcelas.getText()));
                modeloParcela.setNroParcelas(controladorParcelas);
                modeloParcela.setDataVenc(dia +"/"+mesInt+"/"+anoInt);
                repositorio.salvarParcelas(modeloParcela);
            }
            mesInt++;
            if (mesInt > 12) {
                anoInt++;
                mesInt = 1;
            }
            controladorParcelas++;
        }
        JOptionPane.showMessageDialog(null, "Parcelas Geradas");
        dispose();
    }//GEN-LAST:event_btnGerarDuplicatasActionPerformed

    private void btnParcelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParcelarActionPerformed
        if (txtIdVendaParcelas.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingresse o Nro da Nota!");
        }else{
            qtdParcelas = Integer.parseInt(txtQtdeParcelas.getText());
            porcetagem = utilidades.convertirFloat(txtJurosParcelas.getText());
            valorVenda = utilidades.convertirFloat(txtValorVendaParcelas.getText());
            valorParcela = valorVenda/qtdParcelas;
            limparTabelaBaixaParcelas();
            preencherTabelaParcelas();
            btnParcelar.setEnabled(false);
            this.btnGerarDuplicatas.setEnabled(true);
        }
    }//GEN-LAST:event_btnParcelarActionPerformed

    private void txtQtdeParcelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtdeParcelasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.btnParcelar.setEnabled(true);
            this.txtJurosParcelas.requestFocus();
            this.txtValorTotalParcelas.setText("");
            this.btnGerarDuplicatas.setEnabled(false);
        }
    }//GEN-LAST:event_txtQtdeParcelasKeyPressed

    private void txtJurosParcelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJurosParcelasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.btnParcelar.setEnabled(true);
            this.txtValorTotalParcelas.setText("");
        }
    }//GEN-LAST:event_txtJurosParcelasKeyPressed

    private void txtIdVendaParcelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdVendaParcelasKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.txtQtdeParcelas.requestFocus();
        }
    }//GEN-LAST:event_txtIdVendaParcelasKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.toFront();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmParcelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmParcelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmParcelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmParcelas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmParcelas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnGerarDuplicatas;
    public javax.swing.JButton btnParcelar;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaParcelas;
    public javax.swing.JTextField txtDataVendaParcelas;
    public javax.swing.JTextField txtIdVendaParcelas;
    public javax.swing.JTextField txtJurosParcelas;
    public javax.swing.JTextField txtQtdeParcelas;
    public javax.swing.JTextField txtValorTotalParcelas;
    public javax.swing.JTextField txtValorVendaParcelas;
    // End of variables declaration//GEN-END:variables
    
   
    private void ajustarTabelaParcelas(){
        
        tabelaParcela = (DefaultTableModel) tabelaParcelas.getModel();
        
        //Cursor inicia no campo QuantidadeParcelas
        txtIdVendaParcelas.setEnabled(false);
        txtQtdeParcelas.requestFocus();
        
        //Insere o header da tabelaParcelas
        JTableHeader header = tabelaParcelas.getTableHeader();
        header.setDefaultRenderer(new ModHeader());
        tabelaParcelas.setTableHeader(header);
        
        //ajusta as linhas e colunas da tabelaParcelas
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        tabelaParcelas.getColumn(tabelaParcela.getColumnName(0)).setCellRenderer(centro);
        tabelaParcelas.getColumn(tabelaParcela.getColumnName(1)).setCellRenderer(centro);
        tabelaParcelas.getColumn(tabelaParcela.getColumnName(2)).setCellRenderer(direita);
        tabelaParcelas.getColumn(tabelaParcela.getColumnName(1)).setPreferredWidth(100);
        
    }
    
    private void preencherTabelaParcelas(){
        
        //Calcula valor das parcelas
        int index = 1;
        ArrayList<ModVenda> list = new ArrayList<ModVenda>();
        float valorAtualizadoParcela = valorParcela + (valorParcela * (porcetagem/100));
        String data = txtDataVendaParcelas.getText();
        String dia =  ""+data.charAt(0)+data.charAt(1);
        String mes = ""+data.charAt(3)+data.charAt(4);
        int anoInt = Integer.parseInt(""+data.charAt(6)+data.charAt(7)+data.charAt(8)+data.charAt(9));
        int mesInt = Integer.parseInt(mes);
        
        while(index <= qtdParcelas){
            valorTotal = valorTotal + valorAtualizadoParcela;
            if(mesInt < 10){
                list.add(new ModVenda(index, dia+"/"+"0"+mesInt+"/"+anoInt,null,valorAtualizadoParcela,"","","","",0));
            }else {
                list.add(new ModVenda(index, dia+"/"+mesInt+"/"+anoInt,null,valorAtualizadoParcela,"","","","",0));
            }
            mesInt++;
            if (mesInt > 12) {
                anoInt++;
                mesInt = 1;
            }
            index ++;
        }
        
        //SET valor total da venda no campo ValorTotalVenda
        txtValorTotalParcelas.setText(utilidades.formatoPrecio(valorTotal));
        valorTotal = 0;
        //Preenche a tabelaParcelas
        Object[] objeto = new Object[3];
        for (int i = 0; i < list.size(); i++) {
            objeto[0] = returnID(list.get(i).getId_venda());
            objeto[1] = list.get(i).getDataParcela();
            objeto[2] = utilidades.formatoPrecio(list.get(i).getTotal());
            tabelaParcela.addRow(objeto);
        }
        tabelaParcelas.setModel(tabelaParcela);
    }
    
    private void limparTabelaBaixaParcelas(){
        for (int i = 0; i < tabelaParcela.getRowCount(); i++) {
            tabelaParcela.removeRow(i);
            i = i-1;
        }
    }
    
    private String returnNro_factura(int number){
        String numero = "";
        int length = String.valueOf(number).length();
        if(length == 1){
                numero = "0000"+number;
        }
        if(length == 2){
                numero = "000"+number;
        }
        if(length == 3){
                numero = "00"+number;
        }
        if(length == 4){
                numero = "0"+number;
        }
        if(length == 5){
                numero = ""+number;
        }
        return numero;
    }
    
    private String returnID(int number){
        String numero = "";
        int length = String.valueOf(number).length();
        if(length == 1){
                numero = "0"+number;
        }
        if(length == 2){
                numero = ""+number;
        }
        return numero;
    }
    
}


package controller;

import DAO.ConfigDAO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.plaf.ColorUIResource;
import model.ModConfiguracao;
import view.frmLogin;
import view.frmMenu;
import view.frmParcelasBaixa;

/**
 *
 * @author ALEX PC
 */
public class controllerConfig implements MouseListener, ActionListener{
    
    private ConfigDAO repositorio;
    private ModConfiguracao modeloConfig;
    private frmMenu menu;
    private frmParcelasBaixa pagarParcelas;

    public controllerConfig(ConfigDAO repositorio, frmMenu menu) {
        this.modeloConfig = repositorio.getConfig();
        this.repositorio = repositorio;
        this.menu = menu;
        this.menu.lblCategorias.addMouseListener(this);
        this.menu.lblClientes.addMouseListener(this);
        this.menu.lblConfiguracoes.addMouseListener(this);
        this.menu.lblFornecedores.addMouseListener(this);
        this.menu.lblInventario.addMouseListener(this);
        this.menu.lblMarcas.addMouseListener(this);
        this.menu.lblNovaCompra.addMouseListener(this);
        this.menu.lblNovaVenda.addMouseListener(this);
        this.menu.lblProdutos.addMouseListener(this);
        this.menu.lblUsuarios.addMouseListener(this);
        this.menu.lblNovoConvencional.addMouseListener(this);
        this.menu.lblVendas.addMouseListener(this);
        this.menu.lblCompras.addMouseListener(this);
        this.menu.TabbedPanePrincipal.addMouseListener(this);
        this.menu.btnModificarConfig.addActionListener(this);
        this.menu.lblLogout.addMouseListener(this);
        this.menu.lblConvencional.addMouseListener(this);
        atualizarCampos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menu.btnModificarConfig){
            if(menu.txtRucEmpresa.equals("") || menu.txtNomeEmpresa.equals("") || menu.txtTelefoneEmpresa.equals("")
                || menu.txtEnderecoEmpresa.equals("") || menu.txtMensagemEmpresa.equals("")){
                JOptionPane.showMessageDialog(null, "Complete todos o campos!");
            }else{
                modeloConfig.setRuc(menu.txtRucEmpresa.getText());
                modeloConfig.setMensagem(menu.txtMensagemEmpresa.getText());
                modeloConfig.setNomeEmpresa(menu.txtNomeEmpresa.getText());
                modeloConfig.setTelefone(menu.txtTelefoneEmpresa.getText());
                modeloConfig.setEndereco(menu.txtEnderecoEmpresa.getText());
                if(repositorio.atualizarConfig(modeloConfig) == true){
                    JOptionPane.showMessageDialog(null, "Configurações atualizadas!");
                    atualizarCampos();
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
       if(e.getSource() == menu.lblConfiguracoes){
            menu.TabbedPanePrincipal.setSelectedIndex(10);
        }else if(e.getSource() == menu.lblLogout){
            menu.dispose();
            frmLogin login = new frmLogin();
            login.setVisible(true);
        }
    }
    
    //Atualiza os campos de Configuracao
    private void atualizarCampos(){
        menu.txtidEmpresa.setText(String.valueOf(modeloConfig.getId_confi()));
        menu.txtRucEmpresa.setText(modeloConfig.getRuc());
        menu.txtNomeEmpresa.setText(modeloConfig.getNomeEmpresa());
        menu.txtTelefoneEmpresa.setText(modeloConfig.getTelefone());
        menu.txtMensagemEmpresa.setText(modeloConfig.getMensagem());
        menu.txtEnderecoEmpresa.setText(modeloConfig.getEndereco());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == menu.lblNovaVenda){
            menu.lblNovaVenda.setForeground(new Color(0,34,57));
            menu.jPanelNovavenda.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblCategorias){
            menu.lblCategorias.setForeground(new Color(0,34,57));
            menu.jPanelCategorias.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblClientes){
            menu.lblClientes.setForeground(new Color(0,34,57));
            menu.jPanelClientes.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblConfiguracoes){
            menu.lblConfiguracoes.setForeground(new Color(0,34,57));
            menu.jPanelConfiguracoes.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblFornecedores){
            menu.lblFornecedores.setForeground(new Color(0,34,57));
            menu.jPanelFornecedores.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblInventario){
            menu.lblInventario.setForeground(new Color(0,34,57));
            menu.jPanelInventario.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblMarcas){
            menu.lblMarcas.setForeground(new Color(0,34,57));
            menu.jPanelMarcas.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblNovaCompra){
           menu.lblNovaCompra.setForeground(new Color(0,34,57));
            menu.jPanelNovaCompra.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblProdutos){
            menu.lblProdutos.setForeground(new Color(0,34,57));
            menu.jPanelProdutos.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblUsuarios){
            menu.lblUsuarios.setForeground(new Color(0,34,57));
            menu.jPanelUsuarios.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblNovoConvencional){
            menu.lblNovoConvencional.setForeground(new Color(0,34,57));
            menu.jPanelNovoConvencional.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblVendas){
            menu.lblVendas.setForeground(new Color(0,34,57));
            menu.jPanelVendas.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblCompras){
            menu.lblCompras.setForeground(new Color(0,34,57));
            menu.jPanelCompras.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblLogout){
            menu.lblLogout.setForeground(new Color(0,34,57));
            menu.jPanelLogout.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }else if(e.getSource() == menu.lblConvencional){
            menu.lblConvencional.setForeground(new Color(0,34,57));
            menu.jPanelConvencional.setBackground(new ColorUIResource(Color.LIGHT_GRAY));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == menu.lblNovaVenda){
            menu.lblNovaVenda.setForeground(Color.LIGHT_GRAY);
            menu.jPanelNovavenda.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblCategorias){
            menu.lblCategorias.setForeground(Color.LIGHT_GRAY);
            menu.jPanelCategorias.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblClientes){
            menu.lblClientes.setForeground(Color.LIGHT_GRAY);
            menu.jPanelClientes.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblConfiguracoes){
            menu.lblConfiguracoes.setForeground(Color.LIGHT_GRAY);
            menu.jPanelConfiguracoes.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblFornecedores){
            menu.lblFornecedores.setForeground(Color.LIGHT_GRAY);
            menu.jPanelFornecedores.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblInventario){
            menu.lblInventario.setForeground(Color.LIGHT_GRAY);
            menu.jPanelInventario.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblMarcas){
            menu.lblMarcas.setForeground(Color.LIGHT_GRAY);
            menu.jPanelMarcas.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblNovaCompra){
            menu.lblNovaCompra.setForeground(Color.LIGHT_GRAY);
            menu.jPanelNovaCompra.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblProdutos){
            menu.lblProdutos.setForeground(Color.LIGHT_GRAY);
            menu.jPanelProdutos.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblUsuarios){
            menu.lblUsuarios.setForeground(Color.LIGHT_GRAY);
            menu.jPanelUsuarios.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblNovoConvencional){
            menu.lblNovoConvencional.setForeground(Color.LIGHT_GRAY);
            menu.jPanelNovoConvencional.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblVendas){
            menu.lblVendas.setForeground(Color.LIGHT_GRAY);
            menu.jPanelVendas.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblCompras){
            menu.lblCompras.setForeground(Color.LIGHT_GRAY);
            menu.jPanelCompras.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblLogout){
            menu.lblLogout.setForeground(Color.LIGHT_GRAY);
            menu.jPanelLogout.setBackground(new Color(0,34,57));
        }else if(e.getSource() == menu.lblConvencional){
            menu.lblConvencional.setForeground(Color.LIGHT_GRAY);
            menu.jPanelConvencional.setBackground(new Color(0,34,57));
        }
        
    }  

    
}

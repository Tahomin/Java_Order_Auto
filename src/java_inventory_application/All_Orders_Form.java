package java_inventory_application;


import CLASS.THE_ORDER;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class All_Orders_Form extends javax.swing.JFrame {

    /**
     * Creates new form All_Orders_Form
     */
    public All_Orders_Form() {
        initComponents();
        
        populateOrderJtable();
        
        jTable_ORDERS.setShowGrid(true);
        
        jTable_ORDERS.setGridColor(Color.YELLOW);
        
        jTable_ORDERS.setSelectionBackground(Color.gray);
        
        JTableHeader th = jTable_ORDERS.getTableHeader();

        th.setFont(new Font("Tahoma", Font.PLAIN, 16));
        
    }

    
    
    public void populateOrderJtable(){
        
        CLASS.THE_ORDER ord = new CLASS.THE_ORDER();
        ArrayList<CLASS.THE_ORDER> OrderList = ord.ordersList();
        
        String[] colNames = {"Id","Date","Customer"};
        Object[][] rows = new Object[OrderList.size()][3];
        DefaultTableModel model = (DefaultTableModel) jTable_ORDERS.getModel();
        
        for(int i = 0; i < OrderList.size(); i++){
            rows[i][0] = OrderList.get(i).getId();
            rows[i][1] = OrderList.get(i).getOrderDate();
            rows[i][2] = OrderList.get(i).getCustomerId();
        }
        
        model.setDataVector(rows, colNames);

        jTable_ORDERS.setModel(model);
        jTable_ORDERS.setRowHeight(45);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_ORDERS = new javax.swing.JTable();
        jButton_PRINT_ORDER = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 215, 110));

        jTable_ORDERS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_ORDERS);

        jButton_PRINT_ORDER.setBackground(new java.awt.Color(142, 68, 173));
        jButton_PRINT_ORDER.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton_PRINT_ORDER.setForeground(new java.awt.Color(255, 255, 255));
        jButton_PRINT_ORDER.setText("Print Selected Order");
        jButton_PRINT_ORDER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PRINT_ORDERActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_PRINT_ORDER, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jButton_PRINT_ORDER, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
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
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_PRINT_ORDERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PRINT_ORDERActionPerformed
        
        Connection connection = CLASS.DB_INFO.getConnection();
        String query = "SELECT o.id, c.first_name, c.last_name, o.order_date from order_tbl o, customer c where o.customer_id = c.id and o.id = ? order by o.id desc";
        String query2 = "select odt.product_id, prd.name, odt.quantity, odt.price, odt.total from order_detail odt, product prd where odt.product_id = prd.id and odt.order_id = ?";

        ResultSet rs;
        PreparedStatement ps;
        ResultSet rs2;
        PreparedStatement ps2;
        
        try {
            Integer index = jTable_ORDERS.getSelectedRow();
            ps = connection.prepareStatement(query);
            ps.setInt(1, Integer.valueOf(jTable_ORDERS.getValueAt(index, 0).toString()));
            
            rs = ps.executeQuery();
           
             //the file path
               File file = new File(System.getProperty("user.home") + "\\OrderFile.txt");
               //if the file not exist create one
               if(!file.exists()){
                   file.createNewFile();
               }
               
               FileWriter fw = new FileWriter(file.getAbsoluteFile());
               BufferedWriter bw = new BufferedWriter(fw);
               
            // Integer ID, String NAME, Integer CATEGORY_ID, String PRICE, byte[] PICTURE, Integer QUANTITY, String DESCRIPTION
         

               //loop for jtable rows
               if(rs.next()){
                  
                   bw.write("ORDER ID : " + rs.getInt("o.id")+"\t" + "DATE : " + rs.getDate("o.order_date"));
                   bw.write(System.lineSeparator());
                   bw.write(System.lineSeparator());
                   bw.write("CUSTOMER : "+rs.getString("c.first_name")+" "+rs.getString("c.last_name"));
                   bw.write(System.lineSeparator());
                   bw.write(System.lineSeparator());
                   bw.write("_____________________________________________________________________________________________________________________");
                   bw.write(System.lineSeparator());
                   //bw.write("\n_________\n");
                   
                   ps2 = connection.prepareStatement(query2);
                   ps2.setInt(1, Integer.valueOf(jTable_ORDERS.getValueAt(index, 0).toString()));
            
                  rs2 = ps2.executeQuery();
                  
                  Integer quantity;
                  double price;
                  double quantity_X_price;
                  
                  
                  while(rs2.next()){
                  
                   quantity = rs2.getInt("odt.quantity");
                   price = Double.valueOf(rs2.getString("odt.price"));
                   quantity_X_price = quantity * Double.valueOf(rs2.getString("odt.price"));
                   
                   bw.write("PRODUCT : " + rs2.getString("prd.name")+"     " + "QUANTITY : " + quantity +"     " + "PRICE : " + price+"     " + "Quantity X Price : " + quantity_X_price);
                   bw.write(System.lineSeparator());
                   bw.write("_____________________________________________________________________________________________________________________");
                   bw.write(System.lineSeparator());

                  }
                  
                   bw.write(System.lineSeparator());
                   bw.write("ORDER TOTAL AMOUNT : " + CLASS.THE_ORDER.getOrderTotalAmount(Integer.valueOf(jTable_ORDERS.getValueAt(index, 0).toString())));
               }
               //close BufferedWriter
               bw.close();
               //close FileWriter 
               fw.close();
               JOptionPane.showMessageDialog(null, "Data Exported");
        
        } catch (Exception ex) {
            Logger.getLogger(All_Orders_Form.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton_PRINT_ORDERActionPerformed

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
            java.util.logging.Logger.getLogger(All_Orders_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(All_Orders_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(All_Orders_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(All_Orders_Form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new All_Orders_Form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_PRINT_ORDER;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_ORDERS;
    // End of variables declaration//GEN-END:variables
}

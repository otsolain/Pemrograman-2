// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class CetakLaporanForm extends JFrame {
   public CetakLaporanForm() {
      this.setTitle("Cetak Laporan");
      this.setSize(300, 150);
      this.setDefaultCloseOperation(3);
      this.setLocationRelativeTo((Component)null);
      this.initComponents();
   }

   private void initComponents() {
      JButton var1 = new JButton("Cetak Laporan");
      var1.addActionListener((var1x) -> this.cetakLaporan());
      this.setLayout(new FlowLayout());
      this.add(var1);
   }

   private void cetakLaporan() {
      String var1 = "jdbc:sqlserver://localhost:1433;databaseName=datamhs;user=SA;password=Haikal123!;encrypt=true;trustServerCertificate=true";

      try {
         Connection var2 = DriverManager.getConnection(var1);
         File var10000 = new File(".");
         String var3 = var10000.getCanonicalPath() + "/LaporanMahasiswa.jrxml";
         JasperReport var4 = JasperCompileManager.compileReport(var3);
         HashMap var5 = new HashMap();
         var5.put("judul", "Laporan Data Mahasiswa");
         JasperPrint var6 = JasperFillManager.fillReport(var4, var5, var2);
         JasperViewer.viewReport(var6, false);
      } catch (Exception var7) {
         JOptionPane.showMessageDialog(this, "Gagal cetak laporan: " + var7.getMessage(), "Error", 0);
         System.out.println("Error: " + var7.toString());
         var7.printStackTrace();
      }

   }

   public static void main(String[] var0) {
      (new CetakLaporanForm()).setVisible(true);
   }
}

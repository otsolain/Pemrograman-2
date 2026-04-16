import javax.swing.*;

public class MenuUtama extends JFrame {

    private JDesktopPane desktopPane;

    public MenuUtama() {
        setTitle("Aplikasi Penjualan Barang");
        setSize(1024, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();

        // Menu Master
        JMenu menuMaster = new JMenu("Master");
        JMenuItem miBarang    = new JMenuItem("Data Barang");
        JMenuItem miCustomer  = new JMenuItem("Data Customer");
        JMenuItem miSupplier  = new JMenuItem("Data Supplier");
        miBarang.addActionListener(e   -> bukaForm(new BarangForm()));
        miCustomer.addActionListener(e -> bukaForm(new CustomerForm()));
        menuMaster.add(miBarang); menuMaster.add(miCustomer); menuMaster.add(miSupplier);

        // Menu Transaksi
        JMenu menuTransaksi = new JMenu("Transaksi");
        JMenuItem miPenjualan  = new JMenuItem("Penjualan");
        JMenuItem miInventory  = new JMenuItem("Inventory Barang");
        miPenjualan.addActionListener(e -> bukaForm(new TransaksiForm()));
        menuTransaksi.add(miPenjualan); menuTransaksi.add(miInventory);

        // Menu Laporan (Pertemuan 7)
        JMenu menuLaporan = new JMenu("Laporan");
        JMenuItem miLapTransaksi = new JMenuItem("Laporan Transaksi");
        JMenuItem miLapInventory = new JMenuItem("Laporan Inventory");
        menuLaporan.add(miLapTransaksi); menuLaporan.add(miLapInventory);

        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuLaporan);
        setJMenuBar(menuBar);
    }

    private void bukaForm(JInternalFrame form) {
        desktopPane.add(form);
        form.setVisible(true);
    }

    public static void main(String[] args) {
        new MenuUtama().setVisible(true);
    }
}

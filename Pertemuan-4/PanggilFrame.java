public class PanggilFrame {

    public static void main(String[] args) {

        // Cara 1: langsung tampilkan
        new MenuUtama().setVisible(true);

        // Cara 2: simpan ke variabel dulu
        MenuUtama frame = new MenuUtama();
        frame.setVisible(true);
    }
}

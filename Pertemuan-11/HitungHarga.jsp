// File: HitungHarga.java | Package: proses
package proses;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "HitungHarga", urlPatterns = {"/proses/HitungHarga"})
public class HitungHarga extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Membaca parameter dari form JSP
        String namaBarang  = request.getParameter("namaBarang");
        double hargaSatuan = Double.parseDouble(request.getParameter("hargaSatuan"));
        int    jumlah      = Integer.parseInt(request.getParameter("jumlah"));

        // Menghitung total harga
        double totalHarga = hargaSatuan * jumlah;

        // Output HTML langsung dari Servlet
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Hasil Hitung Harga</title></head>");
            out.println("<body style='font-family:Arial; margin:40px;'>");
            out.println("<div style='max-width:400px; border:1px solid #ccc; padding:20px; border-radius:8px;'>");
            out.println("<h2>Hasil Perhitungan</h2>");
            out.println("<table border='1' cellpadding='8' style='border-collapse:collapse; width:100%;'>");
            out.println("<tr><td>Nama Barang</td><td><b>" + namaBarang + "</b></td></tr>");
            out.println("<tr><td>Harga Satuan</td><td>Rp " + hargaSatuan + "</td></tr>");
            out.println("<tr><td>Jumlah</td><td>" + jumlah + "</td></tr>");
            out.println("<tr><td><b>Total Harga</b></td><td><b>Rp " + totalHarga + "</b></td></tr>");
            out.println("</table>");
            out.println("<br><a href='../index.jsp'>← Kembali</a>");
            out.println("</div></body></html>");
        }
    }

    // doGet dipanggil saat method form = GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    // doPost dipanggil saat method form = POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}

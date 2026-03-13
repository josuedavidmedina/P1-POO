package operaciones;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public class Comprobante {

    private final String idTx;
    private final String tipoOperacion;
    private final LocalDateTime fechaHora;
    private final String sucursal;
    private final double valorRetiro;
    private final double saldoResultante;
    private final String numeroCuenta;

    public Comprobante(String tipoOperacion, String sucursal,
                       double valorRetiro, double saldoResultante, String numeroCuenta) {
        this.idTx = "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.tipoOperacion = tipoOperacion;
        this.fechaHora = LocalDateTime.now();
        this.sucursal = sucursal;
        this.valorRetiro = valorRetiro;
        this.saldoResultante = saldoResultante;
        this.numeroCuenta = numeroCuenta;
    }


    public void imprimir() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String linea = "─".repeat(44);

        System.out.println("\n  ╔" + "═".repeat(44) + "╗");
        System.out.println("  ║       UNIBANKSA LUD - COMPROBANTE      ║");
        System.out.println("  ╠" + "═".repeat(44) + "╣");
        System.out.printf("  ║  ID Transacción : %-25s║%n", idTx);
        System.out.printf("  ║  Operación      : %-25s║%n", tipoOperacion);
        System.out.printf("  ║  Fecha y Hora   : %-25s║%n", fechaHora.format(fmt));
        System.out.printf("  ║  Cajero/Sucursal: %-25s║%n", sucursal);
        System.out.printf("  ║  Cuenta         : %-25s║%n", "****" + numeroCuenta.substring(Math.max(0, numeroCuenta.length() - 4)));
        System.out.println("  ║  " + linea + "  ║");
        if (valorRetiro > 0) {
            System.out.printf("  ║  Valor Retiro   : $%-24s║%n", String.format("%,.0f", valorRetiro));
        }
        System.out.printf("  ║  Saldo Resultante: $%-23s║%n", String.format("%,.0f", saldoResultante));
        System.out.println("  ╚" + "═".repeat(44) + "╝");
        System.out.println("         Gracias por usar UniBankSalud \n");
    }

    public void mostrarDetalle() {
        imprimir();
    }


    public String getIdTx() { return idTx; }
    public String getTipoOperacion() { return tipoOperacion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getSucursal() { return sucursal; }
    public double getValorRetiro() { return valorRetiro; }
    public double getSaldoResultante() { return saldoResultante; }
}

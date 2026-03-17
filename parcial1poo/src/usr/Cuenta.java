package usr;

public class Cuenta {

    public enum TipoCuenta { CORRIENTE, AHORROS; }

    public String numeroCuenta;
    public TipoCuenta tipoCuenta;        // ✅ corregido
    double saldo;
    public static final double SALDO_INICIAL = 435_000.0;  // ✅ corregido

    public Cuenta(String numeroCuenta, TipoCuenta tipoCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = SALDO_INICIAL;
    }

    public boolean depositar(double monto) {
        if (monto <= 0) {
            System.out.println("  El monto a depositar debe ser mayor a cero.");
            return false;
        }
        this.saldo += monto;
        return true;
    }

    public boolean retirar(double monto) {
        if (monto <= 0) {
            System.out.println("  El monto a retirar debe ser mayor a cero.");
            return false;
        }
        if (monto > this.saldo) {
            System.out.println("  Saldo insuficiente. Saldo disponible: $" + String.format("%,.0f", this.saldo));
            return false;
        }
        this.saldo -= monto;
        return true;
    }

    public double consultarSaldo() {
        return this.saldo;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public TipoCuenta getTipoCuenta() { return tipoCuenta; }  // ✅ corregido
    public double getSaldo() { return saldo; }
}
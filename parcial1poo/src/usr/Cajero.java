package usr;

import operaciones.Comprobante;
import operaciones.Retiro;
import operaciones.ConsultaSaldo;

/**
 * Representa el cajero automático con todas sus restricciones de negocio.
 */
public class Cajero {

    private final String idCajero;
    private final String ubicacion;
    private int retirosHoy;

    public static final double RETIRO_MINIMO  = 20_000.0;
    public static final double RETIRO_MAXIMO  = 400_000.0;
    public static final int    MAX_RETIROS    = 3;

    public Cajero(String idCajero, String ubicacion) {
        this.idCajero   = idCajero;
        this.ubicacion  = ubicacion;
        this.retirosHoy = 0;
    }

    public boolean validarRetiro(double monto, double saldoDisponible) {
        if (retirosHoy >= MAX_RETIROS) {
            System.out.println("  ❌ Ha alcanzado el límite de " + MAX_RETIROS + " retiros por día.");
            return false;
        }
        if (monto < RETIRO_MINIMO) {
            System.out.printf("  ❌ El retiro mínimo es $%,.0f%n", RETIRO_MINIMO);
            return false;
        }
        if (monto > RETIRO_MAXIMO) {
            System.out.printf("  ❌ El retiro máximo es $%,.0f%n", RETIRO_MAXIMO);
            return false;
        }
        if (monto > saldoDisponible) {
            System.out.printf("  ❌ Saldo insuficiente. Disponible: $%,.0f%n", saldoDisponible);
            return false;
        }
        return true;
    }

    public void registrarOperacion() {
        retirosHoy++;
    }

    public Comprobante procesarRetiro(double monto, Cuenta cuenta) {
        if (!validarRetiro(monto, cuenta.consultarSaldo())) {
            return null;
        }
        Retiro retiro = new Retiro(monto, cuenta, ubicacion);
        if (retiro.ejecutar()) {
            registrarOperacion();
            return retiro.generarComprobante();
        }
        return null;
    }

    public Comprobante procesarConsulta(Cuenta cuenta) {
        ConsultaSaldo consulta = new ConsultaSaldo(cuenta, ubicacion);
        consulta.ejecutar();
        return consulta.generarComprobante();
    }

    public String getIdCajero()      { return idCajero; }
    public String getUbicacion()     { return ubicacion; }
    public int getRetirosHoy()       { return retirosHoy; }
    public int getRetirosRestantes() { return MAX_RETIROS - retirosHoy; }
}
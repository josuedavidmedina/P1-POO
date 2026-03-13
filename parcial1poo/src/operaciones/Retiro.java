package operaciones;


import usr.Cuenta;

/**
 * Operación de retiro de dinero.
 */
public class Retiro extends Operacion {

    private boolean exitoso;

    public Retiro(double monto, Cuenta cuenta, String sucursal) {
        super("RETIRO", monto, cuenta, sucursal);
        this.exitoso = false;
    }


    public boolean ejecutar() {
        exitoso = cuenta.retirar(monto);
        return exitoso;
    }


    public Comprobante generarComprobante() {
        return new Comprobante(
                tipoOperacion,
                sucursal,
                exitoso ? monto : 0,
                cuenta.consultarSaldo(),
                cuenta.getNumeroCuenta()
        );
    }

    public boolean isExitoso() { return exitoso; }
}

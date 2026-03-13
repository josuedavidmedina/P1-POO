package operaciones;

import Cuenta.*;


public class ConsultaSaldo extends Operacion {

    public <Cuenta> ConsultaSaldo(Cuenta cuenta, String sucursal) {
        super("CONSULTA SALDO", 0, cuenta, sucursal);
    }


    public boolean ejecutar() {
        double saldo = cuenta.consultarSaldo();
        System.out.printf("%n  💰 Saldo disponible: $%,.0f%n", saldo);
        System.out.printf("  📋 Tipo de cuenta  : %s%n", cuenta.getTipoCuenta());
        System.out.printf("  🔢 N° de cuenta    : ****%s%n%n",
                cuenta.getNumeroCuenta().substring(Math.max(0, cuenta.getNumeroCuenta().length() - 4)));
        return true;
    }


    public Comprobante generarComprobante() {
        return new Comprobante(
                tipoOperacion,
                sucursal,
                0,
                cuenta.consultarSaldo(),
                cuenta.getNumeroCuenta()
        );
    }
}

package operaciones;

import usr.Cuenta;
import java.time.LocalDateTime;

public class Operacion {

    protected String tipoOperacion;
    protected double monto;
    protected LocalDateTime fechaHoraOperacion;
    protected Cuenta cuenta;
    protected String sucursal;

    public Operacion(String tipoOperacion, double monto, Cuenta cuenta, String sucursal) {
        this.tipoOperacion = tipoOperacion;
        this.monto = monto;
        this.cuenta = cuenta;
        this.sucursal = sucursal;
        this.fechaHoraOperacion = LocalDateTime.now();
    }



    public String getTipoOperacion() { return tipoOperacion; }
    public double getMonto() { return monto; }
    public LocalDateTime getFechaHoraOperacion() { return fechaHoraOperacion; }
}

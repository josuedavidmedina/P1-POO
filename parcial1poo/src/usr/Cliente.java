package usr;

public class Cliente {

    private String id;
    private String nombre;
    private String numeroDocumento;
    private Cuenta cuenta;
    private Seguridad seguridad;  // ✅ agregado

    public Cliente(String id, String nombre, String numeroDocumento, Cuenta cuenta, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.numeroDocumento = numeroDocumento;
        this.cuenta = cuenta;
        this.seguridad = new Seguridad(clave);  // ✅ ahora sí usa la clave
    }

    public boolean validarClave(String clave) {
        return seguridad.validarClave(clave);   // ✅ antes siempre retornaba false
    }

    public boolean cambiarClave(String claveActual, String nuevaClave) {
        return seguridad.cambiarClave(claveActual, nuevaClave);  // ✅ antes estaba vacío
    }

    public boolean cuentaBloqueada() {
        return seguridad.estaBloqueada();       // ✅ antes siempre retornaba false
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public Cuenta getCuenta() { return cuenta; }
}
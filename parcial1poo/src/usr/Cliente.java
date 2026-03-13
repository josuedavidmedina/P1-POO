package usr;

public class Cliente {

    private String id;
    private String nombre;
    private String numeroDocumento;
    private Cuenta cuenta;


    public Cliente(String id, String nombre, String numeroDocumento, Cuenta cuenta, String number) {
        this.id = id;
        this.nombre = nombre;
        this.numeroDocumento = numeroDocumento;
        this.cuenta = cuenta;
    }


    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public Cuenta getCuenta() { return cuenta; }

    public void cambiarClave(String claveActual, String nuevaClave) {
    }

    public boolean cuentaBloqueada() {
    }

    public boolean validarClave(String clave) {
    }
}


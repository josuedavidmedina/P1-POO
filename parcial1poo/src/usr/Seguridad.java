package usr;

/**
 * Clase responsable de gestionar la seguridad y autenticación del cajero.
 */
public class Seguridad {

    private String clave;
    private static final int MAX_INTENTOS = 3;
    private int intentosFallidos;

    public Seguridad(String clave) {
        this.clave = clave;
        this.intentosFallidos = 0;
    }

    /**
     * Valida si la clave ingresada coincide con la clave registrada.
     */
    public boolean validarClave(String claveIngresada) {
        if (claveIngresada == null || claveIngresada.length() != 4 || !claveIngresada.matches("\\d{4}")) {
            System.out.println("  ❌ La clave debe ser de 4 dígitos numéricos.");
            return false;
        }
        if (claveIngresada.equals(this.clave)) {
            intentosFallidos = 0;
            return true;
        } else {
            intentosFallidos++;
            int restantes = MAX_INTENTOS - intentosFallidos;
            if (restantes > 0) {
                System.out.println("  ❌ Clave incorrecta. Intentos restantes: " + restantes);
            } else {
                System.out.println("  🔒 Tarjeta bloqueada por exceso de intentos fallidos.");
            }
            return false;
        }
    }

    /**
     * Verifica si la cuenta está bloqueada.
     */
    public boolean estaBloqueada() {
        return intentosFallidos >= MAX_INTENTOS;
    }

    /**
     * Cambia la clave actual por una nueva.
     */
    public boolean cambiarClave(String claveActual, String nuevaClave) {
        if (!validarClave(claveActual)) {
            System.out.println("  ❌ No se puede cambiar la clave: clave actual incorrecta.");
            return false;
        }
        if (nuevaClave == null || nuevaClave.length() != 4 || !nuevaClave.matches("\\d{4}")) {
            System.out.println("  ❌ La nueva clave debe ser de 4 dígitos numéricos.");
            return false;
        }
        if (nuevaClave.equals(this.clave)) {
            System.out.println("  ❌ La nueva clave no puede ser igual a la actual.");
            return false;
        }
        this.clave = nuevaClave;
        System.out.println("  ✅ Clave actualizada exitosamente.");
        return true;
    }

    public void resetearIntentos() {
        this.intentosFallidos = 0;
    }
}

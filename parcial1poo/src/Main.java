import usr.Cajero;
import usr.Cliente;
import usr.Cuenta;
import operaciones.Comprobante;

import java.util.Scanner;

/**
 * Punto de entrada único del sistema de cajero automático UniBankSalud.
 * Contiene el menú principal y el flujo completo de interacción.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String CANCELAR = "0";

    public static void main(String[] args) {

        // ── Inicialización del sistema ──────────────────────
        Cuenta cuentaCliente = new Cuenta("4521-7890-1234", Cuenta.TipoCuenta.AHORROS);
        Cliente cliente = new Cliente("C001", "María García", "1023456789", cuentaCliente, "1234");
        Cajero cajero  = new Cajero("CAJ-001", "Sucursal Centro - Bogotá");

        mostrarBienvenida();

        // ── Autenticación ───────────────────────────────────
        if (!autenticar(cliente)) {
            System.out.println("\n  🔒 Sistema bloqueado. Contacte al banco.\n");
            return;
        }

        System.out.printf("%n  ✅ Bienvenido/a, %s%n", cliente.getNombre());

        // ── Menú principal ──────────────────────────────────
        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal(cajero);
            String opcion = leerEntrada("  👉 Seleccione una opción: ");

            switch (opcion) {
                case "1" -> procesarRetiro(cliente, cajero);
                case "2" -> procesarConsulta(cliente, cajero);
                case "3" -> procesarCambioClave(cliente);
                case "4" -> {
                    System.out.println("\n  👋 Gracias por usar UniBankSalud. ¡Hasta pronto!\n");
                    continuar = false;
                }
                case CANCELAR -> System.out.println("  ↩️  Regresando al menú principal...");
                default  -> System.out.println("  ⚠️  Opción no válida. Intente de nuevo.");
            }
        }
    }

    // ── Flujos de operación ─────────────────────────────────

    private static void procesarRetiro(Cliente cliente, Cajero cajero) {
        System.out.println("\n  ╔═══════════════════════════╗");
        System.out.println("  ║       💵  RETIRO           ║");
        System.out.println("  ╚═══════════════════════════╝");
        System.out.printf("  Retiros realizados hoy: %d/%d%n",
                cajero.getRetirosHoy(), Cajero.MAX_RETIROS);
        System.out.printf("  Monto permitido: $%,.0f - $%,.0f%n",
                Cajero.RETIRO_MINIMO, Cajero.RETIRO_MAXIMO);
        System.out.println("  (Ingrese " + CANCELAR + " para cancelar)\n");

        String entrada = leerEntrada("  Ingrese el monto a retirar: $");

        if (entrada.equals(CANCELAR)) {
            System.out.println("  ❌ Operación cancelada por el usuario.");
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(entrada.replace(",", "").replace(".", "").trim());
        } catch (NumberFormatException e) {
            System.out.println("  ❌ Monto no válido. Ingrese solo números.");
            return;
        }

        // Confirmar operación
        System.out.printf("%n  ¿Confirma el retiro de $%,.0f? (1=Sí / 0=Cancelar): ", monto);
        String confirmacion = scanner.nextLine().trim();
        if (!confirmacion.equals("1")) {
            System.out.println("  ❌ Operación cancelada por el usuario.");
            return;
        }

        Comprobante comprobante = cajero.procesarRetiro(monto, cliente.getCuenta());
        if (comprobante != null) {
            System.out.println("  ✅ Retiro realizado exitosamente.");
            comprobante.imprimir();
        }
    }

    private static void procesarConsulta(Cliente cliente, Cajero cajero) {
        System.out.println("\n  ╔═══════════════════════════╗");
        System.out.println("  ║    📊 CONSULTA DE SALDO    ║");
        System.out.println("  ╚═══════════════════════════╝");
        Comprobante comprobante = cajero.procesarConsulta(cliente.getCuenta());
        System.out.print("  ¿Desea imprimir comprobante? (1=Sí / 0=No): ");
        String resp = scanner.nextLine().trim();
        if (resp.equals("1")) comprobante.imprimir();
    }

    private static void procesarCambioClave(Cliente cliente) {
        System.out.println("\n  ╔═══════════════════════════╗");
        System.out.println("  ║    🔑 CAMBIO DE CLAVE      ║");
        System.out.println("  ╚═══════════════════════════╝");
        System.out.println("  (Ingrese " + CANCELAR + " en cualquier momento para cancelar)\n");

        String claveActual = leerEntrada("  Ingrese su clave actual   : ");
        if (claveActual.equals(CANCELAR)) { System.out.println("  ❌ Operación cancelada."); return; }

        String nuevaClave = leerEntrada("  Ingrese la nueva clave    : ");
        if (nuevaClave.equals(CANCELAR)) { System.out.println("  ❌ Operación cancelada."); return; }

        String confirmarClave = leerEntrada("  Confirme la nueva clave   : ");
        if (confirmarClave.equals(CANCELAR)) { System.out.println("  ❌ Operación cancelada."); return; }

        if (!nuevaClave.equals(confirmarClave)) {
            System.out.println("  ❌ Las claves nuevas no coinciden.");
            return;
        }
        cliente.cambiarClave(claveActual, nuevaClave);
    }

    // ── Autenticación ────────────────────────────────────────

    private static boolean autenticar(Cliente cliente) {
        System.out.println("\n  🔐 Por favor, autentíquese para continuar.");
        int intentos = 3;
        while (intentos > 0) {
            if (cliente.cuentaBloqueada()) return false;
            String clave = leerEntrada("  Ingrese su clave (4 dígitos): ");
            if (cliente.validarClave(clave)) return true;
            intentos--;
        }
        return false;
    }

    // ── Utilidades de UI ─────────────────────────────────────

    private static void mostrarBienvenida() {
        System.out.println("\n  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║    🏦  CAJERO AUTOMÁTICO - UNIBANKSA LUD     ║");
        System.out.println("  ║         Sistema de Autoservicio v1.0          ║");
        System.out.println("  ╚══════════════════════════════════════════════╝\n");
    }

    private static void mostrarMenuPrincipal(Cajero cajero) {
        System.out.println("\n  ┌───────────────────────────────┐");
        System.out.println("  │        MENÚ PRINCIPAL         │");
        System.out.println("  ├───────────────────────────────┤");
        System.out.println("  │  1. 💵 Retiro de dinero        │");
        System.out.println("  │  2. 📊 Consulta de saldo       │");
        System.out.println("  │  3. 🔑 Cambiar clave           │");
        System.out.println("  │  4. 🚪 Salir                   │");
        System.out.println("  │  0. ↩️  Cancelar / Regresar    │");
        System.out.println("  └───────────────────────────────┘");
        System.out.printf("  Retiros disponibles hoy: %d%n", cajero.getRetirosRestantes());
    }

    private static String leerEntrada(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
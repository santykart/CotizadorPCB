import java.util.Scanner;

public class CotizadorPCB {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        final double PRECIO_BASE_CM2 = 0.06;
        final double EXTRA_ENIG = 0.02;
        final double PRECIO_SMT = 0.05;
        final double PRECIO_THT = 0.09;
        final double PRECIO_MIXTO = 0.07;

        System.out.println("=========================================");
        System.out.println("  SISTEMA DE COTIZACIÓN DE PCB");
        System.out.println("=========================================\n");

        System.out.print("Nombre del Cliente: ");
        String cliente = scanner.nextLine();

        System.out.print("ID de Cotización: ");
        String idCotizacion = scanner.nextLine();

        System.out.print("Cantidad de tarjetas a fabricar: ");
        int cantidad = scanner.nextInt();

        System.out.print("Largo de la tarjeta (cm): ");
        double largo = scanner.nextDouble();

        System.out.print("Ancho de la tarjeta (cm): ");
        double ancho = scanner.nextDouble();

        System.out.print("Número de capas (2, 4 o 6): ");
        int capas = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Tipo de acabado (HASL / ENIG): ");
        String acabado = scanner.nextLine();

        System.out.print("Tipo de ensamble (SMT / THT / MIXTO): ");
        String tipoEnsamble = scanner.nextLine();

        System.out.print("Número estimado de componentes por tarjeta: ");
        int numComponentes = scanner.nextInt();

        double area = largo * ancho;

        double multiplicadorCapas = 1.0;
        if (capas == 4) {
            multiplicadorCapas = 1.40;
        } else if (capas == 6) {
            multiplicadorCapas = 1.80;
        }

        double costoCm2 = PRECIO_BASE_CM2 * multiplicadorCapas;

        if (acabado.equalsIgnoreCase("ENIG")) {
            costoCm2 += EXTRA_ENIG;
        }

        double subtotalFab = (area * costoCm2) * cantidad;

        double costoComp;
        switch (tipoEnsamble.toUpperCase()) {
            case "SMT":
                costoComp = PRECIO_SMT;
                break;
            case "THT":
                costoComp = PRECIO_THT;
                break;
            default:
                costoComp = PRECIO_MIXTO;
                break;
        }

        double subtotalEnsamble = (numComponentes * costoComp) * cantidad;
        double totalEstimado = subtotalFab + subtotalEnsamble;

        String dictamen;
        String observaciones;

        if (area > 500) {
            dictamen = "NO FACTIBLE";
            observaciones = "El área excede el límite máximo permitido (500 cm2).";
        } else if (cantidad < 10) {
            dictamen = "FACTIBLE CON REVISION";
            observaciones = "Lote menor al mínimo. Se aplicó recargo de $50 USD.";
            totalEstimado += 50.00;
        } else {
            dictamen = "FACTIBLE";
            observaciones = "Parámetros dentro del estándar de manufactura.";
        }

        System.out.println("\n======================================================");
        System.out.println("              RESUMEN EJECUTIVO DE COTIZACIÓN         ");
        System.out.println("======================================================");
        System.out.printf("ID Cotización:    %s%n", idCotizacion);
        System.out.printf("Cliente:          %s%n", cliente);
        System.out.printf("Especificaciones: %.2f cm2 | %d Capas | %s%n", area, capas, acabado.toUpperCase());
        System.out.printf("Ensamble:         %s | %d Componentes/tarjeta%n", tipoEnsamble.toUpperCase(), numComponentes);
        System.out.println("------------------------------------------------------");
        System.out.printf("Subtotal Fabricación PCB:  $%10.2f USD%n", subtotalFab);
        System.out.printf("Subtotal Ensamble:         $%10.2f USD%n", subtotalEnsamble);

        if (cantidad < 10 && area <= 500) {
            System.out.printf("Recargo Lote Pequeño:      $%10.2f USD%n", 50.00);
        }

        System.out.println("------------------------------------------------------");
        System.out.printf("TOTAL ESTIMADO:            $%10.2f USD%n", totalEstimado);
        System.out.println("======================================================");
        System.out.println("DICTAMEN:      " + dictamen);
        System.out.println("OBSERVACIONES: " + observaciones);
        System.out.println("======================================================");

        scanner.close();
    }
}

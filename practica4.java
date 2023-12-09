import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class practica4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Variables de los valores de α, β, I y h
        double alpha;
        double beta;
        double I;
        double h;

         //Ingreso de datos del usuario
         System.out.println("Ingrese el valor de a:");
         alpha = sc.nextFloat();
         System.out.println("Ingrese el valor de B:");
         beta = sc.nextFloat();
         System.out.println("Ingrese el valor de I:");
         I = sc.nextFloat();
         System.out.println("Ingrese el valor de h:");
         h = sc.nextFloat();

        // Valor inicial de L(0)
        double L0 = 3.0;

        // Rango de tiempo de 0 a 3
        double tiempoMax = 3.0;

        // Aqui sacamos el numero de iteraciones necesarias que se puedes ocupar para el metodo de adam-Bashford
        int iteraciones = (int) (tiempoMax / h);

        // Lista para almacenar los valores de t y L
        List<double[]> resultado = new ArrayList<>();

        // Inicialización de t y L con RK-4
        double t = 0.0;
        double L = L0;

        // Aqui con RK-4 se obtienen los primeros cuatro valores de L
        for (int i = 0; i < 4; i++) {
            double[] rk4Resultado = rungeKutta4(t, L, alpha, beta, I, h);
            t = rk4Resultado[0];
            L = rk4Resultado[1];
            resultado.add(new double[]{t, L});
        }

        // Imprimir la tabla de valores
        System.out.println("t\tL");
        for (double[] entry : resultado) {
            System.out.println(entry[0] + "\t" + entry[1]);
        }
    }

    // Método de Runge-Kutta de cuarto orden
    private static double[] rungeKutta4(double t, double L, double alpha, double beta, double I, double h) {
        double k1 = h * (alpha * I - beta * L);
        double k2 = h * (alpha * I - beta * (L + k1 / 2.0));
        double k3 = h * (alpha * I - beta * (L + k2 / 2.0));
        double k4 = h * (alpha * I - beta * (L + k3));

        double tsiguiente = t + h;
        double Lsiguiente = L + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
        
        return new double[]{tsiguiente, Lsiguiente};
    }
}

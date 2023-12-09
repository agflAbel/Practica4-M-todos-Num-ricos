import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class practica4 {

    public static void main(String[] args) {
        Solucion sol = new Solucion();

    }
}
class Solucion{
    // Variables de los valores de α, β, I y h
    double alpha;
    double beta;
    double I;
    double h;
    int it = 3;
    // Lista para almacenar los valores de t y L
    List<double[]> resultado = new ArrayList<>();
    public Solucion() {
        Scanner sc = new Scanner(System.in);
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


        // Inicialización de t y L con RK-4
        double t = 0.0;
        double L = L0;

        // Aqui con RK-4 se obtienen los primeros cuatro valores de L
        for (int i = 0; i < iteraciones+2; i++) {
            if(i==0) {
                resultado.add(new double[]{t,L});
            }else if(i<4) {
                double[] rk4Resultado = rungeKutta4(t, L, alpha, beta, I, h);
                t = rk4Resultado[0];
                L = rk4Resultado[1];
                resultado.add(new double[]{t, L});
            }else{
                double[] AB3Resultado = adamsBashforth(t);
                t = AB3Resultado[0];
                L = AB3Resultado[1];
                resultado.add(new double[]{t, L});
            }
        }

        // Imprimir la tabla de valores
        System.out.println("t\t\tL");
        for (double[] entry : resultado) {
            System.out.println(entry[0] + "\t\t" + entry[1]);
        }
    }
    //MEtodo de Adams-Bashfoth de cuarto orden
    private double[] adamsBashforth(double t) {
        double tSiguiente = t + h;
        double[] imas3 = resultado.get(it);        // System.out.println("imas3 : "+imas3[0]+","+imas3[1]+"\t f(imas3) : "+f(imas3[1])+"\t resultado.get("+it+") : "+resultado.get(it).clone()[1]);
        double[] imas2 = resultado.get(it-1);       //System.out.println("imas2 : "+imas2[1]+"\t f(imas2) : "+f(imas2[1])+"\t resultado.get("+it+") : "+resultado.get(it));
        double[] imas1 = resultado.get(it-2);       //System.out.println("imas1 : "+imas1[1]+"\t f(imas1) : "+f(imas1[1])+"\t resultado.get("+it+") : "+resultado.get(it));
        double[] i = resultado.get(it-3);           //System.out.println("i : "+i[1]+"\t f(i) : "+f(i[1])+"\t resultado.get("+it+") : "+resultado.get(it));

        //Segun yo, aqui esta el error, al calcular lSiguiente, pero asi es la formula
        double lSiguiente = imas3[1] + h * (55/24*f(imas3[1])-59/24*f(imas2[1])+37/24*f(imas1[1])-3/8*f(i[1]));
        it++;
        return new double[]{tSiguiente,lSiguiente};
    }
    private double f(double L){
        double res = alpha * I - beta * L;
        return res;
    }

    // Método de Runge-Kutta de cuarto orden
    private double[] rungeKutta4(double t, double L, double alpha, double beta, double I, double h) {
        double k1 = (alpha * I - beta * L);
        double k2 = (alpha * I - beta * (L + k1 / 2.0*h));
        double k3 = (alpha * I - beta * (L + k2 / 2.0*h));
        double k4 = (alpha * I - beta * (L + k3*h));

        double tsiguiente = t + h;
        double Lsiguiente = L + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0*h;

        return new double[]{tsiguiente, Lsiguiente};
    }
}

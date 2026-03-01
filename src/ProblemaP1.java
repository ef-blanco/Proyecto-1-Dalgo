import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProblemaP1 
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

        int numeroCasos = Integer.parseInt(lector.readLine().trim());

        for (int c = 0; c < numeroCasos; c++)
        {
            String texto = lector.readLine();
            int[] resultado = resolverCaso(texto);
            System.out.println(resultado[0] + " " + resultado[1]);
        }
    }

    /**
     * TODO: Implementar la función que itere con los anchos y llame a la función
     * de programación dinámica en EncuentraRios para encontrar el rio más largo
     * con cada ancho
     */
    
     public static int[] resolverCaso(String texto)
    {
        String[] palabras = texto.split(" ");
        // Sabemos que el ancho mínimo es el de la palabra más larga
        int anchoMinimo = 0;
        for (String palabra : palabras)
            if (palabra.length() > anchoMinimo) anchoMinimo = palabra.length();
        // El ancho máximo que se puede tener es meter todo en una sola línea
        int anchoMaximo = texto.length();
        int mejorRio   = 0;
        int mejorAncho = anchoMinimo;
        // Probamos cada ancho desde el mínimo hasta el máximo (W, W+1, w+2 y asi)
        for (int ancho = anchoMinimo; ancho <= anchoMaximo; ancho++){
            // Parte 1 seria ajustar el texto al ancho actual
            List<String> lineas = ajustarTexto(palabras, ancho);
            // Tendria una terminacion temprana si ya hay menos líneas que el mejor río encontrado, con anchos mayores habria entonces aún menos líneas, lo cual seria imposible mejorar.
            if (lineas.size() <= Math.max(1, mejorRio))
                break;
            // Como sehunda parte convertiriamos las líneas a una matriz de 0s y 1s
            int[][] matrizCeros1s = convertirAMatriz(lineas, ancho);
            // Parte 3, ahora encontrariamos el río más largo con programación dinámica
            int rioActual = rioMasLargo(matrizCeros1s);
            // Guardamos en este caso solo si mejora (así conservamos el ancho MÁS PEQUEÑO)
            if (rioActual > mejorRio)
            {
                mejorRio   = rioActual; mejorAncho = ancho;}
        }

        return new int[]{mejorAncho, mejorRio};
    }

    //TODO: Implementar método que ajusta el texto a un ancho
    //Ajuste del texto con Word Wrap greedy pues distribuimos las palabras en lineas sin superar el ancho que se dio
    public static List<String> ajustarTexto(String[] palabras, int ancho)
    {
        List<String> lineas = new ArrayList<>();
        StringBuilder lineaActual = new StringBuilder();

        for (String palabra : palabras)
        {
            if (lineaActual.length() == 0)
            {
                // Primera palabra de la línea siempre va a entra (garantizado por anchoMinimo)
                lineaActual.append(palabra);
            }
            else if (lineaActual.length() + 1 + palabra.length() <= ancho)
            {
                // La palabra cabe en la línea actual (con el espacio separador)
                lineaActual.append(" ").append(palabra);
            }
            else
            {
                // Si de lo contrario no cabe, guardamos la línea actual y empezamos una nueva
                lineas.add(lineaActual.toString());
                lineaActual = new StringBuilder(palabra);
            }
        }

        // Y claro, no olvidamos la última línea
        if (lineaActual.length() > 0)
            lineas.add(lineaActual.toString());

        return lineas;
    }

    //TODO: Implementar método que cambie el formato del texto: Ej. Hello world -> 0000100000
    public static int[][] convertirAMatriz(List<String> lineas, int ancho)
    {
        int numeroLineas = lineas.size();
        int[][] matriz = new int[numeroLineas][ancho];

        for (int fila = 0; fila < numeroLineas; fila++)
        {
            String linea = lineas.get(fila);
            for (int col = 0; col < linea.length(); col++)
            {
                if (linea.charAt(col) == ' ')
                    matriz[fila][col] = 1;  // espacio = 1
                else
                    matriz[fila][col] = 0;  // letra   = 0
            }
            // EJEMPLO: "Holis" =[0,0,0,0,0]
            //EJEMPLO2: "Holis profe" = [0,0,0,0,0,1,0,0,0,0,0]
            // Las columnas después del fin de la línea entonces quedan en 0 (relleno) pues no pertenecen a un rio
        }

        return matriz;
    }


    //Función de programación dinámica que encuentra los rios más largos

    public int rioMasLargo(int[][] cadena)
    {
        """int numFilas  = cadena.length;
        int numCols   = cadena[0].length;
        int rioMaximo = 0;

        int[][] dp = new int[numFilas][numCols];
        for (int col = 0; col < numCols - 1; col++)
        {
            if (cadena[0][col] == 1)
            {
                dp[0][col] = 1;
                rioMaximo  = 1;
            }
        }"""
        int resp = 0;
        int[][] matrizRios = new int[cadena.length][cadena[0].length];

        return resp;

    }

}

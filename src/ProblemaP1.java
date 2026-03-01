import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemaP1 
{
    public static void main(String[] args) throws Exception
    {
        try(FileReader freader = new FileReader(args[0]);
            BufferedReader bf = new BufferedReader(freader))
            {
                int numCasos = Integer.parseInt(bf.readLine());

                List<String> entradas = new ArrayList<String>();

                for(int i = 0; i<numCasos;i++)
                {
                    String texto = bf.readLine();
                    entradas.add(texto);
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
                for(String entrada:entradas)
                {
                    int[] respuesta = resolverCaso(entrada);
                    writer.write(((Integer)respuesta[0]).toString() + " " + ((Integer)respuesta[1]).toString());
                    writer.newLine();
                }
                writer.close();
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

    static public int rioMasLargo(int[][] cadena)
    {
        
        int resp = 0;
        int a = cadena[0].length;
        int n = cadena.length;
        int[][] m = new int[n+1][a];

        for(int[] fila:m)
        {
            Arrays.fill(fila,0);
        }

        int i = 0;
        int j = 0;
        while(i<=n)
        {
            //CASO 1
            if((i==0)||(j==0)||(j==a-1))
            {
                m[i][j] = 0;
            }
            //CASO 2
            else if((i==1)&&((0<j)&&(j<a-1)))
            {
                m[i][j] = cadena[n-1][j]*(1-Math.max(cadena[n-1][j-1],cadena[n-1][j+1]));
            }
            //CASO 3
            else if((i>1)&&((0<j)&&(j<a-1)))
            {
                m[i][j] = cadena[n-i][j]*(1-Math.max(cadena[n-i][j-1],cadena[n-i][j+1]))*(1+(Math.max(Math.max(m[i-1][j],m[i-1][j-1]),Math.max(m[i-1][j-1],m[i-1][j+1]))));
            }

            //Vamos verificando si alcanzamos el rio de longitud max
            if(m[i][j]>=resp)
            {
                resp = m[i][j];
            }

            //Esta parte es para iterar con j sin la necesidad de otro ciclo (No resta complejidad)
            if(j<a-1)
            {
                j++;
            }
            else if(j==a-1)
            {
                j = 0;
                i++;
            }

        }

        return resp;

    }

}

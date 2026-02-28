import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
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

                System.out.println(entradas);

                //Aquí luego se debe correr todo con cada entrada
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


    //TODO: Implementar método que cambie el formato del texto: Ej. Hello world -> 0000100000


    //Función de programación dinámica que encuentra los rios más largos

    public int rioMasLargo(int[][] cadena)
    {
        int resp = 0;
        int[][] matrizRios = new int[cadena.length][cadena[0].length];

        return resp;

    }

}

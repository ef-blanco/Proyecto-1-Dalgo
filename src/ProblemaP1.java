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

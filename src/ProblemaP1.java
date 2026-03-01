import java.io.BufferedReader;
import java.io.FileReader;
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
        while(i<cadena.length)
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

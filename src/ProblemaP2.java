import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author Emmanuel Blanco Corredor - 202312743
 * @author Juan David Guzmán - 202320890
 */

public class ProblemaP2 
{
    // Ejemplo de como debe ser el main
    //TODO: Juan David encárgate de esta parte para que no la vuelva a embarrar 😔 
    public static void main(String[] args) throws Exception {
		ProblemaP2 instancia = new ProblemaP2();
		try ( 
			InputStreamReader is= new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(is);
		) { 
			String line = br.readLine();
			int casos = Integer.parseInt(line);
			line = br.readLine();
			for(int i=0;i<casos && line!=null && line.length()>0 && !"0".equals(line);i++) {
				final String [] dataStr = line.split(" ");
				final int[] numeros = Arrays.stream(dataStr).mapToInt(f->Integer.parseInt(f)).toArray();
				int [] respuestas = instancia.procesarNumeros(numeros);
				System.out.println(respuestas[0]+" "+respuestas[1]);
				line = br.readLine();
			}
		}
	}

    //Función de ecuación de recurrencia
}

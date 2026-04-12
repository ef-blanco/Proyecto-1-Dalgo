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
			if (line == null) return;
			// Se lee la cantidad de casos
			int casos = Integer.parseInt(line.trim());
			
			for(int i=0; i<casos; i++) {
				line = br.readLine();
				while(line != null && line.trim().isEmpty()) {
					line = br.readLine();
				}
				if (line == null) break;
				
				// N = número de sumandos (grafos en este caso)
				int N = Integer.parseInt(line.trim());
				int[] diffs = new int[N];
				
				// Leer cada uno de los N sumandos
				for(int k=0; k<N; k++) {
					line = br.readLine();
					while(line != null && line.trim().isEmpty()) {
						line = br.readLine();
					}
					if (line == null) break;
					
					// Equivalente seguro al .split(" ") pero limpiando espacios dobles y de los extremos
					String[] dataStr = line.trim().split("\\s+");
					int[] numeros = new int[dataStr.length];
					for (int j = 0; j < dataStr.length; j++) {
					    numeros[j] = Integer.parseInt(dataStr[j]);
					}
					
					// Procesa el grafo individual para obtener el diferencial |c0-c1|
					diffs[k] = instancia.bipartitionDifference(numeros);
				}
				
				// Calcula la suma mas eficiente de la bipartita general
				int respuesta = instancia.minimumDifferential(diffs);
				System.out.println(respuesta);
			}
		}
	}

	

    //Función de ecuación de recurrencia
}

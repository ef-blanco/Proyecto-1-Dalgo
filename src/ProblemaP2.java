import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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



    //Función para calcular el diferencial de tamaño de la bipartición de un componente conexo con param data que es lit una lista plana con un retorno del diferencial del componente de forma absoluta
	public int bipartitionDifference(int[] data) {
		int v = data[0];
		int e = data[1];
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] adj = new ArrayList[v];
		for(int i=0; i<v; i++) {
			adj[i] = new ArrayList<>();
		}
		
		int idx = 2;
		for(int i=0; i<e; i++) {
			int x = data[idx++];
			int y = data[idx++];
			adj[x].add(y);
			adj[y].add(x);
		}
		
		int[] color = new int[v];
		for (int i = 0; i < v; i++) {
			color[i] = -1;
		}

		int c0 = 0;
		int c1 = 0;
		Queue<Integer> q = new LinkedList<>();

		// Ahora aunquw el grafo original de prueba indica ser conexo, este bucle atiende pues componentes
		for (int start = 0; start < v; start++) {
			if (color[start] != -1) continue;
			color[start] = 0;
			c0++;
			q.add(start);

			while (!q.isEmpty()) {
				int u = q.poll();
				int nxtColor = 1 - color[u];

				for (int w : adj[u]) {
					if (color[w] == -1) {
						color[w] = nxtColor;
						if (nxtColor == 0) {
							c0++;
						} else {
							c1++;
						}
						q.add(w);
					}
				}
			}
        }
        return Math.abs(c0 - c1);
	}
}

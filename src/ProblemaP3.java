import java.util.*;
import java.io.*;

/**
 * @author Emmanuel Blanco Corredor - 202312743
 * @author Juan David Guzmán - 202320890
 */


public class ProblemaP3 {
    /** Numero de cadenas.*/
    static int n;

    /** Cadenas de entrada que se encuentran ordenadas por longitud ascendente. */
    static String[] strs;

    /** En este caso son las longitudes de cada cadena. */
    static int[] lens;

    /**
     * Tabla del automata de subsecuencias
     * next[i][pos][c] = indice j más pequenio tal que j >= pos y strs[i].charAt(j) == c, o que lens[i] si c no aparece en strs[i] en ninguna posicion donde  >= pos
     * Permite responder en O(1): "¿desde donde avanza la cadena i si elijo el caracter c?"
     * En este caso recorrer cada cadena de derecha a izquierda: O(n · m · 256).
     */
    static int[][][] next;

    /**
     * Filtro global de caracteres
     * validChars[c] = true  <->  c aparece en TODAS las cadenas al menos una vez
     *
     * TOCA PODA GLOBAL DONDE un caracter ausente en cualquier cadena jamas puede ser parte del LCS, sin importar el estado actual. Aqui lit eliminamos de una ramas completas
     */
    static boolean[] validChars;

    /**
     * Tabla de memoizacion donde se saca la codificacion del estado donde longitud max del LCS alcanzable desde ese estado String de n chars donde key[i] = (char) state[i].
     * Cada estado se calcula una unica vez, como resultado de complejidad total lineal en estados visitados.
     */
    static HashMap<String, Integer> memo;



    public static void main(String[] args) throws IOException {
        // BufferedReader para E/S rapida
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        while (T-- > 0) {
            // Leer n y m m es el max de longitud pero usamos lens[i] reales
            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            st.nextToken(); // m consumir pero no usar directamente

            strs = new String[n];
            for (int i = 0; i < n; i++) {
                strs[i] = br.readLine().trim();
            }
            // Optimizamos ordenando por longitud ascendente. strs[0] = cadena mas corta = "cadena guia"
            // La longitud del LCS ≤ lens[0], solo probamos caracteres que son alcanzables en strs[0] desde su posicion actual -> poda del espacio de busqueda
            Arrays.sort(strs, Comparator.comparingInt(String::length));
            lens = new int[n];
            for (int i = 0; i < n; i++) lens[i] = strs[i].length();
            // Construir tablas del automata de subsecuencias
            buildNextTables();
            // Calcular conjunto global de caracteres validos
            computeValidChars();
            // Inicializar memo con capacidad suficiente para reducir rehashing
            memo = new HashMap<>(1 << 16);
            int[] initState = new int[n];
            int lcsLen = dfsLen(initState);
            String lcs = reconstruct(initState, lcsLen);
            out.append(lcs).append("\n");
        }

        System.out.print(out);
    }
    /**
     * Construye next[i][pos][c] para todas las cadenas
     * Algoritmo (por cada cadena i de longitud L):
     *   next[i][L][c] = L para todo c  (centinela: nada alcanzable tras el final)
     *   Para pos = L-1 downto 0:
     *       Copiamos next[i][pos+1] en next[i][pos]  (heredar punteros hacia la derecha)
     *       next[i][pos][strs[i].charAt(pos)] = pos  (sobreescribimos)
     */
    static void buildNextTables() {
        next = new int[n][][];
        for (int i = 0; i < n; i++) {
            int L = lens[i];
            next[i] = new int[L + 1][256];
            Arrays.fill(next[i][L], L); // centinela if fuera del rango
            for (int pos = L - 1; pos >= 0; pos--) {
                // Heredar proxima aparicion del siguiente paso
                System.arraycopy(next[i][pos + 1], 0, next[i][pos], 0, 256);
                next[i][pos][strs[i].charAt(pos)] = pos;
            }
        }
    }

    /**
     * Determinamos ahora que caracteres aparecen en TODAS las cadenas (interseccion tipo global).
     * validChars[c] = (next[i][0][c] < lens[i] para todo i)
     */
    static void computeValidChars() {
        validChars = new boolean[256];
        outer:
        for (int c = 0; c < 256; c++) {
            for (int i = 0; i < n; i++) {
                if (next[i][0][c] >= lens[i]) continue outer; // falta en cadena i
            }
            validChars[c] = true; // aparece en todas
        }
    }

    /**
     * Codificamos el estado (array de n posiciones) como una String compacta
     * Cada posición state[i] cabe en un char Java
     * La clave resultante tiene longitud n y es única para cada estado.
     */
    static String encodeState(int[] state) {
        char[] key = new char[n];
        for (int i = 0; i < n; i++) key[i] = (char) state[i];
        return new String(key);
    }

    static int dfsLen(int[] state) {
        //Capa 1: consultamos al memo
        String key = encodeState(state);
        Integer cached = memo.get(key);
        if (cached != null) return cached;

        int best = 0;

        // Reutilizamos este arreglo para cada caracter candidato evitamos allocations
        int[] newState = new int[n];

        for (int c = 0; c < 256; c++) {
            //Capa 2: poda global
            if (!validChars[c]) continue;

            //Capa 3: poda por cadena guia (mas corta)
            //Si c no aparece en strs[0] desde state[0] no puede extender el LCS
            if (next[0][state[0]][c] >= lens[0]) continue;

            //Capa 4: verificando alcanzabilidad en todas las cadenas
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                int np = next[i][state[i]][c];
                if (np >= lens[i]) {
                    ok = false;
                    break;
                }
                newState[i] = np + 1; //avanzamos la o past el caracter coincidente
            }

            if (!ok) continue;

            //Recurrencia sobre el nuevo estado y actualizar el mejor resultado
            int sub = dfsLen(newState);
            if (1 + sub > best) best = 1 + sub;
        }

        memo.put(key, best);
        return best;
    }

    // ─── Reconstrucción del LCS (Fase 2) ─────────────────────────────────────

    /**
     * Reconstruye la cadena LCS de longitud targetLen desde el estado dado.
     *
     * Usa los mismos filtros que dfsLen, pero en lugar de calcular el óptimo,
     * lo verifica contra el memo ya poblado (O(1) por llamada a dfsLen).
     */
    static String reconstruct(int[] state, int targetLen) {
        if (targetLen == 0) return "";

        int[] newState = new int[n];

        for (int c = 0; c < 256; c++) {
            if (!validChars[c]) continue;
            if (next[0][state[0]][c] >= lens[0]) continue;

            boolean ok = true;
            for (int i = 0; i < n; i++) {
                int np = next[i][state[i]][c];
                if (np >= lens[i]) { ok = false; break; }
                newState[i] = np + 1;
            }

            if (!ok) continue;

            // Verificar si elegir c conduce a una subsecuencia óptima de largo targetLen-1
            // dfsLen ya está en memo → O(1)
            if (dfsLen(newState) == targetLen - 1) {
                // Lo encontramos entonces agregar c y continuar la reconstrucción
                return (char) c + reconstruct(newState.clone(), targetLen - 1);
            }
        }

        return ""; //Es inalcanzable si targetLen > 0 y el algoritmo es correcto
    }
}

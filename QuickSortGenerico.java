import java.util.Comparator;

public class QuickSortGenerico {
    
    // =========================================================
    // 1. Usando Comparable (Ordem Padrão - ex: por ID)
    // =========================================================
    public static <T extends Comparable<T>> void ordenar(T[] array) {
        ordenar(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void ordenar(T[] array, int esq, int dir) {
        if (esq < dir) {
            int pivoIndex = particionar(array, esq, dir);
            ordenar(array, esq, pivoIndex - 1);
            ordenar(array, pivoIndex + 1, dir);
        }
    }

    private static <T extends Comparable<T>> int particionar(T[] array, int esq, int dir) {
        T pivo = array[dir];
        int i = esq - 1;
        for (int j = esq; j < dir; j++) {
            // Usa compareTo no lugar de <=
            if (array[j].compareTo(pivo) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, dir);
        return i + 1;
    }

    // =========================================================
    // 2. Usando Comparator (Ordem Dinâmica - ex: por Preço)
    // =========================================================
    public static <T> void ordenar(T[] array, Comparator<T> comparador) {
        ordenar(array, 0, array.length - 1, comparador);
    }

    private static <T> void ordenar(T[] array, int esq, int dir, Comparator<T> comparador) {
        if (esq < dir) {
            int pivoIndex = particionar(array, esq, dir, comparador);
            ordenar(array, esq, pivoIndex - 1, comparador);
            ordenar(array, pivoIndex + 1, dir, comparador);
        }
    }

    private static <T> int particionar(T[] array, int esq, int dir, Comparator<T> comparador) {
        T pivo = array[dir];
        int i = esq - 1;
        for (int j = esq; j < dir; j++) {
            // Usa o comparador passado via Lambda
            if (comparador.compare(array[j], pivo) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, dir);
        return i + 1;
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

/*QuickSortGenerico.ordenar(produtosCadastrados, (p1, p2) -> Double.compare(p1.valorDeVenda(), p2.valorDeVenda()));*/

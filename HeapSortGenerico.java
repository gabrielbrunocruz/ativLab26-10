public class HeapSortGenerico {

    public static <T extends Comparable<T>> void ordenar(T[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            T temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }
    }

    private static <T extends Comparable<T>> void heapify(T[] array, int n, int i) {
        int maior = i; 
        int esq = 2 * i + 1; 
        int dir = 2 * i + 2; 

        // array[esq].compareTo(array[maior]) > 0 substitui o operador '>'
        if (esq < n && array[esq].compareTo(array[maior]) > 0) {
            maior = esq;
        }

        if (dir < n && array[dir].compareTo(array[maior]) > 0) {
            maior = dir;
        }

        if (maior != i) {
            T swap = array[i];
            array[i] = array[maior];
            array[maior] = swap;

            heapify(array, n, maior);
        }
    }
}

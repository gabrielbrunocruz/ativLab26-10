public class QuickSort {
    
    public static void ordenar(int[] array) {
        ordenar(array, 0, array.length - 1);
    }

    private static void ordenar(int[] array, int esq, int dir) {
        if (esq < dir) {
            int pivo = particionar(array, esq, dir);
            ordenar(array, esq, pivo - 1);
            ordenar(array, pivo + 1, dir);
        }
    }

    private static int particionar(int[] array, int esq, int dir) {
        int pivo = array[dir]; // Escolhe o último elemento como pivô
        int i = esq - 1;

        for (int j = esq; j < dir; j++) {
            if (array[j] <= pivo) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, dir);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

public class HeapSort {

    public static void ordenar(int[] array) {
        int n = array.length;

        // Constrói o Max-Heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }

        // Extrai os elementos do Heap um por um
        for (int i = n - 1; i > 0; i--) {
            // Move a raiz atual (maior) para o fim
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            // Chama o heapify na árvore reduzida
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int n, int i) {
        int maior = i; 
        int esq = 2 * i + 1; 
        int dir = 2 * i + 2; 

        if (esq < n && array[esq] > array[maior]) {
            maior = esq;
        }

        if (dir < n && array[dir] > array[maior]) {
            maior = dir;
        }

        if (maior != i) {
            int swap = array[i];
            array[i] = array[maior];
            array[maior] = swap;

            // Recursivamente aplica o heapify na subárvore afetada
            heapify(array, n, maior);
        }
    }
}

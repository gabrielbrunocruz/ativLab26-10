public class Lista<E> {
    private Celula<E> primeiro;
    private Celula<E> ultimo;

    public Lista() {
        Celula<E> sentinela = new Celula<>();
        primeiro = sentinela;
        ultimo = sentinela;
    }

    public boolean vazia() {
        return primeiro == ultimo;
    }

    public void inserirInicio(E item) {
        Celula<E> nova = new Celula<>(item);
        nova.setProximo(primeiro.getProximo());
        primeiro.setProximo(nova);
        if (primeiro == ultimo) {
            ultimo = nova;
        }
    }

    public void inserirFim(E item) {
        Celula<E> nova = new Celula<>(item);
        ultimo.setProximo(nova);
        ultimo = nova;
    }

    public E removerInicio() {
        if (vazia()) throw new RuntimeException("Erro: Lista vazia!");
        
        Celula<E> removida = primeiro.getProximo();
        E item = removida.getItem();
        primeiro.setProximo(removida.getProximo());
        
        if (primeiro.getProximo() == null) {
            ultimo = primeiro;
        }
        return item;
    }
}

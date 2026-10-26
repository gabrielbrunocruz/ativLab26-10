public class Fila<E> {
    private Celula<E> frente;
    private Celula<E> tras;

    public Fila() {
        Celula<E> sentinela = new Celula<>();
        frente = sentinela;
        tras = sentinela;
    }

    public boolean vazia() {
        return frente == tras;
    }

    public void enfileirar(E item) {
        Celula<E> nova = new Celula<>(item);
        tras.setProximo(nova);
        tras = nova;
    }

    public E desenfileirar() {
        if (vazia()) {
            throw new RuntimeException("Erro: Fila vazia!");
        }
        Celula<E> primeira = frente.getProximo();
        E item = primeira.getItem();
        
        frente.setProximo(primeira.getProximo());
        if (frente.getProximo() == null) {
            tras = frente;
        }
        return item;
    }
}

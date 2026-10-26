public class Pilha<E> {
    private Celula<E> topo;

    public Pilha() {
        topo = null;
    }

    public boolean vazia() {
        return topo == null;
    }

    public void empilhar(E item) {
        Celula<E> nova = new Celula<>(item);
        nova.setProximo(topo);
        topo = nova;
    }

    public E desempilhar() {
        if (vazia()) {
            throw new RuntimeException("Erro: Pilha vazia!");
        }
        E item = topo.getItem();
        topo = topo.getProximo();
        return item;
    }

    public E getTopo() {
        if (vazia()) {
            throw new RuntimeException("Erro: Pilha vazia!");
        }
        return topo.getItem();
    }
}

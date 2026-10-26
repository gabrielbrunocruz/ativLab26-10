/*Caso 3: Substituir o Array Estático por Lista Encadeada
Se o professor pedir para abandonar o array estático de produtos (Produto[] produtosCadastrados) e usar a sua classe Lista.*/
// 1. A declaração do vetor muda para a sua Lista dinâmica
static Lista<Produto> listaDeProdutos = new Lista<>();

// 2. O método de leitura abandona os índices [i]
static void lerProdutos(String nomeArquivoDados) {
    // ...
    int numProdutos = Integer.parseInt(arquivo.nextLine());
    for (int i = 0; i < numProdutos; i++) {
        String linha = arquivo.nextLine();
        Produto produto = Produto.criarDoTexto(linha);
        
        // Insere dinamicamente sem se preocupar com o tamanho do vetor!
        listaDeProdutos.inserirFim(produto); 
    }
    // ...
}

// 3. Método para localizar passa a usar um "Cursor" (Célula) para percorrer a lista
static Produto localizarProdutoPorId(int idBuscado) {
    Celula<Produto> atual = listaDeProdutos.getPrimeiro().getProximo(); // Pula sentinela
    
    while (atual != null) {
        if (atual.getItem().hashCode() == idBuscado) {
            return atual.getItem();
        }
        atual = atual.getProximo();
    }
    return null;
}



/*Caso 4: Usar Pilha para Histórico (Desfazer Ação)
A classe Pedido possui o método incluirProduto. Podemos adicionar uma Pilha de histórico dentro do próprio pedido para permitir que o usuário desfaça a última adição (um Ctrl+Z).

Como ficariam as adaptações em Pedido.java:*/

// 1. Cria a pilha de histórico dentro do pedido
private Pilha<ItemDePedido> historicoInclusoes = new Pilha<>();

// 2. Atualiza o método original para empilhar o que acabou de ser feito
public boolean incluirProduto(Produto novo, int quantidade) {
    ItemDePedido itemDePedido = existeNoPedido(novo);
    
    if (itemDePedido != null) {
        itemDePedido.setQuantidade(quantidade + itemDePedido.getQuantidade());
        // Empilha um registro simbolizando o que foi adicionado
        historicoInclusoes.empilhar(new ItemDePedido(novo, quantidade, novo.valorDeVenda()));
        return true;
    }
    else if (quantItensDePedido < MAX_ITENS_DE_PEDIDO) {
        ItemDePedido novoItem = new ItemDePedido(novo, quantidade, novo.valorDeVenda());
        itensDePedido[quantItensDePedido++] = novoItem;
        historicoInclusoes.empilhar(novoItem); // Guarda no histórico!
        return true;
    }
    return false;
}

// 3. Novo método: Desfazer (Ctrl+Z)
public void desfazerUltimaInclusao() {
    if (historicoInclusoes.vazia()) {
        System.out.println("Não há ações para desfazer neste pedido.");
        return;
    }
    
    // Desempilha a última ação
    ItemDePedido ultimaAcao = historicoInclusoes.desempilhar();
    ItemDePedido itemNoPedido = existeNoPedido(ultimaAcao.getProduto());
    
    if (itemNoPedido != null) {
        int novaQtd = itemNoPedido.getQuantidade() - ultimaAcao.getQuantidade();
        if (novaQtd <= 0) {
            // Lógica para remover do vetor itensDePedido (ou zerar)
            itemNoPedido.setQuantidade(0);
        } else {
            itemNoPedido.setQuantidade(novaQtd);
        }
        System.out.println("Inclusão desfeita com sucesso!");
    }
}


/*Caso 5: Busca Binária (
O(logn)) após a Ordenação
Nos métodos localizarProduto() da sua App.java, você usou uma Busca Linear (um for que percorre do início ao fim, com complexidade 
O(n)).

A Tarefa provável: Uma vez que você agora tem o QuickSort e o HeapSort, o professor pedirá para você primeiro ordenar o vetor de produtos pelo ID e, em seguida, substituir a busca linear por uma Busca Binária, que é absurdamente mais rápida (
O(logn)).

Como ficaria na classe App.java:*/

// O vetor precisa estar ordenado por ID antes de chamar este método!
static Produto buscaBinariaPorId(Produto[] vetor, int idProcurado) {
    int esquerda = 0;
    int direita = vetor.length - 1;

    while (esquerda <= direita) {
        int meio = (esquerda + direita) / 2;
        int idMeio = vetor[meio].hashCode(); // hashCode retorna o ID no seu código

        if (idMeio == idProcurado) {
            return vetor[meio]; // Achou o produto!
        } else if (idMeio < idProcurado) {
            esquerda = meio + 1; // Busca na metade da direita
        } else {
            direita = meio - 1; // Busca na metade da esquerda
        }
    }
    
    return null; // Não encontrou
}


/*Caso 6: Análise Empírica de Complexidade (Cronometrando os Algoritmos)
Como a disciplina exige o entendimento de complexidade assintótica (Big-O, Big-Omega, Big-Theta) e comparações entre algoritmos, um trabalho muito comum é criar um "Medidor de Desempenho".

A Tarefa provável: O professor pedirá para você gerar um vetor gigantesco (ex: 50.000 produtos), rodar diferentes algoritmos (QuickSort, HeapSort e métodos de 
O(n 
2
 ) como Bubble Sort ou Selection Sort) e imprimir no console o tempo que cada um levou em milissegundos para provar na prática qual é o mais eficiente.

Como ficaria um método de Benchmark na sua App.java:*/

public static void analisarDesempenhoOrdenacao() {
    System.out.println("Gerando array de teste com 50.000 produtos...");
    int tamanho = 50000;
    Produto[] arrayParaQuick = new Produto[tamanho];
    Produto[] arrayParaHeap = new Produto[tamanho];
    Produto[] arrayParaBubble = new Produto[tamanho];

    // Preenche os arrays com produtos "falsos" com IDs decrescentes (pior caso)
    for (int i = 0; i < tamanho; i++) {
        Produto p = new ProdutoNaoPerecivel("Teste", 10.0) {
            @Override public String gerarDadosTexto() { return null; }
            @Override public double valorDeVenda() { return 10.0; }
        };
        p.idProduto = tamanho - i; 
        
        arrayParaQuick[i] = p;
        arrayParaHeap[i] = p;
        arrayParaBubble[i] = p;
    }

    // 1. Cronometra o QuickSort
    long inicio = System.nanoTime();
    QuickSortGenerico.ordenar(arrayParaQuick);
    long fim = System.nanoTime();
    System.out.println("QuickSort executado em: " + (fim - inicio) / 1_000_000.0 + " ms");

    // 2. Cronometra o HeapSort
    inicio = System.nanoTime();
    HeapSortGenerico.ordenar(arrayParaHeap);
    fim = System.nanoTime();
    System.out.println("HeapSort executado em: " + (fim - inicio) / 1_000_000.0 + " ms");

    // 3. Cronometra o Bubble Sort (Para ver o impacto do O(n^2))
    inicio = System.nanoTime();
    bubbleSort(arrayParaBubble);
    fim = System.nanoTime();
    System.out.println("BubbleSort executado em: " + (fim - inicio) / 1_000_000.0 + " ms");
}

// Implementação simples de Bubble Sort para comparar
private static void bubbleSort(Produto[] array) {
    int n = array.length;
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (array[j].compareTo(array[j + 1]) > 0) {
                Produto temp = array[j];
                array[j] = array[j + 1];
                array[j + 1] = temp;
            }
        }
    }
}

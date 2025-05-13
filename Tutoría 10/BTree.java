import java.io.PrintWriter;
import java.io.IOException;

/**
 * La implementación permite desbordar temporalmente un nodo (m llaves)
 * y lo divide justo después, garantizando que las dos mitades conservan
 * ≥ mínimo de llaves (para m=5 → 2).
 */
class BTree {
    private final int order;             
    private BTreeNode root;

    public BTree(int order) {
        if (order < 3) throw new IllegalArgumentException("order ≥ 3");
        this.order = order;
        this.root  = new BTreeNode(order, true);
    }

    // búsqueda
    public boolean contains(int key) {
        return search(root, key) != null;
    }

    private BTreeNode search(BTreeNode node, int key) {
        int i = 0;
        while (i < node.keyCount && key > node.keys[i]) i++;
        if (i < node.keyCount && key == node.keys[i]) return node;      // Se encontró
        if (node.isLeaf) return null;                                  // No se encontró
        return node.isLeaf ? null : search(node.branches[i], key);
    }

    // inserción
    // Se permite desbordar un nodo (m llaves) y se divide justo después
    public void insert(int key) {
        insertRecursive(root, key);
        if (root.keyCount == order) {
            BTreeNode newRoot = new BTreeNode(order, false);
            newRoot.branches[0] = root;
            splitChild(newRoot, 0, root);
            root = newRoot;
        }
    }

    private void insertRecursive(BTreeNode node, int key) {
        int i = node.keyCount - 1;

        if (node.isLeaf) {                      
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.keyCount++;
            return;
        }

        // elegir hijo
        while (i >= 0 && key < node.keys[i]) i--;
        i++;

        insertRecursive(node.branches[i], key);

        // dividir si ese hijo se desbordó
        if (node.branches[i].keyCount == order) {
            splitChild(node, i, node.branches[i]);
        }
    }

    /**
     * Divide fullChild (m llaves) en dos nodos con ⌈m/2⌉‑1 y ⌊m/2⌋ llaves,
     * subiendo la llave media al padre.
     */
    private void splitChild(BTreeNode parent, int index, BTreeNode fullChild) {
        int mid = order / 2;                     // índice de la llave que sube

        BTreeNode right = new BTreeNode(order, fullChild.isLeaf);
        right.keyCount = fullChild.keyCount - mid - 1;   // llaves para el nuevo nodo

        // copiar llaves altas 
        for (int j = 0; j < right.keyCount; j++)
            right.keys[j] = fullChild.keys[mid + 1 + j];

        // copiar hijos si es interno
        if (!fullChild.isLeaf) {
            for (int j = 0; j <= right.keyCount; j++)
                right.branches[j] = fullChild.branches[mid + 1 + j];
        }

        // ajustar tamaño del hijo izquierdo
        fullChild.keyCount = mid;                // mantiene ≥ mínimo

        // desplazar ramas del padre
        for (int j = parent.keyCount; j >= index + 1; j--)
            parent.branches[j + 1] = parent.branches[j];
        parent.branches[index + 1] = right;

        // desplazar llaves del padre e insertar la mediana
        for (int j = parent.keyCount - 1; j >= index; j--)
            parent.keys[j + 1] = parent.keys[j];
        parent.keys[index] = fullChild.keys[mid];
        parent.keyCount++;
    }

    // exportat a DOT   
    public void exportDot(String fileName) throws IOException {
        try (PrintWriter out = new PrintWriter(fileName, "UTF-8")) {
            out.println("digraph BTree {");
            out.println("  node [shape=record, height=.1];");
            writeDot(root, out);
            out.println("}");
        }
    }

    private void writeDot(BTreeNode n, PrintWriter out) {
        if (n == null) return;
        String id = id(n);
        StringBuilder lbl = new StringBuilder("<p0>");
        for (int i = 0; i < n.keyCount; i++)
            lbl.append(" | ").append(n.keys[i]).append(" | <p").append(i + 1).append(">");
        out.printf("  %s [label=\"%s\"];%n", id, lbl);
        if (!n.isLeaf) {
            for (int i = 0; i <= n.keyCount; i++) {
                BTreeNode ch = n.branches[i];
                if (ch != null) {
                    out.printf("  %s:p%d -> %s;%n", id, i, id(ch));
                    writeDot(ch, out);
                }
            }
        }
    }

    private static String id(BTreeNode n) { return "n" + System.identityHashCode(n); }
}

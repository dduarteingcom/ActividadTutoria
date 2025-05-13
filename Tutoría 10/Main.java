public class Main {
    public static void main(String[] args) throws Exception {
        
        BTree a = new BTree(5);
        int[] data = {10, 20, 5, 6, 12, 30, 7, 17, 3, 2, 50, 60, 40,
                      25, 15, 1, 4, 9, 8, 11, 13, 14, 16, 18, 19,
                      21, 22, 23, 24, 26, 27, 28, 29, 31, 32, 33, 34, 35, 36};
        BTree b = new BTree(5);
        int[] data2 = {
              1, 2, 3, 4, 5,
              6, 7, 8, 9, 10, 11,
              12, 13, 14,
              15, 16, 17,
              18, 19, 20
        };
        BTree c = new BTree(5);
        int[] data3 = {
              1, 2, 3, 4, 5,
              6, 7, 8,
              9, 10, 11,
              12, 13, 14,
              15, 16, 17
        };
                /**
         * data1 Cubre caso  1, 2 y 4
         * data2 Cubre caso  3,5 y 6 
         * data3 Cubre caso  7
         */
        for (int x : data)  a.insert(x);
        for (int x : data2) b.insert(x);
        for (int x : data3) c.insert(x);

        
        
        

        // genera el archivo DOT     // genera el archivo DOT
        a.exportDot("arbol.dot");
        b.exportDot("arbol2.dot");
        c.exportDot("arbol3.dot");  



        

        System.out.println("Archivo arbol.dot creado. Usa Graphviz para renderizarlo.");

    }
}
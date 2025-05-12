public class BTreeNode {
    int[] keys;            
    BTreeNode[] branches;  
    int keyCount;          
    boolean isLeaf;

    BTreeNode(int order, boolean leaf) {
        this.keys     = new int[order-1];
        this.branches = new BTreeNode[order];
        this.keyCount = 0;
        this.isLeaf   = leaf;
    }
}
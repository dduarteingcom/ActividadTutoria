public class BTreeNode {
    int[] keys;            
    BTreeNode[] branches;  
    int keyCount;          
    boolean isLeaf;

    BTreeNode(int order, boolean leaf) {
        this.keys     = new int[order];
        this.branches = new BTreeNode[order+ 1];
        this.keyCount = 0;
        this.isLeaf   = leaf;
    }
}
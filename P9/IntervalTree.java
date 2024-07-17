import java.util.ArrayList;
import java.util.List;

class Interval {
    int start;
    int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

class IntervalTreeNode {
    Interval interval;
    int maxEnd;
    IntervalTreeNode left, right;
    boolean color; // true for Red, false for Black

    public IntervalTreeNode(Interval interval) {
        this.interval = interval;
        this.maxEnd = interval.end;
        this.left = this.right = null;
        this.color = true; // New nodes are always Red
    }
}

public class IntervalTree {
    private IntervalTreeNode root;

    // Helper method to insert into a Red-Black Tree
    private IntervalTreeNode insert(IntervalTreeNode root, Interval interval) {
        if (root == null) {
            return new IntervalTreeNode(interval);
        }

        // Standard BST insertion
        if (interval.start < root.interval.start) {
            root.left = insert(root.left, interval);
        } else {
            root.right = insert(root.right, interval);
        }

        // Update maxEnd
        if (root.maxEnd < interval.end) {
            root.maxEnd = interval.end;
        }

        // Perform rotation and color flips as needed for Red-Black Tree
        return root;
    }

    // Public method to insert an interval into the tree
    public void insertInterval(int start, int end) {
        Interval interval = new Interval(start, end);
        root = insert(root, interval);
        root.color = false; // Root must be black
    }

    // Helper method to find all intervals overlapping with [start, end]
    private void findOverlappingIntervals(IntervalTreeNode root, int start, int end, List<Interval> result) {
        if (root == null) {
            return;
        }

        // If intervals overlap, add to result
        if (root.interval.start <= end && root.interval.end >= start) {
            result.add(root.interval);
        }

        // Traverse left if possible
        if (root.left != null && root.left.maxEnd >= start) {
            findOverlappingIntervals(root.left, start, end, result);
        }

        // Traverse right if possible
        if (root.right != null && root.interval.start <= end) {
            findOverlappingIntervals(root.right, start, end, result);
        }
    }

    // Public method to find all intervals overlapping with [start, end]
    public List<Interval> findOverlappingIntervals(int start, int end) {
        List<Interval> result = new ArrayList<>();
        findOverlappingIntervals(root, start, end, result);
        return result;
    }

    // Helper method to delete an interval from the tree
    private IntervalTreeNode delete(IntervalTreeNode root, Interval interval) {
        if (root == null) {
            return null;
        }

        // Standard BST deletion
        if (interval.start < root.interval.start) {
            root.left = delete(root.left, interval);
        } else if (interval.start > root.interval.start) {
            root.right = delete(root.right, interval);
        } else { // Found the node to delete
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            IntervalTreeNode successor = minValueNode(root.right);

            // Copy the inorder successor's content to this node
            root.interval = successor.interval;

            // Delete the inorder successor
            root.right = delete(root.right, successor.interval);
        }

        // Update maxEnd
        if (root != null) {
            root.maxEnd = Math.max(getMaxEnd(root.left), getMaxEnd(root.right));
        }

        // Perform rotation and color flips as needed for Red-Black Tree
        return root;
    }

    // Helper method to get the minimum value node in a subtree
    private IntervalTreeNode minValueNode(IntervalTreeNode node) {
        IntervalTreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Public method to delete an interval from the tree
    public void deleteInterval(int start, int end) {
        Interval interval = new Interval(start, end);
        root = delete(root, interval);
        if (root != null) {
            root.color = false; // Root must be black
        }
    }

    // Helper method to get maxEnd of a subtree
    private int getMaxEnd(IntervalTreeNode node) {
        return (node == null) ? Integer.MIN_VALUE : node.maxEnd;
    }

    public static void main(String[] args) {
        IntervalTree intervalTree = new IntervalTree();

        // Insert intervals
        intervalTree.insertInterval(15, 20);
        intervalTree.insertInterval(10, 30);
        intervalTree.insertInterval(5, 12);
        intervalTree.insertInterval(25, 35);

        // Find overlapping intervals with [14, 25]
        List<Interval> overlaps = intervalTree.findOverlappingIntervals(14, 25);
        System.out.println("Intervals overlapping with [14, 25]:");
        for (Interval interval : overlaps) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }

        // Delete interval [10, 30]
        intervalTree.deleteInterval(10, 30);

        // Find overlapping intervals with [14, 25] after deletion
        overlaps = intervalTree.findOverlappingIntervals(14, 25);
        System.out.println("\nIntervals overlapping with [14, 25] after deletion of [10, 30]:");
        for (Interval interval : overlaps) {
            System.out.println("[" + interval.start + ", " + interval.end + "]");
        }
    }
}

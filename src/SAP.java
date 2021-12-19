import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

  private final Digraph G;

  public SAP(Digraph G) {
    this.G = G;
  }

  public int length(int v, int w) {
    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
    int minLength = Integer.MAX_VALUE;
    for (int i = 0; i < G.V(); i++) {
      if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
        minLength = Math.min(minLength, bfsW.distTo(i) + bfsW.distTo(i));
      }
    }
    if (minLength == Integer.MAX_VALUE) {
      return -1;
    }
    return minLength;
  }

  public int ancestor(int v, int w) {
    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(G, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(G, w);
    int minLength = Integer.MAX_VALUE;
    int ancestor = -1;
    for (int i = 0; i < G.V(); i++) {
      if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
        int currentLength = bfsW.distTo(i) + bfsW.distTo(i);
        if (currentLength < minLength) {
          minLength = currentLength;
          ancestor = i;
        }
      }
    }
    return ancestor;
  }
}

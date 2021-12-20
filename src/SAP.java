import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

  private final Digraph G;

  public SAP(Digraph G) {
    this.G = G;
  }

  public int length(int v, int w) {
    validateVertices(v, w);
    BreadthFirstDirectedPaths bfsV = createBFS(G, v);
    BreadthFirstDirectedPaths bfsW = createBFS(G, w);
    int ancestor = ancestor(v, w);
    if (ancestor == -1) {
      return -1;
    }
    return bfsV.distTo(ancestor) + bfsW.distTo(ancestor);
  }

  public int ancestor(int v, int w) {
    validateVertices(v, w);
    return ancestor(createBFS(G, v), createBFS(G, w));
  }

  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    validateVertices(v, w);
    BreadthFirstDirectedPaths bfsV = createBFS(G, v);
    BreadthFirstDirectedPaths bfsW = createBFS(G, w);
    int ancestor = ancestor(v, w);
    if (ancestor == -1) {
      return -1;
    }
    return bfsV.distTo(ancestor) + bfsW.distTo(ancestor);
  }

  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    validateVertices(v, w);
    return ancestor(createBFS(G, v), createBFS(G, w));
  }

  private int ancestor(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
    int minLength = Integer.MAX_VALUE;
    int ancestor = -1;
    int currentLength;
    for (int i = 0; i < G.V(); i++) {
      if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
        if ((currentLength = bfsV.distTo(i) + bfsW.distTo(i)) < minLength) {
          minLength = currentLength;
          ancestor = i;
        }
      }
    }
    return ancestor;
  }

  private void validateVertices(int v, int w) {
    if (v < 0 || v >= G.V() || w < 0 || w >= G.V()) {
      throw new IllegalArgumentException();
    }
  }

  private void validateVertices(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null) {
      throw new IllegalArgumentException();
    }
  }

  private BreadthFirstDirectedPaths createBFS(Digraph G, int v) {
    return new BreadthFirstDirectedPaths(G, v);
  }

  private BreadthFirstDirectedPaths createBFS(Digraph G, Iterable<Integer> v) {
    return new BreadthFirstDirectedPaths(G, v);
  }

  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length   = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}

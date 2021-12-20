import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

  private final WordNet wordNet;

  public Outcast(WordNet wordnet) {
    this.wordNet = wordnet;
  }

  public String outcast(String[] nouns) {
    int maxDistance = 0;
    String outcast = null;
    for (String nounA : nouns) {
      int currentDistance = 0;
      for (String nounB : nouns) {
        currentDistance += wordNet.distance(nounA, nounB);
      }
      if (currentDistance > maxDistance) {
        maxDistance = currentDistance;
        outcast = nounA;
      }
    }
    return outcast;
  }

  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}

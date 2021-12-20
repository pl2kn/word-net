import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordNet {

  private final SAP sap;
  private final Map<Integer, String> synsets;
  private final Map<Integer, ArrayList<Integer>> hypernyms;
  private final Map<String, ArrayList<Integer>> nouns;

  public WordNet(String synsets, String hypernyms) {
    if (synsets == null || hypernyms == null) {
      throw new IllegalArgumentException();
    }
    this.synsets = new HashMap<>();
    this.hypernyms = new HashMap<>();
    nouns = new HashMap<>();
    readSynets(synsets);
    readHypernyms(hypernyms);
    Digraph digraph = new Digraph(this.hypernyms.size());
    for (Integer synsetId : this.hypernyms.keySet()) {
      for (Integer hypernym : this.hypernyms.get(synsetId)) {
        digraph.addEdge(synsetId, hypernym);
      }
    }
    sap = new SAP(digraph);
  }

  public Iterable<String> nouns() {
    return nouns.keySet();
  }

  public int distance(String nounA, String nounB) {
    if (!(isNoun(nounA) && isNoun(nounB))) {
      throw new IllegalArgumentException();
    }
    return sap.length(nouns.get(nounA), nouns.get(nounB));
  }

  public String sap(String nounA, String nounB) {
    if (!(isNoun(nounA) && isNoun(nounB))) {
      throw new IllegalArgumentException();
    }
    int ancestor = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
    return synsets.get(ancestor);
  }

  public boolean isNoun(String word) {
    if (word == null) {
      throw new IllegalArgumentException();
    }
    return nouns.containsKey(word);
  }

  public static void main(String[] args) {
    WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
  }

  private void readSynets(String synsetsFileName) {
    In in = new In(synsetsFileName);
    for (String line : in.readAllLines()) {
      String[] lineElements = line.split(",");
      int synsetId = Integer.parseInt(lineElements[0]);
      for (String noun : lineElements[1].split(" ")) {
        if (!nouns.containsKey(noun)) {
          nouns.put(noun, new ArrayList<>());
        }
        nouns.get(noun).add(synsetId);
      }
      synsets.put(synsetId, lineElements[1]);
    }
  }

  private void readHypernyms(String hypernymsFileName) {
    In in = new In(hypernymsFileName);
    for (String line : in.readAllLines()) {
      String[] lineElements = line.split(",");
      int synsetId = Integer.parseInt(lineElements[0]);
      ArrayList<Integer> hypernymList = new ArrayList<>();
      for (int i = 1; i < lineElements.length; i++) {
        hypernymList.add(Integer.parseInt(lineElements[i]));
      }
      hypernyms.put(synsetId, hypernymList);
    }
  }
}

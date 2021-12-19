import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordNet {

  private Digraph digraph;
  private Map<Integer, ArrayList<String>> synsets;
  private Map<Integer, ArrayList<Integer>> hypernyms;
  private Map<String, Integer> nouns;

  public WordNet(String synsets, String hypernyms) {
    if (synsets == null || hypernyms == null) {
      throw new IllegalArgumentException();
    }
    readSynets(synsets);
    readHypernyms(hypernyms);
    digraph = new Digraph(this.hypernyms.size());
    for (Integer synsetId : this.hypernyms.keySet()) {
      for (Integer hypernym : this.hypernyms.get(synsetId)) {
        digraph.addEdge(synsetId, hypernym);
      }
    }
  }

  public Iterable<String> nouns() {
    return nouns.keySet();
  }

  public int distance(String nounA, String nounB) {
    if (!isNoun(nounA) || !isNoun(nounB)) {
      throw new IllegalArgumentException();
    }
    return 0;
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
    synsets = new HashMap<>();
    nouns = new HashMap<>();
    for (String line : in.readAllLines()) {
      String[] lineElements = line.split(",");
      int synsetId = Integer.parseInt(lineElements[0]);
      ArrayList<String> nounList = new ArrayList<>();
      for (String noun : lineElements[1].split(" ")) {
        nounList.add(noun);
        nouns.put(noun, synsetId);
      }
      synsets.put(synsetId, nounList);
    }
  }

  private void readHypernyms(String hypernymsFileName) {
    In in = new In(hypernymsFileName);
    hypernyms = new HashMap<>();
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

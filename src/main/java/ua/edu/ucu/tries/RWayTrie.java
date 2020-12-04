package ua.edu.ucu.tries;

import ua.edu.ucu.immutable.Queue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class RWayTrie implements Trie {
    private Node root;
    private static final int move = 97;
    private static final int R = 26;

    private static class Node{
        private int val;
        public Node[] next;

        public Node() {
            val = -1;
            next = new Node[R];
        }
    }

    public RWayTrie() {
        root = new Node();
    }

    @Override
    public void add(Tuple t) {

        root = add_helper(root, t.term, t.weight, 0);
    }

    private Node add_helper(Node to, String term, int weight, int index) {
        if (to == null) to = new Node();
        if (term.length() == index) {
            to.val = weight;
            return to;
        }
        int next = term.charAt(index) - move;
        to.next[next] = add_helper(to.next[next], term, weight, index + 1);
        return to;
    }

    private Node get(Node x, String key, int d)
    {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - move], key, d+1);
    }

    @Override
    public boolean contains(String word) {
        Node nd = get(root, word, 0);
        return nd != null && nd.val != -1;
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            root = delete_helper(root, word, 0);
            return true;
        }
        return false;
    }

    private Node delete_helper(Node x, String key, int d)
    {
        if (x == null) return null;
        if (d == key.length())
            x.val = -1;
        else
        {
            int c = key.charAt(d);
            x.next[c - move] = delete_helper(x.next[c - move], key, d+1);
        }
        if (x.val != -1) return x;
        for (int i = 0; i < R; i++)
            if (x.next[i] != null) return x;
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);
        return q;
    }

    private void collect(Node x, String pre,
                         Queue q)
    {
        if (x == null) return;
        if (x.val != -1) q.enqueue(pre);
        for (int c = 0; c < R; c++)
            collect(x.next[c], pre + (char) (c + move), q);
    }

    @Override
    public int size() {
        return size_helper(root);
    }

    public int size_helper(Node node) {
        int size = 0;
        if (node == null) return size;

        if (node.val != -1) size++;
        for (int i = 0; i < R; i++) {
             size += size_helper(node.next[i]);
        }
        return size;
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        int pref_length = pref.length();
        if (pref_length < 2) throw new IndexOutOfBoundsException("k can't be lower than 2");
        int length;
        if (pref_length == 2) {
            length = 2;
        } else {
            length = k;
        }
        length = k == 2 ? 3 : k;

        HashSet<Integer> usedLengths = new HashSet<>();
        List<String> vals = new LinkedList<>();

        for (String s: wordsWithPrefix(pref)) {
            if (s.length() >= length)  {
                if (usedLengths.contains(s.length()) || usedLengths.size() < k) {
                    usedLengths.add(s.length());
                    vals.add(s);
                }
            }
        }
        return vals;
    }
}

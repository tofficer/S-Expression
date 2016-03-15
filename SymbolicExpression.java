import java.io.*;
import java.util.*;
import java.lang.*;

class SymbolicExpression{
  public static void main(String[] args){
    Scanner scnr = new Scanner(System.in);
    String s = scnr.nextLine();
    
    validateInput(s);
    
    String print = getExpression(s);
    System.out.println(print);
  }
  
  //check for E1 -> bad input
  //valid input is in form (A,B) (C,D) (E,F) ...
  public static void validateInput(String s){
    if (s.length() < 5){
      System.out.println("E1");
      System.exit(0);
    }
    
    String regex = "^\\(([A-Z][,][A-Z])\\)[\\s]$"; //"^\(([A-Z][,][A-Z])\)[\s]$";
    Pattern p = Pattern.compile(regex);
    
    for (int i = 0; i < s.length(); i += 6){
      String test = s.substring(i, i+6);
      Matcher m = p.matcher(test);
      if (!m.matches()){
        System.out.println("E1");
        System.exit(0);
      }
    }
  }
  
  public static String getExpression(String s){
    //nodes holds all the nodes in the tree
    HashSet<Character> nodes = new HashSet<Character>(26);
    //HashSet<Integer> nodes_ints = new HashSet<Integer>(26);
    //graph[i][j] represents some node i and it's children j if graph[i][j] = true
    boolean[][] graph = new boolean[26][26];
    
    
    //checks for E2 -> duplicate edge
    for(int i = 1;i < s.length();i += 6){
      int a = s.charAt(i)-'A';
      int b = s.charAt(i+2)-'A';
      if(graph[a][b]){
        return "E2";
      }
      graph[a][b] = true;
      nodes.add(s.charAt(a));
      nodes.add(s.charAt(b));
      //nodes_ints.add(a);
      //nodes_ints.add(b);
    }
                
    //check error E3 -> >2 children
    //for (int parent = 0; parent < 26; parent++){
    for (char parent : nodes){
      int numChild = 0;
      //for (int child = 0; child < 26; child++){
      for (char child : nodes){
        if (graph[parent-'A'][child-'A']) numChild++;
      }
      if (numChild > 2){
        return "E3";
      }
    }
    
    //check E4 -> multiple roots
    //note that root won't have any parents
    int numRoots = 0;
    char root = ' ';
    for(char root_test : nodes){
      int count = 0;
      //for(int parent : nodes_ints){
      for (char parent : nodes){
        if(graph[parent-'A'][root_test-'A']) break;
        count++;
        if (count == nodes.size()){
          numRoots++;
          root = root_test;
        }
      }
    }
    if (numRoots > 1){
      return "E4";
    }
    if (numRoots == 0 || root == ' '){
      return "E5";
    }
    
    boolean[] visited = new boolean[26];
    if(isCycle(root, nodes, graph, visited)){
      return "E5";
    }
    
    return helper(root, graph);
  }
  
  //recursive method that starts with the root and repeats with all children of given node
  //if a node has been visited before then there is a cycle
  private static boolean isCycle(char parent, HashSet<Character> nodes, boolean[][] graph, boolean[] visited){
    if(visited[parent-'A']) return true; 
    visited[parent-'A'] = true;
    for (char child : nodes){
      if(graph[parent-'A'][child-'A']){
        if(isCycle(child, nodes, graph, visited)) return true;
      }
    }
  }
  
  //recursive DFS method to construct the S expression
  private static String helper(char root, boolean[][] graph){
    String leftChild = "";
    String rightChild = "";
    
    for(int lIndex = 0; lIndex < 26; lIndex++){
      if(graph[root-'A'][lIndex]){
        leftChild = helper((char) lIndex+'A', graph);
        for(int rIndex = lIndex+1; rIndex < 26; rIndex++){
          if(graph[root-'A'][rIndex]){
            rightChild = helper((char) rIndex+'A', graph);
            break;
          }
        }
        break;
      }
    }
    return "("+root+leftChild+rightChild+")";
  }
}

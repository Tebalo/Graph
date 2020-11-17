import  java.util.*;
import static java.lang.System.out;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * @author Edzani Wapa Omogolo 201501173
 *
 */
public class Graph1 {
	protected List<Edge> edges;
	protected List<Node> nodes;

	public Graph1() {
		edges = new LinkedList<Edge>();
		nodes = new LinkedList<Node>();
	}
	class Node{
		private String town;
		private boolean visited=false;
		private List<Node> adjecents;
		Node(String town){
			this.town = town;
			this.adjecents = new ArrayList<Node>();
		}
		String getTown() {
			return town;
		}
		void addAdjecents(Node n) {
			this.adjecents.add(n);
		}
		void removeAdjecents(Node n) {
			this.adjecents.remove(n);
		}
		List<Node> getAdjacents(){
			return adjecents;
		}
		boolean isVisited() {
			return this.visited;
		}
		void setVisited(boolean visited) {
			this.visited = visited;
		}
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	class Edge{
		private Node source;
		private Node destination;
		private double weight;
		Edge(Node source,Node destination,double weight){
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
		public Node getSource() {
			return source;
		}
		public void setSource(Node source) {
			this.source = source;
		}
		public Node getDestination() {
			return destination;
		}
		public void setDestination(Node destination) {
			this.destination = destination;
		}
		public double getWeight() {
			return weight;
		}
		public void setWeight(double weight) {
			this.weight = weight;
		}
	}
	Edge addEdge(double weight, String town1,String town2) {
		boolean exists = false;
		for(Edge edge:edges) {
			if((edge.source.equals(town1) && edge.destination.equals(town1)) || (edge.destination.equals(town1) && edge.source.equals(town1))) {
				exists = true;
				return edge;
			}
		}
		if(exists==false) {
			Node node1 = addNode(town1);
			Node node2 = addNode(town2);
			Edge edge = new Edge(node1, node2, weight);
			edges.add(edge);
			node1.addAdjecents(node2);
			node2.addAdjecents(node1);
			return edge;
		}
		return null;
	}
	Node addNode(String town) {
		boolean exists = false;
		for(Node node:nodes) {
			if(node.town.equals(town)) {
				exists = true;
				return node;
			}
		}
		if(exists==false) {
			Node node = new Node(town);
			nodes.add(node);
			return node;
		}
		return null;
	}
	void removeEdge(Edge edge) {
		for(Edge edgex:edges) {
			if((edgex.source.town.contains(edge.source.town)) && edgex.destination.town.contains(edge.destination.town) || (edgex.destination.town.contains(edge.source.town)) && edgex.source.town.contains(edge.destination.town)) {
				edges.remove(edgex);
				edgex.source.addAdjecents(edgex.destination);
				edgex.destination.addAdjecents(edgex.source);
				break;
			}
		}
	}
	void removeEdge(Node head, Node tail) {
		for(Edge edgex:edges) {
			if((edgex.source.town.contains(head.town)) && edgex.destination.town.contains(tail.town) || (edgex.destination.town.contains(head.town)) && edgex.source.town.contains(tail.town)) {
				edges.remove(edgex);
				edgex.source.addAdjecents(edgex.destination);
				edgex.destination.addAdjecents(edgex.source);
				break;
			}
		}
	}
	void removeNode(Node node) {
		List<Edge> edgeD = new LinkedList<Edge>();
		for(Edge edgex:edges) {
			if(edgex.source.town.contains(node.town) || edgex.destination.town.contains(node.town)) {
				edgeD.add(edgex);
				edgex.source.removeAdjecents(edgex.destination);
				edgex.destination.removeAdjecents(edgex.source);
			}
		}
		int i = 0;
		while(i < edgeD.size()) {
			edges.remove(edgeD.get(i));
			i+=1;
		}
		for(Node nodez:nodes) {
			if(nodez.town.contains(node.town)) {
				nodes.remove(nodez);
				break;
			}
		}
	}
	HashSet<Edge> DFS(Node start){
		Stack<Node> DFS_stack = new Stack<Node>();
		start = getNode(start);
		DFS_stack.add(start);
		start.visited = true;
		while(!DFS_stack.isEmpty()) {
			Node nodeRemove = DFS_stack.pop();
			System.out.println(nodeRemove.town+" ");
			
			List<Node> adjs = nodeRemove.getAdjacents();
			
			for(int i = 0;i < adjs.size();i++) {
				Node currentNode = adjs.get(i);
				if(currentNode != null && currentNode.isVisited()==false) {
					currentNode.setVisited(true);
					DFS_stack.add(currentNode);
					
				}
			}
		}
		return null;
	}
	HashSet<Edge> BFT(Node start){
		if(start == null) {
			return null;
		}
		LinkedList<Node> queue = new LinkedList<Node>();
		start = getNode(start);
		queue.add(start);
		start.visited = true;
		
		while(!queue.isEmpty()) {
			Node nodeRemove = queue.remove();
			out.println(nodeRemove.town+" ");
			List<Node> adjs = nodeRemove.getAdjacents();
			for(int i = 0;i < adjs.size();i++) {
				Node currentNode = adjs.get(i);
				
				if(currentNode !=null && currentNode.isVisited()==false) {
					queue.add(currentNode);
					currentNode.visited=true;
				}
			}
		}
		return null;
	}
	void reset(Node start) {
		Stack<Node> DFS_stack = new Stack<Node>();
		start = getNode(start);
		DFS_stack.add(start);
		start.visited = false;
		while(!DFS_stack.isEmpty()) {
			Node nodeRemove = DFS_stack.pop();
			
			List<Node> adjs = nodeRemove.getAdjacents();
		
			for(int i = 0;i < adjs.size();i++) {
				Node currentNode = adjs.get(i);
				if(currentNode != null && currentNode.isVisited()==true) {
					DFS_stack.add(currentNode);
					currentNode.setVisited(false);
				}
			}
		}
	}
	Edge getEdge(int i) {
		return edges.get(i);
	}
	Node getNode(int i) {
		return nodes.get(i);
	}
	Node getNode(Node node) {
		for(Node nodex:nodes) {
			if(nodex.town.contains(node.town)) {
				return nodex;
			}
		}
		return null;
	}
	int numEdges() {
		return edges.size();
	}
	int numNodes() {
		return nodes.size();
	}
	void print() {
		for(Edge edge:edges) {
			System.out.println(edge.source.town+" to/from "+edge.destination.town+" is "+edge.weight+" km");
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Graph1 graph = new Graph1();

		File file = new File("Ass4.txt");
		Pattern regex = Pattern.compile("^\\S+\\s*(\\S+)\\s*(\\S+)\\s*(\\S+).*$");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		while((st=br.readLine())!=null) {
			Matcher matcher = regex.matcher(st);
			String[] set = st.trim().split("\\s+");

			
			int i = 0;
			while(i<set.length) {
				if(i+2<set.length && i%2==0) {
					//System.out.println(set[0]+" is "+set[i+2]+" km "+set[i+1]);
					graph.addEdge(Double.parseDouble(set[i+2]),set[0],set[i+1]);
				}
				i+=1;
			}
		}
		out.println();
		
		System.out.println("Breadth first search:");
		graph.BFT(graph.new Node("Gaborone"));
		graph.reset(graph.new Node("Gaborone"));
		System.out.println();
		System.out.println("Depth first search:");
		graph.DFS(graph.new Node("Gaborone"));
		System.out.println();
		System.out.println("Prints a representation of the graph:");
		System.out.println();
		graph.print();
		System.out.println();
		System.out.println("Number of edges: "+graph.numEdges());
		System.out.println("Number of nodes: "+graph.numNodes());
		graph.removeEdge(graph.new Edge(graph.new Node("Gaborone"),graph.new Node("Francistown"),432));
		graph.removeEdge(graph.new Node("Kanye"),graph.new Node("Gaborone"));
		System.out.println();
		System.out.println("Prints a representation of the graph after deletion:");
		System.out.println();
		graph.print();
		graph.removeNode(graph.new Node("Gaborone"));
		System.out.println();
		graph.print();
		System.out.println();
		System.out.println("Number of edges: "+graph.numEdges());
		System.out.println("Number of nodes: "+graph.numNodes());
	}

}

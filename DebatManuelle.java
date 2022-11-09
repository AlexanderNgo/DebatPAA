import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DebatManuelle {
	
	private HashMap<String, List<String>> graphe;
	
	public DebatManuelle() {
		graphe = new HashMap<String, List<String>>();
	}
	
	public void ajoutArguments(int n) {
		for(int i = 1; i < n+1; i++) {
			graphe.put("A"+i, new ArrayList<String>());
		}
	}
	
	public void ajoutContradictions(String c1, String c2) {
		if(!graphe.containsKey(c1)) {
			System.out.println("l'argument "+c1+" n'appartient pas au débat");
			
		}else if(!graphe.containsKey(c2)) {
			System.out.println("l'argument "+c2+" n'appartient pas au débat");
			
		}else if(graphe.get(c1).contains(c2)) {
			System.out.println(c1+ " contredit déja "+c2);
			
		}else {
			graphe.get(c1).add(c2);
		}		
	}
	
	public HashMap<String,List<String>> getGraph(){
		return graphe;
	}
	
}

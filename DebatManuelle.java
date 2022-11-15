package DebatPAA;

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
			System.out.println("L'argument "+c1+" n'appartient pas au d�bat");
		}else if(!graphe.containsKey(c2)) {
			System.out.println("L'argument "+c2+" n'appartient pas au d�bat");
		}else if(graphe.get(c1).contains(c2)) {
			System.out.println(c1+ " contredit d�ja "+c2);
/*		}else if(c1==c2) {
			System.out.println("Un argument ne peut pas se contredire lui meme");
*/		}else {
			graphe.get(c1).add(c2);
		}		
	}
	
	public HashMap<String,List<String>> getGraph(){
		return graphe;
	}
	
	public void afficheDebat() {                 // AFFICHE LE GRAPHE (AIDE POUR LES TESTS)
		StringBuffer debat = new StringBuffer();
		debat.append("\nGraphe : \n");
		for (String arg : graphe.keySet()) {
			debat.append(arg +" : [ ");
			for(int i = 0; i < graphe.get(arg).size(); i++) {
				debat.append(graphe.get(arg).get(i)+",");
			}
			debat.deleteCharAt(debat.length()-1);
			debat.append(" ]\n");
		}
		System.out.println(debat.toString());
	}
}

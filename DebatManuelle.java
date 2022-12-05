package programmeManuelle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DebatManuelle {
	
	private HashMap<String, List<String>> graphe;
	
	public DebatManuelle() {
		graphe = new HashMap<String, List<String>>();
	}
	
	/**
	*	Nom : ajoutArguments
	*	@param int n
	*	@return NONE
	*	Description : Ajoute un argument au graphe
	**/
	public void ajoutArguments(int n) {
		for(int i = 1; i < n+1; i++) {
			graphe.put("A"+i, new ArrayList<String>());
		}
	}
	
	/**
	*	Nom : ajoutContradictions
	*	@param String c1, String c2
	*	@return NONE
	*	Description : Ajoute une contradiction dans le graphe (c1 contredit c2)
	**/
	public void ajoutContradictions(String c1, String c2) {
		if (c1.equals(c2)) {
			throw new IllegalArgumentException("l'argument \" "+c1+" \" ne peut pas se contredire lui m�me");
		}
		
		if(!graphe.containsKey(c1)) {
			System.out.println("l'argument "+c1+" n'appartient pas au d�bat");
			
		}else if(!graphe.containsKey(c2)) {
			System.out.println("l'argument "+c2+" n'appartient pas au d�bat");
			
		}else if(graphe.get(c1).contains(c2)) {
			System.out.println(c1+ " contredit d�ja "+c2);
			
		}else {
			graphe.get(c1).add(c2);
		}		
	}		
	
	/**
	*	Nom : getGraph
	*	@param NONE
	*	@return HashMap<String,List<String>>
	*	Description : Retourne le graphe
	**/
	public HashMap<String,List<String>> getGraph(){
		return graphe;
	}
	
	/**
	*	Nom : afficheDebat
	*	@param NONE
	*	@return NONE
	*	Description : Affiche le graphe 
	**/
	public void afficheDebat() {                 // AFFICHE LE GRAPHE (AIDE POUR LES TESTS)
		StringBuffer debat = new StringBuffer();
		debat.append("\nDebat : \n");
		for (String arg : graphe.keySet()) {
			debat.append(arg +" : [ ");
			for(int i = 0; i < graphe.get(arg).size(); i++) {
				if(i==0) {
					debat.append(graphe.get(arg).get(i));
				}else {
					debat.append(", "+graphe.get(arg).get(i));
				}
				
			}
			debat.append(" ]\n");
		}
		
		System.out.println(debat.toString());
	}
	
	/**
	*	Nom : ajoutArgumentsString
	*	@param String s
	*	@return NONE
	*	Description : Ajoute un argument, qui correspond � chaine de caract�re quelconque, dans le d�bat.
	**/
	public void ajoutArgumentsString(String s) {
		if ( graphe.containsKey(s)) {
			System.out.println(s+" est deja un argument du d�bat");
		}else {
			graphe.put(s, new ArrayList<String>());
		}
		
	}
	
	public List<String> getArguments(){
		Set<String> setArg= graphe.keySet();
		return new ArrayList<String>(setArg);
	}
}

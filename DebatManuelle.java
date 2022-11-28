package programmeManuelle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	/**
	*	Nom : HashMap
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
		debat.append("\nGraphe : \n");
		for (String arg : graphe.keySet()) {
			debat.append(arg +" : [ ");
			for(int i = 0; i < graphe.get(arg).size(); i++) {
				debat.append(graphe.get(arg).get(i)+", ");
			}
			debat.append("]\n");
		}
		
		System.out.println(debat.toString());
	}
	
	/**
	*	Nom : ajoutArgumentsString
	*	@param String s
	*	@return NONE
	*	Description : Ajoute un argument, qui correspond à chaine de caractère quelconque, dans le débat.
	**/
	public void ajoutArgumentsString(String s) {
		if ( graphe.containsKey(s)) {
			System.out.println(s+" est déja un argument du débat");
		}else {
			graphe.put(s, new ArrayList<String>());
		}
		
	}
}

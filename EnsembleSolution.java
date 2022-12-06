package solutionAuto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import programmeManuelle.DebatManuelle;
import programmeManuelle.SolutionPotentielle;

public class EnsembleSolution {
	
	private DebatManuelle debat;
	private List<String> ensArg;
	
	public EnsembleSolution(DebatManuelle debat) {
		this.debat=debat;
		this.ensArg = debat.getArguments();
	}
	
	/**
	 * Nom : union
	 * @param List<String> l1
	 * @param List<String> l2
	 * @return List<String>
	 * Description: R�alise l'union des valeurs de 2 listes
	 */
	
	private List<String> union(List<String> l1, List<String> l2) {
        Set<String> set = new HashSet<String>();
        set.addAll(l1);
        set.addAll(l2);
        return new ArrayList<String>(set);
    }
	
	private long fact(int n) {
		long fact = 1;
		 	for(int i=1;i<=n;i++){
		 		fact=fact*i;
		 	}
		 return fact;
	}
	
	private int comb(int n, int p) {
		return (int) (fact(n) / (fact(p) * fact(n - p)));
	}
	/**
	 * Nom : getEnsSolPos
	 * @return List<List<List<String>>> ensSolPos
	 * Description : Methode qui retourne l'ensemble des possibilit�s de E  
	 */
    public List<List<List<String>>> getEnsSolPos(){
    	List<List<List<String>>> ensSolPos = new ArrayList<List<List<String>>>(); //ensemble des solutions possibles
    	
    	ensSolPos.add(new ArrayList<List<String>>());
    	ArrayList<String> ensVide=new ArrayList<String>();
    	ensSolPos.get(0).add(ensVide);
    	
    	ensSolPos.add(new ArrayList<List<String>>());
    	for (String arg:ensArg) {
    		ArrayList<String> solPos=new ArrayList<String>();
    		solPos.add(arg);
    		ensSolPos.get(1).add(solPos);
    	}
    	int nbArg=ensArg.size();
    	
    	for(int i =1;i<nbArg;i++) {
    		List<List<String>> liActuel= ensSolPos.get(i);
    		List<List<String>> liSuiv=new ArrayList<List<String>>();
    		int solmax= comb(ensArg.size(),i+1);
    		int taille= liActuel.size();
    		for (int j=0;j<taille && solmax>liSuiv.size();j++) {
    			List<String> arg1=liActuel.get(j);
    			for(int k=j+1;k<taille;k++) {
    				List<String> arg2 = liActuel.get(k);
    				List<String> nouvArg=union(arg1,arg2);
    				if((!liSuiv.contains(nouvArg)) && (nouvArg.size()==i+1)) {
    					liSuiv.add(nouvArg);
    				}
    			}
    		}
    		ensSolPos.add(liSuiv);
    	}
    	return ensSolPos;
    }
   /**
    * Nom : getEnsSolAdm
    * @param List<List<List<String>>> ensSolPos
    * @return List<List<List<String>>>
    * Description : Methode qui retourne l'ensemble des solutions admissibles d'un debat
    */
    public List<List<String>> getEnsSolAdm(List<List<List<String>>> ensSolPos){
    	
    	List<List<String>> ensSolAdm =new ArrayList<List<String>>();
    	SolutionPotentielle solPos= new SolutionPotentielle();
    	
    	ensSolAdm.add(new ArrayList<String>());
    	
    	for(int i =1;i<ensSolPos.size() ;i++) {
    		List<List<String>> listArg =ensSolPos.get(i);
    		for(int j=0;j<listArg.size();j++) {
    			solPos.setArguments(listArg.get(j));
    			if (solPos.verifnoPrint(debat)) {
    				ensSolAdm.add((listArg.get(j)));
    			}
    		}
    	}
    	return ensSolAdm;
    }
    /**
     * Methode qui retourne l'ensemble des solutions preferees d'un debat
     * @param List<List<String>> ensSolAdm
     * @return List<List<String>> ensSolPref
     */
    public List<List<String>> getensSolPref(List<List<String>> ensSolAdm){
    	List<List<String>> ensSolPref =new ArrayList<List<String>>();
    	
    	for(int i=1;i<ensSolAdm.size();i++) {
    		List<String> arg =ensSolAdm.get(i);
        	boolean isNotContains=true;
    		for(int j=1;j<ensSolAdm.size();j++) {
    			if(j!=i) {
    				if(ensSolAdm.get(j).containsAll(arg)) {
        				isNotContains =false;
        			}
    			}
    		}
    		if (isNotContains) {
    			ensSolPref.add(arg);	
    		}
    	}
    	
    	if(ensSolPref.isEmpty()) {
    		ensSolPref.add(new ArrayList<String>());
    	}
    	return ensSolPref;
    }
    
}

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
	 * Description: Réalise l'union des valeurs de 2 listes
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
    	System.out.println(ensSolPos);
    	return ensSolPos;
    }
    
    public List<List<List<String>>> getensSolAdm(List<List<List<String>>> ensSolPos){
    	
    	List<List<List<String>>> ensSolAdm =new ArrayList<List<List<String>>>();
    	SolutionPotentielle solPos= new SolutionPotentielle();
    	
    	for(int i =0;i<ensSolPos.size();i++) {
    		List<List<String>> listArg =ensSolPos.get(i);
    		List<List<String>> listArgAdm = new ArrayList<List<String>>();
    		for(int j=0;j<listArg.size();j++) {
    			solPos.setArguments(listArg.get(j));
    			if (solPos.verifnoPrint(debat)) {
    				listArgAdm.add((listArg.get(j)));
    			}
    		}
    		ensSolAdm.add(listArgAdm);
    	}
    	System.out.println(ensSolAdm);
    	return ensSolAdm;
    }
    
    public List<List<List<String>>> getensSolPref(List<List<List<String>>> ensSolAdm){
    	List<List<List<String>>> ensSolPos =new ArrayList<List<List<String>>>();
    	
    	return ensSolPos;
    }
    
}

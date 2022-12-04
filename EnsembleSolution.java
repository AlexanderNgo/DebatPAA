package solutionAuto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import programmeManuelle.DebatManuelle;

public class EnsembleSolution {

	private List<String> ensArg;
	
	public EnsembleSolution(DebatManuelle debat) {
		this.ensArg = debat.getArguments();
	}
	
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
    	
    	for (String arg:ensArg) {
    		ArrayList<String> solPos=new ArrayList<String>();
    		solPos.add(arg);
    		ensSolPos.get(0).add(solPos);
    	}
    	
    	for(int i =0;i<ensArg.size()-1;i++) {
    		List<List<String>> liActuel= ensSolPos.get(i);
    		List<List<String>> liSuiv=new ArrayList<List<String>>();
    		int solmax=comb(ensArg.size(),i+2);
    		int taille= liActuel.size();
    		
    		for (int j=0;j<taille && solmax>liSuiv.size();j++) {
    			List<String> arg1=liActuel.get(j);
    			for(int k=j+1;k<taille;k++) {
    				List<String> arg2 = liActuel.get(k);
    				List<String> nouvArg=union(arg1,arg2);
    				if((!liSuiv.contains(nouvArg)) && (nouvArg.size()==i+2)) {
    					liSuiv.add(nouvArg);
    				}
    			}
    		}
    		
    		ensSolPos.add(liSuiv);
    		System.out.println(liSuiv);
    	}
    	System.out.println(ensSolPos);
    	return ensSolPos;
    }
}

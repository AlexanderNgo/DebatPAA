import java.util.*;
public class Ensemble
{
	private ArrayList<Argument> l; // liste qui représente les arguments
	private ArrayList<Argument> solution; // ensemble des solutions
	public Ensemble()
	{
		l = new ArrayList<Argument>();
		solution = new ArrayList<Argument>();
	}
	public void remplissageArguments()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Combien d'arguments en tout ? :");
		int res = scan.nextInt();
		int i = 0;
		String resChaine,resChaine1;
		while(i < res) // rempli l'arraylist donc la liste d'arguments
		{
			System.out.println("Entrez le nom de l'argument : ");
			resChaine = scan.next();
			ajouterArgument(resChaine,res);
			i++;
		}//fin remplissage
	}
	public void menu()
	{
		int res;
		int indDepuis = -1;
		int indVers = -1;
		int i =0;
		int j = 0;
		int k = 0;
		int m = 0;
		Argument tmp = null;
		String resChaine,resChaine1;
		Scanner scan = new Scanner(System.in);
		System.out.println("1) ajouter une contradiction   2) fin : ");
		res = scan.nextInt();
		while(res > 2 && res <1)//gestion des erreurs d'entrées
		{
			System.out.println("Entrez uniquement une valeur entre 1 et 2 : ");
			res = scan.nextInt();
		}
		while(res !=2)
		{
			System.out.println("Entrez une contradiction entre 2 arguments ");
			System.out.println("Saisir : ");
			resChaine = scan.next();
			System.out.println("Saisir : ");
			resChaine1 = scan.next();
			//parcours de l'arraylist
			for(i = 0;i<l.size();i++)
			{
				if(l.get(i).getArgument().equalsIgnoreCase(resChaine))
				{
					indDepuis = i; // recupere l'indice du contradicteur
				}
				else if(l.get(i).getArgument().equalsIgnoreCase(resChaine1))
				{
					indVers = i; // recupere l'indice de celui qui est contredit
				}
			}
			l.get(indVers).ajouterContradicteur(l.get(indDepuis)); // on ajoute dans le tableau contradicteur[] de celui qui est contredit l'argument qui le contredit
			System.out.println("1) ajouter une contradiction   2) fin. ");
			res = scan.nextInt();
			while(res > 2 && res <1)//gestion des erreurs d'entrées
			{
				System.out.println("Entrez uniquement une valeur entre 1 et 2 : ");
				res = scan.nextInt();
			}
		}//fin de l'ajout des contradictions
		Argument.etablirDefenseur(l);
	} 
	public void menu2() // menu après la fin de l'ajout des contradictions
	{
		Scanner scan = new Scanner(System.in);
		int res;
		String resString;
		System.out.println("1) Ajouter un argument dans la solution 2) retirer un argument de la solution 3) verifier la solution 4) fin : ");
		res = scan.nextInt();
		while(res != 1 && res != 2 && res !=3 && res != 4)//gestion des erreurs d'entrées
		{
			System.out.println("Entrez uniquement une valeur entre 1 et 4 : ");
			res = scan.nextInt();
		}
		while(res != 4)
		{	
			if(res == 1)
			{
				System.out.println("Quel argument ajouter dans l'ensemble des solutions ? :");
				resString = scan.next();
				ajouterArgumentSolution(resString);
			}
			else if(res ==2)
			{
				System.out.println("Quel argument supprimer dans l'ensemble des solutions ? :");
				resString = scan.next();
				supprimerArgumentSolution(resString);
			}
			else if(res == 3)
			{
				boolean admissible = solutionAdmissible(solution,l);
				if(admissible == true)
				{
					System.out.println("La solution est admissible");
				}
				else
				{
					System.out.println("La solution n'est pas admissible");
				}
			}
			System.out.println("1) Ajouter un argument dans la solution  2) retirer un argument dans la solution 3) verifier la solution 4) fin : ");
			res = scan.nextInt();
			while(res != 1 && res != 2 && res !=3 && res != 4)//gestion des erreurs d'entrées
			{
				System.out.println("Entrez uniquement une valeur entre 1 et 4 : ");
				res = scan.nextInt();
			}
		}
		System.out.println("Voici l'ensemble E : ");
		for(int i = 0;i<solution.size();i++)
		{
			System.out.print(solution.get(i).getArgument()+"   ");
		}
	}
	public void afficherArguments()
	{
		for(int i = 0;i<l.size();i++)
		{
			System.out.println("L'argument "+i+" est : "+l.get(i).getArgument());
		}
		System.out.println("Voici les contradictions : ");
		for(int i = 0;i<l.size();i++)
		{
			l.get(i).afficherContradicteur();
		}
	}
	public void ajouterArgument(String s,int n)
	{
		l.add(new Argument(s,n));
	}
	public void ajouterArgumentSolution(String s) // cherche l'argument dans la liste pour la mettre dans la liste des solutions
	{
		for(int i = 0;i<l.size();i++)
		{
			if(l.get(i).getArgument().equalsIgnoreCase(s))
			{
				if(solution.contains(l.get(i)) == false)// verifie que l'argument n'est pas déjà dans la liste des solutions
				{
					solution.add(l.get(i));
				}
				else
				{
					System.out.println("L'argument est deja dans la solution");
				}
			}
		}
	}
	public void supprimerArgumentSolution(String s)
	{
		boolean dedans = false;
		for(int i = 0;i<l.size();i++)
		{
			if(solution.get(i).getArgument().equalsIgnoreCase(s))
			{
				System.out.println("L'argument "+l.get(i).getArgument()+" va etre supprime");
				solution.remove(i);
				dedans = true;
			}
		}
		if(dedans == false)
		{
			System.out.println("L'argument n'est pas present dans la solution");
		}
	}
	public boolean solutionAdmissible(ArrayList<Argument> sol, ArrayList<Argument> e) // e signifie l'ensemble des arguments // NON FONCTIONEL
	{
		int i,j,k;//indices
		Argument tmp = null;
		boolean boolUn = true;
		boolean boolDeux = true;
		boolean parcouru = false;
		//verifier qu'il n'y a pas de contradiction interne (2 args ne se contredisent pas) -> premiere condition
		for(i = 0;i<sol.size();i++)
		{
			tmp = sol.get(i); // prend l'argument i de sol
			for(j = 0;j<sol.size();j++) //parcours de chacun des args de sol
			{
				if(sol.get(j).getContradicteur().length != 0)// si l'arg j de sol admet contradicteur
				{
					for(k = 0;k<sol.get(j).getContradicteur().length;k++) // parcours de chacun de ses contradicteurs 
					{
						if(sol.get(j).getContradicteur()[k] != null)
						{
							if(tmp.getArgument().equalsIgnoreCase(sol.get(j).getContradicteur()[k].getArgument()) == true) 
							// si dans ses contradicteurs il y a un argument de sol -> contradiction interne
							{
								boolUn = false;
							}
						}
					}
				}// et on recommence pour chacun des args de sol
			}
		}
		//2eme condition 
		//chacun des arguments contredit dans E doit être défendu -> chacun de ces arguments ont autant de défenseur que de contradicteurs
		if(parcouru == false )
		{	
			for(i = 0;i<e.size();i++)
			{
				if(e.get(i).getNbDefenseur() < e.get(i).getNbContradicteur())
				{
					System.out.println("Pour "+i+ " Nb def : "+e.get(i).getNbDefenseur() + " nbContradicteur : "+e.get(i).getNbContradicteur());
					boolDeux = false;
				}
			}
			parcouru = true; //on a besoin de parcourir l'ensemble E qu'une seule fois
		}
		if(boolUn == true && boolDeux == true) // return true ssi les 2 conditions sont respectées
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

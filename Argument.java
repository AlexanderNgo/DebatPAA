import java.util.*;

public class Argument // classe argument
{
	//private boolean accepter;
	private String nomArgument;
	private int indDefenseur,indContradicteur;
	private Argument contradicteur[]; // tableau des arguments qui le contredisent
	private Argument defenseur[]; //peut-être pas la peine
	public Argument(String nomArgument,int n)
	{
		this.nomArgument = nomArgument;
		this.contradicteur = new Argument[n];
		this.defenseur = new Argument[n];
		//this.accepter = false;//initialise a false
	}
	/*public String toString()
	{
		return "L'argument est : "+nomArgument;
	}*/
	public String getArgument()
	{
		return nomArgument;
	}
	/*public boolean getAccepter()
	{
		return accepter;
	}
	public boolean estAccepter()
	{
		if(contradicteur.length == 0 || defenseur.length > 0)
		{
			this.accepter = true;
		}
		else 
		{
			this.accepter = false;
		}
		return accepter;

	}*/
	public void ajouterContradicteur(Argument e)
	{
		contradicteur[indContradicteur] = e;
		indContradicteur++;
	}
	public void ajouterDefenseur(Argument e)
	{
		defenseur[indDefenseur] = e;
		indDefenseur++;
	}
	public Argument[] getContradicteur()
	{
		return contradicteur;
	}
	public void afficherContradicteur()
	{
		if(contradicteur.length > 0) // vérif s'il a des contradicteurs
		{
			for(int i = 0;i<contradicteur.length;i++)
			{
				if(contradicteur[i] != null)
				{
					System.out.println(contradicteur[i].getArgument()+" contredit "+ this.getArgument());
				}
			}
		}
	}
	public int getNbContradicteur()
	{
		int nb = 0;
		for(int i = 0;i<contradicteur.length;i++)
		{
			if(contradicteur[i] != null)
			{
				nb++;
			}
		}
		return nb;
	}
	public int getNbDefenseur()
	{
		int nb = 0;
		for(int i = 0;i<defenseur.length;i++)
		{
			if(defenseur[i] != null)
			{
				nb++;
			}
		}
		return nb;
	}
	public ArrayList<Argument> getArgumentContredis(Argument a, ArrayList<Argument> l)
	// retourne une liste d'argument qui contient les arguments contredis par a
	{
		int i,j,k;
		ArrayList<Argument> liste = new ArrayList<Argument>();
		for(i = 0;i<l.size();i++)
		{
			if(l.get(i).getContradicteur().length > 0) // regarde s'il a des contradicteurs
			{
				for(j = 0;j<l.get(i).getContradicteur().length;j++)
				{
					if(l.get(i).getContradicteur()[j] != null)
					{
						if(l.get(i).getContradicteur()[j].getArgument().equalsIgnoreCase(a.getArgument())== true)
							// regarde si l'argument i contient a en contradicteur
						{
							liste.add(l.get(i)); // si oui alors a contredis i et donc on met l'argument i dans l'arraylist
						}
					}
				}
			}
		}
		return liste;
	}
	public static void etablirDefenseur(ArrayList<Argument> ens)
	{
		//on établit les défenseurs
		// idée : pour chaque argument de ens, si un arg 1 contredis un autre arg 2 , alors l'arg 1 devient défenseur des arguments contredis par 2 
		int i =0;
		int j = 0;
		int k = 0;
		int m = 0;
		Argument tmp = null;
		ArrayList<Argument> liste = new ArrayList<Argument>();
		for(i = 0;i<ens.size();i++)
		{
			tmp = ens.get(i);
			for(j = 0;j<ens.size();j++)
			{
				if(ens.get(j).getContradicteur().length > 0)// donc a des contradicteurs
				{
					for(k = 0;k<ens.get(j).getContradicteur().length;k++) 
					{
						if(ens.get(j).getContradicteur()[k] != null)
						{
							if(ens.get(j).getContradicteur()[k].getArgument().equalsIgnoreCase(tmp.getArgument())== true)// cherche si tmp est dans le tableau des contradicteurs
								//regarde si l'argument tmp est dans les contradicteurs de cet argument là et s'il y est alors il est défenseur de l'argument contredis
							{
								liste = ens.get(j).getArgumentContredis(ens.get(j),ens); // recupère la liste des arguments contredit par ens.get(j)
								for(m = 0;m<liste.size();m++)
								{
									liste.get(m).ajouterDefenseur(ens.get(i)); // chacun des args contredit par ens.get(j) ajoute en défenseur l'argument i
								}
							}
						}
					}
				}
			}
		}
	}
}

package interfaceUtilisateur;

import programmeManuelle.DebatManuelle;



import programmeManuelle.SolutionPotentielle;
import SolutionAuto.EnsembleSolution;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Random;

public class Menu {
	
	private Scanner clavier = new Scanner(System.in);
	
	public Menu() {
	}
	
	
	/**
	*	Nom : menuManuelle
	*	@param NONE
	*	@return DebatManuelle
	*	Description : Permet de creer un debat manuellement
	**/
	public DebatManuelle menuManuelle(DebatManuelle debat) {
		/***** 
		**  CREATION DU GRAPHE
		******/
		//System.out.print("Combien d'arguments possede votre debat ? ");
		int nbArguments = saisi("Combien d'arguments possede votre debat ? ");
			
		debat.ajoutArguments(nbArguments);
			
		boolean fin = false;
		int choix;
		do {	
			System.out.println("\n1) ajouter une contradiction");
			System.out.println("2) fin");
			//System.out.print("\nChoisissez parmi les options ci-dessus et entrer une valeur entre 1 et 2 : ");
			choix = saisi("\nChoisissez parmi les options ci-dessus et entrer une valeur entre 1 et 2 : ");
				
			switch(choix) {
				case 1:
					System.out.print("\nEntrez le premier argument qui contredira le second : ");
					String a1 = clavier.next();
					System.out.print("Entrez le second argument qui sera contredit par le premier : ");
					String a2 = clavier.next();
						
					debat.ajoutContradictions(a1, a2);
					debat.afficheDebat();  // AFFICHE LE GRAPHE OBTENUE APRES L'AJOUT DE LA CONTRADICTION (AIDE POUR LES TESTS)
					break;
						
				case 2:
					System.out.println("\nVous avez termine de representer le graphe qui decrit les arguments et les contradictions.");
					fin = true;
					break;
				default:
					System.out.println("\nMauvais choix de nombre");
			}
		}
		while(!fin);
		return debat;
	}
	
	/**
	 * Nom : menuMannelleSol
	 * @param DebatManuelle debat
	 * @return NONE
	 * Description : Methode qui sert a tester des solution d'un debat manuellement
	 */
	public void menuMannelleSol(DebatManuelle debat ) {
		/***** 
		**  SOLUTION POTENTIELLE
		******/
		int choix;
		boolean fin=false;
		SolutionPotentielle ensembleE = new SolutionPotentielle();
		String arg;
		do {
			
			System.out.println("\n1) Ajouter un argument");
			System.out.println("2) Supprimer un argument");
			System.out.println("3) Verifier la solution");
			System.out.println("4) fin");
			
			//System.out.print("\nChoisissez parmi les options ci-dessus et entrer une valeur entre 1 et 4 : ");
			choix = saisi("\nChoisissez parmi les options ci-dessus et entrer une valeur entre 1 et 4 : ");
			
			switch(choix) {
				case 1:
					System.out.println("\nEntrez l'argument a ajouter : ");
					arg = clavier.next();
					if(debat.getGraph().keySet().contains(arg)) {
						ensembleE.add(arg);
					}else {
						System.out.println("\nL'argument "+arg+" n'est pas dans le debat");
					}
					break;
				case 2:
					System.out.println("\nEntrez l'argument a supprimer : ");
					arg = clavier.next();
					if(debat.getGraph().keySet().contains(arg)) {
						ensembleE.remove(arg);
					}else {
						System.out.println("\nL'argument "+arg+" n'est pas dans le debat");
					}
					break;
				case 3:
					if (ensembleE.verif(debat)){
						System.out.println("\n"+ensembleE.toString()+" est une solution admissible");
					}
					break;
				case 4:
					if (ensembleE.verif(debat)){
						System.out.println("\n"+ensembleE.toString()+" est une solution admissible");
					}else {
						System.out.println("\n"+ensembleE.toString()+" n'est pas une solution admissible");
					}
					fin=true;
					break;
				default:
					System.out.println("\nMauvais choix de nombre");
			}
		}while(!fin);
		
	}
	
	
	/**
	*	Nom : menuAutomatique
	*	@param String fichier
	*	@return NONE
	*	Description : Permet de cr�er un d�bat � partir d'un fichier
	**/
	public void menuAutoFichier(String fichier, DebatManuelle debat) {
		 
		String ligne;                               // Va stocker chaque ligne du fichier
		int nb = 0;                                 // Variable enti�re � incr�menter � chaque nouvelle ligne pour indiquer quel ligne est responsable d'une exception
		String argument1, argument2;                // Pour stocker les arguments donn� par le fichier
		
		try (FileReader fReader = new FileReader(fichier);            // Ouverture du fichier avec FileReader
			BufferedReader bReader = new BufferedReader(fReader);){   // Ouverture du fichier avec BufferedReader pour mieux le manipuler
			
			ArrayList<String> tabArg = new ArrayList<String>();       // Pour stocker les noms des arguments et v�rifier si 2 arguments ont d�ja �t� ajout� au d�bat avant une contradiction
			
			while((ligne = bReader.readLine()) != null) {             // Tant que le fichier contient des lignes
				
				nb++;                                                 // Nombre de ligne
				
				if( ligne.startsWith("argument(") && ligne.endsWith(").") ) {       // SI C'EST POUR L'AJOUT D'UN ARGUMENT
					
					//[
					String[] t1, t2, t3;                     
					t1 = ligne.split("\\(");
					t2 = ligne.split("\\)");
					t3 = ligne.split(",");
					
					if (t1.length != 2 || t2.length != 2 || t3.length != 1) {
						throw new IOException("Ligne "+nb+", le nom de l'argument ne doit pas contenir \",\" ni \"(\" ni \")\"");
					}
					//] On v�rifie que l'argument ne contient pas de "(", de ")", ou de ","
					
					//[
					StringTokenizer st = new StringTokenizer(ligne,"()");
					st.nextToken();
					argument1 = st.nextToken().toString();
					//] On s�lectionne le nom de l'argument
					
					argument1 = argument1.trim();                                             // supprime les espaces entre les parenth�ses et le nom de l'argument
					
					if (argument1.equals("argument") || argument1.equals("contradiction")) {  // Le nom de l'argument ne doit pas �tre ni "argument" ni "contradiction"
						throw new IOException("Ligne "+nb+", le nom de l'argument ne doit pas �tre �gale � \"argument\" ou \"contradiction\"");
					}
					
					debat.ajoutArgumentsString(argument1);                                    // On ajoute l'argument au d�but
					
					tabArg.add(argument1);
					
					//Debat.afficheDebat();                                                         // (� utiliser pour tester le programme) affiche les �tapes de cr�ation du d�bat cr�er � partir du fichier
					
				}else if( ligne.startsWith("contradiction(") && ligne.endsWith(").") && ligne.contains(",") ) {      // SI C'EST POUR AJOUTER UNE CONTRADICTION
					
					//[
					String[] t1, t2, t3;                     
					t1 = ligne.split("\\(");
					t2 = ligne.split("\\)");
					t3 = ligne.split(",");
					
					if (t1.length != 2 || t2.length != 2 || t3.length != 2) {
						throw new IOException("Ligne "+nb+", le nom des arguments dans la contracdiction ne doivent pas contenir de \",\" ni de \"(\" ni de \")\"");
					}
					
					//] On v�rifie qu'aucun argument de la contradiction ne contient  de "(",et de ")". Et on v�rifie qu'il y a bien une virgule
					
					//[
					StringTokenizer st1 = new StringTokenizer(ligne,"()");
					st1.nextToken();
					argument1 = st1.nextToken().toString();
					
					t1 = argument1.split(",");
					
					if (t1.length==0 || t1[0].length() == 0 || t1.length==1) {
						throw new IOException("Ligne "+nb+", il manque un ou plusieurs arguments dans la contradiction");
					}
					//] On v�rifie si une contradiction contient 2 noms
					
					//[
					StringTokenizer st2 = new StringTokenizer(ligne,"(,)");
					st2.nextToken();
					argument1 = st2.nextToken().toString();
					argument2 = st2.nextToken().toString();
					//] On s�lectionne le nom des 2 arguments
					
					argument1.trim();                          // supprime les espaces entre les parenth�ses et le nom de l'argument                                              
					argument2.trim();
					
					if (argument1.equals("argument") || argument1.equals("contradiction")) {   // Le nom de l'argument ne doit pas �tre ni "argument" ni "contradiction"
						throw new IOException("Ligne "+nb+", le nom de l'argument ne doit pas �tre �gale � \"argument\" ou \"contradiction\"");
					}else if (argument2.equals("argument") || argument2.equals("contradiction")) {
						throw new IOException("Ligne "+nb+", le nom de l'argument ne doit pas �tre �gale � \"argument\" ou \"contradiction\"");
					}	
					
					if (!tabArg.contains(argument1)) {                                         // V�rifie si si un argument n�est pas d�fini, ou est d�fini apr�s avoir �t� utilis� dans une contradiction
						throw new IOException("Ligne "+nb+", l'argument "+argument1+" n'a pas encore �t� ajout� au d�bat");         
					}else if (!tabArg.contains(argument2)) {
						throw new IOException("Ligne "+nb+", l'argument "+argument2+" n'a pas encore �t� ajout� au d�bat");         
					}
					
					debat.ajoutContradictions(argument1, argument2);
					
				}else {
					throw new IOException("Ligne "+nb+", probl�me de mise en forme");         // LE FICHIER N'EST PAS DE LA BONNE FORME
				}
			}
		} catch (IllegalArgumentException iae) {
			System.err.println(iae.getMessage());
			clavier.close();
			
		} catch (FileNotFoundException fnfe) {
			System.err.println("Le fichier sp�cifi� est introuvable");
			clavier.close();
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			clavier.close();
	    }
		
		
		//Debat.afficheDebat();                                // (� utiliser pour tester le programme) affiche les �tapes de cr�ation du d�bat cr�er � partir du fichier
	}
	/**
	 * Nom : menuAutoSol
	 * @param DebatManuelle debat
	 * @return NONE
	 * Description : Menu qui propose les 4 options demandées
	 */
	
	public void menuAutoSolu(DebatManuelle debat) {
		// Partie affichage menu et recherche d�une solution admissible, ou pr�f�r�e.
		//TODO
		EnsembleSolution ensSol=new EnsembleSolution(debat);
		List<List<List<String>>> ensSolPos=ensSol.getEnsSolPos();
		List<List<String>> ensSolAdm =ensSol.getEnsSolAdm(ensSolPos);
		List<List<String>> ensSolPref=ensSol.getensSolPref(ensSolAdm);
		List<String> derniereSol = null;
		int rep = 0;
		int res;
		int nombreAppelFonctionAdm = -1;
		int nombreAppelFonctionPref = -1;
		do
		{
			//System.out.println("1) chercher une solution admissible 2) chercher une solution preferee 3) sauvegarder la solution 4) fin \n");
			res = saisi("1) chercher une solution admissible 2) chercher une solution preferee 3) sauvegarder la solution 4) fin ");
			while(res < 1 || res > 4)
			{
				//System.out.println("Entrez une valeur entre 1 et 4 seulement : \n");
				res = saisi("Entrez une valeur entre 1 et 4 seulement : ");
			}
			switch(res)
			{
				case 1 : 
					if(existSoluAdm(ensSolAdm)==false)
					{
						System.out.println("Il n'existe pas de solution Admissible");
					}
					else
					{
						nombreAppelFonctionAdm = ++nombreAppelFonctionAdm;
						derniereSol = getUneSolution(ensSolAdm,nombreAppelFonctionAdm);
						if(nombreAppelFonctionAdm >= derniereSol.size())
						{
							nombreAppelFonctionAdm = -1;
						}
						rep = 1;
						for(int i = 0;i< derniereSol.size();i++)
						{
							System.out.print(derniereSol.get(i)+"  ");
						}
						System.out.println("\n");
						//System.out.println("Voici une solution Admissible : "+derniereSol);
					}
					break;
				case 2 :
					if(existSoluPref(ensSolAdm)==false)
					{
						System.out.println("Il n'existe pas de solution Preferee");
					}
					else
					{
						nombreAppelFonctionPref = ++nombreAppelFonctionPref;
						derniereSol = getUneSolution(ensSolPref,nombreAppelFonctionPref);
						if(nombreAppelFonctionPref >= derniereSol.size())
						{
							nombreAppelFonctionPref= -1;
						}
						rep = 2;
						for(int i = 0;i< derniereSol.size();i++)
						{
							System.out.print(derniereSol.get(i)+"  ");
						}
						System.out.println("\n");
						//System.out.println("Voici une solution Preferee : "+derniereSol);
					}
					break;
				case 3 :
					System.out.println("On sauvegardera la derniere solution montree");
					if(rep == 2 && existSoluPref(ensSolPref) == true) // verif qu'il y a bien une solution pref (sinon sauvegarder quand meme mais fichier vide)
					{
						String nomFichier = "SauvegardeSolu.txt";
						sauvegarderSolu(nomFichier,derniereSol);
					}
					else if(rep == 1 && existSoluAdm(ensSolAdm)==true) // rep == 1 implique que la derniere sol montrée était une sol adm
					{
						String nomFichier = "SauvegardeSolu.txt";
						sauvegarderSolu(nomFichier,derniereSol);
					}
					break;
				case 4 : 
					System.out.println("Fin du programme \n");
					break;
				default : 
					break;
			}
		}
		while(res !=4);
		
	}
	/**
	 * Nom : existSoluAdm
	 * @param List<List<String>>
	 * @return boolean
	 * Description : Vérifie s'il existe une solution admissible
	 */
	public boolean existSoluAdm(List<List<String>> sol)
	{
		if(sol.isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/**
	 * Nom : existSoluPref
	 * @param List<List<String>>
	 * @return boolean
	 * Description : Vérifie s'il existe une solution préférée
	 */
	public boolean existSoluPref(List<List<String>> sol)
	{
		if(sol.isEmpty())
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	/**
	 * Nom : getUneSolution
	 * @param List<List<String>>
	 * @return List<String>
	 * Description : Retourne UNE solution parmi l'ensemble des solutions (hormis l'ensemble vide)
	 */
	public List<String> getUneSolution(List<List<String>> sol,int indice)
	{
		//pas nécessaire de vérifier l'existance de l'ensemble car ça sera fait lors de l'appel
		//s'il y a que l'ensemble vide comme solution :
		//System.out.println("Voici l'ensemble des solutions possibles : "+sol); // à mettre en commentaire !!
		/*if(sol.size() == 1) //forcément l'ensemble vide
		{
			return sol.get(0);
		}*/
		/*else
		{
			Random random = new Random();
			int res = random.nextInt((sol.size()-1)+1);
			while(sol.get(res).size() == 0)// si on tombe sur l'ensemble vide, on continue à chercher une autre ensemble
			{
				res = random.nextInt((sol.size()-1)+1); // recommence un nouveau nombre aléatoire
			}
			return sol.get(res);
		}*/
		if(indice >= sol.size())
		{
			indice = 0;
		}
		return sol.get(indice);
	}
	/*public String afficheSoluAdm(List<List<String>> sol)
	{
		if(sol.isEmpty())
		{
			return "Pas de solution admissible\n";
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			for(int i = 0;i<sol.size();i++) //Si plusieurs élement dans les sous liste rajouter une boucle for pour la parcourir
			{
				sb.append(sol.get(i).get(0)+", ");
			}
			sb.append("}");
			return sb.toString();
		}
	}
	public String afficheSoluPref(List<List<String>> sol)
	{
		if(sol.isEmpty())
		{
			return "Pas de solution preferee\n";
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append("{ ");
			for(int i = 0;i<sol.size();i++)
			{
				sb.append(sol.get(i).get(0)+", "); //Si plusieurs élement dans les sous liste rajouter une boucle for pour la parcourir
			}
			sb.append("}");
			return sb.toString();
		}
	}*/
	/**
	 * Nom : sauvegarderSolu
	 * @param String,List<List<String>>
	 * @return NONE
	 * Description : Sauvegarde dans le fichier nomFichier la dernière solution montrée (adm ou pref)
	 */
	public void sauvegarderSolu(String nomFichier,List<String> sol)
	{
		try
		{
			File f = new File(nomFichier);
			f.createNewFile();
			if(f.exists()) // vérif que le fichier a bien été crée
			{
				FileWriter fw = new FileWriter(f);
				for(int i = 0;i<sol.size();i++)
				{
					fw.write(sol.get(i)+"  ");
				}
				//fw.write("Arguments : "+sol);
				System.out.println("L'ensemble "+sol+" a ete sauvegarder dans le fichier : "+nomFichier);
				fw.close();
			}
			else
			{
				System.out.println("Erreur dans la creation du fichier ");
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
	}
	/**
	 * Nom : saisi
	 * @param String
	 * @return int
	 * Description : Methode qui permet une saisie et qui gère les erreurs de types
	 */
	public int saisi(String s)
	{
		boolean erreur;
		int valeur = 0;
		do
		{
			clavier = new Scanner(System.in); //redéclare car sinon boucle infini
			try
			{
				erreur = false;
				System.out.println(s);
				valeur = clavier.nextInt();
			}
			catch(InputMismatchException e)
			{
				e = new InputMismatchException("Erreur de type, veuillez entrer un entier ");
				System.out.println(e.getMessage());
				erreur = true;
			}
		}
		while(erreur);
		return valeur;
	}
}
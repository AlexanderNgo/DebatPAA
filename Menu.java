package interfaceUtilisateur;

import programmeManuelle.Debat;
import programmeManuelle.SolutionPotentielle;
import solutionAuto.EnsembleSolution;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileWriter;
import java.util.InputMismatchException;

public class Menu {
	
	private Scanner clavier = new Scanner(System.in);
	
	public Menu() //constructeur par defaut
	{
	}
	
	
	/**
	*	Nom : menuManuelle
	*	@param NONE
	*	@return DebatManuelle
	*	Description : Permet de creer un debat manuellement
	**/
	public Debat menuManuelle(Debat debat) {
		/***** 
		**  CREATION DU GRAPHE
		******/
		int nbArguments = saisi("Combien d'arguments possede votre debat ? ");
			
		debat.ajoutArguments(nbArguments);
			
		boolean fin = false;
		int choix;
		do {	
			System.out.println("\n1) ajouter une contradiction");
			System.out.println("2) fin");
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
	 * @param Debat debat
	 * @return NONE
	 * Description : Methode qui sert a tester des solution d'un debat manuellement
	 */
	public void menuMannelleSol(Debat debat ) { // fonction qui affichait le menu demande lors de la première phase
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
	*	Description : Permet de creer un debat e partir d'un fichier
	**/
	public void menuAutoFichier(String fichier, Debat debat) throws IOException
	{
		 
		String ligne;                               // Va stocker chaque ligne du fichier
		int nb = 0;                                 // Variable entiere e incrementer e chaque nouvelle ligne pour indiquer quel ligne est responsable d'une exception
		String argument1, argument2;                // Pour stocker les arguments donne par le fichier
		
			FileReader fReader = new FileReader(fichier);            // Ouverture du fichier avec FileReader
			BufferedReader bReader = new BufferedReader(fReader);{   // Ouverture du fichier avec BufferedReader pour mieux le manipuler
			
			ArrayList<String> tabArg = new ArrayList<String>();       // Pour stocker les noms des arguments et verifier si 2 arguments ont deja ete ajoute au debat avant une contradiction
			
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
					//] On verifie que l'argument ne contient pas de "(", de ")", ou de ","
					
					//[
					StringTokenizer st = new StringTokenizer(ligne,"()");
					st.nextToken();
					argument1 = st.nextToken().toString();
					//] On selectionne le nom de l'argument
					
					argument1 = argument1.trim();                                             // supprime les espaces entre les parentheses et le nom de l'argument
					
					if (argument1.equals("argument") || argument1.equals("contradiction")) {  // Le nom de l'argument ne doit pas etre ni "argument" ni "contradiction"
						throw new IOException("Ligne "+nb+", le nom de l'argument ne doit pas etre egale e \"argument\" ou \"contradiction\"");
					}
					
					debat.ajoutArgumentsString(argument1);                                    // On ajoute l'argument au debut
					
					tabArg.add(argument1);
					
					//Debat.afficheDebat();                                                         // (e utiliser pour tester le programme) affiche les etapes de creation du debat creer e partir du fichier
					
				}else if( ligne.startsWith("contradiction(") && ligne.endsWith(").") && ligne.contains(",") ) {      // SI C'EST POUR AJOUTER UNE CONTRADICTION
					
					//[
					String[] t1, t2, t3;                     
					t1 = ligne.split("\\(");
					t2 = ligne.split("\\)");
					t3 = ligne.split(",");
					
					if (t1.length != 2 || t2.length != 2 || t3.length != 2) {
						throw new IOException("Ligne "+nb+", le nom des arguments dans la contracdiction ne doivent pas contenir de \",\" ni de \"(\" ni de \")\"");
					}
					
					//] On verifie qu'aucun argument de la contradiction ne contient  de "(",et de ")". Et on verifie qu'il y a bien une virgule
					
					//[
					StringTokenizer st1 = new StringTokenizer(ligne,"()");
					st1.nextToken();
					argument1 = st1.nextToken().toString();
					
					t1 = argument1.split(",");
					
					if (t1.length==0 || t1[0].length() == 0 || t1.length==1) {
						throw new IOException("Ligne "+nb+", il manque un ou plusieurs arguments dans la contradiction");
					}
					//] On verifie si une contradiction contient 2 noms
					
					//[
					StringTokenizer st2 = new StringTokenizer(ligne,"(,)");
					st2.nextToken();
					argument1 = st2.nextToken().toString();
					argument2 = st2.nextToken().toString();
					//] On selectionne le nom des 2 arguments
					
					argument1.trim();                          // supprime les espaces entre les parentheses et le nom de l'argument                                              
					argument2.trim();
					
					if (argument1.equals("argument") || argument1.equals("contradiction")) {   // Le nom de l'argument ne doit pas etre ni "argument" ni "contradiction"
						throw new IOException("Ligne "+nb+", le nom de l'argument ne doit pas etre egale e \"argument\" ou \"contradiction\"");
					}else if (argument2.equals("argument") || argument2.equals("contradiction")) {
						throw new IOException("Ligne "+nb+", le nom de l'argument ne doit pas etre egale e \"argument\" ou \"contradiction\"");
					}	
					
					if (!tabArg.contains(argument1)) {                                         // Verifie si si un argument neest pas defini, ou est defini apres avoir ete utilise dans une contradiction
						throw new IOException("Ligne "+nb+", l'argument "+argument1+" n'a pas encore ete ajoute au debat");         
					}else if (!tabArg.contains(argument2)) {
						throw new IOException("Ligne "+nb+", l'argument "+argument2+" n'a pas encore ete ajoute au debat");         
					}
					debat.ajoutContradictions(argument1, argument2);
					
				}else {
					throw new IOException("Ligne "+nb+", probleme de mise en forme");         // LE FICHIER N'EST PAS DE LA BONNE FORME
				}
			}
			bReader.close();
		} 
	}
	/**
	 * Nom : menuAutoSol
	 * @param Debat debat
	 * @return NONE
	 * Description : Menu qui propose les 4 options demandees
	 */
	
	public void menuAutoSolu(Debat debat) {

		EnsembleSolution ensSol=new EnsembleSolution(debat);
		List<List<List<String>>> ensSolPos=ensSol.getEnsSolPos();
		List<List<String>> ensSolAdm =ensSol.getEnsSolAdm(ensSolPos);
		List<List<String>> ensSolPref=ensSol.getensSolPref(ensSolAdm);
		List<String> derniereSol = null;
		int rep = 0; // pour savoir quel est la derniere sol
		int res;//resultat de la saisie
		int nombreAppelFonctionAdm = 0; //compteur du nombre de fois où l'on appel la fonction getUneSolution
		int nombreAppelFonctionPref = 0;
		do
		{
			res = saisi("1) chercher une solution admissible 2) chercher une solution preferee 3) sauvegarder la solution 4) fin ");
			while(res < 1 || res > 4)
			{
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
						derniereSol = getUneSolution(ensSolAdm,nombreAppelFonctionAdm);
						rep = 1;
						for(int i = 0;i< derniereSol.size();i++)
						{
							if(i == derniereSol.size()-1)
							{
								System.out.println(derniereSol.get(i));
							}
							else
								System.out.print(derniereSol.get(i)+",");
						}
						nombreAppelFonctionAdm++;
						if(nombreAppelFonctionAdm >= ensSolAdm.size())
						{
							nombreAppelFonctionAdm = 0;
						}
						System.out.println();
					}
					break;
				case 2 :
					if(existSoluPref(ensSolAdm)==false)
					{
						System.out.println("Il n'existe pas de solution Preferee");
					}
					else
					{
						derniereSol = getUneSolution(ensSolPref,nombreAppelFonctionPref);
						if(nombreAppelFonctionPref >= ensSolPref.size())
						{
							nombreAppelFonctionPref= -1;
						}
						rep = 2;
						for(int i = 0;i< derniereSol.size();i++)
						{
							if(i == derniereSol.size()-1)
							{
								System.out.println(derniereSol.get(i));
							}
							else
								System.out.print(derniereSol.get(i)+",");
						}
						System.out.println();
						nombreAppelFonctionPref++;
						if(nombreAppelFonctionPref >= ensSolPref.size())
						{
							nombreAppelFonctionPref= 0;
						}
					}
					break;
				case 3 :
					System.out.println("On sauvegardera la derniere solution montree");
					String nomFichier = "SauvegardeSolu.txt"; //on suppose qu'on sauvegarde tout dans un seul et même fichier en ne gardant que la derniere solution
					if(rep == 2 && existSoluPref(ensSolPref) == true) // verif qu'il y a bien une solution pref (sinon sauvegarder quand meme mais fichier vide)
					{
						
						sauvegarderSolu(nomFichier,derniereSol);
					}
					else if(rep == 1 && existSoluAdm(ensSolAdm)==true) // rep == 1 implique que la derniere sol montree etait une sol adm
					{
						
						sauvegarderSolu(nomFichier,derniereSol);
					}
					break;
				case 4 : 
					System.out.println("Fin du programme ");
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
	 * Description : Verifie s'il existe une solution admissible
	 */
	private boolean existSoluAdm(List<List<String>> sol)
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
	 * Description : Verifie s'il existe une solution preferee
	 */
	private boolean existSoluPref(List<List<String>> sol)
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
	 * Description : Retourne UNE solution parmi l'ensemble des solutions 
	 */
	private List<String> getUneSolution(List<List<String>> sol,int indice)
	{
		return sol.get(indice);
	}
	
	/**
	 * Nom : sauvegarderSolu
	 * @param String,List<List<String>>
	 * @return NONE
	 * Description : Sauvegarde dans le fichier nomFichier la solution (adm ou pref)
	 */
	
	private void sauvegarderSolu(String nomFichier,List<String> sol)
	{
		try
		{
			File f = new File(nomFichier);
			f.createNewFile();
			if(f.exists()) // verif que le fichier a bien ete cree
			{
				FileWriter fw = new FileWriter(f);
				for(int i = 0;i<sol.size();i++)
				{
					if(i == sol.size()-1)//afin de supprime la virgule lorsque c'est le dernier argument de l'ensemble
					{
						fw.write(sol.get(i));
					}
					else
						fw.write(sol.get(i)+",");
				}
				System.out.println("La solution "+sol+" a ete sauvegardee dans le fichier : "+nomFichier);
				fw.close();
			}
			else
			{
				System.out.println("Erreur dans la creation du fichier ");
			}
		}
		catch(FileNotFoundException e)
		{
			e = new FileNotFoundException("Le fichier n'a pas pu etre trouve");
			System.out.println(e.getMessage());
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
	}
	
	/**
	 * Methode qui permet une saisie et qui gere les erreurs de types
	 * @param String s
	 * @return int
	 */
	public int saisi(String s) // s correspond aux phrases conçu pour l'utilisateur
	{
		boolean erreur;
		int valeur = 0;
		do
		{
			clavier = new Scanner(System.in); //redeclare car sinon boucle infini
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

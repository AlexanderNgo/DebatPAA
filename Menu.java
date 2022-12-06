package interfaceUtilisateur;

import programmeManuelle.DebatManuelle;
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
		System.out.print("Combien d'arguments possede votre debat ? ");
		int nbArguments = clavier.nextInt();
		
		debat.ajoutArguments(nbArguments);
		
		boolean fin = false;
		int choix;
		do {	
			System.out.println("\n1) ajouter une contradiction");
			System.out.println("2) fin");
			System.out.print("\nChoisissez parmi les options ci-dessus et entrer une valeur entre 1 et 2 : ");
			choix = clavier.nextInt();
			
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
		}while(!fin);
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
			
			System.out.print("\nChoisissez parmi les options ci-dessus et entrer une valeur entre 1 et 4 : ");
			choix =clavier.nextInt();
			
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
	
	public void menuAutoSolu(DebatManuelle debat) {
		// Partie affichage menu et recherche d�une solution admissible, ou pr�f�r�e.
		//TODO
		EnsembleSolution ensSol=new EnsembleSolution(debat);
		List<List<List<String>>> ensSolPos=ensSol.getEnsSolPos();
		List<List<String>> ensSolAdm =ensSol.getEnsSolAdm(ensSolPos);
		List<List<String>> ensSolPref=ensSol.getensSolPref(ensSolAdm);
		
		
	}
}

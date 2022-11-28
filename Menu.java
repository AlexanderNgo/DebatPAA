package interfaceUtilisateur;

import programmeManuelle.DebatManuelle;
import programmeManuelle.SolutionPotentielle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Menu {
	
	public Menu() {
	}
	
	/**
	*	Nom : menuManuelle
	*	@param NONE
	*	@return NONE
	*	Description : Permet de créer un débat et de tester si des ensembles d'arguments sont admissibles ou pas
	**/
	public void menuManuelle() {
		DebatManuelle debat = new DebatManuelle();
		Scanner clavier = new Scanner(System.in);
		
		
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
		
		
		/***** 
		**  SOLUTION POTENTIELLE
		******/
		fin=false;
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
		
		clavier.close();
	}
	
	/**
	*	Nom : menuAutomatique
	*	@param String fichier
	*	@return NONE
	*	Description : Permet de créer un débat à partir d'un fichier, de trouver des solutions admissibles et préférées, et de sauvegarder les solutions
	**/
	public void menuAutomatique(String fichier) {
		
		DebatManuelle Debat = new DebatManuelle();  // Pour créer le débat à partir du fichier donné en paramètre
		Scanner clavier = new Scanner(System.in);   
		String ligne;                               // Va stocker chaque ligne du fichier
		int nb = 0;                                 // Variable entière à incrémenter à chaque nouvelle ligne pour indiquer quel ligne est responsable d'une exception
		String argument1, argument2;                // Pour stocker les arguments donné par le fichier
		
		try (FileReader fReader = new FileReader(fichier);            // Ouverture du fichier avec FileReader
			BufferedReader bReader = new BufferedReader(fReader);){   // Ouverture du fichier avec BufferedReader pour mieux le manipuler
			
			while((ligne = bReader.readLine()) != null) {             // Tant que le fichier contient des lignes
				
				nb++;                                                 // Nombre de ligne
				
				if( ligne.startsWith("argument(") && ligne.endsWith(").") ) {       // SI C'EST POUR L'AJOUT D'UN ARGUMENT
					
					//[
					String[] t1, t2, t3;                     
					t1 = ligne.split("\\(");
					t2 = ligne.split("\\)");
					t3 = ligne.split(",");
					
					if (t1.length != 2 || t2.length != 2 || t3.length != 1) {
						throw new IOException("Ligne "+nb+" le nom de l'argument ne doit pas contenir \",\" ni \"(\" ni \")\"");
					}
					//] On vérifie que l'argument ne contient pas de "(", ")", ","
					
					//[
					StringTokenizer st = new StringTokenizer(ligne,"()");
					st.nextToken();
					argument1 = st.nextToken().toString();
					//] On sélectionne le nom de l'argument
					
					argument1 = argument1.replaceAll("\\s", "");   // On supprime les espaces et tabulation entre les parenthèses et dans le nom de l'argument
					
					if (argument1.equals("argument") || argument1.equals("contradiction")) {                                                   // Le nom de l'argument ne doit pas être ni "argument" ni "contradiction"
						throw new IOException("Ligne "+nb+" le nom de l'argument ne doit pas être égale à \"argument\" ou \"contradiction\"");
					}
					
					Debat.ajoutArgumentsString(argument1);   // On ajoute l'argument au début
					
					//Debat.afficheDebat(); pour les tests
					
				}else if( ligne.startsWith("contradiction(") && ligne.endsWith(").") ) {    // SI C'EST POUR AJOUTER UNE CONTRADICTION
					
				}else {
					throw new IOException("Ligne "+nb+" problème de mise en forme");        // LE FICHIER N'EST PAS DE LA BONNE FORME
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("Le fichier spécifié est introuvable");
			clavier.close();
		} catch (IOException e) {
			e.printStackTrace();
			clavier.close();
		} 		
		
		clavier.close();
	}
}

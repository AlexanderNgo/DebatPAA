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
	
	public void menuAutomatique(String fichier) {
		
		DebatManuelle Debat = new DebatManuelle();
		Scanner clavier = new Scanner(System.in);
		String ligne;
		int nb = 0;
		String argument1, argument2;
		
		try {
			FileReader fReader = new FileReader(fichier);
			BufferedReader bReader = new BufferedReader(fReader);
			
			while((ligne = bReader.readLine()) != null) {
				nb++;
				if( ligne.startsWith("argument(") && ligne.endsWith(").") ) {
					StringTokenizer st = new StringTokenizer(ligne,"()");
					st.nextToken();
					argument1 = st.nextToken().toString();
					
					StringBuffer a = new StringBuffer(argument1);
					for(int i = 0; i <a.length(); i++) {
						if (a.charAt(i) == ' ') {
							a.deleteCharAt(i);
						}
					}
					argument1 = a.toString();
					
					if (argument1 == "argument" || argument1 == "contradiction" || argument1 == "," || argument1 == "(") {
						throw new NomArgumentException();
					}
	
					
				}else if( ligne.startsWith("contradiction(") && ligne.endsWith(").") ) {
					
				}else {
					throw new IOException("Problème de mise en forme ligne "+nb);
				}
			}
		} catch (FileNotFoundException fnfe) {
			System.err.println("Le fichier spécifié est introuvable");
		} catch (IOException e) {
			System.err.println("Problème de ligne");
		} catch (NomArgumentException nae) {
			
		}
		
		
		
		
		
	}
}

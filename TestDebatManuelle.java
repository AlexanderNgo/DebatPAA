import java.util.Scanner;

public class TestDebatManuelle {
	
	public static void main(String[] args) {
		
		DebatManuelle debat = new DebatManuelle();
		Scanner clavier = new Scanner(System.in);
		
		System.out.println("Combien d'arguments possèdent votre débat ? ");
		int nbArguments = clavier.nextInt();
		
		debat.ajoutArguments(nbArguments);
		
		boolean fin = false;
		int choix;
		do {	
			System.out.println("1) ajouter une contradiction\n");
			System.out.println("2) fin");
			
			choix = clavier.nextInt();
			
			switch(choix) {
				case 1:
					System.out.println("Entrez le premier argument qui contredira le second : ");
					String a1 = clavier.nextLine();
					System.out.println("Entrez le second argument qui sera contredit par le premier : ");
					String a2 = clavier.nextLine();
					
					debat.ajoutContradictions(a1, a2);
					break;
					
				case 2:
					System.out.println("Vous avez terminé de représenter le graphe qui décrit les arguments et les contradictions");
					fin = true;
					break;
			}
		}while(!fin);
		
		fin=false;
		SolutionPotentielle ensembleE = new SolutionPotentielle();
		String arg;
		do {
			
			System.out.println("1) Ajouter un argument");
			System.out.println("2) Supprimer un argument");
			System.out.println("3) Vérifier la solution");
			System.out.println("4) fin");
			
			do {
				System.out.println("Entrez un valeur entre 1 et 4");
				choix =clavier.nextInt();
			}while( 0>choix || choix>4 );
			
			switch(choix) {
				case 1:
					System.out.println("Entrez l'argument a ajouter : ");
					arg = clavier.nextLine();
					ensembleE.add(arg);
					break;
				case 2:
					System.out.println("Entrez l'argument a supprimer : ");
					arg = clavier.nextLine();
					ensembleE.remove(arg);
					break;
				case 3:
					if (ensembleE.verif(debat)){
						System.out.println(ensembleE.toString()+"est une solution admissible");
					}else {
						//TODO
					}
					break;
				case 4:
					if (ensembleE.verif(debat)){
						System.out.println(ensembleE.toString()+"est une solution admissible");
					}else {
						System.out.println(ensembleE.toString()+"n'est une solution admissible");
					}
					fin=true;
					break;
				default:
					System.out.println("Mauvais choix de nombre");
			}
		}while(!fin);
		
		clavier.close();
	}
}

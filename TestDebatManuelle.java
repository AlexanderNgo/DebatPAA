package programmeManuelle;

import interfaceUtilisateur.Menu;


public class TestDebatManuelle {
	
	public static void main(String[] args) {
		Menu m = new Menu();                        // Classe pour afficher les différentes parties du menu
		DebatManuelle Debat = new DebatManuelle();  // Pour créer le débat à partir du fichier donné en paramètre de menuAutoFichier
		
		m.menuAutoFichier(args[0], Debat);
		m.menuAutoSolu(Debat);
	}	
		
}
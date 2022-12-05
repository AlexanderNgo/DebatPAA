package programmeManuelle;

import interfaceUtilisateur.Menu;


public class TestDebatManuelle {
	
	public static void main(String[] args) {
		Menu m = new Menu();                        // Classe pour afficher les diff�rentes parties du menu
		DebatManuelle debat = new DebatManuelle();  // Pour cr�er le d�bat � partir du fichier donn� en param�tre de menuAutoFichier
		debat.ajoutArguments(5);
		//m.menuAutoFichier(args[0], debat);
		m.menuAutoSolu(debat);
	}	
		
}
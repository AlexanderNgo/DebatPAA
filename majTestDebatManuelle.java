package programmeManuelle;

import interfaceUtilisateur.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;


public class TestDebatManuelle {
	
	public static void main(String[] args) {
		try
		{
			Menu m = new Menu();                        // Classe pour afficher les diff�rentes parties du menu
			DebatManuelle debat =new DebatManuelle();  // Pour cr�er le d�bat � partir du fichier donn� en param�tre de menuAutoFichier
			Scanner scan = new Scanner(System.in);
			int res;
			System.out.println("1) remplir le graphe manuellement 2) automatiquement ? : \n");
			res = scan.nextInt();
			while(res != 1 && res !=2)
			{
				System.out.println("Entrez soit 1 soit 2 : \n");
				res = scan.nextInt();
			}
			if(res == 1)
				m.menuManuelle(debat);
			else if(res == 2)
				m.menuAutoFichier(args[0], debat);
			//m.menuMannelleSol(debat);
			m.menuAutoSolu(debat);
			scan.close();
		}
		catch(InputMismatchException e)
		{
			e = new InputMismatchException("Erreur, veuillez entrer un nombre entier lors de la saisie");
			System.out.println(e.getMessage());
		}
	}	
		
}

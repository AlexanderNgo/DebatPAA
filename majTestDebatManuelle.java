package programmeManuelle;

import interfaceUtilisateur.Menu;

import java.io.File;
import java.util.Scanner;


public class TestDebatManuelle 
{
	
	public static void main(String[] args) 
	{
		Menu m = new Menu();                        // Classe pour afficher les diff�rentes parties du menu
		DebatManuelle debat =new DebatManuelle();  // Pour cr�er le d�bat � partir du fichier donn� en param�tre de menuAutoFichier
		Scanner scan = new Scanner(System.in);
		int res;
		//System.out.println("1) remplir le graphe manuellement 2) automatiquement ? : \n");
		res = m.saisi("1) remplir le graphe manuellement 2) automatiquement ? : \n");
		while(res != 1 && res !=2)
		{
			//System.out.println("Entrez soit 1 soit 2 : \n");
			res = m.saisi("Entrez soit 1 soit 2 : \n");
		}
		if(res == 1)
		{
			m.menuManuelle(debat);
			m.menuAutoSolu(debat);
		}
		else if(res == 2)
		{
			m.menuAutoFichier(args[0], debat);
			File f = new File(args[0]);
			if(f.exists()) //vérifier que le fichier existe avant d'exécuter le menu
				m.menuAutoSolu(debat);
		}
		scan.close();
	}	
		
}

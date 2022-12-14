package programmeManuelle;

import interfaceUtilisateur.Menu;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class TestDebatManuelle 
{
	
	public static void main(String[] args) 
	{
		Menu m = new Menu();                        // Classe pour afficher les diff�rentes parties du menu
		DebatManuelle debat =new DebatManuelle();  // Pour cr�er le d�bat � partir du fichier donn� en param�tre de menuAutoFichier
		Scanner scan = new Scanner(System.in);
		int res;
		res = m.saisi("1) remplir le graphe manuellement 2) automatiquement ? :");
		while(res != 1 && res !=2)
		{
			res = m.saisi("Entrez soit 1 soit 2 :");
		}
		if(res == 1)
		{
			m.menuManuelle(debat);
			m.menuAutoSolu(debat);
		}
		else if(res == 2)
		{
			try
			{
				String nomFichier = new String();
				System.out.println("Entrez le chemin où se trouve votre fichier ou le nom du fichier (avec l'extension .txt) : ");
				nomFichier = scan.next();
				//String s = "C:\\Users\\azeau\\eclipse-workspace\\Projet\\Fichier.txt";
				m.menuAutoFichier(nomFichier, debat);
				File f = new File(nomFichier);
				if(f.exists()) //vérifier que le fichier existe avant d'exécuter le menu
					m.menuAutoSolu(debat);
			}
			catch (FileNotFoundException fnfe) 
			{
				System.err.println("Le fichier specifie est introuvable");
				
			}
			catch (IOException e) 
			{
				//e = new IOException("Erreur d'entree/sortie");
				System.err.println(e.getMessage());
		    }
			catch (IllegalArgumentException iae) 
			{
				//iae = new IllegalArgumentException("Erreur survenu suite à un argument incorrect");
				System.err.println(iae.getMessage());
			} 
			
		}
		scan.close();
	}	
		
}

package Main;

import interfaceUtilisateur.Menu;
import programmeManuelle.Debat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Main 
{
	
	public static void main(String[] args) 
	{
		Menu m = new Menu();                        // Classe pour afficher les differentes parties du menu
		Debat debat =new Debat();  // Pour creer le debat a partir du fichier donne en parametre de menuAutoFichier
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
				System.out.println("Entrez le chemin ou se trouve votre fichier ou le nom du fichier (avec l'extension .txt) : ");
				nomFichier = scan.next();
				m.menuAutoFichier(nomFichier, debat);
				File f = new File(nomFichier);
				if(f.exists()) //verifier que le fichier existe avant d'executer le menu
					m.menuAutoSolu(debat);
			}
			catch (FileNotFoundException fnfe) 
			{
				System.err.println("Le fichier specifie est introuvable");
				
			}
			catch (IOException e) 
			{
				System.err.println(e.getMessage());
		    }
			catch (IllegalArgumentException iae) 
			{
				System.err.println(iae.getMessage());
			} 
			
		}
		scan.close();
	}	
		
}

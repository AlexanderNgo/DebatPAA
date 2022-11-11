import java.util.*;
public class main 
{
	public static void main(String[] args) 
	{
		Ensemble e = new Ensemble();
		e.remplissageArguments();
		e.menu();
		e.afficherArguments();
		e.menu2();
	}
}

//Test effectuer avec les outputs suivants : 4 a1 a2 a3 a4 1 a1 a2 1 a2 a3 1 a3 a4 1 a4 a3 1 a3 a2 1 a2 a1 2 1 a1 3 1 a3 3 2 a3 1 a2 3 4

package programmeManuelle;

import java.util.ArrayList;
import java.util.List;

public class SolutionPotentielle {
	private List<String> arguments;
	
	public SolutionPotentielle() {
		this.arguments=new ArrayList<String>();
	}
	/**
	 * Nom : setArguments
	 * @param List<String> arg
	 * @return NONE
	 * Description : Methode qui remplace l'attribut arguments par un autre donne 
	 */
	public void setArguments(List<String> arg) {
		this.arguments=arg;
	}
	/**
	*	Nom : add
	*	@param String arg
	*	@return NONE
	*	Description : Ajoute un argument dans la solution potentielle E
	**/
	public void add(String arg){
		if (arguments.contains(arg)) {
			System.out.println("L'argument "+arg+" est deja dans E");
		}else {
			arguments.add(arg);
		}
	}
	
	/**
	*	Nom : remove
	*	@param String arg
	*	@return NONE
	*	Description : Enleve un argument de la solution potentielle E
	**/
	public void remove(String arg) {
		if (!arguments.contains(arg)) {
			System.out.println("L'argument "+arg+" n'est pas dans E");
		}else {
			arguments.remove(arg);
		}
	}
	
	/**
	*	Methode place dans la methode principale de verif, qui va tester si l'ensemble E se contredit ou pas.
	*	@param DebatManuelle debat
	*	@return boolean
	**/
	private boolean contradictionVerif(Debat debat) {
		for(String arg:arguments) {
			List<String> contres=debat.getGraph().get(arg);
			for (String contre :contres) {
				if (arguments.contains(contre)) {
					System.out.println(contre+" contredit "+arg);
					return false;//presense de contradiction
				}
			}
		}
		return true;
	}
	/**
	 * Meme methode que contradictionVerif() mais sans rien afficher dans la console
	 * @param debat
	 * @return boolean
	 */
	private boolean contradictionVerifnoPrint(Debat debat) {
		for(String arg:arguments) {
			List<String> contres=debat.getGraph().get(arg);
			for (String contre :contres) {
				if (arguments.contains(contre)) {
					return false;//presense de contradiction
				}
			}
		}
		return true;
	}
	/**
	*	Methode qui verifie si la solution potentielle E est admissible ou pas
	*	@param DebatManuelle debat
	*	@return boolean
	**/
	public boolean verif(Debat debat) {
		if (arguments.size() == 0) {                 // SI ENSEMBLE VIDE ALORS SOLU ADMI
			return true;
		}
		if (!contradictionVerif(debat)) {            // SI CONTRADICTION INTERNE SOLU NON ADMI
			return false;
		}
		
		List<String> argPasDansSolu = new ArrayList<String>();  // VA CONTENIR LES ARGUMENTS DU DEBAT QUI NE SONT PAS PRESENT DANS L'ENSEMBLE E
		
		for (String arg: debat.getGraph().keySet()) { // AJOUT DE TOUS LES ARGUMENTS DU DEBAT QUI NE SONT PAS DANS L'ENSEMBLE E 
			if (!arguments.contains(arg)) {
				argPasDansSolu.add(arg);
			}
		}
		
		/* Dans cette partie on teste la defense de l'ensemble E. Pour chaque argument A dans argPasDansSolu 
		 *on verifie que A ne contredise pas un argument de E. Si A contredit un 
		 * argument de E on verifie qu'au moins un argument de E contredise A
		 */
		for (String argAttaque : argPasDansSolu) {  // POUR CHAQUE ARGUMENT QUI PEUT POTENTIELLEMENT CONTRDIRE
			for (String argSoluPot1 : arguments) {  // POUR CHAQUE ARGUEMENT DANS ENSEMBLE E
				if (debat.getGraph().get(argAttaque).contains(argSoluPot1)) {  // SI ARGUMENT ARGATTAQUE CONTREDIT ARGUMENT DE E ALORS ON VERIFIE QU'AU MOINS 1 ARGUMENT DE E CONTREDIT ARGATTAQUE
					boolean contredit = false;
					int i = 0;
					while (!contredit && i<arguments.size()) { // TANT QUE ARGATTAQUE N'EST PAS CONTREDIT ET QU'IL RESTE DES ARGUMENTS DANS E
						if (debat.getGraph().get(arguments.get(i)).contains(argAttaque)) { // SI UN ARGUMENT DE E CONTREDIT ARGATTAQUE ON SORT
							contredit = true;
						}
						i++;
					}
					if (contredit == false) { // SI AUCUN ARGUMENT DE E N'A CONTREDIT ARGATTAQUE ALORS L'ENSEMBLE E NE SE DEFEND PAS
						System.out.println(argAttaque+" contredit "+argSoluPot1+" mais aucune aucun argument dans l'ensemble E ne contredit "+argAttaque);
						return false;
					}		
				}
			}
		}
		return true; // ENSEMBLE E EST ADMISSIBLE
	}
	
	/**
	 * 	Meme chose que verif() mais sans rien afficher dans la console
	*	@param DebatManuelle debat
	*	@return boolean
	**/
	public boolean verifnoPrint(Debat debat) {
		if (arguments.size() == 0) {         
			return true;
		}
		if (!contradictionVerifnoPrint(debat)) {  
			return false;
		}
		
		List<String> argPasDansSolu = new ArrayList<String>();  
		for (String arg: debat.getGraph().keySet()) {
			if (!arguments.contains(arg)) {
				argPasDansSolu.add(arg);
			}
		}
		
		for (String argAttaque : argPasDansSolu) {  
			for (String argSoluPot1 : arguments) { 
				if (debat.getGraph().get(argAttaque).contains(argSoluPot1)) { 
					boolean contredit = false;
					int i = 0;
					while (!contredit && i<arguments.size()) {
						if (debat.getGraph().get(arguments.get(i)).contains(argAttaque)) {
							contredit = true;
						}
						i++;
					}
					if (contredit == false) { 
						return false;
					}		
				}
			}
		}
		return true; 
	}
	
	/**
	*	Retourne la solution potentielle E
	*	@param NONE
	*	@return String
	**/
	public String toString() {
		StringBuffer sb=new StringBuffer("L'ensemble E { ");
		for (String arg:arguments) {
			sb.append(arg);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" }");
		return sb.toString();
	}
}


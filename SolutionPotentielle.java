import java.util.ArrayList;
import java.util.List;

public class SolutionPotentielle {
	private List<String> arguments;
	
	public SolutionPotentielle() {
		this.arguments=new ArrayList<String>();
	}
	
	public void add(String arg){
		if (arguments.contains(arg)) {
			System.out.println("L'argument "+arg+" est d�ja dans E");
		}else {
			arguments.add(arg);
		}
	}
	
	public void remove(String arg) {
		if (!arguments.contains(arg)) {
			System.out.println("L'argument "+arg+" n'est pas dans E");
		}else {
			arguments.remove(arg);
		}
	}
	
	/*M�thode plac� dans la m�thode principale de v�rification, 
	 * qui va tester si l'ensemble E se contredit ou pas.
	 */
	private boolean contradictionVerif(DebatManuelle debat) {
		for(String arg:arguments) {
			List<String> contres=debat.getGraph().get(arg);
			for (String contre :contres) {
				if (arguments.contains(contre)) {
					System.out.println(arg + " est contr� par : "+ contre);
					return false;//pr�sense de contradiction
				}
			}
		}
		return true;
	}
	
	public boolean verif(DebatManuelle debat) {
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

	public String toString() {
		StringBuffer sb=new StringBuffer("L'ensemble E {");
		for (String arg:arguments) {
			sb.append(arg);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("}");
		return sb.toString();
	}
}

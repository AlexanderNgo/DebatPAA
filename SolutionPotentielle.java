import java.util.ArrayList;
import java.util.List;

public class SolutionPotentielle {
	private List<String> arguments;
	
	public SolutionPotentielle() {
		this.arguments=new ArrayList<String>();
	}
	
	public void add(String arg){
		if (arguments.contains(arg)) {
			System.out.println("L'argument "+arg+" est d�j� dans E");
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
	
	public boolean verif(DebatManuelle debat) {
		for(String arg:arguments) {
			List<String> contres=debat.getGraph().get(arg);
			for (String contre :contres) {
				if (arguments.contains(contre)) {
					return false;//présense de contradiction
				}
			}//TODO
		}
		return true;
		
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

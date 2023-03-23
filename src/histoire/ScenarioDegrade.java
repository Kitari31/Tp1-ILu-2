package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;
public class ScenarioDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		System.out.println(etal.libererEtal());
		System.out.println(etal.acheterProduit(2,null));
		System.out.println(etal.acheterProduit(-2,bonemine));
		System.out.println(etal.acheterProduit(2,bonemine));
		System.out.println("Fin du test");
	}
}

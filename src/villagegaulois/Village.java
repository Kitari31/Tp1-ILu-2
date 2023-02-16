package villagegaulois;

import java.util.ArrayList;
import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	private static class Marche {
		private Etal[] etal;
		
		public  Marche(int nbEtals) {
			this.etal = new Etal[nbEtals];
			for(int i=0;i<etal.length;i++) {
				etal[i]= new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur,
				String produit, int nbProduit) {
			etal[indiceEtal].occuperEtal(vendeur,produit,nbProduit);
		}
		
		public int trouverEtalLibre(){
			int i;
			for(i=0;i<etal.length;i++){
				if(!etal[i].isEtalOccupe()){
					return i;
				}
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			int nbEtals = 0;
			for(int i=0;i<etal.length;i++){
				if(etal[i].contientProduit(produit)) {
					nbEtals+=1;
				}
			}
			Etal[] etalOccupee = new Etal[nbEtals];
			
			int j=0;
			for(int i=0;i<etal.length;i++){
				if(etal[i].contientProduit(produit)) {
					etalOccupee[j]=etal[i];
					j=j+1;
				}
			}
			return etalOccupee;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < etal.length; i++) {
				if(etal[i].getVendeur()==gaulois){
					return etal[i];
				}
			}
			return null;
		}

		public void afficherMarche() {
			int nbEtalVide = 0;
			String texte = "Liste des marchés :\n";
			for (int i = 0; i< etal.length; i++) {
				if(!etal[i].isEtalOccupe()) {
					nbEtalVide+=1;
				}
				else {
					texte += "- " + etal[i].getVendeur().getNom() +"\n";
				}
			}
			texte = "Il reste " +nbEtalVide + " étals non utilisés dans le marché.\n\n" + texte;
			System.out.println(texte);
		}
		
	}
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int numEtal = marche.trouverEtalLibre();
		marche.utiliserEtal(numEtal,vendeur,produit,nbProduit);
		numEtal+=1;
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + "à l'étal n°" + numEtal + ".\n");
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit){
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + produit + " sont : \n");
		Etal[] etalProduit = marche.trouverEtals(produit);
		if(etalProduit.length >= 1) {
			for (int i = 0; i < etalProduit.length; i++) {
				chaine.append("- "+ etalProduit);
			}
		}
		return chaine.toString();
	}
}





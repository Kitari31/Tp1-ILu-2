package villagegaulois;

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
			Etal[] etalProduit = new Etal[etal.length];
			int nbrVenteProduit = 0;
			for(Etal etal : etal) {
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					etalProduit[nbrVenteProduit]=etal;
					nbrVenteProduit+=1;
				}
			}
			return etalProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < etal.length; i++) {
				if(etal[i].getVendeur()==gaulois){
					return etal[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder("Liste des marchés :\n");
			int nbEtalVide = 0;
			for (int i = 0; i< etal.length; i++) {
				if(!etal[i].isEtalOccupe()) {
					nbEtalVide+=1;
				}
				else {
					chaine.append("- " + etal[i].getVendeur().getNom() +"\n");
				}
			}
			chaine.append("Il reste " +nbEtalVide + " étals non utilisés dans le marché.\n");
			return chaine.toString();
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
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsVendeursProduit = marche.trouverEtals(produit);
		if(etalsVendeursProduit.length==0) {
			chaine.append("Aucun vendeurs ne vend de " + produit + ".\n");
			return chaine.toString();
		}
		chaine.append("Les vendeurs qui proposent des " + produit + " sont : \n");
		for (Etal etal : etalsVendeursProduit) {
			if (etal != null && etal.isEtalOccupe()) {
				chaine.append("   - " + etal.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalVendeur=rechercherEtal(vendeur);
		chaine.append(etalVendeur.libererEtal());
		return chaine.toString();
		
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		int etalsOccupes = marche.etal.length;
		if (etalsOccupes==0) {
			chaine.append("Il n'y a aucun vendeur au village " + getNom()+".\n");
			return chaine.toString();
		}
		chaine.append("Le marché du village "+ getNom() + " possède plusieurs étals :\n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
}





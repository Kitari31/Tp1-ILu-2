package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;
	
	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}
	
	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		try {
	        if (etalOccupe == false) {
	            throw new IllegalArgumentException("L'étal n'est pas occupé par un vendeur.\n");
	        }
	    } catch (IllegalArgumentException e) {
	        return e.getMessage();
	    }

	    etalOccupe = false;
	    StringBuilder chaine = new StringBuilder(
	            "Le vendeur " + vendeur.getNom() + " quitte son étal, ");
	    int produitVendu = quantiteDebutMarche - quantite;
	    if (produitVendu > 0) {
	        chaine.append(
	                "il a vendu " + produitVendu + " parmi " + produit + ".\n");
	    } else {
	        chaine.append("il n'a malheureusement rien vendu.\n");
	    }
	    return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}
	
	public String afficherEtalMarche() {
		if (etalOccupe) {
			return vendeur.getNom() + " vend " + quantite + " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		//nom acheteur = nul
		try {
			if(acheteur == null) {
				throw new Exception("L'acheteur n'a pas de nom.\n");
			}
		} catch(Exception e) {
	        return e.getMessage();
		}
		
		//quantité acheté <1
		try {
			if(quantiteAcheter<1) {
				throw new Exception("La quantité acheté ne peux pas être inferieur à 1.\n");
			}
		} catch(Exception e) {
	        return e.getMessage();
		}
		
		//l'étal doit être occuper
		try {
			if(!etalOccupe) {
				throw new IllegalArgumentException("L'étal n'est pas occupé.\n");
			}
		} catch(IllegalArgumentException e) {
	        return e.getMessage();
		}
		
		StringBuilder chaine = new StringBuilder();
		chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
				+ " " + produit + " à " + vendeur.getNom());
		if (quantite == 0) {
			chaine.append(", malheureusement il n'y en a plus !");
			quantiteAcheter = 0;
		}
		if (quantiteAcheter > quantite) {
			chaine.append(", comme il n'y en a plus que " + quantite + ", "
					+ acheteur.getNom() + " vide l'étal de "
					+ vendeur.getNom() + ".\n");
			quantiteAcheter = quantite;
			quantite = 0;
		}
		if (quantite != 0) {
			quantite -= quantiteAcheter;
			chaine.append(". " + acheteur.getNom()
					+ ", est ravi de tout trouver sur l'étal de "
					+ vendeur.getNom() + "\n");
		}
		return chaine.toString();
	}

	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}
}

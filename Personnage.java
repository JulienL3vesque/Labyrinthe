/* Fichier: Personnage.java
 *
 * Auteurs: Philippe Brisson et
 * 
 * Le jeu de labyrinthe invisible est un jeu de m�moire qui fonctionne selon le principe suivant:
 * Au d�but du jeu, l'ordinateur cr�e un labyrinthe au hasard. Il cr�e au hasard une entr�e par la gauche et une sortie
 * par la droite. Des murets sont construits � l�int�rieur. Un personnage (repr�sent� par @) est plac� juste � l�entr�e
 * du labyrinthe dans l'extr�me gauche du labyrinthe
 *
*/

public class Personnage {
	
	// attributs i (position en hauteur), j (position en largeur), vie (actuelle) et vieInitiale (entr�e par l'utilisateur) et labyrinthe associ�.
	private int i, j, vie, vieInitiale;
	private Labyrinthe labyrinthe;
	
	// prend en param�tre le labyrinthe, la position (i, j) et le nombre de vies et construit un personnage
	Personnage(Labyrinthe l, int i, int j, int vie){
		
		setLabyrinthe(l);
		this.i = i;
		this.j = j;
		this.vie = vie;
		this.vieInitiale = vie;
		
	}
	
	// retourne le labyrinthe associ� au personnage
	public Labyrinthe getLabyrinthe() {
		
		return labyrinthe;
	
	}

	// prend un labyrinthe en param�tre et l'associe au joueur
	public void setLabyrinthe(Labyrinthe labyrinthe) {
		
		this.labyrinthe = labyrinthe;
	
	}
	
	// retourne la position i du joueur
	public int getPositionI(){
		
		return i;
		
	}
	
	// retourne la position j du joueur
	public int getPositionJ(){
		
		return j;
		
	}
	
	// retourne le nombre de vies restantes au joueur
	public int getVie(){
		
		return vie;
		
	}
	
	// retourne le nombre de vies initiales du joueur
	public int getVieInitiale(){
		
		return vieInitiale;
		
	}
	
	// fait perdre une vie au joueur.
	public void perdreUneVie(){
		
		vie--;
		
	}
	
	// prend en param�tre un labyrinthe et une direction, et v�rifie s'il y a un mur ou pas dans la direction demand�e.
	public boolean peutSeDeplacer(Labyrinthe l, char direction){
		
		// si on veut aller a droite, on retourne "true" s'il n'y a pas de mur
		if(direction == 'd'){
			
			return !l.aMuretADroite(i, j);
		}
		
		// si on veut aller a gauche, on retourne "true" s'il n'y a pas de mur
		else if(direction == 'g'|| direction == 's'){
			
			return !l.aMuretAGauche(i, j);
		}
		
		// si on veut aller en haut, on retourne "true" s'il n'y a pas de mur
		else if(direction == 'h' || direction == 'e'){
			
			return !l.aMuretEnHaut(i, j);
		}
		
		// si on veut aller en bas, on retourne "true" s'il n'y a pas de mur
		else{
			
			return !l.aMuretEnBas(i, j);
		}
		
	}
	
	// prend une direction en param�tre et actualise la position du joueur.
	public void actualiserPosition(char direction){

		switch(direction){
		
		// si on veut aller a gauche, on modifie la position j.
		case 'g':
			
			j -= 8;
			break;
		
		// si on veut aller a droite, on modifie la position j.
		case 'd':
			
			j += 8;
			break;
			
		// si on veut aller en haut, on modifie la position i.
		case 'h':
			
			i -= 4;
			break;
			
		// si on veut aller en bas, on modifie la position i.
		case 'b':
			
			i += 4;
			break;
			
		}
		
	}

}

/* Fichier: Labyrinthe.java
 *
 * Auteurs: Philippe Brisson et
 * 
 * Le jeu de labyrinthe invisible est un jeu de mémoire qui fonctionne selon le principe suivant:
 * Au début du jeu, l'ordinateur crée un labyrinthe au hasard. Il crée au hasard une entrée par la gauche et une sortie
 * par la droite. Des murets sont construits à l’intérieur. Un personnage (représenté par @) est placé juste à l’entrée
 * du labyrinthe dans l'extrême gauche du labyrinthe
 *
*/

public class Labyrinthe {
	
	// tableauLabyrinthe = le tableau vu par l'utilisateur. HMURET et LMURET sont des constantes.
	// hauteurTableau et largeurTableau sont les dimentions du tableau.
	// dimentionsHauteur et dimentionsLargeur sont les dimentions entrées par l'utilisateur à la base.
	private char[][] tableauLabyrinthe;
	private static final int HMURET = 4;
	private static final int LMURET = 8;
	private int hauteurTableau, largeurTableau, dimentionsHauteur, dimentionsLargeur;
	
	// constructeur prend en paramètre la hauteur et largeur prises en argument et construit l'objet Labyrinthe.
	Labyrinthe(int h, int w){
		
		// argument hauteur, argument largeur.
		dimentionsHauteur = h;
		dimentionsLargeur = w;
		
		// dimentions du tableau de jeu.
		hauteurTableau = h*HMURET + 1;
		largeurTableau = w*LMURET + 1;
		
		// création du tableau.
		creeTableau(hauteurTableau, largeurTableau);
		
	}
	
	// constructeur qui copie toutes les caractéristiques d'un autre labyrinthe.
	Labyrinthe(Labyrinthe l){
		
		dimentionsHauteur = l.dimentionsHauteur;
		dimentionsLargeur = l.dimentionsLargeur;
		hauteurTableau = l.hauteurTableau;
		largeurTableau = l.largeurTableau;
		
		tableauLabyrinthe = new char[hauteurTableau][largeurTableau];
		
		l.copieLabyrinthe(this);
		
	}
	
	// créé un tableau de dimentions hauteur * largeur et le rempli de caractères ' '.
	public void creeTableau(int hauteur, int largeur){
		
		tableauLabyrinthe = new char[hauteur][largeur];
		
		for(int i = 0; i < hauteur; i++){
			
			for(int j = 0; j < largeur; j++){
				
				tableauLabyrinthe[i][j] = ' ';		
				
			}
		}
	}
	
	// efface le tableau en le remplissant de caractères ' '.
	public void effaceTableau(){
		
		for(int i = 0; i < hauteurTableau; i++){
			
			for(int j = 0; j < largeurTableau; j++){
				
				tableauLabyrinthe[i][j] = ' ';
				
			}
		}
	}
	
	// dessine les enceintes du labyrinthe.
	public void dessineMurdEnceinte(){
		
		// ajoute les + à chaque coin.
		tableauLabyrinthe[0][0] = tableauLabyrinthe[0][largeurTableau-1] = tableauLabyrinthe[hauteurTableau-1][0] = tableauLabyrinthe[hauteurTableau-1][largeurTableau-1] = '+';
		
		// ajoute les murs verticaux.
		for(int i = 1; i < hauteurTableau-1; i++){
			
			tableauLabyrinthe[i][0] = '|';
			tableauLabyrinthe[i][largeurTableau - 1] = '|';
			
		}
		
		//ajoute les murs horizontaux.
		for(int j = 1; j < largeurTableau-1; j++){
			
			tableauLabyrinthe[0][j] = '-';
			tableauLabyrinthe[hauteurTableau - 1][j] = '-';
			
		}
		
	}
	
	// dessine la sortie à l'endroit j indiqué.
	public void dessineOuverture(int j){
		
		for(int i = j-1; i <= j+1; i++){
			
			tableauLabyrinthe[i][largeurTableau-1] = ' ';
			
		}
		
	}
	
	// dessine l'entrée à l'endroit j indiqué
	public void dessineEntree(int j){
		
		for(int i = j-1; i <= j+1; i++){
			
			tableauLabyrinthe[i][0] = ' ';
			
		}
		
	}
	
	// dessine un muret vertical à l'endroit (i, j) indiqué.
	public void dessineMuretVertical(int i, int j){
		
		for(int k = i - 1; k <= i+1; k++){
			
			tableauLabyrinthe[k][j] = '|';
			
		}
		
	}
	// dessine un muret horizontal à l'endroit (i, j) indiqué.
	public void dessineMuretHorizontal(int i, int j){
		
		for(int k = j - 3; k <= j+3; k++){
			
			tableauLabyrinthe[i][k] = '-';
			
		}
		
	}
	
	// vérifie s'il y a un muret à droite de l'endroit (i, j).
	public boolean aMuretADroite(int i, int j){
			
		return (tableauLabyrinthe[i][j+4] == '|');
		
	}
	
	// vérifie s'il y a un muret à gauche de l'endroit (i, j).
	public boolean aMuretAGauche(int i, int j){
		
		return (tableauLabyrinthe[i][j-4] == '|');
		
	}
	
	// vérifie s'il y a un muret en haut de l'endroit (i, j).
	public boolean aMuretEnHaut(int i, int j){

		return (tableauLabyrinthe[i-2][j] == '-');
		
	}
	
	// vérifie s'il y a un muret à en bas de l'endroit (i, j).
	public boolean aMuretEnBas(int i, int j){
		
		return (tableauLabyrinthe[i+2][j] == '-');
		
	}
	
	// vérifie si l'entrée est à gauche de la position (i, j).
	public boolean aEntreeAGauche(int i, int j){
		
		return(j-4 == 0 && !aMuretAGauche(i, j));
		
	}
	
	// vérifie si la sortie est à droite de la position (i, j).
	public boolean aSortieADroite(int i, int j){
		
		return(j+5 == largeurTableau && !aMuretADroite(i, j));
		
	}
	
	// dessine le personnage à l'endroit où il est.
	public void dessinePersonnage(Personnage p){
		
		tableauLabyrinthe[p.getPositionI()][p.getPositionJ()] = '@';
		
	}
	
	// efface le personnage de l'endroit où il est.
	public void effacePersonnage(Personnage p){
		
		tableauLabyrinthe[p.getPositionI()][p.getPositionJ()] = ' ';
		
	}
	
	// rafraichit l'écran avec 200 lignes vides.
	public static void effaceEcran(){
		
		for(int i = 0; i < 200; i++){
			
			System.out.println("\n");
			
		}
	}
	
	// dessine le labyrinthe à l'écran.
	public void affiche(){
		
		String jeu = "";
		
		for(int i = 0; i < hauteurTableau; i++){
			
			for(int j = 0; j < largeurTableau; j++){
				
				jeu += tableauLabyrinthe[i][j];
				
			}
			
			jeu += "\n";
		
		}
		
		System.out.println(jeu);
		
	}
	
	// construit un labyrinthe aléatoire avec la densité de murs demandée.
	public void construitLabyrintheAleatoire(double densite){
		
		// on calcule le nombre de murs que l'on peut dessiner dans les dimentions données.
		int nombreMurs = (dimentionsHauteur-1)+(2*dimentionsHauteur-1)*(dimentionsLargeur-1);
		
		// nombreDeMursRequis est le nombre de murs qui seront placés par rapport à la densité demandée.
		// nombreDeMursMis est une variable qui va compter combien de murs ont été placés déjà.
		int nombreDeMursRequis = (int) (Math.round(densite*nombreMurs));
		int nombreDeMursMis = 0;
		
		// tant et aussi longtemps qu'on a pas mis le nombre de murs requis, le système continue de mettre des murs.
		while(nombreDeMursRequis != nombreDeMursMis){
			
			// murVerticalouHorizontal est un flip coin( o ou 1) à savoir si le prochain mur est horizontal ou vertical.
			int murVerticalouHorizontal = (int) Math.round(Math.random());
			
			// si le flipcoin tombe sur 0, on essaye de mettre un mur horizontal.
			if(murVerticalouHorizontal == 0){
				
				nombreDeMursMis += ajouterMurHorizontalAleatoire();
				
			}
			
			// sinon on essaye de mettre un mur vertical.
			else{
				
				nombreDeMursMis += ajouterMurVerticalAleatoire();
				
			}
		}
	}
	
	// méthode qui essaie de mettre un mur horizontal aléatoire. Si elle réussit, elle retourne 1 (pour dire qu'elle a 
	// bel et bien ajouté un mur) S'il y a déjà un mur à l'endroit aléatoire trouvé, la méthode ne créé aucun mur et 
	// retourne 0.
	public int ajouterMurHorizontalAleatoire(){
		
		// positions aléatoires générées.
		int positionHauteur = (int) Math.floor(Math.random()*(dimentionsHauteur-1)+1)*HMURET;
		int positionLargeur = (int) Math.floor(Math.random()*(dimentionsLargeur)+1)*LMURET-4;
		

		// vérifie s'il y a un tableau à l'endroit aléatoire indiqué.
		if(tableauLabyrinthe[positionHauteur][positionLargeur] == ' '){
			
			dessineMuretHorizontal(positionHauteur, positionLargeur);

			return 1;
			
		}
		
		return 0;
		
	}
	
	// méthode qui essaie de mettre un mur vertical aléatoire. Si elle réussit, elle retourne 1 (pour dire qu'elle a 
	// bel et bien ajouté un mur) S'il y a déjà un mur à l'endroit aléatoire trouvé, la méthode ne créé aucun mur et 
	// retourne 0.
	public int ajouterMurVerticalAleatoire(){
		
		// positions aléatoires générées.
		int positionHauteur = (int) Math.floor(Math.random()*(dimentionsHauteur)+1)*HMURET-2;
		int positionLargeur = (int) Math.floor(Math.random()*(dimentionsLargeur-1)+1)*LMURET;
		
		// vérifie s'il y a un tableau à l'endroit aléatoire indiqué.
		if(tableauLabyrinthe[positionHauteur][positionLargeur] == ' '){
			
			dessineMuretVertical(positionHauteur, positionLargeur);
			
			return 1;
			
		}
		
		return 0;
		
	}
	
	// prend la hauteur, la largeur, la densité et la position initiale au hasard et appelles les fonctions afin d'initialiser le labyrinthe.
	public void InitieLabyrinthe(int hauteur, int largeur, double densite, int positionInitialeHasard){
		
		// dessine les murs d'einceinte, construit le labyrinthe avec la bonne densité, dessine l'entrée.
		dessineMurdEnceinte();
		construitLabyrintheAleatoire(densite);
		dessineEntree(positionInitialeHasard);
		
		// créé et dessine une sortie à une position au hasard.
		int sortieHasard = (int) Math.floor(Math.random()*hauteur+1)*4-2;
		dessineOuverture(sortieHasard);
		
	}
	
	// prend un labyrinthe l en paramètre et enlève tous les murs de ce dernier.
	public void rendreLabyrintheInvisible(Labyrinthe l){
		
		for(int i = 1; i < l.hauteurTableau-1; i++){
			
			for(int j = 1; j < l.largeurTableau-1; j++){
				
				if(tableauLabyrinthe[i][j] != '@'){
					
					l.tableauLabyrinthe[i][j] = ' ';
				
				}
				
			}
			
		}
		
	}
	
	// prend un personnage p et une direction et dessine un mur à l'endroit demandé.
	public void rendreMurVisible(Personnage p, char direction){
		
		switch(direction){
		
		// si le joueur a entré dans un muret à gauche, on dessine un muret à gauche.
		case 'g':
			
			dessineMuretVertical(p.getPositionI(), p.getPositionJ()-4);
			break;
		
		// si le joueur a entré dans un muret à droite, on dessine un muret à droite.
		case 'd':
			
			dessineMuretVertical(p.getPositionI(), p.getPositionJ()+4);
			break;
		
		// si le joueur a entré dans un muret en haut, on dessine un muret en haut.
		case 'h':
			
			dessineMuretHorizontal(p.getPositionI()-2, p.getPositionJ());
			break;
		
		// si le joueur a entré dans un muret en bas, on dessine un muret en bas.
		case 'b':
			
			dessineMuretHorizontal(p.getPositionI()+2, p.getPositionJ());
			break;
			
		}
		
	}
	
	// prend le labyrinthe auquel la méthode est appelée pour le copier dans celui prit en paramètre.
	public void copieLabyrinthe(Labyrinthe l){
		
		for(int i = 0; i < l.hauteurTableau; i++){
			
			for(int j = 0; j < l.largeurTableau; j++){
				
				l.tableauLabyrinthe[i][j] = tableauLabyrinthe[i][j];
				
			}
			
		}
		
	}
	
}

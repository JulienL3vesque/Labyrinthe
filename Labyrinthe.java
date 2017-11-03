/* Fichier: Labyrinthe.java
 *
 * Auteurs: Philippe Brisson et
 * 
 * Le jeu de labyrinthe invisible est un jeu de m�moire qui fonctionne selon le principe suivant:
 * Au d�but du jeu, l'ordinateur cr�e un labyrinthe au hasard. Il cr�e au hasard une entr�e par la gauche et une sortie
 * par la droite. Des murets sont construits � l�int�rieur. Un personnage (repr�sent� par @) est plac� juste � l�entr�e
 * du labyrinthe dans l'extr�me gauche du labyrinthe
 *
*/

public class Labyrinthe {
	
	// tableauLabyrinthe = le tableau vu par l'utilisateur. HMURET et LMURET sont des constantes.
	// hauteurTableau et largeurTableau sont les dimentions du tableau.
	// dimentionsHauteur et dimentionsLargeur sont les dimentions entr�es par l'utilisateur � la base.
	private char[][] tableauLabyrinthe;
	private static final int HMURET = 4;
	private static final int LMURET = 8;
	private int hauteurTableau, largeurTableau, dimentionsHauteur, dimentionsLargeur;
	
	// constructeur prend en param�tre la hauteur et largeur prises en argument et construit l'objet Labyrinthe.
	Labyrinthe(int h, int w){
		
		// argument hauteur, argument largeur.
		dimentionsHauteur = h;
		dimentionsLargeur = w;
		
		// dimentions du tableau de jeu.
		hauteurTableau = h*HMURET + 1;
		largeurTableau = w*LMURET + 1;
		
		// cr�ation du tableau.
		creeTableau(hauteurTableau, largeurTableau);
		
	}
	
	// constructeur qui copie toutes les caract�ristiques d'un autre labyrinthe.
	Labyrinthe(Labyrinthe l){
		
		dimentionsHauteur = l.dimentionsHauteur;
		dimentionsLargeur = l.dimentionsLargeur;
		hauteurTableau = l.hauteurTableau;
		largeurTableau = l.largeurTableau;
		
		tableauLabyrinthe = new char[hauteurTableau][largeurTableau];
		
		l.copieLabyrinthe(this);
		
	}
	
	// cr�� un tableau de dimentions hauteur * largeur et le rempli de caract�res ' '.
	public void creeTableau(int hauteur, int largeur){
		
		tableauLabyrinthe = new char[hauteur][largeur];
		
		for(int i = 0; i < hauteur; i++){
			
			for(int j = 0; j < largeur; j++){
				
				tableauLabyrinthe[i][j] = ' ';		
				
			}
		}
	}
	
	// efface le tableau en le remplissant de caract�res ' '.
	public void effaceTableau(){
		
		for(int i = 0; i < hauteurTableau; i++){
			
			for(int j = 0; j < largeurTableau; j++){
				
				tableauLabyrinthe[i][j] = ' ';
				
			}
		}
	}
	
	// dessine les enceintes du labyrinthe.
	public void dessineMurdEnceinte(){
		
		// ajoute les + � chaque coin.
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
	
	// dessine la sortie � l'endroit j indiqu�.
	public void dessineOuverture(int j){
		
		for(int i = j-1; i <= j+1; i++){
			
			tableauLabyrinthe[i][largeurTableau-1] = ' ';
			
		}
		
	}
	
	// dessine l'entr�e � l'endroit j indiqu�
	public void dessineEntree(int j){
		
		for(int i = j-1; i <= j+1; i++){
			
			tableauLabyrinthe[i][0] = ' ';
			
		}
		
	}
	
	// dessine un muret vertical � l'endroit (i, j) indiqu�.
	public void dessineMuretVertical(int i, int j){
		
		for(int k = i - 1; k <= i+1; k++){
			
			tableauLabyrinthe[k][j] = '|';
			
		}
		
	}
	// dessine un muret horizontal � l'endroit (i, j) indiqu�.
	public void dessineMuretHorizontal(int i, int j){
		
		for(int k = j - 3; k <= j+3; k++){
			
			tableauLabyrinthe[i][k] = '-';
			
		}
		
	}
	
	// v�rifie s'il y a un muret � droite de l'endroit (i, j).
	public boolean aMuretADroite(int i, int j){
			
		return (tableauLabyrinthe[i][j+4] == '|');
		
	}
	
	// v�rifie s'il y a un muret � gauche de l'endroit (i, j).
	public boolean aMuretAGauche(int i, int j){
		
		return (tableauLabyrinthe[i][j-4] == '|');
		
	}
	
	// v�rifie s'il y a un muret en haut de l'endroit (i, j).
	public boolean aMuretEnHaut(int i, int j){

		return (tableauLabyrinthe[i-2][j] == '-');
		
	}
	
	// v�rifie s'il y a un muret � en bas de l'endroit (i, j).
	public boolean aMuretEnBas(int i, int j){
		
		return (tableauLabyrinthe[i+2][j] == '-');
		
	}
	
	// v�rifie si l'entr�e est � gauche de la position (i, j).
	public boolean aEntreeAGauche(int i, int j){
		
		return(j-4 == 0 && !aMuretAGauche(i, j));
		
	}
	
	// v�rifie si la sortie est � droite de la position (i, j).
	public boolean aSortieADroite(int i, int j){
		
		return(j+5 == largeurTableau && !aMuretADroite(i, j));
		
	}
	
	// dessine le personnage � l'endroit o� il est.
	public void dessinePersonnage(Personnage p){
		
		tableauLabyrinthe[p.getPositionI()][p.getPositionJ()] = '@';
		
	}
	
	// efface le personnage de l'endroit o� il est.
	public void effacePersonnage(Personnage p){
		
		tableauLabyrinthe[p.getPositionI()][p.getPositionJ()] = ' ';
		
	}
	
	// rafraichit l'�cran avec 200 lignes vides.
	public static void effaceEcran(){
		
		for(int i = 0; i < 200; i++){
			
			System.out.println("\n");
			
		}
	}
	
	// dessine le labyrinthe � l'�cran.
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
	
	// construit un labyrinthe al�atoire avec la densit� de murs demand�e.
	public void construitLabyrintheAleatoire(double densite){
		
		// on calcule le nombre de murs que l'on peut dessiner dans les dimentions donn�es.
		int nombreMurs = (dimentionsHauteur-1)+(2*dimentionsHauteur-1)*(dimentionsLargeur-1);
		
		// nombreDeMursRequis est le nombre de murs qui seront plac�s par rapport � la densit� demand�e.
		// nombreDeMursMis est une variable qui va compter combien de murs ont �t� plac�s d�j�.
		int nombreDeMursRequis = (int) (Math.round(densite*nombreMurs));
		int nombreDeMursMis = 0;
		
		// tant et aussi longtemps qu'on a pas mis le nombre de murs requis, le syst�me continue de mettre des murs.
		while(nombreDeMursRequis != nombreDeMursMis){
			
			// murVerticalouHorizontal est un flip coin( o ou 1) � savoir si le prochain mur est horizontal ou vertical.
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
	
	// m�thode qui essaie de mettre un mur horizontal al�atoire. Si elle r�ussit, elle retourne 1 (pour dire qu'elle a 
	// bel et bien ajout� un mur) S'il y a d�j� un mur � l'endroit al�atoire trouv�, la m�thode ne cr�� aucun mur et 
	// retourne 0.
	public int ajouterMurHorizontalAleatoire(){
		
		// positions al�atoires g�n�r�es.
		int positionHauteur = (int) Math.floor(Math.random()*(dimentionsHauteur-1)+1)*HMURET;
		int positionLargeur = (int) Math.floor(Math.random()*(dimentionsLargeur)+1)*LMURET-4;
		

		// v�rifie s'il y a un tableau � l'endroit al�atoire indiqu�.
		if(tableauLabyrinthe[positionHauteur][positionLargeur] == ' '){
			
			dessineMuretHorizontal(positionHauteur, positionLargeur);

			return 1;
			
		}
		
		return 0;
		
	}
	
	// m�thode qui essaie de mettre un mur vertical al�atoire. Si elle r�ussit, elle retourne 1 (pour dire qu'elle a 
	// bel et bien ajout� un mur) S'il y a d�j� un mur � l'endroit al�atoire trouv�, la m�thode ne cr�� aucun mur et 
	// retourne 0.
	public int ajouterMurVerticalAleatoire(){
		
		// positions al�atoires g�n�r�es.
		int positionHauteur = (int) Math.floor(Math.random()*(dimentionsHauteur)+1)*HMURET-2;
		int positionLargeur = (int) Math.floor(Math.random()*(dimentionsLargeur-1)+1)*LMURET;
		
		// v�rifie s'il y a un tableau � l'endroit al�atoire indiqu�.
		if(tableauLabyrinthe[positionHauteur][positionLargeur] == ' '){
			
			dessineMuretVertical(positionHauteur, positionLargeur);
			
			return 1;
			
		}
		
		return 0;
		
	}
	
	// prend la hauteur, la largeur, la densit� et la position initiale au hasard et appelles les fonctions afin d'initialiser le labyrinthe.
	public void InitieLabyrinthe(int hauteur, int largeur, double densite, int positionInitialeHasard){
		
		// dessine les murs d'einceinte, construit le labyrinthe avec la bonne densit�, dessine l'entr�e.
		dessineMurdEnceinte();
		construitLabyrintheAleatoire(densite);
		dessineEntree(positionInitialeHasard);
		
		// cr�� et dessine une sortie � une position au hasard.
		int sortieHasard = (int) Math.floor(Math.random()*hauteur+1)*4-2;
		dessineOuverture(sortieHasard);
		
	}
	
	// prend un labyrinthe l en param�tre et enl�ve tous les murs de ce dernier.
	public void rendreLabyrintheInvisible(Labyrinthe l){
		
		for(int i = 1; i < l.hauteurTableau-1; i++){
			
			for(int j = 1; j < l.largeurTableau-1; j++){
				
				if(tableauLabyrinthe[i][j] != '@'){
					
					l.tableauLabyrinthe[i][j] = ' ';
				
				}
				
			}
			
		}
		
	}
	
	// prend un personnage p et une direction et dessine un mur � l'endroit demand�.
	public void rendreMurVisible(Personnage p, char direction){
		
		switch(direction){
		
		// si le joueur a entr� dans un muret � gauche, on dessine un muret � gauche.
		case 'g':
			
			dessineMuretVertical(p.getPositionI(), p.getPositionJ()-4);
			break;
		
		// si le joueur a entr� dans un muret � droite, on dessine un muret � droite.
		case 'd':
			
			dessineMuretVertical(p.getPositionI(), p.getPositionJ()+4);
			break;
		
		// si le joueur a entr� dans un muret en haut, on dessine un muret en haut.
		case 'h':
			
			dessineMuretHorizontal(p.getPositionI()-2, p.getPositionJ());
			break;
		
		// si le joueur a entr� dans un muret en bas, on dessine un muret en bas.
		case 'b':
			
			dessineMuretHorizontal(p.getPositionI()+2, p.getPositionJ());
			break;
			
		}
		
	}
	
	// prend le labyrinthe auquel la m�thode est appel�e pour le copier dans celui prit en param�tre.
	public void copieLabyrinthe(Labyrinthe l){
		
		for(int i = 0; i < l.hauteurTableau; i++){
			
			for(int j = 0; j < l.largeurTableau; j++){
				
				l.tableauLabyrinthe[i][j] = tableauLabyrinthe[i][j];
				
			}
			
		}
		
	}
	
}

/* Fichier: Laby.java
 *
 * Auteurs: Philippe Brisson et
 * 
 * Le jeu de labyrinthe invisible est un jeu de m�moire qui fonctionne selon le principe suivant:
 * Au d�but du jeu, l'ordinateur cr�e un labyrinthe au hasard. Il cr�e au hasard une entr�e par la gauche et une sortie
 * par la droite. Des murets sont construits � l�int�rieur. Un personnage (repr�sent� par @) est plac� juste � l�entr�e
 * du labyrinthe dans l'extr�me gauche du labyrinthe
 *
*/

import java.util.Scanner;

public class Laby {
	
	// boolean rejouerPartie reste true tant qu'on veut rejouer une partie.
	// boolean partieEnCours reste true tant qu'on veut continuer � jouer la partie en cours.
	private boolean rejouerPartie;
	private boolean partieEnCours;
	
	// prend 5 arguments en param�tre et les changes en int / double
	public static void main(String[] args){
		
		if(args.length != 5){
			
			throw new IllegalArgumentException("Nombre de param�tres incorrects. \nUtilisation: java Laby <hauteur> <largeur> <densite> <duree visible> <nb vies>\nEx: java Laby 10 20 0.20 10 5 ");
		
		}
		// change tous les param�tres entr�es par l'utilisateur et les change en int / double
		int hauteur = Integer.parseInt(args[0]);
		int largeur = Integer.parseInt(args[1]);
		double densite = Double.parseDouble(args[2]);
		int temps = Integer.parseInt(args[3]);
		int vies = Integer.parseInt(args[4]);
		
		// cr�� l'objet jeu qui sera servira de partie
		Laby jeu = new Laby();
		
		// appelle la fonction jeuLabyrintheInvisible avec tous les arguments pass�s en param�tre.
		jeu.jeuLabyrintheInvisible(hauteur, largeur, densite, temps, vies);
		
	}
	
	// constructeur qui rend les booleans rejouerPartie et partieEnCours true.
	Laby(){
		
		rejouerPartie = partieEnCours = true;
	}
	
	// g�re la partie, rejoue une partie et quitte le programme
	public void jeuLabyrintheInvisible(int hauteur, int largeur, double densite, int temps, int vies){
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		// tant que rejouerPartie est true, le jeu continuera de g�rer des labyrinthes
		while(rejouerPartie){
			
			// cr�� le labyrinthe initial, son entr�e et sa sortie
			Labyrinthe board = new Labyrinthe(hauteur, largeur);
			int positionInitialeHasard = (int) Math.floor(Math.random()*hauteur+1)*4-2;
			board.InitieLabyrinthe(hauteur, largeur, densite, positionInitialeHasard);
			
			// cr�� le personnage et le positionne
			Personnage Bonhomme = new Personnage(board, positionInitialeHasard, 4, vies);
			board.dessinePersonnage(Bonhomme);
			
			// cr�� le labyrinthe invisible dans lequel le jeu se d�roulera
			Labyrinthe boardInvisible = new Labyrinthe(board);
			board.rendreLabyrintheInvisible(boardInvisible);
			
			// "efface" l'�cran avec 200 lignes vides, affiche le labyrinthe pendant le temps demand� par l'utilisateur
			Labyrinthe.effaceEcran();
			board.affiche();
			sleep(temps*1000);
			
			// on d�fini partie en cours = true ici pour commencer une nouvelle partie
			partieEnCours = true;
			
			// boucle while de la partie en cours. On sort de cette boucle seulement pour une nouvelle partie ou pas de partie du tout.
			while(partieEnCours){
				
				// "efface" l'�cran, affiche le labyrinthe invisible et donne les instructions au joueur (nombre de vies, directions).
				Labyrinthe.effaceEcran();
				boardInvisible.affiche();
				System.out.println("Il vous reste " + Bonhomme.getVie() + " sur " + vies + ".\n\nQuelle direction souhaitez-vous prendre?\n(droite: d; gauche: g ou s; haut: h ou e; bas: b ou x)");
				
				char input = scan.next().charAt(0);
				
				// prend un char en param�tre (la valeur d'entr�e du joueur) et �value s'il s'agit d'une direction ou d'une option.
				String verificationInput = verifierInput(input);
				
				// switch case pour savoir si le joueur demande une direction (h, b, g, d) ou une option (p, q, v).
				// le case "unknown" est le cas par d�faut qui ne fait rien si le joueur n'a pas mis une entr�e reconnaissable
				switch(verificationInput){
				
				case "direction":
					
					// l'input est une direction, on appelle la m�thode gesionDeplacement
					char direction = input;
					gestionDeplacement(board, boardInvisible, Bonhomme, direction);
					
					break;
					
				case "option":
					
					// l'input est une option, on appelle la m�thode gestionOption
					char option = input;
					gestionOption(board, boardInvisible, option);
					break;
					
					
				case "unknown":
					
					// l'input n'est pas une entr�e valide, le programme ne fait rien avec �a
					break;
				
				}
			
			}
			
		}
		
	}
	
	// prend un char en param�tre (entr�e de l'utilisateur) et �value si c'est une direction ou une option
	public String verifierInput(char input){
		
		// retourne "direction" si l'entr�e est d, s, e, x, g, h ou b
		if(input == 'd' || input == 's' || input == 'e' || input == 'x' || input == 'g' || input == 'h' || input == 'b'){
			
			return "direction";
			
		}
		
		// retourne "option" si l'entr�e est q, p ou v.
		else if(input == 'q' || input == 'p' || input == 'v'){
			
			return "option";
			
		}
		
		// retourne unknown sinon.
		return "unknown";
		
	}
	
	// prend en param�tre les deux labyrinthes, le personnage et la direction du personnage et �value le d�placement souhait�
	public void gestionDeplacement(Labyrinthe board, Labyrinthe boardInvisible, Personnage Bonhomme, char direction){
		
		// boolean qui v�rifie si le d�placement du joueur est valide (mur ou pas)
		boolean peutSeDeplacer = Bonhomme.peutSeDeplacer(board, direction);
		
		// si � priori le personnage peut se d�placer
		if(peutSeDeplacer){
			
			// v�rifie si la direction du personnage est la sortie
			if(board.aSortieADroite(Bonhomme.getPositionI(), Bonhomme.getPositionJ())&& direction == 'd'){
				
				// si c'est la sortie, on f�licite le joueur et on appelle la m�thode gesionFinDePartie.
				System.out.println("Bravo, vous �tes parvenu jusqu'� la sortie en commettant seulement "+ (Bonhomme.getVieInitiale() -Bonhomme.getVie()) + " erreurs.");
				gestionFinDePartie();
					
			}
			
			// v�rifie si la direction du personnage est l'entr�e.
			else if(board.aEntreeAGauche(Bonhomme.getPositionI(), Bonhomme.getPositionJ()) && direction == 'g'){
				
				// si c'est l'entr�e, c'est trait� comme si c'�tait un mur invisible et non, il n'y a pas de sortie facile ici.
				aEntreDansUnMur(boardInvisible, Bonhomme, direction);
				
			}
			
			// sinon, le r�placement est valide et on appelle la fonction en cons�quence.
			else{
					
				effectuerDeplacement(board, boardInvisible, Bonhomme, direction);
					
			}
				
		}
		
		// si le d�placement n'est pas valide, alors on a entr� dans un mur et on appelle la fonction en cons�quence.
		else{
				
			aEntreDansUnMur(boardInvisible, Bonhomme, direction);
								
		}
			
	}
	
	// prend en param�tre les deux labyrinthes et l'option et �value l'option souhait�e.
	public void gestionOption(Labyrinthe board, Labyrinthe boardInvisible, char option){
		
		switch(option){
		
		// si 'q', alors la partie se termine et il n'y a pas de nouvelle partie non plus.
		case 'q':
			
			partieEnCours = false;
			rejouerPartie = false;
			System.out.println("Fin de partie.");
			break;
		
		// si 'p', alors la partie se termine mais une nouvelle va commencer.
		case 'p':
			
			partieEnCours = false;
			break;
		
		// si 'v', alors le labyrinthe est rendu visible au joueur.
		case 'v':
			
			board.copieLabyrinthe(boardInvisible);
			
		}
		
	}
	
	// g�re la fin d'une partie.
	public void gestionFinDePartie(){
		
		// termine la partie en cours et demande au joueur s'il veut rejouer.
		partieEnCours = false;
		System.out.println("Voulez-vous rejouer une autre partie? \noui/non"); 
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String reponse = scan.nextLine();
		
		// si la r�ponse est n'importe quoi sauf oui, il n'y a pas de nouveau labyrinthe et le jeu se termine.
		if(!reponse.equals("oui")){
			 
			rejouerPartie = false;
			System.out.println("Fin de partie.");
		}
		 
	}
	
	// effectue tous les appels n�c�ssaires � un d�placement valide dans le labyrinthe.
	public void effectuerDeplacement(Labyrinthe board, Labyrinthe boardInvisible, Personnage Bonhomme, char direction){
		
		// efface le personnage sur le labyrinthe , le labyrinthe invisible, actualise la position du personnage, 
		// dessine le personnage sur les deux labyrinthes avec la nouvelle position
		board.effacePersonnage(Bonhomme);
		boardInvisible.effacePersonnage(Bonhomme);
		Bonhomme.actualiserPosition(direction);
		board.dessinePersonnage(Bonhomme);
		boardInvisible.dessinePersonnage(Bonhomme);
	
	}
		
	// prend en param�tre le labyrinthe invisible, le personnage et la direction et g�re lorsque le joueur entre dans un mur.
	public void aEntreDansUnMur(Labyrinthe boardInvisible, Personnage Bonhomme, char direction){
		
		// personnage perd une vie.
		Bonhomme.perdreUneVie();
		
		// rend le mur visible.
		boardInvisible.rendreMurVisible(Bonhomme, direction);
		
		// �value si le personnage reste encore des vies.
		if(Bonhomme.getVie() == 0){
			
			System.out.println("Vous avez perdu, vous avez �puis� vos " + Bonhomme.getVieInitiale() + " vies!");
			gestionFinDePartie();
		
		}
		
	}
	
	// m�thode permettant d'endormir le programme pour x millisecondes.
	public static void sleep(long millisecondes){
		 
		try {
			
			Thread.sleep(millisecondes);
			 
		}
	 
		catch(InterruptedException e){
	 
			System.out.println("Sleep interrompu");
	 
		}
		 
	}
	
}



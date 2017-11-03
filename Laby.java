/* Fichier: Laby.java
 *
 * Auteurs: Philippe Brisson et
 * 
 * Le jeu de labyrinthe invisible est un jeu de mémoire qui fonctionne selon le principe suivant:
 * Au début du jeu, l'ordinateur crée un labyrinthe au hasard. Il crée au hasard une entrée par la gauche et une sortie
 * par la droite. Des murets sont construits à l’intérieur. Un personnage (représenté par @) est placé juste à l’entrée
 * du labyrinthe dans l'extrême gauche du labyrinthe
 *
*/

import java.util.Scanner;

public class Laby {
	
	// boolean rejouerPartie reste true tant qu'on veut rejouer une partie.
	// boolean partieEnCours reste true tant qu'on veut continuer à jouer la partie en cours.
	private boolean rejouerPartie;
	private boolean partieEnCours;
	
	// prend 5 arguments en paramètre et les changes en int / double
	public static void main(String[] args){
		
		if(args.length != 5){
			
			throw new IllegalArgumentException("Nombre de paramètres incorrects. \nUtilisation: java Laby <hauteur> <largeur> <densite> <duree visible> <nb vies>\nEx: java Laby 10 20 0.20 10 5 ");
		
		}
		// change tous les paramètres entrées par l'utilisateur et les change en int / double
		int hauteur = Integer.parseInt(args[0]);
		int largeur = Integer.parseInt(args[1]);
		double densite = Double.parseDouble(args[2]);
		int temps = Integer.parseInt(args[3]);
		int vies = Integer.parseInt(args[4]);
		
		// créé l'objet jeu qui sera servira de partie
		Laby jeu = new Laby();
		
		// appelle la fonction jeuLabyrintheInvisible avec tous les arguments passés en paramètre.
		jeu.jeuLabyrintheInvisible(hauteur, largeur, densite, temps, vies);
		
	}
	
	// constructeur qui rend les booleans rejouerPartie et partieEnCours true.
	Laby(){
		
		rejouerPartie = partieEnCours = true;
	}
	
	// gère la partie, rejoue une partie et quitte le programme
	public void jeuLabyrintheInvisible(int hauteur, int largeur, double densite, int temps, int vies){
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		// tant que rejouerPartie est true, le jeu continuera de gérer des labyrinthes
		while(rejouerPartie){
			
			// créé le labyrinthe initial, son entrée et sa sortie
			Labyrinthe board = new Labyrinthe(hauteur, largeur);
			int positionInitialeHasard = (int) Math.floor(Math.random()*hauteur+1)*4-2;
			board.InitieLabyrinthe(hauteur, largeur, densite, positionInitialeHasard);
			
			// créé le personnage et le positionne
			Personnage Bonhomme = new Personnage(board, positionInitialeHasard, 4, vies);
			board.dessinePersonnage(Bonhomme);
			
			// créé le labyrinthe invisible dans lequel le jeu se déroulera
			Labyrinthe boardInvisible = new Labyrinthe(board);
			board.rendreLabyrintheInvisible(boardInvisible);
			
			// "efface" l'écran avec 200 lignes vides, affiche le labyrinthe pendant le temps demandé par l'utilisateur
			Labyrinthe.effaceEcran();
			board.affiche();
			sleep(temps*1000);
			
			// on défini partie en cours = true ici pour commencer une nouvelle partie
			partieEnCours = true;
			
			// boucle while de la partie en cours. On sort de cette boucle seulement pour une nouvelle partie ou pas de partie du tout.
			while(partieEnCours){
				
				// "efface" l'écran, affiche le labyrinthe invisible et donne les instructions au joueur (nombre de vies, directions).
				Labyrinthe.effaceEcran();
				boardInvisible.affiche();
				System.out.println("Il vous reste " + Bonhomme.getVie() + " sur " + vies + ".\n\nQuelle direction souhaitez-vous prendre?\n(droite: d; gauche: g ou s; haut: h ou e; bas: b ou x)");
				
				char input = scan.next().charAt(0);
				
				// prend un char en paramètre (la valeur d'entrée du joueur) et évalue s'il s'agit d'une direction ou d'une option.
				String verificationInput = verifierInput(input);
				
				// switch case pour savoir si le joueur demande une direction (h, b, g, d) ou une option (p, q, v).
				// le case "unknown" est le cas par défaut qui ne fait rien si le joueur n'a pas mis une entrée reconnaissable
				switch(verificationInput){
				
				case "direction":
					
					// l'input est une direction, on appelle la méthode gesionDeplacement
					char direction = input;
					gestionDeplacement(board, boardInvisible, Bonhomme, direction);
					
					break;
					
				case "option":
					
					// l'input est une option, on appelle la méthode gestionOption
					char option = input;
					gestionOption(board, boardInvisible, option);
					break;
					
					
				case "unknown":
					
					// l'input n'est pas une entrée valide, le programme ne fait rien avec ça
					break;
				
				}
			
			}
			
		}
		
	}
	
	// prend un char en paramètre (entrée de l'utilisateur) et évalue si c'est une direction ou une option
	public String verifierInput(char input){
		
		// retourne "direction" si l'entrée est d, s, e, x, g, h ou b
		if(input == 'd' || input == 's' || input == 'e' || input == 'x' || input == 'g' || input == 'h' || input == 'b'){
			
			return "direction";
			
		}
		
		// retourne "option" si l'entrée est q, p ou v.
		else if(input == 'q' || input == 'p' || input == 'v'){
			
			return "option";
			
		}
		
		// retourne unknown sinon.
		return "unknown";
		
	}
	
	// prend en paramètre les deux labyrinthes, le personnage et la direction du personnage et évalue le déplacement souhaité
	public void gestionDeplacement(Labyrinthe board, Labyrinthe boardInvisible, Personnage Bonhomme, char direction){
		
		// boolean qui vérifie si le déplacement du joueur est valide (mur ou pas)
		boolean peutSeDeplacer = Bonhomme.peutSeDeplacer(board, direction);
		
		// si à priori le personnage peut se déplacer
		if(peutSeDeplacer){
			
			// vérifie si la direction du personnage est la sortie
			if(board.aSortieADroite(Bonhomme.getPositionI(), Bonhomme.getPositionJ())&& direction == 'd'){
				
				// si c'est la sortie, on félicite le joueur et on appelle la méthode gesionFinDePartie.
				System.out.println("Bravo, vous êtes parvenu jusqu'à la sortie en commettant seulement "+ (Bonhomme.getVieInitiale() -Bonhomme.getVie()) + " erreurs.");
				gestionFinDePartie();
					
			}
			
			// vérifie si la direction du personnage est l'entrée.
			else if(board.aEntreeAGauche(Bonhomme.getPositionI(), Bonhomme.getPositionJ()) && direction == 'g'){
				
				// si c'est l'entrée, c'est traité comme si c'était un mur invisible et non, il n'y a pas de sortie facile ici.
				aEntreDansUnMur(boardInvisible, Bonhomme, direction);
				
			}
			
			// sinon, le réplacement est valide et on appelle la fonction en conséquence.
			else{
					
				effectuerDeplacement(board, boardInvisible, Bonhomme, direction);
					
			}
				
		}
		
		// si le déplacement n'est pas valide, alors on a entré dans un mur et on appelle la fonction en conséquence.
		else{
				
			aEntreDansUnMur(boardInvisible, Bonhomme, direction);
								
		}
			
	}
	
	// prend en paramètre les deux labyrinthes et l'option et évalue l'option souhaitée.
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
	
	// gère la fin d'une partie.
	public void gestionFinDePartie(){
		
		// termine la partie en cours et demande au joueur s'il veut rejouer.
		partieEnCours = false;
		System.out.println("Voulez-vous rejouer une autre partie? \noui/non"); 
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String reponse = scan.nextLine();
		
		// si la réponse est n'importe quoi sauf oui, il n'y a pas de nouveau labyrinthe et le jeu se termine.
		if(!reponse.equals("oui")){
			 
			rejouerPartie = false;
			System.out.println("Fin de partie.");
		}
		 
	}
	
	// effectue tous les appels nécéssaires à un déplacement valide dans le labyrinthe.
	public void effectuerDeplacement(Labyrinthe board, Labyrinthe boardInvisible, Personnage Bonhomme, char direction){
		
		// efface le personnage sur le labyrinthe , le labyrinthe invisible, actualise la position du personnage, 
		// dessine le personnage sur les deux labyrinthes avec la nouvelle position
		board.effacePersonnage(Bonhomme);
		boardInvisible.effacePersonnage(Bonhomme);
		Bonhomme.actualiserPosition(direction);
		board.dessinePersonnage(Bonhomme);
		boardInvisible.dessinePersonnage(Bonhomme);
	
	}
		
	// prend en paramètre le labyrinthe invisible, le personnage et la direction et gère lorsque le joueur entre dans un mur.
	public void aEntreDansUnMur(Labyrinthe boardInvisible, Personnage Bonhomme, char direction){
		
		// personnage perd une vie.
		Bonhomme.perdreUneVie();
		
		// rend le mur visible.
		boardInvisible.rendreMurVisible(Bonhomme, direction);
		
		// évalue si le personnage reste encore des vies.
		if(Bonhomme.getVie() == 0){
			
			System.out.println("Vous avez perdu, vous avez épuisé vos " + Bonhomme.getVieInitiale() + " vies!");
			gestionFinDePartie();
		
		}
		
	}
	
	// méthode permettant d'endormir le programme pour x millisecondes.
	public static void sleep(long millisecondes){
		 
		try {
			
			Thread.sleep(millisecondes);
			 
		}
	 
		catch(InterruptedException e){
	 
			System.out.println("Sleep interrompu");
	 
		}
		 
	}
	
}



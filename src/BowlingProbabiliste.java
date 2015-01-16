import java.io.Console;


public class BowlingProbabiliste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// On a 10 quilles au départ
		
		System.out.println("Beginner");
		for (int i = 0; i < 100; i++) {
			System.out.print(probaStrike("Beginner")+ " ");
		}
		System.out.println("Casual");
		for (int i = 0; i < 100; i++) {
			System.out.print(probaStrike("Casual")+ " ");
		}
		System.out.println("Advanced");
		for (int i = 0; i < 100; i++) {
			System.out.print(probaStrike("Advanced")+ " ");
		}
		
		System.out.print(probaStrike("BLOP")+ " ");
		
		

	}
	
	public static int Lancer(String type){	
		int [] Scores = null;
		// First Lancer
		Scores[0] = probaStrike(type);
		// Second Lancer
		Scores[1] = probaStrike(type, Scores[0]);
		return 0;
		
	}
	
	private static int probaStrike(String type, int i) {
		
		return 0;
	}

	public static boolean isStrike(){
		return false;
	}
	
	public static boolean isSpare(){
		return false;
	}
	
	public static int probaStrike(String type){
		int nbQuille = 0;
		double alea;

				switch (type) {
				case "Beginner":
					// TODO externaliser les profils (classe ProfileType)
					alea = Math.random()*10;
					if(alea <= 1){
						nbQuille = RandomInRange(9, 10);
					}else if(alea <= 3) {
						nbQuille = RandomInRange(6, 8);
						
					} else{
						nbQuille = RandomInRange(0, 5);
					}					
					break;
				case "Casual":
					alea = Math.random()*10;
					if(alea <= 2){
						nbQuille = RandomInRange(9, 10);
					}else if(alea <= 3) {
						nbQuille = RandomInRange(0, 3);
						
					} else{
						nbQuille = RandomInRange(4, 8);
					}		
					break;
				case "Advanced":
					alea = Math.random()*10;
					if(alea <= 2){
						nbQuille = RandomInRange(0, 6);	
					} else{
						nbQuille = RandomInRange(7, 10);
					}
					break;				
				default:
					System.err.println("The profil given is unrecognized");
					break;
				}
			return nbQuille;	
				
	}
	
	public static int RandomInRange(int min, int max){
		int nbQuille = min + (int)(Math.random() * ((max - min) + 1));
		return nbQuille;
	}

}

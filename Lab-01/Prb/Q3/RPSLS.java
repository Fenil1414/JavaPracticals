import java.util.Random;
import java.util.Scanner;

enum Move {
    ROCK, PAPER, SCISSORS, LIZARD, SPOCK
}

class RPSLS {

    static int winner(Move p, Move c) {
        if (p == c)
            return 0;

        switch (p) {
            case ROCK:
                return (c == Move.SCISSORS || c == Move.LIZARD) ? 1 : -1;

            case PAPER:
                return (c == Move.ROCK || c == Move.SPOCK) ? 1 : -1;

            case SCISSORS:
                return (c == Move.PAPER || c == Move.LIZARD) ? 1 : -1;

            case LIZARD:
                return (c == Move.SPOCK || c == Move.PAPER) ? 1 : -1;

            case SPOCK:
                return (c == Move.SCISSORS || c == Move.ROCK) ? 1 : -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();

        int playerScore = 0, computerScore = 0;

        for (int i = 1; i <= 5; i++) {
            System.out.print("Enter move (ROCK/PAPER/SCISSORS/LIZARD/SPOCK): ");
            Move player = Move.valueOf(sc.next().toUpperCase());

            Move computer = Move.values()[r.nextInt(5)];

            System.out.println("Computer: " + computer);

            int result = winner(player, computer);

            if (result == 1) {
                System.out.println("You win this round!");
                playerScore++;
            } else if (result == -1) {
                System.out.println("Computer wins this round!");
                computerScore++;
            } else {
                System.out.println("Round Draw!");
            }

            System.out.println();
        }

        System.out.println("Your Score = " + playerScore);
        System.out.println("Computer Score = " + computerScore);

        if (playerScore > computerScore)
            System.out.println("You win!");
        else if (computerScore > playerScore)
            System.out.println("Computer wins!");
        else
            System.out.println("Match Draw!");

        sc.close();
    }
}
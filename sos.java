import java.util.Scanner;
import java.util.Random;

class SOS 
{ 
static class Move 
{ 
	int row, col;
	char c; 
};  

// This function returns true if there are moves 
// remaining on the board. It returns false if 
// there are no moves left to play. 
static Boolean isMovesLeft(char b[][]) 
{ 
	for (int i = 0; i < 3; i++) 
		for (int j = 0; j < 3; j++) 
			if (b[i][j] == '_') 
				return true; 
	return false; 
} 

static void printBoard(char b[][]){
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
			System.out.print(b[i][j]+" ");
		}
		System.out.println("");
	}
}


// edw apla tha checkarw an uparxei SOS, meta mesa sto max/min
// tha apodothei +10 h -10, opote tha to balw na kanei 
// return boolean kai MESA sthn minmax tha balw +10/-10

//xreiasthke na orisw panw apo mia T.K giati den mou ebgaine
static boolean evaluate(char b[][]) 
{ 
	// Checking for rows for SOS 
	for (int row = 0; row < 3; row++) 
	{ 
		if (b[row][0] == 's' && b[row][1] == 'o' && b[row][2] == 's') 
		{ 
			return true; 
		} 
	} 

	// Checking for columns for SOS 
	for (int col = 0; col < 3; col++) 
	{ 
		if (b[0][col] == 's' && b[1][col] == 'o' && b[2][col] == 's') 
		{ 
			return true; 
		} 
	}

	// Checking for diagonal SOS
	if (b[0][0] == 's' && b[1][1] == 'o' && b[2][2] == 's') 
	{ 
		return true; 
	} 

	if (b[0][2] == 's' && b[1][1] == 'o' && b[2][0] == 's') 
	{ 
		return true; 
	}  
 
	// Else if none of them have won then return 0 
	return false; 
}
static boolean evaluate2(char b[][]){

	//orizontio check gia SO / OS
	for(int i=0; i<3; i++){
		if(b[i][0]=='s' && b[i][1]=='o'){
			return true;
		}
	}
	for(int i=0; i<3; i++){
		if(b[i][1]=='o' && b[i][2]=='s'){
			return true;
		}
	}

	//katheto check gia SO / OS
	for(int i=0; i<3; i++){
		if(b[0][i]=='s' && b[1][i]=='o'){
			return true;
		}
	}

	for(int i=0; i<3; i++){
		if(b[1][i]=='o' && b[2][i]=='s'){
			return true;
		}
	}

	// diagwnia
	if(b[0][0] == 's' && b[1][1] == 'o'){return true;}
	if(b[1][1] == 'o' && b[2][2] == 's'){return true;}
	if(b[0][2] == 's' && b[1][1] == 'o'){return true;}
	if(b[1][1] == 'o' && b[2][0] == 's'){return true;}
	return false;
}

static boolean evaluate3(char b[][]){
	for(int i=0; i<3; i++){
		if(b[i][0]==b[i][1]){
			return true;
		}
	}

	for(int i=0; i<3; i++){
		if(b[i][1]==b[i][2]){
			return true;
		}
	}

	for(int i=0; i<3; i++){
		if(b[0][i]==b[1][i]){
			return true;
		}
	}

	for(int i=0; i<3; i++){
		if(b[1][i]==b[2][i]){
			return true;
		}
	}

	if(b[0][0] == b[1][1]){return true;}
	if(b[1][1] == b[2][2]){return true;}
	if(b[0][2] == b[1][1]){return true;}
	if(b[1][1] == b[2][0]){return true;}
	return false;
}

static boolean evaluate4(char b[][]){
	for(int i=0; i<3; i++){
		if(b[i][0]== 's' && b[i][2] == 's'){
			return true;
		}
	}

	for(int i=0; i<3; i++){
		if(b[0][i]== 's' && b[2][i] == 's'){
			return true;
		}
	}

	if(b[0][0] == 's' && b[2][2] == 's'){return true;}
	if(b[0][2] == 's' && b[2][0] == 's'){return true;}

	return false;
}

// This is the minimax function. It considers all 
// the possible ways the game can go and returns 
// the value of the board 
static int minimax(char board[][], 
					int depth, Boolean isMax) 
{ 
	boolean win = evaluate(board);
	boolean halfWin = evaluate2(board);
	boolean sameChar = evaluate3(board);
	boolean fk = evaluate4(board);
	int score; 
 
	if(win == true && isMax == true){score = 10; return score;}
	if(win == true && isMax == false){score = -10; return score;}

	//paromoiws, ALLA EBALA -6 wste na protimhsei na balei idio char DUO FORES
	if(fk == true && isMax == true){score = -6; return score;}
	if(fk == true && isMax == false){score = 6; return score;}	
	//edw dinw antithetes times giati DEN THELOUME SO/OS
	if(halfWin == true && isMax == true){score = -5; return score;}
	if(halfWin == true && isMax == false){score = 5; return score;}

 

	if (isMovesLeft(board) == false) 
		return 0; 
	//edw dinw antithetes times giati DEN THELOUME duo idia char sthn seira
	//if(sameChar == true && isMax == true){score = 0; return score;}
	//if(sameChar == true && isMax == false){score = 0; return score;}


	//MAX = COMPUTER
	if (isMax) 
	{
		int best = -1000;

		// Traverse all cells for "S"
		for (int i = 0; i < 3; i++) 
		{ 
			for (int j = 0; j < 3; j++) 
			{ 
				// Check if cell is empty 
				if (board[i][j]=='_') 
				{ 
					// Make the move 
					board[i][j] = 'o';
					best = Math.max(best, minimax(board, 
									 depth + 1, !isMax));
					board[i][j] = '_';
					board[i][j] = 's'; 

					// Call minimax recursively and choose 
					// the maximum value 
					best = Math.max(best, minimax(board, 
									depth + 1, !isMax)); 

					// Undo the move 
					board[i][j] = '_';

				} 
			} 
		}
		return best; 
	} 
	// If this minimizer's move 
	else
	{ 
		int best = 1000;

		// Traverse all cells for "S"
		for (int i = 0; i < 3; i++) 
		{ 
			for (int j = 0; j < 3; j++) 
			{ 
				// Check if cell is empty 
				if (board[i][j] == '_') 
				{ 
					// Make the move 
					board[i][j] = 'o';
					best =  Math.min(best, minimax(board, 
									depth + 1, !isMax));
					board[i][j] = '_';
					board[i][j] = 's'; 

					// Call minimax recursively and choose 
					// the minimum value 
					best = Math.min(best, minimax(board, 
									depth + 1, !isMax)); 

					// Undo the move 
					board[i][j] = '_';
				} 
			} 
		} 
		return best; 
	} 
} 

// This will return the best possible 
// move for the player 
static Move findBestMove(char board[][]) 
{ 
	int bestVal = -1000; 
	Move bestMove = new Move();
	bestMove.row = -1; 
	bestMove.col = -1;


	// Traverse all cells, evaluate minimax function 
	// for all empty cells. And return the cell 
	// with optimal value. First we do it for "S" 
	for (int i = 0; i < 3; i++) 
	{ 
		for (int j = 0; j < 3; j++) 
		{ 
			// Check if cell is empty 
			if (board[i][j] == '_') 
			{ 
				// Make the move 
				board[i][j] = 's'; 

				// compute evaluation function for this 
				// move.
				//-------- DEN KSERW AN PREPEI FALSE H TRUE
				int moveVal = minimax(board, 0, true); 

				// Undo the move 
				board[i][j] = '_'; 

				// If the value of the current move is 
				// more than the best value, then update 
				// best/ 
				if (moveVal > bestVal) 
				{ 
					bestMove.row = i; 
					bestMove.col = j;
					bestMove.c = 's'; 
					bestVal = moveVal; 
				}

				//Then we do it for "O"
				board[i][j] = 'o';

				moveVal = minimax(board, 0, true);

				// Undo the move 
				board[i][j] = '_';

				if (moveVal > bestVal) 
				{ 
					bestMove.row = i; 
					bestMove.col = j;
					bestMove.c = 'o'; 
					bestVal = moveVal; 
				} 

			} 
		} 
	}

	System.out.printf("The value of the best Move S " + 
							"is : %d\n\n", bestVal); 

	return bestMove; 
} 

// Driver code 
public static void main(String[] args) 
{ 
	char board[][] = {{ '_', '_', '_' }, 
					{ '_', '_', '_' }, 
					{ '_', '_', '_' }}; 


	Random rand = new Random();
	int yo = rand.nextInt(2);
	int pp[] = {0,2};
	//board[2][pp[yo]] = 'o';
	Scanner keyb = new Scanner(System.in);
	Move bestMove = findBestMove(board);
	board[bestMove.row][bestMove.col] = bestMove.c;
	boolean end = false;
	while(isMovesLeft(board) == true){
		printBoard(board);
		end = evaluate(board);
		if(end == true){System.out.println("THE end");break;}
		System.out.println("Enter S or O: ");
		char so = keyb.next().charAt(0);
		System.out.println("Enter ROW: ");
		int r = keyb.nextInt();
		System.out.println("Enter COLUMN: ");
		int c = keyb.nextInt();
		board[r][c] = so;
		end = evaluate(board);
		if(end == true){System.out.println("THE end");break;}
		bestMove = findBestMove(board);
		end = evaluate(board);
		if(bestMove.row == -1){System.out.println("THE end");break;}
		board[bestMove.row][bestMove.col] = bestMove.c;

	}
	printBoard(board); 
	if(isMovesLeft(board) == false && end == false){System.out.println("TIE");}
} 

} 
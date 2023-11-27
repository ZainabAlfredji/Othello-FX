package main;

import java.util.List;

import com.eudycontreras.othello.capsules.AgentMove;
import com.eudycontreras.othello.capsules.MoveWrapper;
import com.eudycontreras.othello.capsules.ObjectiveWrapper;
import com.eudycontreras.othello.controllers.Agent;
import com.eudycontreras.othello.controllers.AgentController;
import com.eudycontreras.othello.controllers.GameController;
import com.eudycontreras.othello.enumerations.BoardCellState;
import com.eudycontreras.othello.enumerations.BoardCellType;
import com.eudycontreras.othello.enumerations.PlayerTurn;
import com.eudycontreras.othello.models.GameBoardState;
import com.eudycontreras.othello.threading.ThreadManager;
import com.eudycontreras.othello.threading.TimeSpan;

public class ExampleAgentA extends Agent{

    //private PlayerTurn player_turn = PlayerTurn.PLAYER_ONE;

    private GameController gameController;
    private GameBoardState gameBoardState;

    public ExampleAgentA(GameController gameController) {
		this(gameController, PlayerTurn.PLAYER_ONE);
	}
	
	public ExampleAgentA(String name) {
		super(name, PlayerTurn.PLAYER_ONE);
	}
	
	public ExampleAgentA(GameController gameController, PlayerTurn playerTurn) {
		super(playerTurn);
        this.gameController = gameController;
	}

    @Override
    public AgentMove getMove(GameBoardState gameState) {
        resetCounters();
        return getABpruningMove(gameState);
        
    }


    private AgentMove getABpruningMove(GameBoardState gameState) {

        //setSearchDepth(8);

        long startTime = System.currentTimeMillis();
        int maxSearchTime = 5000; // 5 seconds in milliseconds

        //List<GameBoardState> childStates = gameState.generateChildStates(gameState);
        //gameState.addChildStates(childStates.toArray(new GameBoardState[0]));

        int bestScore = this.getPlayerTurn() == PlayerTurn.PLAYER_ONE ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        MoveWrapper bestMoveWrapper = null;

        List<ObjectiveWrapper> possibleMoves = AgentController.getAvailableMoves(gameState, this.getPlayerTurn());

        for (ObjectiveWrapper move : possibleMoves) {
            GameBoardState newState = AgentController.getNewState(gameState, move);
            int score = AB_pruning(newState, UserSettings.MAX_SEARCH_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, this.getPlayerTurn() != PlayerTurn.PLAYER_ONE);

            if ((this.getPlayerTurn() == PlayerTurn.PLAYER_ONE && score > bestScore) || 
                (this.getPlayerTurn() != PlayerTurn.PLAYER_ONE && score < bestScore)) {
                bestScore = score;
                bestMoveWrapper = new MoveWrapper(move);
            }

            if (AgentController.timeLimitExceeded(maxSearchTime, startTime)) {
                break; // Stop searching if time limit exceeded
            }
        }

        return bestMoveWrapper;

    }

    private int AB_pruning(GameBoardState node, int search_depth, int alpha, int beta, boolean max_player) {
        setNodesExamined(getNodesExamined() + 1);
        System.out.println("AB_pruning called - max_player: " + max_player);

        int currentDepth = UserSettings.MAX_SEARCH_DEPTH - search_depth;
        setSearchDepth(Math.max(getSearchDepth(), currentDepth));
        System.out.println("AB_pruning called - Search Depth: " + search_depth + ", Current Depth: " + currentDepth);


       

        if (search_depth == 0 || node.isTerminal())
        {
            setReachedLeafNodes(getReachedLeafNodes() + 1);
            System.out.println("Leaf Node Reached - Depth: " + currentDepth + ", Total Leaf Nodes: " + getReachedLeafNodes());
            return (int) node.getStaticScore(max_player ? BoardCellState.WHITE : BoardCellState.BLACK);
        } 
       
        System.out.println("Number of child states: " + node.getChildStates().size());
        if(max_player)
        {
            int max_evaluation = Integer.MIN_VALUE;
            

            for (GameBoardState child : node.getChildStates())
            {
                System.out.println("Going deeper - Current Depth: " + (UserSettings.MAX_SEARCH_DEPTH - (search_depth - 1)));
                int evaluation = AB_pruning(child, search_depth - 1, alpha, beta, !max_player);
                max_evaluation = Math.max(max_evaluation, evaluation);
                alpha = Math.max(alpha, evaluation);
                if (alpha >= beta)
                {
                    setPrunedCounter(getPrunedCounter() + 1);
                    System.out.println("Pruning at Min - Depth: " + currentDepth + ", Total Prunes: " + getPrunedCounter());
                    break;
                }
            }
            
            return max_evaluation;
        }
        else
        {
            int min_evaluation = Integer.MAX_VALUE;
           
            for (GameBoardState child : node.getChildStates())
            {
                int evaluation = AB_pruning(child, search_depth - 1, alpha, beta, !max_player);

                min_evaluation = Math.min(min_evaluation, evaluation);
                beta = Math.min(beta, evaluation);
                if(alpha >= beta)
                {
                    setPrunedCounter(getPrunedCounter() + 1);
                    System.out.println("Pruning at Min - Depth: " + currentDepth + ", Total Prunes: " + getPrunedCounter());
                    break;
                }
            }
            
            return min_evaluation;
        }
        
        
    }
    
    
}
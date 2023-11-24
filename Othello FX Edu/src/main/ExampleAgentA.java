package main;

import java.util.List;

import com.eudycontreras.othello.capsules.AgentMove;
import com.eudycontreras.othello.controllers.AgentController;
import com.eudycontreras.othello.controllers.Agent;
import com.eudycontreras.othello.enumerations.PlayerTurn;
import com.eudycontreras.othello.models.GameBoardState;
import com.eudycontreras.othello.threading.ThreadManager;
import com.eudycontreras.othello.threading.TimeSpan;

/**
 * <H2>Created by</h2> Eudy Contreras
 * <h4> Mozilla Public License 2.0 </h4>
 * Licensed under the Mozilla Public License 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <a href="https://www.mozilla.org/en-US/MPL/2.0/">visit Mozilla Public Lincense Version 2.0</a>
 * <H2>Class description</H2>
 * 
 * @author Eudy Contreras
 */
public class ExampleAgentA extends Agent{
	
	private ExampleAgentA() {
		super(PlayerTurn.PLAYER_ONE);
		// TODO Auto-generated constructor stub
	}
	
	private ExampleAgentA(PlayerTurn playerTurn) {
		super(playerTurn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Delete the content of this method and Implement your logic here!
	 */
	@Override
	public AgentMove getMove(GameBoardState gameState) {
		return getExampleMove(gameState);
	}
	
	/**
	 * Default template move which serves as an example of how to implement move
	 * making logic. Note that this method does not use Alpha beta pruning and
	 * the use of this method can disqualify you
	 * 
	 * @param gameState
	 * @return
	 */
	private AgentMove getExampleMove(GameBoardState gameState){
		/*int depth = 3; // Define your depth limit
        AgentMove bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

		// Use AgentController to generate possible moves
        List<AgentMove> possibleMoves = AgentController.generateMoves(gameState, getPlayerTurn());

        for (AgentMove move : possibleMoves) {
            GameBoardState newState = gameState.applyMove(move);
            int score = minimax(newState, depth - 1, alpha, beta, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;*/
		int waitTime = UserSettings.MIN_SEARCH_TIME; // 1.5 seconds
		
		ThreadManager.pause(TimeSpan.millis(waitTime)); // Pauses execution for the wait time to cause delay
		
		return AgentController.getExampleMove(gameState, playerTurn); // returns an example AI move Note: this is not AB Pruning
	}

	private int minimax(GameBoardState position, int depth, int alpha, int beta,  boolean maximizingPlayer){
		if(depth == 0 || position.isTerminal()){
			//return static evalutaion of position
		}
		if(maximizingPlayer){
			//int maxEval = Integer.MIN_VALUE
			//for each child of position
			//int eval = minimax(child, depth - 1, alpha, beta, false)
			//maxEval = Math.max(maxEval, eval)
			//alpha = Math.max(alpha, eval)
			//if(beta <= alpha){break}
			//return maxEval
		} else {
			//int minEval = Integer.MAX_VALUE
			//for each child of position
			//int eval = minimax(child, depth - 1, alpha, beta, true)
			//minEval = Math.min(maxEval, eval)
			//beta = Math.min(beta, eval)
			//if(beta <= alpha){break}
			//return minEval
		}
	}

	/*private int minimax(GameBoardState state, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || state.isTerminal()) {
            return AgentController.evaluate(state, getPlayerTurn());
        }

        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (AgentMove move : AgentController.generateMoves(state, getPlayerTurn())) {
                GameBoardState newState = state.applyMove(move);
                int eval = minimax(newState, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha)
                    break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (AgentMove move : AgentController.generateMoves(state, getOpponentTurn())) {
                GameBoardState newState = state.applyMove(move);
                int eval = minimax(newState, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha)
                    break;
            }
            return minEval;
        }
    }

    private PlayerTurn getOpponentTurn() {
        return getPlayerTurn() == PlayerTurn.PLAYER_ONE ? PlayerTurn.PLAYER_TWO : PlayerTurn.PLAYER_ONE;
    }*/


}

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze
	 *            initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a
		// given
		// position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		boolean[][] inFrontier = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		double[][] fVal = new double[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		// TODO initialize the root state and add
		// to frontier list
		// ...
		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		State initial_state = new State(maze.getPlayerSquare(), null, 0, 0);
		frontier.add(new StateFValuePair(initial_state, 0));
		inFrontier[initial_state.getX()][initial_state.getY()] = true;
		fVal[initial_state.getX()][initial_state.getY()] = initial_state.getGValue() + distance(initial_state);

		while (!frontier.isEmpty()) {
			// TODO return true if a solution has been found
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use frontier.poll() to extract the minimum stateFValuePair.
			// use frontier.add(...) to add stateFValue pairs

			// Update maxSizeOfFrontier
			if (frontier.size() > maxSizeOfFrontier)
				maxSizeOfFrontier = frontier.size();

			StateFValuePair currFpair = frontier.poll();
			// Update the explored list
			State current = currFpair.getState();
			explored[current.getX()][current.getY()] = true;

			// if a solution is found
			if (current.isGoal(maze)) {
				// Update maxDepthSearched
				maxDepthSearched = current.getDepth();
				// Update the cost
				cost = current.getGValue();

				// Update noOfNodesExpanded
				for (int i = 0; i < explored.length; i++) {
					for (int j = 0; j < explored[i].length; j++) {
						if (explored[i][j] == true)
							noOfNodesExpanded += 1;
					}
				}

				// Update the maze
				// Trace back from goal to start, changing the values of squares to '.'
				current = current.getParent(); // G should maintain as Goal
				while (current.getParent() != null) {

					maze.setOneSquare(current.getSquare(), '.');
					current = current.getParent();
				}

				return true;
			}

			// If current state is not a goal
			ArrayList<State> neighbors = current.getSuccessors(explored, maze);

			// Loop through the successors
			for (State neighbor : neighbors) {
				double cost = neighbor.getGValue() + distance(neighbor);
				StateFValuePair newPair = new StateFValuePair(neighbor, cost);
				// Check if a successor already exists in the frontier list
				if (inFrontier[neighbor.getX()][neighbor.getY()] == false) {
					frontier.add(newPair);
					inFrontier[neighbor.getX()][neighbor.getY()] = true;
					fVal[neighbor.getX()][neighbor.getY()] = cost;
				} else if (fVal[neighbor.getX()][neighbor.getY()] < cost) {
					// If the state is already in the frontier

					// If new node, n, has the same state as previous node, m, that is in Explored,
					// we know that the optimal path to the state is guaranteed to have already been
					// found; Therefore, node n can be thrown away. Remove m only when g(n) < g(m)

					frontier.add(newPair);
					fVal[neighbor.getX()][neighbor.getY()] = cost;
				}
			}
		}

		// TODO return false if no solution
		return false;
	}

	/**
	 * A helper method to calculate the Euclidean distance from the current state to
	 * goal
	 * 
	 * @return the Euclidean distance
	 */
	private double distance(State state) {
		return Math.hypot(maze.getGoalSquare().X - state.getX(), maze.getGoalSquare().Y - state.getY());
	}

}

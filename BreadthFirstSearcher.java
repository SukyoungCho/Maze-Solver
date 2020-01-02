import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze
	 *            initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state associated with
		// a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		boolean[][] frontier = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		State initial_state = new State(super.maze.getPlayerSquare(), null, 0, 0);
		queue.add(initial_state);
		frontier[initial_state.getX()][initial_state.getY()] = true;

		while (!queue.isEmpty()) {
			// TODO return true if find a solution
			// TODO maintain the cost, noOfNodesExpanded (a.k.a. noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use queue.pop() to pop the queue.
			// use queue.add(...) to add elements to queue

			// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

			// Update maxSizeofFrontier
			if (queue.size() > maxSizeOfFrontier)
				maxSizeOfFrontier = queue.size();

			State current = queue.pop();

			// Update the explored square
			explored[current.getX()][current.getY()] = true;

			// If it is a solution, return true
			if (current.isGoal(maze)) {
				// Update maxDepthSearched
				maxDepthSearched = current.getDepth();
				// cost update
				cost = current.getGValue();
				current = current.getParent();

				// Update noOfNodesExpanded
				for (int i = 0; i < explored.length; i++) {
					for (int j = 0; j < explored[i].length; j++) {
						if (explored[i][j])
							noOfNodesExpanded += 1;
					}
				}

				// Change paths to '.'
				while (current.getParent() != null) {
					maze.setOneSquare(current.getSquare(), '.');
					current = current.getParent();
				}
				return true;
			}

			// If we have not found the goal state
			ArrayList<State> neighbors = current.getSuccessors(explored, maze);

			// Loop through the successors to check if the state is already visited
			while (!neighbors.isEmpty()) {
				State neighbor = neighbors.remove(0);
				if (frontier[neighbor.getX()][neighbor.getY()] == false)
					queue.add(neighbor);
				frontier[neighbor.getX()][neighbor.getY()] = true;
			}
		}
		// return false if no solution
		return false;
	}
}

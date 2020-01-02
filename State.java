import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the BFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param visited
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
		// FILL THIS METHOD

		// TODO check all four neighbors in left, down, right, up order
		// TODO remember that each successor's depth and gValue are
		// +1 of this object.
		ArrayList<State> successors = new ArrayList<State>();

		int x = getX();
		int y = getY();

		// X is Vertical, and Y is Horizontal.
		// For Up and Down x > 0 && x < maze.getNoOfRows() - 1
		// For Left and Right y > 0 && y < maze.getNoOfCols() - 1

		// In LDRU order
		// Left
		if (y > 0 && maze.getSquareValue(x, y - 1) != '%' && !explored[x][y - 1]) {
			Square newSquare = new Square(x, y - 1);
			State newState = new State(newSquare, this, gValue + 1, depth + 1);
			successors.add(newState);
		}

		// Down
		if (x < maze.getNoOfRows() - 1 && maze.getSquareValue(x + 1, y) != '%' && !explored[x + 1][y]) {
			Square newSquare = new Square(x + 1, y);
			State newState = new State(newSquare, this, gValue + 1, depth + 1);
			successors.add(newState);
		}

		// Right
		if (y < maze.getNoOfCols() - 1 && maze.getSquareValue(x, y + 1) != '%' && !explored[x][y + 1]) {
			Square newSquare = new Square(x, y + 1);
			State newState = new State(newSquare, this, gValue + 1, depth + 1);
			successors.add(newState);
		}

		// Up
		if (x > 0 && maze.getSquareValue(x - 1, y) != '%' && !explored[x - 1][y]) {
			Square newSquare = new Square(x - 1, y);
			State newState = new State(newSquare, this, gValue + 1, depth + 1);
			successors.add(newState);
		}

		return successors;
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze
	 *            initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X && square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the BFS but you will need it in A-star search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}

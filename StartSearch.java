import java.io.FileNotFoundException;
import java.io.IOException;


/**
	 * This class is used to shoot the arrow to the target.
	 * @author Zicong Qu
	 */
public class StartSearch {
	private Map targetMap;
	private int numArrows;
	private int inertia = 0;
	private int direction = -1;
	
	/**
	 * Constructor takes the name of the file containing the description of the map
	 * and create an object of the class Map.
	 * @param filename the name of the file containing the description of the map
	 * @throws IOException 
	 */
	public StartSearch(String filename) throws IOException{
		try {
		targetMap = new Map(filename);
		numArrows = targetMap.quiverSize();
		}
		catch (IOException e){
			System.out.println("File not found.");
		}
	}
	
	
	public static void main(String[] args) {
		int targetNum = 0, countArrowShot = 1;
		int backTrack = 0;
		// Program to execute all the tests(provided in the instruction)
		if (args.length < 1) {
			System.out.println("You must provide the name of the input file");
			System.exit(0);
			}
		String mapFileName = args[0];

		try {
			int maxPathLength = Integer.parseInt(args[1]);
		} catch ( Exception e) {
			int maxPathLength = 14;
		}
		// Create stack to keep track of the cells we have passed.
		ArrayStack<MapCell> stack = new ArrayStack<MapCell>();
		try {
			StartSearch path = new StartSearch(mapFileName);
			// Cell is the cell we are currently on.
			MapCell cell = path.targetMap.getStart(); // Get the starting cell
			cell.markInitial();

			while (path.numArrows >= countArrowShot) {
				countArrowShot++;
				stack.push(cell);
				while (cell!=null) {
					MapCell next_cell = (MapCell) path.nextCell(cell); 
					// If we can find the next cell to move to.
					if (next_cell != null) {
						stack.push(next_cell);
						next_cell.markInStack();
						if (next_cell.isTarget()) {
							int stacksize = stack.size();
							for (int i = 0; i < stacksize; i ++) {
								MapCell deleteCell = (MapCell) stack.peek();
								deleteCell.markOutStack();
								stack.pop();
							}
							targetNum ++;
							cell = path.targetMap.getStart();
							path.direction = -1;
							path.inertia = 0;
							break;
						}
					}
					
					// When nextCell method cannot find the next cell to move to.
					else {
						int stacksize = stack.size();
						for (int i = 0; i < stacksize; i ++) {
							MapCell deleteCell = (MapCell) stack.peek();
							deleteCell.markOutStack();
							stack.pop();
						}
						cell = path.targetMap.getStart();
						path.direction = -1;
						path.inertia = 0;
						break;
					}
				cell = next_cell;					
				backTrack = 0;
				}
			}
		} 
		catch (IOException e) {
			System.out.println("Parameters are invalid.");
		}
		catch (InvalidMapException e) {
			System.out.println("A letter isn't a proper cell type");
		}
		catch (EmptyStackException e) {
			System.out.println("Stack is empty");
		}
		catch (InvalidNeighbourIndexException e) {
			System.out.println("Attempting to set/get a neighbour that is not index 0-3.");
		} 
		System.out.println(targetNum + " targets were hit.");
	}
	
	/** Generates the best possible next cell to move to.
	 *   @param the current cell we are at.
	 */
	public MapCell nextCell(MapCell cell) {
		MapCell next_cell = null;
		// 
		if ( direction >= 0 && direction <= 3) {
			if (cell.getNeighbour(direction) == null || cell.getNeighbour(direction).isBlackHole() || cell.getNeighbour(direction).isMarked()) {
				
			}
			else {
				if ( cell.getNeighbour(direction).isTarget() && !cell.getNeighbour(direction).isMarked()) {
					inertia++;
					return cell.getNeighbour(direction);
				}
				
				if ( cell.getNeighbour(direction).isCrossPath() && !cell.getNeighbour(direction).isMarked()) {
					inertia++;
					return cell.getNeighbour(direction);
				}
				
				if ( direction == 0 && cell.getNeighbour(direction).isVerticalPath() && !cell.getNeighbour(direction).isMarked()) {
					inertia++;
					return cell.getNeighbour(direction);
				}
				
				if ( direction == 1 && cell.getNeighbour(direction).isHorizontalPath() && !cell.getNeighbour(direction).isMarked()) {
					inertia++;
					return cell.getNeighbour(direction);
				}
				
				if ( direction == 2 && cell.getNeighbour(direction).isVerticalPath() && !cell.getNeighbour(direction).isMarked()) {
					inertia++;
					return cell.getNeighbour(direction);
				}
				
				if ( direction == 3 && cell.getNeighbour(direction).isHorizontalPath() && !cell.getNeighbour(direction).isMarked()) {
					inertia++;
					return cell.getNeighbour(direction);
				}
			}
		}
		
		// When we cannot turn anymore
		if ( inertia >= 3)
			return next_cell;
		System.out.println("My Direction at START = " + direction + " and my inertia is " + inertia);
		
		if ( cell.isHorizontalPath() || cell.isVerticalPath())
			return next_cell;
		
		for (int i = 0;  i < 4; i ++) {
			if (cell.getNeighbour(i) != null && !cell.getNeighbour(i).isBlackHole()) {
				if (!cell.getNeighbour(i).isMarked() && cell.getNeighbour(i).isTarget()) {
					next_cell = cell.getNeighbour(i);
					direction = i;
					inertia = 0;
					return	next_cell;		
				}
			}
		}
		
		for (int i = 0;  i < 4; i ++) {
			if (cell.getNeighbour(i) != null && !cell.getNeighbour(i).isBlackHole()) {
				if (!cell.getNeighbour(i).isMarked() && cell.getNeighbour(i).isCrossPath()) {
					next_cell = cell.getNeighbour(i);
					direction = i;
					inertia = 0;
					return	next_cell;		
				}
			}
		}
		
		if (cell.getNeighbour(0) != null && !cell.getNeighbour(0).isBlackHole()) {
			if (!cell.getNeighbour(0).isMarked() && cell.getNeighbour(0).isVerticalPath()) {
				next_cell = cell.getNeighbour(0);
				direction = 0;
				inertia = 0;
				return	next_cell;		
			}
		}
		
		if (cell.getNeighbour(1) != null && !cell.getNeighbour(1).isBlackHole()) {
			if (!cell.getNeighbour(1).isMarked() && cell.getNeighbour(1).isHorizontalPath()) {
				next_cell = cell.getNeighbour(1);
				direction = 1;
				inertia = 0;
				return	next_cell;		
			}
		}

		if (cell.getNeighbour(2) != null && !cell.getNeighbour(2).isBlackHole()) {
			if (!cell.getNeighbour(2).isMarked() && cell.getNeighbour(2).isVerticalPath()) {
				next_cell = cell.getNeighbour(2);
				direction = 2;
				inertia = 0;
				return	next_cell;		
			}
		}
		
		if (cell.getNeighbour(3) != null && !cell.getNeighbour(3).isBlackHole()) {
			if (!cell.getNeighbour(3).isMarked() && cell.getNeighbour(3).isHorizontalPath()) {
				next_cell = cell.getNeighbour(3);
				direction = 3;
				inertia = 0;
				return	next_cell;		
			}
		}
		inertia = 0; // Clear inertia every time we turn.
		return next_cell;
	}
}

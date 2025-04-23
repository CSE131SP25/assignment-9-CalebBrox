package assignment9;

import java.util.LinkedList;

import edu.princeton.cs.introcs.StdDraw;

public class Snake {

	private static final double SEGMENT_SIZE = 0.03;
	private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 0.5;
	private LinkedList<BodySegment> segments;
	private double deltaX;
	private double deltaY;
	
	public Snake() {
		segments = new LinkedList<>();
		segments.add(new BodySegment(0.5, 0.5, SEGMENT_SIZE));//FIXME - set up the segments instance variable
		deltaX = 0;
		deltaY = 0;
	}
	
	public void changeDirection(int direction) {
		if(direction == 1) { //up
			deltaY = MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 2) { //down
			deltaY = -MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 3) { //left
			deltaY = 0;
			deltaX = -MOVEMENT_SIZE;
		} else if (direction == 4) { //right
			deltaY = 0;
			deltaX = MOVEMENT_SIZE;
		}
	}
	
	/**
	 * Moves the snake by updating the position of each of the segments
	 * based on the current direction of travel
	 */
	public void move() {
		BodySegment head = segments.getFirst();
		double newX = head.getX() + deltaX;
		double newY = head.getY() + deltaY;
		
		BodySegment newHead = new BodySegment(newX, newY, SEGMENT_SIZE);
		segments.addFirst(newHead);
		segments.removeLast(); // Only remove if we're not growing//FIXME
	}
	
	/**
	 * Draws the snake by drawing each segment
	 */
	public void draw() {
		for (int i = 1; i < segments.size(); i++) {
			segments.get(i).draw(); // Draw body segments first
		}

		// Draw head with face
		BodySegment head = segments.getFirst();
		head.draw();

		drawBigHead(head.getX(), head.getY());
		drawFace(head.getX(), head.getY());
		
		
//		
//		for (BodySegment segment : segments) {
//			segment.draw();
//		}//FIXME
	}

	private void drawFace(double x, double y) {
		double eyeOffsetX = SEGMENT_SIZE * 0.33;
		double eyeOffsetY = SEGMENT_SIZE * 0.33;
		double eyeRadius = SEGMENT_SIZE * 0.15;

		// Draw two eyes
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledCircle(x - eyeOffsetX, y + eyeOffsetY, eyeRadius);
		StdDraw.filledCircle(x + eyeOffsetX, y + eyeOffsetY, eyeRadius);
		// draw mouth
		double smileRadius = SEGMENT_SIZE * 0.4;
		double smileYOffset = SEGMENT_SIZE * 0.3;
		StdDraw.setPenRadius(SEGMENT_SIZE * 0.15);
		StdDraw.arc(x, y - smileYOffset, smileRadius, 200, 340);
		
//		StdDraw.setPenColor(StdDraw.BLACK);
//		StdDraw.arc(x, y - 0.005, 0.006, 200, 340);
	}
	
	private void drawBigHead(double x, double y) {
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.filledCircle(x, y, SEGMENT_SIZE * 1.3); // slightly bigger than body
	}
	
	/**
	 * The snake attempts to eat the given food, growing if it does so successfully
	 * @param f the food to be eaten
	 * @return true if the snake successfully ate the food
	 */
	public boolean eatFood(Food f) {
		BodySegment head = segments.getFirst();
		double dx = head.getX() - f.getX();
		double dy = head.getY() - f.getY();
		double distance = Math.sqrt(dx * dx + dy * dy); // find the distance between the snake head and the food  	
		
		if (distance < SEGMENT_SIZE + f.getRadius()) {
			BodySegment tail = segments.getLast();
			BodySegment secondLast = segments.size() > 1 ? segments.get(segments.size() - 2) : tail; // ? : is an if else shortcut

			// Calculate direction of tail movement
			double offsetX = tail.getX() - secondLast.getX();
			double offsetY = tail.getY() - secondLast.getY();

			// Add 3 new segments in a line behind the tail
			for (int i = 1; i <= 3; i++) {
				double newX = tail.getX() + offsetX * i;
				double newY = tail.getY() + offsetY * i;
				segments.addLast(new BodySegment(newX, newY, SEGMENT_SIZE));
			}

			return true;
		}
		
//		
//		if (distance < SEGMENT_SIZE + f.getRadius()) {
//			// Simulate growth by adding a segment (don’t remove tail in move next time)
//			BodySegment tail = segments.getLast();
//			segments.addLast(new BodySegment(tail.getX(), tail.getY(), SEGMENT_SIZE));
//			return true;
//		}
		return false;//FIXME
	
	}
	
	/**
	 * Returns true if the head of the snake is in bounds
	 * @return whether or not the head is in the bounds of the window
	 */
	public boolean isInbounds() {
		BodySegment head = segments.getFirst();
		double x = head.getX();
		double y = head.getY();
		return (x >= 0 && x <= 1 && y >= 0 && y <= 1);//FIXME
		//return true;
	}
}

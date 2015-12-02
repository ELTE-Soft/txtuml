package hu.elte.txtuml.layout.visualizer.statements;

/***
 * Enumeration of the Types of Statements.
 * 
 * @author Bal�zs Gregorics
 *
 */
public enum StatementType
{
	/**
	 * No knowledge of this type.
	 */
	unknown,
	/**
	 * North
	 */
	north,
	/**
	 * South
	 */
	south,
	/**
	 * East
	 */
	east,
	/**
	 * West
	 */
	west,
	/**
	 * Above
	 */
	above,
	/**
	 * Below
	 */
	below,
	/**
	 * Right
	 */
	right,
	/**
	 * left
	 */
	left,
	/**
	 * Priority
	 */
	priority,
	/**
	 * Phantom
	 */
	phantom,
	/**
	 * Vertical
	 */
	vertical,
	/**
	 * Horizontal
	 */
	horizontal,
	/**
	 * Corridor size.
	 */
	corridorsize,
	/**
	 * Overlap arrange mode.
	 */
	overlaparrange;
	
	/**
	 * Returns whether {@link StatementType} st is appliable on Objects/Boxes.
	 * 
	 * @param st
	 *            {@link StatementType} to check.
	 * @return whether {@link StatementType} st is appliable on Objects/Boxes.
	 */
	public static boolean isOnObjects(StatementType st)
	{
		switch (st)
		{
			case above:
			case below:
			case right:
			case left:
			case north:
			case south:
			case east:
			case west:
				return true;
			case horizontal:
			case phantom:
			case priority:
			case unknown:
			case vertical:
			case corridorsize:
			case overlaparrange:
			default:
				return false;
		}
	}
}

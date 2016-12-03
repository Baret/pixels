package de.gleex.opc.fadecandy.pixel;

import java.util.ArrayList;

/**
 * An effect is a sequence of multiple frames with a fixed amount of time between each frame.
 *
 * @author Baret
 *
 */
public class Effect extends ArrayList<Frame> {
	/**
	 *
	 */
	private static final long serialVersionUID = 5388653077079699699L;
	private long timeBetweenFrames;

	public Effect(final long timeBetweenFrames) {
		this.timeBetweenFrames = timeBetweenFrames;
	}

	/**
	 * @return the timeBetweenFrames
	 */
	public long getTimeBetweenFrames() {
		return timeBetweenFrames;
	}

	/**
	 * @param timeBetweenFrames
	 *            the timeBetweenFrames to set
	 */
	public void setTimeBetweenFrames(final long timeBetweenFrames) {
		this.timeBetweenFrames = timeBetweenFrames;
	}
}

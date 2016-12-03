package de.gleex.opc.fadecandy.pixel;

import java.util.Arrays;

/**
 * A frame is an arrangement of a fixed number of pixels.
 *
 * @author Baret
 *
 */
public class Frame {
	private Pixel[] pixels;

	public Frame(final int pixelCount) {
		pixels = new Pixel[pixelCount];
		for (int i = 0; i < pixels.length; i++) {
			setPixel(i, new Pixel());
		}
	}

	public void setPixel(final int index, final Pixel newPixel) {
		pixels[index] = newPixel;
	}

	public Pixel getPixel(final int index) {
		return pixels[index];
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Frame [pixels=" + Arrays.toString(pixels) + "]";
	}

	public int getPixelCount() {
		return pixels.length;
	}
}

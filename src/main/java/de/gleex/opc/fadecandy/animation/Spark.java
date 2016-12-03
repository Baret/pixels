package de.gleex.opc.fadecandy.animation;

import de.gleex.opc.official.Animation;
import de.gleex.opc.official.PixelStrip;

public class Spark extends Animation {

	/** Colors of the chasing pixel. */
	int color[] = { makeColor(196, 196, 196), // White
			makeColor(128, 128, 0), // Yellow
			makeColor(96, 64, 0), // Yellow-orange
			makeColor(64, 32, 0), // orange
			makeColor(32, 0, 0), // red
			makeColor(16, 0, 0), // red
			makeColor(0, 0, 0), // black
	};

	int currentPixel;
	int numPixels;

	@Override
	public void reset(PixelStrip strip) {
		currentPixel = 0;
		numPixels = strip.getPixelCount();
	}

	@Override
	public boolean draw(PixelStrip strip) {

		for (int i = 0; i < color.length; i++) {
			strip.setPixelColor(pixNum(currentPixel, i), color[i]);
		}
		currentPixel = pixNum(currentPixel + 1, 0);
		return true;
	}

	/**
	 * Return the pixel number that is i steps behind number p.
	 */
	int pixNum(int p, int i) {
		return (p + numPixels - i) % numPixels;
	}

}

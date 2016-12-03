package de.gleex.opc.fadecandy.animation;

import de.gleex.opc.official.Animation;
import de.gleex.opc.official.PixelStrip;

public class Knightrider extends Animation {

	private int currentPixel = 0;
	private boolean goingUp = true;

	@Override
	public void reset(PixelStrip strip) {
		currentPixel = 0;
		goingUp = true;
		strip.clear();
	}

	@Override
	public boolean draw(PixelStrip strip) {
		int red = makeColor(200, 0, 0);
		strip.clear();
		setPix(strip, currentPixel, red);
		if (goingUp) {
			currentPixel++;
		} else {
			currentPixel--;
		}
		if (currentPixel >= strip.getPixelCount() - 1) {
			goingUp = false;
		}
		if(currentPixel <= 0) {
			goingUp = true;
		}
		return true;
	}

}

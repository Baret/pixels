package de.gleex.opc.fadecandy.animation;

import de.gleex.opc.official.Animation;
import de.gleex.opc.official.PixelStrip;

public class Cantest extends Animation {

	private int pixel;

	private int currentColor;
	private double currentPercentage;

	public Cantest(final int pixel) {
		this.pixel = pixel;
		currentColor = nextColor();
	}

	private int nextColor() {
		currentPercentage = 0.;
		if(getRed(currentColor) > 0) {
			return makeColor(0, 255, 0);
		} else if(getGreen(currentColor) > 0) {
			return makeColor(0, 0, 255);
		}
		return makeColor(255, 0, 0);
	}

	@Override
	public void reset(final PixelStrip strip) {
		strip.clear();
	}

	@Override
	public boolean draw(final PixelStrip strip) {
		final int brightness = (int) (255 * currentPercentage);
		final int color = fadeColor(currentColor, Math.min(brightness, 255));
		setPix(strip, pixel, color);
		currentPercentage += 0.1;
		if(currentPercentage >= 1.) {
			currentColor = nextColor();
		}
		return true;
	}

}

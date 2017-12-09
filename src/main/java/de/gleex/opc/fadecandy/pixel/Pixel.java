package de.gleex.opc.fadecandy.pixel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Pixel {
	@Id
	@GeneratedValue
	@Setter
	private Long id;
	private short red;
	private short green;
	private short blue;

	/**
	 * Creates a new color object with all values set to 0 ("black")
	 */
	public Pixel() {
		this((short) 0, (short) 0, (short) 0);
	}

	/**
	 * @param r
	 *            must be in 0-255
	 * @param g
	 *            must be in 0-255
	 * @param b
	 *            must be in 0-255
	 */
	public Pixel(final short r, final short g, final short b) {
		setRed(r);
		setGreen(g);
		setBlue(b);
	}

	public Pixel(final int r, final int g, final int b) {
		this((short) r, (short) g, (short) b);
	}

	/**
	 * Accepts only values in the range of 0 to 255 (including). Smaller values will be set to 0, bigger ones to 255.
	 *
	 * @param red
	 *            the red to set
	 */
	public void setRed(final short red) {
		this.red = (short) Math.max(0, Math.min(255, red));
	}

	/**
	 * Accepts only values in the range of 0 to 255 (including). Smaller values will be set to 0, bigger ones to 255.
	 *
	 * @param green
	 *            the green to set
	 */
	public void setGreen(final short green) {
		this.green = (short) Math.max(0, Math.min(255, green));
	}

	/**
	 * Accepts only values in the range of 0 to 255 (including). Smaller values will be set to 0, bigger ones to 255.
	 *
	 * @param blue
	 *            the blue to set
	 */
	public void setBlue(final short blue) {
		this.blue = (short) Math.max(0, Math.min(255, blue));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + red + "," + green + "," + blue + "]";
	}

	public byte[] toByteArray() {
		return new byte[] { (byte) red, (byte) green, (byte) blue };
	}
}

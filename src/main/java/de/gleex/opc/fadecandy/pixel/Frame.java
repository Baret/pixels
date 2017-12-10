package de.gleex.opc.fadecandy.pixel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.collections4.list.FixedSizeList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A frame is an arrangement of a fixed number of pixels.
 *
 * @author Baret
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Frame {
	@Id
	@GeneratedValue
	private Long id;
	@Getter
	@OneToMany(cascade=CascadeType.ALL)
	@Setter
	private List<Pixel> pixels;

	public Frame(final int pixelCount) {
		List<Pixel> tmpList = new ArrayList<>(pixelCount);
		for (int i = 0; i < pixelCount; i++) {
			tmpList.add(new Pixel());
		}
		pixels = FixedSizeList.fixedSizeList(tmpList);
	}

	public void setPixel(final int index, final Pixel newPixel) {
		pixels.set(index, newPixel);
	}

	public Pixel getPixel(final int index) {
		return pixels.get(index);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Frame [pixels=" + pixels.toString() + "]";
	}

	public int getPixelCount() {
		return pixels.size();
	}
}

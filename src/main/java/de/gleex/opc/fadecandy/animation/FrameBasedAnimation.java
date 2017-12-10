package de.gleex.opc.fadecandy.animation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.assertj.core.util.VisibleForTesting;

import de.gleex.opc.fadecandy.pixel.Frame;
import de.gleex.opc.fadecandy.pixel.Pixel;
import de.gleex.opc.official.Animation;
import de.gleex.opc.official.PixelStrip;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class FrameBasedAnimation extends Animation {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@OneToMany(cascade={CascadeType.ALL})
	private List<Frame> frames;
	@Transient
	private int currentFrame = 0;

	public FrameBasedAnimation(String name) {
		this.name = name;
		frames = new ArrayList<>();
	}

	@Override
	public void reset(PixelStrip strip) {
		currentFrame = 0;
		putCurrentFrameOnStrip(strip);
	}

	@VisibleForTesting
	boolean putCurrentFrameOnStrip(PixelStrip strip) {
		Frame frame = frames.get(currentFrame);
		if(frame.getPixelCount() != strip.getPixelCount()) {
			return false;
		}
		for(int i = 0; i < frame.getPixelCount(); i++) {
			final Pixel pixel = frame.getPixel(i);
			setPix(strip, i, makeColor(pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
		}
		return true;
	}

	@Override
	public boolean draw(PixelStrip strip) {
		boolean redraw = putCurrentFrameOnStrip(strip);
		currentFrame++;
		if(currentFrame >= frames.size()) {
			currentFrame = 0;
		}
		return redraw;
	}

	public void addFrame(Frame frame) {
		frames.add(frame);
	}

}

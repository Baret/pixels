package de.gleex.opc.fadecandy.animation;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.gleex.opc.fadecandy.pixel.Frame;
import de.gleex.opc.fadecandy.pixel.Pixel;
import de.gleex.opc.official.PixelStrip;

@RunWith(MockitoJUnitRunner.class)
public class FrameBasedAnimationTest {

	private FrameBasedAnimation animation;
	
	@Before
	public void setUp() throws Exception {
		animation = new FrameBasedAnimation("testanimation");
	}

	@Test
	public void testCycle() {
		int pixelcount = 1;
		Frame frame1 = mockFrame(pixelcount);
		Frame frame2 = mockFrame(pixelcount);
		List<Frame> frames = new ArrayList<>(2);
		frames.add(frame1);
		frames.add(frame2);
		animation.setFrames(frames);
		
		PixelStrip strip = mock(PixelStrip.class);
		when(strip.getPixelCount()).thenReturn(pixelcount);
		assertTrue(animation.draw(strip));
		assertTrue(animation.draw(strip));
		assertTrue(animation.draw(strip));
		
		verify(frame1, times(2)).getPixel(eq(0));
		verify(frame2, times(1)).getPixel(eq(0));
		verify(strip, times(3)).setPixelColor(eq(0), eq(0));
		verify(strip, times(3)).getPixelCount();
		verifyNoMoreInteractions(strip);
	}

	@Test
	public void testPxelCount() {
		Frame frame = mockFrame(2);
		List<Frame> frames = new ArrayList<>(1);
		frames.add(frame);
		animation.setFrames(frames);

		PixelStrip strip = mock(PixelStrip.class);
		when(strip.getPixelCount()).thenReturn(2);
		
		assertTrue(animation.putCurrentFrameOnStrip(strip));
		
		when(strip.getPixelCount()).thenReturn(1);
		assertFalse(animation.putCurrentFrameOnStrip(strip));
	}

	private Frame mockFrame(int pixelcount) {
		Frame frame = mock(Frame.class);
		when(frame.getPixelCount()).thenReturn(pixelcount);
		for(int i = 0; i < pixelcount; i++) {
			when(frame.getPixel(i)).thenReturn(new Pixel());
		}
		return frame;
	}

}

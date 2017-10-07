package de.gleex.opc.fadecandy.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.gleex.opc.fadecandy.animation.FrameBasedAnimation;

@Repository
public interface AnimationRepository extends CrudRepository<FrameBasedAnimation, Long> {
	// no methods yet
}

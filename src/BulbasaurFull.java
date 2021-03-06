import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BulbasaurFull extends Bulbasaur {

    private static final Random rand = new Random();

    public BulbasaurFull(String id, Point position,
                         List<PImage> images, int resourceLimit,
                         int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod, resourceLimit);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(getPosition(),
                Munchlax.class);

        if (fullTarget.isPresent() && moveTo(world, fullTarget.get(), scheduler)) {
            transform(world, scheduler, imageStore);
        }
        else {
            scheduleEvent(scheduler,
                    new Activity(this, world, imageStore),
                    getActionPeriod());
        }
    }


    public void transform(WorldModel world,
                          EventScheduler scheduler, ImageStore imageStore) {
        Movable bulbasaur = new BulbasaurNotFull(getId(), getPosition(), getImages(), getResourceLimit(),
                0, getActionPeriod(), getAnimationPeriod());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(bulbasaur);
        scheduler.scheduleActions(bulbasaur, world, imageStore);
    }

    public int getResourceCount() {
        return 0;
    }

    public void _moveToHelper(WorldModel world, Entity target, EventScheduler scheduler) {
    }

}

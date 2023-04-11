/**
 * <p>
 * superhero simulate obj
 * </p>
 *
 * @author Xiaotian Li 1141181
 * @since 2023-03-06 20:43
 */
public class Superhero extends Thread {

    // superhero id
    private final int id;

    private final Roster rosterNew;

    private final Roster rosterComplete;

    private Mission currentMission;

    private boolean isInRoom;

    private boolean isInMansion;

    // the home star
    private final Mansion mansion;

    public Superhero(int id, Roster rosterNew, Roster rosterComplete, Mansion mansion) {
        this.id = id;
        this.rosterNew = rosterNew;
        this.rosterComplete = rosterComplete;
        this.mansion = mansion;
        isInMansion = false;
        isInRoom = false;
    }

    @Override
    public void run() {
        // hero process loop
        while (!isInterrupted()) {
            runHeroLifeCycle();
        }
    }

    /**
     * one cycle of hero behaviors
     */
    private void runHeroLifeCycle() {
        try {
            // Try to enter mansion
           mansion.registerHeroInMansion(this.getHeroId());

            // mingling before meeting
            sleep(Params.getDiscussionTime());

            // enter meeting room
            mansion.registerHeroInRoom(this.getHeroId());

            // do things in the room
            workInTheRoom();

            // leave room
            mansion.registerHeroOutRoom(this.getHeroId());

            // mingling after meeting
            sleep(Params.getDiscussionTime());

            // try leave
            mansion.registerHeroOutMansion(this.getHeroId());

            // hero shout to work
            System.out.printf("Superhero %d sets of to complete Mission %d!%n", id, currentMission.getId());

            // pretend to conduct current mission by sleeping
            sleep(Params.getMissionTime());

            // mark mission as done, ready to go back
            currentMission.completed = true;
            System.out.printf("Superhero %d completed Mission %d!%n", id, currentMission.getId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * defines hero behaviors in the room
     *
     * @throws InterruptedException exception
     */
    private void workInTheRoom() throws InterruptedException {
        // if have current task, submit task
        if (currentMission != null) {
            rosterComplete.submitCompletedMission(currentMission);
            System.out.printf("Superhero %d releases Mission %d.%n", id, currentMission.getId());
        }

        // get new task
        currentMission = rosterNew.acquireNewMission();
        System.out.printf("Superhero %d acquires Mission %d.%n", id, currentMission.getId());
    }

    public int getHeroId() {
        return id;
    }
}

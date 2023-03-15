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
        while (true) {
            runHeroLifeCycle();
        }
    }

    /**
     * one cycle of hero behaviors
     */
    private void runHeroLifeCycle() {
        try {
            // Try to enter mansion
            synchronized (mansion.getMansionEntryLock()) {
                while (!enterMansion()) {
                    // if hero cannot enter the mansion, wait for notification
                    mansion.getMansionEntryLock().wait();
                }
            }

            // mingling before meeting
            sleep(Params.getDiscussionTime());

            // enter meeting room
            boolean isEnterSuccess = enterRoom();
            if (!isEnterSuccess) {
                throw new Exception(String.format("[Error] Hero %d enter room", id));
            }

            // do things in the room
            workInTheRoom();

            // leave room
            boolean isLeaveSuccess = leaveRoom();
            if (!isLeaveSuccess) {
                throw new Exception(String.format("[Error] Hero %d leave room", id));
            }

            // mingling after meeting
            sleep(Params.getDiscussionTime());

            // try leave
            synchronized (mansion.getMansionLeaveLock()) {
                while (!leaveMansion()) {
                    mansion.getMansionLeaveLock().wait();
                }
            }

            // hero shout to work
            System.out.printf("Superhero %d sets of to complete Mission %d!%n", id, currentMission.getId());

            // pretend to conduct current mission by sleeping
            sleep(Params.getMissionTime());

            // mark mission as done, ready to go back
            currentMission.completed = true;

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
        // wait start meeting
        synchronized (mansion.getMeetingStartLock()) {
            while (!mansion.isMeetingStarted()) {
                // wait until be notified for meeting
                mansion.getMeetingStartLock().wait();
            }
        }

        // System.out.println("[debug] passed the meeting start lock");

        // if have current task, submit task
        if (currentMission != null) {
            rosterComplete.addNew(currentMission);
             System.out.printf("Superhero %d releases Mission %d.%n", id, currentMission.getId());
        }

        // get new task
        Mission theNewMission;
        // System.out.println("[debug] before get mission, hero: " + id);
        do {
            theNewMission = rosterNew.getOne();
            // if (theNewMission != null) System.out.println("[debug] get mission: " + theNewMission.getId());
        } while (theNewMission == null);

        // System.out.println("[debug] after get mission, hero: " + id);

        // replace current mission to new one
        currentMission = theNewMission;
        System.out.printf("Superhero %d acquires Mission %d.%n", id, currentMission.getId());
    }

    /**
     * enter the mansion. Register id to the set.
     */
    private boolean enterMansion() {
        boolean isSuccess = mansion.registerHeroInMansion(id);
        if (isSuccess) {
            isInMansion = true;
        }
        return isSuccess;
    }

    /**
     * leave mansion.
     */
    private boolean leaveMansion() {
        boolean isSuccess = mansion.registerHeroOutMansion(id);
        if (isSuccess) {
            isInMansion = false;
        }

        return isSuccess;
    }

    /**
     * hero enter room
     */
    private boolean enterRoom() {
        boolean isSuccess = mansion.registerHeroInRoom(id);
        if (isSuccess) {
            isInRoom = true;
        }

        return isSuccess;
    }

    /**
     * hero leave room
     */
    private boolean leaveRoom() {
        boolean isSuccess = mansion.registerHeroOutRoom(id);
        if (isSuccess) {
            isInRoom = false;
        }

        return isSuccess;
    }

    public int getHeroId() {
        return id;
    }
}

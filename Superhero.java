/**
 * <p>
 *  superhero simulate obj
 * </p>
 *
 * @author 996Worker
 * @since 2023-03-06 20:43
 */
public class Superhero extends Thread {

    // super hero id
    private final int id;

    private final Roster rosterNew;

    private final Roster rosterComplete;

    private Mission currentMission;

    // the home star
    private final Mansion mansion;
    public Superhero(int id, Roster rosterNew, Roster rosterComplete, Mansion mansion) {
        this.id = id;
        this.rosterNew = rosterNew;
        this.rosterComplete = rosterComplete;
        this.mansion = mansion;
    }

    @Override
    public void run() {
        ;
    }

    /**
     * enter the mansion. Register id to the set.
     */
    private void enterMansion() {
        if (mansion.getInMansionIdSet().contains(id)) {
            // if already in mansion
            return;
        }

        if (mansion.getProfessorInMansion()) {
            // if professor in mansion
            return;
        }

        mansion.getInMansionIdSet().add(id);
        System.out.printf("Superhero %d enters Mansion.%n", id);
    }

    /**
     * leave mansion.
     */
    private void leaveMansion() {
        if (mansion.getSecretRoomIdSet().contains(id)) {
            // in the room can not leave the mansion
            return;
        }

        if (!mansion.getInMansionIdSet().contains(id)) {
            // if not in the mansion at all
            return;
        }

        if (mansion.getProfessorInMansion()) {
            // if professor in mansion
            return;
        }

        mansion.getInMansionIdSet().remove(id);
        System.out.printf("Superhero %d exits from Mansion.%n", id);
    }

    /**
     * hero enter room
     */
    private void enterRoom() {
        if (!mansion.getInMansionIdSet().contains(id)) {
            // hero should in mansion before enter room
            return;
        }

        if (mansion.getSecretRoomIdSet().contains(id)) {
            // if already in room
            return;
        }

        mansion.getSecretRoomIdSet().add(id);
        System.out.printf("Superhero %d enters the Secret Room.%n", id);
    }

    /**
     * hero leave room
     */
    private void leaveRoom() {
        if (!mansion.getInMansionIdSet().contains(id)) {
            // hero should in mansion before leaving room
            return;
        }

        if (!mansion.getSecretRoomIdSet().contains(id)) {
            // hero should in room before leave the room
            return;
        }

        mansion.getSecretRoomIdSet().add(id);
        System.out.printf("Superhero %d leaves the Secret Room.%n", id);
    }

    public int getHeroId() {
        return id;
    }

    public Roster getRosterNew() {
        return rosterNew;
    }

    public Roster getRosterComplete() {
        return rosterComplete;
    }

    public Mansion getMansion() {
        return mansion;
    }
}

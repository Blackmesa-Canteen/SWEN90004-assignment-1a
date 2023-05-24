import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Mansion simulation obj
 * </p>
 *
 * @author Xiaotian Li 1141181
 * @since 2023-03-06 20:34
 */
public class Mansion {

    // roster for holding new tasks
    private final Roster rosterNew;
    // roster for holding completed tasks
    private final Roster rosterComplete;
    // id string
    private final String id;

    // secret room hero id set
    private final Set<Integer> inRoomIdSet;

    // hero id set who are in Mansion now
    private final Set<Integer> inMansionIdSet;

    // Modification: added waitMeeting set
    private final Set<Integer> waitMeetingIdSet;
    private final Set<Integer> finishMeetingIdSet;

    private volatile boolean isProfessorInMansion;

    private volatile boolean isMeetingStarted;

    public Mansion(String id, Roster rosterNew, Roster rosterComplete) {
        this.id = id;
        this.rosterNew = rosterNew;
        this.rosterComplete = rosterComplete;
        inRoomIdSet = new HashSet<>();
        inMansionIdSet = new HashSet<>();
        waitMeetingIdSet = new HashSet<>();
        finishMeetingIdSet = new HashSet<>();
        isProfessorInMansion = false;
        isMeetingStarted = false;
    }

    /**
     * enter the mansion. Register id to the set.
     */
    public synchronized void registerHeroInMansion(int heroId) {

        if (inMansionIdSet.contains(heroId)) {
            // if already in mansion
            return;
        }

        while (isProfessorInMansion) {
            // if professor in mansion, can not in
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        inMansionIdSet.add(heroId);
        waitMeetingIdSet.add(heroId);
        System.out.printf("Superhero %d enters Mansion.%n", heroId);
    }

    /**
     * leave mansion.
     */
    public synchronized void registerHeroOutMansion(int heroId) {
        if (inRoomIdSet.contains(heroId)) {
            // in the room can not leave the mansion
            return;
        }

        if (!inMansionIdSet.contains(heroId)) {
            // if not in the mansion at all
            return;
        }

        while (isProfessorInMansion) {
            // if professor in mansion, can not out
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        inMansionIdSet.remove(heroId);
        finishMeetingIdSet.remove(heroId);
        System.out.printf("Superhero %d exits from Mansion.%n", heroId);
    }

    /**
     * hero enter room
     */
    public synchronized boolean registerHeroInRoom(int heroId) {
        if (!inMansionIdSet.contains(heroId)) {
            // hero should in mansion before enter room
            return false;
        }

        if (inRoomIdSet.contains(heroId)) {
            // if already in room
            return false;
        }

        inRoomIdSet.add(heroId);
        System.out.printf("Superhero %d enters the Secret Room.%n", heroId);

        // notify prof to start meeting
        notifyAll();
        return true;
    }

    /**
     * hero leave room
     */
    public synchronized boolean registerHeroOutRoom(int heroId) {
        if (!inMansionIdSet.contains(heroId)) {
            // hero should in mansion before leaving room
            return false;
        }

        if (!inRoomIdSet.contains(heroId)) {
            // hero should in room before leave the room
            return false;
        }

        inRoomIdSet.remove(heroId);
        waitMeetingIdSet.remove(heroId);
        finishMeetingIdSet.add(heroId);
        System.out.printf("Superhero %d leaves the Secret Room.%n", heroId);

        // notify professor to end meeting
        notifyAll();

        return true;
    }

    /**
     * register professor in mansion
     */
    public synchronized void registerProfessorInMansion() {
        isProfessorInMansion = true;
        System.out.println("Professor Z enters the Mansion.");
    }

    /**
     * professor start meeting
     */
    public synchronized void professorStartMeeting() {
//        while (inMansionIdSet.size() != inRoomIdSet.size()) {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
        // Modify: change start meeting condition
        while (inRoomIdSet.size() != inMansionIdSet.size() - finishMeetingIdSet.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isMeetingStarted = true;
        rosterComplete.setMeetingStarted(true);
        rosterNew.setMeetingStarted(true);
        System.out.println("Meeting begins!");

        // notify all waiting hero for meeting
        notifyAll();
    }

    /**
     * professor end meeting
     */
    public synchronized void professorEndMeeting() {
        while (inRoomIdSet.size() > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        isMeetingStarted = false;
        rosterComplete.setMeetingStarted(false);
        rosterNew.setMeetingStarted(false);
        System.out.println("Meeting ends!");
    }

    /**
     * register professor out mansion
     */
    public synchronized void registerProfessorOutMansion() {
        isProfessorInMansion = false;
        System.out.println("Professor Z leaves the Mansion.");

        // notify heroes to in/out mansion
        notifyAll();
    }

    public Roster getRosterNew() {
        return rosterNew;
    }

    public Roster getRosterComplete() {
        return rosterComplete;
    }

    public String getId() {
        return id;
    }

    public Set<Integer> getInRoomIdSet() {
        return inRoomIdSet;
    }

    public Set<Integer> getInMansionIdSet() {
        return inMansionIdSet;
    }

    public synchronized boolean isProfessorInMansion() {
        return isProfessorInMansion;
    }

    public synchronized void setProfessorInMansion(Boolean professorInMansion) {
        isProfessorInMansion = professorInMansion;
    }

    public synchronized boolean isMeetingStarted() {
        return isMeetingStarted;
    }

    public synchronized void setMeetingStarted(boolean meetingStarted) {
        isMeetingStarted = meetingStarted;
    }
}

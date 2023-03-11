import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  Mansion simulation obj
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
    private final Set<Integer>  inMansionIdSet;

    private volatile boolean isProfessorInMansion;

    // lock for superhero mansion entry
    private final Object mansionEntryLock = new Object();

    // lock for superhero mansion leaving
    private final Object mansionLeaveLock = new Object();

    private volatile boolean isMeetingStarted;

    // lock for meeting started
    private final Object meetingStartLock = new Object();

    // lock for meeting ended
    private final Object meetingEndLock = new Object();

    public Mansion(String id, Roster rosterNew, Roster rosterComplete) {
        this.id = id;
        this.rosterNew = rosterNew;
        this.rosterComplete = rosterComplete;
        inRoomIdSet = new HashSet<>();
        inMansionIdSet = new HashSet<>();
        isProfessorInMansion = false;
        isMeetingStarted = false;
    }

    /**
     * enter the mansion. Register id to the set.
     */
    public boolean registerHeroInMansion(int heroId) {
        synchronized (this) {
            if (isProfessorInMansion) {
                // if professor in mansion, can not in
                return false;
            }

            if (inMansionIdSet.contains(heroId)) {
                // if already in mansion
                return false;
            }

            inMansionIdSet.add(heroId);
            System.out.printf("Superhero %d enters Mansion.%n", heroId);
            return true;
        }
    }

    /**
     * leave mansion.
     */
    public boolean registerHeroOutMansion(int heroId) {
        synchronized (this) {
            if (isProfessorInMansion) {
                // if professor in mansion, can not out
                return false;
            }

            if (inRoomIdSet.contains(heroId)) {
                // in the room can not leave the mansion
                return false;
            }

            if (!inMansionIdSet.contains(heroId)) {
                // if not in the mansion at all
                return false;
            }

            inMansionIdSet.remove(heroId);
            System.out.printf("Superhero %d exits from Mansion.%n", heroId);
            return true;
        }
    }

    /**
     * hero enter room
     */
    public boolean registerHeroInRoom(int heroId) {
        synchronized (this) {
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
        }

        // try start meeting
        tryStartMeeting();
        return true;
    }

    /**
     * hero leave room
     */
    public boolean registerHeroOutRoom(int heroId) {
        synchronized (this) {
            if (!inMansionIdSet.contains(heroId)) {
                // hero should in mansion before leaving room
                return false;
            }

            if (!inRoomIdSet.contains(heroId)) {
                // hero should in room before leave the room
                return false;
            }

            inRoomIdSet.remove(heroId);
            System.out.printf("Superhero %d leaves the Secret Room.%n", heroId);
        }

        // try end meeting
        tryEndMeeting();
        return true;
    }

    /**
     * register professor in mansion
     */
    public void registerProfessorInMansion() {
        synchronized (this) {
            isProfessorInMansion = true;
            System.out.println("Professor Z enters the Mansion.");
        }

        tryStartMeeting();
    }

    /**
     * register professor out mansion
     */
    public void registerProfessorOutMansion() {
        synchronized (this) {
            isProfessorInMansion = false;
            System.out.println("Professor Z leaves the Mansion.");
        }

        // notify all waiting heroes for entering mansion
        synchronized (mansionEntryLock) {
            mansionEntryLock.notifyAll();
        }

        // notify all waiting heroes for leaving mansion
        synchronized (mansionLeaveLock) {
            mansionLeaveLock.notifyAll();
        }
    }

    /**
     * Once all superheroes who are present in the Mansion
     * have also entered the Secret Room, Professor Z starts a meeting
     */
    private void tryStartMeeting() {
        synchronized (meetingStartLock) {
            boolean isAllHeroInRoom = inMansionIdSet.size() == inRoomIdSet.size();
            if (isProfessorInMansion && isAllHeroInRoom) {
                isMeetingStarted = true;
                // notify all waiting hero for meeting
                meetingStartLock.notifyAll();

                System.out.println("Meeting begins!");
            }
        }
    }

    /**
     * Once all superheroes have left the Secret Room, Professor Z ends the meeting
     *
     */
    private void tryEndMeeting() {
        synchronized (meetingEndLock) {
            boolean isAllHeroOutRoom = inRoomIdSet.size() == 0;
            if (isAllHeroOutRoom) {
                isMeetingStarted = false;
                // notify waiting professor for meeting end event
                meetingEndLock.notifyAll();

                System.out.println("Meeting ends!");
            }

            // System.out.println("[debug] roomSize: " + inRoomIdSet.size());


        }
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

    public boolean isProfessorInMansion() {
        return isProfessorInMansion;
    }

    public void setProfessorInMansion(Boolean professorInMansion) {
        isProfessorInMansion = professorInMansion;
    }

    public Object getMansionEntryLock() {
        return mansionEntryLock;
    }

    public Object getMeetingStartLock() {
        return meetingStartLock;
    }

    public boolean isMeetingStarted() {
        return isMeetingStarted;
    }

    public void setMeetingStarted(boolean meetingStarted) {
        isMeetingStarted = meetingStarted;
    }

    public Object getMansionLeaveLock() {
        return mansionLeaveLock;
    }

    public Object getMeetingEndLock() {
        return meetingEndLock;
    }
}

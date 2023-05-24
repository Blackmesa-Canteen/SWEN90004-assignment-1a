import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * <p>
 *  The roster class for task recording
 * </p>
 *
 * @author Xiaotian Li 1141181
 * @since 2023-03-06 20:22
 */
public class Roster {

    // roster id string
    private String id;

    // mission collection
    private final Deque<Mission> missionList;

    // is meeting started
    private volatile boolean isMeetingStarted = false;

    public Roster(String id) {
        this.id = id;
        missionList = new LinkedList<>();
    }

    /**
     * add complete/new mission into the queue
     * @param mission mission obj
     */
    public synchronized void addNew(Mission mission) {
        System.out.printf(
                "Mission %d added to New Roster.%n", mission.getId());
        missionList.offer(mission);

        // notify users to get mission if new mission are added
        notifyAll();
    }

    /**
     * try to submit mission to completed mission.
     * Will block if meeting is not started
     */
    public synchronized void submitCompletedMission(Mission mission) {
        while (!isMeetingStarted()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        missionList.offer(mission);
    }

    /**
     * give out a mission if the meeting has been started.
     */
    public synchronized Mission acquireNewMission() {
        while (!isMeetingStarted || missionList.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return missionList.poll();
    }

    /**
     * remove a mission from the queue
     */
    public synchronized Mission removeComplete() {
        Mission mission = missionList.poll();
        if (mission != null) {
            System.out.printf(
                    "Mission %d removed from Complete Roster.%n", mission.getId()
            );
        }
        return missionList.poll();
    }

    /**
     * get one mission
     * @return mission obj
     */
    public synchronized Mission getOne() {
        return missionList.poll();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Deque<Mission> getMissionList() {
        return missionList;
    }

    public synchronized boolean isMeetingStarted() {
        return isMeetingStarted;
    }

    public synchronized void setMeetingStarted(boolean meetingStarted) {
        isMeetingStarted = meetingStarted;
        // notify waiting Roster lock
        notifyAll();
    }
}

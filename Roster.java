import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  The roster class for task recording
 * </p>
 *
 * @author 996Worker
 * @since 2023-03-06 20:22
 */
public class Roster {

    // roster id string
    private String id;

    // mission collection
    private final Deque<Mission> missionList;

    public Roster(String id) {
        this.id = id;
        missionList = new LinkedList<>();
    }

    /**
     * add complete/new mission into the queue
     * @param mission mission obj
     */
    public void addNew(Mission mission) {
        missionList.offer(mission);
    }

    /**
     * remove a mission from the queue
     */
    public void removeComplete() {
        missionList.poll();
    }

    /**
     * get one mission
     * @return mission obj
     */
    public Mission getOne() {
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
}

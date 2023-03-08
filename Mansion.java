import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  Mansion simulation obj
 * </p>
 *
 * @author 996Worker
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
    private final Set<Integer> secretRoomIdSet;

    // hero id set who are in Mansion now
    private final Set<Integer>  inMansionIdSet;

    private Boolean isProfessorInMansion;

    private Boolean isMeetingGoing;

    public Mansion(String id, Roster rosterNew, Roster rosterComplete) {
        this.id = id;
        this.rosterNew = rosterNew;
        this.rosterComplete = rosterComplete;
        secretRoomIdSet = new HashSet<>();
        inMansionIdSet = new HashSet<>();
        isProfessorInMansion = false;
        isMeetingGoing = false;
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

    public Set<Integer> getSecretRoomIdSet() {
        return secretRoomIdSet;
    }

    public Set<Integer> getInMansionIdSet() {
        return inMansionIdSet;
    }

    public Boolean getProfessorInMansion() {
        return isProfessorInMansion;
    }

    public Boolean getMeetingGoing() {
        return isMeetingGoing;
    }

    public void setProfessorInMansion(Boolean professorInMansion) {
        isProfessorInMansion = professorInMansion;
    }

    public void setMeetingGoing(Boolean meetingGoing) {
        isMeetingGoing = meetingGoing;
    }
}

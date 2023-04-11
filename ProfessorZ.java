/**
 * <p>
 *  Professor Z simulation obj
 * </p>
 *
 * @author Xiaotian Li 1141181
 * @since 2023-03-06 20:36
 */
public class ProfessorZ extends Thread {

    private final Mansion mansion;

    public ProfessorZ(Mansion mansion) {
        this.mansion = mansion;
    }

    @Override
    public void run() {
        try {
            while (true) {
                doProfessorLifeCycle();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * professor behaviors in a cycle
     */
    private void doProfessorLifeCycle() throws InterruptedException {
        // professor is outside mansion for a while
        sleep(Params.getProfWaitingTime());

        // professor go to mansion
        mansion.registerProfessorInMansion();

        // professor stay in mansion;
        sleep(Params.getDiscussionTime());

        // start meeting
        mansion.professorStartMeeting();

        // prof end meeting
        mansion.professorEndMeeting();

        // professor stay in mansion for some time;
        sleep(Params.getDiscussionTime());

        // professor out the mansion
        mansion.registerProfessorOutMansion();
    }

    public Mansion getMansion() {
        return mansion;
    }
}

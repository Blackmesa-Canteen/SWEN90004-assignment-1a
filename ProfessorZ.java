/**
 * <p>
 *  Professor Z simulation obj
 * </p>
 *
 * @author 996Worker
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
        sleep(Params.getProfWaitingTime());

        // if meeting is not ended, should not leave
        synchronized (mansion.getMeetingEndLock()) {
            if (mansion.isMeetingStarted()) {
                mansion.getMeetingEndLock().wait();
            }
        }

        // professor out the mansion
        mansion.registerProfessorOutMansion();
    }

    public Mansion getMansion() {
        return mansion;
    }
}

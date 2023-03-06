/**
 * <p>
 *  Professor Z simulation obj
 * </p>
 *
 * @author 996Worker
 * @since 2023-03-06 20:36
 */
public class ProfessorZ extends Thread {

    private final Mansion home;

    public ProfessorZ(Mansion home) {
        this.home = home;
    }

    @Override
    public void run() {
        ;
    }

    public Mansion getHome() {
        return home;
    }
}

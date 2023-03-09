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
        ;
    }

    public Mansion getMansion() {
        return mansion;
    }
}

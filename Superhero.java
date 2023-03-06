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

    // the home star
    private final Mansion home;
    public Superhero(int id, Roster rosterNew, Roster rosterComplete, Mansion home) {
        this.id = id;
        this.rosterNew = rosterNew;
        this.rosterComplete = rosterComplete;
        this.home = home;
    }

    @Override
    public void run() {
        ;
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

    public Mansion getHome() {
        return home;
    }
}

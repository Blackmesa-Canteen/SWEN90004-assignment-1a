/**
 * Parameters for the questing knights simulator.
 *
 * @author ngeard@unimelb.edu.au
 * @author Xiaotian Li 1141181
 *
 */

import java.util.Random;
import java.lang.Math;

class Params {

	static Random rnd = new Random();

	// number of knights in the simulator
    static final int NUM_SUPERHEROES = 3;

    // average duration that superheros spend discussing world affairs before and after meetings
    static final int MEAN_DISCUSSION_TIME = 2000;

    // average duration that knights spend completing a mission
    static final int MEAN_MISSION_TIME = 400;

    // average interval between Professor Z leaving and re-entering the Mansion
    static final int MEAN_PROF_WAITING_TIME = 2000;

    // duration between new quests being added
    static final int MISSION_ADDITION_TIME = 2000;

    // duration between completed quests being removed
    static final int MISSION_REMOVAL_TIME = 200;

    // generate a random discussion duration
    static int getDiscussionTime() {
        return (int) Math.max(0.0, rnd.nextGaussian() *
                MEAN_DISCUSSION_TIME / 2 + MEAN_DISCUSSION_TIME);
    }

    // generate a random questing duration
    static int getMissionTime() {
        return (int) Math.max(0.0, rnd.nextGaussian() *
        		MEAN_MISSION_TIME / 6 + MEAN_MISSION_TIME);
    }

    // generate a random interval for Professor Z to be away
    static int getProfWaitingTime() {
    	return (int) Math.max(0.0, (rnd.nextGaussian() *
    			MEAN_PROF_WAITING_TIME / 8) + MEAN_PROF_WAITING_TIME);
    }

}

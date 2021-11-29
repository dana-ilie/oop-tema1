package sort;

import actor.Actor;

import java.util.Comparator;

/**
 * Actors name comparator(ascending order)
 */
public class ActorNameSorterAsc implements Comparator<Actor> {
    /**
     * @param o1 first actor
     * @param o2 second actor
     * @return result of comparison
     */
    @Override
    public int compare(final Actor o1, final Actor o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

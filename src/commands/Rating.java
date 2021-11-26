package commands;

import entertainment.Movie;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActionInputData;
import user.User;

import java.util.ArrayList;

public class Rating extends Command {
    private double grade;

    public Rating(String username, String videoTitle, double grade) {
        super("rating", username, videoTitle);
        this.grade = grade;
    }


    public String commandAction(ArrayList<User> users, ArrayList<Movie> movies, ArrayList<Serial> serials, ActionInputData action) {
        // TODO Check if the videos were seen

        String message = super.commandAction(users, movies, serials, action);
        User user = new User();

        /*
         * find the user
         */
        for (User u : users) {
            if (u.getUsername().equals(super.getUsername())) {
                user = u;
            }
        }


        /*
         * rate a movie
         */
        if (action.getSeasonNumber() == 0) {
            if (user.getMoviesRated().size() == 0) {
                user.getMoviesRated().add(super.getVideoTitle());

                for (Movie movie : movies) {
                    if(movie.getTitle().equals(super.getVideoTitle())) {
                        movie.getRatings().add(grade);
                    }
                }

                message = "success -> " + super.getVideoTitle() + " was rated with " + grade + " by " + user.getUsername();
            } else {
                /*
                 * check if the movie was rated
                 */
                int movieWasRated = 0;
                for (String movieTitle : user.getMoviesRated()) {
                    if (movieTitle.equals(super.getVideoTitle())) {
                        message = "error -> " + movieTitle + " was already rated";
                        movieWasRated = 1;
                    }
                }
                if (movieWasRated == 0) {
                    user.getMoviesRated().add(super.getVideoTitle());

                    for (Movie movie : movies) {
                        if(movie.getTitle().equals(super.getVideoTitle())) {
                            movie.getRatings().add(grade);
                        }
                    }

                    message = "success -> " + super.getVideoTitle() + " was rated with " + grade + " by " + user.getUsername();
                }
            }
        } else {
            /*
             * rate a tv show season
             */
            int seasonNumber = action.getSeasonNumber();
            if (user.getRatedSerials().size() == 0 || user.getRatedSerials().get(super.getVideoTitle()) == null) {
                ArrayList<Integer> ratedSeasons = new ArrayList<Integer>();
                ratedSeasons.add(seasonNumber);
                user.getRatedSerials().put(super.getVideoTitle(), ratedSeasons);

                for (Serial serial : serials) {
                    if (serial.getTitle().equals(super.getVideoTitle())) {
                        serial.getSeasons().get(seasonNumber - 1).getRatings().add(grade);
                    }
                }

                message = "success -> " + super.getVideoTitle() + " was rated with " + grade + " by " + super.getUsername();
            } else {
                /*
                 * check if the season was rated
                 */
                int seasonWasRated = 0;
                for (Integer seasonNr : user.getRatedSerials().get(super.getVideoTitle())) {
                    if (seasonNr.equals(seasonNumber)) {
                        seasonWasRated = 1;
                        break;
                    }
                }

                if (seasonWasRated == 1) {
                    message = "error -> " + super.getVideoTitle() + " was already rated";
                } else {
                    user.getRatedSerials().get(super.getVideoTitle()).add(seasonNumber);
                    for (Serial serial : serials) {
                        if (serial.getTitle().equals(super.getVideoTitle())) {
                            serial.getSeasons().get(seasonNumber - 1).getRatings().add(grade);
                        }
                    }

                    message = "success -> " + super.getVideoTitle() + " was rated with " + grade + " by " + super.getUsername();
                }
            }
        }


        return message;
    }

}

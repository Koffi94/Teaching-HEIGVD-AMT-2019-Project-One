package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;
import ch.heigvd.amt.projectone.model.Session;
import ch.heigvd.amt.projectone.model.User;

import javax.ejb.Local;
import java.sql.Timestamp;
import java.util.LinkedList;

@Local
public interface SessionManagerLocal {

    // CRUD
    public void createSession(Timestamp sessionTime, String roomName, String roomProperty, Movie movie, User owner);
    public LinkedList<Session> findAllSessions(User owner);
    public void updateSession(int sessionId, Timestamp sessionTime, String roomName, String roomProperty, Movie movie, User owner);
    public void deleteSession(int sessionId);

}

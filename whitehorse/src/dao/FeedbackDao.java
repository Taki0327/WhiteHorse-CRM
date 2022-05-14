package dao;

import entity.Feedback;
import entity.User;

public interface FeedbackDao {
    public boolean InsertFeed(User user,Feedback feedback);
    public boolean SelectFeed(String feedtxt);
}

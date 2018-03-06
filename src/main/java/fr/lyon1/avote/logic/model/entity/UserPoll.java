package fr.lyon1.avote.logic.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_polls")
public class UserPoll implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserPoll)) {
            return false;
        }
        UserPoll other = (UserPoll) object;
        return (this.poll.getPollId() == other.poll.getPollId()) && (this.user.getUserId() == other.user.getUserId());
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (poll != null ? poll.hashCode() : 0);
        return result;
    }
}

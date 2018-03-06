package fr.lyon1.avote.logic.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.lyon1.avote.logic.service.MajorityVoteService;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "polls")
@JsonIgnoreProperties({"owner", "userPolls"})
public class Poll implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poll_id")
    private int pollId;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Lob
    @Column(name = "subject", columnDefinition = "TEXT")
    private String subject;
    @Column(name = "anonymity")
    private boolean anonymous;
    @Column(name = "poll_type")
    private PollType pollType;
    @Column(name = "number_rounds")
    private int numberRounds;
    @Column(name = "is_published")
    private boolean isPublished;
    @Column(name = "is_closed")
    private boolean isClosed;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "published_at")
    private Date publishedAt;
    @Column(name = "closed_at")
    private Date closedAt;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "restriction_id")
    @JsonManagedReference
    private Restriction restriction;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Choice> choices = new ArrayList<>();
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private Collection<UserPoll> userPolls = new ArrayList<>();

    public Poll() {
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public PollType getPollType() {
        return pollType;
    }

    public void setPollType(PollType pollType) {
        this.pollType = pollType;
    }

    public int getNumberRounds() {
        return numberRounds;
    }

    public void setNumberRounds(int numberRounds) {
        this.numberRounds = numberRounds;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    public Restriction getRestriction() {
        return restriction;
    }

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Collection<UserPoll> getUserPolls() {
        return userPolls;
    }

    public void setUserPolls(Collection<UserPoll> userPolls) {
        this.userPolls = userPolls;
    }

    public void addChoice(Choice choice) {
        choice.setPoll(this);
        choices.add(choice);
    }

    // used in velocity template -- DO NOT REMOVE THIS METHOD (even if it is marked unused)
    @JsonIgnore
    public Choice getMajorWinner() {
        return MajorityVoteService.getMajorityChoice(choices);
    }

}

package fr.lyon1.avote.logic.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "is_activated")
    private boolean isActivated = true;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(name = "created_at")
    private Date createdAt;
    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private UserAPI userAPI;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<UserPoll> userPolls = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Poll> polls = new ArrayList<>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public Date CreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Collection<UserPoll> getUserPolls() {
        return userPolls;
    }

    public Collection<Vote> getVotes() {
        return votes;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", password='" + password + '\'' + ", birthday=" + birthday + ", isDeleted=" + isDeleted + ", isActivated=" + isActivated + ", address=" + address + ", createdAt=" + createdAt + ", userPolls=" + userPolls + ", votes=" + votes + ", polls=" + polls + '}';
    }

    public UserAPI getUserAPI() {
        return userAPI;
    }

    public void setUserAPI(UserAPI userAPI) {
        this.userAPI = userAPI;
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        User user = (User) o;
//
//        if (userId != user.userId) return false;
//        if (isDeleted != user.isDeleted) return false;
//        if (isActivated != user.isActivated) return false;
//        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
//        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
//        if (email != null ? !email.equals(user.email) : user.email != null) return false;
//        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
//        if (password != null ? !password.equals(user.password) : user.password != null) return false;
//        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
//        if (address != null ? !address.equals(user.address) : user.address != null) return false;
//        if (createdAt != null ? !createdAt.equals(user.createdAt) : user.createdAt != null) return false;
//        if (userPolls != null ? !userPolls.equals(user.userPolls) : user.userPolls != null) return false;
//        if (votes != null ? !votes.equals(user.votes) : user.votes != null) return false;
//        return polls != null ? polls.equals(user.polls) : user.polls == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = userId;
//        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
//        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
//        result = 31 * result + (email != null ? email.hashCode() : 0);
//        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
//        result = 31 * result + (password != null ? password.hashCode() : 0);
//        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
//        result = 31 * result + (isDeleted ? 1 : 0);
//        result = 31 * result + (isActivated ? 1 : 0);
//        result = 31 * result + (address != null ? address.hashCode() : 0);
//        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
//        result = 31 * result + (userPolls != null ? userPolls.hashCode() : 0);
//        result = 31 * result + (votes != null ? votes.hashCode() : 0);
//        result = 31 * result + (polls != null ? polls.hashCode() : 0);
//        return result;
//    }
}

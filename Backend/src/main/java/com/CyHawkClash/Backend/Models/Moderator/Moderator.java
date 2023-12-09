package com.CyHawkClash.Backend.Models.Moderator;


import com.CyHawkClash.Backend.Models.User.User;
import jakarta.persistence.*;

@Entity
public class Moderator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "deleted")
    private boolean deleted;


    public Moderator(User user) {
        this.user = user;
        this.deleted = false;
    }

    public Moderator(){}

    public int getId(){
        return this.id;
    }
    public User getUser() {
        return user;
    }

    public boolean isDeleted(){
        return deleted;
    }

    public void delete(){
        this.deleted = true;
    }
}

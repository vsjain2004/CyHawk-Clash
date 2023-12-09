package com.CyHawkClash.Backend.Models.UserAchievement;

import com.CyHawkClash.Backend.Models.Achievement.Achievement;
import jakarta.persistence.*;
import com.CyHawkClash.Backend.Models.User.User;


@Entity
public class UserAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @Column(name ="progress")
    private float progress;

    @Column(name = "unlocked")
    private boolean unlocked;

    public UserAchievement(User user, Achievement achievement) {
        this.user = user;
        this.achievement = achievement;
        this.progress = 0;
        this.unlocked = false;
    }

    public UserAchievement() {
    }

    public int getId(){
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlock() {
        this.unlocked = true;
    }
}

package com.dreamgames.backendengineeringcasestudy.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private int coins = 2000;

    @Column(nullable = false)
    private int level = 1;

    @Column(nullable = false, length = 1)
    private Character abGroup;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partnership> partnershipsAsUser1 = new ArrayList<>();

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Partnership> partnershipsAsUser2 = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private LeaderBoard leaderBoard;

    @OneToMany(mappedBy = "inviterId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> sentInvitations = new ArrayList<>();

    @OneToMany(mappedBy = "invitedId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitation> recievedInvitations = new ArrayList<>();


    public User(String username, Character abGroup) {
        this.username = username;
        this.abGroup = abGroup;
    }

    public User() {

    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, username='%s', coins='%d', level='%d', abGroup='%s', createdAt='%s']",
                id, username, coins, level, abGroup, createdAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Character getAbGroup() {
        return abGroup;
    }

    public void setAbGroup(Character abGroup) {
        this.abGroup = abGroup;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Partnership> getPartnershipsAsUser1() {
        return partnershipsAsUser1;
    }

    public void setPartnershipsAsUser1(List<Partnership> partnershipsAsUser1) {
        this.partnershipsAsUser1 = partnershipsAsUser1;
    }

    public List<Partnership> getPartnershipsAsUser2() {
        return partnershipsAsUser2;
    }

    public void setPartnershipsAsUser2(List<Partnership> partnershipsAsUser2) {
        this.partnershipsAsUser2 = partnershipsAsUser2;
    }

    public LeaderBoard getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(LeaderBoard leaderBoard) {
        this.leaderBoard = leaderBoard;
    }
}

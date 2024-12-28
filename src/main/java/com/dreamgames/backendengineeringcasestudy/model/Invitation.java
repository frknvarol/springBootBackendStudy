package com.dreamgames.backendengineeringcasestudy.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1)
    private Character abGroup;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "inviter_id", nullable = false)
    private User inviterUser;

    @ManyToOne
    @JoinColumn(name = "invited_id", nullable = false)
    private User invitedUser;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING, ACCEPTED, REJECTED, DEPRECATED
    }



    public Invitation() {
    }

    public Invitation(User inviterUser, User invitedUser, Event event) {
        this.inviterUser = inviterUser;
        this.invitedUser = invitedUser;
        this.event = event;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getInviterUser() {
        return inviterUser;
    }

    public void setInviterUser(User inviterId) {
        this.inviterUser = inviterId;
    }

    public User getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(User invitedId) {
        this.invitedUser = invitedId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

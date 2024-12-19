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
    private User inviterId;

    @ManyToOne
    @JoinColumn(name = "invited_id", nullable = false)
    private User invitedId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    public Invitation() {
    }

    public Invitation(User inviterId, User invitedId, Event event) {
        this.inviterId = inviterId;
        this.invitedId = invitedId;
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

    public User getInviterId() {
        return inviterId;
    }

    public void setInviterId(User inviterId) {
        this.inviterId = inviterId;
    }

    public User getInvitedId() {
        return invitedId;
    }

    public void setInvitedId(User invitedId) {
        this.invitedId = invitedId;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

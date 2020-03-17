package com.example.assignment;

import com.example.asset.Asset;
import com.example.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Assignment {
    @Id
    private Long id;

    @Column(nullable = false, name = "start_time")
    private LocalDateTime start;

    @Column(name = "end_time")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Assignment() {
    }

    public Assignment(Long id, LocalDateTime start, LocalDateTime end) {
        if(id < 0) {
            throw new IllegalArgumentException();
        }
        this.id = id;

        if(start.isAfter(end)) {
            this.start = null;
        } else {
            this.start = start;
        }

        if(end.isBefore(start)) {
            this.end = null;
        } else {
            this.end = end;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(id < 0) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        if(start.isAfter(end)) {
            this.start = null;
        } else {
            this.start = start;
        }
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        if(end.isBefore(start)) {
            this.end = null;
        } else {
            this.end = end;
        }
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;

        Assignment that = (Assignment) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getStart() != null ? !getStart().equals(that.getStart()) : that.getStart() != null) return false;
        if (getEnd() != null ? !getEnd().equals(that.getEnd()) : that.getEnd() != null) return false;
        if (getAsset() != null ? !getAsset().equals(that.getAsset()) : that.getAsset() != null) return false;
        return getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (getAsset() != null ? getAsset().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}

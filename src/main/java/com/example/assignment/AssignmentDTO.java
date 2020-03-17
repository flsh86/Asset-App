package com.example.assignment;

import java.time.LocalDateTime;

public class AssignmentDTO {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long userId;
    private Long assetId;

    public AssignmentDTO() {
    }

    public AssignmentDTO(Long id, LocalDateTime start, LocalDateTime end, Long userId, Long assetId) {
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

        if(userId < 0) {
            throw new IllegalArgumentException();
        } else {
            this.userId = userId;
        }

        if(assetId < 0) {
            throw new IllegalArgumentException();
        } else {
            this.assetId = assetId;
        }

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        if(userId < 0) {
            throw new IllegalArgumentException();
        } else {
            this.userId = userId;
        }
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        if(assetId < 0) {
            throw new IllegalArgumentException();
        } else {
            this.assetId = assetId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssignmentDTO)) return false;

        AssignmentDTO that = (AssignmentDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getStart() != null ? !getStart().equals(that.getStart()) : that.getStart() != null) return false;
        if (getEnd() != null ? !getEnd().equals(that.getEnd()) : that.getEnd() != null) return false;
        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return getAssetId() != null ? getAssetId().equals(that.getAssetId()) : that.getAssetId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + (getAssetId() != null ? getAssetId().hashCode() : 0);
        return result;
    }
}

package com.example.user;

import java.time.LocalDateTime;

public class UserAssignmentDTO {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long assetId;
    private String assetName;
    private String assetSerialNumber;

    public UserAssignmentDTO() {
    }

    public UserAssignmentDTO(Long id, LocalDateTime start, LocalDateTime end, Long assetId, String assetName, String assetSerialNumber) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.assetId = assetId;
        this.assetName = assetName;
        this.assetSerialNumber = assetSerialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetSerialNumber() {
        return assetSerialNumber;
    }

    public void setAssetSerialNumber(String assetSerialNumber) {
        this.assetSerialNumber = assetSerialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAssignmentDTO)) return false;

        UserAssignmentDTO that = (UserAssignmentDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getStart() != null ? !getStart().equals(that.getStart()) : that.getStart() != null) return false;
        if (getEnd() != null ? !getEnd().equals(that.getEnd()) : that.getEnd() != null) return false;
        if (getAssetId() != null ? !getAssetId().equals(that.getAssetId()) : that.getAssetId() != null) return false;
        if (getAssetName() != null ? !getAssetName().equals(that.getAssetName()) : that.getAssetName() != null)
            return false;
        return getAssetSerialNumber() != null ? getAssetSerialNumber().equals(that.getAssetSerialNumber()) : that.getAssetSerialNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getStart() != null ? getStart().hashCode() : 0);
        result = 31 * result + (getEnd() != null ? getEnd().hashCode() : 0);
        result = 31 * result + (getAssetId() != null ? getAssetId().hashCode() : 0);
        result = 31 * result + (getAssetName() != null ? getAssetName().hashCode() : 0);
        result = 31 * result + (getAssetSerialNumber() != null ? getAssetSerialNumber().hashCode() : 0);
        return result;
    }
}

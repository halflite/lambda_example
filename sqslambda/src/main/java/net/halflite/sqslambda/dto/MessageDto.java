package net.halflite.sqslambda.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MessageDto {
  private final Long id;
  private final String statusType;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public MessageDto(Long id, String statusType) {
    this.id = id;
    this.statusType = statusType;
  }

  public Long getId() {
    return id;
  }

  public String getStatusType() {
    return statusType;
  }

  @Override
  public String toString() {
    return String.format("%s[id=%d, statusType=%s]", this.getClass().getSimpleName(), this.id, this.statusType);
  }
}

package com.Chatify.DTO;

import java.util.List;

public class CreateGroupChats {
   
    private String name;
    private List<Long> participantIDs;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getParticipantIDs() {
        return participantIDs;
    }
    
    public void setParticipantIDs(List<Long> participantIDs) {
        this.participantIDs = participantIDs;
    }
  
     
}

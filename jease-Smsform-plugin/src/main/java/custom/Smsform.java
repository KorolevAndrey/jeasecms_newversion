package custom;

import java.util.Date;
import jease.cms.domain.Content;

public class Smsform extends Content {

    private String topic;
    private String location;
    private Date start;
    private Date stop;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public StringBuilder getFulltext() {
        return super.getFulltext().append("\n")
                      .append(topic).append("\n")
                      .append(location);
    }

    public void replace(String target, String replacement) {
       super.replace(target, replacement);
       setTopic(getTopic().replace(target, replacement));
       setLocation(getLocation().replace(target, replacement));
    }

    public Smsform copy(boolean recursive) {
        Smsform meeting = (Smsform) super.copy(recursive);
        meeting.setTopic(getTopic());
        meeting.setLocation(getLocation());
        meeting.setStart(getStart());
        meeting.setStop(getStop());
        return meeting;
    }
}
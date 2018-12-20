package custom;

import jease.cms.web.content.editor.ContentEditor;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

public class MeetingEditor extends 
                    ContentEditor<Meeting> {

 Textbox topic = new Textbox();
 Textbox location = new Textbox();
 Datebox start = new Datebox();
 Datebox stop = new Datebox();

 public MeetingEditor() {
 }

 public void init() {
  add("Topic", topic, "Please enter topic.");
  add("Location", location);
  add("Start", start);
  add("Stop", stop);
 }

 public void load() {
  topic.setText(getNode().getTopic());
  location.setText(getNode().getLocation());
  start.setValue(getNode().getStart());
  stop.setValue(getNode().getStop());
 }

 public void validate() {
  validate(topic.getValue().isEmpty(), "Topic required");
  validate(location.getValue().isEmpty(), "Location required");
  validate(start.getValue() == null || stop.getValue() == null,
     "Date required");
  validate(start.getValue().after(stop.getValue()), 
      "Date invalid");
 }

 public void save() {
  getNode().setTopic(topic.getText());
  getNode().setLocation(location.getText());
  getNode().setStart(start.getValue());
  getNode().setStop(stop.getValue());
 }
}
package com.aliat.alm.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class Message {

	@Id  //id_Sequence
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Message_Seq")
	@SequenceGenerator(name = "Message_Seq", sequenceName = "Message_Seq", allocationSize=1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEXT")
    private String text;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", text=" + text + "]";
    }
}

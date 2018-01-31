package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Mail implements Serializable{

    @Id
    @Basic(optional = false)
    @Column
    private int id;

    @Column
    private int idSender;

    @Column
    private int idReceiver;

    @Column
    private String subject;

    @Column
    private String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    public int getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(int idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

package model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Authentication implements Serializable{

    @Id
    @Basic(optional = false)
    @Column
    private int id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String address;

    public Authentication(String login, String password, String address) {
        this.login = login;
        this.password = password;
        this.address = address;
    }

    public Authentication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

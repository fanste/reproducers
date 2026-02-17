package hhh20178;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tblTestUser"
        , uniqueConstraints = @UniqueConstraint(columnNames = {"varUserName"})
)
public class TblTestUser implements java.io.Serializable {


    private Integer intUserId;
    private String varUserName;
    private Set<TblTestUserContact> tblUserContacts = new HashSet<>(0);

    public TblTestUser() {
    }

    public TblTestUser(String varUserName) {
        this.varUserName = varUserName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "intUserID", unique = true, nullable = false, length = 36)
    public Integer getIntUserId() {
        return this.intUserId;
    }

    public void setIntUserId(Integer intUserId) {
        this.intUserId = intUserId;
    }


    @Column(name = "varUserName", nullable = false, length = 20)
    public String getVarUserName() {
        return this.varUserName;
    }

    public void setVarUserName(String varUserName) {
        this.varUserName = varUserName;
    }


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tblUser")
    public Set<TblTestUserContact> getTblUserContacts() {
        return this.tblUserContacts;
    }

    public void setTblUserContacts(Set<TblTestUserContact> tblUserContacts) {
        this.tblUserContacts = tblUserContacts;
    }

}



package hhh20178;


import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name="tblTestUserContact"
)

public class TblTestUserContact implements java.io.Serializable
 {


     private Integer intUserContactId;
     private int intContactType;
     private TblTestUser tblUser;
     private String varCompleted;

    public TblTestUserContact() {
    }

	    public TblTestUserContact(TblTestUser tblUser, int intContactType, String varCompleted) {
        this.tblUser = tblUser;
        this.intContactType = intContactType;
        this.varCompleted = varCompleted;
    }

   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="intUserContactID", unique=true, nullable=false)
    public Integer getIntUserContactId() {
        return this.intUserContactId;
    }
    public void setIntUserContactId(Integer intUserContactId) {
        this.intUserContactId = intUserContactId;
    }

    @Column(name="intUserContactTypeID", nullable=false)
    public int getIntContactType() {
        return this.intContactType;
    }
    public void setIntContactType(int intContactType) {
        this.intContactType = intContactType;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="intUserID", nullable=false)
    public TblTestUser getTblUser() {
        return this.tblUser;
    }
    public void setTblUser(TblTestUser tblUser) {
        this.tblUser = tblUser;
    }


    @Column(name="varCompleted", nullable=false, length=100)
    public String getVarCompleted() {
        return this.varCompleted;
    }
    public void setVarCompleted(String varCompleted) {
        this.varCompleted = varCompleted;
    }
}



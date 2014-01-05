/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrazhribernik
 */
@Entity
@Table(name = "LostPasswordRequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LostPasswordRequest.findAll", query = "SELECT l FROM LostPasswordRequest l ORDER BY l.isRead ASC"),
    @NamedQuery(name = "LostPasswordRequest.findByIdLostPasswordRequest", query = "SELECT l FROM LostPasswordRequest l WHERE l.idLostPasswordRequest = :idLostPasswordRequest"),
    })
public class LostPasswordRequest implements Serializable {
    @Column(name = "isRead")
    private Boolean isRead;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLostPasswordRequest")
    private Integer idLostPasswordRequest;
    @JoinColumn(name = "User_idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User useridUser;

    public LostPasswordRequest() {
    }

    public LostPasswordRequest(Integer idLostPasswordRequest) {
        this.idLostPasswordRequest = idLostPasswordRequest;
    }

    public Integer getIdLostPasswordRequest() {
        return idLostPasswordRequest;
    }

    public void setIdLostPasswordRequest(Integer idLostPasswordRequest) {
        this.idLostPasswordRequest = idLostPasswordRequest;
    }


    public User getUseridUser() {
        return useridUser;
    }

    public void setUseridUser(User useridUser) {
        this.useridUser = useridUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLostPasswordRequest != null ? idLostPasswordRequest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LostPasswordRequest)) {
            return false;
        }
        LostPasswordRequest other = (LostPasswordRequest) object;
        if ((this.idLostPasswordRequest == null && other.idLostPasswordRequest != null) || (this.idLostPasswordRequest != null && !this.idLostPasswordRequest.equals(other.idLostPasswordRequest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.entity.LostPasswordRequest[ idLostPasswordRequest=" + idLostPasswordRequest + " ]";
    }


    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
    
}

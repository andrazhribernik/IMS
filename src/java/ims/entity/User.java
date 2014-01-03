/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *JPA entity User, which represents table user in database.
 * @author andrazhribernik
 */
@Entity
@Table(name = "User")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findAllExceptAdmin", query = "SELECT u FROM User u WHERE u.roleidRole.name <> 'admin'"),
    @NamedQuery(name = "User.findByIdUser", query = "SELECT u FROM User u WHERE u.idUser = :idUser"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")})
public class User implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useridUser")
    private Set<LostPasswordRequest> lostPasswordRequestSet;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUser")
    private Integer idUser;
    @Size(max = 45)
    @Column(name = "username")
    private String username;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @ManyToMany(mappedBy = "userSet")
    private Set<Image> imageSet;
    @JoinColumn(name = "Role_idRole", referencedColumnName = "idRole")
    @ManyToOne(optional = false)
    private Role roleidRole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useridUser")
    private Set<Image> imageSet1;

    /**
     * Constructor
     */
    public User() {
    }
    /**
     * Constructor
     * @param idUser 
     */
    public User(Integer idUser) {
        this.idUser = idUser;
    }
    /**
     * Get id of User
     * @return id
     */
    public Integer getIdUser() {
        return idUser;
    }
    /**
     * Set id of user
     * @param idUser 
     */
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
    /**
     * Get username of User
     * @return username 
     */
    public String getUsername() {
        return username;
    }
    /**
     * Set username of User
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Get password of user
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Set password of user
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Get Set of Images that were bought by User
     * @return Set of Images
     */
    @XmlTransient
    public Set<Image> getImageSet() {
        return imageSet;
    }
    /**
     * Set Set of Images that were bought by User
     * @param imageSet 
     */
    public void setImageSet(Set<Image> imageSet) {
        this.imageSet = imageSet;
    }
    /**
     * Get Role of User
     * @return Role
     */
    public Role getRoleidRole() {
        return roleidRole;
    }
    /**
     * Set Role of User
     * @param roleidRole 
     */
    public void setRoleidRole(Role roleidRole) {
        this.roleidRole = roleidRole;
    }
    /**
     * Get Set of Images that are owned/uploaded by User
     * @return Set of images
     */
    @XmlTransient
    public Set<Image> getImageSet1() {
        return imageSet1;
    }
    /**
     * Set Set of Images that are owned/uploaded by User
     * @param imageSet1 
     */
    public void setImageSet1(Set<Image> imageSet1) {
        this.imageSet1 = imageSet1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.entity.User[ idUser=" + idUser + " ]";
    }

    @XmlTransient
    public Set<LostPasswordRequest> getLostPasswordRequestSet() {
        return lostPasswordRequestSet;
    }

    public void setLostPasswordRequestSet(Set<LostPasswordRequest> lostPasswordRequestSet) {
        this.lostPasswordRequestSet = lostPasswordRequestSet;
    }
    
}

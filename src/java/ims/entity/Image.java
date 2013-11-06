/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *JPA entity Image, which represents a table image in database.
 * @author andrazhribernik
 */
@Entity
@Table(name = "Image")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findByIdImage", query = "SELECT i FROM Image i WHERE i.idImage = :idImage"),
    @NamedQuery(name = "Image.findByName", query = "SELECT i FROM Image i WHERE i.name = :name"),
    @NamedQuery(name = "Image.findByPassword", query = "SELECT i FROM Image i WHERE i.password = :password"),
    @NamedQuery(name = "Image.findByDate", query = "SELECT i FROM Image i WHERE i.date = :date")})
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idImage")
    private Integer idImage;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinTable(name = "Image_has_User", joinColumns = {
        @JoinColumn(name = "Image_idImage", referencedColumnName = "idImage")}, inverseJoinColumns = {
        @JoinColumn(name = "User_idUser", referencedColumnName = "idUser")})
    @ManyToMany()
    private Set<User> userSet;
    @JoinColumn(name = "User_idUser", referencedColumnName = "idUser")
    @ManyToOne(optional = false)
    private User useridUser;
    @JoinColumn(name = "Category_idCategory", referencedColumnName = "idCategory")
    @ManyToOne(optional = false)
    private Category categoryidCategory;
    /**
     * Constructor
     */
    public Image() {
    }
    /**
     * Constructor
     * @param idImage 
     */
    public Image(Integer idImage) {
        this.idImage = idImage;
    }
    /**
     * Constructor
     * @param idImage
     * @param name 
     */
    public Image(Integer idImage, String name) {
        this.idImage = idImage;
        this.name = name;
    }
    
    /**
     * Getter
     * @return imageId
     */
    public Integer getIdImage() {
        return idImage;
    }
    /**
     * Setter
     * @param idImage 
     */
    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }
    
    /**
     * Getter
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Return image password for full-access to object Image
     * @return password 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Set a password for full-access to object Image
     * @param password 
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Getter
     * @return Date of when the image was created
     */
    public Date getDate() {
        return date;
    }
    /**
     * Set the date of image creation
     * @param date 
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
    /**
     * Get the Set of Users that bought that image
     * @return Set of Users
     */
    @XmlTransient
    public Set<User> getUserSet() {
        return userSet;
    }
    /**
     * Set the Set of Users that bought that image
     * @param userSet 
     */
    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }
    
    /**
     * Get the user who is the owner of that image. 
     * Normally this is the user who was uploaded this Image.
     * @return User
     */
    public User getUseridUser() {
        return useridUser;
    }
    /**
     * Set the user who is the owner of that image.
     * Normally this is the user who was uploaded this Image.
     * @param useridUser 
     */
    public void setUseridUser(User useridUser) {
        this.useridUser = useridUser;
    }
    /**
     * Get Category of this image.
     * @return Category 
     */
    public Category getCategoryidCategory() {
        return categoryidCategory;
    }
    /**
     * Set category of this image.
     * @param categoryidCategory 
     */
    public void setCategoryidCategory(Category categoryidCategory) {
        this.categoryidCategory = categoryidCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImage != null ? idImage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.idImage == null && other.idImage != null) || (this.idImage != null && !this.idImage.equals(other.idImage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.entity.Image[ idImage=" + idImage + " ]";
    }
    
}

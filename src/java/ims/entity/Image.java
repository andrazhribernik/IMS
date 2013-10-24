/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrazhribernik
 */
@Entity
@Table(name = "Image")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findByIdImage", query = "SELECT i FROM Image i WHERE i.idImage = :idImage"),
    @NamedQuery(name = "Image.findByDate", query = "SELECT i FROM Image i WHERE i.date = :date"),
    @NamedQuery(name = "Image.findByName", query = "SELECT i FROM Image i WHERE i.name = :name"),
    @NamedQuery(name = "Image.findByPassword", query = "SELECT i FROM Image i WHERE i.password = :password")})
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idImage")
    private Integer idImage;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "Users_idUsers", referencedColumnName = "idUser")
    @ManyToOne(fetch = FetchType.LAZY)
    private User usersidUsers;
    @JoinColumn(name = "Category_idCategory", referencedColumnName = "idCategory")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryidCategory;

    public Image() {
    }

    public Image(Integer idImage) {
        this.idImage = idImage;
    }

    public Integer getIdImage() {
        return idImage;
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUsersidUsers() {
        return usersidUsers;
    }

    public void setUsersidUsers(User usersidUsers) {
        this.usersidUsers = usersidUsers;
    }

    public Category getCategoryidCategory() {
        return categoryidCategory;
    }

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
        return "enterprise.web_jpa_war.entity.Image[ idImage=" + idImage + " ]";
    }
    
}

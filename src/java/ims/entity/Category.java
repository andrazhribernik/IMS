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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *This is JPA entity Category, which represents a table category of image in database.
 * @author andrazhribernik
 */
@Entity
@Table(name = "Category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findByIdCategory", query = "SELECT c FROM Category c WHERE c.idCategory = :idCategory"),
    @NamedQuery(name = "Category.findByCategoryName", query = "SELECT c FROM Category c WHERE c.categoryName = :categoryName")})
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCategory")
    private Integer idCategory;
    @Size(max = 45)
    @Column(name = "categoryName")
    private String categoryName;
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryidCategory")
    private Set<Image> imageSet;
    
    /**
     * Constructor
     */
    public Category() {
    }
    
    /**
     * Constructor
     * @param idCategory 
     */
    public Category(Integer idCategory) {
        this.idCategory = idCategory;
    }
    
    /**
     * Getter
     * @return id of category
     */
    public Integer getIdCategory() {
        return idCategory;
    }
    /**
     * Setter
     * @param idCategory 
     */
    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }
    /**
     * Getter
     * @return category name
     */
    public String getCategoryName() {
        return categoryName;
    }
    /**
     * Setter 
     * @param categoryName 
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    /**
     * Getter which return Set of all images which belong to object Category
     * @return Set of Images
     */
    @XmlTransient
    public Set<Image> getImageSet() {
        return imageSet;
    }
    /**
     * Setter which set Set of Images for object Category
     * @param imageSet 
     */
    public void setImageSet(Set<Image> imageSet) {
        this.imageSet = imageSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategory != null ? idCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.idCategory == null && other.idCategory != null) || (this.idCategory != null && !this.idCategory.equals(other.idCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.entity.Category[ idCategory=" + idCategory + " ]";
    }
    
}

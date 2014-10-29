/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jason
 */
@Entity
@Table(name = "shoppinglistentity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shoppinglistentity.findAll", query = "SELECT s FROM Shoppinglistentity s"),
    @NamedQuery(name = "Shoppinglistentity.findById", query = "SELECT s FROM Shoppinglistentity s WHERE s.id = :id")})
public class Shoppinglistentity implements Serializable {
    @ManyToMany(mappedBy = "shoppinglistentityList")
    private List<Lineitementity> lineitementityList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;
    @ManyToMany(mappedBy = "shoppinglistentityList")
    private List<Itementity> itementityList;
    @OneToMany(mappedBy = "shoppinglistId")
    private List<Memberentity> memberentityList;

    public Shoppinglistentity() {
    }

    public Shoppinglistentity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public List<Itementity> getItementityList() {
        return itementityList;
    }

    public void setItementityList(List<Itementity> itementityList) {
        this.itementityList = itementityList;
    }

    @XmlTransient
    public List<Memberentity> getMemberentityList() {
        return memberentityList;
    }

    public void setMemberentityList(List<Memberentity> memberentityList) {
        this.memberentityList = memberentityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shoppinglistentity)) {
            return false;
        }
        Shoppinglistentity other = (Shoppinglistentity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Shoppinglistentity[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Lineitementity> getLineitementityList() {
        return lineitementityList;
    }

    public void setLineitementityList(List<Lineitementity> lineitementityList) {
        this.lineitementityList = lineitementityList;
    }
    
}

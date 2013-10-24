
import ims.entity.Role;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andrazhribernik
 */
public class TestClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         EntityManager em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
        //persist the person entity
        //em.persist(person);
        Role r = new Role();
        r.setName("user");
        em.persist(r);
        
    }
}

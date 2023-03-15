package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import entities.Address;
import entities.Person;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

@NoArgsConstructor
public class AddressFacade {

    private static AddressFacade instance;
    private static EntityManagerFactory emf;

    public static AddressFacade getAddressFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AddressFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<AddressDTO> getAllAddresses () {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Address> query = em.createQuery("SELECT a FROM Address a", Address.class);
            List<Address> addressList = query.getResultList();
            return AddressDTO.getDTOs(addressList);
        } finally {
            em.close();
        }
    }

    // Creating a new Address
    public AddressDTO create(AddressDTO addressDTO) {
        Address address = new Address(addressDTO.getStreet(), addressDTO.getAdditionalInfo());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AddressDTO(address);
    }

    // Edit address
    public AddressDTO editAddress(AddressDTO addressDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Address address = em.find(Address.class, addressDTO.getId());
            if (addressDTO.getStreet() != null) {
                address.setStreet(addressDTO.getStreet());
            }

            if (addressDTO.getAdditionalInfo() != null) {
                address.setAdditionalInfo(addressDTO.getAdditionalInfo());
            }

            em.getTransaction().commit();
            return new AddressDTO(address);
        } finally {
            em.close();
        }
    }

    // Delete address
    public AddressDTO deleteAddress(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Address address = em.find(Address.class, id);
            em.remove(address);
            em.getTransaction().commit();
            return new AddressDTO(address);
        } finally {
            em.close();
        }
    }

}

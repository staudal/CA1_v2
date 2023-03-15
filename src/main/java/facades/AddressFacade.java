package facades;

import dtos.AddressDTO;
import entities.Address;
import entities.Person;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    // Get address from personDTO
    public AddressDTO getAddressFromPerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, personDTO.getId());
        try {
            return new AddressDTO(em.find(Address.class, person.getAddress().getId()));
        } finally {
            em.close();
        }
    }

}

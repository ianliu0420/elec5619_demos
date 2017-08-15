package au.edu.sydney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.edu.sydney.dao.PersonDao;
import au.edu.sydney.domain.Person;

@Service(value="personService")
// @Transactional
public class PersonService {

    @Autowired
    private PersonDao personDao;
    
    // business logic of registering a Person into the database
    public void registerPerson(Person person) {
        
        // Step 1: check whether this person is already in the database
        
        // Step 2: if not, save this person into the database
        personDao.savePerson(person);
    }
    
}

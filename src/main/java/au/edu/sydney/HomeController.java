package au.edu.sydney;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.edu.sydney.dao.PersonDao;
import au.edu.sydney.domain.Person;
import au.edu.sydney.service.PersonService;


/**
 * Handles requests for the application home page.
 */
@Controller
@Transactional
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

//    @Autowired
//    private SessionFactory sessionFactory;
    
    @Autowired
    private PersonDao personDao;
    
    @Autowired
    private PersonService personService;
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
        String formattedDate = dateFormat.format(date);
        model.addAttribute("serverTime", formattedDate);
        return "home";
    }

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/jdbcAdd", method = RequestMethod.GET)
    public String jdbcAdd(Locale locale, Model model) {
        
        // JDBC driver name and database URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
        String DB_URL = "jdbc:mysql://localhost/elec5619";

        //  Database credentials
        String USER = "root"; // 
        String PASS = "root";
        
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try{
           //Register JDBC driver
           Class.forName("com.mysql.jdbc.Driver");

           //Open a connection
           System.out.println("Connecting to database...");
           conn = DriverManager.getConnection(DB_URL,USER,PASS);
           
           String insertTableSQL = "INSERT INTO employee"
                           + "(id, age, first, last) VALUES"
                           + "(?,?,?,?)";
           
           preparedStatement = conn.prepareStatement(insertTableSQL);
           preparedStatement.setInt(1, 1);
           preparedStatement.setInt(2, 25);
           preparedStatement.setString(3, "Test");
           preparedStatement.setString(4, "Name");

           // execute insert SQL statement
           preparedStatement.executeUpdate();
           System.out.println("Record is inserted into employee table!");
           
           //Clean-up environment
           preparedStatement.close();
           conn.close();
        }catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }finally{
           try{
              if(preparedStatement!=null)
                  preparedStatement.close();
           }catch(SQLException se2){
           }
           try{
              if(conn!=null)
                 conn.close();
           }catch(SQLException se){
              se.printStackTrace();
           }
        }

        return "home";
    }
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    
    
    @Autowired
    private SessionFactory sessionFactory;
    @RequestMapping(value = "/hibernateAdd", method = RequestMethod.GET)
    public String hibernateAdd(Locale locale, Model model) {
        
        Person p = new Person();
        p.setAge(20);
        p.setFirst("FirstName");
        p.setLast("lastName");
        
        sessionFactory.getCurrentSession().save(p);
        return "home";
    }

    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/hibernateDaoAdd", method = RequestMethod.GET)
    public String hibernateDaoAdd(Locale locale, Model model) {
        Person p = new Person();
        p.setAge(20);
        p.setFirst("FirstName");
        p.setLast("lastName");

        personDao.savePerson(p);
        
        return "home";
    }

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/hibernateDaoServiceAdd", method = RequestMethod.GET)
    public String hibernateDaoServiceAdd(Locale locale, Model model) {
        Person p = new Person();
        p.setAge(20);
        p.setFirst("FirstName");
        p.setLast("lastName");

        personService.registerPerson(p);
        
        return "home";
    }
    
}

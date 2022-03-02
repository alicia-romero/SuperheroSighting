/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package raj.superherosighting.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import raj.superherosighting.dto.Location;
import raj.superherosighting.dto.Organization;
import raj.superherosighting.dto.Sighting;
import raj.superherosighting.dto.Superhero;

/**
 *
 * @author romeroalicia
 */
@SpringBootTest
public class SightingDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    public SightingDaoDBTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations) {
            locationDao.deleteLocationById(location.getLocationId());
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getOrganizationId());
        }
        
        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getSightingId());
        }
        
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        for(Superhero superhero : superheroes) {
            superheroDao.deleteSuperheroById(superhero.getSuperheroId());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addSighting and getSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testAddAndGetSighting() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 1");
        location.setLatitude(new BigDecimal("1.00000000"));
        location.setLongitude(new BigDecimal("1.00000000"));
        location = locationDao.addLocation(location);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("0000 Test Street 0");
        organization.setWebsiteUrl("https//:testorganization.com");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setImagePath("/test.png");
        superhero.setSuperpower("Test superpower");
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting.setSuperhero(superhero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId()); 
        assertEquals(sighting, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 1");
        location.setLatitude(new BigDecimal("1.00000000"));
        location.setLongitude(new BigDecimal("1.00000000"));
        location = locationDao.addLocation(location);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("0000 Test Street 0");
        organization.setWebsiteUrl("https//:testorganization.com");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setImagePath("/test.png");
        superhero.setSuperpower("Test superpower");
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting.setSuperhero(superhero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.parse("2000-02-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting2.setSuperhero(superhero);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);
        
        List<Sighting> sightings = sightingDao.getAllSightings(); 
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));   
    }

    /**
     * Test of getAllSightingsByDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightingsByDate() {
        LocalDate date = LocalDate.parse("2000-01-01");
        
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 1");
        location.setLatitude(new BigDecimal("1.00000000"));
        location.setLongitude(new BigDecimal("1.00000000"));
        location = locationDao.addLocation(location);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("0000 Test Street 0");
        organization.setWebsiteUrl("https//:testorganization.com");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setImagePath("/test.png");
        superhero.setSuperpower("Test superpower");
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting.setSuperhero(superhero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.parse("2000-02-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting2.setSuperhero(superhero);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setDate(LocalDateTime.parse("2000-01-01 01:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting3.setSuperhero(superhero);
        sighting3.setLocation(location);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> sightings = sightingDao.getAllSightingsByDate(date); 
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting2));
        assertTrue(sightings.contains(sighting3));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 1");
        location.setLatitude(new BigDecimal("1.00000000"));
        location.setLongitude(new BigDecimal("1.00000000"));
        location = locationDao.addLocation(location);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("0000 Test Street 0");
        organization.setWebsiteUrl("https//:testorganization.com");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setImagePath("/test.png");
        superhero.setSuperpower("Test superpower");
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting.setSuperhero(superhero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId()); 
        assertEquals(sighting, fromDao);
        
        sighting.setDate(LocalDateTime.parse("2000-02-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("0000 Test Street 2");
        location2.setLatitude(new BigDecimal("2.00000000"));
        location2.setLongitude(new BigDecimal("2.00000000"));
        location2 = locationDao.addLocation(location2);
        sighting.setLocation(location2);
        
        sightingDao.updateSighting(sighting);
        
        assertNotEquals(sighting, fromDao);
        
        fromDao = sightingDao.getSightingById(sighting.getSightingId()); 
        assertEquals(sighting, fromDao);        
    }

    /**
     * Test of deleteSightingById method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSightingById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 1");
        location.setLatitude(new BigDecimal("1.00000000"));
        location.setLongitude(new BigDecimal("1.00000000"));
        location = locationDao.addLocation(location);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("0000 Test Street 0");
        organization.setWebsiteUrl("https//:testorganization.com");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setImagePath("/test.png");
        superhero.setSuperpower("Test superpower");
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting.setSuperhero(superhero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getSightingId()); 
        assertEquals(sighting, fromDao);
        
        sightingDao.deleteSightingById(sighting.getSightingId());
        
        fromDao = sightingDao.getSightingById(sighting.getSightingId()); 
        assertNull(fromDao);
    }

    /**
     * Test of getSightingsForSuperhero method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForSuperhero() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 1");
        location.setLatitude(new BigDecimal("1.00000000"));
        location.setLongitude(new BigDecimal("1.00000000"));
        location = locationDao.addLocation(location);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("0000 Test Street 0");
        organization.setWebsiteUrl("https//:testorganization.com");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setImagePath("/test.png");
        superhero.setSuperpower("Test superpower");
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Name 2");
        superhero2.setDescription("Test Description 2");
        superhero2.setImagePath("/test2.png");
        superhero2.setSuperpower("Test superpower 2");
        superhero2.setOrganizations(organizations);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting.setSuperhero(superhero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting2.setSuperhero(superhero2);
        sighting2.setLocation(location);
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting3.setSuperhero(superhero);
        sighting3.setLocation(location);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> sightings = sightingDao.getSightingsForSuperhero(superhero);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting2));
        assertTrue(sightings.contains(sighting3));
    }

    /**
     * Test of getSightingsForLocation method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsForLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 1");
        location.setLatitude(new BigDecimal("1.00000000"));
        location.setLongitude(new BigDecimal("1.00000000"));
        location = locationDao.addLocation(location);
        
        Location location2 = new Location();
        location2.setName("Test Name 2");
        location2.setDescription("Test Description 2");
        location2.setAddress("0000 Test Street 2");
        location2.setLatitude(new BigDecimal("2.00000000"));
        location2.setLongitude(new BigDecimal("2.00000000"));
        location2 = locationDao.addLocation(location2);
        
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("0000 Test Street 0");
        organization.setWebsiteUrl("https//:testorganization.com");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        Superhero superhero = new Superhero();
        superhero.setName("Test Name");
        superhero.setDescription("Test Description");
        superhero.setImagePath("/test.png");
        superhero.setSuperpower("Test superpower");
        superhero.setOrganizations(organizations);
        superhero = superheroDao.addSuperhero(superhero);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting.setSuperhero(superhero);
        sighting.setLocation(location);
        sighting = sightingDao.addSighting(sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting2.setSuperhero(superhero);
        sighting2.setLocation(location2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        Sighting sighting3 = new Sighting();
        sighting3.setDate(LocalDateTime.parse("2000-01-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting3.setSuperhero(superhero);
        sighting3.setLocation(location);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Sighting> sightings = sightingDao.getSightingsForLocation(location);
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertFalse(sightings.contains(sighting2));
        assertTrue(sightings.contains(sighting3));
    }
    
}

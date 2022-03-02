/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package raj.superherosighting.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class LocationDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    public LocationDaoDBTest() {
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
     * Test of addLocation and getLocationById methods, of class LocationDaoDB.
     */
    @Test
    public void testAddAndGetLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 0");
        location.setLatitude(new BigDecimal("0.00000000"));
        location.setLongitude(new BigDecimal("0.00000000"));
        
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
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
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 0");
        location.setLatitude(new BigDecimal("0.00000000"));
        location.setLongitude(new BigDecimal("0.00000000"));
        
        location = locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
        
        location.setName("New Test Name");
        locationDao.updateLocation(location);
        
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getLocationId());
        
        assertEquals(location, fromDao);
    }

    /**
     * Test of deleteLocationById method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Test Name");
        location.setDescription("Test Description");
        location.setAddress("0000 Test Street 0");
        location.setLatitude(new BigDecimal("0.00000000"));
        location.setLongitude(new BigDecimal("0.00000000")); 
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
        
        Location fromDao = locationDao.getLocationById(location.getLocationId());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationById(location.getLocationId());
        
        fromDao = locationDao.getLocationById(location.getLocationId());
        assertNull(fromDao);
    }
    
}

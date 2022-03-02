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
public class SuperheroDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    public SuperheroDaoDBTest() {
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
     * Test of addSuperhero and getSuperheroById method, of class SuperheroDaoDB.
     */
    @Test
    public void testAddAndGetSuperhero() {
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
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getSuperheroId());
        assertEquals(superhero, fromDao);
    }

    /**
     * Test of getAllSuperheroes method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetAllSuperheroes() {
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
        
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertTrue(superheroes.contains(superhero2));
    }

    /**
     * Test of updateSuperhero method, of class SuperheroDaoDB.
     */
    @Test
    public void testUpdateSuperhero() {
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
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getSuperheroId());
        assertEquals(superhero, fromDao);
        
        superhero.setName("New Test Name");
        Organization organization2 = new Organization();
        organization2.setName("Test Name 2");
        organization2.setDescription("Test Description 2");
        organization2.setAddress("0000 Test Street 2");
        organization2.setWebsiteUrl("https//:testorganization2.com");
        organization2 = organizationDao.addOrganization(organization2);
        organizations.add(organization2);
        superhero.setOrganizations(organizations);
        
        superheroDao.updateSuperhero(superhero);
        
        assertNotEquals(superhero, fromDao);
        
        fromDao = superheroDao.getSuperheroById(superhero.getSuperheroId());
        assertEquals(superhero, fromDao);
    }

    /**
     * Test of deleteSuperheroById method, of class SuperheroDaoDB.
     */
    @Test
    public void testDeleteSuperheroById() {
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
        
        Superhero fromDao = superheroDao.getSuperheroById(superhero.getSuperheroId());
        assertEquals(superhero, fromDao);
        
        superheroDao.deleteSuperheroById(superhero.getSuperheroId());
        
        fromDao = superheroDao.getSuperheroById(superhero.getSuperheroId());
        assertNull(fromDao);
    }

    /**
     * Test of getSuperheroesForOrganization method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetSuperheroesForOrganization() {
        Organization organization = new Organization();
        organization.setName("Test Name");
        organization.setDescription("Test Description");
        organization.setAddress("0000 Test Street 0");
        organization.setWebsiteUrl("https//:testorganization.com");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<>();
        
        Organization organization2 = new Organization();
        organization2.setName("Test Name 2");
        organization2.setDescription("Test Description 2");
        organization2.setAddress("0000 Test Street 2");
        organization2.setWebsiteUrl("https//:testorganization2.com");
        organization2 = organizationDao.addOrganization(organization2);
        List<Organization> organizations2 = new ArrayList<>();
        
        organizations.add(organization);
        organizations.add(organization2);
                
        organizations2.add(organization2);
        
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
        superhero2.setOrganizations(organizations2);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Superhero superhero3 = new Superhero();
        superhero3.setName("Test Name 3");
        superhero3.setDescription("Test Description 3");
        superhero3.setImagePath("/test3.png");
        superhero3.setSuperpower("Test superpower 3");
        superhero3.setOrganizations(organizations);
        superhero3 = superheroDao.addSuperhero(superhero3);
        
        List<Superhero> superheroes = superheroDao.getSuperheroesForOrganization(organization);
        assertEquals(2, superheroes.size());
        assertTrue(superheroes.contains(superhero));
        assertFalse(superheroes.contains(superhero2));
        assertTrue(superheroes.contains(superhero3));
    }

    /**
     * Test of getSuperheroesForLocation method, of class SuperheroDaoDB.
     */
    @Test
    public void testGetSuperheroesForLocation() {
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
        
        Superhero superhero2 = new Superhero();
        superhero2.setName("Test Name 2");
        superhero2.setDescription("Test Description 2");
        superhero2.setImagePath("/test2.png");
        superhero2.setSuperpower("Test superpower 2");
        superhero2.setOrganizations(organizations);
        superhero2 = superheroDao.addSuperhero(superhero2);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.parse("2000-02-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting2.setSuperhero(superhero2);
        sighting2.setLocation(location2);
        sighting2 = sightingDao.addSighting(sighting2);
        
        Superhero superhero3 = new Superhero();
        superhero3.setName("Test Name 3");
        superhero3.setDescription("Test Description 3");
        superhero3.setImagePath("/test3.png");
        superhero3.setSuperpower("Test superpower 3");
        superhero3.setOrganizations(organizations);
        superhero3 = superheroDao.addSuperhero(superhero3);
        
        Sighting sighting3 = new Sighting();
        sighting3.setDate(LocalDateTime.parse("2000-03-01 00:00:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        sighting3.setSuperhero(superhero3);
        sighting3.setLocation(location);
        sighting3 = sightingDao.addSighting(sighting3);
        
        List<Superhero> superheroes = superheroDao.getSuperheroesForLocation(location);
        assertEquals(2, superheroes.size());
        assertEquals("Test Name", superheroes.get(0).getName());
        assertEquals("Test Name 3", superheroes.get(1).getName());
    }
    
}

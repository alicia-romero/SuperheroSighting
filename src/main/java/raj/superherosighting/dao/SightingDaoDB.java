/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raj.superherosighting.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import raj.superherosighting.dao.LocationDaoDB.LocationMapper;
import raj.superherosighting.dao.SuperheroDaoDB.SuperheroMapper;
import raj.superherosighting.dto.Location;
import raj.superherosighting.dto.Organization;
import raj.superherosighting.dto.Sighting;
import raj.superherosighting.dto.Superhero;

/**
 *
 * @author romeroalicia
 */
@Repository
public class SightingDaoDB implements SightingDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE sightingId = ?;";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setSuperhero(getSuperheroForSighting(id));
            sighting.setLocation(getLocationForSighting(id));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    private Superhero getSuperheroForSighting(int id) {
        final String SELECT_SUPERHERO_FOR_SIGHTING = "SELECT s.* FROM superhero s "
                + "JOIN sighting si ON s.superheroId = si.superheroId WHERE si.sightingId = ?;";
        Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_FOR_SIGHTING, new SuperheroMapper(), id);
        superhero.setOrganizations(getOrganizationsForSuperhero(superhero.getSuperheroId()));
        return superhero;
    }
    
    private List<Organization> getOrganizationsForSuperhero(int id) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = "SELECT o.* FROM organization o "
                + "JOIN superheroOrganization so ON o.organizationId = so.organizationId WHERE so.superheroId = ?;";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationDaoDB.OrganizationMapper(), id);
    }
    
    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting si ON l.locationId = si.locationId WHERE si.sightingId = ?;";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting;";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateSuperheroesAndLocations(sightings);
        return sightings;
    }
    
    private void associateSuperheroesAndLocations(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setSuperhero(getSuperheroForSighting(sighting.getSightingId()));
            sighting.setLocation(getLocationForSighting(sighting.getSightingId()));
        }
    }

    @Override
    public List<Sighting> getAllSightingsByDate(LocalDate date) {
       final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting WHERE DATE(date) = ?;";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper(), date);
        associateSuperheroesAndLocations(sightings);
        return sightings; 
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(date, superheroId, locationId) "
                + "VALUES(?,?,?);";
        jdbc.update(INSERT_SIGHTING,
                sighting.getDate(),
                sighting.getSuperhero().getSuperheroId(),
                sighting.getLocation().getLocationId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        sighting.setSightingId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET date = ?, superheroId = ?, "
                + "locationId = ? WHERE sightingId = ?;";
        jdbc.update(UPDATE_SIGHTING, 
                sighting.getDate(),
                sighting.getSuperhero().getSuperheroId(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingId());
    }

    @Override
    public void deleteSightingById(int id) {       
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE sightingId = ?;";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsForSuperhero(Superhero superhero) {
        final String SELECT_SIGHTINGS_FOR_SUPERHERO = "SELECT * FROM sighting WHERE superheroId = ?;";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPERHERO, 
                new SightingMapper(), superhero.getSuperheroId());
        associateSuperheroesAndLocations(sightings);
        return sightings;
    }
    
    @Override
    public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting WHERE locationId = ?;";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION, 
                new SightingMapper(), location.getLocationId());
        associateSuperheroesAndLocations(sightings);
        return sightings;
    }
    
     public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingId(rs.getInt("sightingId"));
            sighting.setDate(rs.getTimestamp("date").toLocalDateTime());
            
            return sighting;
        }
        
    }
    
}

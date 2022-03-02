/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raj.superherosighting.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import raj.superherosighting.dao.OrganizationDaoDB.OrganizationMapper;
import raj.superherosighting.dto.Location;
import raj.superherosighting.dto.Organization;
import raj.superherosighting.dto.Superhero;

/**
 *
 * @author romeroalicia
 */
@Repository
public class SuperheroDaoDB implements SuperheroDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE superheroId = ?;";
            Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            superhero.setOrganizations(getOrganizationsForSuperhero(id));
            return superhero;
        } catch(DataAccessException ex) {
            return null;
        }
    }
    
    private List<Organization> getOrganizationsForSuperhero(int id) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHERO = "SELECT o.* FROM organization o "
                + "JOIN superheroOrganization so ON o.organizationId = so.organizationId WHERE so.superheroId = ?;";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHERO, new OrganizationMapper(), id);
    }

    @Override
    public List<Superhero> getAllSuperheroes() {
        final String SELECT_ALL_SUPERHEROES = "SELECT * FROM superhero";
        List<Superhero> superheroes = jdbc.query(SELECT_ALL_SUPERHEROES, new SuperheroMapper());
        associateOrganizations(superheroes);
        return superheroes;
    }
    
    private void associateOrganizations(List<Superhero> superheroes) {
        for (Superhero superhero: superheroes) {
            superhero.setOrganizations(getOrganizationsForSuperhero(superhero.getSuperheroId()));
        }
        
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO superhero(name, description, imagePath, superpower) "
                + "VALUES(?,?,?,?);";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getImagePath(),
                superhero.getSuperpower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        superhero.setSuperheroId(newId);
        insertSuperheroOrganization(superhero);
        return superhero;
    }
    
    private void insertSuperheroOrganization(Superhero superhero) {
        final String INSERT_SUPERHERO_ORGANIZATION = "INSERT INTO "
                + "superheroOrganization(superheroId, organizationId) VALUES(?,?);";
        for(Organization organization : superhero.getOrganizations()) {
            jdbc.update(INSERT_SUPERHERO_ORGANIZATION, 
                    superhero.getSuperheroId(),
                    organization.getOrganizationId());
        }
    }

    @Override
    @Transactional
    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero SET name = ?, description = ?, "
                + "imagePath = ?, superpower = ? WHERE superheroId = ?;";
      
        jdbc.update(UPDATE_SUPERHERO, 
                superhero.getName(), 
                superhero.getDescription(), 
                superhero.getImagePath(),
                superhero.getSuperpower(),
                superhero.getSuperheroId());
        
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superheroOrganization WHERE superheroId = ?;";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, superhero.getSuperheroId());
        insertSuperheroOrganization(superhero);
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superheroOrganization WHERE superheroId = ?;";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, id);
        
        final String DELETE_SUPERHERO = "DELETE FROM superhero WHERE superheroId = ?;";
        jdbc.update(DELETE_SUPERHERO, id);
    }

    @Override
    public List<Superhero> getSuperheroesForOrganization(Organization organization) {
        final String SELECT_SUPERHEROES_FOR_ORGANIZATION = "SELECT s.* FROM superhero s "
                + "JOIN superheroOrganization so ON s.superheroId = so.superheroId WHERE so.organizationId = ?;";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_ORGANIZATION, 
                new SuperheroMapper(), organization.getOrganizationId());
        associateOrganizations(superheroes);
        return superheroes;
    }

    @Override
    public List<Superhero> getSuperheroesForLocation(Location location) {
        final String SELECT_SUPERHEROES_FOR_LOCATION = "SELECT s.* FROM superhero s "
                + "JOIN sighting si ON s.superheroId = si.superheroId "
                + "JOIN location l ON si.locationId = l.locationId "
                + "WHERE l.locationId = ?;";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROES_FOR_LOCATION, 
                new SuperheroMapper(), location.getLocationId());
        return superheroes;
    }
    
    public static final class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int rowNum) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setSuperheroId(rs.getInt("superheroId"));
            superhero.setName(rs.getString("name"));
            superhero.setDescription(rs.getString("description"));
            superhero.setImagePath(rs.getString("imagePath"));
            superhero.setSuperpower(rs.getString("superpower"));
            
            return superhero;
        }
        
    }
    
}

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
import raj.superherosighting.dto.Organization;

/**
 *
 * @author romeroalicia
 */
@Repository
public class OrganizationDaoDB implements OrganizationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String GET_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE organizationId = ?;";
            return jdbc.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATIONS = "SELECT * FROM organization;";
        return jdbc.query(GET_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(name, description, address, websiteUrl) "
                + "VALUES(?,?,?,?);";
        jdbc.update(INSERT_ORGANIZATION, 
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getWebsiteUrl());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID();", Integer.class);
        organization.setOrganizationId(newId);
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, "
                + "address = ?, websiteUrl = ? WHERE organizationId = ?;";
        jdbc.update(UPDATE_ORGANIZATION, 
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getWebsiteUrl(),
                organization.getOrganizationId());
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superheroOrganization WHERE organizationId = ?;";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, id);
        
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE organizationId = ?;";
        jdbc.update(DELETE_ORGANIZATION, id);
    }
    
    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganizationId(rs.getInt("organizationId"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            organization.setWebsiteUrl(rs.getString("websiteURL"));
            
            return organization;
        }
        
    }
    
}

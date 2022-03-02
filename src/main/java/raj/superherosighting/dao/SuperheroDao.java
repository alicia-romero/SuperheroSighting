/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package raj.superherosighting.dao;

import java.util.List;
import raj.superherosighting.dto.Location;
import raj.superherosighting.dto.Organization;
import raj.superherosighting.dto.Superhero;

/**
 *
 * @author romeroalicia
 */
public interface SuperheroDao {
    Superhero getSuperheroById(int id);
    List<Superhero> getAllSuperheroes();
    Superhero addSuperhero(Superhero superhero);
    void updateSuperhero(Superhero superhero);
    void deleteSuperheroById(int id);
    
    List<Superhero> getSuperheroesForOrganization(Organization organization);
    List<Superhero> getSuperheroesForLocation(Location location);
}

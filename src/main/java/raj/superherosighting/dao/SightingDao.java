/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package raj.superherosighting.dao;

import java.time.LocalDate;
import java.util.List;
import raj.superherosighting.dto.Location;
import raj.superherosighting.dto.Sighting;
import raj.superherosighting.dto.Superhero;

/**
 *
 * @author romeroalicia
 */
public interface SightingDao {
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    List<Sighting> getAllSightingsByDate(LocalDate date);
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    
    List<Sighting> getSightingsForSuperhero(Superhero superhero);
    List<Sighting> getSightingsForLocation(Location location);
}

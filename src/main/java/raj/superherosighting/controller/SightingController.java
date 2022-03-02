/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raj.superherosighting.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import raj.superherosighting.dao.LocationDao;
import raj.superherosighting.dao.OrganizationDao;
import raj.superherosighting.dao.SightingDao;
import raj.superherosighting.dao.SuperheroDao;
import raj.superherosighting.dto.Location;
import raj.superherosighting.dto.Organization;
import raj.superherosighting.dto.Sighting;
import raj.superherosighting.dto.Superhero;

/**
 *
 * @author romeroalicia
 */
@Controller
public class SightingController {
    
    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();
        
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        List<Location> locations = locationDao.getAllLocations();
        
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "sightings";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String superheroId = request.getParameter("superheroId");
        String locationId = request.getParameter("locationId");

        Sighting sighting = new Sighting();
        sighting.setDate(date);
        
        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if(violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        }
        
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        List<Location> locations = locationDao.getAllLocations();
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String superheroId = request.getParameter("superheroId");
        String locationId = request.getParameter("locationId");

        sighting.setDate(date);
        
        sighting.setSuperhero(superheroDao.getSuperheroById(Integer.parseInt(superheroId)));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        
        sightingDao.updateSighting(sighting);
        
        return "redirect:/sightings";
    }
    
}

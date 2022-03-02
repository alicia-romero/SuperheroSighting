/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raj.superherosighting.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import raj.superherosighting.dao.LocationDao;
import raj.superherosighting.dao.OrganizationDao;
import raj.superherosighting.dao.SightingDao;
import raj.superherosighting.dao.SuperheroDao;
import raj.superherosighting.dto.Location;
import raj.superherosighting.dto.Organization;
import raj.superherosighting.dto.Superhero;

/**
 *
 * @author romeroalicia
 */
@Controller
public class SuperheroController {
    
    Set<ConstraintViolation<Superhero>> violations = new HashSet<>();
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperheroDao superheroDao;
    
    private final String IMAGE_DIR = "./src/main/resources/static/images/";
    
    @GetMapping("superheroes")
    public String displaySuperheroes(Model model) {
        List<Superhero> superheroes = superheroDao.getAllSuperheroes();
        List<Organization> organizations = organizationDao.getAllOrganizations();
        
        model.addAttribute("superheroes", superheroes);
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", violations);
        return "superheroes";
    }
    
    @PostMapping("addSuperhero")
    public String addSuperhero(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpower = request.getParameter("superpower");
        String imagePath = request.getParameter("imagePath");
        
        Superhero superhero = new Superhero();
        superhero.setName(name);
        superhero.setDescription(description);
        superhero.setSuperpower(superpower);
        superhero.setImagePath(imagePath);
        
        String[] organizationIds = request.getParameterValues("organizationId");
        
        List<Organization> organizations = new ArrayList<>();
        for(String organizationId : organizationIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }
        superhero.setOrganizations(organizations);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superhero);

        if(violations.isEmpty()) {
            superheroDao.addSuperhero(superhero);
        }

        return "redirect:/superheroes";
    }
    
    @GetMapping("superheroDetail")
    public String superheroDetail(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        model.addAttribute("superhero", superhero);
        return "superheroDetail";
    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(Integer id) {
        superheroDao.deleteSuperheroById(id);
        return "redirect:/superheroes";
    }
    
    @GetMapping("editSuperhero")
    public String editSuperhero(Integer id, Model model) {
        Superhero superhero = superheroDao.getSuperheroById(id);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("superhero", superhero);
        model.addAttribute("organizations", organizations);
        return "editSuperhero";
    }
    
    // https://attacomsian.com/blog/spring-boot-thymeleaf-file-upload 
    // this is the resource I used to add an image
    @PostMapping("editSuperhero")
    public String performEditSuperhero(HttpServletRequest request, 
            @RequestParam("file") MultipartFile file, Model model) {
 
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path path = Paths.get(IMAGE_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpower = request.getParameter("superpower");
        
        superhero.setName(name);
        superhero.setDescription(description);
        superhero.setSuperpower(superpower);
        superhero.setImagePath(fileName);
        
        String[] organizationIds = request.getParameterValues("organizationId");
        
        List<Organization> organizations = new ArrayList<>();
        for(String organizationId : organizationIds) {
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }
        superhero.setOrganizations(organizations);
        superheroDao.updateSuperhero(superhero);
        
        model.addAttribute("supehero", superhero);
        
        return "redirect:/superheroes";
    }
    

}

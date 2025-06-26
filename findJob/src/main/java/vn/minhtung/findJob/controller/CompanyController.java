package vn.minhtung.findJob.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.minhtung.findJob.domain.Company;
import vn.minhtung.findJob.domain.dto.ResultPageinationDTO;
import vn.minhtung.findJob.service.CompanyService;
import vn.minhtung.findJob.util.anotation.ApiMessage;


@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany (@Valid @RequestBody Company company){
        Company saveCompany = this.companyService.handleCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCompany);
    }

    @GetMapping("/companies")
    @ApiMessage("Get All Companies")
    public ResponseEntity<ResultPageinationDTO> getAllCompany(
            @Filter Specification<Company> spec, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.getAllCompanies(spec, pageable));
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable long id){
        Company company = this.companyService.getCompanyById(id);
        return ResponseEntity.status(HttpStatus.OK).body(company);
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company){
        Company updateCompany = this.companyService.updateCompany(company);
        return ResponseEntity.ok(updateCompany);
    }

    @DeleteMapping("companies/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable long id){
        this.companyService.deleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body("Da dc xoa");
    }

}

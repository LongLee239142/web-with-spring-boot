package vn.longlee.jobhunter.controller;

import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import vn.longlee.jobhunter.domain.Company;
import vn.longlee.jobhunter.domain.RestResponse;
import vn.longlee.jobhunter.domain.dto.ResultPaginationDTO;
import vn.longlee.jobhunter.service.CompanyService;
import vn.longlee.jobhunter.util.annotation.ApiMessage;
import vn.longlee.jobhunter.util.error.IdInvalidException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody Company reqCompany) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.companyService.handleCreateCompany(reqCompany));
    }
@GetMapping("/companies")
@ApiMessage("fetch all companies")
public ResponseEntity<ResultPaginationDTO> getCompany(
        @Filter Specification<Company> spec,
        Pageable pageable) {

    return ResponseEntity.status(HttpStatus.OK).body(
            this.companyService.fetchAllCompany(spec, pageable));
}
    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
        Company companyLongLee = this.companyService.handleUpdateCompany(company);
        return ResponseEntity.ok(companyLongLee);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<RestResponse> deleteCompany(@PathVariable("id") long id) throws IdInvalidException {
        if (id <= 0) {
            throw new IdInvalidException("ID không hợp lệ. ID phải là một số nguyên dương.");
        }
        this.companyService.handleDeleteCompany(id);
        return ResponseEntity.noContent().build();
    }

}

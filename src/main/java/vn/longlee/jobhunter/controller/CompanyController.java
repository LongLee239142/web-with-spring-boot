package vn.longlee.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import jakarta.validation.Valid;
import vn.longlee.jobhunter.domain.Company;
import vn.longlee.jobhunter.domain.RestResponse;
import vn.longlee.jobhunter.domain.dto.ResultPaginationDTO;
import vn.longlee.jobhunter.service.CompanyService;
import vn.longlee.jobhunter.util.error.IdInvalidException;

import java.util.Optional;

@RestController
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
    public ResponseEntity<ResultPaginationDTO> getCompany(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        String sCurrent = currentOptional.isPresent() ? currentOptional.get() : "";
        String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";

        int current = Integer.parseInt(sCurrent);
        int pageSize = Integer.parseInt(sPageSize);

        Pageable pageable = PageRequest.of(current - 1, pageSize);
        return ResponseEntity.ok(this.companyService.fetchAllCompany(pageable));
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

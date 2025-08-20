package vn.longlee.jobhunter.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.longlee.jobhunter.domain.Company;
import vn.longlee.jobhunter.domain.User;
import vn.longlee.jobhunter.domain.dto.Meta;
import vn.longlee.jobhunter.domain.dto.ResultPaginationDTO;
import vn.longlee.jobhunter.repository.CompanyRepository;

import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company handleCreateCompany(Company company) {
        return companyRepository.save(company);
    }

    public ResultPaginationDTO fetchAllCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPage(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());

        return rs;
    }

    public Company fetchCompanyById(long id) {
        Optional<Company> companyOptional = this.companyRepository.findById(id);
        return companyOptional.orElse(null);
    }

    public void handleDeleteCompany(long id) {
        companyRepository.deleteById(id);
    }

    public Company handleUpdateCompany( Company company) {
        Company companyUpdate = this.fetchCompanyById(company.getId());
        if (companyUpdate != null) {
            BeanUtils.copyProperties(company, companyUpdate);
            companyRepository.save(companyUpdate);
        }
        return companyUpdate;
    }
}

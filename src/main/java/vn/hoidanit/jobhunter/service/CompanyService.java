package vn.hoidanit.jobhunter.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company handleCreateCompany(Company company) {
        return companyRepository.save(company);
    }

    public ResultPaginationDTO fetchAllCompany(Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pageCompany.getNumber() + 1);
        mt.setPageSize(pageCompany.getSize());

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

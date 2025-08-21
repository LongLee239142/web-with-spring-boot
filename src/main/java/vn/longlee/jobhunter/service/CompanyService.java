package vn.longlee.jobhunter.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.longlee.jobhunter.domain.Company;
import vn.longlee.jobhunter.domain.User;
import vn.longlee.jobhunter.domain.response.ResultPaginationDTO;
import vn.longlee.jobhunter.repository.CompanyRepository;
import vn.longlee.jobhunter.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    public Company handleCreateCompany(Company company) {
        return companyRepository.save(company);
    }

    public ResultPaginationDTO fetchAllCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pageCompany = this.companyRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPage(pageCompany.getTotalPages());
        mt.setTotal(pageCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageCompany.getContent());
        return rs;
    }
    public Optional<Company> findById(long id) {
        return this.companyRepository.findById(id);
    }



    public void handleDeleteCompany(long id) {
        Optional<Company> comOptional = this.companyRepository.findById(id);
        if (comOptional.isPresent()) {
            Company com = comOptional.get();
            // fetch all user belong to this company
            List<User> users = this.userRepository.findByCompany(com);
            this.userRepository.deleteAll(users);
            this.companyRepository.deleteById(id);
        }
    }

    public Company handleUpdateCompany(Company com) {
        Optional<Company> companyOptional = this.companyRepository.findById(com.getId());
        if (companyOptional.isPresent()) {
            Company currentCompany = companyOptional.get();
            currentCompany.setLogo(com.getLogo());
            currentCompany.setName(com.getName());
            currentCompany.setDescription(com.getDescription());
            currentCompany.setAddress(com.getAddress());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }
}

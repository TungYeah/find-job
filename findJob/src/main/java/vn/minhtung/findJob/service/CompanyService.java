package vn.minhtung.findJob.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.minhtung.findJob.domain.Company;
import vn.minhtung.findJob.domain.User;
import vn.minhtung.findJob.domain.dto.Meta;
import vn.minhtung.findJob.domain.dto.ResultPageinationDTO;
import vn.minhtung.findJob.repository.CompanyRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Company handleCompany(Company company){
      return this.companyRepository.save(company);
    }

    public ResultPageinationDTO getAllCompanies(Specification<Company> spec, Pageable pageable){
        Page<Company> companies = this.companyRepository.findAll(spec, pageable);
        Meta mt = new Meta();
        ResultPageinationDTO rs = new ResultPageinationDTO();

        mt.setPage(companies.getNumber() + 1);
        mt.setPageSize(companies.getSize());
        mt.setPages(companies.getTotalPages());
        mt.setTotal(companies.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(companies.getContent());

        return rs ;
    }

    public Company getCompanyById(long id){
        return this.companyRepository.findById(id).orElseThrow();
    }

    public void deleteCompany(long id){
        if (!companyRepository.existsById(id)){
            throw new NoSuchElementException("ko tim thay id");
        }
        else
            this.companyRepository.deleteById(id);
    }

    public Company updateCompany(Company company){
        Optional<Company> updateCompany = this.companyRepository.findById(company.getId());
        if (updateCompany.isPresent()){
            Company currentCompany = updateCompany.get();
            currentCompany.setLogo(company.getLogo());
            currentCompany.setName(company.getName());
            currentCompany.setDescription(company.getDescription());
            currentCompany.setAddress(company.getAddress());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }
}

package com.project.pm360.service;

import com.project.pm360.model.Investment;
import com.project.pm360.repository.InvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvestmentService {

    @Autowired
    private InvestmentRepository investmentRepository;

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    public Optional<Investment> getInvestmentById(Long id) {
        return investmentRepository.findById(id);
    }

public Investment saveInvestment(Investment investment) {
        // set timestamp first
        investment.setRecordedAt(LocalDateTime.now());

        // fetch last record for same asset
        Optional<Investment> lastOpt = investmentRepository
            .findFirstByAssetOrderByRecordedAtDesc(investment.getAsset());

        double diffPercent = 0.0;
        if (lastOpt.isPresent()) {
            double lastPrice = lastOpt.get().getPrice();
            // percent change from last price
            diffPercent = ((investment.getPrice() - lastPrice) / lastPrice) * 100.0;
        }
        investment.setDifference(diffPercent);

        return investmentRepository.save(investment);
    }

    public void deleteInvestment(Long id) {
        investmentRepository.deleteById(id);
    }
}
package com.hotel.billing.controller;

import com.hotel.billing.model.Bill;
import com.hotel.billing.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BillController {

    @Autowired
    private BillService service;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("bill", new Bill());
        return "bill-form";
    }

    @PostMapping("/generate-bill")
    public String generateBill(@ModelAttribute Bill bill, Model model) {
        double amount = service.calculateAmount(bill.getRoomType(), bill.getDays());
        bill.setTotalAmount(amount);
        service.saveToFile(bill);
        model.addAttribute("bill", bill);
        return "success";
    }



    @GetMapping("/history")
    public String viewHistory(Model model) {
        List<Bill> billList = service.getAllBills();
        model.addAttribute("bills", billList);
        return "billing-history";
    }




}

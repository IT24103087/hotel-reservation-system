package com.hotel.billing.service;

import com.hotel.billing.model.Bill;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillService {

    // ✅ Calculate total based on room type and days
    public double calculateAmount(String roomType, int days) {
        switch (roomType.toLowerCase()) {
            case "standard": return 3000 * days;
            case "deluxe": return 5000 * days;
            case "suite": return 8000 * days;
            default: return 0;
        }
    }

    // ✅ Save the bill to a text file with ID and payment type
    public void saveToFile(Bill bill) {
        try (FileWriter fw = new FileWriter("C:\\Users\\USER\\OneDrive\\Desktop\\HotelBillingSystemSpringBoot-Fixed (2)\\HotelBillingSystemSpringBoot-Fixed\\data\\bills.txt", true)) {
            fw.write(bill.toFileString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ Read all bills from file and return as a list
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\USER\\OneDrive\\Desktop\\HotelBillingSystemSpringBoot-Fixed (2)\\HotelBillingSystemSpringBoot-Fixed\\data\\bills.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String type = parts[2];
                    int days = Integer.parseInt(parts[3]);
                    double amount = Double.parseDouble(parts[4]);
                    String payment = parts[5];

                    bills.add(new Bill(id, name, type, days, amount, payment));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }
}

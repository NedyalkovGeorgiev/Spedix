package org.informatics.service;

import org.informatics.dto.TransportDTO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileService {

    public void exportTransportsReport(List<TransportDTO> transports, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("============================================================\n");
            writer.write("                SPEDIX LOGISTICS: TRANSPORT REPORT          \n");
            writer.write("============================================================\n");
            writer.write(String.format("%-5s | %-12s | %-12s | %-10s | %-15s\n",
                    "ID", "FROM", "TO", "PRICE", "DRIVER"));
            writer.write("------------------------------------------------------------\n");

            for (TransportDTO t : transports) {
                writer.write(String.format("%-5d | %-12s | %-12s | %-10.2f | %-15s\n",
                        t.getId(),
                        t.getStartPoint(),
                        t.getEndPoint(),
                        t.getPrice(),
                        t.getDriverName()));
            }

            writer.write("============================================================\n");
            System.out.println("Report successfully exported to: " + filePath);

        } catch (IOException e) {
            System.err.println("Failed to export report: " + e.getMessage());
        }
    }
}

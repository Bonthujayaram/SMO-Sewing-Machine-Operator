package com.cutm.smo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cutm.smo.dto.CuttingRequest;
import com.cutm.smo.dto.WorkerRequest;
import com.cutm.smo.models.BundleTable;
import com.cutm.smo.models.EmployeePerformanceTable;
import com.cutm.smo.models.OrdersTable;
import com.cutm.smo.models.WorkstationsTable;
import com.cutm.smo.services.SmoService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
    @Autowired
    private SmoService smoservice;


    @CrossOrigin(origins = "*")
    @PostMapping("/getjobid")
    public BundleTable getBundleByJobId(@RequestBody int jobid) {
        return smoservice.getBundleByJobId(jobid);
    }
	
    @CrossOrigin(origins = "*")
    @PostMapping("/getqr")
    public BundleTable getBundleByQrId(@RequestBody int jobid) { // Consider renaming or adjusting logic
        return smoservice.getBundleByJobId(jobid);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/setbundle")
    public ResponseEntity<Void> setBundle(@RequestBody CuttingRequest request) {
        try {
            logger.info("Received CuttingRequest: {}", request);
            smoservice.createBundle(request);
            logger.info("Bundle created successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Failed to create bundle", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/performbundle")
    public ResponseEntity<Void> performBundle(@RequestBody WorkerRequest request) {
        try {
            logger.info("Received WorkerRequest: employeeid={}, machineqr={}, qrid={}", 
                request.getEmployeename(), request.getMachineqr(), request.getJobqr());
            smoservice.recordWorkerData(request);
            logger.info("Worker data recorded successfully");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Failed to record worker data", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
   
    @CrossOrigin(origins = "*") 
    @GetMapping("/getorders")
    public List<OrdersTable> getOrders() {
        return smoservice.getAllOrders();
    }
    
    @CrossOrigin(origins = "*")
    @PostMapping("/setorders")
    public OrdersTable addOrder(@RequestBody OrdersTable order) {
        return smoservice.addOrder(order);
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/worker-names")
    public List<String> getWorkerNames() {
        return smoservice.getWorkerNames();
    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/machines")
    public List<WorkstationsTable> getAllMachines() {
        return smoservice.getAllMachines();
    }
    
    
    @CrossOrigin(origins = "*")
    @GetMapping("/worker/{employeeName}/performance")
    public ResponseEntity<List<EmployeePerformanceTable>> getWorkerPerformance(@PathVariable String employeeName) {
        logger.info("Received request for performance data of employee: {}", employeeName);

        List<EmployeePerformanceTable> performance = smoservice.getWorkerPerformance(employeeName);
        
        if (performance != null && !performance.isEmpty()) {
            logger.info("Performance data found for {}", employeeName);
            return ResponseEntity.ok(performance);
        } else {
            logger.warn("No performance data found for {}", employeeName);
            return ResponseEntity.notFound().build();
        }
    }
    
  



    

}
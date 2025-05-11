package com.cutm.smo.services;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cutm.smo.dto.CuttingRequest;
import com.cutm.smo.dto.WorkerRequest;
import com.cutm.smo.models.BundleTable;
import com.cutm.smo.models.EmployeeLoginTable;
import com.cutm.smo.models.EmployeePerformanceTable;
import com.cutm.smo.models.OrdersTable;
import com.cutm.smo.models.WorkstationJobsTable;
import com.cutm.smo.models.WorkstationsTable;
import com.cutm.smo.repositories.BundleTableRepository;
import com.cutm.smo.repositories.EmployeeLoginTableRepository;
import com.cutm.smo.repositories.EmployeePerformanceTableRepository;
import com.cutm.smo.repositories.OrdersTableRepository;
import com.cutm.smo.repositories.WorkstationJobsTableRepository;
import com.cutm.smo.repositories.WorkstationsTableRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SmoService {
	private int jobIdCounter = 0;
	
	@Autowired
    private OrdersTableRepository orderRepo;
	
@Autowired
BundleTableRepository bundleresp;

@Autowired
private EmployeePerformanceTableRepository employeePerformanceRepo;

@Autowired
private WorkstationJobsTableRepository workstationJobsRepo;

@Autowired
private EmployeeLoginTableRepository employeeLoginRepo;

@Autowired
private WorkstationsTableRepository workstationsRepo;




public BundleTable getBundleByJobId(int jobid) {
    return bundleresp.findByJobid(jobid).orElse(null);
}

public BundleTable getBundleByQrno(String qrno) {
    return bundleresp.findByQrid(qrno).orElse(null);
}
  
  public BundleTable createBundle(CuttingRequest request) {
      BundleTable bundle = new BundleTable();
      bundle.setQrid(request.getQrid());
      bundle.setType(request.getType());
      bundle.setSize(request.getSize());
      bundle.setQuantity(request.getQuantity());
      
      // Set default values for other fields
      bundle.setJobid(++jobIdCounter); // Generate unique jobid (see notes below)
      bundle.setStyle(""); // Default empty string; adjust if needed
      bundle.setIsfree(true); // Default to true
      // starttime and endtime left as null as per your instruction

      return bundleresp.save(bundle);
  }
  
 

  public void recordWorkerData(WorkerRequest request) {
      // Fetch jobid from BundleTable using qrid (qrno)
      BundleTable bundle = bundleresp.findByQrid(request.getJobqr())
          .orElseThrow(() -> new RuntimeException("No bundle found for qrid: " + request.getJobqr()));
      int jobId = bundle.getJobid();
      
   // Fetch related entities
      EmployeeLoginTable employee = employeeLoginRepo.findByEmployeeinfo_Name(request.getEmployeename())
          .orElseThrow(() -> new RuntimeException("Employee not found"));
      WorkstationsTable machine = workstationsRepo.findByQrid(request.getMachineqr())
          .orElseThrow(() -> new RuntimeException("Machine not found"));
      int machineId= machine.getMachineid();

      // Find existing records where outscan is null for this machineid and jobid
      WorkstationJobsTable existingWorkstationJob = workstationJobsRepo
          .findTopByMachineidAndJobidAndOutscanIsNull(machineId, jobId)
          .orElse(null);

      EmployeePerformanceTable existingPerformance = employeePerformanceRepo
          .findTopByMachineidAndJobidAndOutscanIsNull(machineId, jobId)
          .orElse(null);

      
     
      if (existingWorkstationJob == null) {
          // Odd submission: Create new records with starttime/inscan
          WorkstationJobsTable newWorkstationJob = new WorkstationJobsTable();
          newWorkstationJob.setEmployeeid(employee);
          newWorkstationJob.setMachineid(machineId);
          newWorkstationJob.setJobid(jobId);
          newWorkstationJob.setJobstatus(WorkstationJobsTable.JobStatus.inprogress);
          newWorkstationJob.setInscan(ZonedDateTime.now()); // Starttime
          newWorkstationJob.setOutscan(null); // Endtime null
          workstationJobsRepo.save(newWorkstationJob);
          log.info("Started new workstation job: jobid={}, machineqr={}", jobId, request.getMachineqr());

          EmployeePerformanceTable newPerformance = new EmployeePerformanceTable();
          newPerformance.setEmployeeid(employee.getEmployeeid());
          newPerformance.setMachineid(machineId);
          newPerformance.setJobid(jobId);
          newPerformance.setInscan(ZonedDateTime.now()); // Starttime
          newPerformance.setOutscan(null); // Endtime null
          employeePerformanceRepo.save(newPerformance);
          log.info("Started new performance record: jobid={}, employeeid={}", jobId, request.getEmployeename());
      } else {
          // Even submission: Update existing records with endtime/outscan
          existingWorkstationJob.setOutscan(ZonedDateTime.now()); // Endtime
          existingWorkstationJob.setJobstatus(WorkstationJobsTable.JobStatus.completed);
          workstationJobsRepo.save(existingWorkstationJob);
          log.info("Completed workstation job: jobid={}, machineqr={}", jobId, request.getMachineqr());

          if (existingPerformance != null) {
              existingPerformance.setOutscan(ZonedDateTime.now()); // Endtime
              employeePerformanceRepo.save(existingPerformance);
              log.info("Completed performance record: jobid={}, employeeid={}", jobId, request.getEmployeename());
          } else {
              log.warn("No matching performance record found to update for jobid={} and machineqr={}", 
                  jobId, request.getMachineqr());
          }
      }
  }
  
  public List<OrdersTable> getAllOrders() {
      return orderRepo.findAll();
  }
  
  public OrdersTable addOrder(OrdersTable order) {
	    return orderRepo.save(order); // Saves the order to the database
	}
 

  public List<String> getWorkerNames() {
      return employeeLoginRepo.findWorkerNames();
  }
  
  public List<WorkstationsTable> getAllMachines() {
      return workstationsRepo.findAll();
  }
  
  public List<EmployeePerformanceTable> getWorkerPerformance(String employeeName) {
	    log.info("Fetching performance data for employee: {}", employeeName);

	    Optional<EmployeeLoginTable> employeeOpt = employeeLoginRepo.findByEmployeeinfo_Name(employeeName);

	    if (employeeOpt.isPresent()) {
	        int employeeId = employeeOpt.get().getEmployeeid();
	        log.info("Found employee ID: {}", employeeId);

	        return employeePerformanceRepo.findByEmployeeid(employeeId);
	    } else {
	        log.warn("Employee not found: {}", employeeName);
	        return Collections.emptyList();
	    }
	}
}

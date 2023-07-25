package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Employee;
import com.csi.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class EmployeeController {


    @Autowired
    EmployeeService employeeServiceImpl;


    @PutMapping("/savedata")
    public ResponseEntity<Employee> saveData(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeServiceImpl.saveData(employee));
    }

    @GetMapping("/getalldata")
    public ResponseEntity<List<Employee>> getAllData() {
        return ResponseEntity.ok(employeeServiceImpl.getAllData());
    }

    @GetMapping("/getdatabyid/{empId}")
    public ResponseEntity<Optional<Employee>> getDataById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeServiceImpl.getDataById(empId));
    }

    @PutMapping("/updatedata/{empId}")
    public ResponseEntity<Employee> updateData(@PathVariable int empId, @RequestBody Employee employee) {
        Employee employee1 = employeeServiceImpl.getDataById(empId).orElseThrow(() -> new RecordNotFoundException("Employee Id Does Not Exist"));

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpDob(employee.getEmpDob());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpAddress(employee.getEmpAddress());

        return new ResponseEntity<>(employeeServiceImpl.updateData(employee1), HttpStatus.CREATED);

    }

    @PatchMapping("/changeaddress/{empId}/{empAddress}")
    public ResponseEntity<Employee> chnagesOfAddress(@PathVariable int empId, @PathVariable String empAddress) {
        Employee employee = employeeServiceImpl.getDataById(empId).orElseThrow(() -> new RecordNotFoundException("Employee Id Does Not Exist"));
        employee.setEmpAddress(empAddress);
        return ResponseEntity.ok(employeeServiceImpl.patchData(employee));
    }

    @DeleteMapping("/deletedatabyid/{empId}")
    public ResponseEntity<String> deleteDataById(@PathVariable int empId) {
        employeeServiceImpl.deleteDataById(empId);
        return ResponseEntity.ok("DATA DELETED SUCCESFULLY");
    }
    @DeleteMapping("/deletealldata")
    public ResponseEntity<String> deleteAllData(){
        employeeServiceImpl.deleteAllData();
        return ResponseEntity.ok("Data Deleted");
    }

}

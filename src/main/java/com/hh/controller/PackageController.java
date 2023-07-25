package com.hh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hh.dto.PackageDTO;
import com.hh.entity.Package;
import com.hh.repository.PackageRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/packages")
public class PackageController {
	
	@Autowired
	private PackageRepository packageRepository;
	
	private Logger logger = LoggerFactory.getLogger(PackageController.class);
	
	@GetMapping("/")
	public String packages() {
		return "packages/package_home";
	}
	
	@GetMapping("/add_package_form")
	public String addPackageForm(@ModelAttribute("package") PackageDTO packageDto) {
		//packageDto = new PackageDTO();
		return "packages/add_package_form";
	}
	
	@PostMapping("/add_package")
	public String addPackage(@ModelAttribute("package") @Valid PackageDTO packageDto, BindingResult bindingResult) {
		logger.info("New package to add "+packageDto);
		if(bindingResult.hasErrors()) {
			logger.warn("Added package have invalid input values. "+bindingResult);
			return "packages/add_package_form";
		}
		Package pkg = new Package(packageDto);
		
		pkg = packageRepository.save(pkg);
		logger.info("Package Saved with package id "+pkg.getId());
		
		return "redirect:/packages/view_packages";
	}
	
	@GetMapping("/view_packages")
	public String viewPackages(Model model) {
		List<Package> allPackages = packageRepository.findAll();
		model.addAttribute("allPackages", allPackages);
		
		return "packages/all_packages";
	}
	
	@GetMapping("/edit/{package_id}")
	public String editPackageForm(@PathVariable("package_id") Integer packageId,  Model model) {
		Package pkg = packageRepository.findById(packageId).get();
		model.addAttribute("package", pkg);
		return "packages/edit_package_form";
	}
	
	@PostMapping("/edit_package")
	public String editPackage(@ModelAttribute("package") @Valid Package pkg, BindingResult bindingResult) {
		logger.info("package came for edit "+pkg);
		if(bindingResult.hasErrors()) {
			logger.warn("Package have invalid input values.");
			return "packages/edit_package_form";
		}
				
		pkg = packageRepository.save(pkg);
		logger.info("Package Saved with package id "+pkg.getId());
		
		return "redirect:/packages/view_packages";
	}
	
	@GetMapping("/delete/{package_id}")
	public String deletePackage(@PathVariable("package_id") Integer packageId,  Model model) {
		Package pkg = packageRepository.findById(packageId).get();
		if(pkg!=null) {
			packageRepository.delete(pkg);
			logger.info("Package '"+pkg.getName()+"' deleted.");
		}else {
			logger.warn("Invalid package id "+packageId+". Cant delete!");
		}
		return "redirect:/packages/view_packages";
	}
	
	
}

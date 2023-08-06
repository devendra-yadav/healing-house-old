package com.hh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hh.dto.PackageDTO;
import com.hh.entity.Package;
import com.hh.repository.PackageRepository;

@Service
public class PackageService {
	
	@Autowired
	private PackageRepository packageRepository;
	
	public Package savePackage(PackageDTO packageDto) {
		Package pkg = new Package(packageDto);
		pkg = packageRepository.save(pkg);
		return pkg;
	}
	
	public Package savePackage(Package pkg) {
		pkg = packageRepository.save(pkg);
		return pkg;
	}
	
	public List<Package> getAllPackages(){
		List<Package> allPackages = packageRepository.findAll();
		return allPackages;
	}
	
	public Package getPackageById(int packageId) {
		Package pkg = packageRepository.findById(packageId).get();
		return pkg;
	}
	
	public void deletePackageById(int packageId) {
		packageRepository.deleteById(packageId);
	}
	
}

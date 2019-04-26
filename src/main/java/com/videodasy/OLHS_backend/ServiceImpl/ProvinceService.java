package com.videodasy.OLHS_backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodasy.OLHS_backend.Dao.ProvinceDao;
import com.videodasy.OLHS_backend.Domain.Province;
import com.videodasy.OLHS_backend.Service.IProvinceService;

@Service
public class ProvinceService implements IProvinceService {
	
	@Autowired
	private ProvinceDao provinceDao;

	@Override
	public String create(Province province) {
		
		return provinceDao.createProvince(province);
	}

	@Override
	public String update(Province province) {
		
		return provinceDao.updateProvince(province);
	}

	@Override
	public String delete(Province province) {
		
		return provinceDao.deleteProvince(province);
	}

	@Override
	public Province findByUuid(String uuid) {
		
		return provinceDao.findByUuid(uuid);
	}

	@Override
	public List<Province> all() {
	
		return provinceDao.allProvinces();
	}

}

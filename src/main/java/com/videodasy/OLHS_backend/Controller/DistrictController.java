package com.videodasy.OLHS_backend.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.videodasy.OLHS_backend.Domain.District;
import com.videodasy.OLHS_backend.Domain.Province;
import com.videodasy.OLHS_backend.Service.IDistrictService;
import com.videodasy.OLHS_backend.Service.IProvinceService;
import com.videodasy.OLHS_backend.Utility.Messages;
import com.videodasy.OLHS_backend.Utility.ResponseBean;

@RestController
@RequestMapping(value="/districts")
public class DistrictController {

	@Autowired
	private IProvinceService provinceService;
	
	@Autowired
	private IDistrictService districtService;
	
	/**
	 * The Method to create District
	 * @param request
	 * @param map
	 * @return
	 */
	
	@RequestMapping(value="/save",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>createProvince(HttpServletRequest request,@RequestBody Disreg reg){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					Province province=provinceService.findByUuid(reg.getProuuid());
					District district=new District();
					district.setName(reg.getName());
					district.setProvince(province);
					districtService.create(district);
					
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.save);
					rb.setObject(district);
				}else {
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN ");
					rb.setObject(null);
				}
			}else {
				rb.setCode(Messages.TOKEN_NOT_FOUND);
				rb.setDescription("  TOKEN NOT FOUND");
				rb.setObject(null);
			}
				
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to Save province");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	
	/**
	 * The method to retrieve all district
	 * @param request
	 * @return
	 */
	
	
	@RequestMapping(value="",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>allProvinces(HttpServletRequest request){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					List<District> districts=districtService.all();
				
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription("Retrieved Successfuly");
					rb.setObject(districts);
				}else {
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN ");
					rb.setObject(null);
				}
			}else {
				rb.setCode(Messages.TOKEN_NOT_FOUND);
				rb.setDescription("  TOKEN NOT FOUND");
				rb.setObject(null);
			}
				
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to Save province");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	/**
	 * The method to retrieve District  by Uuid
	 * @param request
	 * @param uuid
	 * @return
	 */

	@RequestMapping(value="/{uuid}",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>ProvinceByUuid(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					District district=districtService.findByUuid(uuid);
				
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.save);
					rb.setObject(district);
				}else {
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN ");
					rb.setObject(null);
				}
			}else {
				rb.setCode(Messages.TOKEN_NOT_FOUND);
				rb.setDescription("  TOKEN NOT FOUND");
				rb.setObject(null);
			}
				
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to Retrieve province");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	

	@RequestMapping(value="delete/{uuid}",method=RequestMethod.DELETE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>deleteProvince(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
			
					District district=districtService.findByUuid(uuid);
				    districtService.delete(district); 
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.delete);
					rb.setObject(district);
					
				}else {
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN ");
					rb.setObject(null);
				}
			}else {
				rb.setCode(Messages.TOKEN_NOT_FOUND);
				rb.setDescription("  TOKEN NOT FOUND");
				rb.setObject(null);
			}
				
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to Retrieve province");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>updateProvince(HttpServletRequest request, @RequestBody Map<String,String> map){
		ResponseBean rb=new ResponseBean();
		try {
			
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
			
		      District district=districtService.findByUuid(map.get("districtuuid"));
		      district.setName(map.get("name"));
				  
		       districtService.update(district);
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(" province is successfuly updated");
					rb.setObject(district);
				}else {
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN ");
					rb.setObject(null);
				}
			}else {
				rb.setCode(Messages.TOKEN_NOT_FOUND);
				rb.setDescription("  TOKEN NOT FOUND");
				rb.setObject(null);
			}
			  
			
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to  updated A province");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	/**
	 * The method to retrieve districts by province uuid
	 * @param request
	 * @param uuid
	 * @return
	 */
	
	@RequestMapping(value="/province/{uuid}",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>allDistrictByProvince(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					Province province=provinceService.findByUuid(uuid);
					
					List<District> districts=districtService.FindDistrictByProvince(province.getId());
				
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription("Retrieved Successfuly");
					rb.setObject(districts);
				}else {
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN ");
					rb.setObject(null);
				}
			}else {
				rb.setCode(Messages.TOKEN_NOT_FOUND);
				rb.setDescription("  TOKEN NOT FOUND");
				rb.setObject(null);
			}
				
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to Save province");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	public static class Disreg{
		private String name;
		private String prouuid;
		

		public String getProuuid() {
			return prouuid;
		}

		public void setProuuid(String prouuid) {
			this.prouuid = prouuid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		 
	}
	
}

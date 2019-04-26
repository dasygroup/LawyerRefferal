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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.videodasy.OLHS_backend.Domain.Province;
import com.videodasy.OLHS_backend.Service.IProvinceService;
import com.videodasy.OLHS_backend.Utility.Messages;
import com.videodasy.OLHS_backend.Utility.ResponseBean;

@RestController
@RequestMapping(value="/provinces")
public class ProvinceController {
	
	@Autowired
	private IProvinceService provinceService;
	
	/**
	 * The Method to create Province
	 * @param request
	 * @param map
	 * @return
	 */
	
	@RequestMapping(value="/save",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>createProvince(HttpServletRequest request,@RequestBody Provincea pro){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			String username=request.getHeader("doneBy");
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					Province province=new Province();
					province.setName(pro.getName());
					province.setDoneBy(username);
					provinceService.create(province);
					
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.save);
					rb.setObject(province);
				}else {
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN");
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
	
	
	
	@RequestMapping(value="",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>allProvinces(HttpServletRequest request){
		ResponseBean rb=new ResponseBean();
		try {
				
			String token=request.getHeader(Messages.olhs_token_name);
			String username=request.getHeader("doneBy");
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					List<Province> provinces=provinceService.all();
				
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription("Retrieved Successfuly");
					rb.setObject(provinces);
				}else {
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN");
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
	 * The method to retrieve Province by Uuid
	 * @param request
	 * @param uuid
	 * @return
	 */

	@RequestMapping(value="/{uuid}",method=RequestMethod.GET,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>ProvinceByUuid(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			
			String token=request.getHeader(Messages.olhs_token_name);
			String username=request.getHeader("doneBy");
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					Province province=provinceService.findByUuid(uuid);
				
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.save);
					rb.setObject(province);
					
		}else {
			rb.setCode(Messages.INCORRECT_TOKEN);
			rb.setDescription(" INCORRECT TOKEN");
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
			String username=request.getHeader("doneBy");
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					Province province=provinceService.findByUuid(uuid);
				    provinceService.delete(province); 
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.delete);
					rb.setObject(province);
		}else {
			rb.setCode(Messages.INCORRECT_TOKEN);
			rb.setDescription(" INCORRECT TOKEN");
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
			String username=request.getHeader("doneBy");
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
			
		      Province province=provinceService.findByUuid(map.get("prouuid"));
		       province.setName(map.get("name"));
				  
		       provinceService.update(province);
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(" province is successfuly updated");
					rb.setObject(province);
				 
		}else {
			rb.setCode(Messages.INCORRECT_TOKEN);
			rb.setDescription(" INCORRECT TOKEN");
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
	
	
	
	public static class Provincea{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		 
	}
	
}

package com.videodasy.OLHS_backend.Controller;


import java.util.Date;
import java.util.List;

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

import com.videodasy.OLHS_backend.Domain.CaseType;
import com.videodasy.OLHS_backend.Domain.District;
import com.videodasy.OLHS_backend.Domain.Lawyer;
import com.videodasy.OLHS_backend.Domain.Martalstatus;
import com.videodasy.OLHS_backend.Domain.Registrant;
import com.videodasy.OLHS_backend.Domain.RegistrantCategory;
import com.videodasy.OLHS_backend.Domain.WorkStatus;
import com.videodasy.OLHS_backend.Service.IDistrictService;
import com.videodasy.OLHS_backend.Service.ILawyerService;
import com.videodasy.OLHS_backend.Service.IRegistrantService;
import com.videodasy.OLHS_backend.Utility.Messages;
import com.videodasy.OLHS_backend.Utility.ResponseBean;

@RestController
@RequestMapping(value="/lawyers")
public class LawyerController {
	
	@Autowired
	private ILawyerService lawyerService;
	
	@Autowired
	private IRegistrantService registrantService;
	
	@Autowired
	private IDistrictService districtService;
	
	@RequestMapping(value = "/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody InnerLawyer innerLawyer, HttpServletRequest request){
        ResponseBean responseBean=new ResponseBean();
        try{
            String token=request.getHeader(Messages.olhs_token_name);
            String doneBy=request.getHeader("doneBy");
            if(token!=null){
                Registrant registrant=registrantService.findByUuid(innerLawyer.getReguuid());
                District district=districtService.findByUuid(innerLawyer.getDistrictuuid());
                Lawyer lawyer=new Lawyer();
                lawyer.setNames(innerLawyer.getNames());
                lawyer.setAddress(innerLawyer.getAddress());
                lawyer.setDob(innerLawyer.getDob());
                lawyer.setDistrict(district);
                lawyer.setWorkstatus(WorkStatus.ACTIVE);
                
        
                    if (registrant == null) {
                        responseBean.setCode(Messages.ERROR_CODE);
                        responseBean.setDescription("Registrant does not exist");
                        responseBean.setObject(null);
                    } else {
                        
                    	lawyer.setDoneBy(doneBy);
                    	lawyer.setRegistrant(registrant);
                        responseBean.setCode(Messages.SUCCESS_CODE);
                        responseBean.setDescription(Messages.save);
                        responseBean.setObject(lawyer);
                    }
                

            }else{
                responseBean.setCode(Messages.TOKEN_NOT_FOUND);
                responseBean.setDescription(" TOKEN NOT FOUND ");
                responseBean.setObject(null);
            }

        }catch (Exception ex){
            ex.printStackTrace();
           
            responseBean.setCode(Messages.ERROR_CODE);
            responseBean.setDescription(Messages.error);
            responseBean.setObject(null);
        }
        return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
    }
      
	
	@RequestMapping(value="/",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>retrieveHarvests(HttpServletRequest request){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					List<Lawyer>lawyers=lawyerService.all();
					
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription("All Lawyers are successfuly retrieved");
					rb.setObject(lawyers);
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
			rb.setDescription("failed to retrieve harvests");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{uuid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>retrieveHarvestById(HttpServletRequest request, @PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					Lawyer lawyer=lawyerService.findByUuid(uuid);
					   
					   if(lawyer==null) {
						   rb.setCode(Messages.SUCCESS_CODE);
							rb.setDescription(" lawyer that you want was not found");
					   }
					   else {
						rb.setCode(Messages.SUCCESS_CODE);
						rb.setDescription(" lawyer is successfuly retrieved");
						rb.setObject(lawyer);
					   }
				}else 
				{
					rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription("  INCORRECT TOKEN");
					rb.setObject(null);
				}
			}else {
				rb.setCode(Messages.TOKEN_NOT_FOUND);
				rb.setDescription("  TOKEN NOT FOUND");
				rb.setObject(null);
			}
		   
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to retrieve A lawyer");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/delete/{uuid}",method=RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>deleteHarvest(HttpServletRequest request, @PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			
			String token=request.getHeader(Messages.olhs_token_name);
			String username=request.getHeader("doneBy");
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					Lawyer lawyer=lawyerService.findByUuid(uuid);
					   if(lawyer==null) {
						   rb.setCode(Messages.NOT_FOUND);
							rb.setDescription(" lawyer you want to delete does not exist");
							
					   }else {
						   lawyer.setLastUpdatedBy(username);
					     lawyerService.delete(lawyer);
						rb.setCode(Messages.SUCCESS_CODE);
						rb.setDescription(" lawyer is successfuly deleted");
						rb.setObject(null);
					   }
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
			rb.setDescription("failed to delete A harvest");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>updateHarvest(HttpServletRequest request, @RequestBody Lawyer lawyer){
		ResponseBean rb=new ResponseBean();
		try {
			
			String token=request.getHeader(Messages.olhs_token_name);
			String username=request.getHeader("doneBy");
		  if(token!=null) {
			  if(token.equalsIgnoreCase(Messages.token)) {
				  lawyer.setLastUpdatedBy(username);
				  lawyerService.update(lawyer);
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(" lawyer is successfuly updated");
					rb.setObject(lawyer);
				  
			  }else 
			  {
				  rb.setCode(Messages.INCORRECT_TOKEN);
					rb.setDescription(" INCORRECT TOKEN");
					rb.setObject(null);
			  }
		  }else 
		  {
			  rb.setCode(Messages.TOKEN_NOT_FOUND);
				rb.setDescription("  TOKEN NOT FOUND");
				rb.setObject(null);
		  }
		    
			
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to  updated A lawyer");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/district/{uuid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>retrieveMemberHarvest(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			
			 String token=request.getHeader(Messages.olhs_token_name);
			 
			 if(token!=null) {
				 if(token.equalsIgnoreCase(Messages.token)) {
					 District district=districtService.findByUuid(uuid);
					 
					 List<Lawyer>list=lawyerService.FindByDistrictId(district.getId());
						
						rb.setCode(Messages.SUCCESS_CODE);
						rb.setDescription("All Lawyers in District are successfuly Retrieved");
						rb.setObject(list);
					 
				 }else 
				 {
					    rb.setCode(Messages.INCORRECT_TOKEN);
						rb.setDescription("  INCORRECT 	TOKEN ");
						rb.setObject(null);
				 }
			 }else {
				    rb.setCode(Messages.TOKEN_NOT_FOUND);
					rb.setDescription("  TOKEN NOT FOUND");
					rb.setObject(null);
			 }
			
			
			
		} catch (Exception e) {
			rb.setCode(Messages.ERROR_CODE);
			rb.setDescription("failed to  Retrieve All harvest");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	
	
	public static class InnerLawyer {
        private String doneBy;
        private String reguuid;
        private String districtuuid;
        private String address;
        private String maritalStatus;
        private String names;
        private Date dob;
        private String workstatus;
        private String identity;

        

        public String getNames() {
            return names;
        }

        /**
         * @return the isMember
         */
        
        public void setNames(String names) {
            this.names = names;
        }

       

        

        public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public Date getDob() {
			return dob;
		}

		public void setDob(Date dob) {
			this.dob = dob;
		}

		public String getWorkstatus() {
			return workstatus;
		}

		public void setWorkstatus(String workstatus) {
			this.workstatus = workstatus;
		}

		public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getDoneBy() {
            return doneBy;
        }

        public void setDoneBy(String doneBy) {
            this.doneBy = doneBy;
        }

		public String getReguuid() {
			return reguuid;
		}

		public void setReguuid(String reguuid) {
			this.reguuid = reguuid;
		}

		public String getDistrictuuid() {
			return districtuuid;
		}

		public void setDistrictuuid(String districtuuid) {
			this.districtuuid = districtuuid;
		}

       
    }

 

}

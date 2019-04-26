package com.videodasy.OLHS_backend.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.videodasy.OLHS_backend.Domain.CaseType;
import com.videodasy.OLHS_backend.Domain.District;
import com.videodasy.OLHS_backend.Domain.Gender;
import com.videodasy.OLHS_backend.Domain.Lawyer;
import com.videodasy.OLHS_backend.Domain.Profile;
import com.videodasy.OLHS_backend.Domain.Registrant;
import com.videodasy.OLHS_backend.Domain.RegistrantCategory;
import com.videodasy.OLHS_backend.Domain.WorkStatus;
import com.videodasy.OLHS_backend.Service.IDistrictService;
import com.videodasy.OLHS_backend.Service.ILawyerService;
import com.videodasy.OLHS_backend.Service.IRegistrantService;
import com.videodasy.OLHS_backend.Utility.Messages;
import com.videodasy.OLHS_backend.Utility.ResponseBean;

@RestController
@RequestMapping(value="/registrants")
public class RegistrantController {
	
	
	@Autowired
	private IRegistrantService registrantService;
	
	@Autowired
	private ILawyerService lawyerService;
	
	@Autowired
	private IDistrictService districtService;
	

	/**
	 * The Method to create Registrant
	 * @param request
	 * @param map
	 * @return
	 */
	
	@RequestMapping(value="/save",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>createRegistrant(HttpServletRequest request,@RequestBody Map<String,String>map){
		ResponseBean rb=new ResponseBean();
		try {
			String username=request.getHeader("doneBy");
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
					Registrant registrant=new Registrant();
					registrant.setNames(map.get("names"));
					registrant.setGender(Gender.valueOf(map.get("gender")));
					registrant.setEmail(map.get("email"));
					registrant.setRegistrantCategory(RegistrantCategory.valueOf(map.get("category")));
					registrant.setPhone(map.get("phone"));
					registrant.setDoneBy(username);
					registrantService.create(registrant);
					
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.save);
					rb.setObject(registrant);
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
     * Save With register Admin
     * 
     * @param regAdmin
     * @param request
     * @return
     */

    @RequestMapping(value = "/savewithadmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createWithAdmin(@RequestBody RegAdmin regAdmin, HttpServletRequest request) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String token = request.getHeader(Messages.olhs_token_name);
            
            if (token != null) {
                if(token.equalsIgnoreCase(Messages.token)){
                    Registrant registrant = registrantService.buildRegistrant(regAdmin);
                    /**
                     * Checking if the Registrant with same Username exist
                     */
                   
                    Optional<Registrant> optional = Optional
                            .ofNullable(registrantService.findByIdentity(registrant.getIdentity()));
                    if (!optional.isPresent()) {
                        String doneBy = request.getHeader("doneBy");
                        registrant.setDoneBy(doneBy);
                        registrant.setLastUpdatedBy(doneBy);
                        responseBean.setCode(Messages.SUCCESS_CODE);
                        responseBean.setObject(registrant);
                        responseBean.setDescription(registrantService.create(registrant));
                        SystemUser user = new SystemUser();
                        user.setNames(registrant.getNames());
                     
                        user.setApplicationName(regAdmin.getApplicationName());
                        user.setNationalId(regAdmin.getNationalId());
                        user.setUsername(regAdmin.getUsername());
                        user.setPassword(regAdmin.getPassword());
                        user.setRole(regAdmin.getRole());
                        user.setObjectName("registrant");
                        user.setObjectId(registrant.getId() + "");
                        user.setGender(registrant.getGender());
                        user.setPhone(regAdmin.getPhone());
                        user.setEmail(regAdmin.getEmail());
    
                        final String uri = Messages.myum_url + "/users/save";
                        RestTemplate restTemplate = new RestTemplate();
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.set("doneby", doneBy);
                        headers.set("myum_token", "MYUM" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                        HttpEntity<SystemUser> entity = new HttpEntity<SystemUser>(user, headers);
                        ResponseBean response = restTemplate.postForObject(uri, entity, ResponseBean.class);
    
                    } else {
                        responseBean.setDescription("REGISTRANT with IDENTITY: " + registrant.getIdentity() + " already exist");
                    }
                }else{
                    responseBean.setCode(Messages.INCORRECT_TOKEN);
                    responseBean.setDescription("Incorrect token ");
                    responseBean.setObject(null);
                }
               
            } else {
                responseBean.setCode(Messages.TOKEN_NOT_FOUND);
                responseBean.setDescription(" TOKEN NOT FOUND ");
                responseBean.setObject(null);
            }
        } catch (Exception ex) {
            // Todo: correct the error
            System.out.println("RegistrantController (createWithAdmin) " + ex.getMessage());
            responseBean.setCode(Messages.ERROR_CODE);
            responseBean.setDescription(Messages.error);
            responseBean.setObject(null);
        }
        return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
    }

	
    /**
     * Save With Lawyer With Registrant
     * 
     * @param regAdmin
     * @param request
     * @return
     */

    @RequestMapping(value = "/savewithLawyer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createLawyerWithRegistrant(@RequestBody RegLawyer reglawyer, HttpServletRequest request) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String token = request.getHeader(Messages.olhs_token_name);
            
            if (token != null) {
                if(token.equalsIgnoreCase(Messages.token)){
                    Registrant registrant = registrantService.buildRegistrant(reglawyer);
                    /**
                     * Checking if the Registrant with same Username exist
                     */
                   
                    Optional<Registrant> optional = Optional
                            .ofNullable(registrantService.findByIdentity(registrant.getIdentity()));
                    if (!optional.isPresent()) {
                        String doneBy = request.getHeader("doneBy");
                        registrant.setDoneBy(doneBy);
                        registrant.setLastUpdatedBy(doneBy);
                        responseBean.setCode(Messages.SUCCESS_CODE);
                        responseBean.setObject(registrant);
                        responseBean.setDescription(registrantService.create(registrant));
                        
                        Lawyer lawyer=new Lawyer();
                        District district=districtService.findByUuid(reglawyer.getDistrictuuid());
                        lawyer.setAddress(reglawyer.getAddress());
                        lawyer.setCasetype(reglawyer.getCasetype());
                        lawyer.setDob(reglawyer.getDob());
                        lawyer.setDistrict(district);
                        lawyer.setNames(reglawyer.getNames());
                        lawyer.setWorkstatus(reglawyer.getStatus());
                        lawyer.setRegistrant(registrant);
                        
                        SystemUser user = new SystemUser();
                        user.setNames(registrant.getNames());
                     
                        user.setApplicationName(reglawyer.getApplicationName());
                        user.setNationalId(reglawyer.getNationalId());
                        user.setUsername(reglawyer.getUsername());
                        user.setPassword(reglawyer.getPassword());
                        user.setRole(reglawyer.getRole());
                        user.setObjectName("registrant");
                        user.setObjectId(registrant.getId() + "");
                        user.setGender(registrant.getGender());
                        user.setPhone(reglawyer.getPhone());
                        user.setEmail(reglawyer.getEmail());
    
                        final String uri = Messages.myum_url + "/users/save";
                        RestTemplate restTemplate = new RestTemplate();
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.set("doneby", doneBy);
                        headers.set("myum_token", "MYUM" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                        HttpEntity<SystemUser> entity = new HttpEntity<SystemUser>(user, headers);
                        ResponseBean response = restTemplate.postForObject(uri, entity, ResponseBean.class);
                        
    
                    } else {
                        responseBean.setDescription("Entity with IDENTITY: " + registrant.getIdentity() + " already exist");
                    }
                }else{
                    responseBean.setCode(Messages.INCORRECT_TOKEN);
                    responseBean.setDescription("Incorrect token ");
                    responseBean.setObject(null);
                }
               
            } else {
                responseBean.setCode(Messages.TOKEN_NOT_FOUND);
                responseBean.setDescription(" TOKEN NOT FOUND ");
                responseBean.setObject(null);
            }
        } catch (Exception ex) {
            // Todo: correct the error
            System.out.println("RegistrantController (createWithAdmin) " + ex.getMessage());
            responseBean.setCode(Messages.ERROR_CODE);
            responseBean.setDescription(Messages.error);
            responseBean.setObject(null);
        }
        return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
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
				
					List<Registrant> registrants=registrantService.all();
				
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription("Retrieved Successfuly");
					rb.setObject(registrants);
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
				
					Registrant registrant=registrantService.findByUuid(uuid);
				
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.save);
					rb.setObject(registrant);
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
	
	/**
	 * 	The Method To delete Registrant By uuid
	 * @param request
	 * @param uuid
	 * @return
	 */

	@RequestMapping(value="delete/{uuid}",method=RequestMethod.DELETE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>deleteRegistrant(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
			
					Registrant registrant=registrantService.findByUuid(uuid);
					registrantService.delete(registrant); 
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.delete);
					rb.setObject(registrant);
					
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
	
	/**
	 * The Method to Update The Registrant
	 * @param request
	 * @param map
	 * @return
	 */
	
	@RequestMapping(value="/update",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>updateProvince(HttpServletRequest request, @RequestBody Map<String,String> map){
		ResponseBean rb=new ResponseBean();
		try {
			String username=request.getHeader("doneBy");
			String token=request.getHeader(Messages.olhs_token_name);
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
				
			
		      Registrant registrant=registrantService.findByUuid(map.get("rguuid"));
		        registrant.setNames(map.get("name"));
		        registrant.setGender(Gender.valueOf(map.get("gender")));
				registrant.setEmail(map.get("email"));
				registrant.setRegistrantCategory(RegistrantCategory.valueOf(map.get("category")));
				registrant.setPhone(map.get("phone"));
				registrant.setDoneBy(username);
		       registrantService.update(registrant);
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(" province is successfuly updated");
					rb.setObject(registrant);
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
	
	
	@RequestMapping(value = "byid/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findbYId(@PathVariable long id, HttpServletRequest request) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String token = request.getHeader(Messages.olhs_token_name);
            if (token != null) {
                if(token.equalsIgnoreCase(Messages.token)){
                    Optional<Registrant> optional = Optional.ofNullable(registrantService.findOne(id));
                    if (optional.isPresent()) {
                        Registrant r=optional.get();
                        r.setPicture("/pictures/registrant/"+r.getUuid());
                        responseBean.setCode(Messages.SUCCESS_CODE);
                        responseBean.setObject(r);
                    } else {
                        responseBean.setCode(Messages.SUCCESS_CODE);
                        responseBean.setDescription(Messages.not_found);
                        responseBean.setObject(null);
                    }
                }else{
                      responseBean.setDescription("INCORRECT TOKEN ");
                    responseBean.setObject(null);
                }
                
            } else {
                responseBean.setCode(Messages.INCORRECT_TOKEN);
                responseBean.setDescription("INCCORECT TOKEN ");
                responseBean.setObject(null);
            }
        } catch (Exception ex) {
            responseBean.setCode(Messages.ERROR_CODE);
            responseBean.setDescription(Messages.error);
            responseBean.setObject(null);
        }
        return new ResponseEntity<Object>(responseBean, HttpStatus.OK);
    }


	
    /**
     * InnerClass for Registration With Admin
     */
    public static class RegAdmin extends Profile {

        /**
         * Registrant Admin properties
         */
        private String Names;
        private String username;
        private String role;
        private String applicationName;
        private String objectName;
        private String objectId;
        private String nationalId;
        private String password;

        /**
         * Registrant Properties
         */
     
        private Date doneAt; // = new Timestamp(new Date().getTime());
        private String doneBy = "";
        private Date lastUpdatedAt; // = null;
        private String lastUpdatedBy = "";
        private boolean deletedStatus = false;
        private String martal;
        private String registrantCategory;
        private String file;
        private String email;
   	    private String phone;
   	   

       
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public Date getDoneAt() {
            return doneAt;
        }

        public void setDoneAt(Date doneAt) {
            this.doneAt = doneAt;
        }

        public String getDoneBy() {
            return doneBy;
        }

        public void setDoneBy(String doneBy) {
            this.doneBy = doneBy;
        }

        public Date getLastUpdatedAt() {
            return lastUpdatedAt;
        }

        public void setLastUpdatedAt(Date lastUpdatedAt) {
            this.lastUpdatedAt = lastUpdatedAt;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public boolean isDeletedStatus() {
            return deletedStatus;
        }

        public void setDeletedStatus(boolean deletedStatus) {
            this.deletedStatus = deletedStatus;
        }

        public String getRegistrantCategory() {
            return registrantCategory;
        }

        public void setRegistrantCategory(String registrantCategory) {
            this.registrantCategory = registrantCategory;
        }


       

        public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getNames() {
			return Names;
		}

		public void setNames(String names) {
			Names = names;
		}

		public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getApplicationName() {
            return applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

		public String getMartal() {
			return martal;
		}

		public void setMartal(String martal) {
			this.martal = martal;
		}

		

    }

    public static class SystemUser {
       
        private String Names;
        private String role;
        private String ApplicationName;
        private String objectName;
        private String objectId;
        private String nationalId;
        private String password;
        private String username;
        private String phone;
   	    private Gender gender;
   	    private String email;
   	    
   	    
   	    

        public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		

		public Gender getGender() {
			return gender;
		}

		public void setGender(Gender gender2) {
			this.gender = gender2;
		}

		public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

       
       
        public String getNames() {
			return Names;
		}

		public void setNames(String names) {
			Names = names;
		}

		public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getApplicationName() {
            return ApplicationName;
        }

        public void setApplicationName(String applicationName) {
            ApplicationName = applicationName;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        
        

    }

    // INNER CLASS TO SAVE LAWYER
    
    public static class RegLawyer extends RegAdmin{
  	  private String address;
  	  private CaseType casetype;
  	  private Date dob;
  	  private WorkStatus status;
  	  private String districtuuid;
  	  private String identity;
  	 

  	  private RegistrantCategory category;
  	  
  	
  	public String getAddress() {
  		return address;
  	}
  	public void setAddress(String address) {
  		this.address = address;
  	}
  	public CaseType getCasetype() {
  		return casetype;
  	}
  	public void setCasetype(CaseType casetype) {
  		this.casetype = casetype;
  	}
  	public Date getDob() {
  		return dob;
  	}
  	public void setDob(Date dob) {
  		this.dob = dob;
  	}
  	public WorkStatus getStatus() {
  		return status;
  	}
  	public void setStatus(WorkStatus status) {
  		this.status = status;
  	}
  
  	public String getDistrictuuid() {
		return districtuuid;
	}
	public void setDistrictuuid(String districtuuid) {
		this.districtuuid = districtuuid;
	}
	
  	
  	public String getIdentity() {
  		return identity;
  	}
  	public void setIdentity(String identity) {
  		this.identity = identity;
  	}
  	
  	public RegistrantCategory getCategory() {
  		return category;
  	}
  	public void setCategory(RegistrantCategory category) {
  		this.category = category;
  	}
  	  
  	  
  	  
    }
	
	
}

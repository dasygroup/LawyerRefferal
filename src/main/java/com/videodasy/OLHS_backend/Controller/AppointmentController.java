package com.videodasy.OLHS_backend.Controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.videodasy.OLHS_backend.Domain.Appointment;
import com.videodasy.OLHS_backend.Domain.AppointmentStatus;
import com.videodasy.OLHS_backend.Domain.Registrant;
import com.videodasy.OLHS_backend.Service.IAppointmentService;
import com.videodasy.OLHS_backend.Service.ILawyerService;
import com.videodasy.OLHS_backend.Service.IRegistrantService;
import com.videodasy.OLHS_backend.Utility.Messages;
import com.videodasy.OLHS_backend.Utility.ResponseBean;

@RestController
@RequestMapping(value="/appointments")
public class AppointmentController {
	@Autowired
	private IAppointmentService appointmentService;
	@Autowired
	private IRegistrantService registrantService;
	@Autowired
	private ILawyerService lawyerService;
	
	
	@RequestMapping(value="/save",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>createAppointment(HttpServletRequest request,@RequestParam Map<String,String>map){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			String username=request.getHeader("doneBy");
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					Appointment appoint=new Appointment();
					appoint.setAppointmentDate(new SimpleDateFormat("dd/MM/yyyy").parse(map.get("appointment_date")));
					appoint.setAppointmentStatus(AppointmentStatus.OPEN);
					appoint.setLawyer(lawyerService.findByUuid(map.get("lawyeruuid")));
					appoint.setRegistrant(registrantService.findByUuid(map.get("reguuid")));
					appoint.setDoneBy(username);
					appointmentService.create(appoint);
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.save);
					rb.setObject(appoint);
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
			rb.setDescription("failed to Save Appointment");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	/**
	 * The Method to retrieve all Appointments
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value="",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>RetrieveAppointments(HttpServletRequest request){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					List<Appointment> appointments=appointmentService.all();
					
					
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription(Messages.save);
					rb.setObject(appointments);
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
			rb.setDescription("failed to Retrieve Appointments");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	/**
	 * The Method to retrieve Appointment By Uuid
	 * @param request
	 * @param uuid
	 * @return
	 */
	
	@RequestMapping(value="/{uuid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>RetrieveAppointmentByuuid(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					
					Appointment appointment=appointmentService.findByUuid(uuid);
					if(appointment==null) {
						rb.setCode(Messages.ERROR_CODE);
						rb.setDescription("The Appointment not found");
					}else {
						rb.setCode(Messages.SUCCESS_CODE);
						rb.setDescription(Messages.save);
						rb.setObject(appointment);
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
			rb.setDescription("failed to Retrieve Appointments");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	/**
	 * The Method to delete Appointment
	 * @param request
	 * @param uuid
	 * @return
	 */
	
	@RequestMapping(value="delete/{uuid}",method=RequestMethod.DELETE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>deleteAppointmentByuuid(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					
					Appointment appointment=appointmentService.findByUuid(uuid);
					if(appointment==null) {
						rb.setCode(Messages.ERROR_CODE);
						rb.setDescription("The Appointment not found");
					}else {
						appointmentService.delete(appointment);	
						rb.setCode(Messages.SUCCESS_CODE);
						rb.setDescription(Messages.delete);
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
			rb.setDescription("failed to Retrieve Appointments");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	/**
	 * The Method to update Appointment By Uuid
	 * @param request
	 * @param map
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value="/update/{uuid}",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>UpdateAppointment(HttpServletRequest request,@RequestParam Map<String,String>map,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			String username=request.getHeader("doneBy");
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					Appointment appoint=appointmentService.findByUuid(uuid);
					if(appoint==null) {
						rb.setCode(Messages.ERROR_CODE);
						rb.setDescription("The Appointment not found");
					}else {
						appoint.setAppointmentDate(new SimpleDateFormat("dd/MM/yyyy").parse(map.get("appointment_date")));
						appoint.setAppointmentStatus(AppointmentStatus.OPEN);
						appoint.setLawyer(lawyerService.findByUuid(map.get("lawyeruuid")));
						appoint.setRegistrant(registrantService.findByUuid(map.get("reguuid")));
						appoint.setDoneBy(username);
						appointmentService.update(appoint);
						rb.setCode(Messages.SUCCESS_CODE);
						rb.setDescription(Messages.update);
						rb.setObject(appoint);
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
			rb.setDescription("failed to Save Appointment");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	/**
	 * The Method to retrieve Appointment by RegistrantID
	 * @param request
	 * @param uuid
	 * @return
	 */
	
	@RequestMapping(value="/registrant/{uuid}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object>RetrieveAppointmentByRegistrant(HttpServletRequest request,@PathVariable String uuid){
		ResponseBean rb=new ResponseBean();
		try {
			String token=request.getHeader(Messages.olhs_token_name);
			
			if(token!=null) {
				if(token.equalsIgnoreCase(Messages.token)) {
					Registrant registrant=registrantService.findByUuid(uuid);
					
					List<Appointment> appointments=appointmentService.FindByRegistrantId(registrant.getId());
					
					
					rb.setCode(Messages.SUCCESS_CODE);
					rb.setDescription("Retrieved Successfuly");
					rb.setObject(appointments);
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
			rb.setDescription("failed to Retrieve Appointments");
			rb.setObject(null);
		}
		
		return new ResponseEntity<Object>(rb,HttpStatus.OK);
	}
	
	

}

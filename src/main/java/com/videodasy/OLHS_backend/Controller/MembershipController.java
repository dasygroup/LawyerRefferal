package com.videodasy.OLHS_backend.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.videodasy.OLHS_backend.Domain.Membership;
import com.videodasy.OLHS_backend.Domain.MembershipComment;
import com.videodasy.OLHS_backend.Domain.MembershipStatus;
import com.videodasy.OLHS_backend.Domain.MemebershipDocument;
import com.videodasy.OLHS_backend.Domain.Registrant;
import com.videodasy.OLHS_backend.Service.IMemberhipCommentService;
import com.videodasy.OLHS_backend.Service.IMembershipDocument;
import com.videodasy.OLHS_backend.Service.IMembershipService;
import com.videodasy.OLHS_backend.Service.IRegistrantService;
import com.videodasy.OLHS_backend.Utility.Messages;
import com.videodasy.OLHS_backend.Utility.ResponseBean;

@RestController
@RequestMapping(value="/memberships")
public class MembershipController {

	@Autowired
	private IMembershipService membershipService;
	
	@Autowired
	private IRegistrantService registrantService;
	@Autowired
	private IMembershipDocument documentservice;
	
	@Autowired
	private IMemberhipCommentService commentService;
	
	/**
	 * The Method to create A membership
	 * @param request
	 * @param files
	 * @param map
	 * @return
	 */
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Object> createMembership(HttpServletRequest request, @RequestParam("files") MultipartFile[] files,
	 @RequestParam Map<String, String> map) {
		ResponseBean rs = new ResponseBean();
		Membership m = new Membership();
		try {

			String userToken = request.getHeader(Messages.token_name);

			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else {
				Registrant applicant = registrantService.findByUuid(map.get("rguuid"));
				
				List<MultipartFile> fil = Arrays.asList(files);

				if (applicant == null) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("applicant not found");
					rs.setObject(null);
		 
				} else {
					Object upload = membershipService.upLoad(fil, applicant.getUuid());
					
					if (upload != null) {
						m.setApplicant(applicant);
						m.setDoneBy(map.get("username"));
						m.setLastUpdatedBy(map.get("username"));
						m.setAmount(Double.parseDouble(map.get("amount")));
						m.setDescription(map.get("description"));
						m.setMemberStatus(MembershipStatus.PENDING);
						m.setApplicationDate(new SimpleDateFormat("dd/MM/yyyy").parse(map.get("membership_date")));
						membershipService.create(m);
				
						@SuppressWarnings("unchecked")
						List<String> li = (List<String>) upload;
						for (String s : li) {
							MemebershipDocument md = new MemebershipDocument();
							md.setMembership(m);
							md.setPath(s);
							String filename = s.split("_")[1];
							md.setFileName(filename);
							md.setDoneBy(map.get("username"));
							md.setLastUpdatedBy(map.get("username"));
							documentservice.create(md);
							
						}
						rs.setCode(Messages.SUCCESS_CODE);
						rs.setDescription("success");
						rs.setObject(m);
					} else {
						rs.setCode(Messages.ERROR_CODE);
						rs.setDescription("Error occured while trying to upload files");
						rs.setObject(null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	/**
	 * The method to Update The Membership
	 * @param files
	 * @param map
	 * @param membershipUuid
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/update/{membershipUuid}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateLoan(@RequestParam("files") MultipartFile[] files,
			@RequestParam Map<String, String> map,
			@PathVariable String membershipUuid, HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {

			String userToken = request.getHeader(Messages.token_name);

			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else {
				Membership membership = membershipService.findByUuid(membershipUuid);
				if (membership == null) {
					rs.setCode(404);
					rs.setDescription("membership not found");
					rs.setObject(null);
				} else {
					
					membership.setDoneBy(map.get("username"));
					membership.setLastUpdatedBy(map.get("username"));
					membership.setAmount(Double.parseDouble(map.get("amount")));
					membership.setDescription(map.get("description"));
					membership.setMemberStatus(MembershipStatus.PENDING);
					membership.setApplicationDate(new SimpleDateFormat("dd/MM/yyyy").parse(map.get("membership_date")));
					
					if (membership.getMemberStatus().equals(MembershipStatus.REFERRAL)) {
						membership.setMemberStatus(MembershipStatus.RESUBMITTED);
					}
					membershipService.update(membership);
					List<MultipartFile> fil = Arrays.asList(files);
					if (!fil.isEmpty()) {
						Object upload = membershipService.upLoad(fil, membership.getUuid());
						if (upload == null) {
							rs.setCode(300);
							rs.setDescription("Error occured when trying to upload the file");
							rs.setObject(null);
							return new ResponseEntity<>(rs, HttpStatus.OK);
						} else {
							documentservice.deleteAllByMembership(membership.getId());
							@SuppressWarnings("unchecked")
							List<String> li = (List<String>) upload;
							for (String s : li) {
								MemebershipDocument md = new MemebershipDocument();
								md.setMembership(membership);
								md.setPath(s);
								String filename = s.split("_")[1];
								md.setFileName(filename);
								md.setDoneBy(map.get("username"));
								md.setLastUpdatedBy(map.get("username"));
								documentservice.create(md);
							}
						}
					}

					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(membership);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	/**
	 * The Method to delete Membership
	 * @param uuid
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/delete/{uuid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteLoan(@PathVariable String uuid, HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);
			String username = request.getHeader("doneBy");
			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else if (username == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("username not available");
				rs.setObject(null);
			} else {
				Membership membership = membershipService.findByUuid(uuid);
				if (membership == null) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("Membership with id:" + uuid + " wasn't found");
					rs.setObject(null);
				} else {
					membership.setLastUpdatedBy(username);
					List<MemebershipDocument> md = documentservice.FindByMembershipId(membership.getId());
					membershipService.delete(membership);
					for (MemebershipDocument d : md) {
						d.setLastUpdatedBy(username);
						documentservice.delete(d);
					}
					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(null);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	/**
	 * The Membership to Cancel Membership
	 * @param uuid
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/cancel/{uuid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cancelLoan(@PathVariable String uuid, HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);
			String username = request.getHeader("doneBy");
			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else if (username == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("username not available");
				rs.setObject(null);
			} else {
				Membership membership = membershipService.findByUuid(uuid);
				if (membership == null) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("Membership with id:" + uuid + " wasn't found");
					rs.setObject(null);
				} else {
					membership.setLastUpdatedBy(username);
					membership.setMemberStatus(MembershipStatus.CANCELED);
					membershipService.update(membership);
					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(null);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	/**
	 * The Method to Confirm Membership
	 * @param uuid
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/confirm/{uuid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> confirmLoanPayment(@PathVariable String uuid, HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);
			String username = request.getHeader("doneBy");
			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else if (username == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("username not available");
				rs.setObject(null);
			} else {
				Membership membership = membershipService.findByUuid(uuid);
				if (membership == null) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("Loan with id:" + uuid + " wasn't found");
					rs.setObject(null);
				} else {
					membership.setLastUpdatedBy(username);
					membership.setPaymentReceived(true);
					membershipService.update(membership);
					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(membership);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	/**
	 * The Method to retrieve Membership by Uuid
	 * @param uuid
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findByUuid(@PathVariable String uuid, HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);

			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else {

				Membership membership = membershipService.findByUuid(uuid);
				if (membership == null || membership.isDeletedStatus() == true) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("membership with uuid:" + uuid + " wasn't found");
					rs.setObject(null);
				} else {
					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(membership);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	/**
	 * The Method to retrieve All Membership
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findAll(HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);

			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else {
				List<Membership> li = membershipService.all();
				rs.setCode(Messages.SUCCESS_CODE);
				rs.setDescription("success");
				rs.setObject(li);
			}
		} catch (Exception e) {

			e.printStackTrace();
			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	/**
	 * The Method to save MembershipComment
	 * @param mUuid
	 * @param mComment
	 * @param request
	 * @return
	 */
		
	@RequestMapping(value = "/{mUuid}/comments/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createComment(@PathVariable String mUuid, @RequestBody MembershipComment mComment,
			HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);
			String username = request.getHeader("username");
			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else if (username == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("username not found");
				rs.setObject(null);
			} else {
				Membership membership = membershipService.findByUuid(mUuid);
				if (membership == null) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("Loan with id:" + mUuid + " wasn't found");
					rs.setObject(null);
				} else {
					mComment.setMembership(membership);
					mComment.setDoneBy(username);
					mComment.setLastUpdatedBy(username);
					commentService.create(mComment);
					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(mComment);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	/**
	 * The Method to Update MembershipComment
	 * @param uuid
	 * @param mComment
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/comments/update/{uuid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateComment(@PathVariable String uuid, @RequestBody MembershipComment mComment,
			HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);
			String username = request.getHeader("username");

			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else if (username == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("username not found");
				rs.setObject(null);
			} else {
				MembershipComment mc = commentService.findByUuid(uuid);
				if (mc == null) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("MembershipComment with id:" + uuid + " wasn't found");
					rs.setObject(null);
				} else {
					mComment.setId(mc.getId());
					mComment.setUuid(mc.getUuid());
					mComment.setMembership(mc.getMembership());
					mComment.setDoneBy(mc.getDoneBy());
					mComment.setLastUpdatedBy(username);
					commentService.update(mComment);
					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(mComment);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/comments/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteComment(@PathVariable String id, HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);
			String username = request.getHeader("username");

			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else if (username == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("username not found");
				rs.setObject(null);
			} else {
				MembershipComment mc = commentService.findByUuid(id);
				if (mc == null) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("MembershipComment with id:" + id + " wasn't found");
					rs.setObject(null);
				} else {
					mc.setLastUpdatedBy(username);
					commentService.delete(mc);
					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(null);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	@RequestMapping(value = "/comments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> findOneComment(@PathVariable String id, HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();
		try {
			String userToken = request.getHeader(Messages.token_name);

			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else {
				MembershipComment mc = commentService.findByUuid(id);
				if (mc == null) {
					rs.setCode(Messages.NOT_FOUND);
					rs.setDescription("MembershipComment with id:" + id + " wasn't found");
					rs.setObject(null);
				} else {
					rs.setCode(Messages.SUCCESS_CODE);
					rs.setDescription("success");
					rs.setObject(mc);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			rs.setCode(Messages.ERROR_CODE);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}
	
	/**
	 * The Method to process Membership
	 * @param membershipProcess
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/processmembership", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> processLoan(@RequestBody MembershipProcess membershipProcess, HttpServletRequest request) {
		ResponseBean rs = new ResponseBean();

		try {

			String userToken = request.getHeader(Messages.token_name);

			if (userToken == null) {
				rs.setCode(Messages.ERROR_CODE);
				rs.setDescription("token not available");
				rs.setObject(null);
			} else if (!userToken.equalsIgnoreCase(Messages.token)) {
				rs.setCode(Messages.INCORRECT_TOKEN);
				rs.setDescription("Incorrect token");
				rs.setObject(null);
			} else {
				Membership membership = membershipService.findByUuid(membershipProcess.getMembershipUuid());
				if (membership == null) {
					rs.setCode(404);
					rs.setDescription("loan not found");
					rs.setObject(null);
				} else {
					String stage = membershipProcess.getStage();
					String action = membershipProcess.getDecision();
					if (action.equalsIgnoreCase("approve")) {
						if (stage.equals("002")) {
							membership.setMemberStatus(MembershipStatus.INPROGRESS);
						} else if (stage.equals("003")) {
							membership.setMemberStatus(MembershipStatus.INPROGRESS);
						} else if (stage.equals("004")) {
							membership.setMemberStatus(MembershipStatus.INPROGRESS);
						} else if (stage.equals("005")) {
						
							membership.setIssuedAt(new Date());
							membership.setMemberStatus(MembershipStatus.ISSUED);
						}
					} else if (action.equalsIgnoreCase("reject")) {
						membership.setMemberStatus(MembershipStatus.REJECTED);
					} else if (action.equalsIgnoreCase("refer")) {
						membership.setMemberStatus(MembershipStatus.REFERRAL);
					}
					membership.setMembershipStage(membershipProcess.getStage());
					membership.setLastUpdatedBy(membershipProcess.getDoneBy());
					if (membershipProcess.getComment().length() > 0) {
						membership.setComment(membershipProcess.getComment());
						membershipService.update(membership);
						MembershipComment mc = new MembershipComment();
						mc.setComment(membershipProcess.getComment());
						mc.setMembership(membership);
						mc.setDoneBy(membershipProcess.getDoneBy());
						mc.setLastUpdatedBy(membershipProcess.getDoneBy());
						commentService.create(mc);
					} else {
						membershipService.update(membership);
					}
					rs.setCode(200);
					rs.setDescription(Messages.save);
					rs.setObject(membership);
				}
			}
		} catch (Exception e) {
			rs.setCode(300);
			rs.setDescription(Messages.error);
			rs.setObject(null);
		}
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	
	/**
	 * The Method to Download Membership Document File
	 * @param param
	 * @param uuid
	 * @return
	 * @throws IOException
	 */
		@RequestMapping(path = "/documents/download/{uuid}", method = RequestMethod.GET)
		public ResponseEntity<Resource> download(String param, @PathVariable("uuid") String uuid) throws IOException {

			MemebershipDocument doc = documentservice.findByUuid(uuid);
			String filePath = doc.getPath();
			File file = new File(filePath);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Content-Disposition", "inline; filename=" + doc.getFileName());
			Path path = Paths.get(filePath);
			byte[] b = Files.readAllBytes((path));
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(bis));
		}

		public static class MembershipProcess {

			private String membershipUuid;
			private String stage;
			private String comment;
			private String decision;
			private String doneBy;
			private Date paymentDate;

		

			public String getMembershipUuid() {
				return membershipUuid;
			}

			public void setMembershipUuid(String membershipUuid) {
				this.membershipUuid = membershipUuid;
			}

			public String getStage() {
				return stage;
			}

			public void setStage(String stage) {
				this.stage = stage;
			}

			public String getComment() {
				return comment;
			}

			public void setComment(String comment) {
				this.comment = comment;
			}

			public String getDecision() {
				return decision;
			}

			public void setDecision(String decision) {
				this.decision = decision;
			}

			public String getDoneBy() {
				return doneBy;
			}

			public void setDoneBy(String doneBy) {
				this.doneBy = doneBy;
			}

			/**
			 * @return Date return the paymentDate
			 */
			public Date getPaymentDate() {
				return paymentDate;
			}

			/**
			 * @param paymentDate the paymentDate to set
			 */
			public void setPaymentDate(Date paymentDate) {
				this.paymentDate = paymentDate;
			}
		}

}

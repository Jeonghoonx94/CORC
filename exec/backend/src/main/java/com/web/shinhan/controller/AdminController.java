package com.web.shinhan.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.shinhan.model.PaymentDto;
import com.web.shinhan.model.StoreDto;
import com.web.shinhan.model.UserDto;
import com.web.shinhan.model.service.PaymentService;
import com.web.shinhan.model.service.StoreService;
import com.web.shinhan.model.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = { "*" })
public class AdminController {

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	UserService userService;

	@Autowired
	StoreService storeService;

	@Autowired
	PaymentService paymentService;

	@ApiOperation(value = "회원 목록 조회", notes = "회원들의 정보를 반환한다.", response = HashMap.class)
	@GetMapping("/user/list")
	public ResponseEntity<Map<String, Object>> findUserList(@RequestParam String email, Pageable pageable)
			throws Exception {
		logger.info("findUserList - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		Page<UserDto> page = null;

		// 회원 정보 조회
		try {
			page = userService.findAllUser(pageable);
			resultMap.put("userList", page);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원 정보 조회", notes = "회원의 정보를 가지고 온다.", response = HashMap.class)
	@GetMapping("/user/info")
	public ResponseEntity<Map<String, Object>> findUserInfo(@RequestParam int userId, HttpServletRequest req)
			throws Exception {
		logger.info("findUserInfo - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		// 회원 정보 조회
		try {
			resultMap.put("info", userService.findUserInfo(userId));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원 결제 내역", notes = "회원의 결제 내역을 가지고 온다.", response = HashMap.class)
	@GetMapping("/user/payment")
	public ResponseEntity<Map<String, Object>> findUserPayment(@RequestParam int userId, Pageable pageable,
			HttpServletRequest req) throws Exception {
		logger.info("findUserPayment - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		Page<PaymentDto> page = null;
		HttpStatus status = HttpStatus.ACCEPTED;

		// 회원 정보 조회
		try {
			resultMap.put("info", userService.findUserInfo(userId));
			page = paymentService.findUserPayment(userId, pageable);
			resultMap.put("paymentList", page);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "사용자 등록", notes = "입력한 정보를 토대로 DB에 정보를 저장한다.", response = Boolean.class)
	@PostMapping("/user/regist")
	public ResponseEntity<Boolean> registUser(@RequestBody UserDto user) {
		logger.info("regist - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 회원 정보 담기
		user.setAccessTime(LocalDateTime.now());
		user.setLimitTime(LocalDateTime.now().plusDays(30));

		// 회원가입
		try {
			userService.insertUser(user);
			flag = true;
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "회원 활성화", notes = "회원들을 활성화하여 성공 여부에 따라 true, false를 반환한다.", response = Boolean.class)
	@PutMapping("/user/activate")
	public ResponseEntity<Boolean> userStatus(@RequestBody int[] userIds, @RequestBody int userStatus) {
		logger.info("activateUser - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		try {
			if (userStatus == 1) {
				for (int userId : userIds) {
					int active = userService.activateUser(userId);
					if (active == 0) {
						return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
					}
				}
			} else if (userStatus == 2) {
				for (int userId : userIds) {
					int active = userService.banUser(userId);
					if (active == 0) {
						return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
					}
				}
			} else if (userStatus == 0) {
				for (int userId : userIds) {
					int active = userService.deleteUser(userId);
					if (active == 0) {
						return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
					}
				}
			}
			flag = true;
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "이메일 중복 체크", notes = "같은 이메일로 가입한 사용자가 있는지 확인한다.", response = Boolean.class)
	@PostMapping("/check/email")
	public ResponseEntity<Boolean> emailCheck(@RequestParam String email) {
		logger.info("emailCheck - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Boolean>(userService.emailCheck(email), status);
	}

	@ApiOperation(value = "사번 중복 체크", notes = "같은 사번으로 가입한 사용자가 있는지 확인한다.", response = Boolean.class)
	@PostMapping("/check/employeenum")
	public ResponseEntity<Boolean> employeenumCheck(@RequestParam int employee_num) {
		logger.info("employeenumCheck - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;

		return new ResponseEntity<Boolean>(userService.employeenumCheck(employee_num), status);

	}

	@ApiOperation(value = "회원 정보 수정", notes = "회원의 정보를 수정한다.", response = Boolean.class)
	@PutMapping("/modify/user")
	public ResponseEntity<Boolean> modifyUserInfo(@RequestBody UserDto newDto) {
		logger.info("modifyUserInfo - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 회원 정보 조회
		String email = newDto.getEmail();
		// 회원 소개 수정
		try {
			flag = userService.modifyUserInfo(email, newDto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "회원 한도 수정", notes = "회원의 한도를 수정한다.", response = Boolean.class)
	@PutMapping("/modify/limit")
	public ResponseEntity<Boolean> modifyCardLimit(@RequestBody int[] userIds, int limit) {
		logger.info("modifyCardLimit - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		try {
//			int cnt = 0;
			for (int userId : userIds) {
//				int limit = limits[cnt];
				flag = userService.modifyCardLimit(userId, limit);
				if (!flag) {
					return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
				}
//				cnt++;
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "결제 내역", notes = "모든 결제 내역을 반환한다.", response = HashMap.class)
	@GetMapping("/payment")
	public ResponseEntity<Map<String, Object>> findAllPayment(Pageable pageable) {
		logger.info("findPayment - 호출, ");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		Page<PaymentDto> page = null;

		try {
			page = paymentService.findAll(pageable);
			resultMap.put("paymentList", page);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "정산", notes = "선택된 결제 내역을 정산한다.", response = Boolean.class)
	@GetMapping("/payment/confirm")
	public ResponseEntity<Boolean> confirmPayment(@RequestBody int[] paymentIds) {
		logger.info("confirmPayment - 호출 ");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		try {
			for (int paymentId : paymentIds) {
				flag = paymentService.confirmPayment(paymentId);
				if (!flag) {
					return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
				}
			}
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, HttpStatus.OK);
	}

	@ApiOperation(value = "가맹점 목록 조회", notes = "가맹점들의 정보를 반환한다.", response = HashMap.class)
	@GetMapping("/store/list")
	public ResponseEntity<Map<String, Object>> findStoreList(Pageable pageable) throws Exception {
		logger.info("findStoreList - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.OK;
		Page<StoreDto> page = null;

		// 가맹점 정보 조회
		try {
			page = storeService.findAllStore(pageable);
			resultMap.put("storeList", page);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "가맹점 정보 조회", notes = "가맹점의 정보를 가지고 온다.", response = HashMap.class)
	@GetMapping("/store/info")
	public ResponseEntity<Map<String, Object>> findStoreInfo(@RequestParam int storeId, HttpServletRequest req)
			throws Exception {
		logger.info("findStoreInfo - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		// 가맹점 정보 조회
		try {
			resultMap.put("info", storeService.findStoreInfo(storeId));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "가맹점 결제 내역", notes = "가맹점의 결제 내역을 가지고 온다.", response = HashMap.class)
	@GetMapping("/store/payment")
	public ResponseEntity<Map<String, Object>> findStorePayment(@RequestParam int storeId, Pageable pageable,
			HttpServletRequest req) throws Exception {
		logger.info("findStorePayment - 호출");

		Map<String, Object> resultMap = new HashMap<>();
		Page<PaymentDto> page = null;
		HttpStatus status = HttpStatus.ACCEPTED;

		// 가맹점 정보 조회
		try {
			resultMap.put("info", storeService.findStoreInfo(storeId));
			page = paymentService.findStorePayment(storeId, pageable);
			resultMap.put("paymentList", page);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "가맹점 등록", notes = "입력한 정보를 토대로 DB에 정보를 저장한다.", response = Boolean.class)
	@PostMapping("/store/regist")
	public ResponseEntity<Boolean> registStore(@RequestBody StoreDto store) {
		logger.info("regist - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 가맹점가입
		try {
			storeService.registStore(store);
			flag = true;
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "가맹점 정보 수정", notes = "가맹점의 정보를 수정한다.", response = Boolean.class)
	@PutMapping("/store/modify")
	public ResponseEntity<Boolean> modifyStoreInfo(@RequestBody StoreDto newDto) {
		logger.info("modifyStoreInfo - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 가맹점 정보 조회
		String email = newDto.getEmail();
		// 가맹점 소개 수정
		try {
			flag = storeService.modifyStoreInfo(email, newDto);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "가맹점 정지", notes = "가맹점들을 정지 처리하여 성공 여부에 따라 true, false를 반환한다.", response = Boolean.class)
	@PutMapping("/store/status")
	public ResponseEntity<Boolean> storeApplication(@RequestBody int[] storeIds, int storeStatus) {
		logger.info("allowStoreApplication - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		// 가맹점 탈퇴
		try {
			if (storeStatus == 2) {
				for (int userId : storeIds) {
					int active = storeService.allowStoreApplication(userId);
					if (active == 0) {
						return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
					}
				}
			} else if (storeStatus == 0) {
				for (int storeId : storeIds) {
					int active = storeService.denyStoreApplication(storeId);
					if (active == 0) {
						return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
					}
				}
			}
			flag = true;
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

	@ApiOperation(value = "가맹점 신청 거부", notes = "가맹점들을 탈퇴 처리하여 성공 여부에 따라 true, false를 반환한다.", response = Boolean.class)
	@PutMapping("/deny")
	public ResponseEntity<Boolean> denyStoreApplication(@RequestBody int[] storeIds) {
		logger.info("denyStoreApplication - 호출");

		HttpStatus status = HttpStatus.ACCEPTED;
		boolean flag = false;

		try {
			for (int storeId : storeIds) {
				int active = storeService.denyStoreApplication(storeId);
				if (active == 0) {
					return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
				}
			}
			flag = true;
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Boolean>(flag, status);
	}

}
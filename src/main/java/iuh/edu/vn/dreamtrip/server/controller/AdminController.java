package iuh.edu.vn.dreamtrip.server.controller;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iuh.edu.vn.dreamtrip.server.dto.TaiKhoanAdminUserDTO;
import iuh.edu.vn.dreamtrip.server.entity.NguoiDung;
import iuh.edu.vn.dreamtrip.server.service.NguoiDungServiceImp;
import iuh.edu.vn.dreamtrip.server.service.TaiKhoanServiceImp;

@RestController
@RequestMapping("/admin")
public class AdminController {
	static Logger logger = Logger.getLogger(AdminController.class.getName());
	@Autowired
	private NguoiDungServiceImp nguoiDungService;
	@Autowired
	private TaiKhoanServiceImp tkDB;
	public AdminController(NguoiDungServiceImp nguoiDungService) {
		this.nguoiDungService = nguoiDungService;
	}
	// Đk tài khoản đầu vào là một lớp dto chứa 2 object là tk và thông tin tài khoản
	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@PostMapping("/create")
	public boolean insertNguoiDung(@RequestBody TaiKhoanAdminUserDTO tk_user_dto) throws InterruptedException, ExecutionException {
		if(tkDB.getTK(tk_user_dto.getTk().getUserName()) != null)
		{
			logger.log(Level.SEVERE, "Erro tao tai khoan: tên tài khoản đã tồn tại!");
			return false;
		}
		try {
			tkDB.insertTK(tk_user_dto.getTk());
			nguoiDungService.insertNguoiDung(tk_user_dto);
			return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erro tao tai khoan:"+e);
			logger.log(Level.SEVERE, "tai khoan:"+tk_user_dto);
			return false;
		}
	}
	
	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@GetMapping("/get")
	public NguoiDung getNguoiDung(@RequestParam String document_id) throws InterruptedException, ExecutionException {
		NguoiDung nd =  nguoiDungService.getNguoiDung(document_id);
		if (nd != null) {
			return nd;
		}
		logger.log(Level.WARNING, "Không tìm thấy người dùng có id: "+document_id);
		return null;
	}
	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@GetMapping("/findByEmail")
	public NguoiDung findNguoiDungByEmail(@RequestParam String email) throws InterruptedException, ExecutionException {
		NguoiDung nd =  nguoiDungService.getNguoiDungByEmail(email);
		return nd;
	}
	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@PutMapping("/update")
	public boolean updateNguoiDung(@RequestBody NguoiDung nguoiDung) throws InterruptedException, ExecutionException {
		String res =  nguoiDungService.updateNguoiDung(nguoiDung);
		if (res != null) {
			return true;
		}
		logger.log(Level.WARNING, "ERRO: Lỗi cập nhật thông tin : "+ nguoiDung);
		return false;
	}
	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@DeleteMapping("/delete")
	public boolean deleteNguoiDung(@RequestParam String document_id) throws InterruptedException, ExecutionException {
		if(nguoiDungService.getNguoiDung(document_id) == null)
		{
			logger.log(Level.SEVERE, "Erro xóa tai khoan: tài khoản không tồn tại!");
			return false;
		}
		try {
			 nguoiDungService.deleteNguoiDung(document_id);
			 return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erro xóa người dùng:"+e);
			 return false;
		}
	}
}

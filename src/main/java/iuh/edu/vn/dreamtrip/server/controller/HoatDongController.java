package iuh.edu.vn.dreamtrip.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import iuh.edu.vn.dreamtrip.server.entity.Tour;
import iuh.edu.vn.dreamtrip.server.service.DanhGiaServiceImp;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iuh.edu.vn.dreamtrip.server.entity.HoatDong;
import iuh.edu.vn.dreamtrip.server.service.HoatDongService;
import iuh.edu.vn.dreamtrip.server.service.HoatDongServiceImp;

import lombok.NoArgsConstructor;

@CrossOrigin()
@RestController
@RequestMapping("/hoatdong")
@NoArgsConstructor
public class HoatDongController {
	Logger logger = Logger.getLogger(HoatDongController.class.getName());
	@Autowired
	HoatDongServiceImp dbHoatDong;
	@Autowired
	DanhGiaServiceImp danhGiaService;

	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@PostMapping("/insert")
	public boolean insertHoatDong(@RequestBody HoatDong hd) throws InterruptedException, ExecutionException {
		dbHoatDong.createHoatDong(hd);
		logger.log(Level.INFO, "Hoạt động: " + hd);
		return true;
	}

	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http:/localhost:8081"})
	@GetMapping("/find")
	public List<HoatDong> getHoatDong(@RequestParam String tourID) throws InterruptedException, ExecutionException {
		List<HoatDong> listHD = dbHoatDong.getHoatDong(tourID);
		if (listHD != null) {
			return listHD;
		}
		logger.log(Level.WARNING, "Không tìm thấy hoạt động nào");
		return null;
	}

	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@PutMapping("/update")
	public boolean updateHoatDong(@RequestBody HoatDong hd) throws InterruptedException, ExecutionException {
		String res = dbHoatDong.updateHoatDong(hd);
		if (res != null) {
			return true;
		}
		logger.log(Level.WARNING, "ERRO: Lỗi cập nhật thông tin hoạt động : " + hd);
		return false;
	}

	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@DeleteMapping("/delete")
	public boolean deleteHoatDong(@RequestParam String document_id) throws InterruptedException, ExecutionException {
		if (dbHoatDong.getHoatDong(document_id) == null) {
			logger.log(Level.SEVERE, "Erro xóa tai khoan: tài khoản không tồn tại!");
			return false;
		}
		try {
			dbHoatDong.deleteHoatDong(document_id);
			return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Erro xóa hoạt động:" + e);
			return false;
		}
	}

	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@GetMapping("/findbyTourId")
	public List<HoatDong> findAllHDbyTourID(@RequestParam String tourId)
			throws InterruptedException, ExecutionException {
		return dbHoatDong.findAllsByTourId(tourId);
	}

	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
	@GetMapping("/findalls")
	public List<HoatDong> findAlls() throws InterruptedException, ExecutionException {
		return dbHoatDong.findAlls();
	}

	@CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
	@GetMapping("/rate")
	public List<HoatDong> getTourForRate(@RequestParam String userId, @RequestParam boolean status) throws ExecutionException, InterruptedException {
		List<HoatDong> list = new ArrayList<>();
		List<String> listId = danhGiaService.getByUserId(userId, status);
		for (String id : listId) {
			list.add(dbHoatDong.findById(id));
		}
		return list;
	}
}

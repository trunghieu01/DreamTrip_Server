package iuh.edu.vn.dreamtrip.server.service;

import java.util.concurrent.ExecutionException;

import iuh.edu.vn.dreamtrip.server.dto.TaiKhoanAdminUserDTO;
import iuh.edu.vn.dreamtrip.server.entity.NguoiDung;
import iuh.edu.vn.dreamtrip.server.entity.TaiKhoan;

public interface NguoiDungService {

	public String insertNguoiDung(TaiKhoanAdminUserDTO tk_user_dto) throws InterruptedException, ExecutionException;

	public String insert(NguoiDung tk_user_dto) throws InterruptedException, ExecutionException;

	public NguoiDung getNguoiDung(String document_id) throws InterruptedException, ExecutionException;
	public NguoiDung getNguoiDungByEmail(String email) throws InterruptedException, ExecutionException;

	public String updateNguoiDung(NguoiDung adminUser) throws InterruptedException, ExecutionException;

	public String deleteNguoiDung(String document_id);
}

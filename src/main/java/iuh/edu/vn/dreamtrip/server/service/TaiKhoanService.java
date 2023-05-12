package iuh.edu.vn.dreamtrip.server.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import iuh.edu.vn.dreamtrip.server.entity.NguoiDung;
import iuh.edu.vn.dreamtrip.server.entity.TaiKhoan;

public interface TaiKhoanService {

	public String insertTK(TaiKhoan taiKhoan) throws InterruptedException, ExecutionException;

	public TaiKhoan getTK(String userName) throws InterruptedException, ExecutionException;

	public String updateTK(TaiKhoan taiKhoan) throws InterruptedException, ExecutionException;

	public String deleteTK(String userName);
	public List<TaiKhoan> findAll() throws ExecutionException, InterruptedException;
}

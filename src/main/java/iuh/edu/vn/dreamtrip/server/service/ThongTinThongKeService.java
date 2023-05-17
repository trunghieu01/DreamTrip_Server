package iuh.edu.vn.dreamtrip.server.service;

import iuh.edu.vn.dreamtrip.server.dto.ThongTinThongKeThangDTO;
import iuh.edu.vn.dreamtrip.server.entity.ThongTinThongKe;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ThongTinThongKeService {
    public String ínsert(ThongTinThongKe tttk) throws ExecutionException, InterruptedException;
    public String update(ThongTinThongKe tttk) throws ExecutionException, InterruptedException;
    public List<ThongTinThongKe> getTttkByThangNam(int thang, int nam) throws ExecutionException, InterruptedException;
    public ThongTinThongKe getThongTinThongKeByIdTour(String tourId) throws ExecutionException, InterruptedException;
    public List<ThongTinThongKeThangDTO> thongKeCacThangTrongNam(int nam) throws ExecutionException, InterruptedException;
    public ThongTinThongKe getTttkByThangNamAndTourId(String tourId) throws ExecutionException, InterruptedException ;
	List<ThongTinThongKe> getTTTKByTourId(String tourId) throws ExecutionException, InterruptedException;
	List<ThongTinThongKe> getTttkOfOneTour(int thang, int nam, String tourId)
			throws ExecutionException, InterruptedException;
	List<ThongTinThongKeThangDTO> thongKeCacThangTrongNamOfOne(int nam, String tourId)
			throws ExecutionException, InterruptedException;
    }

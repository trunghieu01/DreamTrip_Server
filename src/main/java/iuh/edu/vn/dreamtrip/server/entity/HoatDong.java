package iuh.edu.vn.dreamtrip.server.entity;

import java.util.List;

import com.google.cloud.firestore.annotation.DocumentId;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HoatDong {
	@DocumentId
	private String id;
	private String thoiGianHD;
	private String viTri;
	private String thongTin;
	private String tieuDe;
	private String tourId;
	private List<String> hinhAnh;
	private double longitude;
	private double latitude;
	private int danhGia;
	private String doanPhim;
	private String amThanh;
}

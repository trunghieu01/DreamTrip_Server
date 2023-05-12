package iuh.edu.vn.dreamtrip.server.entity;

import com.google.cloud.firestore.annotation.DocumentId;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class NguoiDung {
	@DocumentId
	private String document_id;
	private String ten;
	private String diaChi;
	private String email;
	private String sdt;
	private String avatar;

	public NguoiDung(String ten, String diaChi, String email, String sdt, String avatar) {
		this.ten = ten;
		this.diaChi = diaChi;
		this.email = email;
		this.sdt = sdt;
		this.avatar = avatar;
	}
}

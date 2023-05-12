package iuh.edu.vn.dreamtrip.server.dto;

import iuh.edu.vn.dreamtrip.server.entity.NguoiDung;
import iuh.edu.vn.dreamtrip.server.entity.TaiKhoan;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class TaiKhoanAdminUserDTO {
	private TaiKhoan tk;
	private NguoiDung nguoiDung;
	
}

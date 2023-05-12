package iuh.edu.vn.dreamtrip.server.entity;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaiKhoan {
	private String userName;
	private String password;
	private boolean status;// mặc định flase --. verify sang true
	private boolean isAdmin;// admin hay ko

}

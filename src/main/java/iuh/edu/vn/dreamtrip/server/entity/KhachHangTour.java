package iuh.edu.vn.dreamtrip.server.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class KhachHangTour {
    @DocumentId
    private String document_id;
    private String nguoiDungId;
    private String tourId;
    private String sdt;
    private int nguoiLon;
    private int treEm;
    private Date ngayDi;
    private boolean status;
}

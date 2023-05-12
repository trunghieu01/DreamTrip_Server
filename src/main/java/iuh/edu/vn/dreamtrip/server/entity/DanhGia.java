package iuh.edu.vn.dreamtrip.server.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class DanhGia {
    @DocumentId
    private String id;
    private String nguoiDungID;
    private String tourId;
    private String binhLuan;
    private int danhGia;
    private String hoatDongID;
    private Date thoiGian;
    private boolean status;
}

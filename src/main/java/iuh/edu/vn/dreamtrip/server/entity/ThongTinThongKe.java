package iuh.edu.vn.dreamtrip.server.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinThongKe implements Serializable {

    @DocumentId
    private String document_id;
    private  String tourId;
    private String tenTour;
    private Date thangNam;
    private int slThich;
    private int slDatTour;
    private int slThemKeHoach;

}

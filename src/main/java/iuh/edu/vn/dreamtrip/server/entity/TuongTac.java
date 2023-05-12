package iuh.edu.vn.dreamtrip.server.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TuongTac {
    @DocumentId
    private String document_id;
    private String tourId;
    private List<String> userDaThich;
    private List<String> userDaDat;
    private List<String>  userLenKeHoach;
}

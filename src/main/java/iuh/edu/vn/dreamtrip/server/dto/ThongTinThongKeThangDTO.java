package iuh.edu.vn.dreamtrip.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ThongTinThongKeThangDTO {
    private String name;
    private int slThich;
    private int slDat;
    private int slThemKeHoach;
    private int amt;
}

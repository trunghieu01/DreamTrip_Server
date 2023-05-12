package iuh.edu.vn.dreamtrip.server.controller;

import iuh.edu.vn.dreamtrip.server.dto.ThongTinThongKeThangDTO;
import iuh.edu.vn.dreamtrip.server.entity.ThongTinThongKe;
import iuh.edu.vn.dreamtrip.server.entity.Tour;
import iuh.edu.vn.dreamtrip.server.service.ThongTinThongKeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin()
@RestController
@RequestMapping("/thongKe")
public class ThongTinThongKeController {
    @Autowired
    private ThongTinThongKeServiceImp dbTttk;

    public ThongTinThongKeController(ThongTinThongKeServiceImp dbTttk) {
         this.dbTttk = dbTttk;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/insert")
    public String insertTour(@RequestBody ThongTinThongKe tttk) throws InterruptedException, ExecutionException {
        return dbTttk.ínsert(tttk);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/find")
    public List<ThongTinThongKe> findByThang(@RequestParam int thang, int nam) throws InterruptedException, ExecutionException {
        return dbTttk.getTttkByThangNam(thang, nam);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByNam")
    public List<ThongTinThongKeThangDTO> findAllThangInNam(int nam) throws InterruptedException, ExecutionException {
        return dbTttk.thongKeCacThangTrongNam(nam);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findByThongKeTourNow")
    public ThongTinThongKe getTttkByThangNamAndTourId(String tourId) throws InterruptedException, ExecutionException {
        return dbTttk.getTttkByThangNamAndTourId(tourId);
    }
}

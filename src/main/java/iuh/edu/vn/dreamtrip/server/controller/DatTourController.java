package iuh.edu.vn.dreamtrip.server.controller;

import iuh.edu.vn.dreamtrip.server.entity.KhachHangTour;
import iuh.edu.vn.dreamtrip.server.entity.ThongTinThongKe;
import iuh.edu.vn.dreamtrip.server.entity.Tour;
import iuh.edu.vn.dreamtrip.server.service.DatTourServiceImp;
import iuh.edu.vn.dreamtrip.server.service.ThongTinThongKeServiceImp;
import iuh.edu.vn.dreamtrip.server.service.TourServiceImp;
import iuh.edu.vn.dreamtrip.server.service.TuongTacServiceImp;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin()
@RestController
@RequestMapping("/datTour")
@NoArgsConstructor
public class DatTourController {

    @Autowired
    DatTourServiceImp datTourService;

    @Autowired
    TuongTacServiceImp tuongTacService;

    @Autowired
    ThongTinThongKeServiceImp thongTinThongKeServiceImp;

    @Autowired
    TourServiceImp tourServiceImp;

    @PostMapping("/insert")
    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://192.168.1.2:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    public KhachHangTour insert(@RequestBody KhachHangTour k) throws ExecutionException, InterruptedException {
        datTourService.insert(k);
        ThongTinThongKe tttk = thongTinThongKeServiceImp.getTttkByThangNamAndTourId(k.getTourId());
        if (tttk != null) {
            int luotDat = tttk.getSlDatTour();
            luotDat = luotDat + 1;
            tttk.setSlDatTour(luotDat);
            String update = thongTinThongKeServiceImp.update(tttk);
            System.out.println(update);
            tuongTacService.book(k.getTourId(),k.getNguoiDungId());
        } else {
            Tour tour = tourServiceImp.getTour(k.getTourId());
            Date new_date = new Date();
            tttk = new ThongTinThongKe();
            tttk.setTourId(k.getTourId());
            tttk.setTenTour(tour.getTenTour());
            tttk.setThangNam(new_date);
            tttk.setSlThich(0);
            tttk.setSlDatTour(0);
            tttk.setSlThemKeHoach(1);
            String res = thongTinThongKeServiceImp.ínsert(tttk);
            tuongTacService.book(k.getTourId(),k.getNguoiDungId());
        }
        return k;
    }

    @GetMapping("/getAll")
    public List<KhachHangTour> getAll() throws ExecutionException, InterruptedException {
        return datTourService.getAll();
    }

    @GetMapping("/getChecked")
    public List<KhachHangTour> getChecked() throws ExecutionException, InterruptedException {
        return datTourService.getChecked();
    }

    @GetMapping("/getNotChecked")
    public List<KhachHangTour> getNotChecked() throws ExecutionException, InterruptedException {
        return datTourService.getNotCheck();
    }

    @GetMapping("/check")
    public boolean check(@RequestParam String tourId, @RequestParam String userId) throws ExecutionException, InterruptedException {
        return datTourService.check(tourId, userId);
    }
    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
    @PutMapping("/adminCheck")
    public boolean adminCheck(@RequestBody KhachHangTour khachHangTour) throws ExecutionException, InterruptedException {
        return datTourService.adminCheck(khachHangTour);
    }
    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/find")
    public List<KhachHangTour> findAllByTourIdAndUserId(@RequestParam String tourId, @RequestParam String userId) throws ExecutionException, InterruptedException {
        return datTourService.findKhTourByTourIdAndUserId(tourId, userId);
    }
}

package iuh.edu.vn.dreamtrip.server.controller;

import iuh.edu.vn.dreamtrip.server.entity.ThongTinThongKe;
import iuh.edu.vn.dreamtrip.server.entity.Tour;
import iuh.edu.vn.dreamtrip.server.entity.TuongTac;
import iuh.edu.vn.dreamtrip.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@CrossOrigin()
@RestController
@RequestMapping("/tuongtac")
public class TuongTacController {
    @Autowired
    TuongTacServiceImp tuongTacServiceImp;
    @Autowired
    ThongTinThongKeServiceImp thongTinThongKeServiceImp;
    @Autowired
    TourServiceImp tourServiceImp;
    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
    @PostMapping("/insert")
    public String insertTour(@RequestBody TuongTac tuongTac) throws InterruptedException, ExecutionException {
        return tuongTacServiceImp.insert(tuongTac);
    }

    @PutMapping("/update")
    public String update(@RequestBody TuongTac tuongTac) throws InterruptedException, ExecutionException {
        return tuongTacServiceImp.insert(tuongTac);
    }

    @PutMapping("/like")
    public String like(@RequestParam String tourId,@RequestParam String userId) throws InterruptedException, ExecutionException {
        ThongTinThongKe tttk = thongTinThongKeServiceImp.getTttkByThangNamAndTourId(tourId);
        if (tttk != null) {
            int luotLike = tttk.getSlThich();
            luotLike = luotLike + 1;
            tttk.setSlThich(luotLike);
            String update = thongTinThongKeServiceImp.update(tttk);
            System.out.println(update);
            return tuongTacServiceImp.like(tourId, userId);
        } else {
            Tour tour = tourServiceImp.getTour(tourId);
            Date new_date = new Date();
            tttk = new ThongTinThongKe();
            tttk.setTourId(tourId);
            tttk.setTenTour(tour.getTenTour());
            tttk.setThangNam(new_date);
            tttk.setSlThich(1);
            tttk.setSlDatTour(0);
            tttk.setSlThemKeHoach(0);
            String res = thongTinThongKeServiceImp.ínsert(tttk);
            return tuongTacServiceImp.like(tourId, userId);
        }
    }

    @PutMapping("/unlike")
    public String unlike(@RequestParam String tourId,@RequestParam String userId) throws InterruptedException, ExecutionException {
        ThongTinThongKe tttk = thongTinThongKeServiceImp.getTttkByThangNamAndTourId(tourId);
        if (tttk != null) {
            int luotLike = tttk.getSlThich();
            if (luotLike >0) {
                luotLike = luotLike - 1;
            } else {
                luotLike = 0;
            }
            tttk.setSlThich(luotLike);
            String update = thongTinThongKeServiceImp.update(tttk);
            System.out.println(update);
            return tuongTacServiceImp.unLike(tourId, userId);
        } else {
            Tour tour = tourServiceImp.getTour(tourId);
            Date new_date = new Date();
            tttk = new ThongTinThongKe();
            tttk.setTourId(tourId);
            tttk.setTenTour(tour.getTenTour());
            tttk.setThangNam(new_date);
            tttk.setSlThich(0);
            tttk.setSlDatTour(0);
            tttk.setSlThemKeHoach(0);
            String res = thongTinThongKeServiceImp.ínsert(tttk);
            return tuongTacServiceImp.unLike(tourId, userId);
        }
    }

    @PutMapping("/plan")
    public String plan(@RequestParam String tourId,@RequestParam String userId) throws InterruptedException, ExecutionException {
        ThongTinThongKe tttk = thongTinThongKeServiceImp.getTttkByThangNamAndTourId(tourId);
        if (tttk != null) {
            int luotThemKeHoach = tttk.getSlThemKeHoach();
            luotThemKeHoach = luotThemKeHoach + 1;
            tttk.setSlThemKeHoach(luotThemKeHoach);
            String update = thongTinThongKeServiceImp.update(tttk);
            System.out.println(update);
            return tuongTacServiceImp.plan(tourId,userId);
        } else {
            Tour tour = tourServiceImp.getTour(tourId);
            Date new_date = new Date();
            tttk = new ThongTinThongKe();
            tttk.setTourId(tourId);
            tttk.setTenTour(tour.getTenTour());
            tttk.setThangNam(new_date);
            tttk.setSlThich(0);
            tttk.setSlDatTour(0);
            tttk.setSlThemKeHoach(1);
            String res = thongTinThongKeServiceImp.ínsert(tttk);
            return tuongTacServiceImp.plan(tourId,userId);
        }

    }

    @PutMapping("/dropOurPlan")
    public String dropPlan(@RequestParam String tourId,@RequestParam String userId) throws InterruptedException, ExecutionException {
        ThongTinThongKe tttk = thongTinThongKeServiceImp.getTttkByThangNamAndTourId(tourId);
        if (tttk != null) {
            int luotThemKeHoach = tttk.getSlThemKeHoach();
            if (luotThemKeHoach > 0) {
                luotThemKeHoach = luotThemKeHoach - 1;
            } else {
                luotThemKeHoach = 0;
            }
            tttk.setSlThemKeHoach(luotThemKeHoach);
            String update = thongTinThongKeServiceImp.update(tttk);
            System.out.println(update);
            return tuongTacServiceImp.dropOutPlan(tourId,userId);
        } else {
            Tour tour = tourServiceImp.getTour(tourId);
            Date new_date = new Date();
            tttk = new ThongTinThongKe();
            tttk.setTourId(tourId);
            tttk.setTenTour(tour.getTenTour());
            tttk.setThangNam(new_date);
            tttk.setSlThich(0);
            tttk.setSlDatTour(0);
            tttk.setSlThemKeHoach(0);
            String res = thongTinThongKeServiceImp.ínsert(tttk);
            return tuongTacServiceImp.dropOutPlan(tourId,userId);
        }
    }

    @PutMapping("/book")
    public String book(@RequestParam String tourId,@RequestParam String userId) throws InterruptedException, ExecutionException {
        ThongTinThongKe tttk = thongTinThongKeServiceImp.getTttkByThangNamAndTourId(tourId);
        if (tttk != null) {
            int luotDat = tttk.getSlDatTour();
            luotDat = luotDat + 1;
            tttk.setSlDatTour(luotDat);
            String update = thongTinThongKeServiceImp.update(tttk);
            System.out.println(update);
            return tuongTacServiceImp.book(tourId,userId);
        } else {
            Tour tour = tourServiceImp.getTour(tourId);
            Date new_date = new Date();
            tttk = new ThongTinThongKe();
            tttk.setTourId(tourId);
            tttk.setTenTour(tour.getTenTour());
            tttk.setThangNam(new_date);
            tttk.setSlThich(0);
            tttk.setSlDatTour(0);
            tttk.setSlThemKeHoach(1);
            String res = thongTinThongKeServiceImp.ínsert(tttk);
            return tuongTacServiceImp.book(tourId, userId);
        }
    }

    @GetMapping("/check")
    public boolean checkLiked(@RequestParam String tourId,@RequestParam String userId) throws InterruptedException, ExecutionException {
        return tuongTacServiceImp.checkLike(tourId,userId);
    }
    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/findAll")
    public TuongTac findAllBytourId(@RequestParam String tourId) throws InterruptedException, ExecutionException {
        return tuongTacServiceImp.findAllByTourId(tourId);
    }
}

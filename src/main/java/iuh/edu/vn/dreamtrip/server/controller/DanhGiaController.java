package iuh.edu.vn.dreamtrip.server.controller;

import iuh.edu.vn.dreamtrip.server.entity.DanhGia;
import iuh.edu.vn.dreamtrip.server.service.DanhGiaServiceImp;
import iuh.edu.vn.dreamtrip.server.service.TourService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/danhGia")
@NoArgsConstructor
public class DanhGiaController {

    Logger logger = Logger.getLogger(DanhGiaController.class.getName());
    @Autowired
    DanhGiaServiceImp danhGiaService;

    @Autowired
    TourService tourService;

    @GetMapping("/getByUserId")
    public List<String> getByUserId(@RequestParam String userId, @RequestParam boolean status) throws InterruptedException, ExecutionException {
        logger.log(Level.WARNING, "UserId: " + userId);
        return danhGiaService.getByUserId(userId, status);
    }

    @GetMapping("/getForUser")
    public List<DanhGia> getDanhGia(@RequestParam String userId, @RequestParam boolean status) throws InterruptedException, ExecutionException {
        logger.log(Level.WARNING, "UserId: " + userId);
        return danhGiaService.getDanhGia(userId, status);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String id) throws ExecutionException, InterruptedException {
        logger.log(Level.WARNING, "Id: " + id);
        return danhGiaService.delete(id);
    }

    @PostMapping("/add")
    public DanhGia create(@RequestBody DanhGia d) throws ExecutionException, InterruptedException {
        return danhGiaService.insert(d);
    }

    @PutMapping("/update")
    public String update(@RequestParam String id, @RequestParam String comment, @RequestParam int rate,@RequestParam String tourId) throws ExecutionException, InterruptedException {
        logger.log(Level.WARNING, "Id: " + id);
        //Cap nhat danh gia cho tour
        tourService.updateRatingTour(danhGiaService.getForRatingTour(tourId), tourId);
        return danhGiaService.update(rate, comment, id);
    }
}

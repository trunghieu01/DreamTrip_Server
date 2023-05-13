package iuh.edu.vn.dreamtrip.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import iuh.edu.vn.dreamtrip.server.entity.HoatDong;
import iuh.edu.vn.dreamtrip.server.entity.TuongTac;
import iuh.edu.vn.dreamtrip.server.service.DanhGiaServiceImp;
import iuh.edu.vn.dreamtrip.server.service.HoatDongServiceImp;
import iuh.edu.vn.dreamtrip.server.service.TuongTacServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iuh.edu.vn.dreamtrip.server.entity.Tour;

import iuh.edu.vn.dreamtrip.server.service.TourServiceImp;

@CrossOrigin()
@RestController
@RequestMapping("/tour")
public class TourController {
    @Autowired
    TourServiceImp dbTour;
    @Autowired
    HoatDongServiceImp hoatDongservice;
    @Autowired
    TuongTacServiceImp tuongTacService;

    public TourController(TourServiceImp dbTour) {
        this.dbTour = dbTour;
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
    @PostMapping("/insert")
    public String insertTour(@RequestBody Tour tour) throws InterruptedException, ExecutionException {
        Tour new_tour = dbTour.insertTour(tour);
        List<String> new_list = new ArrayList<>();
        if ( new_tour != null){
            TuongTac tuongTac = new TuongTac();
            tuongTac.setTourId(new_tour.getDocument_id());
            tuongTac.setUserDaThich(new_list);
            tuongTac.setUserLenKeHoach(new_list);
            tuongTac.setUserDaDat(new_list);
            String new_tuongTac = tuongTacService.insert(tuongTac);
            return new_tuongTac;
        } else {
            return  "Không thể thêm tour";
        }

    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
    @GetMapping("/get_tour")
    public Tour getTour(@RequestParam String document_id) throws InterruptedException, ExecutionException {
        return dbTour.getTour(document_id);
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
    @PutMapping("/update")
    public String updateTour(@RequestBody Tour tour) throws InterruptedException, ExecutionException {
        return dbTour.updateTour(tour);
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"})
    @DeleteMapping("/delete")
    public String deleteTour(@RequestParam String document_id) throws InterruptedException, ExecutionException {
        String result = dbTour.deleteTour(document_id);
        if (result != null) {
            List<HoatDong> hoatDongs = hoatDongservice.findAllsByTourId(document_id);
            for (HoatDong hd :
                    hoatDongs) {
                hoatDongservice.deleteHoatDong(hd.getId());
            }
            return result;
        }
        return result;
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/findAlls")
    public ResponseEntity<List<Tour>> findAllTour() throws InterruptedException, ExecutionException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Get-Header", "ExampleHeader");
        return ResponseEntity.ok().headers(headers).body(dbTour.findAlls());
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/searchs")
    public List<Tour> searchTourByName(@RequestParam String tourName) throws InterruptedException, ExecutionException {
        return dbTour.searchTourByName(tourName);
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/findByCate")
    public ResponseEntity<List<Tour>> findByCate(@RequestParam String cate) throws InterruptedException, ExecutionException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Get-Header", "ExampleHeader");
        return ResponseEntity.ok().headers(headers).body(dbTour.findByCategory(cate));
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://192.168.34.106:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/findTrending")
    public ResponseEntity<List<Tour>> findTrending() throws InterruptedException, ExecutionException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Get-Header", "ExampleHeader");
        return ResponseEntity.ok().headers(headers).body(dbTour.findTrending());
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://192.168.34.106:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/findPopular")
    public ResponseEntity<List<Tour>> findPopular() throws InterruptedException, ExecutionException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Get-Header", "ExampleHeader");
        return ResponseEntity.ok().headers(headers).body(dbTour.findPopular());
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("/findFilter")
    public ResponseEntity<List<Tour>> findByNameAndCate(@RequestParam String cate, @RequestParam String name) throws InterruptedException, ExecutionException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Get-Header", "ExampleHeader");
        if (name == null) {
            return ResponseEntity.ok().headers(headers).body(dbTour.findByCategory(cate));
        }
        if (cate.equals("all")) {
            return ResponseEntity.ok().headers(headers).body(dbTour.searchTourByName(name));
        }
        return ResponseEntity.ok().headers(headers).body(dbTour.findByNameAndCate(name, cate));
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("liked")
    public List<Tour> getLiked(String userId) throws ExecutionException, InterruptedException {
        List<Tour> list = new ArrayList<>();
        List<String> tourId = tuongTacService.getTourIdLiked(userId);
        for (String id : tourId) {
            list.add(dbTour.getTour(id));
        }
        return list;
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("booked")
    public List<Tour> getBooked(String userId) throws ExecutionException, InterruptedException {
        List<Tour> list = new ArrayList<>();
        List<String> tourId = tuongTacService.getTourIdBooked(userId);
        for (String id : tourId) {
            list.add(dbTour.getTour(id));
        }
        return list;
    }

    @CrossOrigin(origins = {"https://tourapp-d8ea8.firebaseapp.com/", "https://tourapp-d8ea8.web.app/", "http://localhost:3000", "http://localhost:8081"}, allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @GetMapping("planed")
    public List<Tour> getPlaned(String userId) throws ExecutionException, InterruptedException {
        List<Tour> list = new ArrayList<>();
        List<String> tourId = tuongTacService.getTourIdPlaned(userId);
        for (String id : tourId) {
            list.add(dbTour.getTour(id));
        }
        return list;
    }
}



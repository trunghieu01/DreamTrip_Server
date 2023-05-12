package iuh.edu.vn.dreamtrip.server.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import iuh.edu.vn.dreamtrip.server.entity.KhachHangTour;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class DatTourServiceImp implements DatTourService {

    Firestore dbFireStore = FirestoreClient.getFirestore();

    @Override
    public KhachHangTour insert(KhachHangTour k) throws ExecutionException, InterruptedException {
        List<KhachHangTour> list = dbFireStore.collection("khachHangTour").whereEqualTo("tourId", k.getTourId()).whereEqualTo("nguoiDungId", k.getNguoiDungId()).get().get().getDocuments().parallelStream().map(tour -> {
            final var tourDocument = tour.toObject(KhachHangTour.class);
            return tourDocument;
        }).collect(Collectors.toList());
        boolean check = true;
        for (KhachHangTour t : list){
            if (!t.isStatus())
                check = false;
        }
        if (check == true) {
            dbFireStore.collection("khachHangTour").document().create(k);
            return k;
        }
        return null;
    }

    @Override
    public boolean update(KhachHangTour k) {
        return false;
    }

    @Override
    public List<KhachHangTour> getAll() throws ExecutionException, InterruptedException {
        return dbFireStore.collection("khachHangTour").get().get().getDocuments().parallelStream().map(tour -> {
            final var tourDocument = tour.toObject(KhachHangTour.class);
            return tourDocument;
        }).collect(Collectors.toList());
    }

    @Override
    public List<KhachHangTour> getNotCheck() throws ExecutionException, InterruptedException {
        return dbFireStore.collection("khachHangTour").whereEqualTo("status", false).get().get().getDocuments().parallelStream().map(tour -> {
            final var tourDocument = tour.toObject(KhachHangTour.class);
            return tourDocument;
        }).collect(Collectors.toList());
    }

    @Override
    public List<KhachHangTour> getChecked() throws ExecutionException, InterruptedException {
        return dbFireStore.collection("khachHangTour").whereEqualTo("status", true).get().get().getDocuments().parallelStream().map(tour -> {
            final var tourDocument = tour.toObject(KhachHangTour.class);
            return tourDocument;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean check(String tourId, String userId) throws ExecutionException, InterruptedException {
        List<KhachHangTour> list = dbFireStore.collection("khachHangTour").whereEqualTo("tourId", tourId).whereEqualTo("nguoiDungId", userId).get().get().getDocuments().parallelStream().map(tour -> {
            final var tourDocument = tour.toObject(KhachHangTour.class);
            return tourDocument;
        }).collect(Collectors.toList());
        boolean check = true;
        for (KhachHangTour t : list){
            if (!t.isStatus())
                check = false;
        }
        return check;
    }
    @Override
    public boolean adminCheck(KhachHangTour khachHangTour) throws ExecutionException, InterruptedException {

           if (khachHangTour.isStatus()) {
               return false;
           } else {
               khachHangTour.setStatus(true);
               ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection("khachHangTour").document(khachHangTour.getDocument_id())
                       .set(khachHangTour);
               return collectionApiFuture.get().getUpdateTime().toString() != "" ?true :false;
           }
    }
    @Override
    public List<KhachHangTour> findKhTourByTourIdAndUserId(String tourId, String userId) throws ExecutionException, InterruptedException {
        List<KhachHangTour> list = dbFireStore.collection("khachHangTour").whereEqualTo("tourId", tourId).whereEqualTo("nguoiDungId", userId).get().get().getDocuments().parallelStream().map(tour -> {
            final var tourDocument = tour.toObject(KhachHangTour.class);
            return tourDocument;
        }).collect(Collectors.toList());
        return list;
    }
}
